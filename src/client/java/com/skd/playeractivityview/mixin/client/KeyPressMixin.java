package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.loader.ClientEvents;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyPressMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), remap = false)
    private void playerActivityView$onKeyPress(long window, int action, net.minecraft.client.input.KeyEvent event, CallbackInfo ci) {
        if (action == 1) {
            ClientEvents.onKey();
        }
    }
}
