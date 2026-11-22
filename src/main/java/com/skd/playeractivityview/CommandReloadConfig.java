package com.skd.playeractivityview;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.skd.playeractivityview.config.CustomArmCorrections;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
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

    public static void registerClient(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            LiteralArgumentBuilder.<FabricClientCommandSource>literal("player_activity_view")
                .then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("reloadJSON")
                    .executes(ctx -> {
                        CustomArmCorrections.loadJsonConfigs();
                        ctx.getSource().sendFeedback(Component.literal("Reloaded config JSONs"));
                        return 1;
                    })
                )
        );
    }
}
