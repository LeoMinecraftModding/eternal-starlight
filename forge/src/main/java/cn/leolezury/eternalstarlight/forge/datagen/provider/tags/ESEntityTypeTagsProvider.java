package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public ESEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(ESTags.EntityTypes.ROBOTIC).add(
			ESEntities.FREEZE.get(),
			ESEntities.STARLIGHT_GOLEM.get()
		);
		tag(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS).add(
			ESEntities.TANGLED.get(),
			ESEntities.TANGLED_SKULL.get(),
			ESEntities.TANGLED_HATRED.get(),
			ESEntities.LUNAR_MONSTROSITY.get()
		);
		tag(ESTags.EntityTypes.ABYSSAL_FIRE_IMMUNE).add(
			ESEntities.LUMINOFISH.get(),
			ESEntities.LUMINARIS.get(),
			ESEntities.TWILIGHT_GAZE.get()
		);
		tag(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB).add(
			ESEntities.CRYSTALLIZED_MOTH.get(),
			EntityType.BAT,
			EntityType.WARDEN
		);
		tag(ESTags.EntityTypes.GLEECH_IMMUNE).add(
			ESEntities.GLEECH.get(),
			ESEntities.NIGHTFALL_SPIDER.get(),
			EntityType.SILVERFISH,
			EntityType.SPIDER,
			EntityType.CAVE_SPIDER
		);
		tag(EntityTypeTags.SKELETONS).add(
			ESEntities.LONESTAR_SKELETON.get(),
			ESEntities.TANGLED.get()
		);
		tag(EntityTypeTags.UNDEAD).add(
			ESEntities.TANGLED_SKULL.get()
		);
		tag(EntityTypeTags.ARTHROPOD).add(
			ESEntities.NIGHTFALL_SPIDER.get(),
			ESEntities.GLEECH.get()
		);
		tag(EntityTypeTags.AQUATIC).add(
			ESEntities.LUMINOFISH.get(),
			ESEntities.LUMINARIS.get(),
			ESEntities.TWILIGHT_GAZE.get()
		);
		tag(EntityTypeTags.IMMUNE_TO_INFESTED).add(
			ESEntities.GLEECH.get()
		);
		tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(
			ESEntities.ASTRAL_GOLEM.get(),
			ESEntities.CRYSTALLIZED_MOTH.get(),
			ESEntities.SHIMMER_LACEWING.get(),
			ESEntities.GRIMSTONE_GOLEM.get()
		);
	}
}
