package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.loader.ClientEvents;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseButtonPostMixin {
    @Inject(method = "onButton", at = @At("HEAD"), remap = false)
    private void playerActivityView$onButton(long window, net.minecraft.client.input.MouseButtonInfo button, int action, CallbackInfo ci) {
        ClientEvents.onMouse(action == 1);
    }
}
