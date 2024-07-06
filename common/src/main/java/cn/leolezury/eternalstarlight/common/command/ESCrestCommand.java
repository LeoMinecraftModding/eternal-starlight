package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.network.chat.Component;

public class ESCrestCommand {
	public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		return Commands.literal("crest").requires((commandSourceStack) -> {
			return commandSourceStack.hasPermission(2);
		}).then(Commands.literal("give").then(Commands.argument("crest", ResourceArgument.resource(commandBuildContext, ESRegistries.CREST)).executes((commandContext) -> {
			return giveCrest(commandContext.getSource(), ResourceArgument.getResource(commandContext, "crest", ESRegistries.CREST).value(), 1);
		}).then(Commands.argument("level", IntegerArgumentType.integer(1)).executes((commandContext) -> {
			return giveCrest(commandContext.getSource(), ResourceArgument.getResource(commandContext, "crest", ESRegistries.CREST).value(), IntegerArgumentType.getInteger(commandContext, "level"));
		})))).then(Commands.literal("remove").then(Commands.argument("crest", ResourceArgument.resource(commandBuildContext, ESRegistries.CREST)).executes((commandContext) -> {
			return removeCrest(commandContext.getSource(), ResourceArgument.getResource(commandContext, "crest", ESRegistries.CREST).value());
		})));
	}

	private static int giveCrest(CommandSourceStack commandSourceStack, Crest crest, int level) {
		if (ESCrestUtil.giveCrest(commandSourceStack.getPlayer(), new Crest.Instance(crest, level))) {
			commandSourceStack.sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".crest.give"), true);
		} else {
			commandSourceStack.sendFailure(Component.translatable("commands." + EternalStarlight.ID + ".crest.give_fail"));
		}
		return 1;
	}

	private static int removeCrest(CommandSourceStack commandSourceStack, Crest crest) {
		if (ESCrestUtil.removeCrest(commandSourceStack.getPlayer(), crest)) {
			commandSourceStack.sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".crest.remove"), true);
		} else {
			commandSourceStack.sendFailure(Component.translatable("commands." + EternalStarlight.ID + ".crest.remove_fail"));
		}
		return 1;
	}
}

