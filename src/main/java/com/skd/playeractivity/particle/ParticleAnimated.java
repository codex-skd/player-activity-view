package com.skd.playeractivity.particle;

import com.skd.playeractivity.spritesets.SpriteSetPlayer;
import net.minecraft.client.multiplayer.ClientLevel;

public class ParticleAnimated extends ParticleRotating {
    private final SpriteSetPlayer spriteSet;

    public ParticleAnimated(ClientLevel level, double x, double y, double z, SpriteSetPlayer spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
    }

    @Override
    public void tick() {
        super.tick();
        if (spriteSet != null) {
            setSprite(spriteSet.get(age, 0));
        }
    }
}
