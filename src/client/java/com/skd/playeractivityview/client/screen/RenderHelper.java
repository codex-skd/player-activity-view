package com.skd.playeractivityview.client.screen;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.PlayerActivityClient;
import com.skd.playeractivityview.PlayerStatus;
import com.skd.playeractivityview.PlayerStatusClient;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import com.skd.playeractivityview.mixin.client.AbstractContainerScreenAccessorMixin;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenderHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger("player_activity_view/screen");
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
        if (PlayerActivityClient.getPlayerStatusManagerClient() == null) return;
        // ServerSyncedConfig (used by useDynamicGUISystem) is only populated after connecting to a server,
        // so it must not be touched before the level/player null-check above.
        if (!useDynamicGUISystem()) return;
        PlayerStatusClient local = (PlayerStatusClient) PlayerActivityClient.getPlayerStatusManagerClient().getStatusLocal();
        ScreenData screenData = local.getScreenData();
        if (!screenData.isNeedsNewRenderToPixelData()) return;
        screenData.setNeedsNewRenderToPixelData(false);
        LOGGER.info("[capture] triggered, screen={}", mc.gui.screen() == null ? "null" : mc.gui.screen().getClass().getSimpleName());

        ScreenRectangle guiBounds = computeGuiPanelBounds(mc);
        LOGGER.info("[capture] guiBounds={}", guiBounds);
        RenderTarget target = mc.gameRenderer.mainRenderTarget();
        Screenshot.takeScreenshot(target, 1, image -> {
            try {
                onScreenshotCaptured(image, screenData, local, guiBounds, mc.getWindow().getGuiScale());
            } catch (Throwable t) {
                LOGGER.error("[capture] onScreenshotCaptured threw", t);
            } finally {
                image.close();
            }
        });
    }

    /**
     * How many of the most recent message lines to include in the dedicated ChatScreen crop, on top
     * of the input field. Mirrored chat is unreadable if the whole scrollback is captured, so the
     * crop is intentionally kept to the input area plus a handful of recent lines.
     */
    private static final int CHAT_CROP_MESSAGE_LINES = 4;

    /**
     * Cheaply re-extracts the current screen's layout (no GPU work) to find the on-screen pixel bounds
     * of its GUI elements, so the capture can be cropped to just the menu panel instead of the whole
     * frame (world + dark background included). Returns null if the screen has no boundable elements.
     */
    private static void expandBox(int[] box, ScreenRectangle r) {
        if (r == null) return;
        box[0] = Math.min(box[0], r.left());
        box[1] = Math.min(box[1], r.top());
        box[2] = Math.max(box[2], r.right());
        box[3] = Math.max(box[3], r.bottom());
    }

    private static ScreenRectangle computeGuiPanelBounds(Minecraft mc) {
        if (mc.gui.screen() == null) return null;
        int mouseX = (int) (mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth());
        int mouseY = (int) (mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight());
        GuiRenderState guiRenderState = new GuiRenderState();
        GuiGraphicsExtractor extractor = new GuiGraphicsExtractor(mc, guiRenderState, mouseX, mouseY);
        float partialTick = mc.getDeltaTracker().getGameTimeDeltaPartialTick(true);
        mc.gui.screen().extractRenderState(extractor, mouseX, mouseY, partialTick);

        int[] box = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        guiRenderState.forEachElement(element -> expandBox(box, element.bounds()), GuiRenderState.TraverseRange.ALL);
        guiRenderState.forEachItem(item -> expandBox(box, item.bounds()));
        guiRenderState.forEachText(text -> expandBox(box, text.bounds()));
        guiRenderState.forEachPictureInPicture(pip -> expandBox(box, pip.bounds()));
        if (box[0] > box[2] || box[1] > box[3]) return null;

        if (mc.gui.screen() instanceof ChatScreen) {
            ScreenRectangle chatBounds = computeChatScreenBounds(mc);
            if (chatBounds != null) return chatBounds;
        }

        if (mc.gui.screen() instanceof AbstractContainerScreen<?> containerScreen) {
            AbstractContainerScreenAccessorMixin accessor = (AbstractContainerScreenAccessorMixin) containerScreen;
            int left = accessor.playerActivityView$getLeftPos();
            int top = accessor.playerActivityView$getTopPos();
            int width = accessor.playerActivityView$getImageWidth();
            int height = accessor.playerActivityView$getImageHeight();
            if (width > 0 && height > 0) {
                // In 26.2 the container background is extracted as a full-screen element, so the
                // element box spans the whole window. Clamp to the deterministic panel rectangle
                // instead of unioning it, so the mirror shows just the inventory panel.
                box[0] = Math.max(box[0], left);
                box[1] = Math.max(box[1], top);
                box[2] = Math.min(box[2], left + width);
                box[3] = Math.min(box[3], top + height);
            }
        }

        int padding = 8;
        int guiWidth = mc.getWindow().getGuiScaledWidth();
        int guiHeight = mc.getWindow().getGuiScaledHeight();
        int left = Math.max(0, box[0] - padding);
        int top = Math.max(0, box[1] - padding);
        int right = Math.min(guiWidth, box[2] + padding);
        int bottom = Math.min(guiHeight, box[3] + padding);
        if (right <= left || bottom <= top) return null;
        return new ScreenRectangle(left, top, right - left, bottom - top);
    }

    /**
     * Dedicated crop for ChatScreen: the generic bounding box of the chat's GUI elements spans the
     * full-width message scrollback (most of the screen height), which is unreadable once squeezed
     * into the small mirrored panel. Instead bound the capture to the input field area plus a handful
     * of the most recent message lines, matching where the vanilla chat input and lines actually render:
     * the input is an EditBox at (4, height-12) spanning the window width, and the message log ends at
     * (height-40)/chatScale with entries of height 9*(lineSpacing+1)*chatScale (see ChatScreen.init and
     * ChatComponent.extractRenderState).
     */
    private static ScreenRectangle computeChatScreenBounds(Minecraft mc) {
        int guiWidth = mc.getWindow().getGuiScaledWidth();
        int guiHeight = mc.getWindow().getGuiScaledHeight();
        double scale = Math.max(mc.options.chatScale().get(), 0.1);
        int entryHeight = (int) Math.round(scale * 9.0 * (mc.options.chatLineSpacing().get() + 1.0));
        int messagesTop = guiHeight - 40 - CHAT_CROP_MESSAGE_LINES * entryHeight;
        int top = Math.max(0, Math.min(guiHeight - 14, messagesTop) - 2);
        int left = 2;
        int right = guiWidth - 2;
        if (right <= left || top >= guiHeight) return null;
        return new ScreenRectangle(left, top, right - left, guiHeight - top);
    }

    private static void onScreenshotCaptured(NativeImage image, ScreenData screenData, PlayerStatusClient local, ScreenRectangle guiBounds, double guiScale) {
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();
        if (srcWidth <= 0 || srcHeight <= 0) return;

        int cropX0 = 0, cropY0 = 0, cropWidth = srcWidth, cropHeight = srcHeight;
        if (guiBounds != null) {
            cropX0 = clamp((int) Math.round(guiBounds.left() * guiScale), 0, srcWidth - 1);
            cropY0 = clamp((int) Math.round(guiBounds.top() * guiScale), 0, srcHeight - 1);
            int cropX1 = clamp((int) Math.round(guiBounds.right() * guiScale), cropX0 + 1, srcWidth);
            int cropY1 = clamp((int) Math.round(guiBounds.bottom() * guiScale), cropY0 + 1, srcHeight);
            cropWidth = cropX1 - cropX0;
            cropHeight = cropY1 - cropY0;
        }

        int outWidth = cropWidth;
        int outHeight = cropHeight;
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
            int srcY = cropY0 + Math.min(cropHeight - 1, y * cropHeight / outHeight);
            for (int x = 0; x < outWidth; x++) {
                int srcX = cropX0 + Math.min(cropWidth - 1, x * cropWidth / outWidth);
                packPixel(packed, idx, image.getPixel(srcX, srcY));
                idx += 4;
            }
        }

        byte[] compressed = compress(packed);
        ScreenParticleRenderer.getInstance().widthScaledDown = outWidth;
        ScreenParticleRenderer.getInstance().heightScaledDown = outHeight;
        screenData.setUncompressedSize(packed.length);
        screenData.setTexturePixelData(ByteBuffer.wrap(compressed));
        LOGGER.info("[capture] srcSize={}x{} crop=({},{})+{}x{} out={}x{} packedBytes={} compressedBytes={}",
            srcWidth, srcHeight, cropX0, cropY0, cropWidth, cropHeight, outWidth, outHeight, packed.length, compressed.length);
        PlayerActivityClient.getPlayerStatusManagerClient().sendScreenRenderData(local);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
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
        if (width <= 0 || height <= 0 || rgba == null || rgba.remaining() < width * height * 4) {
            LOGGER.warn("[texture] rejected update owner={} width={} height={} remaining={}",
                owner, width, height, rgba == null ? -1 : rgba.remaining());
            return;
        }

        DynamicTexture texture = screenData.getImage();
        boolean created = texture == null || texture.getPixels().getWidth() != width || texture.getPixels().getHeight() != height;
        if (created) {
            if (texture != null) texture.close();
            Identifier id = Identifier.fromNamespaceAndPath(PlayerActivity.MODID, "dynamic_screen/" + owner);
            final Identifier labelId = id;
            texture = new DynamicTexture(labelId::toString, width, height, false);
            Minecraft.getInstance().getTextureManager().register(id, texture);
            screenData.setImage(texture);
            screenData.setTextureId(id);
            LOGGER.info("[texture] created owner={} id={} {}x{}", owner, id, width, height);
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
        if (created) LOGGER.info("[texture] first upload done owner={} samplePixel(0,0)=0x{}", owner, Integer.toHexString(pixels.getPixel(0, 0)));
    }
}
