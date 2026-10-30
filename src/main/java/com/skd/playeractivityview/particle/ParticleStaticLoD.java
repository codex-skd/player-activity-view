package com.skd.playeractivityview.particle;

import com.skd.playeractivityview.spritesets.SpriteSetPlayer;
import net.minecraft.client.multiplayer.ClientLevel;

public class ParticleStaticLoD extends ParticleRotating {
    private final SpriteSetPlayer spriteSet;

    public ParticleStaticLoD(ClientLevel level, double x, double y, double z, SpriteSetPlayer spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
    }

    public void setParticleFromDistanceToCamera(float dist) {
        if (spriteSet != null) {
            int age = (int)(dist * 2);
            setSprite(spriteSet.get(age, 0));
        }
    }
}
