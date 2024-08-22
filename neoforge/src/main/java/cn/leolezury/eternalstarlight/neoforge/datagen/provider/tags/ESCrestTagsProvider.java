package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESCrestTagsProvider extends CrestTagsProvider {
	public ESCrestTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(ESTags.Crests.IS_IN_CREST_POT).add(
			ESCrests.BURST_SPARK,
			ESCrests.FLAMING_AFTERSHOCK,
			ESCrests.FLAMING_ARC,
			ESCrests.FLAMING_RING,
			ESCrests.MERGED_FIREBALL,
			ESCrests.SURROUNDING_FIREBALLS,
			ESCrests.FROZEN_FOG,
			ESCrests.ICY_SPIKES
		);
	}
}