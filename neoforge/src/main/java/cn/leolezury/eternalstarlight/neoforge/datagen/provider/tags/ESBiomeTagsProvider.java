package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
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
		// conventional tags
		tag(Tags.Biomes.IS_HOT).add(
			ESBiomes.CRYSTALLIZED_DESERT
		);
		tag(Tags.Biomes.IS_COLD).add(
			ESBiomes.STARLIGHT_PERMAFROST_FOREST
		);
		tag(Tags.Biomes.IS_DENSE_VEGETATION).add(
			ESBiomes.STARLIGHT_FOREST,
			ESBiomes.STARLIGHT_DENSE_FOREST,
			ESBiomes.STARLIGHT_PERMAFROST_FOREST,
			ESBiomes.DARK_SWAMP,
			ESBiomes.SCARLET_FOREST,
			ESBiomes.TORREYA_FOREST
		);
		tag(Tags.Biomes.IS_WET).add(
			ESBiomes.STARLIT_SEA,
			ESBiomes.THE_ABYSS,
			ESBiomes.WARM_SHORE
		);
		tag(Tags.Biomes.IS_DRY).add(
			ESBiomes.CRYSTALLIZED_DESERT
		);
		tag(Tags.Biomes.IS_CONIFEROUS_TREE).add(
			ESBiomes.STARLIGHT_PERMAFROST_FOREST
		);
		tag(Tags.Biomes.IS_DECIDUOUS_TREE).add(
			ESBiomes.SCARLET_FOREST
		);
		tag(Tags.Biomes.IS_SWAMP).add(
			ESBiomes.DARK_SWAMP
		);
		tag(Tags.Biomes.IS_DESERT).add(
			ESBiomes.CRYSTALLIZED_DESERT
		);
		tag(Tags.Biomes.IS_BEACH).add(
			ESBiomes.WARM_SHORE
		);
		tag(Tags.Biomes.IS_BEACH).add(
			ESBiomes.WARM_SHORE
		);
		tag(Tags.Biomes.IS_RIVER).add(
			ESBiomes.SHIMMER_RIVER,
			ESBiomes.ETHER_RIVER
		);
		tag(Tags.Biomes.IS_OCEAN).add(
			ESBiomes.STARLIT_SEA,
			ESBiomes.THE_ABYSS
		);
		tag(Tags.Biomes.IS_DEEP_OCEAN).add(
			ESBiomes.THE_ABYSS
		);
		tag(Tags.Biomes.IS_SHALLOW_OCEAN).add(
			ESBiomes.STARLIT_SEA
		);
		tag(Tags.Biomes.IS_MAGICAL).add(
			ESBiomes.STARLIGHT_FOREST,
			ESBiomes.STARLIGHT_DENSE_FOREST,
			ESBiomes.STARLIGHT_PERMAFROST_FOREST,
			ESBiomes.DARK_SWAMP,
			ESBiomes.SCARLET_FOREST,
			ESBiomes.TORREYA_FOREST,
			ESBiomes.CRYSTALLIZED_DESERT,
			ESBiomes.SHIMMER_RIVER,
			ESBiomes.ETHER_RIVER,
			ESBiomes.STARLIT_SEA,
			ESBiomes.THE_ABYSS,
			ESBiomes.WARM_SHORE
		);
		tag(Tags.Biomes.IS_SANDY).add(
			ESBiomes.CRYSTALLIZED_DESERT,
			ESBiomes.WARM_SHORE
		);
		tag(Tags.Biomes.IS_SNOWY).add(
			ESBiomes.STARLIGHT_PERMAFROST_FOREST
		);
		tag(Tags.Biomes.IS_AQUATIC).add(
			ESBiomes.SHIMMER_RIVER,
			ESBiomes.STARLIT_SEA,
			ESBiomes.THE_ABYSS
		);
		tag(BiomeTags.IS_FOREST).add(
			ESBiomes.STARLIGHT_FOREST,
			ESBiomes.STARLIGHT_DENSE_FOREST,
			ESBiomes.STARLIGHT_PERMAFROST_FOREST,
			ESBiomes.DARK_SWAMP,
			ESBiomes.SCARLET_FOREST,
			ESBiomes.TORREYA_FOREST
		);
		tag(BiomeTags.IS_BEACH).add(
			ESBiomes.WARM_SHORE
		);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_COMMON).add(
			Biomes.PLAINS,
			Biomes.SNOWY_PLAINS,
			Biomes.SUNFLOWER_PLAINS,
			Biomes.SAVANNA_PLATEAU
		);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_FOREST).add(
			Biomes.FOREST,
			Biomes.FLOWER_FOREST,
			Biomes.BIRCH_FOREST,
			Biomes.DARK_FOREST,
			Biomes.OLD_GROWTH_BIRCH_FOREST,
			Biomes.WINDSWEPT_FOREST
		);
		tag(ESTags.Biomes.HAS_PORTAL_RUINS_DESERT).add(
			Biomes.DESERT,
			Biomes.BADLANDS,
			Biomes.ERODED_BADLANDS,
			Biomes.WOODED_BADLANDS
		);
		tag(ESTags.Biomes.HAS_GOLEM_FORGE).add(
			ESBiomes.STARLIGHT_FOREST,
			ESBiomes.STARLIGHT_DENSE_FOREST,
			ESBiomes.STARLIGHT_PERMAFROST_FOREST
		);
		tag(ESTags.Biomes.HAS_CURSED_GARDEN).add(
			ESBiomes.STARLIGHT_FOREST,
			ESBiomes.STARLIGHT_DENSE_FOREST
		);
	}
}