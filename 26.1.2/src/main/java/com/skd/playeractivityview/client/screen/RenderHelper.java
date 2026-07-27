package com.skd.playeractivityview.client.screen;

import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class RenderHelper {
    public static boolean useDynamicGUISystem() {
        return !ServerSyncedConfig.DYNAMIC_GUI_USE_OLD_SIMPLE.get();
    }

    public static byte[] compress(byte[] input) {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream(decompressedSize);
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

    public static void renderWithTooltipEnd() {
        if (PlayerActivity.getPlayerStatusManagerClient() == null) return;
        PlayerActivity.getPlayerStatusManagerClient().renderScreenCapture();
    }
}
