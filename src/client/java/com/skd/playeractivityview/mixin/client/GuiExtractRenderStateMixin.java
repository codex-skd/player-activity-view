package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivityClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class GuiExtractRenderStateMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"), remap = false)
    private void onExtractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (PlayerActivityClient.getPlayerStatusManagerClient() != null) {
            PlayerActivityClient.getPlayerStatusManagerClient().onGuiRender(graphics);
        }
    }
}
