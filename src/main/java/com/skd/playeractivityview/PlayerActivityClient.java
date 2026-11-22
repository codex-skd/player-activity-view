package com.skd.playeractivityview;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = PlayerActivity.MODID, dist = Dist.CLIENT)
public class PlayerActivityClient {
    public PlayerActivityClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
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
