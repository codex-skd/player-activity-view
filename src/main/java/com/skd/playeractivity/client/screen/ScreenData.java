package com.skd.playeractivity.client.screen;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class ScreenData {
    private ByteBuffer texturePixelData;
    private byte[] texturePixelDataPartial;
    private DynamicTexture image;
    private int width;
    private int height;
    private ParticleRenderType particleRenderType;
    private final AtomicBoolean isBufferReady = new AtomicBoolean(false);
    private long gameTicksSinceLastScreenSend = 0L;
    private Screen lastScreen;
    private boolean needsNewRenderToPixelData = false;
    private int lastIndexReceived = 0;
    private long gameTicksSinceFirstPacket = 0L;

    public ByteBuffer getTexturePixelData() { return texturePixelData; }
    public void setTexturePixelData(ByteBuffer buf) { this.texturePixelData = buf; }
    public byte[] getTexturePixelDataPartial() { return texturePixelDataPartial; }
    public void setTexturePixelDataPartial(byte[] data) { this.texturePixelDataPartial = data; }
    public DynamicTexture getImage() { return image; }
    public void setImage(DynamicTexture t) { this.image = t; }
    public int getWidth() { return width; }
    public void setWidth(int w) { this.width = w; }
    public int getHeight() { return height; }
    public void setHeight(int h) { this.height = h; }
    public ParticleRenderType getParticleRenderType() { return particleRenderType; }
    public void setParticleRenderType(ParticleRenderType t) { this.particleRenderType = t; }
    public AtomicBoolean getIsBufferReady() { return isBufferReady; }
    public long getGameTicksSinceLastScreenSend() { return gameTicksSinceLastScreenSend; }
    public void setGameTicksSinceLastScreenSend(long t) { this.gameTicksSinceLastScreenSend = t; }
    public Screen getLastScreen() { return lastScreen; }
    public void setLastScreen(Screen s) { this.lastScreen = s; }
    public boolean isNeedsNewRenderToPixelData() { return needsNewRenderToPixelData; }
    public void setNeedsNewRenderToPixelData(boolean v) { this.needsNewRenderToPixelData = v; }
    public int getLastIndexReceived() { return lastIndexReceived; }
    public void setLastIndexReceived(int i) { this.lastIndexReceived = i; }
    public long getGameTicksSinceFirstPacket() { return gameTicksSinceFirstPacket; }
    public void setGameTicksSinceFirstPacket(long t) { this.gameTicksSinceFirstPacket = t; }

    public void markNeedsNewRenderFromPixelData(boolean v) { this.needsNewRenderToPixelData = v; }
}
