package com.skd.playeractivity.client.screen;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.Deflater;
import net.minecraft.client.Minecraft;

public class ByteBufferProcessor {
    private static ByteBufferProcessor instance;
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static ByteBufferProcessor getInstance() {
        if (instance == null) instance = new ByteBufferProcessor();
        return instance;
    }

    public void compressPixelDataAsync(byte[] inputBytes, ByteBufferProcessorCallback callback) {
        executor.submit(() -> {
            try {
                byte[] compressed = compress(inputBytes);
                Minecraft.getInstance().execute(() -> callback.onComplete(compressed));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static byte[] compress(byte[] input) {
        Deflater deflater = new Deflater(Deflater.BEST_SPEED);
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

    public interface ByteBufferProcessorCallback {
        void onComplete(byte[] compressedData);
    }
}
