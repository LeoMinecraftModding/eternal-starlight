package cn.leolezury.eternalstarlight.neoforge.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.command.PostEffectArgument;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.registries.Registries;

public class ESCommandArgumentTypes {
	private static final RegistrationProvider<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = RegistrationProvider.get(Registries.COMMAND_ARGUMENT_TYPE, EternalStarlight.ID);
	public static final RegistryObject<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<?, ?>> POST_EFFECT = COMMAND_ARGUMENT_TYPES.register("post_effect", () -> ArgumentTypeInfos.registerByClass(PostEffectArgument.class, new PostEffectArgument.Info()));

	public static void loadClass() {
	}
}
