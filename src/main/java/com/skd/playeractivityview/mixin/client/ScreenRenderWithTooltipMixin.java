package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.client.screen.RenderHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenRenderWithTooltipMixin {

    @Inject(method = "renderWithTooltip", at = @At("TAIL"))
    private void renderWithTooltipEnd(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        if (!RenderHelper.performingOwnRender) {
            RenderHelper.renderWithTooltipEnd(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }
}
