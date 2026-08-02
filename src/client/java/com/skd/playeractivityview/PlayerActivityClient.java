package com.skd.playeractivityview;

import com.skd.playeractivityview.loader.ClientEvents;
import com.skd.playeractivityview.network.PacketNBTFromServer;
import com.skd.playeractivityview.render.DynamicScreenRenderer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;

public class PlayerActivityClient implements ClientModInitializer {
    private static PlayerStatusManagerClient playerStatusManagerClient;

    public static PlayerStatusManagerClient getPlayerStatusManagerClient() {
        if (playerStatusManagerClient == null) playerStatusManagerClient = new PlayerStatusManagerClient();
        return playerStatusManagerClient;
    }

    @Override
    public void onInitializeClient() {
        new ClientEvents();

        PlayerActivityNetworkingFabric.clientboundRegistration = () ->
            ClientPlayNetworking.registerGlobalReceiver(PacketNBTFromServer.TYPE, (packet, context) ->
                context.client().execute(() -> PlayerStatusManagerClient.receiveServerPacket(packet.nbt())));

        PlayerActivityNetworkingFabric.clientboundHandler = data ->
            ClientPlayNetworking.send(new com.skd.playeractivityview.network.PacketNBTFromClient(data));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null && client.player != null) {
                getPlayerStatusManagerClient().tickGame();
                for (net.minecraft.world.entity.player.Player p : client.level.players()) {
                    getPlayerStatusManagerClient().tickPlayerClient(p);
                }
            }
        });

        new DynamicScreenRenderer();

        initParticleSprites();
    }

    private void initParticleSprites() {
        try {
            TextureAtlas atlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_PARTICLES);
            ModParticles.textureAtlasUpload(atlas);
        } catch (Exception e) {
            // Atlas not ready yet, will be initialized on first access
        }
        try {
            ((ReloadableResourceManager)Minecraft.getInstance().getResourceManager()).registerReloadListener(new PreparableReloadListener() {
                @Override
                public CompletableFuture<Void> reload(PreparableReloadListener.SharedState state, Executor bgExec, PreparableReloadListener.PreparationBarrier barrier, Executor gameExec) {
                    try {
                        TextureAtlas atlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_PARTICLES);
                        ModParticles.textureAtlasUpload(atlas);
                    } catch (Exception ignored) {}
                    return barrier.wait(Unit.INSTANCE).thenRun(() -> {});
                }
            });
        } catch (Exception e) {
            // ModernFix freezes listener list after reload
        }
    }

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
