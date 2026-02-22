package cn.leolezury.eternalstarlight.common.client.book.component;

import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;

public interface BookComponentConfig {
	HashSet<HashSet<ResourceLocation>> unlockConditions();

	ResourceLocation id();
}
