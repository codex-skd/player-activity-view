package com.skd.playeractivity.loader;

import com.skd.playeractivity.CommandReloadConfig;
import com.skd.playeractivity.PlayerActivity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

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
    public void onKey(PlayerInteractEvent.LeftClickEmpty event) {
        PlayerActivity.getPlayerStatusManagerClient().onKey();
    }
}
