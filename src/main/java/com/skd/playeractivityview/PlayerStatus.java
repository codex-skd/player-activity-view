package com.skd.playeractivityview;

import com.skd.playeractivityview.client.screen.ScreenData;
import com.skd.playeractivityview.math.Lerpables;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class PlayerStatus {
    public enum PlayerGuiState {
        NONE, CHAT_SCREEN, INVENTORY, CRAFTING, ESCAPE, EDIT_SIGN, EDIT_BOOK,
        CHEST, ENCHANTING_TABLE, ANVIL, BEACON, BREWING_STAND, DISPENSER,
        FURNACE, GRINDSTONE, HOPPER, HORSE, LOOM, VILLAGER, COMMAND_BLOCK, MISC;

        private static final Map<Integer, PlayerGuiState> lookup = new HashMap<>();
        private static final List<PlayerGuiState> listPointingGuis = new ArrayList<>();
        private static final List<PlayerGuiState> listTypingGuis = new ArrayList<>();
        private static final List<PlayerGuiState> listSoundMakerGuis = new ArrayList<>();

        public static boolean isPointingGui(PlayerGuiState state) { return listPointingGuis.contains(state); }
        public static boolean isTypingGui(PlayerGuiState state) { return listTypingGuis.contains(state); }
        public static boolean canPreventIdleInGui(PlayerGuiState state) { return listPointingGuis.contains(state); }
        public static boolean isSoundMakerGui(PlayerGuiState state) { return listSoundMakerGuis.contains(state); }
        public static PlayerGuiState get(int intValue) { return lookup.get(intValue); }

        static {
            for (PlayerGuiState e : EnumSet.allOf(PlayerGuiState.class)) {
                lookup.put(e.ordinal(), e);
                listPointingGuis.add(e);
                listSoundMakerGuis.add(e);
            }
            listPointingGuis.remove(NONE);
            listPointingGuis.remove(CHAT_SCREEN);
            listPointingGuis.remove(EDIT_BOOK);
            listPointingGuis.remove(EDIT_SIGN);
            listPointingGuis.remove(COMMAND_BLOCK);
            listTypingGuis.add(CHAT_SCREEN);
            listTypingGuis.add(EDIT_BOOK);
            listTypingGuis.add(EDIT_SIGN);
            listTypingGuis.add(COMMAND_BLOCK);
            listSoundMakerGuis.remove(NONE);
            listSoundMakerGuis.remove(CHAT_SCREEN);
            listSoundMakerGuis.remove(CHEST);
        }
    }

    public enum PlayerChatState {
        NONE, CHAT_FOCUSED, CHAT_TYPING;
        private static final Map<Integer, PlayerChatState> lookup = new HashMap<>();
        public static PlayerChatState get(int intValue) { return lookup.get(intValue); }
        static { for (PlayerChatState e : EnumSet.allOf(PlayerChatState.class)) lookup.put(e.ordinal(), e); }
    }

    private PlayerGuiState playerGuiState = PlayerGuiState.NONE;
    private boolean playerGuiDontSendDetailedGUIInfo = false;
    private boolean playerGuiDontSendItemInfo = false;
    private PlayerChatState playerChatState = PlayerChatState.NONE;
    private float typingAmplifier = 1.0F;
    private float screenPosPercentX = 0.0F;
    private float screenPosPercentY = 0.0F;
    private boolean isPressing = false;
    private int ticksSinceLastAction = 0;
    private int ticksToMarkPlayerIdleSyncedForClient = 6000;
    private Particle particle;
    private Particle particleIdle;
    private long lastTypeTime;
    private String lastTypeString = "";
    private boolean flagForRemoval = false;
    private long lastTypeTimeForAmp;
    private String lastTypeStringForAmp = "";
    private int lastTypeDiff;
    private Lerpables lerpTarget = new Lerpables();
    private final Lerpables lerpPrev = new Lerpables();
    public float lerpTicks = 0.0F;
    public float lerpTicksPrev = 0.0F;
    public float lerpTicksMax = 5.0F;
    public float lastPartialTick = 0.0F;
    public float yRotHeadWhileOverriding = 0.0F;
    public float xRotHeadWhileOverriding = 0.0F;
    public float yRotHeadBeforeOverriding = 0.0F;
    public float xRotHeadBeforeOverriding = 0.0F;
    private float typingAmplifierSmooth = 0.5F;
    private final CompoundTag nbtCache = new CompoundTag();
    private ScreenData screenData;
    private BlockPos lastBlockOpened = BlockPos.ZERO;
    private final InventorySnapshot inventorySnapshotPlayer = new InventorySnapshot();
    private final InventorySnapshot inventorySnapshotContainer = new InventorySnapshot();
    private final InventorySnapshot inventorySnapshotCarried = new InventorySnapshot();
    private boolean isCarriedItemFromPlayerInventory = false;
    private UUID uuid;

    public PlayerStatus(PlayerGuiState playerGuiState, UUID uuid) {
        this.playerGuiState = playerGuiState;
        this.uuid = uuid;
    }

    public void tick() {
        lerpTicksPrev = lerpTicks;
        if (isLerping()) lerpTicks++;
    }

    public void setNewLerp(float ticks) {
        lerpTicksMax = ticks;
        lerpTicks = 0.0F;
        lerpTicksPrev = 0.0F;
    }

    public float getPartialLerp(float partialTick) {
        float lerpPrev = lerpTicksPrev / lerpTicksMax;
        float lerp = lerpTicks / lerpTicksMax;
        return Math.min(lerpPrev + (lerp - lerpPrev) * partialTick, lerpTicksMax);
    }

    public void resetParticles() {
        if (particle != null) particle.remove();
        if (particleIdle != null) particleIdle.remove();
        particle = null;
        particleIdle = null;
    }

    public void reset() {
        resetParticles();
        ticksSinceLastAction = 0;
    }

    public boolean isLerping() { return lerpTicks < lerpTicksMax; }

    // Getters and setters
    public PlayerGuiState getPlayerGuiState() { return playerGuiState; }
    public void setPlayerGuiState(PlayerGuiState state) { this.playerGuiState = state; }
    public boolean isPlayerGuiDontSendDetailedGUIInfo() { return playerGuiDontSendDetailedGUIInfo; }
    public void setPlayerGuiDontSendDetailedGUIInfo(boolean val) { this.playerGuiDontSendDetailedGUIInfo = val; }
    public boolean isPlayerGuiDontSendItemInfo() { return playerGuiDontSendItemInfo; }
    public void setPlayerGuiDontSendItemInfo(boolean val) { this.playerGuiDontSendItemInfo = val; }
    public Particle getParticle() { return particle; }
    public void setParticle(Particle p) { this.particle = p; }
    public long getLastTypeTime() { return lastTypeTime; }
    public void setLastTypeTime(long t) { this.lastTypeTime = t; }
    public String getLastTypeString() { return lastTypeString; }
    public void setLastTypeString(String s) { this.lastTypeString = s; }
    public float getScreenPosPercentX() { return screenPosPercentX; }
    public void setScreenPosPercentX(float v) { this.screenPosPercentX = v; }
    public float getScreenPosPercentY() { return screenPosPercentY; }
    public void setScreenPosPercentY(float v) { this.screenPosPercentY = v; }
    public Lerpables getLerpTarget() { return lerpTarget; }
    public void setLerpTarget(Lerpables target) { this.lerpTarget = target; }
    public Lerpables getLerpPrev() { return lerpPrev; }
    public boolean isPressing() { return isPressing; }
    public void setPressing(boolean v) { this.isPressing = v; }
    public float getTypingAmplifier() { return typingAmplifier; }
    public void setTypingAmplifier(float v) { this.typingAmplifier = v; }
    public int getLastTypeDiff() { return lastTypeDiff; }
    public void setLastTypeDiff(int v) { this.lastTypeDiff = v; }
    public long getLastTypeTimeForAmp() { return lastTypeTimeForAmp; }
    public void setLastTypeTimeForAmp(long v) { this.lastTypeTimeForAmp = v; }
    public String getLastTypeStringForAmp() { return lastTypeStringForAmp; }
    public void setLastTypeStringForAmp(String s) { this.lastTypeStringForAmp = s; }
    public int getTicksSinceLastAction() { return ticksSinceLastAction; }
    public void setTicksSinceLastAction(int v) { this.ticksSinceLastAction = v; }
    public float getTypingAmplifierSmooth() { return typingAmplifierSmooth; }
    public void setTypingAmplifierSmooth(float v) { this.typingAmplifierSmooth = v; }
    public boolean isFlagForRemoval() { return flagForRemoval; }
    public void setFlagForRemoval(boolean v) { this.flagForRemoval = v; }
    public Particle getParticleIdle() { return particleIdle; }
    public void setParticleIdle(Particle p) { this.particleIdle = p; }
    public boolean isIdle() { return ticksSinceLastAction > ticksToMarkPlayerIdleSyncedForClient; }
    public CompoundTag getNbtCache() { return nbtCache; }
    public int getTicksToMarkPlayerIdleSyncedForClient() { return ticksToMarkPlayerIdleSyncedForClient; }
    public void setTicksToMarkPlayerIdleSyncedForClient(int v) { this.ticksToMarkPlayerIdleSyncedForClient = v; }
    public PlayerChatState getPlayerChatState() { return playerChatState; }
    public void setPlayerChatState(PlayerChatState s) { this.playerChatState = s; }
    public ScreenData getScreenData() {
        if (screenData == null) screenData = new ScreenData();
        return screenData;
    }
    public void setScreenData(ScreenData sd) { this.screenData = sd; }
    public BlockPos getLastBlockOpened() { return lastBlockOpened; }
    public void setLastBlockOpened(BlockPos pos) { this.lastBlockOpened = pos; }
    public InventorySnapshot getInventorySnapshotPlayer() { return inventorySnapshotPlayer; }
    public InventorySnapshot getInventorySnapshotContainer() { return inventorySnapshotContainer; }
    public InventorySnapshot getInventorySnapshotCarried() { return inventorySnapshotCarried; }
    public boolean isCarriedItemFromPlayerInventory() { return isCarriedItemFromPlayerInventory; }
    public void setCarriedItemFromPlayerInventory(boolean v) { this.isCarriedItemFromPlayerInventory = v; }
    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }
}
