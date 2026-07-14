package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.render.EntityRenderStateTracker;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class SetupAnimInjectMixin {
    @Inject(method = "setupAnim", at = @At("TAIL"), remap = false)
    private void onSetupAnim(HumanoidRenderState state, CallbackInfo ci) {
        if (PlayerActivity.getPlayerStatusManagerClient() != null) {
            PlayerActivity.getPlayerStatusManagerClient().onSetupAnim((HumanoidModel<?>) (Object) this, state);
        }
    }
}
