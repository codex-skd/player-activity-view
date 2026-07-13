package com.skd.playeractivityview;

import com.skd.playeractivityview.config.ConfigClient;
import com.skd.playeractivityview.config.ConfigCommon;
import com.skd.playeractivityview.config.CustomArmCorrections;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import com.skd.playeractivityview.network.PacketNBTFromClient;
import com.skd.playeractivityview.network.PacketNBTFromServer;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import net.minecraft.resources.Identifier;
import net.minecraft.server.players.PlayerList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PlayerActivity.MODID)
public class PlayerActivity {
    public static final String MODID = "player_activity_view";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static String configJSONName = "player_activity_view-item-arm-adjustments.json";

    private static PlayerStatusManagerClient playerStatusManagerClient;
    private static PlayerStatusManagerServer playerStatusManagerServer;
    private static PlayerActivity instance;

    public static PlayerActivity instance() { return instance; }

    public static PlayerStatusManagerClient getPlayerStatusManagerClient() {
        if (playerStatusManagerClient == null) playerStatusManagerClient = new PlayerStatusManagerClient();
        return playerStatusManagerClient;
    }

    public static PlayerStatusManagerServer getPlayerStatusManagerServer() {
        if (playerStatusManagerServer == null) playerStatusManagerServer = new PlayerStatusManagerServer();
        return playerStatusManagerServer;
    }

    public PlayerActivity(ModContainer container) {
        instance = this;

        container.registerConfig(ModConfig.Type.COMMON, ConfigCommon.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ConfigClient.SPEC);
        container.registerConfig(ModConfig.Type.SERVER, ServerSyncedConfig.SPEC);

        new PlayerActivityNetworkingNeoForge();

        container.getEventBus().addListener(this::setup);
        container.getEventBus().addListener(this::registerPackets);

        NeoForge.EVENT_BUS.addListener(this::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);

        if (FMLEnvironment.getDist().isClient()) {
            com.skd.playeractivityview.loader.ClientEvents clientEvents = new com.skd.playeractivityview.loader.ClientEvents();
            container.getEventBus().addListener(clientEvents::getRegisteredParticles);
            NeoForge.EVENT_BUS.addListener(clientEvents::onRegisterCommandsClient);
            NeoForge.EVENT_BUS.addListener(clientEvents::onGameTick);
            NeoForge.EVENT_BUS.addListener(clientEvents::onKey);
        }

        generateJsonConfigFile(configJSONName);
        CustomArmCorrections.loadJsonConfigs();
    }

    private void setup(FMLCommonSetupEvent event) {}

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.0");
        PlayerActivityNetworkingNeoForge.register(registrar);
    }

    public PlayerList getPlayerList() {
        return ServerLifecycleHooks.getCurrentServer() == null ? null : ServerLifecycleHooks.getCurrentServer().getPlayerList();
    }

    public boolean isModInstalled(String modID) {
        return ModList.get().isLoaded(modID);
    }

    public float getFarPlane() {
        return 250.0F;
    }

    public void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            getPlayerStatusManagerClient().tickPlayer(event.getEntity());
        } else {
            getPlayerStatusManagerServer().tickPlayer(event.getEntity());
        }
    }

    public void onPlayerJoin(PlayerLoggedInEvent event) {
        getPlayerStatusManagerServer().playerLoggedIn(event.getEntity());
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
}
