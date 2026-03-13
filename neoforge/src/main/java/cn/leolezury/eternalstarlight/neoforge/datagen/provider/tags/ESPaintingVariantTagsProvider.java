package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESPaintingVariantTagsProvider extends PaintingVariantTagsProvider {
	public ESPaintingVariantTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
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
				ESPaintingVariants.NOVUS_SOL
			);
	}
}