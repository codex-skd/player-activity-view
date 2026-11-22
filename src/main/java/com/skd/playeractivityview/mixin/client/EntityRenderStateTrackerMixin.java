package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.render.EntityRenderStateTracker;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderStateTrackerMixin {
    @Inject(method = "extractEntity", at = @At("RETURN"), remap = false)
    private void onExtractEntity(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        if (entity instanceof Player player) {
            EntityRenderStateTracker.put(cir.getReturnValue(), player.getUUID());
        }
    }
}
