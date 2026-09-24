package com.skd.playeractivityview.mixin;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuDoClickMixin {
    @Inject(method = "clicked", at = @At("HEAD"), remap = false)
    private void onClicked(int slotIndex, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci) {
        if (!player.level().isClientSide() && PlayerActivity.getPlayerStatusManagerServer() != null) {
            PlayerActivity.getPlayerStatusManagerServer().onContainerClick(player, slotIndex);
        }
    }
}
