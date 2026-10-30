package com.skd.playeractivityview;

import java.util.HashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;

public class FakePlayerHelper {
    private static final HashMap<String, Boolean> cache = new HashMap<>();

    public static boolean isFakePlayer(Player player) {
        String className = player.getClass().getName();
        if (cache.containsKey(className)) return cache.get(className);
        boolean result = false;
        if (className.contains("fake") || className.contains("Fake")) {
            result = true;
        } else {
            if (FabricLoader.getInstance().isModLoaded("fabric-api")) {
                try {
                    Class<?> clazz = Class.forName("net.fabricmc.fabric.api.entity.FakePlayer");
                    result = clazz.isInstance(player);
                } catch (Exception e) {}
            }
        }
        cache.put(className, result);
        return result;
    }
}
