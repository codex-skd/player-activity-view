package com.skd.playeractivityview;

import com.skd.playeractivityview.network.PacketNBTFromClient;
import com.skd.playeractivityview.network.PacketNBTFromServer;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlayerActivityNetworkingFabric extends PlayerActivityNetworking {

    /** Filled in by the client entrypoint (client source set) so the common code stays server-safe. */
    public static Consumer<CompoundTag> clientboundHandler;

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(PacketNBTFromClient.TYPE, PacketNBTFromClient.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PacketNBTFromServer.TYPE, PacketNBTFromServer.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PacketNBTFromClient.TYPE, (packet, context) ->
            context.server().execute(() -> packet.handle(context.player())));
    }

    @Override
    public void clientSendToServer(CompoundTag data) {
        if (clientboundHandler != null) clientboundHandler.accept(data);
    }

    @Override
    public void serverSendToClientAll(CompoundTag data) {
        PacketNBTFromServer packet = new PacketNBTFromServer(data);
        if (PlayerActivity.instance().getPlayerList() != null) {
            for (ServerPlayer sp : PlayerActivity.instance().getPlayerList().getPlayers()) {
                ServerPlayNetworking.send(sp, packet);
            }
        }
    }

    @Override
    public void serverSendToClientPlayer(CompoundTag data, Player player) {
        if (player instanceof ServerPlayer sp) {
            ServerPlayNetworking.send(sp, new PacketNBTFromServer(data));
        }
    }

    @Override
    public void serverSendToClientNear(CompoundTag data, Vec3 pos, double dist, Level level) {
        PacketNBTFromServer packet = new PacketNBTFromServer(data);
        if (PlayerActivity.instance().getPlayerList() != null) {
            for (ServerPlayer sp : PlayerActivity.instance().getPlayerList().getPlayers()) {
                if (sp.level().dimension() == level.dimension() && sp.distanceToSqr(pos) <= dist * dist) {
                    ServerPlayNetworking.send(sp, packet);
                }
            }
        }
    }
}
