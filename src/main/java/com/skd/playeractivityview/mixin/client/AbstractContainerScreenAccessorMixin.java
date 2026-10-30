package com.skd.playeractivityview.mixin.client;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes the on-screen panel rectangle (leftPos/topPos/imageWidth/imageHeight) of any
 * AbstractContainerScreen so the screen-mirror crop can bound itself to the actual rendered
 * panel instead of relying on which GUI elements happen to be extracted.
 */
@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessorMixin {
    @Accessor(value = "leftPos", remap = false)
    int playerActivityView$getLeftPos();

    @Accessor(value = "topPos", remap = false)
    int playerActivityView$getTopPos();

    @Accessor(value = "imageWidth", remap = false)
    int playerActivityView$getImageWidth();

    @Accessor(value = "imageHeight", remap = false)
    int playerActivityView$getImageHeight();}
