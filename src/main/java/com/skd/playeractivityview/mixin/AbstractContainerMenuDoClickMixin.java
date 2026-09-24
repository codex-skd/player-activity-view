package com.skd.playeractivityview.mixin;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuDoClickMixin {

    @Shadow
    public final NonNullList<Slot> slots = NonNullList.create();

    @Inject(method = "doClick", at = @At("HEAD"))
    private void doClickPre(int pSlotId, int pButton, ClickType pClickType, Player pPlayer, CallbackInfo ci) {
        if (!pPlayer.level().isClientSide()) {
            PlayerActivity.getPlayerStatusManagerServer().onContainerClick(pPlayer, pSlotId);
        }
    }
}
