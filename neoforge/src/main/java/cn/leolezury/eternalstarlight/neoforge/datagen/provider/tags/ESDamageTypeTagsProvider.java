package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ESDamageTypeTagsProvider extends TagsProvider<DamageType> {
	public ESDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, Registries.DAMAGE_TYPE, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(ESTags.DamageTypes.BYPASSES_CRESCENT_PENDANT)
			.add(
				ESDamageTypes.NUMBNESS
			);
		tag(DamageTypeTags.BYPASSES_ARMOR)
			.add(
				ESDamageTypes.ETHER,
				ESDamageTypes.CRYSTAL_INFECTION,
				ESDamageTypes.SOUL_ABSORB
			);
		tag(DamageTypeTags.BYPASSES_ENCHANTMENTS)
			.add(
				ESDamageTypes.ETHER,
				ESDamageTypes.CRYSTAL_INFECTION,
				ESDamageTypes.SOUL_ABSORB
			);
		tag(DamageTypeTags.BYPASSES_SHIELD)
			.add(
				ESDamageTypes.ETHER,
				ESDamageTypes.CRYSTAL_INFECTION,
				ESDamageTypes.SONAR,
				ESDamageTypes.SOUL_ABSORB,
				ESDamageTypes.NUMBNESS
			);
		tag(DamageTypeTags.NO_IMPACT)
			.add(
				ESDamageTypes.ETHER,
				ESDamageTypes.CRYSTAL_INFECTION,
				ESDamageTypes.SONAR,
				ESDamageTypes.DAGGER_OF_HUNGER,
				ESDamageTypes.SOUL_ABSORB,
				ESDamageTypes.NUMBNESS,
				ESDamageTypes.STARFIRE,
				ESDamageTypes.LASER,
				ESDamageTypes.ELECTRIC_SHOCK,
				ESDamageTypes.POISON,
				ESDamageTypes.ENERGIZED_FLAME
			);
		tag(DamageTypeTags.NO_KNOCKBACK)
			.add(
				ESDamageTypes.ETHER,
				ESDamageTypes.CRYSTAL_INFECTION,
				ESDamageTypes.SONAR,
				ESDamageTypes.DAGGER_OF_HUNGER,
				ESDamageTypes.SOUL_ABSORB,
				ESDamageTypes.NUMBNESS,
				ESDamageTypes.STARFIRE,
				ESDamageTypes.LASER,
				ESDamageTypes.ELECTRIC_SHOCK,
				ESDamageTypes.POISON,
				ESDamageTypes.ENERGIZED_FLAME
			);
		tag(DamageTypeTags.IS_PROJECTILE)
			.add(
				ESDamageTypes.WILT,
				ESDamageTypes.SHATTERED_BLADE,
				ESDamageTypes.SEEDS
			);
		tag(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS)
			.add(
				ESDamageTypes.METEOR,
				ESDamageTypes.SHATTERED_BLADE,
				ESDamageTypes.SEEDS,
				ESDamageTypes.LASER
			);
		tag(DamageTypeTags.PANIC_CAUSES)
			.add(
				ESDamageTypes.METEOR,
				ESDamageTypes.WILT,
				ESDamageTypes.GROUND_SMASH,
				ESDamageTypes.SHATTERED_BLADE,
				ESDamageTypes.SONAR,
				ESDamageTypes.SOUL_ABSORB,
				ESDamageTypes.SEEDS,
				ESDamageTypes.STARFIRE,
				ESDamageTypes.LASER,
				ESDamageTypes.ENERGIZED_FLAME,
				ESDamageTypes.FREEZE,
				ESDamageTypes.ELECTRIC_SHOCK,
				ESDamageTypes.POISON,
				ESDamageTypes.BITE
			);
	}
}
