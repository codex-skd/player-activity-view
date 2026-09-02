package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerStatusManagerClient;
import com.skd.playeractivityview.ShaderInstanceBlur;
import com.skd.playeractivityview.PlayerActivity;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Mixin(GameRenderer.class)
public abstract class GameRendererReloadShadersMixin {

    @Shadow
    @Final
    private Map<String, ShaderInstance> shaders;

    @Inject(
            method = "reloadShaders", at = @At("RETURN")
    )
    private void onLoadShaders(ResourceProvider resourceProvider, CallbackInfo ci) {

        PlayerStatusManagerClient.particle = null;
        PlayerStatusManagerClient.positionTexBlur = null;
        PlayerStatusManagerClient.positionTexBlurHorizontal = null;
        PlayerStatusManagerClient.positionTexBlurVertical = null;

        try {
            PlayerStatusManagerClient.particle = new ShaderInstanceBlur(getResourceFactory(resourceProvider), "particle",
                    DefaultVertexFormat.PARTICLE);
            PlayerStatusManagerClient.positionTexBlur = new ShaderInstanceBlur(getResourceFactory(resourceProvider), "position_tex_blur",
                    DefaultVertexFormat.POSITION_TEX);
            PlayerStatusManagerClient.positionTexBlurHorizontal = new ShaderInstanceBlur(getResourceFactory(resourceProvider), "position_tex_blur_horizontal",
                    DefaultVertexFormat.POSITION_TEX);
            PlayerStatusManagerClient.positionTexBlurVertical = new ShaderInstanceBlur(getResourceFactory(resourceProvider), "position_tex_blur_vertical",
                    DefaultVertexFormat.POSITION_TEX);

            shaders.put(PlayerStatusManagerClient.particle.getName(), PlayerStatusManagerClient.particle);
            shaders.put(PlayerStatusManagerClient.positionTexBlur.getName(), PlayerStatusManagerClient.positionTexBlur);
            shaders.put(PlayerStatusManagerClient.positionTexBlurHorizontal.getName(), PlayerStatusManagerClient.positionTexBlurHorizontal);
            shaders.put(PlayerStatusManagerClient.positionTexBlurVertical.getName(), PlayerStatusManagerClient.positionTexBlurVertical);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ResourceProvider getResourceFactory(ResourceProvider resourceManager) {
        ResourceProvider resourceFactory = new ResourceProvider() {
            @Override
            public Optional<Resource> getResource(ResourceLocation resourceLocation) {
                ResourceLocation corrected = ResourceLocation.fromNamespaceAndPath(
                        PlayerActivity.MODID, resourceLocation.getPath());
                return resourceManager.getResource(corrected);
            }
        };
        return resourceFactory;
    }
}
