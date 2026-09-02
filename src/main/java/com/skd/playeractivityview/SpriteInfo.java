package com.skd.playeractivityview;

import com.skd.playeractivityview.spritesets.SpriteSetPlayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class SpriteInfo {
    private final String name;
    private final int frames;
    private final int tickDelay;
    private TextureAtlasSprite sprite;
    private SpriteSetPlayer spriteSet;

    public SpriteInfo(String name, int frames, int tickDelay) {
        this.name = name;
        this.frames = frames;
        this.tickDelay = tickDelay;
    }

    public void setupSprites(TextureAtlas atlas) {
        sprite = atlas.getSprite(ResourceLocation.fromNamespaceAndPath("player_activity_view", "particle/" + name));
        if (frames > 0) {
            TextureAtlasSprite[] sprites = new TextureAtlasSprite[frames];
            for (int i = 0; i < frames; i++) {
                sprites[i] = atlas.getSprite(ResourceLocation.fromNamespaceAndPath("player_activity_view", "particle/" + name + i));
            }
            spriteSet = new SpriteSetPlayer(sprites, tickDelay);
        }
    }

    public TextureAtlasSprite getSprite() { return sprite; }
    public SpriteSetPlayer getSpriteSet() { return spriteSet; }
    public String getName() { return name; }
}
