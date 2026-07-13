package com.skd.playeractivityview.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;

public class ParticleEngineCustom implements PreparableReloadListener {
    private final List<Particle> particles = new ArrayList<>();
    private ClientLevel level;
    private final TextureManager textureManager;

    public ParticleEngineCustom(ClientLevel level, TextureManager textureManager) {
        this.level = level;
        this.textureManager = textureManager;
    }

    public void add(Particle particle) {
        particles.add(particle);
    }

    public void tick() {
        List<Particle> toRemove = new ArrayList<>();
        for (Particle p : particles) {
            p.tick();
            if (!p.isAlive()) toRemove.add(p);
        }
        particles.removeAll(toRemove);
    }

    public void render(Camera camera, float partialTick) {
    }

    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.SharedState currentReload, Executor taskExecutor, PreparableReloadListener.PreparationBarrier preparationBarrier, Executor reloadExecutor) {
        return preparationBarrier.wait(Unit.INSTANCE).thenRun(() -> {});
    }

    public void setLevel(ClientLevel level) { this.level = level; }
}
