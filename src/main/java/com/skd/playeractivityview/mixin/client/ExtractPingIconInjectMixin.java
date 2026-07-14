package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class ExtractPingIconInjectMixin {
    @Inject(method = "extractPingIcon", at = @At("TAIL"), remap = false)
    private void onExtractPingIcon(GuiGraphicsExtractor graphics, int slotWidth, int xo, int yo, PlayerInfo info, CallbackInfo ci) {
        if (PlayerActivity.getPlayerStatusManagerClient() != null) {
            PlayerActivity.getPlayerStatusManagerClient().renderPingIconHook(graphics, slotWidth, xo, yo, info);
        }
    }
}
