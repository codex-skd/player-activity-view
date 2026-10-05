package com.skd.playeractivityview.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ParticleStatic extends ParticleRotating {
    private final float brightnessVal;

    public ParticleStatic(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        this(level, x, y, z, sprite, 1.0F);
    }

    public ParticleStatic(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite, float brightness) {
        super(level, x, y, z);
        this.brightnessVal = brightness;
        this.setSprite(sprite);
    }

    @Override
    public void tick() { super.tick(); }

    @Override
    protected int getLightColor(float partialTick) {
        int light = super.getLightColor(partialTick);
        int sky = (light >> 16) & 0xFF;
        int block = (int)((light & 0xFF) * brightnessVal);
        return (sky << 16) | (Math.min(block, 240) & 0xFF);
    }
}
