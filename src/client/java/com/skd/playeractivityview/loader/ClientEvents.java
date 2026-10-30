package com.skd.playeractivityview.loader;

import com.skd.playeractivityview.CommandReloadConfig;
import com.skd.playeractivityview.PlayerActivityClient;
import com.skd.playeractivityview.client.screen.RenderHelper;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class ClientEvents {
    public ClientEvents() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            CommandReloadConfig.registerClient(dispatcher));
    }

    public static void onRenderFramePost() {
        RenderHelper.captureScreenIfNeeded();
    }

    public static void onGameTick() {
        PlayerActivityClient.getPlayerStatusManagerClient().tickGame();
    }

    public static void onMouse(boolean pressed) {
        PlayerActivityClient.getPlayerStatusManagerClient().onMouse(pressed);
    }

    public static void onKey() {
        PlayerActivityClient.getPlayerStatusManagerClient().onKey();
    }
}
