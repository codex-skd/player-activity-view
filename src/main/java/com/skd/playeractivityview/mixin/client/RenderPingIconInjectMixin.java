package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PlayerTabOverlay.class, priority = 999)
public abstract class RenderPingIconInjectMixin {

    @Redirect(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;renderPingIcon(Lnet/minecraft/client/gui/GuiGraphics;IIILnet/minecraft/client/multiplayer/PlayerInfo;)V"))
    private void renderPingIconRedirect(PlayerTabOverlay playerTabOverlay, GuiGraphics pGuiGraphics, int p_281809_, int p_282801_, int pY, PlayerInfo pPlayerInfo) {
        if (!PlayerActivity.getPlayerStatusManagerClient().renderPingIconHook((PlayerTabOverlay)(Object)this, pGuiGraphics, p_281809_, p_282801_, pY, pPlayerInfo)) {
            this.callRenderPingIcon(pGuiGraphics, p_281809_, p_282801_, pY, pPlayerInfo);
        }
    }

    @SuppressWarnings("target")
    @org.spongepowered.asm.mixin.Shadow(remap = false)
    private void renderPingIcon(GuiGraphics pGuiGraphics, int p_281809_, int p_282801_, int pY, PlayerInfo pPlayerInfo) {}

    private void callRenderPingIcon(GuiGraphics pGuiGraphics, int p_281809_, int p_282801_, int pY, PlayerInfo pPlayerInfo) {
        this.renderPingIcon(pGuiGraphics, p_281809_, p_282801_, pY, pPlayerInfo);
    }
}
