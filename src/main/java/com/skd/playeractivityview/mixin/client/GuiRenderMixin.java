package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.client.screen.RenderHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiRenderMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        PlayerActivity.getPlayerStatusManagerClient().onGuiRender();
        RenderHelper.guiRender(guiGraphics);
    }
}
