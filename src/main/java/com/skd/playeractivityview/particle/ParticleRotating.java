package com.skd.playeractivityview.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public abstract class ParticleRotating extends SingleQuadParticle {
    public float rotationYaw;
    public float prevRotationYaw;
    public float rotationPitch;
    public float prevRotationPitch;
    public float rotationRoll;
    public float prevRotationRoll;
    private float brightness = 1.0F;
    private boolean dead = false;

    protected ParticleRotating(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, null);
        this.gravity = 0.0F;
        this.lifetime = 0;
        this.quadSize = 0.3F;
    }

    @Override
    public void tick() {
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
        prevRotationRoll = rotationRoll;
        super.tick();
    }

    @Override
    public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTickTime) {
        float yaw = Mth.lerp(partialTickTime, prevRotationYaw, rotationYaw);
        float pitch = Mth.lerp(partialTickTime, prevRotationPitch, rotationPitch);
        float roll = Mth.lerp(partialTickTime, prevRotationRoll, rotationRoll);
        Quaternionf rotation = new Quaternionf();
        rotation.rotationY(yaw * Mth.DEG_TO_RAD);
        rotation.rotateX(pitch * Mth.DEG_TO_RAD);
        rotation.rotateZ(roll * Mth.DEG_TO_RAD);
        Vec3 pos = camera.position();
        float x = (float)(Mth.lerp(partialTickTime, this.xo, this.x) - pos.x());
        float y = (float)(Mth.lerp(partialTickTime, this.yo, this.y) - pos.y());
        float z = (float)(Mth.lerp(partialTickTime, this.zo, this.z) - pos.z());
        this.extractRotatedQuadForParticle(particleTypeRenderState, rotation, x, y, z, partialTickTime);
    }

    protected void extractRotatedQuadForParticle(QuadParticleRenderState particleTypeRenderState, Quaternionf rotation, float x, float y, float z, float partialTickTime) {
        particleTypeRenderState.add(
            getLayer(),
            x,
            y,
            z,
            rotation.x,
            rotation.y,
            rotation.z,
            rotation.w,
            getQuadSize(partialTickTime),
            getU0(),
            getU1(),
            getV0(),
            getV1(),
            ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol),
            getLightCoords(partialTickTime)
        );
    }

    public void keepAlive() { this.age = 0; }

    @Override
    public void remove() { this.dead = true; }

    @Override
    public boolean isAlive() { return !this.dead && this.age < this.lifetime; }

    public void setBrightness(float b) { this.brightness = b; }

    public void setQuadSize(float size) { this.quadSize = size; }

    public void setAlpha(float a) { this.alpha = a; }

    @Override
    protected int getLightCoords(float partialTick) {
        int light = super.getLightCoords(partialTick);
        int sky = (light >> 16) & 0xFF;
        int block = (int)((light & 0xFF) * brightness);
        return (sky << 16) | (block & 0xFF);
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        if (sprite != null) {
            return SingleQuadParticle.Layer.bySprite(sprite);
        }
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }
}
