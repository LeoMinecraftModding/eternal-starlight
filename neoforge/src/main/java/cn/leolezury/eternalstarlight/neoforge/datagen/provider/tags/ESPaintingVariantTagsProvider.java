package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariant;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESPaintingVariantTagsProvider extends TagsProvider<ESPaintingVariant> {
	public ESPaintingVariantTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, ESRegistries.PAINTING_VARIANT, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(ESTags.PaintingVariants.PLACEABLE)
			.add(
				ESPaintingVariants.THE_CATALYST,
				ESPaintingVariants.CRYSTALBORN,
				ESPaintingVariants.VOIDSTONE_APPLE,
				ESPaintingVariants.PUNGENCY_FRUIT_FEAST,
				ESPaintingVariants.STARFLOWERS,
				ESPaintingVariants.COLD_ANOMALY,
				ESPaintingVariants.NORTHLAND_HUT,
				ESPaintingVariants.RAT_HEAD,
				ESPaintingVariants.ORIGINAL_CRETEOR,
				ESPaintingVariants.TWILIGHT_SQUID,
				ESPaintingVariants.POWER,
				ESPaintingVariants.POT_OF_CRESTS,
				ESPaintingVariants.THIRSTY,
				ESPaintingVariants.STACK,
				ESPaintingVariants.CARVED_SLATE,
				ESPaintingVariants.INSECT_SPECIMEN,
				ESPaintingVariants.SWORD_OF_THE_LAKE,
				ESPaintingVariants.SKELETON_LONESTAR,
				ESPaintingVariants.HYMN_OF_THE_RATS,
				ESPaintingVariants.COOLER,
				ESPaintingVariants.RIVEN_WELKIN,
				ESPaintingVariants.UMBROUS_ALCHEMIST,
				ESPaintingVariants.EXTINGUISHED_SUN,
				ESPaintingVariants.NOVUS_SOL,
				ESPaintingVariants.MONSTROSITY_HUNTER,
				ESPaintingVariants.IMAGINARY,
				ESPaintingVariants.A_THOUSAND_SUNS,
				ESPaintingVariants.THE_DARK_SIDE_OF_A_STAR,
				ESPaintingVariants.SPACE_OF_COLOR_LUMPS,
				ESPaintingVariants.JORMUNGAND
			);
	}
}
