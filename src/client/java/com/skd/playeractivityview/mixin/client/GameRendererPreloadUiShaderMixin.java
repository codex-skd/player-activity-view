package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererPreloadUiShaderMixin {
    @Inject(method = "preloadUiShader", at = @At("TAIL"), remap = false)
    private void onPreloadUiShader(ResourceProvider resourceProvider, CallbackInfo ci) {
        PlayerActivity.initCustomShaders(resourceProvider);
    }
}
