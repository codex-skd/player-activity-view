package com.skd.playeractivityview.network;

import com.skd.playeractivityview.PlayerActivityNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public record PacketNBTFromClient(CompoundTag nbt) implements PacketBase {
    public static final Type<PacketNBTFromClient> TYPE = new Type<>(Identifier.fromNamespaceAndPath("player_activity_view", "nbt_server"));
    public static final StreamCodec<FriendlyByteBuf, PacketNBTFromClient> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG, PacketNBTFromClient::nbt, PacketNBTFromClient::new
    );

    public void handle(Player player) {
        try {
            if (player != null) {
                com.skd.playeractivityview.PlayerActivity.getPlayerStatusManagerServer().receiveAny(player, this.nbt);
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
