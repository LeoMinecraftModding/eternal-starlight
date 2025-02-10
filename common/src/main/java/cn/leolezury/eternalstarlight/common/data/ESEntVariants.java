package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.animal.EntVariant;
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
	public static final ResourceKey<EntVariant> STARLIGHT_MANGROVE = create("starlight_mangrove");

	public static void bootstrap(BootstrapContext<EntVariant> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(LUNAR, new EntVariant(HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_FOREST), biomes.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST)), EternalStarlight.id("textures/entity/ent/lunar")));
		context.register(NORTHLAND, new EntVariant(HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST)), EternalStarlight.id("textures/entity/ent/northland")));
		context.register(SCARLET, new EntVariant(HolderSet.direct(biomes.getOrThrow(ESBiomes.SCARLET_FOREST)), EternalStarlight.id("textures/entity/ent/scarlet")));
		context.register(STARLIGHT_MANGROVE, new EntVariant(HolderSet.direct(biomes.getOrThrow(ESBiomes.DARK_SWAMP)), EternalStarlight.id("textures/entity/ent/starlight_mangrove")));
	}

	public static ResourceKey<EntVariant> create(String name) {
		return ResourceKey.create(ESRegistries.ENT_VARIANT, EternalStarlight.id(name));
	}
}
