package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.network.PostEffectPacket;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectData;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.Locale;

public class ESPostEffectCommand {
	private static final int DEFAULT_DURATION = 60;
	private static final float DEFAULT_RADIUS = 10.0F;
	private static final float DEFAULT_INTENSITY = 1.0F;

	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("posteffect")
			.requires(source -> source.hasPermission(2))
			.then(Commands.literal("spawn")
				.then(Commands.argument("effect", PostEffectArgument.postEffect())
					.then(Commands.argument("pos", Vec3Argument.vec3(false))
						.executes(context -> spawn(context, DEFAULT_DURATION, DEFAULT_RADIUS, DEFAULT_INTENSITY))
						.then(Commands.argument("duration", TimeArgument.time(1))
							.executes(context -> spawn(context, IntegerArgumentType.getInteger(context, "duration"), DEFAULT_RADIUS, DEFAULT_INTENSITY))
							.then(Commands.argument("radius", FloatArgumentType.floatArg(0.1F))
								.executes(context -> spawn(context, IntegerArgumentType.getInteger(context, "duration"), FloatArgumentType.getFloat(context, "radius"), DEFAULT_INTENSITY))
								.then(Commands.argument("intensity", FloatArgumentType.floatArg(0.01F))
									.executes(context -> spawn(context, IntegerArgumentType.getInteger(context, "duration"), FloatArgumentType.getFloat(context, "radius"), FloatArgumentType.getFloat(context, "intensity")))))))))
			.then(Commands.literal("clear")
				.executes(ESPostEffectCommand::clear));
	}

	private static int spawn(CommandContext<CommandSourceStack> context, int duration, float radius, float intensity) {
		PostEffectData data = PostEffectArgument.getPostEffect(context, "effect");
		Vec3 position = Vec3Argument.getVec3(context, "pos");
		ESPlatform.INSTANCE.sendToAllClients(context.getSource().getLevel(), new PostEffectPacket(data.type().id(), data, position, duration, radius, intensity));
		context.getSource().sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".posteffect.spawned", data.type().id().toString(), formatPosition(position), duration), true);
		return 1;
	}

	private static int clear(CommandContext<CommandSourceStack> context) {
		ESPlatform.INSTANCE.sendToAllClients(context.getSource().getLevel(), new SimpleActionPacket(SimpleActionPacket.S2C_CLEAR_POST_EFFECT));
		context.getSource().sendSuccess(() -> Component.translatable("commands." + EternalStarlight.ID + ".posteffect.cleared_all"), true);
		return 1;
	}

	public static String formatPosition(Vec3 position) {
		return String.format(Locale.ROOT, "%.1f, %.1f, %.1f", position.x, position.y, position.z);
	}
}
