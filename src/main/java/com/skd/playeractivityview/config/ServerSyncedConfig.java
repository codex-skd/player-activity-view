package com.skd.playeractivityview.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerSyncedConfig {
    public static boolean isLoaded() { return SPEC.isLoaded(); }

    public static boolean dynamicGuiShowEntireScreen() {
        return isLoaded() ? DYNAMIC_GUI_SHOW_ENTIRE_SCREEN.get() : DYNAMIC_GUI_SHOW_ENTIRE_SCREEN.getDefault();
    }

    public static boolean dynamicGuiUseOldSimple() {
        return isLoaded() ? DYNAMIC_GUI_USE_OLD_SIMPLE.get() : DYNAMIC_GUI_USE_OLD_SIMPLE.getDefault();
    }

    public static boolean dynamicGuiDisableBackground() {
        return isLoaded() ? DYNAMIC_GUI_DISABLE_BACKGROUND.get() : DYNAMIC_GUI_DISABLE_BACKGROUND.getDefault();
    }

    public static int dynamicGuiBlurLevel() {
        return isLoaded() ? DYNAMIC_GUI_BLUR_LEVEL.get() : DYNAMIC_GUI_BLUR_LEVEL.getDefault();
    }

    public static double dynamicGuiSizeRadius() {
        return isLoaded() ? DYNAMIC_GUI_SIZE_RADIUS.get() : DYNAMIC_GUI_SIZE_RADIUS.getDefault();
    }

    public static int dynamicGuiTickSendRate() {
        return isLoaded() ? DYNAMIC_GUI_TICK_SEND_RATE.get() : DYNAMIC_GUI_TICK_SEND_RATE.getDefault();
    }

    public static boolean dynamicGuiDontSendConstantUpdates() {
        return isLoaded() ? DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.get() : DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.getDefault();
    }

    public static int distanceRequiredToShowGuiInfo() {
        return isLoaded() ? DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get() : DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.getDefault();
    }

    public static boolean showIdleStatesInPlayerList() {
        return isLoaded() ? SHOW_IDLE_STATES_IN_PLAYER_LIST.get() : SHOW_IDLE_STATES_IN_PLAYER_LIST.getDefault();
    }

    public static boolean showIdleStatesInPlayerAboveHead() {
        return isLoaded() ? SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.get() : SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.getDefault();
    }

    public static boolean showPlayerAnimations() {
        return isLoaded() ? SHOW_PLAYER_ANIMATIONS.get() : SHOW_PLAYER_ANIMATIONS.getDefault();
    }

    public static boolean showPlayerAnimationTyping() {
        return isLoaded() ? SHOW_PLAYER_ANIMATION_TYPING.get() : SHOW_PLAYER_ANIMATION_TYPING.getDefault();
    }

    public static boolean showPlayerAnimationIdle() {
        return isLoaded() ? SHOW_PLAYER_ANIMATION_IDLE.get() : SHOW_PLAYER_ANIMATION_IDLE.getDefault();
    }

    public static boolean showPlayerAnimationGui() {
        return isLoaded() ? SHOW_PLAYER_ANIMATION_GUI.get() : SHOW_PLAYER_ANIMATION_GUI.getDefault();
    }

    public static boolean showPlayerActiveNonChatGui() {
        return isLoaded() ? SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get() : SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.getDefault();
    }

    public static boolean showPlayerActiveChatGui() {
        return isLoaded() ? SHOW_PLAYER_ACTIVE_CHAT_GUI.get() : SHOW_PLAYER_ACTIVE_CHAT_GUI.getDefault();
    }

    public static boolean screenTypingVisible() {
        return isLoaded() ? SCREEN_TYPING_VISIBLE.get() : SCREEN_TYPING_VISIBLE.getDefault();
    }

    public static boolean playScreenOpenSounds() {
        return isLoaded() ? PLAY_SCREEN_OPEN_SOUNDS.get() : PLAY_SCREEN_OPEN_SOUNDS.getDefault();
    }

    public static boolean playMouseClickSounds() {
        return isLoaded() ? PLAY_MOUSE_CLICK_SOUNDS.get() : PLAY_MOUSE_CLICK_SOUNDS.getDefault();
    }

    public static boolean showItemsTransferred() {
        return isLoaded() ? SHOW_ITEMS_TRANSFERRED.get() : SHOW_ITEMS_TRANSFERRED.getDefault();
    }

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
