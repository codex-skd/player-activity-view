package com.skd.playeractivityview;

import com.mojang.datafixers.util.Pair;
import com.skd.playeractivityview.client.screen.RenderHelper;
import com.skd.playeractivityview.client.screen.ScreenParticleRenderer;
import com.skd.playeractivityview.config.ConfigClient;
import com.skd.playeractivityview.config.CustomArmCorrections;
import com.skd.playeractivityview.config.ServerConfigSyncHelper;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import com.skd.playeractivityview.math.Lerpables;
import com.skd.playeractivityview.particle.ParticleAnimated;
import com.skd.playeractivityview.particle.ParticleItem;
import com.skd.playeractivityview.particle.ParticleRotating;
import com.skd.playeractivityview.particle.ParticleStatic;
import com.skd.playeractivityview.particle.ParticleStaticLoD;
import com.skd.playeractivityview.particle.ParticleStaticPartial;
import com.mojang.authlib.GameProfile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractCommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class PlayerStatusManagerClient extends PlayerStatusManager {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("player_activity_view/net");
    private static final int SCREEN_TYPING_CHAR_LIMIT = 50;

    private final PlayerStatus selfPlayerStatus = new PlayerStatus(PlayerStatus.PlayerGuiState.NONE, null);
    private final PlayerStatus selfPlayerStatusPrev = new PlayerStatus(PlayerStatus.PlayerGuiState.NONE, null);
    public final HashMap<UUID, PlayerStatus> lookupPlayerToStatusPrev = new HashMap<>();
    private final long typingIdleTimeout = 60L;
    private final int armMouseTickRate = 5;
    private int typeRatePollCounter = 0;
    private int steadyTickCounter = 0;
    private final int forcedSyncRate = 40;
    private Level lastLevel;
    private boolean wasMousePressed = false;
    private int mousePressedCountdown = 0;
    public ShaderInstanceBlur positionTexBlur;
    public ShaderInstanceBlur positionTexBlurHorizontal;
    public ShaderInstanceBlur positionTexBlurVertical;
    private static final HashMap<String, Boolean> lookupPlayersReceivedLatestGUIRender = new HashMap<>();

    public static ParticleEngine getParticleEngine() {
        return Minecraft.getInstance().particleEngine;
    }

    public void tickGame() {
        steadyTickCounter++;
        if (steadyTickCounter == Integer.MAX_VALUE) steadyTickCounter = 0;
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        if (Minecraft.getInstance().getConnection() == null) return;

        Iterator<Entry<UUID, PlayerStatus>> it = lookupPlayerToStatus.entrySet().iterator();
        while (it.hasNext()) {
            Entry<UUID, PlayerStatus> entry = it.next();
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(entry.getKey());
            PlayerStatus ps = entry.getValue();
            if (playerInfo == null) { ps.reset(); it.remove(); }
            if (level.getPlayerByUUID(entry.getKey()) == null && (ps.getParticle() != null || ps.getParticleIdle() != null)) {
                ps.resetParticles();
            }
        }

        if (lastLevel != level) {
            for (Entry<UUID, PlayerStatus> e : lookupPlayerToStatus.entrySet()) e.getValue().resetParticles();
            selfPlayerStatus.reset();
            selfPlayerStatusPrev.reset();
            selfPlayerStatus.getScreenData().setGameTicksSinceLastScreenSend(0L);
        }
        lastLevel = level;

        long gameTime = level.getGameTime();
        Screen screen = Minecraft.getInstance().screen;
        boolean guiBlacklisted = false;
        boolean validGui = !guiBlacklisted && screen != null
            && selfPlayerStatus.getPlayerGuiState() != PlayerStatus.PlayerGuiState.NONE;
        boolean stillActiveInGUI = selfPlayerStatus.getTicksSinceLastAction() < 100;
        boolean delayPassed = selfPlayerStatus.getScreenData().getGameTicksSinceLastScreenSend()
            + ServerSyncedConfig.DYNAMIC_GUI_TICK_SEND_RATE.get() < gameTime;
        boolean canRenderNewGUI = !ServerSyncedConfig.DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.get()
            || selfPlayerStatus.getScreenData().getLastScreen() != screen;

        if (stillActiveInGUI && delayPassed && validGui && canRenderNewGUI) {
            selfPlayerStatus.getScreenData().setGameTicksSinceLastScreenSend(gameTime);
            selfPlayerStatus.getScreenData().setNeedsNewRenderToPixelData(true);
            updateNearbyPlayerListAndCheckIfNewPlayerNear();
        }
        selfPlayerStatus.getScreenData().setLastScreen(screen);

        if (delayPassed && validGui
            && (ServerSyncedConfig.DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.get() || !stillActiveInGUI)
            && selfPlayerStatus.getScreenData().getTexturePixelData() != null) {
            if (updateNearbyPlayerListAndCheckIfNewPlayerNear()) {
                sendScreenRenderData(selfPlayerStatus);
            }
        }
        if (screen == null) selfPlayerStatus.getScreenData().setGameTicksSinceLastScreenSend(0L);
    }

    private boolean updateNearbyPlayerListAndCheckIfNewPlayerNear() {
        HashMap<String, Boolean> lookup = new HashMap<>();
        boolean newPlayer = false;
        Minecraft mc = Minecraft.getInstance();
        for (AbstractClientPlayer player : mc.level.players()) {
            if (player.position().distanceTo(new Vec3(mc.player.getX(), mc.player.getY(), mc.player.getZ()))
                <= ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get()) {
                lookup.put(player.getName().getString(), true);
                if (!lookupPlayersReceivedLatestGUIRender.containsKey(player.getName().getString())) newPlayer = true;
            }
        }
        lookupPlayersReceivedLatestGUIRender.clear();
        lookupPlayersReceivedLatestGUIRender.putAll(lookup);
        return newPlayer;
    }

    @Override
    public void tickPlayerClient(Player player) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.getUUID().equals(player.getUUID())) tickLocalPlayerClient(player);
        tickOtherPlayerClient(player);
        PlayerStatus status = getStatus(player.getUUID());
        status.tick();
        float adjRate = 0.1F;
        if (status.getTypingAmplifierSmooth() < status.getTypingAmplifier() - adjRate)
            status.setTypingAmplifierSmooth(status.getTypingAmplifierSmooth() + adjRate);
        else if (status.getTypingAmplifierSmooth() > status.getTypingAmplifier() + adjRate)
            status.setTypingAmplifierSmooth(status.getTypingAmplifierSmooth() - adjRate);
    }

    private void tickLocalPlayerClient(Player player) {
        Minecraft mc = Minecraft.getInstance();
        PlayerStatus local = getStatusLocal();
        PlayerStatus prevLocal = getStatusPrevLocal();
        if (mc.player != null && mc.player.getUUID() != null && local.getUuid() == null) {
            local.setUuid(mc.player.getUUID());
            prevLocal.setUuid(mc.player.getUUID());
        }
        prevLocal.setPlayerGuiState(local.getPlayerGuiState());

        if (!ConfigClient.SEND_ACTIVE_GUI.get() || local.isIdle()) sendGuiStatus(PlayerStatus.PlayerGuiState.NONE);
        else if (mc.screen instanceof ChatScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.CHAT_SCREEN);
        else if (mc.screen instanceof CraftingScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.CRAFTING);
        else if (mc.screen instanceof PauseScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.ESCAPE);
        else if (mc.screen instanceof BookEditScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.EDIT_BOOK);
        else if (mc.screen instanceof AbstractSignEditScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.EDIT_SIGN);
        else if (mc.screen instanceof ContainerScreen || mc.screen instanceof ShulkerBoxScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.CHEST);
        else if (mc.screen instanceof EnchantmentScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.ENCHANTING_TABLE);
        else if (mc.screen instanceof AnvilScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.ANVIL);
        else if (mc.screen instanceof BeaconScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.BEACON);
        else if (mc.screen instanceof BrewingStandScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.BREWING_STAND);
        else if (mc.screen instanceof DispenserScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.DISPENSER);
        else if (mc.screen instanceof AbstractFurnaceScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.FURNACE);
        else if (mc.screen instanceof GrindstoneScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.GRINDSTONE);
        else if (mc.screen instanceof HopperScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.HOPPER);
        else if (mc.screen instanceof HorseInventoryScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.HORSE);
        else if (mc.screen instanceof LoomScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.LOOM);
        else if (mc.screen instanceof MerchantScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.VILLAGER);
        else if (mc.screen instanceof AbstractCommandBlockEditScreen) sendGuiStatus(PlayerStatus.PlayerGuiState.COMMAND_BLOCK);
        else if (mc.screen != null && !(mc.screen instanceof DeathScreen)) sendGuiStatus(PlayerStatus.PlayerGuiState.MISC);
        else if (mc.screen == null) sendGuiStatus(PlayerStatus.PlayerGuiState.NONE);

        String chatText = "";
        if (mc.screen instanceof ChatScreen cs) chatText = "";
        else if (mc.screen instanceof BookEditScreen bes) chatText = "";
        else if (mc.screen instanceof AbstractSignEditScreen ses) chatText = "";
        else if (mc.screen instanceof AbstractCommandBlockEditScreen cbes) chatText = "";

        if (checkIfTyping(chatText, player)) sendChatStatus(PlayerStatus.PlayerChatState.CHAT_TYPING);
        else if (isGuiFocusedOnTextBox(mc.screen)) sendChatStatus(PlayerStatus.PlayerChatState.CHAT_FOCUSED);
        else sendChatStatus(PlayerStatus.PlayerChatState.NONE);

        if (ConfigClient.SEND_MOUSE_INFO.get() && mc.screen != null && mc.level.getGameTime() % armMouseTickRate == 0L) {
            PlayerStatus.PlayerGuiState guiState = local.getPlayerGuiState();
            if (PlayerStatus.PlayerGuiState.canPreventIdleInGui(guiState)) {
                Pair<Float, Float> pos = getMousePos();
                if (pos.getFirst() != local.getScreenPosPercentX() || pos.getSecond() != local.getScreenPosPercentY()) onAction();
                sendMouse(pos, local.isPressing());
            }
        }
        if (prevLocal.getTicksSinceLastAction() != local.getTicksSinceLastAction())
            prevLocal.setTicksSinceLastAction(local.getTicksSinceLastAction());

        if (ConfigClient.SEND_IDLE_STATE.get()) {
            local.setTicksSinceLastAction(local.getTicksSinceLastAction() + 1);
            if (local.getTicksSinceLastAction() > local.getTicksToMarkPlayerIdleSyncedForClient() && local.isIdle() != prevLocal.isIdle())
                sendIdle(local);
        } else local.setTicksSinceLastAction(0);

        if (!wasMousePressed && mousePressedCountdown > 0) {
            mousePressedCountdown--;
            if (mousePressedCountdown == 0) sendMouse(getMousePos(), false);
        }
        tickSyncing(player);
    }

    private boolean isGuiFocusedOnTextBox(Screen screen) {
        return screen instanceof ChatScreen || screen instanceof AbstractSignEditScreen
            || screen instanceof BookEditScreen || screen instanceof AbstractCommandBlockEditScreen;
    }

    private void tickSyncing(Player player) {
        if (steadyTickCounter % forcedSyncRate == 0) {
            PlayerStatus local = getStatusLocal();
            sendIdle(local);
            sendGuiStatus(local.getPlayerGuiState(), true);
            sendTyping(local);
        }
    }

    public void onMouse(boolean pressedAnything) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        PlayerStatus.PlayerGuiState guiState = getStatus(mc.player).getPlayerGuiState();
        if (ConfigClient.SEND_MOUSE_INFO.get() && PlayerStatus.PlayerGuiState.canPreventIdleInGui(guiState)) {
            if (pressedAnything) { mousePressedCountdown = 3; wasMousePressed = true; }
            else wasMousePressed = false;
            sendMouse(getMousePos(), mousePressedCountdown > 0);
        }
        if (mc.screen == null || (pressedAnything && PlayerStatus.PlayerGuiState.canPreventIdleInGui(guiState))) onAction();
    }

    public void onKey() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.player != null && (mc.screen == null || isGuiFocusedOnTextBox(mc.screen))) onAction();
    }

    public void onAction() {
        if (!ConfigClient.SEND_IDLE_STATE.get()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        PlayerStatus local = getStatusLocal();
        if (local.isIdle()) { local.setTicksSinceLastAction(0); sendIdle(local); }
        else local.setTicksSinceLastAction(0);
    }

    private Pair<Float, Float> getMousePos() {
        Minecraft mc = Minecraft.getInstance();
        double guiScale = mc.getWindow().getGuiScale();
        double guiScaleMax = 4.0;
        double xPercent = mc.mouseHandler.xpos() / mc.getWindow().getScreenWidth() - 0.5;
        double yPercent = mc.mouseHandler.ypos() / mc.getWindow().getScreenHeight() - 0.5;
        double emphasis = guiScaleMax / guiScale;
        double edgeLimit = 0.75;
        xPercent *= emphasis; yPercent *= emphasis;
        xPercent = Math.max(Math.min(xPercent, edgeLimit), -edgeLimit);
        yPercent = Math.max(Math.min(yPercent, edgeLimit), -edgeLimit);
        return Pair.of((float)xPercent, (float)yPercent);
    }

    private boolean checkIfTyping(String input, Player player) {
        PlayerStatus local = getStatusLocal();
        PlayerStatus prevLocal = getStatusPrevLocal();
        if (prevLocal.getPlayerGuiState() == PlayerStatus.PlayerGuiState.NONE && local.getPlayerGuiState() != PlayerStatus.PlayerGuiState.NONE) {
            local.setLastTypeString(input);
            local.setLastTypeStringForAmp(input);
            local.setTypingAmplifier(0.0F);
            local.setLastTypeDiff(0);
            local.setLastTypeTime(0L);
        }
        typeRatePollCounter++;
        if (input.length() > 0 && !input.startsWith("/")) {
            if (!input.equals(local.getLastTypeString())) {
                local.setLastTypeString(input);
                local.setLastTypeTime(player.level().getGameTime());
            }
            if (typeRatePollCounter >= 10) {
                typeRatePollCounter = 0;
                int prevLen = local.getLastTypeStringForAmp().length();
                if (!input.equals(local.getLastTypeStringForAmp())) {
                    local.setLastTypeStringForAmp(input);
                    local.setLastTypeTimeForAmp(player.level().getGameTime());
                    int newDiff = input.length() - prevLen;
                    float amp = Math.max(0.0F, Math.min(8.0F, (float)newDiff / 6.0F * 2.0F));
                    local.setTypingAmplifier(ConfigClient.SEND_TYPING_SPEED.get() ? amp : 1.0F);
                    sendTyping(local);
                } else if (ConfigClient.SEND_TYPING_SPEED.get()) local.setTypingAmplifier(0.0F);
            }
            return local.getLastTypeTime() + typingIdleTimeout >= player.level().getGameTime();
        } else {
            local.setLastTypeString(input);
            local.setLastTypeDiff(0);
            return false;
        }
    }

    public void onGuiRender(GuiGraphicsExtractor graphics) {
        if (Minecraft.getInstance().level == null || Minecraft.getInstance().player == null) return;
        if (!ConfigClient.SCREEN_TYPING_VISIBLE.get()) return;
        String typingText = getTypingPlayers();
        if (typingText.isEmpty()) return;
        int x = 2 + ConfigClient.SCREEN_TYPING_RELATIVE_POSITION_X.get();
        int y = Minecraft.getInstance().getWindow().getGuiScaledHeight() - 40 + ConfigClient.SCREEN_TYPING_RELATIVE_POSITION_Y.get();
        graphics.text(Minecraft.getInstance().font, typingText, x, y, 0xFFFFFF);
    }

    private String getTypingPlayers() {
        Minecraft mc = Minecraft.getInstance();
        StringBuilder sb = new StringBuilder();
        for (Entry<UUID, PlayerStatus> entry : lookupPlayerToStatus.entrySet()) {
            if (entry.getValue().getPlayerGuiState() == PlayerStatus.PlayerGuiState.CHAT_SCREEN
                && entry.getValue().getPlayerChatState() == PlayerStatus.PlayerChatState.CHAT_TYPING) {
                PlayerInfo info = mc.getConnection().getPlayerInfo(entry.getKey());
                if (info != null && info.getProfile() != null) sb.append(info.getProfile().name()).append(", ");
            }
        }
        int len = sb.length();
        StringBuilder anim = new StringBuilder();
        int animRate = 6;
        for (long t = mc.level.getGameTime() % (animRate * 4L); t > animRate; t -= animRate) anim.append(".");
        if (len > SCREEN_TYPING_CHAR_LIMIT)
            return ConfigClient.SCREEN_TYPING_MULTIPLE_PLAYERS_TEXT.get() + anim;
        if (sb.length() > 2)
            return sb.substring(0, sb.length() - 2) + ConfigClient.SCREEN_TYPING_TEXT.get() + anim;
        return sb.toString();
    }

    private boolean shouldAnimate(Player player) {
        Minecraft mc = Minecraft.getInstance();
        return player != mc.player || !mc.options.getCameraType().isFirstPerson();
    }

    private void tickOtherPlayerClient(Player player) {
        PlayerStatus ps = getStatus(player);
        PlayerStatus psPrev = getStatusPrev(player);
        long stableTime = steadyTickCounter;
        float sin = (float)Math.sin(stableTime / 30.0F % 360.0F);
        float cos = (float)Math.cos(stableTime / 30.0F % 360.0F);
        float idleY = 2.6F + cos * 0.03F;
        boolean idleChanged = ps.isIdle() != psPrev.isIdle() || ps.getParticleIdle() == null;
        boolean statusChanged = ps.getPlayerGuiState() != psPrev.getPlayerGuiState() || ps.getParticle() == null;
        boolean chatChanged = ps.getPlayerChatState() != psPrev.getPlayerChatState()
            && ps.getPlayerGuiState() == PlayerStatus.PlayerGuiState.CHAT_SCREEN;
        boolean isOwnPlayer = (getStatusLocal().getUuid() != null && ps.getUuid() == getStatusLocal().getUuid());
        boolean hideOwn = (!ConfigClient.SHOW_GUIS_FOR_YOUR_OWN_PLAYER_IN_3RD_PERSON.get()
            || Minecraft.getInstance().options.getCameraType().isFirstPerson()) && isOwnPlayer;
        if (hideOwn) { statusChanged = false; chatChanged = false; }

        if ((idleChanged || !ps.isIdle()) && ps.getParticleIdle() != null) { ps.getParticleIdle().remove(); ps.setParticleIdle(null); }
        if ((statusChanged || chatChanged || ps.getPlayerGuiState() == PlayerStatus.PlayerGuiState.NONE) && ps.getParticle() != null) { ps.getParticle().remove(); ps.setParticle(null); }
        if (ps.getParticle() != null && !ps.getParticle().isAlive()) { ps.getParticle().remove(); ps.setParticle(null); }
        if (ps.getParticleIdle() != null && !ps.getParticleIdle().isAlive()) { ps.getParticleIdle().remove(); ps.setParticleIdle(null); }

        double quadSize = 0.3 + Math.sin(stableTime / 10.0F % 360.0F) * 0.01;
        if (shouldAnimate(player) && !player.isInvisible()) {
            if (idleChanged && ConfigClient.SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.get()
                && ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.get() && ps.isIdle()) {
                ParticleRotating p = new ParticleStatic((ClientLevel)player.level(), player.position().x, player.position().y + idleY, player.position().z, ModParticles.idle.getSprite());
                if (p != null) { ps.setParticleIdle(p); getParticleEngine().add(p); p.setQuadSize((float)quadSize); }
            }
            if (statusChanged || chatChanged) {
                ParticleRotating particle = null;
                Vec3 pos = getParticlePosition(player);
                boolean newRender = RenderHelper.useDynamicGUISystem() && !ps.isPlayerGuiDontSendDetailedGUIInfo();
                boolean isChatScreen = ps.getPlayerGuiState() == PlayerStatus.PlayerGuiState.CHAT_SCREEN;
                boolean chatDynamicScreenReady = isChatScreen && newRender && ps.getScreenData().getImage() != null
                    && ConfigClient.SHOW_PLAYER_ACTIVE_CHAT_GUI.get() && ServerSyncedConfig.SHOW_PLAYER_ACTIVE_CHAT_GUI.get();

                if (ConfigClient.SHOW_PLAYER_ACTIVE_CHAT_GUI.get() && ServerSyncedConfig.SHOW_PLAYER_ACTIVE_CHAT_GUI.get()
                    && PlayerStatus.PlayerGuiState.isTypingGui(getStatus(player).getPlayerGuiState())
                    && !chatDynamicScreenReady) {
                    if (getStatus(player).getPlayerChatState() == PlayerStatus.PlayerChatState.CHAT_FOCUSED) {
                        particle = new ParticleAnimated((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.chat_idle.getSpriteSet());
                    } else if (getStatus(player).getPlayerChatState() == PlayerStatus.PlayerChatState.CHAT_TYPING) {
                        particle = new ParticleAnimated((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.chat_typing.getSpriteSet());
                    }
                }
                if (ConfigClient.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get() && ServerSyncedConfig.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get()) {
                    TextureAtlasSprite sprite = null;
                    float brightness = 0.7F;
                    int subX = 0, subY = 0;
                    boolean dynamicScreenReady = !isChatScreen && newRender && ps.getPlayerGuiState() != PlayerStatus.PlayerGuiState.NONE
                        && ps.getScreenData().getImage() != null;
                    if (dynamicScreenReady) {
                        // Rendered separately as a world-space billboard by DynamicScreenRenderer, not a Particle.
                    } else {
                        switch (ps.getPlayerGuiState()) {
                            case INVENTORY: particle = new ParticleStaticLoD((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.inventory.getSpriteSet()); break;
                            case CRAFTING: particle = new ParticleStaticLoD((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.crafting.getSpriteSet()); break;
                            case ESCAPE: particle = new ParticleStaticLoD((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.escape.getSpriteSet()); break;
                            case CHEST: particle = new ParticleStaticLoD((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.chest.getSpriteSet()); break;
                            case EDIT_SIGN: particle = new ParticleStatic((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.sign.getSprite(), 0.7F); break;
                            case EDIT_BOOK: break;
                            case ENCHANTING_TABLE: sprite = ModParticles.enchanting_table.getSprite(); subX = 176; subY = 166; break;
                            case ANVIL: sprite = ModParticles.anvil.getSprite(); subX = 176; subY = 166; break;
                            case BEACON: sprite = ModParticles.beacon.getSprite(); subX = 231; subY = 219; break;
                            case BREWING_STAND: sprite = ModParticles.brewing_stand.getSprite(); subX = 176; subY = 166; break;
                            case DISPENSER: sprite = ModParticles.dispenser.getSprite(); subX = 176; subY = 166; break;
                            case FURNACE: sprite = ModParticles.furnace.getSprite(); subX = 176; subY = 166; break;
                            case GRINDSTONE: sprite = ModParticles.grindstone.getSprite(); subX = 176; subY = 166; break;
                            case HOPPER: sprite = ModParticles.hopper.getSprite(); subX = 176; subY = 134; break;
                            case HORSE: sprite = ModParticles.horse.getSprite(); subX = 176; subY = 166; break;
                            case LOOM: sprite = ModParticles.loom.getSprite(); subX = 176; subY = 166; break;
                            case VILLAGER: sprite = ModParticles.villager.getSprite(); subX = 277; subY = 167; break;
                            case COMMAND_BLOCK: sprite = ModParticles.command_block.getSprite(); subX = 308; subY = 213; break;
                            case MISC: if (ConfigClient.SHOW_PLAYER_ACTIVE_GUI_IF_NOT_EXACT_MATCH.get()) particle = new ParticleStaticLoD((ClientLevel)player.level(), pos.x, pos.y, pos.z, ModParticles.chest.getSpriteSet()); break;
                        }
                        if (sprite != null) particle = new ParticleStaticPartial((ClientLevel)player.level(), pos.x, pos.y, pos.z, sprite, brightness, subX, subY);
                    }
                }
                if (particle != null) { ps.setParticle(particle); getParticleEngine().add(particle); }
            }
        }

        if (ps.getParticleIdle() != null && ps.getParticleIdle() instanceof ParticleStatic sp) {
            if (sp.isAlive()) {
                sp.keepAlive();
                sp.setPos(player.position().x, player.position().y + idleY, player.position().z);
                sp.setParticleSpeed(0, 0, 0);
                sp.rotationYaw = -player.yBodyRot + 180.0F; sp.prevRotationYaw = sp.rotationYaw;
                sp.rotationRoll = cos * 5.0F; sp.prevRotationRoll = sp.rotationRoll;
                sp.setQuadSize(0.15F + sin * 0.03F); sp.setAlpha(0.5F);
            }
        }
        if (ps.getParticle() != null && ps.getParticle() instanceof ParticleRotating pr) {
            if (pr.isAlive()) {
                pr.keepAlive();
                Vec3 ppos = getParticlePosition(player);
                pr.setPos(ppos.x, ppos.y, ppos.z); pr.setParticleSpeed(0, 0, 0);
                if (!(pr instanceof ParticleAnimated)) {
                    pr.setQuadSize((float)quadSize);
                    if (Minecraft.getInstance().getCameraEntity() != null) {
                        double dist = Minecraft.getInstance().getCameraEntity().distanceTo(player);
                        double capped = Math.max(3, Math.min(10, dist));
                        double capped2 = Math.max(3, Math.min(6, dist));
                        float alpha = (float)Math.max(0.35, 1.0 - capped / 10.0) + 0.15F;
                        float bright = (float)Math.max(0.55, 1.0 - capped / 10.0) + 0.15F;
                        float fade = (float)Math.max(0, 1.0 - capped2 / 6.0);
                        quadSize = 0.3 + Math.sin(stableTime / 10.0F % 360.0F) * 0.01 * fade;
                        pr.setAlpha(alpha); pr.setBrightness(bright); pr.setQuadSize((float)quadSize);
                        if (pr instanceof ParticleStaticLoD l) l.setParticleFromDistanceToCamera((float)dist);
                    } else pr.setAlpha(0.5F);
                }
                pr.rotationYaw = -player.yBodyRot; pr.prevRotationYaw = pr.rotationYaw;
                pr.rotationPitch = 20.0F; pr.prevRotationPitch = pr.rotationPitch;
            }
        }
        psPrev.setPlayerGuiState(ps.getPlayerGuiState());
        psPrev.setPlayerChatState(ps.getPlayerChatState());
        if (psPrev.getTicksSinceLastAction() != ps.getTicksSinceLastAction()) psPrev.setTicksSinceLastAction(ps.getTicksSinceLastAction());
    }

    public void renderPingIconHook(GuiGraphicsExtractor graphics, int slotWidth, int xo, int yo, PlayerInfo info) {
        if (!ConfigClient.SHOW_IDLE_STATES_IN_PLAYER_LIST.get() || info == null || info.getProfile() == null) return;
        UUID uuid = info.getProfile().id();
        if (uuid == null) return;
        PlayerStatus ps = lookupPlayerToStatus.get(uuid);
        if (ps != null && ps.isIdle()) {
            graphics.text(Minecraft.getInstance().font, "ZZZ", xo + slotWidth - 22, yo, 0xAAAAAA);
        }
    }

    public void onSetupAnim(HumanoidModel<?> model, HumanoidRenderState state) {
        java.util.UUID uuid = com.skd.playeractivityview.render.EntityRenderStateTracker.get(state);
        if (uuid == null) return;
        PlayerStatus ps = lookupPlayerToStatus.get(uuid);
        if (ps == null) return;
        PlayerStatus.PlayerGuiState guiState = ps.getPlayerGuiState();
        PlayerStatus.PlayerChatState chatState = ps.getPlayerChatState();
        boolean isIdle = ps.isIdle();
        boolean pointing = PlayerStatus.PlayerGuiState.isPointingGui(guiState) && ConfigClient.SHOW_PLAYER_ANIMATION_GUI.get();
        boolean typing = chatState != PlayerStatus.PlayerChatState.NONE
            && PlayerStatus.PlayerGuiState.isTypingGui(guiState)
            && ConfigClient.SHOW_PLAYER_ANIMATION_TYPING.get();

        if (pointing) {
            float xPercent = ps.getScreenPosPercentX();
            float yPercent = ps.getScreenPosPercentY();
            model.rightArm.xRot = (float)(-Math.toRadians(67.5) - yPercent * 0.5);
            model.rightArm.yRot = (float)(-Math.toRadians(15) + xPercent * 0.5);
            model.leftArm.xRot = (float)(-Math.toRadians(70));
            model.leftArm.yRot = (float)Math.toRadians(25);
            model.head.xRot = (float)(Math.toRadians(15) + yPercent * 0.3);
            model.head.yRot = (float)(xPercent * 0.5);
        } else if (typing) {
            long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
            double rightWave = Math.sin(gameTime * 0.3) * Math.toRadians(12);
            double leftWave = Math.sin(gameTime * 0.3 + Math.PI) * Math.toRadians(12);
            model.rightArm.xRot = (float)(-Math.toRadians(67.5) + rightWave);
            model.leftArm.xRot = (float)(-Math.toRadians(67.5) + leftWave);
            model.rightArm.yRot = (float)(-Math.toRadians(20));
            model.leftArm.yRot = (float)Math.toRadians(20);
            model.head.xRot = (float)Math.toRadians(15);
        } else if (isIdle && ConfigClient.SHOW_PLAYER_ANIMATION_IDLE.get()) {
            model.head.xRot = (float)Math.toRadians(70);
        }
    }

    public void setPoseTarget(UUID uuid, boolean becauseMousePress) {
        PlayerStatus ps = getStatus(uuid);
        ps.getLerpPrev().rightArm = ps.getLerpTarget().rightArm.copyPartialLerp(ps, ps.getLerpPrev().rightArm, ps.lastPartialTick);
        ps.getLerpPrev().leftArm = ps.getLerpTarget().leftArm.copyPartialLerp(ps, ps.getLerpPrev().leftArm, ps.lastPartialTick);
        ps.getLerpPrev().head = ps.getLerpTarget().head.copyPartialLerp(ps, ps.getLerpPrev().head, ps.lastPartialTick);
        if (Float.isNaN(ps.getLerpPrev().rightArm.yRot)) ps.getLerpPrev().rightArm.yRot = 0;
        if (Float.isNaN(ps.getLerpPrev().rightArm.xRot)) ps.getLerpPrev().rightArm.xRot = 0;

        boolean pointing = PlayerStatus.PlayerGuiState.isPointingGui(ps.getPlayerGuiState());
        boolean typing = ps.getPlayerChatState() != PlayerStatus.PlayerChatState.NONE
            && PlayerStatus.PlayerGuiState.isTypingGui(ps.getPlayerGuiState());
        boolean idle = ps.isIdle();
        if (!ConfigClient.SHOW_PLAYER_ANIMATION_GUI.get() || !ServerSyncedConfig.SHOW_PLAYER_ANIMATION_GUI.get()) pointing = false;
        if (!ConfigClient.SHOW_PLAYER_ANIMATION_TYPING.get() || !ServerSyncedConfig.SHOW_PLAYER_ANIMATION_TYPING.get()) typing = false;
        if (!ConfigClient.SHOW_PLAYER_ANIMATION_IDLE.get() || !ServerSyncedConfig.SHOW_PLAYER_ANIMATION_IDLE.get()) idle = false;

        ps.setNewLerp(becauseMousePress ? armMouseTickRate * 0.5F : armMouseTickRate * 1.0F);
        if (pointing || typing) {
            ps.getLerpTarget().head.xRot = (float)Math.toRadians(15);
            ps.getLerpTarget().head.yRot = 0;
        }
        if (pointing) {
            double xPercent = ps.getScreenPosPercentX();
            double yPercent = ps.getScreenPosPercentY();
            double x = Math.toRadians(90) - Math.toRadians(22.5) - yPercent;
            double y = -Math.toRadians(15) + xPercent;
            double xHead = Math.toRadians(22.5) + yPercent;
            ps.getLerpTarget().rightArm.yRot = (float)y;
            ps.getLerpTarget().rightArm.xRot = (float)(-x);
            ps.getLerpTarget().head.yRot = (float)xPercent * 0.5F;
            ps.getLerpTarget().head.xRot = (float)xHead * 0.5F;
            if (ps.isPressing()) {
                Vec3 vec = calculateViewVector((float)Math.toDegrees(y), (float)Math.toDegrees(x));
                float press = 1.0F;
                ps.getLerpTarget().rightArm.x = (float)(press * vec.y);
                ps.getLerpTarget().rightArm.y = (float)(press * vec.z);
                ps.getLerpTarget().rightArm.z = (float)(press * vec.x);
            } else {
                ps.getLerpTarget().rightArm.x = 0; ps.getLerpTarget().rightArm.z = 0; ps.getLerpTarget().rightArm.y = 0;
            }
            ps.getLerpTarget().leftArm.xRot = (float)(-Math.toRadians(70));
            ps.getLerpTarget().leftArm.yRot = (float)Math.toRadians(25);
        } else if (typing) {
            long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
            double rightWave = Math.sin(gameTime * 0.3) * Math.toRadians(12);
            double leftWave = Math.sin(gameTime * 0.3 + Math.PI) * Math.toRadians(12);
            double x = Math.toRadians(90) - Math.toRadians(22.5);
            ps.getLerpTarget().rightArm.xRot = (float)(-x + rightWave);
            ps.getLerpTarget().leftArm.xRot = (float)(-x + leftWave);
            ps.getLerpTarget().rightArm.yRot = (float)(-Math.toRadians(20));
            ps.getLerpTarget().leftArm.yRot = (float)Math.toRadians(20);
        }
        if (!pointing && !typing && !idle) {
            ps.setLerpTarget(new Lerpables());
            ps.getLerpTarget().head.xRot = ps.xRotHeadBeforeOverriding;
            ps.getLerpTarget().head.yRot = ps.yRotHeadBeforeOverriding;
        }
        if (idle) {
            ps.getLerpTarget().head.xRot = (float)Math.toRadians(70);
            ps.setNewLerp(40.0F);
        }
        if (getStatusPrev(uuid).getPlayerGuiState() == PlayerStatus.PlayerGuiState.NONE
            && getStatus(uuid).getPlayerGuiState() != PlayerStatus.PlayerGuiState.NONE) {
            ps.getLerpPrev().head.xRot = ps.xRotHeadBeforeOverriding;
            ps.getLerpPrev().head.yRot = ps.yRotHeadBeforeOverriding;
        }
    }

    public Vec3 getParticlePosition(Player player) {
        Vec3 pos = player.position();
        Vec3 lookVec = getBodyAngle(player).scale(0.85);
        return new Vec3(pos.x + lookVec.x, pos.y + 1.2, pos.z + lookVec.z);
    }

    @Override
    public PlayerStatus getStatusLocal() { return selfPlayerStatus; }

    public PlayerStatus getStatusPrevLocal() { return selfPlayerStatusPrev; }

    public PlayerStatus getStatusPrev(Player player) { return getStatusPrev(player.getUUID()); }

    public PlayerStatus getStatusPrev(UUID uuid) { return getStatusPrev(uuid, false); }

    public PlayerStatus getStatusPrev(UUID uuid, boolean local) {
        if (local) return getStatusPrevLocal();
        checkPrev(uuid);
        return lookupPlayerToStatusPrev.get(uuid);
    }

    private void checkPrev(UUID uuid) {
        if (!lookupPlayerToStatusPrev.containsKey(uuid))
            lookupPlayerToStatusPrev.put(uuid, new PlayerStatus(PlayerStatus.PlayerGuiState.NONE, uuid));
    }

    public void sendGuiStatus(PlayerStatus.PlayerGuiState status) { sendGuiStatus(status, false); }

    public void sendGuiStatus(PlayerStatus.PlayerGuiState status, boolean force) {
        if (getStatusLocal().getPlayerGuiState() != status || force) {
            CompoundTag data = new CompoundTag();
            data.putInt(PlayerActivityNetworking.NBTDataPlayerGuiStatus, status.ordinal());
            data.putBoolean(PlayerActivityNetworking.NBTDataPlayerGuiDontSendDetailedGUIInfo, ConfigClient.DONT_SEND_DETAILED_GUI_INFO.get());
            data.putBoolean(PlayerActivityNetworking.NBTDataPlayerGuiDontSendItemInfo, ConfigClient.DONT_SEND_ITEM_INFO.get());
            PlayerActivityNetworking.instance().clientSendToServer(data);
        }
        getStatusLocal().setPlayerGuiState(status);
    }

    public void sendChatStatus(PlayerStatus.PlayerChatState status) { sendChatStatus(status, false); }

    public void sendChatStatus(PlayerStatus.PlayerChatState status, boolean force) {
        if (getStatusLocal().getPlayerChatState() != status || force) {
            CompoundTag data = new CompoundTag();
            data.putInt(PlayerActivityNetworking.NBTDataPlayerChatStatus, status.ordinal());
            PlayerActivityNetworking.instance().clientSendToServer(data);
        }
        getStatusLocal().setPlayerChatState(status);
    }

    public void sendMouse(Pair<Float, Float> pos, boolean pressed) {
        Minecraft mc = Minecraft.getInstance();
        float x = pos.getFirst();
        float y = pos.getSecond();
        if ((mc.level.getNearestPlayer(mc.player.getX(), mc.player.getY(), mc.player.getZ(),
            ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get(), e -> e != mc.player) != null)
            && (getStatusLocal().getScreenPosPercentX() != x || getStatusLocal().getScreenPosPercentY() != y || getStatusLocal().isPressing() != pressed)) {
            CompoundTag data = new CompoundTag();
            data.putFloat(PlayerActivityNetworking.NBTDataPlayerMouseX, x);
            data.putFloat(PlayerActivityNetworking.NBTDataPlayerMouseY, y);
            data.putBoolean(PlayerActivityNetworking.NBTDataPlayerMousePressed, pressed);
            PlayerActivityNetworking.instance().clientSendToServer(data);
        }
        getStatusLocal().setScreenPosPercentX(x);
        getStatusLocal().setScreenPosPercentY(y);
        getStatusLocal().setPressing(pressed);
    }

    public void sendScreenRenderData(PlayerStatus status) {
        if (status.getScreenData().getTexturePixelData() == null) return;
        CompoundTag data = new CompoundTag();
        int limit = 31000;
        int size = status.getScreenData().getTexturePixelData().remaining();
        byte[] inputBytes = new byte[size];
        status.getScreenData().getTexturePixelData().get(inputBytes);
        if (size < limit) {
            data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataSize, status.getScreenData().getUncompressedSize());
            data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenWidth, ScreenParticleRenderer.getInstance().widthScaledDown);
            data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenHeight, ScreenParticleRenderer.getInstance().heightScaledDown);
            data.putByteArray(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelData, inputBytes);
            data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataPacketCount, 1);
            data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataPacketIndex, 1);
            LOGGER.info("[send] single-packet compressedBytes={} uncompressedSize={} dims={}x{}",
                size, status.getScreenData().getUncompressedSize(), ScreenParticleRenderer.getInstance().widthScaledDown, ScreenParticleRenderer.getInstance().heightScaledDown);
            PlayerActivityNetworking.instance().clientSendToServer(data);
        } else {
            int count = Mth.ceil((float)size / limit);
            int idx = 0;
            for (int i = 0; i < count; i++) {
                byte[] part;
                if (idx + limit < size) part = Arrays.copyOfRange(inputBytes, idx, idx + limit);
                else part = Arrays.copyOfRange(inputBytes, idx, size);
                idx += part.length;
                data = new CompoundTag();
                data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataSize, status.getScreenData().getUncompressedSize());
                data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenWidth, ScreenParticleRenderer.getInstance().widthScaledDown);
                data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenHeight, ScreenParticleRenderer.getInstance().heightScaledDown);
                data.putByteArray(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelData, part);
                data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataPacketCount, count);
                data.putInt(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataPacketIndex, i);
                PlayerActivityNetworking.instance().clientSendToServer(data);
            }
        }
        status.getScreenData().getTexturePixelData().flip();
    }

    public void sendTyping(PlayerStatus status) {
        CompoundTag data = new CompoundTag();
        data.putFloat(PlayerActivityNetworking.NBTDataPlayerTypingAmp, status.getTypingAmplifier());
        PlayerActivityNetworking.instance().clientSendToServer(data);
    }

    public void sendIdle(PlayerStatus status) {
        CompoundTag data = new CompoundTag();
        data.putInt(PlayerActivityNetworking.NBTDataPlayerIdleTicks, status.getTicksSinceLastAction());
        PlayerActivityNetworking.instance().clientSendToServer(data);
    }

    public void receiveAny(UUID uuid, CompoundTag data) {
        PlayerStatus status = getStatus(uuid);
        PlayerStatus statusPrev = getStatusPrev(uuid);
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerTypingAmp))
            status.setTypingAmplifier(data.getFloatOr(PlayerActivityNetworking.NBTDataPlayerTypingAmp, 0f));
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerMouseX)) {
            float x = data.getFloatOr(PlayerActivityNetworking.NBTDataPlayerMouseX, 0f);
            float y = data.getFloatOr(PlayerActivityNetworking.NBTDataPlayerMouseY, 0f);
            boolean pressed = data.getBooleanOr(PlayerActivityNetworking.NBTDataPlayerMousePressed, false);
            boolean diffPress = status.isPressing() != pressed;
            setMouse(uuid, x, y, pressed);
            setPoseTarget(uuid, diffPress);
            if (pressed && diffPress) {
                Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
                if (player != null && ConfigClient.PLAY_MOUSE_CLICK_SOUNDS.get() && ServerSyncedConfig.PLAY_MOUSE_CLICK_SOUNDS.get()
                    && player != Minecraft.getInstance().player) {
                    player.level().playLocalSound(player.getOnPos(), SoundEvents.CHICKEN_EGG, SoundSource.PLAYERS, 0.05F, 0.1F, false);
                }
            }
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerGuiStatus)) {
            PlayerStatus.PlayerGuiState guiState = PlayerStatus.PlayerGuiState.get(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerGuiStatus, 0));
            status.setPlayerGuiState(guiState);
            if (data.contains(PlayerActivityNetworking.NBTDataPlayerGuiDontSendDetailedGUIInfo))
                status.setPlayerGuiDontSendDetailedGUIInfo(data.getBooleanOr(PlayerActivityNetworking.NBTDataPlayerGuiDontSendDetailedGUIInfo, false));
            if (data.contains(PlayerActivityNetworking.NBTDataPlayerGuiDontSendItemInfo))
                status.setPlayerGuiDontSendItemInfo(data.getBooleanOr(PlayerActivityNetworking.NBTDataPlayerGuiDontSendItemInfo, false));
            if (status.getPlayerGuiState() != statusPrev.getPlayerGuiState()) {
                if (statusPrev.getPlayerGuiState() == PlayerStatus.PlayerGuiState.NONE) status.setLerpTarget(new Lerpables());
                setPoseTarget(uuid, false);
                Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
                if (player != null && ConfigClient.PLAY_SCREEN_OPEN_SOUNDS.get() && ServerSyncedConfig.PLAY_SCREEN_OPEN_SOUNDS.get()
                    && player != Minecraft.getInstance().player) {
                    PlayerStatus.PlayerGuiState prev = statusPrev.getPlayerGuiState();
                    if (PlayerStatus.PlayerGuiState.isSoundMakerGui(guiState) || PlayerStatus.PlayerGuiState.isSoundMakerGui(prev)
                        || guiState == PlayerStatus.PlayerGuiState.INVENTORY || guiState == PlayerStatus.PlayerGuiState.CRAFTING
                        || guiState == PlayerStatus.PlayerGuiState.MISC || prev == PlayerStatus.PlayerGuiState.INVENTORY
                        || prev == PlayerStatus.PlayerGuiState.CRAFTING || prev == PlayerStatus.PlayerGuiState.MISC) {
                        player.level().playLocalSound(player.getOnPos(), SoundEvents.ARMOR_EQUIP_CHAIN.value(), SoundSource.PLAYERS, 0.9F, 1.0F, false);
                    }
                }
            }
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerChatStatus)) {
            PlayerStatus.PlayerChatState state = PlayerStatus.PlayerChatState.get(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerChatStatus, 0));
            status.setPlayerChatState(state);
            if (status.getPlayerChatState() != statusPrev.getPlayerChatState()) {
                if (statusPrev.getPlayerChatState() == PlayerStatus.PlayerChatState.NONE) status.setLerpTarget(new Lerpables());
                if (status.getPlayerChatState() == PlayerStatus.PlayerChatState.CHAT_FOCUSED) {
                    status.setTypingAmplifier(1.0F);
                    status.setTypingAmplifierSmooth(1.0F);
                }
                setPoseTarget(uuid, false);
            }
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerIdleTicks)) {
            status.setTicksSinceLastAction(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerIdleTicks, 0));
            status.setTicksToMarkPlayerIdleSyncedForClient(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerTicksToGoIdle, 0));
            statusPrev.setTicksToMarkPlayerIdleSyncedForClient(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerTicksToGoIdle, 0));
            getStatusLocal().setTicksToMarkPlayerIdleSyncedForClient(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerTicksToGoIdle, 0));
            getStatusPrevLocal().setTicksToMarkPlayerIdleSyncedForClient(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerTicksToGoIdle, 0));
            if (statusPrev.isIdle() != status.isIdle()) setPoseTarget(uuid, false);
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelData)) {
            byte[] pixelData = data.getByteArray(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelData).orElse(new byte[0]);
            int decompSize = data.getIntOr(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataSize, 0);
            int packetCount = data.getIntOr(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataPacketCount, 0);
            int packetIndex = data.getIntOr(PlayerActivityNetworking.NBTDataPlayerScreenCompressedPixelDataPacketIndex, 0);
            status.getScreenData().setWidth(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerScreenWidth, 0));
            status.getScreenData().setHeight(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerScreenHeight, 0));
            LOGGER.info("[recv] uuid={} decompSize={} packetCount={} packetIndex={} chunkBytes={} dims={}x{}",
                uuid, decompSize, packetCount, packetIndex, pixelData.length, status.getScreenData().getWidth(), status.getScreenData().getHeight());
            long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0;
            if (packetCount > 1) {
                if (packetIndex == 0) {
                    status.getScreenData().setGameTicksSinceFirstPacket(gameTime);
                    status.getScreenData().setLastIndexReceived(0);
                    status.getScreenData().setTexturePixelDataPartial(pixelData);
                } else if (packetIndex == status.getScreenData().getLastIndexReceived() + 1) {
                    status.getScreenData().setLastIndexReceived(packetIndex);
                    if (gameTime <= status.getScreenData().getGameTicksSinceFirstPacket() + 10 && pixelData.length > 0) {
                        byte[] existing = status.getScreenData().getTexturePixelDataPartial();
                        byte[] combined = new byte[existing.length + pixelData.length];
                        System.arraycopy(existing, 0, combined, 0, existing.length);
                        System.arraycopy(pixelData, 0, combined, existing.length, pixelData.length);
                        status.getScreenData().setTexturePixelDataPartial(combined);
                        if (packetIndex == packetCount - 1 && status.getScreenData().getTexturePixelDataPartial() != null) {
                            try {
                                status.getScreenData().setTexturePixelData(RenderHelper.decompress(status.getScreenData(), ByteBuffer.wrap(status.getScreenData().getTexturePixelDataPartial()), decompSize));
                                status.getScreenData().markNeedsNewRenderFromPixelData(true);
                                status.getScreenData().getIsBufferReady().set(true);
                                RenderHelper.updateScreenTexture(status.getScreenData(), status.getScreenData().getTexturePixelData(),
                                    status.getScreenData().getWidth(), status.getScreenData().getHeight(), uuid);
                            } catch (Exception e) { e.printStackTrace(); }
                        }
                    }
                }
            } else {
                try {
                    status.getScreenData().setTexturePixelData(RenderHelper.decompress(status.getScreenData(), ByteBuffer.wrap(pixelData), decompSize));
                    status.getScreenData().markNeedsNewRenderFromPixelData(true);
                    status.getScreenData().getIsBufferReady().set(true);
                    RenderHelper.updateScreenTexture(status.getScreenData(), status.getScreenData().getTexturePixelData(),
                        status.getScreenData().getWidth(), status.getScreenData().getHeight(), uuid);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public void receiveItemMove(CompoundTag data) {
        if (data.contains(PlayerActivityNetworking.NBTDataItemTransferItemStack)) {
            java.util.Optional<ItemStack> parsed = ItemStack.CODEC.parse(Minecraft.getInstance().level.registryAccess().createSerializationContext(net.minecraft.nbt.NbtOps.INSTANCE), data.getCompound(PlayerActivityNetworking.NBTDataItemTransferItemStack).orElse(new net.minecraft.nbt.CompoundTag())).result();
            ItemStack stack = parsed.orElse(ItemStack.EMPTY);
            ParticleItem pi = new ParticleItem(Minecraft.getInstance().level, 1.0F, stack, Minecraft.getInstance().renderBuffers(),
                Minecraft.getInstance().getEntityRenderDispatcher(),
                data.getFloatOr(PlayerActivityNetworking.NBTDataItemTransferFromX, 0f), data.getFloatOr(PlayerActivityNetworking.NBTDataItemTransferFromY, 0f), data.getFloatOr(PlayerActivityNetworking.NBTDataItemTransferFromZ, 0f),
                data.getFloatOr(PlayerActivityNetworking.NBTDataItemTransferToX, 0f), data.getFloatOr(PlayerActivityNetworking.NBTDataItemTransferToY, 0f), data.getFloatOr(PlayerActivityNetworking.NBTDataItemTransferToZ, 0f));
            getParticleEngine().add(pi);
        }
    }

    public void receiveServerConfig(CompoundTag nbt) {
        ServerConfigSyncHelper.getInstance().updateSyncableConfigOnClient(nbt);
        ScreenParticleRenderer.getInstance().resizeScaledDown(ScreenParticleRenderer.getInstance().width, ScreenParticleRenderer.getInstance().height);
    }

    public void renderScreenCapture() {
    }

    public void onExtractBackground(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        PlayerStatus local = getStatusLocal();
        if (local.isIdle() && mc.screen != null && !(mc.screen instanceof ChatScreen)) {
            ci.cancel();
        }
    }
}
