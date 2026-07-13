package com.skd.playeractivity.network;

import com.skd.playeractivity.PlayerActivityNetworking;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public record PacketNBTFromServer(CompoundTag nbt) implements PacketBase {
    public static final Type<PacketNBTFromServer> TYPE = new Type<>(Identifier.fromNamespaceAndPath("player_activity", "nbt_client"));
    public static final StreamCodec<FriendlyByteBuf, PacketNBTFromServer> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG, PacketNBTFromServer::nbt, PacketNBTFromServer::new
    );

    public void handle(Player player) {
        try {
            if (nbt.contains(PlayerActivityNetworking.NBTDataPlayerUUID)) {
                UUID uuid = UUID.fromString(nbt.getStringOr(PlayerActivityNetworking.NBTDataPlayerUUID, ""));
                com.skd.playeractivity.PlayerActivity.getPlayerStatusManagerClient().receiveAny(uuid, this.nbt);
            } else if (nbt.contains(PlayerActivityNetworking.NBTDataServerConfig)) {
                com.skd.playeractivity.PlayerActivity.getPlayerStatusManagerClient().receiveServerConfig(this.nbt);
            } else if (nbt.contains(PlayerActivityNetworking.NBTDataItemTransferItemStack)) {
                com.skd.playeractivity.PlayerActivity.getPlayerStatusManagerClient().receiveItemMove(this.nbt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
