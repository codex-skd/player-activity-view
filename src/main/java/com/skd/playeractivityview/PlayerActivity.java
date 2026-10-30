package com.skd.playeractivityview;

import com.skd.playeractivityview.config.ConfigClient;
import com.skd.playeractivityview.config.ConfigCommon;
import com.skd.playeractivityview.config.CustomArmCorrections;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerActivity implements ModInitializer {
    public static final String MODID = "player_activity_view";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static String configJSONName = "player_activity_view-item-arm-adjustments.json";

    private static PlayerStatusManagerServer playerStatusManagerServer;
    private static PlayerActivity instance;
    private static MinecraftServer currentServer;

    public static PlayerActivity instance() { return instance; }

    public static PlayerStatusManagerServer getPlayerStatusManagerServer() {
        if (playerStatusManagerServer == null) playerStatusManagerServer = new PlayerStatusManagerServer();
        return playerStatusManagerServer;
    }

    @Override
    public void onInitialize() {
        instance = this;

        new PlayerActivityNetworkingFabric();
        PlayerActivityNetworkingFabric.register();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> currentServer = server);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> currentServer = null);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (handler.getPlayer() != null) {
                getPlayerStatusManagerServer().playerLoggedIn(handler.getPlayer());
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getPlayerList() != null) {
                for (net.minecraft.server.level.ServerPlayer sp : server.getPlayerList().getPlayers()) {
                    getPlayerStatusManagerServer().tickPlayer(sp);
                }
            }
        });

        generateJsonConfigFile(configJSONName);
        CustomArmCorrections.loadJsonConfigs();
    }

    public PlayerList getPlayerList() {
        return currentServer == null ? null : currentServer.getPlayerList();
    }

    public boolean isModInstalled(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    public float getFarPlane() {
        return 250.0F;
    }

    public static void generateJsonConfigFile(String filename) {
        String filePath = "config/" + filename;
        String contents = getContentsFromResourceLocation(Identifier.fromNamespaceAndPath(MODID, filePath));
        if (!contents.isEmpty()) {
            File fileOut = new File("./config/" + filename);
            if (!fileOut.exists()) {
                try {
                    FileUtils.writeStringToFile(fileOut, contents, StandardCharsets.UTF_8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getContentsFromResourceLocation(Identifier loc) {
        try {
            String str = "assets/" + loc.toString().replace(":", "/");
            InputStream in = PlayerActivity.class.getClassLoader().getResourceAsStream(str);
            if (in == null) return "";
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static void dbg(Object obj) {}

    private static net.minecraft.server.packs.resources.ResourceProvider shaderResourceProvider;

    public static void initCustomShaders(net.minecraft.server.packs.resources.ResourceProvider resourceProvider) {
        shaderResourceProvider = resourceProvider;
    }

    public static net.minecraft.server.packs.resources.ResourceProvider getShaderResourceProvider() {
        return shaderResourceProvider;
    }
}
