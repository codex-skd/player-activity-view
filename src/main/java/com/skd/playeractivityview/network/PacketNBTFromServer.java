package com.skd.playeractivityview.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;

public record PacketNBTFromServer(CompoundTag nbt) implements PacketBase {
    public static final Type<PacketNBTFromServer> TYPE = new Type<>(Identifier.fromNamespaceAndPath("player_activity_view", "nbt_client"));
    public static final StreamCodec<FriendlyByteBuf, PacketNBTFromServer> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG, PacketNBTFromServer::nbt, PacketNBTFromServer::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
