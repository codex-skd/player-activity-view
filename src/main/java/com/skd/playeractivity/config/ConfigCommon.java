package com.skd.playeractivity.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigCommon {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ANNOUNCE_IDLE_STATES_IN_CHAT = BUILDER
            .comment("Announce idle state changes in chat")
            .define("announceIdleStatesInChat", false);

    public static final ModConfigSpec.IntValue TICKS_TO_MARK_PLAYER_IDLE = BUILDER
            .comment("Default 5 minutes (6000 ticks)")
            .defineInRange("ticksToMarkPlayerIdle", 6000, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
