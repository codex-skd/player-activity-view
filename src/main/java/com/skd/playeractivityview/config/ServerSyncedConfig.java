package com.skd.playeractivityview.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerSyncedConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DYNAMIC_GUI_USE_OLD_SIMPLE = BUILDER
            .comment("Disable new dynamic gui system and use old simple visual")
            .define("dynamicGuiUseOldSimpleGUIVisual", false);

    public static final ModConfigSpec.IntValue DYNAMIC_GUI_TICK_SEND_RATE = BUILDER
            .comment("Delay in ticks before sending a new GUI image")
            .defineInRange("dynamicGuiTickSendRateOfGUIUpdates", 10, 0, 100);

    public static final ModConfigSpec.BooleanValue DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES = BUILDER
            .comment("Only sends initial GUI image, no constant updates")
            .define("dynamicGuiDontSendConstantGUIUpdates", true);

    public static final ModConfigSpec.IntValue DYNAMIC_GUI_BLUR_LEVEL = BUILDER
            .comment("Blur level (0-2) to prevent aliasing artifacts")
            .defineInRange("dynamicGuiBlurLevel", 1, 0, 2);

    public static final ModConfigSpec.DoubleValue DYNAMIC_GUI_SIZE_RADIUS = BUILDER
            .comment("Radius in pixels for GUI circle cut-off. Set to -1 to disable")
            .defineInRange("dynamicGuiSizeRadiusInPixelsToShow", 112.0, -1.0, 512.0);

    public static final ModConfigSpec.BooleanValue DYNAMIC_GUI_SHOW_ENTIRE_SCREEN = BUILDER
            .comment("Show clients entire screen instead of circle crop")
            .define("dynamicGuiShowClientsEntireScreen", false);

    public static final ModConfigSpec.BooleanValue DYNAMIC_GUI_DISABLE_BACKGROUND = BUILDER
            .comment("Disables background rendering for most guis")
            .define("dynamicGuiDisableBackgroundRendering", true);

    public static final ModConfigSpec.BooleanValue SHOW_ITEMS_TRANSFERRED = BUILDER
            .comment("Show items being transferred between player and container")
            .define("showItemsBeingTransferredBetweenPlayerAndContainer", true);

    public static final ModConfigSpec.IntValue DISTANCE_REQUIRED_TO_SHOW_GUI_INFO = BUILDER
            .comment("How close another player has to be to see GUI info")
            .defineInRange("distanceRequiredToShowGUIInfo", 10, 1, 100);

    // Shared visual settings that server can override
    public static final ModConfigSpec.BooleanValue SHOW_IDLE_STATES_IN_PLAYER_LIST = BUILDER
            .define("showIdleStatesInPlayerList", true);

    public static final ModConfigSpec.BooleanValue SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD = BUILDER
            .define("showIdleStatesInPlayerAboveHead", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATIONS = BUILDER
            .define("showPlayerAnimations", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATION_TYPING = BUILDER
            .define("showPlayerAnimation_Typing", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATION_IDLE = BUILDER
            .define("showPlayerAnimation_Idle", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATION_GUI = BUILDER
            .define("showPlayerAnimation_Gui", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ACTIVE_NON_CHAT_GUI = BUILDER
            .define("showPlayerActiveNonChatGui", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ACTIVE_CHAT_GUI = BUILDER
            .define("showPlayerActiveChatGui", true);

    public static final ModConfigSpec.BooleanValue SCREEN_TYPING_VISIBLE = BUILDER
            .define("screenTypingVisible", true);

    public static final ModConfigSpec.BooleanValue PLAY_SCREEN_OPEN_SOUNDS = BUILDER
            .define("playScreenOpenSounds", true);

    public static final ModConfigSpec.BooleanValue PLAY_MOUSE_CLICK_SOUNDS = BUILDER
            .define("playMouseClickSounds", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
