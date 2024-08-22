package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESMobEffectTagsProvider extends MobEffectTagsProvider {
	public ESMobEffectTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(ESTags.MobEffects.SWAMP_SILVER_ARMOR_CAN_REMOVE).add(
			MobEffects.POISON.value(),
			MobEffects.WITHER.value(),
			MobEffects.CONFUSION.value(),
			MobEffects.HUNGER.value(),
			MobEffects.MOVEMENT_SLOWDOWN.value(),
			MobEffects.INFESTED.value(),
			MobEffects.BLINDNESS.value()
		);
	}
}