package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.loader.ClientEvents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class RenderFramePostMixin {
    @Inject(method = "render", at = @At("TAIL"), remap = false)
    private void playerActivityView$onRenderFramePost(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        ClientEvents.onRenderFramePost();
    }
}
