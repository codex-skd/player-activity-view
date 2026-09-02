package com.skd.playeractivityview.client.screen;

import com.skd.playeractivityview.PlayerStatus;
import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.config.ConfigClient;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import com.skd.playeractivityview.mixin.client.NativeImageAccessorMixin;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class RenderHelper {

    public static boolean performingOwnRender = false;

    public static ByteBufferProcessor processor = new ByteBufferProcessor(buffer -> {
        ByteBuffer processed = compress(buffer);
        return processed;
    });

    public static void guiRender(GuiGraphics guiGraphics) {
        long gameTime = 0;
        if (Minecraft.getInstance().level != null) {
            gameTime = Minecraft.getInstance().level.getGameTime();
        }

        for (PlayerStatus playerStatus : PlayerActivity.getPlayerStatusManagerClient().lookupPlayerToStatus.values()) {
            ScreenData screenData = playerStatus.getScreenData();

            if ((screenData.getIsBufferReady().get() && screenData.needsNewRenderFromPixelData() && screenData.getTexturePixelData() != null && screenData.getGameTicksSinceLastScreenReceiveAndRender() + ConfigClient.TICK_RECEIVE_AND_RENDER_RATE_OF_GUI_UPDATES.get() < gameTime)) {
                screenData.markNeedsNewRenderFromPixelData(false);
                screenData.setGameTicksSinceLastScreenReceiveAndRender(gameTime);

                ScreenParticleRenderer.getInstance().checkSetup();

                if (playerStatus.getScreenData().getParticleRenderType() == null) {
                    playerStatus.getScreenData().initClient();
                }

                if (screenData.getImage() == null) {
                    screenData.setImage(new DynamicTexture(screenData.getWidth(), screenData.getHeight(), true));
                } else {
                    if (screenData.getImage().getPixels().getWidth() != screenData.getWidth() || screenData.getImage().getPixels().getHeight() != screenData.getHeight()) {
                        screenData.closeImage();
                        screenData.setImage(new DynamicTexture(screenData.getWidth(), screenData.getHeight(), true));
                    }
                }
                long nativeImagePixelMemoryAddress = ((NativeImageAccessorMixin)((Object)screenData.getImage().getPixels())).pixels();
                if (nativeImagePixelMemoryAddress != -1) {
                    MemoryUtil.memCopy(MemoryUtil.memAddress(screenData.getDecompressionBuffer()), nativeImagePixelMemoryAddress,
                            screenData.getWidth() * screenData.getHeight() * ScreenParticleRenderer.bytesPerPixel);
                }

                screenData.getImage().upload();

                playerStatus.getScreenData().getIsBufferReady().set(false);

            }
        }
    }

    public static void bindVanillaRenderTargetAndSetupProjectionMatrix() {
        Window window = Minecraft.getInstance().getWindow();
        Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, (float)((double)window.getWidth() / window.getGuiScale()), (float)((double)window.getHeight() / window.getGuiScale()), 0.0F, 1000.0F, PlayerActivity.instance().getFarPlane());
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }

    public static void unbindVanillaRenderTarget() {
        Minecraft.getInstance().getMainRenderTarget().unbindWrite();
    }

    public static boolean useDynamicGUISystem() {
        if (ServerSyncedConfig.dynamicGuiUseOldSimple()) return false;
        return true;
    }

    public static synchronized void renderWithTooltipEnd(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (!useDynamicGUISystem()) return;
        if (Minecraft.getInstance().level == null || Minecraft.getInstance().player == null) {
            return;
        }

        PlayerStatus playerStatusLocal = PlayerActivity.getPlayerStatusManagerClient().getStatusLocal();

        if (processor.hasProcessedBuffers()) {
            try {
                ByteBuffer result = processor.getProcessedBuffer();
                if (result != null) {
                    ScreenData screenDataLocal = playerStatusLocal.getScreenData();
                    screenDataLocal.setTexturePixelData(result);
                    PlayerActivity.getPlayerStatusManagerClient().sendScreenRenderData(playerStatusLocal);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        boolean needsScreenUpdate = playerStatusLocal.getScreenData().isNeedsNewRenderToPixelData();

        if (needsScreenUpdate && !processor.hasWork()) {

            playerStatusLocal.getScreenData().setNeedsNewRenderToPixelData(false);
            ScreenParticleRenderer.getInstance().checkSetup();
            unbindVanillaRenderTarget();
            ScreenParticleRenderer.getInstance().bind();

            RenderSystem.clear(16640, Minecraft.ON_OSX);

            if (Minecraft.getInstance().screen != null) {
                if (ServerSyncedConfig.dynamicGuiDisableBackground()) {
                    ScreenParticleRenderer.isRenderingParticleGUI = true;
                    ScreenParticleRenderer.isRenderingParticleGUI2 = true;
                }
                performingOwnRender = true;

                Minecraft.getInstance().screen.renderWithTooltip(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                performingOwnRender = false;
                ScreenParticleRenderer.isRenderingParticleGUI = false;
                ScreenParticleRenderer.isRenderingParticleGUI2 = false;
            }

            ScreenParticleRenderer.getInstance().unbind();

            Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, (float)ScreenParticleRenderer.getInstance().widthScaledDown, (float)ScreenParticleRenderer.getInstance().heightScaledDown, 0.0F, 1000.0F, 21000.0F);
            RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);

            ScreenParticleRenderer.getInstance().bindScaledDown();

            RenderSystem.clear(16640, Minecraft.ON_OSX);

            double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
            if (ServerSyncedConfig.dynamicGuiShowEntireScreen()) {
                guiScale = 1;
            }
            int croppedWidth = (int) (ScreenParticleRenderer.getInstance().widthScaledDown * guiScale);
            int croppedHeight = (int) (ScreenParticleRenderer.getInstance().heightScaledDown * guiScale);

            int centerX = ScreenParticleRenderer.getInstance().width / 2;
            int centerY = ScreenParticleRenderer.getInstance().height / 2;
            int x1 = centerX - (croppedWidth / 2);
            int x2 = centerX + (croppedWidth / 2);
            int y1 = centerY - (croppedHeight / 2);
            int y2 = centerY + (croppedHeight / 2);
            float minU = (float)x1 / (float)ScreenParticleRenderer.getInstance().width;
            float maxU = (float)x2 / (float)ScreenParticleRenderer.getInstance().width;
            float minV = (float)y1 / (float)ScreenParticleRenderer.getInstance().height;
            float maxV = (float)y2 / (float)ScreenParticleRenderer.getInstance().height;

            x1 = 0;
            x2 = ScreenParticleRenderer.getInstance().widthScaledDown;
            y1 = 0;
            y2 = ScreenParticleRenderer.getInstance().heightScaledDown;

            ScreenParticleRenderer.getInstance().innerBlitCustomShaderHorizontal(pGuiGraphics.pose()
                , x1, x2
                , y1, y2
                , 0
                , minU, maxU, minV, maxV);

            ScreenParticleRenderer.getInstance().innerBlitCustomShaderVertical(pGuiGraphics.pose()
                    , 0, ScreenParticleRenderer.getInstance().widthScaledDown
                    , 0, ScreenParticleRenderer.getInstance().heightScaledDown
                    , 0
                    , 0, 1, 0, 1);

            ByteBuffer pixelBuffer = getPixelDataFromFrameBuffer();

            processor.submitForProcessing(pixelBuffer);

            ScreenParticleRenderer.getInstance().unbindScaledDown();

            bindVanillaRenderTargetAndSetupProjectionMatrix();
        }
    }

    public static ByteBuffer compress(ByteBuffer inputBuffer) {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);

        byte[] inputBytes = new byte[inputBuffer.remaining()];
        inputBuffer.get(inputBytes);
        deflater.setInput(inputBytes);
        deflater.finish();

        ByteBuffer outputBuffer = ByteBuffer.allocateDirect(inputBytes.length + 512);
        byte[] temp = new byte[1024];

        while (!deflater.finished()) {
            int compressedBytes = deflater.deflate(temp);
            if (outputBuffer.remaining() < compressedBytes) {
                ByteBuffer newBuffer = ByteBuffer.allocateDirect(outputBuffer.capacity() * 2);
                outputBuffer.flip();
                newBuffer.put(outputBuffer);
                outputBuffer = newBuffer;
            }
            outputBuffer.put(temp, 0, compressedBytes);
        }
        deflater.end();

        outputBuffer.flip();
        inputBuffer.flip();
        return outputBuffer;
    }

    public static ByteBuffer decompress(ScreenData screenData, ByteBuffer compressedBuffer, int expectedSize) throws Exception {
        Inflater inflater = new Inflater();

        byte[] compressedBytes = new byte[compressedBuffer.remaining()];
        compressedBuffer.get(compressedBytes);
        inflater.setInput(compressedBytes);

        ByteBuffer decompressionBuffer = screenData.getDecompressionBuffer();

        if (decompressionBuffer == null) {
            decompressionBuffer = MemoryUtil.memAlloc(expectedSize);
            screenData.setDecompressionBuffer(decompressionBuffer);
        } else {
            decompressionBuffer.clear();
        }

        byte[] temp = new byte[1024];

        while (!inflater.finished()) {
            int decompressedBytes = inflater.inflate(temp);

            if (decompressionBuffer.remaining() < decompressedBytes) {
                int newCapacity = Math.max(decompressionBuffer.capacity() * 2, decompressionBuffer.capacity() + decompressedBytes);
                ByteBuffer newBuffer = MemoryUtil.memAlloc(newCapacity);
                decompressionBuffer.flip();
                newBuffer.put(decompressionBuffer);
                MemoryUtil.memFree(decompressionBuffer);
                decompressionBuffer = newBuffer;
                screenData.setDecompressionBuffer(decompressionBuffer);
            }

            decompressionBuffer.put(temp, 0, decompressedBytes);
        }
        inflater.end();

        decompressionBuffer.flip();

        return decompressionBuffer;
    }

    public static ByteBuffer getPixelDataFromFrameBuffer() {
        int width = ScreenParticleRenderer.getInstance().widthScaledDown;
        int height = ScreenParticleRenderer.getInstance().heightScaledDown;

        ByteBuffer pixelBuffer = ByteBuffer.allocateDirect(width * height * ScreenParticleRenderer.bytesPerPixel);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelBuffer);

        return pixelBuffer;
    }
}
