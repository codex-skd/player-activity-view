package com.skd.playeractivityview;

import com.skd.playeractivityview.network.PacketBase;
import com.skd.playeractivityview.network.PacketNBTFromClient;
import com.skd.playeractivityview.network.PacketNBTFromServer;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PlayerActivityNetworkingNeoForge extends PlayerActivityNetworking {

    public static void register(PayloadRegistrar registrar) {
        registerClientboundPacket(PacketNBTFromServer.TYPE, PacketNBTFromServer.STREAM_CODEC, PacketNBTFromServer::handle, registrar);
        registerServerboundPacket(PacketNBTFromClient.TYPE, PacketNBTFromClient.STREAM_CODEC, PacketNBTFromClient::handle, registrar);
    }

    private static <T extends PacketBase> void registerServerboundPacket(
            Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, Player> handler, PayloadRegistrar registrar) {
        IPayloadHandler<T> serverHandler = (packet, ctx) -> ctx.enqueueWork(() -> handler.accept(packet, ctx.player()));
        registrar.playToServer(type, codec, serverHandler);
    }

    private static <T extends PacketBase> void registerClientboundPacket(
            Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, Player> handler, PayloadRegistrar registrar) {
        IPayloadHandler<T> clientHandler = (packet, ctx) -> ctx.enqueueWork(() -> handler.accept(packet, Minecraft.getInstance().player));
        registrar.playToClient(type, codec, clientHandler);
    }

    @Override
    public void clientSendToServer(CompoundTag data) {
        ClientPacketDistributor.sendToServer(new PacketNBTFromClient(data));
    }

    @Override
    public void serverSendToClientAll(CompoundTag data) {
        PacketDistributor.sendToAllPlayers(new PacketNBTFromServer(data));
    }

    @Override
    public void serverSendToClientPlayer(CompoundTag data, Player player) {
        PacketDistributor.sendToPlayer((ServerPlayer)player, new PacketNBTFromServer(data));
    }

    @Override
    public void serverSendToClientNear(CompoundTag data, Vec3 pos, double dist, Level level) {
        PacketDistributor.sendToPlayersNear((ServerLevel)level, null, pos.x, pos.y, pos.z, dist, new PacketNBTFromServer(data));
    }
}
