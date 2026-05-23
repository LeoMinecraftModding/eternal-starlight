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
	public static final ResourceKey<BiomeData> UMBRAL_PLAINS = create("umbral_plains");
	public static final ResourceKey<BiomeData> GLIMMER_SCRUBLAND = create("glimmer_scrubland");
	public static final ResourceKey<BiomeData> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
	public static final ResourceKey<BiomeData> PERMAFROST_PEAKS = create("permafrost_peaks");
	public static final ResourceKey<BiomeData> STARLIGHT_TAIGA = create("starlight_taiga");
	public static final ResourceKey<BiomeData> DARK_SWAMP = create("dark_swamp");
	public static final ResourceKey<BiomeData> SCARLET_FOREST = create("scarlet_forest");
	public static final ResourceKey<BiomeData> TORREYA_FOREST = create("torreya_forest");
	public static final ResourceKey<BiomeData> CRYSTALLIZED_DESERT = create("crystallized_desert");
	public static final ResourceKey<BiomeData> LUCENT_MYCELIUM_ISLE = create("lucent_mycelium_isle");
	public static final ResourceKey<BiomeData> SOLARIS_ISLES = create("solaris_isles");
	public static final ResourceKey<BiomeData> SHIMMER_RIVER = create("shimmer_river");
	public static final ResourceKey<BiomeData> ETHER_RIVER = create("ether_river");
	public static final ResourceKey<BiomeData> SHIMMER_RIVER_TRANSITION = create("shimmer_river_transition");
	public static final ResourceKey<BiomeData> STARLIT_SEA = create("starlit_sea");
	public static final ResourceKey<BiomeData> ICY_SEA = create("icy_sea");
	public static final ResourceKey<BiomeData> SPIRAL_KELP_FOREST = create("spiral_kelp_forest");
	public static final ResourceKey<BiomeData> LUSH_SHALLOW_SEA = create("lush_shallow_sea");
	public static final ResourceKey<BiomeData> THE_ABYSS = create("the_abyss");
	public static final ResourceKey<BiomeData> THE_ABYSS_TRANSITION = create("the_abyss_transition");
	public static final ResourceKey<BiomeData> WARM_SHORE = create("warm_shore");
	public static final ResourceKey<BiomeData> GRIM_SHORE = create("grim_shore");

	public static void bootstrap(BootstrapContext<BiomeData> context) {
		HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);

		context.register(STARLIGHT_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_FOREST), 65, 14)
			.build());

		context.register(STARLIGHT_DENSE_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST), 65, 14)
			.build());

		context.register(UMBRAL_PLAINS, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.UMBRAL_PLAINS), 60, 8)
			.build());

		context.register(GLIMMER_SCRUBLAND, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.GLIMMER_SCRUBLAND), 60, 10)
			.build());

		context.register(STARLIGHT_PERMAFROST_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST), 90, 40)
			.hasRivers(false)
			.build());

		context.register(PERMAFROST_PEAKS, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.PERMAFROST_PEAKS), 120, 30)
			.hasRivers(false)
			.build());

		context.register(STARLIGHT_TAIGA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_TAIGA), 75, 20)
			.build());

		context.register(DARK_SWAMP, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.DARK_SWAMP), 61, 5)
			.build());

		context.register(SCARLET_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SCARLET_FOREST), 70, 18)
			.build());

		context.register(TORREYA_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.TORREYA_FOREST), 57, 6)
			.withFluid(ESBlocks.ETHER.asHolder())
			.build());

		context.register(CRYSTALLIZED_DESERT, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.CRYSTALLIZED_DESERT), 65, 14)
			.hasRivers(false)
			.build());

		context.register(LUCENT_MYCELIUM_ISLE, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.LUCENT_MYCELIUM_ISLE), 65, 10)
			.hasRivers(false)
			.build());

		context.register(SOLARIS_ISLES, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SOLARIS_ISLES), 64, 6)
			.hasRivers(false)
			.build());

		context.register(STARLIT_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIT_SEA), 30, 8)
			.isOcean(true)
			.build());

		context.register(ICY_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.ICY_SEA), 30, 8)
			.isOcean(true)
			.build());

		context.register(SPIRAL_KELP_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SPIRAL_KELP_FOREST), 25, 8)
			.isOcean(true)
			.build());

		context.register(LUSH_SHALLOW_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.LUSH_SHALLOW_SEA), 32, 8)
			.isOcean(true)
			.build());

		context.register(THE_ABYSS, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS), -50, 5)
			.isOcean(true)
			.build());

		context.register(THE_ABYSS_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS), -10, 3)
			.isOcean(true)
			.build());

		context.register(SHIMMER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER), 35, 5)
			.build());

		context.register(SHIMMER_RIVER_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER), 38, 5)
			.build());

		context.register(ETHER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.ETHER_RIVER), 35, 3)
			.withFluid(ESBlocks.ETHER.asHolder())
			.build());

		context.register(WARM_SHORE, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.WARM_SHORE), 61, 5)
			.build());

		context.register(GRIM_SHORE, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.GRIM_SHORE), 61, 12)
			.build());
	}

	public static ResourceKey<BiomeData> create(String name) {
		return ResourceKey.create(ESRegistries.BIOME_DATA, EternalStarlight.id(name));
	}
}
