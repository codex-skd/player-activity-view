package com.skd.playeractivityview.loader;

import com.skd.playeractivityview.CommandReloadConfig;
import com.skd.playeractivityview.PlayerActivity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class ClientEvents {
    public void getRegisteredParticles(net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent event) {}

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
        boolean pressed = event.getAction() == 1;
        PlayerActivity.getPlayerStatusManagerClient().onMouse(pressed);
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.Key event) {
        if (event.getAction() == 1) { // PRESS
            PlayerActivity.getPlayerStatusManagerClient().onKey();
        }
    }
}
