package com.skd.playeractivity.spritesets;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;

public class SpriteSetPlayer implements net.minecraft.client.particle.SpriteSet {
    private final TextureAtlasSprite[] sprites;
    private final int tickDelay;

    public SpriteSetPlayer(TextureAtlasSprite[] sprites, int tickDelay) {
        this.sprites = sprites;
        this.tickDelay = tickDelay;
    }

    @Override
    public TextureAtlasSprite get(int age, int ticks) {
        if (sprites.length == 0) return null;
        int index = (age / Math.max(1, tickDelay)) % sprites.length;
        return sprites[index];
    }

    @Override
    public TextureAtlasSprite get(RandomSource random) {
        return sprites[random.nextInt(sprites.length)];
    }

    @Override
    public TextureAtlasSprite first() {
        if (sprites.length == 0) return null;
        return sprites[0];
    }
}
