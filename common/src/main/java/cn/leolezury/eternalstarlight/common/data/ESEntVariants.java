package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.animal.EntVariant;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ESEntVariants {
	public static final ResourceKey<EntVariant> LUNAR = create("lunar");
	public static final ResourceKey<EntVariant> NORTHLAND = create("northland");
	public static final ResourceKey<EntVariant> SCARLET = create("scarlet");
	public static final ResourceKey<EntVariant> BANYIN = create("banyin");
	public static final ResourceKey<EntVariant> CRADLEWOOD = create("cradlewood");

	public static void bootstrap(BootstrapContext<EntVariant> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(LUNAR, new EntVariant(EternalStarlight.id("entity/ent/lunar"), ESItems.LUNAR_LEAVES.asHolder(), HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_FOREST), biomes.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST))));
		context.register(NORTHLAND, new EntVariant(EternalStarlight.id("entity/ent/northland"), ESItems.NORTHLAND_LEAVES.asHolder(), HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST), biomes.getOrThrow(ESBiomes.PERMAFROST_PEAKS), biomes.getOrThrow(ESBiomes.STARLIGHT_TAIGA))));
		context.register(SCARLET, new EntVariant(EternalStarlight.id("entity/ent/scarlet"), ESItems.SCARLET_LEAVES.asHolder(), HolderSet.direct(biomes.getOrThrow(ESBiomes.SCARLET_FOREST))));
		context.register(BANYIN, new EntVariant(EternalStarlight.id("entity/ent/banyin"), ESItems.BANYIN_LEAVES.asHolder(), HolderSet.direct(biomes.getOrThrow(ESBiomes.DARK_SWAMP))));
		context.register(CRADLEWOOD, new EntVariant(EternalStarlight.id("entity/ent/cradlewood"), ESItems.CRADLEWOOD_LEAVES.asHolder(), HolderSet.direct(biomes.getOrThrow(ESBiomes.SOLARIS_ISLES))));
	}

	public static ResourceKey<EntVariant> create(String name) {
		return ResourceKey.create(ESRegistries.ENT_VARIANT, EternalStarlight.id(name));
	}
}
