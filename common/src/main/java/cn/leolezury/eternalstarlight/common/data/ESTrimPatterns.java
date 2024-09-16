package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.List;

public class ESTrimPatterns {
	public static final ResourceKey<TrimPattern> KEEPER = create("keeper");
	public static final ResourceKey<TrimPattern> FORGE = create("forge");

	public static final List<ResourceKey<TrimPattern>> TRIM_PATTERNS = List.of(
		KEEPER,
		FORGE
	);

	public static void bootstrap(BootstrapContext<TrimPattern> context) {
		register(context, ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), KEEPER);
		register(context, ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), FORGE);
	}

	private static void register(BootstrapContext<TrimPattern> context, Item item, ResourceKey<TrimPattern> resourceKey) {
		TrimPattern trimPattern = new TrimPattern(resourceKey.location(), BuiltInRegistries.ITEM.wrapAsHolder(item), Component.translatable(Util.makeDescriptionId("trim_pattern", resourceKey.location())), false);
		context.register(resourceKey, trimPattern);
	}

	public static ResourceKey<TrimPattern> create(String name) {
		return ResourceKey.create(Registries.TRIM_PATTERN, EternalStarlight.id(name));
	}
}
