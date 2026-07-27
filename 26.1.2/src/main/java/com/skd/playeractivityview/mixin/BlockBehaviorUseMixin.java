package com.skd.playeractivityview.mixin;

import com.skd.playeractivityview.PlayerActivity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.world.level.block.Block.class)
public class BlockBehaviorUseMixin {
    @Inject(method = "useItemOn", at = @At("HEAD"))
    private void onUseItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level.isClientSide() && PlayerActivity.getPlayerStatusManagerServer() != null) {
            PlayerActivity.getPlayerStatusManagerServer().useBlock(player, pos);
        }
    }
}
