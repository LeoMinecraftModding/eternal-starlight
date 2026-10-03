package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESBiomeTagsProvider extends BiomeTagsProvider {
	public ESBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(ESTags.Biomes.IS_STARLIGHT)
			.add(
				ESBiomes.STARLIGHT_FOREST,
				ESBiomes.STARLIGHT_DENSE_FOREST,
				ESBiomes.UMBRAL_PLAINS,
				ESBiomes.GLIMMER_SCRUBLAND,
				ESBiomes.STARLIGHT_PERMAFROST_FOREST,
				ESBiomes.PERMAFROST_PEAKS,
				ESBiomes.STARLIGHT_TAIGA,
				ESBiomes.DARK_SWAMP,
				ESBiomes.SCARLET_FOREST,
				ESBiomes.TORREYA_FOREST,
				ESBiomes.CRYSTALLIZED_DESERT,
				ESBiomes.LUCENT_MYCELIUM_ISLE,
				ESBiomes.SOLARIS_ISLES,
				ESBiomes.STARLIT_SKY,
				ESBiomes.SHIMMER_RIVER,
				ESBiomes.ETHER_RIVER,
				ESBiomes.STARLIT_SEA,
				ESBiomes.ICY_SEA,
				ESBiomes.SPIRAL_KELP_FOREST,
				ESBiomes.LUSH_SHALLOW_SEA,
				ESBiomes.THE_ABYSS,
				ESBiomes.WARM_SHORE,
				ESBiomes.GRIM_SHORE
			);
		tag(ESTags.Biomes.PERMAFROST)
			.add(
				ESBiomes.STARLIGHT_PERMAFROST_FOREST,
				ESBiomes.PERMAFROST_PEAKS
			);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_COMMON)
			.addTag(Tags.Biomes.IS_PLAINS)
			.addTag(Tags.Biomes.IS_SAVANNA);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_FOREST)
			.addTag(Tags.Biomes.IS_FOREST);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_DESERT)
			.addTag(Tags.Biomes.IS_DESERT)
			.addTag(Tags.Biomes.IS_BADLANDS);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_JUNGLE)
			.addTag(Tags.Biomes.IS_JUNGLE);
		// cold land biomes only, the cold overworld tag also contains the cold oceans
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_COLD)
			.add(
				Biomes.TAIGA,
				Biomes.OLD_GROWTH_PINE_TAIGA,
				Biomes.OLD_GROWTH_SPRUCE_TAIGA,
				Biomes.WINDSWEPT_HILLS,
				Biomes.WINDSWEPT_GRAVELLY_HILLS,
				Biomes.WINDSWEPT_FOREST,
				Biomes.SNOWY_PLAINS,
				Biomes.ICE_SPIKES,
				Biomes.GROVE,
				Biomes.SNOWY_SLOPES,
				Biomes.JAGGED_PEAKS,
				Biomes.FROZEN_PEAKS,
				Biomes.SNOWY_TAIGA
			);
		tag(ESTags.Biomes.HAS_GOLEM_FORGE)
			.add(
				ESBiomes.STARLIGHT_FOREST,
				ESBiomes.STARLIGHT_DENSE_FOREST,
				ESBiomes.UMBRAL_PLAINS,
				ESBiomes.GLIMMER_SCRUBLAND,
				ESBiomes.STARLIGHT_PERMAFROST_FOREST,
				ESBiomes.PERMAFROST_PEAKS,
				ESBiomes.STARLIGHT_TAIGA,
				ESBiomes.SCARLET_FOREST
			);
		tag(ESTags.Biomes.HAS_CURSED_GARDEN)
			.add(
				ESBiomes.STARLIGHT_FOREST,
				ESBiomes.STARLIGHT_DENSE_FOREST,
				ESBiomes.UMBRAL_PLAINS,
				ESBiomes.GLIMMER_SCRUBLAND,
				ESBiomes.STARLIGHT_TAIGA,
				ESBiomes.SCARLET_FOREST
			);
		tag(ESTags.Biomes.HAS_STRANGHOUL_DEN)
			.add(
				ESBiomes.DARK_SWAMP
			);
	}
}