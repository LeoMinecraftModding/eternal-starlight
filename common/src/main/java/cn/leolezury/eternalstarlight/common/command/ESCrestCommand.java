package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ESCrestCommand {
	public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext commandBuildContext) {
		return Commands.literal("crest").requires((commandSourceStack) -> {
			return commandSourceStack.hasPermission(2);
		}).then(Commands.literal("give").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("crest", ResourceArgument.resource(commandBuildContext, ESRegistries.CREST)).executes((commandContext) -> {
			return giveCrest(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "targets"), ResourceArgument.getResource(commandContext, "crest", ESRegistries.CREST), 1);
		}).then(Commands.argument("level", IntegerArgumentType.integer(1)).executes((commandContext) -> {
			return giveCrest(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "targets"), ResourceArgument.getResource(commandContext, "crest", ESRegistries.CREST), IntegerArgumentType.getInteger(commandContext, "level"));
		}))))).then(Commands.literal("remove").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("crest", ResourceArgument.resource(commandBuildContext, ESRegistries.CREST)).executes((commandContext) -> {
			return removeCrest(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "targets"), ResourceArgument.getResource(commandContext, "crest", ESRegistries.CREST));
		}))));
	}

	private static int giveCrest(CommandSourceStack commandSourceStack, ServerPlayer serverPlayer, Holder<Crest> crest, int level) {
		if (level <= crest.value().maxLevel() && ESCrestUtil.giveCrest(serverPlayer, new Crest.Instance(crest, level))) {
			commandSourceStack.sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".crest.give"), true);
		} else {
			commandSourceStack.sendFailure(Component.translatable("commands." + EternalStarlight.ID + ".crest.give_fail"));
		}
		return 1;
	}

	private static int removeCrest(CommandSourceStack commandSourceStack, ServerPlayer serverPlayer, Holder<Crest> crest) {
		if (ESCrestUtil.removeCrest(serverPlayer, crest)) {
			commandSourceStack.sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".crest.remove"), true);
		} else {
			commandSourceStack.sendFailure(Component.translatable("commands." + EternalStarlight.ID + ".crest.remove_fail"));
		}
		return 1;
	}
}

