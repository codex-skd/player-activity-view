package com.skd.playeractivityview.mixin.client;

import com.skd.playeractivityview.ModParticles;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureAtlas.class)
public class TextureAtlasUploadMixin {
    @Inject(method = "upload", at = @At("TAIL"))
    private void onUpload(CallbackInfo ci) {
        ModParticles.textureAtlasUpload((TextureAtlas)(Object)this);
    }
}
