package com.skd.playeractivity.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;

public class ParticleDynamic extends ParticleRotating {
    private final ParticleRenderType renderType;
    private final float aspectRatio;

    public ParticleDynamic(ClientLevel level, double x, double y, double z, ParticleRenderType renderType, float aspectRatio) {
        super(level, x, y, z);
        this.renderType = renderType;
        this.aspectRatio = aspectRatio;
        this.gravity = 0.0F;
        this.quadSize = 1.0F;
    }

    @Override
    public ParticleRenderType getGroup() {
        return renderType;
    }
}
