package com.skd.playeractivityview;

import com.mojang.datafixers.util.Pair;
import com.skd.playeractivityview.config.ConfigCommon;
import com.skd.playeractivityview.config.ServerConfigSyncHelper;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlayerStatusManagerServer extends PlayerStatusManager {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("player_activity_view");

    @Override
    public void tickPlayer(Player player) {
        getStatus(player).setTicksToMarkPlayerIdleSyncedForClient(ConfigCommon.TICKS_TO_MARK_PLAYER_IDLE.get());
        super.tickPlayer(player);
    }

    public void receiveAny(Player player, CompoundTag data) {
        data.putString(PlayerActivityNetworking.NBTDataPlayerUUID, player.getUUID().toString());
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerGuiStatus)) {
            PlayerStatus.PlayerGuiState state = PlayerStatus.PlayerGuiState.get(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerGuiStatus, 0));
            getStatus(player).setPlayerGuiState(state);
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerChatStatus)) {
            PlayerStatus.PlayerChatState state = PlayerStatus.PlayerChatState.get(data.getIntOr(PlayerActivityNetworking.NBTDataPlayerChatStatus, 0));
            getStatus(player).setPlayerChatState(state);
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerIdleTicks)) {
            handleIdleState(player, data.getIntOr(PlayerActivityNetworking.NBTDataPlayerIdleTicks, 0));
            data.putInt(PlayerActivityNetworking.NBTDataPlayerTicksToGoIdle, ConfigCommon.TICKS_TO_MARK_PLAYER_IDLE.get());
        }
        if (data.contains(PlayerActivityNetworking.NBTDataPlayerMouseX)) {
            setMouse(player.getUUID(),
                data.getFloatOr(PlayerActivityNetworking.NBTDataPlayerMouseX, 0f),
                data.getFloatOr(PlayerActivityNetworking.NBTDataPlayerMouseY, 0f),
                data.getBooleanOr(PlayerActivityNetworking.NBTDataPlayerMousePressed, false));
        }
        getStatus(player).getNbtCache().merge(data);
        if (!data.contains(PlayerActivityNetworking.NBTDataPlayerGuiStatus)
            && !data.contains(PlayerActivityNetworking.NBTDataPlayerIdleTicks)
            && !data.contains(PlayerActivityNetworking.NBTDataPlayerChatStatus)) {
            PlayerActivityNetworking.instance().serverSendToClientNear(
                data, player.position(), ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get(), player.level());
        } else {
            PlayerActivityNetworking.instance().serverSendToClientAll(data);
        }
    }

    public void handleIdleState(Player player, int idleTicks) {
        if (PlayerActivity.instance().getPlayerList() != null) {
            PlayerStatus status = getStatus(player);
            if (PlayerActivity.instance().getPlayerList().getPlayerCount() > 1 || singleplayerTesting) {
                if (idleTicks > ConfigCommon.TICKS_TO_MARK_PLAYER_IDLE.get()) {
                    if (!status.isIdle()) broadcast(player.getDisplayName().getString() + " has gone idle");
                } else if (status.isIdle()) {
                    broadcast(player.getDisplayName().getString() + " is no longer idle");
                }
            }
            status.setTicksSinceLastAction(idleTicks);
        }
    }

    public void broadcast(String msg) {
        if (PlayerActivity.instance().getPlayerList() != null && ConfigCommon.ANNOUNCE_IDLE_STATES_IN_CHAT.get()) {
            PlayerActivity.instance().getPlayerList().broadcastSystemMessage(Component.literal(msg), false);
        }
    }

    @Override
    public void playerLoggedIn(Player player) {
        super.playerLoggedIn(player);
        if (player instanceof ServerPlayer) {
            for (Entry<UUID, PlayerStatus> entry : lookupPlayerToStatus.entrySet()) {
                PlayerActivityNetworking.instance().serverSendToClientPlayer(entry.getValue().getNbtCache(), player);
            }
            PlayerActivityNetworking.instance().serverSendToClientPlayer(getServerConfigNBT(), player);
        }
    }

    public void syncServerConfigToAllPlayers() {
        if (PlayerActivity.instance().getPlayerList() != null) {
            for (ServerPlayer sp : PlayerActivity.instance().getPlayerList().getPlayers()) {
                PlayerActivityNetworking.instance().serverSendToClientPlayer(getServerConfigNBT(), sp);
            }
        }
    }

    public CompoundTag getServerConfigNBT() {
        CompoundTag nbt = ServerConfigSyncHelper.getInstance().getSyncableConfigOnServer();
        nbt.putBoolean(PlayerActivityNetworking.NBTDataServerConfig, true);
        return nbt;
    }

    public void sendItemMove(Player player, Level level, ItemStack stack, float fromX, float fromY, float fromZ, float toX, float toY, float toZ) {
        if (level.getNearestPlayer(fromX, fromY, fromZ, ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get(), e -> e != player) != null) {
            CompoundTag data = new CompoundTag();
            CompoundTag itemData = (CompoundTag)ItemStack.CODEC.encodeStart(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), stack).result().orElse(new CompoundTag());
            if (itemData.sizeInBytes() > 31000) {
                stack = new ItemStack(stack.getItem(), stack.getCount());
                itemData = (CompoundTag)ItemStack.CODEC.encodeStart(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), stack).result().orElse(new CompoundTag());
            }
            data.put(PlayerActivityNetworking.NBTDataItemTransferItemStack, itemData);
            data.putFloat(PlayerActivityNetworking.NBTDataItemTransferFromX, fromX);
            data.putFloat(PlayerActivityNetworking.NBTDataItemTransferFromY, fromY);
            data.putFloat(PlayerActivityNetworking.NBTDataItemTransferFromZ, fromZ);
            data.putFloat(PlayerActivityNetworking.NBTDataItemTransferToX, toX);
            data.putFloat(PlayerActivityNetworking.NBTDataItemTransferToY, toY);
            data.putFloat(PlayerActivityNetworking.NBTDataItemTransferToZ, toZ);
            PlayerActivityNetworking.instance().serverSendToClientNear(data, new Vec3(fromX, fromY, fromZ), ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get(), level);
        }
    }

    public void makeNewInventorySnapshot(AbstractContainerMenu menu, Player player) {
        PlayerStatus ps = getStatus(player);
        ps.getInventorySnapshotPlayer().itemStackList.clear();
        ps.getInventorySnapshotContainer().itemStackList.clear();
        ps.getInventorySnapshotCarried().itemStackList.clear();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (!item.isEmpty()) ps.getInventorySnapshotPlayer().itemStackList.add(item.copy());
        }
        for (Slot slot : menu.slots) {
            if (!(slot.container instanceof Inventory)) ps.getInventorySnapshotContainer().itemStackList.add(slot.getItem().copy());
        }
        if (!menu.getCarried().isEmpty()) ps.getInventorySnapshotCarried().itemStackList.add(menu.getCarried().copy());
    }

    public void doClickPre(AbstractContainerMenu menu, int slotId, int button, Object clickType, Player player) {
    }

    public void useBlock(Player player, BlockPos pos) {
        if (ServerSyncedConfig.SHOW_ITEMS_TRANSFERRED.get() && !FakePlayerHelper.isFakePlayer(player)) {
            PlayerStatus ps = getStatus(player);
            if (!ps.isPlayerGuiDontSendItemInfo()) ps.setLastBlockOpened(pos);
        }
    }

    public void doClickPost(AbstractContainerMenu menu, int slotId, int button, Object clickType, Player player) {
    }

    private ItemStack getMatchingItem(ItemStack stack, List<ItemStack> list) {
        for (ItemStack s : list) if (ItemStack.isSameItem(stack, s)) return s;
        return null;
    }

    private Pair<List<ItemStack>, List<ItemStack>> processInventorySnapshots(List<ItemStack> pre, List<ItemStack> post) {
        List<ItemStack> added = new ArrayList<>(), removed = new ArrayList<>();
        for (int i = 0; i < post.size(); i++) {
            ItemStack preStack = pre.get(i), postStack = post.get(i);
            if (!ItemStack.isSameItem(preStack, postStack)) {
                if (preStack.isEmpty() && !postStack.isEmpty()) added.add(postStack.copy());
                else if (!preStack.isEmpty() && postStack.isEmpty()) removed.add(preStack.copy());
            } else if (preStack.getCount() > postStack.getCount()) {
                ItemStack s = preStack.copy(); s.setCount(preStack.getCount() - postStack.getCount()); removed.add(s);
            } else if (preStack.getCount() < postStack.getCount()) {
                ItemStack s = postStack.copy(); s.setCount(postStack.getCount() - preStack.getCount()); added.add(s);
            }
        }
        return Pair.of(added, removed);
    }

    private void sendItemMove(Player player, ItemStack stack, boolean toContainer) {
        PlayerStatus ps = getStatus(player);
        BlockPos pos = ps.getLastBlockOpened();
        Vec3 lookVec = getBodyAngle(player).scale(0.55);
        if (toContainer) {
            sendItemMove(player, player.level(), stack, (float)(player.getX() + lookVec.x), (float)(player.getY() + 1.2), (float)(player.getZ() + lookVec.z),
                (float)pos.getX() + 0.5F, (float)pos.getY() + 0.7F, (float)pos.getZ() + 0.5F);
        } else {
            sendItemMove(player, player.level(), stack, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.7F, (float)pos.getZ() + 0.5F,
                (float)(player.getX() + lookVec.x), (float)(player.getY() + 1.2), (float)(player.getZ() + lookVec.z));
        }
    }
}
