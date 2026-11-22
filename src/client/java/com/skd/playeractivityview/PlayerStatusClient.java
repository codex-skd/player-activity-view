package com.skd.playeractivityview;

import com.skd.playeractivityview.client.screen.ScreenData;
import net.minecraft.client.particle.Particle;

/**
 * Client-side extension of {@link PlayerStatus} that holds the render-state particles (the
 * "active GUI" billboard and the idle indicator) and the live screen-mirror data. Kept in the
 * client source set because {@code net.minecraft.client.particle.Particle} and {@code ScreenData}
 * reference client-only classes that do not exist on a dedicated server.
 */
public class PlayerStatusClient extends PlayerStatus {
    private Particle particle;
    private Particle particleIdle;
    private ScreenData screenData;

    public PlayerStatusClient(PlayerGuiState playerGuiState, java.util.UUID uuid) {
        super(playerGuiState, uuid);
    }

    public Particle getParticle() { return particle; }
    public void setParticle(Particle p) { this.particle = p; }
    public Particle getParticleIdle() { return particleIdle; }
    public void setParticleIdle(Particle p) { this.particleIdle = p; }

    public ScreenData getScreenData() {
        if (screenData == null) screenData = new ScreenData();
        return screenData;
    }

    public void resetParticles() {
        if (particle != null) particle.remove();
        if (particleIdle != null) particleIdle.remove();
        particle = null;
        particleIdle = null;
    }
}
