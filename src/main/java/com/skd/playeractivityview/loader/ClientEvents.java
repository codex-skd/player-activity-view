package com.skd.playeractivityview.loader;

import com.skd.playeractivityview.CommandReloadConfig;
import com.skd.playeractivityview.ModParticles;
import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.client.screen.RenderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;

public class ClientEvents {

    public void getRegisteredParticles(TextureAtlasStitchedEvent event) {
        ModParticles.textureAtlasUpload(event.getAtlas());
    }

    @SubscribeEvent
    public void onRegisterCommandsClient(RegisterClientCommandsEvent event) {
        CommandReloadConfig.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onGameTick(ClientTickEvent.Post event) {
        PlayerActivity.getPlayerStatusManagerClient().tickGame();
    }

    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseButton.Post event) {
        PlayerActivity.getPlayerStatusManagerClient().onMouse(event.getAction() != 0);
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.Key event) {
        if (event.getAction() == 1) {
            PlayerActivity.getPlayerStatusManagerClient().onKey();
        }
    }
}
