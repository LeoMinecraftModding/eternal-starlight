package cn.leolezury.eternalstarlight.common.platform.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface RegistryObject<R, T extends R> extends Supplier<T> {
	Holder<R> asHolder();

	ResourceKey<T> getResourceKey();

	ResourceLocation getId();

	@Override
	T get();
}
