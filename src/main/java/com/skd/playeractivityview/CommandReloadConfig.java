package com.skd.playeractivityview;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.skd.playeractivityview.config.CustomArmCorrections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandReloadConfig {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("player_activity_view")
            .then(Commands.literal("reloadJSON")
                .executes(ctx -> {
                    CustomArmCorrections.loadJsonConfigs();
                    ctx.getSource().sendSuccess(() -> Component.literal("Reloaded config JSONs"), true);
                    return 1;
                })
            )
        );
    }
}
