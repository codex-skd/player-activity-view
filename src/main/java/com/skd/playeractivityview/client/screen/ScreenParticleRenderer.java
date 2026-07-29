package com.skd.playeractivityview.client.screen;

public class ScreenParticleRenderer {
    private static ScreenParticleRenderer instance;
    public int width;
    public int height;
    public int widthScaledDown;
    public int heightScaledDown;
    public static boolean isRenderingParticleGUI = false;

    public static ScreenParticleRenderer getInstance() {
        if (instance == null) instance = new ScreenParticleRenderer();
        return instance;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        resizeScaledDown(width, height);
    }

    public void resizeScaledDown(int width, int height) {
        this.width = width;
        this.height = height;
        widthScaledDown = Math.min(width, 512);
        heightScaledDown = (int)((float)height / (float)width * widthScaledDown);
    }

    public void renderBlurToScreen() {
    }
}
