package cn.leolezury.eternalstarlight.common.client.book.component;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;

@Environment(EnvType.CLIENT)
public interface BookComponentConfig {
	HashSet<HashSet<ResourceLocation>> unlockConditions();

	ResourceLocation id();
}
