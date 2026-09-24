package com.skd.playeractivityview.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ParticleStaticPartial extends ParticleStatic {
    private final int subSizeX;
    private final int subSizeY;

    public ParticleStaticPartial(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite, float brightness, int subSizeX, int subSizeY) {
        super(level, x, y, z, sprite, brightness);
        this.subSizeX = subSizeX;
        this.subSizeY = subSizeY;
    }

    @Override
    protected float getU1() {
        float texWidth = sprite.contents().width();
        return sprite.getU0() + (subSizeX / texWidth) * (sprite.getU1() - sprite.getU0());
    }

    @Override
    protected float getV1() {
        float texHeight = sprite.contents().height();
        return sprite.getV0() + (subSizeY / texHeight) * (sprite.getV1() - sprite.getV0());
    }
}
