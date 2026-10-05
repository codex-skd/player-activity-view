package com.skd.playeractivityview.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;

/**
 * Renders a remote player's mirrored screen as a quad textured with that player's live DynamicTexture.
 *
 * <p>On 1.21.1 {@code AbstractTexture#getId()} returns the raw GL texture id (int), so a per-instance
 * {@link RenderType} cannot be built from a ResourceLocation. Instead the per-player
 * {@link ParticleRenderType} created by {@code ScreenData#initClient()} is reused: its
 * {@code begin()} resolves the current DynamicTexture id at render time, so it survives the texture
 * being replaced on resize.
 *
 * <p>UVs are overridden to the full 0..1 range because the source is a standalone texture, not an
 * atlas sprite — {@code TextureSheetParticle#sprite} is never assigned here and would be null.
 */
public class ParticleDynamic extends ParticleRotating {
    private final ParticleRenderType renderType;
    private final float aspectRatio;

    public ParticleDynamic(ClientLevel level, double x, double y, double z, ParticleRenderType renderType, float aspectRatio) {
        super(level, x, y, z);
        this.renderType = renderType;
        this.aspectRatio = aspectRatio;
        this.gravity = 0.0F;
        this.quadSize = 1.0F;
        float clamped = Mth.clamp(aspectRatio, 1.0F / 3.0F, 3.0F);
        this.quadScaleX = clamped;
        this.quadScaleY = 1.0F / clamped;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return renderType;
    }

    @Override
    protected float getU0() { return 0.0F; }

    @Override
    protected float getU1() { return 1.0F; }

    // glReadPixels reads the framebuffer bottom-row first, and glTexImage2D stores that same first
    // row at v=0, so the mirrored screen ends up stored with its bottom at v=0. Minecraft's GUI
    // projection flips Y when drawing into the framebuffer, which means the buffer's v=0 row is the
    // bottom of the screen. Sampling it at the top of the quad would render the mirror upside down,
    // so V is inverted here to put it back the right way up.
    @Override
    protected float getV0() { return 1.0F; }

    @Override
    protected float getV1() { return 0.0F; }

    public float getAspectRatio() { return aspectRatio; }
}
