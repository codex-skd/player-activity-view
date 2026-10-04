package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenExtractBackgroundMixin {
    @Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true, remap = false)
    private void onExtractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (PlayerActivity.getPlayerStatusManagerClient() != null) {
            PlayerActivity.getPlayerStatusManagerClient().onExtractBackground(ci);
        }
    }
}
