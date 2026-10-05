package com.skd.playeractivityview.client.screen;

import com.skd.playeractivityview.PlayerStatus;
import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.config.ConfigClient;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import com.skd.playeractivityview.mixin.client.AbstractContainerScreenAccessorMixin;
import com.skd.playeractivityview.mixin.client.NativeImageAccessorMixin;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
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

            // Crop to where the screen actually drew its panel instead of blindly centring a
            // 256x256 box on the window, which cut off rows at the top of tall-ish panels. The crop
            // stays square (the target and the network payload are fixed 256x256) but grows to
            // contain the whole panel and re-centres on it, so wide-and-short panels like the pause
            // menu also sit inside the circular vignette instead of having their corners eaten.
            int fbWidth = ScreenParticleRenderer.getInstance().width;
            int fbHeight = ScreenParticleRenderer.getInstance().height;
            int[] crop = computeCropFrameBufferRect(
                    Minecraft.getInstance().screen,
                    (int) (ScreenParticleRenderer.getInstance().widthScaledDown * guiScale),
                    fbWidth, fbHeight);

            float minU = (float) crop[0] / (float) fbWidth;
            float maxU = (float) crop[2] / (float) fbWidth;
            float minV = (float) crop[1] / (float) fbHeight;
            float maxV = (float) crop[3] / (float) fbHeight;

            int x1 = 0;
            int x2 = ScreenParticleRenderer.getInstance().widthScaledDown;
            int y1 = 0;
            int y2 = ScreenParticleRenderer.getInstance().heightScaledDown;

            ScreenParticleRenderer.getInstance().innerBlitCustomShaderHorizontal(pGuiGraphics.pose()
                , x1, x2
                , y1, y2
                , 0
                , minU, maxU, minV, maxV);

            // Second pass must target a different framebuffer than the one it samples.
            ScreenParticleRenderer.getInstance().bindFinal();
            RenderSystem.clear(16640, Minecraft.ON_OSX);

            ScreenParticleRenderer.getInstance().innerBlitCustomShaderVertical(pGuiGraphics.pose()
                    , 0, ScreenParticleRenderer.getInstance().widthScaledDown
                    , 0, ScreenParticleRenderer.getInstance().heightScaledDown
                    , 0
                    , 0, 1, 0, 1
                    , toTargetRect(crop,
                            ScreenParticleRenderer.getInstance().widthScaledDown,
                            ScreenParticleRenderer.getInstance().heightScaledDown));

            ByteBuffer pixelBuffer = getPixelDataFromFrameBuffer();

            processor.submitForProcessing(pixelBuffer);

            ScreenParticleRenderer.getInstance().unbindFinal();

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

    /**
     * Crop to take out of the full-resolution screen capture, in framebuffer pixels:
     * {@code [cropX1, cropY1, cropX2, cropY2, panelX1, panelY1, panelX2, panelY2]}.
     *
     * <p>The mirror target is a fixed square, so the crop is a square. It is at least
     * {@code minSide} (to keep a little context around small panels) and grows to contain the whole
     * panel rect, then re-centres on the panel rather than on the window. Without this the crop was
     * always a 256x256 GUI-unit box centred on the window, which clipped rows off tall panels and
     * pushed wide panels outside the soft mask.
     *
     * <p>The panel rect comes from {@link net.minecraft.client.gui.screens.inventory.AbstractContainerScreen}'s
     * own layout fields, which cover the inventory, chest, furnace, crafting table and the rest of
     * the container screens. When the screen is not one of those the panel rect falls back to the
     * crop itself, so the mask covers the whole image.
     */
    private static int[] computeCropFrameBufferRect(Screen screen, int minSide, int fbWidth, int fbHeight) {
        int side = minSide;
        int centerX = fbWidth / 2;
        int centerY = fbHeight / 2;
        int panelLeft = 0;
        int panelTop = 0;
        int panelRight = fbWidth;
        int panelBottom = fbHeight;

        if (screen instanceof AbstractContainerScreen<?> container) {
            AbstractContainerScreenAccessorMixin accessor = (AbstractContainerScreenAccessorMixin) container;
            int guiScale = (int) Minecraft.getInstance().getWindow().getGuiScale();
            int padding = 6;
            int left = (accessor.playerActivityView$getLeftPos() - padding) * guiScale;
            int top = (accessor.playerActivityView$getTopPos() - padding) * guiScale;
            int right = (accessor.playerActivityView$getLeftPos() + accessor.playerActivityView$getImageWidth() + padding) * guiScale;
            int bottom = (accessor.playerActivityView$getTopPos() + accessor.playerActivityView$getImageHeight() + padding) * guiScale;

            if (right > left && bottom > top) {
                centerX = (left + right) / 2;
                centerY = (top + bottom) / 2;
                side = Math.max(side, Math.max(right - left, bottom - top));
                panelLeft = left;
                panelTop = top;
                panelRight = right;
                panelBottom = bottom;
            }
        }

        side = Math.min(side, Math.min(fbWidth, fbHeight));

        int x1 = Math.max(0, centerX - side / 2);
        int y1 = Math.max(0, centerY - side / 2);
        int x2 = Math.min(fbWidth, centerX + side / 2);
        int y2 = Math.min(fbHeight, centerY + side / 2);
        return new int[] { x1, y1, x2, y2, panelLeft, panelTop, panelRight, panelBottom };
    }

    /**
     * Maps the panel rect from framebuffer space into the mirror target's pixel space, so the shader's
     * soft mask can hug the panel. Framebuffer Y and target Y both grow upwards (the vertical blit
     * samples {@code minV} at the bottom of the quad), so this is a plain scale with no flip.
     */
    private static float[] toTargetRect(int[] crop, int targetWidth, int targetHeight) {
        double spanX = crop[2] - crop[0];
        double spanY = crop[3] - crop[1];
        if (spanX <= 0 || spanY <= 0) {
            return new float[] { 0.0F, 0.0F, targetWidth, targetHeight };
        }
        double sx = targetWidth / spanX;
        double sy = targetHeight / spanY;
        float x1 = (float) ((crop[4] - crop[0]) * sx);
        float y1 = (float) ((crop[5] - crop[1]) * sy);
        float x2 = (float) ((crop[6] - crop[0]) * sx);
        float y2 = (float) ((crop[7] - crop[1]) * sy);
        // Outside the target means the panel did not survive clamping; fall back to the full image.
        if (x2 - x1 < 1.0F || y2 - y1 < 1.0F
            || x2 < 0.0F || y2 < 0.0F || x1 > targetWidth || y1 > targetHeight) {
            return new float[] { 0.0F, 0.0F, targetWidth, targetHeight };
        }
        return new float[] { x1, y1, x2, y2 };
    }

    public static ByteBuffer getPixelDataFromFrameBuffer() {
        int width = ScreenParticleRenderer.getInstance().widthScaledDown;
        int height = ScreenParticleRenderer.getInstance().heightScaledDown;

        ByteBuffer pixelBuffer = ByteBuffer.allocateDirect(width * height * ScreenParticleRenderer.bytesPerPixel);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelBuffer);

        return pixelBuffer;
    }
}
