package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import java.util.Collection;

public class ESBehaviorCommand {
	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("behavior")
			.requires((stack) -> stack.hasPermission(2))
			.then(Commands.literal("get")
				.then(Commands.argument("targets", EntityArgument.entities())
					.executes(ESBehaviorCommand::getBehavior)))
			.then(Commands.literal("set")
				.then(Commands.argument("targets", EntityArgument.entities())
					.then(Commands.argument("state", IntegerArgumentType.integer(0))
						.executes(ESBehaviorCommand::setBehavior))));
	}

	private static int getBehavior(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		Collection<? extends Entity> entities = EntityArgument.getEntities(ctx, "targets");
		int count = 0;
		for (Entity entity : entities) {
			if (entity instanceof MultiBehaviorUser user) {
				ctx.getSource().sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".behavior.get", entity.getDisplayName(), user.getBehaviorState()), false);
				count++;
			}
		}
		if (count == 0) {
			ctx.getSource().sendFailure(Component.translatable("commands." + EternalStarlight.ID + ".behavior.no_targets"));
		}
		return count;
	}

	private static int setBehavior(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		Collection<? extends Entity> entities = EntityArgument.getEntities(ctx, "targets");
		int state = IntegerArgumentType.getInteger(ctx, "state");
		int count = 0;
		for (Entity entity : entities) {
			if (entity instanceof MultiBehaviorUser user && user.getBehaviorState() != state) {
				user.getBehaviorManager().forceStartPhase(state);
				count++;
			}
		}
		int finalCount = count;
		if (finalCount > 0) {
			ctx.getSource().sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".behavior.set", finalCount, state), true);
		} else {
			ctx.getSource().sendFailure(Component.translatable("commands." + EternalStarlight.ID + ".behavior.no_targets"));
		}
		return count;
	}
}
