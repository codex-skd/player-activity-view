package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.client.screen.ScreenParticleRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostChain.class)
public class PostChainResizeMixin {
    @Inject(method = "resize", at = @At("TAIL"))
    private void onResize(int width, int height, CallbackInfo ci) {
        ScreenParticleRenderer.getInstance().resize(width, height);
    }
}
