package com.skd.playeractivityview.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.world.item.ItemStack;

public class ParticleItem extends Particle {
    public ParticleItem(ClientLevel level, float size, ItemStack stack, Object renderBuffers,
                        Object dispatcher, float fromX, float fromY, float fromZ,
                        float toX, float toY, float toZ) {
        super(level, fromX, fromY, fromZ);
        this.lifetime = 6;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
    }

    @Override
    public void tick() {
        super.tick();
        if (age >= lifetime) remove();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
    }
}
