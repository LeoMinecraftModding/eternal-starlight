package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
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
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_COLD)
			.addTag(Tags.Biomes.IS_COLD_OVERWORLD);
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