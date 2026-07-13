package com.skd.playeractivityview;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlas;

public class ModParticles {
    public static SpriteInfo inventory = add("inventory_", 3, 0);
    public static SpriteInfo chest = add("chest_", 3, 0);
    public static SpriteInfo crafting = add("crafting_", 3, 0);
    public static SpriteInfo escape = add("escape_menu_", 3, 0);
    public static SpriteInfo sign = add("sign", 0, 0);
    public static SpriteInfo book = add("book", 0, 0);
    public static SpriteInfo enchanting_table = add("enchanting_table", 0, 0);
    public static SpriteInfo anvil = add("anvil", 0, 0);
    public static SpriteInfo beacon = add("beacon", 0, 0);
    public static SpriteInfo brewing_stand = add("brewing_stand", 0, 0);
    public static SpriteInfo dispenser = add("dispenser", 0, 0);
    public static SpriteInfo furnace = add("furnace", 0, 0);
    public static SpriteInfo grindstone = add("grindstone", 0, 0);
    public static SpriteInfo hopper = add("hopper", 0, 0);
    public static SpriteInfo horse = add("horse", 0, 0);
    public static SpriteInfo loom = add("loom", 0, 0);
    public static SpriteInfo villager = add("villager", 0, 0);
    public static SpriteInfo command_block = add("command_block", 0, 0);
    public static SpriteInfo chat_idle = add("chat_idle_", 2, 6);
    public static SpriteInfo chat_typing = add("chat_typing_", 6, 2);
    public static SpriteInfo idle = add("idle");

    public static List<SpriteInfo> particles = new ArrayList<>();

    private static SpriteInfo add(String name) { return add(name, 0, 0); }

    private static SpriteInfo add(String name, int frames, int tickDelay) {
        SpriteInfo info = new SpriteInfo(name, frames, tickDelay);
        particles.add(info);
        return info;
    }

    public static void textureAtlasUpload(TextureAtlas atlas) {
        if (atlas.location().equals(TextureAtlas.LOCATION_PARTICLES)) {
            for (SpriteInfo info : particles) {
                info.setupSprites(atlas);
            }
        }
    }
}
