package com.skd.playeractivityview.client.screen;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.PlayerStatus;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

public class RenderHelper {
    private static final int MAX_CAPTURE_DIMENSION = 192;

    public static boolean useDynamicGUISystem() {
        return !ServerSyncedConfig.DYNAMIC_GUI_USE_OLD_SIMPLE.get();
    }

    public static byte[] compress(byte[] input) {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream(input.length);
        byte[] buf = new byte[8192];
        while (!deflater.finished()) {
            int count = deflater.deflate(buf);
            bos.write(buf, 0, count);
        }
        deflater.end();
        return bos.toByteArray();
    }

    public static ByteBuffer decompress(ScreenData screenData, ByteBuffer compressed, int decompressedSize) {
        byte[] input = new byte[compressed.remaining()];
        compressed.get(input);
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream(decompressedSize);
        byte[] buf = new byte[8192];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buf);
                bos.write(buf, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        inflater.end();
        byte[] decompressed = bos.toByteArray();
        ByteBuffer result = ByteBuffer.allocateDirect(decompressedSize);
        result.put(decompressed, 0, Math.min(decompressed.length, decompressedSize));
        result.flip();
        return result;
    }

    /**
     * Kept as a no-op hook target for ScreenExtractRenderStateWithTooltipMixin; actual capture now runs
     * from RenderFrameEvent.Post (captureScreenIfNeeded) since GUI extraction no longer draws synchronously.
     */
    public static void renderWithTooltipEnd() {
    }

    /**
     * Grabs the current frame's main render target, downscales it on the CPU and queues it for sending.
     * Must run after the frame (including GUI) has been fully drawn, i.e. from RenderFrameEvent.Post.
     */
    public static void captureScreenIfNeeded() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (PlayerActivity.getPlayerStatusManagerClient() == null) return;
        // ServerSyncedConfig (used by useDynamicGUISystem) is only populated after connecting to a server,
        // so it must not be touched before the level/player null-check above.
        if (!useDynamicGUISystem()) return;
        PlayerStatus local = PlayerActivity.getPlayerStatusManagerClient().getStatusLocal();
        ScreenData screenData = local.getScreenData();
        if (!screenData.isNeedsNewRenderToPixelData()) return;
        screenData.setNeedsNewRenderToPixelData(false);

        RenderTarget target = mc.getMainRenderTarget();
        Screenshot.takeScreenshot(target, 1, image -> {
            try {
                onScreenshotCaptured(image, screenData, local);
            } finally {
                image.close();
            }
        });
    }

    private static void onScreenshotCaptured(NativeImage image, ScreenData screenData, PlayerStatus local) {
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();
        if (srcWidth <= 0 || srcHeight <= 0) return;
        int outWidth = srcWidth;
        int outHeight = srcHeight;
        if (Math.max(outWidth, outHeight) > MAX_CAPTURE_DIMENSION) {
            if (outWidth >= outHeight) {
                outHeight = Math.max(1, outHeight * MAX_CAPTURE_DIMENSION / outWidth);
                outWidth = MAX_CAPTURE_DIMENSION;
            } else {
                outWidth = Math.max(1, outWidth * MAX_CAPTURE_DIMENSION / outHeight);
                outHeight = MAX_CAPTURE_DIMENSION;
            }
        }

        byte[] packed = new byte[outWidth * outHeight * 4];
        int idx = 0;
        for (int y = 0; y < outHeight; y++) {
            int srcY = Math.min(srcHeight - 1, y * srcHeight / outHeight);
            for (int x = 0; x < outWidth; x++) {
                int srcX = Math.min(srcWidth - 1, x * srcWidth / outWidth);
                packPixel(packed, idx, image.getPixel(srcX, srcY));
                idx += 4;
            }
        }

        byte[] compressed = compress(packed);
        ScreenParticleRenderer.getInstance().widthScaledDown = outWidth;
        ScreenParticleRenderer.getInstance().heightScaledDown = outHeight;
        screenData.setUncompressedSize(packed.length);
        screenData.setTexturePixelData(ByteBuffer.wrap(compressed));
        PlayerActivity.getPlayerStatusManagerClient().sendScreenRenderData(local);
    }

    private static void packPixel(byte[] out, int offset, int argb) {
        out[offset] = (byte) (argb >>> 24);
        out[offset + 1] = (byte) (argb >>> 16);
        out[offset + 2] = (byte) (argb >>> 8);
        out[offset + 3] = (byte) argb;
    }

    private static int unpackPixel(ByteBuffer in, int offset) {
        int a = in.get(offset) & 0xFF;
        int r = in.get(offset + 1) & 0xFF;
        int g = in.get(offset + 2) & 0xFF;
        int b = in.get(offset + 3) & 0xFF;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Builds/updates the GPU texture used to render a remote player's live screen mirror from freshly
     * decompressed pixel bytes (ARGB, 4 bytes/pixel, produced by {@link #packPixel}).
     */
    public static void updateScreenTexture(ScreenData screenData, ByteBuffer rgba, int width, int height, UUID owner) {
        if (width <= 0 || height <= 0 || rgba == null || rgba.remaining() < width * height * 4) return;

        DynamicTexture texture = screenData.getImage();
        if (texture == null || texture.getPixels().getWidth() != width || texture.getPixels().getHeight() != height) {
            if (texture != null) texture.close();
            Identifier id = Identifier.fromNamespaceAndPath(PlayerActivity.MODID, "dynamic_screen/" + owner);
            final Identifier labelId = id;
            texture = new DynamicTexture(labelId::toString, width, height, false);
            Minecraft.getInstance().getTextureManager().register(id, texture);
            screenData.setImage(texture);
            screenData.setTextureId(id);
        }

        NativeImage pixels = texture.getPixels();
        int base = rgba.position();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int offset = base + (x + y * width) * 4;
                pixels.setPixel(x, y, unpackPixel(rgba, offset));
            }
        }
        texture.upload();
    }
}
