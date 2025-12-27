package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewingVariant;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ESShimmerLacewingVariants {
	public static final ResourceKey<ShimmerLacewingVariant> RIVER = create("river");
	public static final ResourceKey<ShimmerLacewingVariant> SWAMP = create("swamp");

	public static void bootstrap(BootstrapContext<ShimmerLacewingVariant> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(RIVER, new ShimmerLacewingVariant(EternalStarlight.id("entity/shimmer_lacewing/river"), EternalStarlight.id("entity/shimmer_lacewing/river_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.SHIMMER_RIVER))));
		context.register(SWAMP, new ShimmerLacewingVariant(EternalStarlight.id("entity/shimmer_lacewing/swamp"), EternalStarlight.id("entity/shimmer_lacewing/swamp_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.DARK_SWAMP))));
	}

	public static ResourceKey<ShimmerLacewingVariant> create(String name) {
		return ResourceKey.create(ESRegistries.SHIMMER_LACEWING_VARIANT, EternalStarlight.id(name));
	}
}
