package com.skd.playeractivityview;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class PlayerActivityClient {
    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
