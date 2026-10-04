package com.skd.playeractivityview.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigClient {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SEND_MOUSE_INFO = BUILDER
            .comment("Sends relative mouse position and clicking")
            .define("sendMouseInfo", true);

    public static final ModConfigSpec.BooleanValue SEND_TYPING_SPEED = BUILDER
            .comment("Sends a calculated rate of typing")
            .define("sendTypingSpeed", true);

    public static final ModConfigSpec.BooleanValue SEND_ACTIVE_GUI = BUILDER
            .comment("Sends when you open a Gui and what Gui")
            .define("sendActiveGui", true);

    public static final ModConfigSpec.BooleanValue SEND_IDLE_STATE = BUILDER
            .comment("Sends when you go idle/return")
            .define("sendIdleState", true);

    public static final ModConfigSpec.BooleanValue SHOW_IDLE_STATES_IN_PLAYER_LIST = BUILDER
            .comment("Enables/disables idle visual in the server player list tab screen")
            .define("showIdleStatesInPlayerList", true);

    public static final ModConfigSpec.BooleanValue SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD = BUILDER
            .comment("Enables/disables idle visual above player head")
            .define("showIdleStatesInPlayerAboveHead", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATIONS = BUILDER
            .comment("Setting false disables all animations")
            .define("showPlayerAnimations", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATION_TYPING = BUILDER
            .comment("Enables/disables typing animation")
            .define("showPlayerAnimation_Typing", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATION_IDLE = BUILDER
            .comment("Enables/disables idle animation")
            .define("showPlayerAnimation_Idle", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ANIMATION_GUI = BUILDER
            .comment("Enables/disables GUI animation")
            .define("showPlayerAnimation_Gui", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ACTIVE_NON_CHAT_GUI = BUILDER
            .comment("Show any Gui that isn't chat typing related in world")
            .define("showPlayerActiveNonChatGui", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ACTIVE_CHAT_GUI = BUILDER
            .comment("Show the chat typing Gui in world")
            .define("showPlayerActiveChatGui", true);

    public static final ModConfigSpec.BooleanValue SHOW_PLAYER_ACTIVE_GUI_IF_NOT_EXACT_MATCH = BUILDER
            .comment("Fallback to chest GUI if no exact match for modded GUIs")
            .define("showPlayerActiveGuiIfNotExactMatch", true);

    public static final ModConfigSpec.BooleanValue SCREEN_TYPING_VISIBLE = BUILDER
            .comment("Show 'Player is typing...' on the chat screen")
            .define("screenTypingVisible", true);

    public static final ModConfigSpec.ConfigValue<String> SCREEN_TYPING_MULTIPLE_PLAYERS_TEXT = BUILDER
            .comment("String to use when too many people are typing")
            .define("screenTypingMultiplePlayersText", "Several people are typing");

    public static final ModConfigSpec.ConfigValue<String> SCREEN_TYPING_TEXT = BUILDER
            .comment("String to use next to the typing player(s) name")
            .define("screenTypingText", " is typing");

    public static final ModConfigSpec.IntValue SCREEN_TYPING_CHARACTER_LIMIT = BUILDER
            .comment("Max characters before switching to 'Several people are typing'")
            .defineInRange("screenTypingCharacterLimit", 50, 1, 200);

    public static final ModConfigSpec.IntValue SCREEN_TYPING_RELATIVE_POSITION_X = BUILDER
            .comment("X position adjustment for typing indicator")
            .defineInRange("screenTypingRelativePosition_X", 0, -1000, 1000);

    public static final ModConfigSpec.IntValue SCREEN_TYPING_RELATIVE_POSITION_Y = BUILDER
            .comment("Y position adjustment for typing indicator")
            .defineInRange("screenTypingRelativePosition_Y", 0, -1000, 1000);

    public static final ModConfigSpec.BooleanValue PLAY_SCREEN_OPEN_SOUNDS = BUILDER
            .comment("Plays a sound when a player opens some Guis")
            .define("playScreenOpenSounds", true);

    public static final ModConfigSpec.BooleanValue PLAY_MOUSE_CLICK_SOUNDS = BUILDER
            .comment("Plays a subtle sound when a player clicks their mouse in a Gui")
            .define("playMouseClickSounds", true);

    public static final ModConfigSpec.DoubleValue PARTICLE_SIZE_SCALE = BUILDER
            .comment("Adjusts the size of the gui visual that appears in front of a player")
            .defineInRange("particleSizeScale", 1.0, 0.1, 10.0);

    public static final ModConfigSpec.IntValue TICK_RECEIVE_AND_RENDER_RATE_OF_GUI_UPDATES = BUILDER
            .comment("Delay between ticks to accept and update GUI images")
            .defineInRange("tickReceiveAndRenderRateOfGUIUpdates", 10, 1, 100);

    public static final ModConfigSpec.BooleanValue DONT_SEND_DETAILED_GUI_INFO = BUILDER
            .comment("Privacy: don't send detailed GUI info")
            .define("dontSendDetailedGUIInfo", false);

    public static final ModConfigSpec.BooleanValue DONT_SEND_ITEM_INFO = BUILDER
            .comment("Privacy: don't send item transfer info")
            .define("dontSendItemInfo", false);

    public static final ModConfigSpec.BooleanValue SHOW_GUIS_FOR_YOUR_OWN_PLAYER_IN_3RD_PERSON = BUILDER
            .comment("Show own dynamic guis in 3rd person")
            .define("showGuisForYourOwnPlayerIn3rdPerson", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
