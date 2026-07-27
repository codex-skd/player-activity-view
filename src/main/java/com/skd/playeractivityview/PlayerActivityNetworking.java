package com.skd.playeractivityview;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class PlayerActivityNetworking {
    public static final String NBTDataPlayerUUID = "playerUuid";
    public static final String NBTDataPlayerGuiStatus = "playerGuiStatus";
    public static final String NBTDataPlayerGuiDontSendDetailedGUIInfo = "dontSendDetailedGUIInfo";
    public static final String NBTDataPlayerGuiDontSendItemInfo = "dontSendDetailedItemInfo";
    public static final String NBTDataPlayerChatStatus = "playerChatStatus";
    public static final String NBTDataPlayerTypingAmp = "playerTypingAmp";
    public static final String NBTDataPlayerScreenCompressedPixelData = "screenCompressedPixelData";
    public static final String NBTDataPlayerScreenCompressedPixelDataPacketCount = "screenCompressedPixelDataPacketCount";
    public static final String NBTDataPlayerScreenCompressedPixelDataPacketIndex = "screenCompressedPixelDataPacketIndex";
    public static final String NBTDataPlayerScreenCompressedPixelDataSize = "screenCompressedPixelDataSize";
    public static final String NBTDataPlayerScreenWidth = "screenWidth";
    public static final String NBTDataPlayerScreenHeight = "screenHeight";
    public static final String NBTDataPlayerIdleTicks = "playerIdleTicks";
    public static final String NBTDataPlayerTicksToGoIdle = "playerTicksToGoIdle";
    public static final String NBTDataPlayerMouseX = "playerMouseX";
    public static final String NBTDataPlayerMouseY = "playerMouseY";
    public static final String NBTDataPlayerMousePressed = "playerMousePressed";
    public static final String NBTDataItemTransferItemStack = "itemTransferItemStack";
    public static final String NBTDataItemTransferFromX = "itemTransferFromX";
    public static final String NBTDataItemTransferFromY = "itemTransferFromY";
    public static final String NBTDataItemTransferFromZ = "itemTransferFromZ";
    public static final String NBTDataItemTransferToX = "itemTransferToX";
    public static final String NBTDataItemTransferToY = "itemTransferToY";
    public static final String NBTDataItemTransferToZ = "itemTransferToZ";
    public static final String NBTDataServerConfig = "serverConfig";

    public final List<CompoundTag> listQueue = new ArrayList<>();
    public long lastTickProcessed = 0L;
    private static PlayerActivityNetworking instance;

    public static PlayerActivityNetworking instance() {
        return instance;
    }

    public PlayerActivityNetworking() {
        instance = this;
    }

    public abstract void clientSendToServer(CompoundTag data);

    public void clientSendToServerAddToQueue(CompoundTag data) {
        listQueue.add(data);
    }

    public void process1ItemFromQueue(Level level) {
        if (!listQueue.isEmpty() && level.getGameTime() > lastTickProcessed + 20L) {
            lastTickProcessed = level.getGameTime();
            clientSendToServer(listQueue.remove(0));
        }
    }

    public abstract void serverSendToClientAll(CompoundTag data);
    public abstract void serverSendToClientPlayer(CompoundTag data, Player player);
    public abstract void serverSendToClientNear(CompoundTag data, Vec3 pos, double dist, Level level);
}
