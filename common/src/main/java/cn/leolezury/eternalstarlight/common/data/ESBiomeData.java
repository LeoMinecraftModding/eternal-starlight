package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ESBiomeData {
	public static final ResourceKey<BiomeData> STARLIGHT_FOREST = create("starlight_forest");
	public static final ResourceKey<BiomeData> STARLIGHT_DENSE_FOREST = create("starlight_dense_forest");
	public static final ResourceKey<BiomeData> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
	public static final ResourceKey<BiomeData> DARK_SWAMP = create("dark_swamp");
	public static final ResourceKey<BiomeData> SCARLET_FOREST = create("scarlet_forest");
	public static final ResourceKey<BiomeData> TORREYA_FOREST = create("torreya_forest");
	public static final ResourceKey<BiomeData> CRYSTALLIZED_DESERT = create("crystallized_desert");
	public static final ResourceKey<BiomeData> SHIMMER_RIVER = create("shimmer_river");
	public static final ResourceKey<BiomeData> ETHER_RIVER = create("ether_river");
	public static final ResourceKey<BiomeData> SHIMMER_RIVER_TRANSITION = create("shimmer_river_transition");
	public static final ResourceKey<BiomeData> STARLIT_SEA = create("starlit_sea");
	public static final ResourceKey<BiomeData> SPIRAL_KELP_FOREST = create("spiral_kelp_forest");
	public static final ResourceKey<BiomeData> LUSH_SHALLOW_SEA = create("lush_shallow_sea");
	public static final ResourceKey<BiomeData> THE_ABYSS = create("the_abyss");
	public static final ResourceKey<BiomeData> THE_ABYSS_TRANSITION = create("the_abyss_transition");

	public static void bootstrap(BootstrapContext<BiomeData> context) {
		HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);

		// ---- Land biomes ----

		// Starlight Forest: temperate, moderate humidity, inland, moderate erosion (common default)
		context.register(STARLIGHT_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_FOREST), 65, 14)
				.build());

		// Starlight Dense Forest: wetter, more inland than forest
		context.register(STARLIGHT_DENSE_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST), 65, 14)
				.build());

		// Starlight Permafrost Forest: cold, high altitude, no beaches/rivers
		context.register(STARLIGHT_PERMAFROST_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST), 110, 40)
				.hasRivers(false)
				.build());

		// Dark Swamp: warm, very wet (wider range for larger patches)
		context.register(DARK_SWAMP, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.DARK_SWAMP), 61, 5)
				.build());

		// Scarlet Forest: warm, moderate humidity (wider range for larger patches)
		context.register(SCARLET_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SCARLET_FOREST), 70, 18)
				.build());

		// Torreya Forest: used exclusively as the transition (shore) biome around ether_river;
		// excluded from Climate lookup (isTransitionOnly). Climate params are dummies.
		context.register(TORREYA_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.TORREYA_FOREST), 57, 6)
				.build());

		// Crystallized Desert: hot, very dry, inland
		context.register(CRYSTALLIZED_DESERT, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.CRYSTALLIZED_DESERT), 65, 14)
				.hasRivers(false)
				.build());

		// ---- Ocean biomes ----

		// Starlit Sea: any temperature, open ocean (deep)
		context.register(STARLIT_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIT_SEA), 30, 8)
				.isOcean(true)
				.build());

		// Spiral Kelp Forest: cool ocean with wider range (larger patches)
		context.register(SPIRAL_KELP_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SPIRAL_KELP_FOREST), 25, 8)
				.isOcean(true)
				.build());

		// Lush Shallow Sea: warm, near coast (wider band for larger area)
		context.register(LUSH_SHALLOW_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.LUSH_SHALLOW_SEA), 32, 8)
				.isOcean(true)
				.build());

		context.register(THE_ABYSS, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS), -55, 5)
				.isOcean(true)
				.build());

		context.register(THE_ABYSS_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS), -40, 3)
				.isOcean(true)
				.build());

		context.register(SHIMMER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER), 35, 5)
				.build());

		context.register(SHIMMER_RIVER_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER), 38, 5)
				.build());

		context.register(ETHER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.ETHER_RIVER), 35, 3)
				.withFluid(ESBlocks.ETHER.asHolder())
				.build());
	}

	public static ResourceKey<BiomeData> create(String name) {
		return ResourceKey.create(ESRegistries.BIOME_DATA, EternalStarlight.id(name));
	}
}
