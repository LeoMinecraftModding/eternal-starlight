package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public class ESCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
        commandDispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(EternalStarlight.ID)
                        .then(ESWeatherCommand.register(commandDispatcher, commandBuildContext)));
    }
}
