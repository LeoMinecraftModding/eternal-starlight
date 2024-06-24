package cn.leolezury.eternalstarlight.common.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public record ResourceKeyComponent<T>(ResourceKey<T> resourceKey) {
	public static <T> Codec<ResourceKeyComponent<T>> codec(ResourceKey<? extends Registry<T>> resourceKey) {
		return ResourceKey.codec(resourceKey).xmap(ResourceKeyComponent::new, ResourceKeyComponent::resourceKey);
	}
}
