package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public ESEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		// conventional tags
		tag(Tags.EntityTypes.BOSSES)
			.add(
				ESEntities.THE_GATEKEEPER.get(),
				ESEntities.STARLIGHT_GOLEM.get(),
				ESEntities.LUNAR_MONSTROSITY.get(),
				ESEntities.SOLAR_CREEPER.get()
			);
		tag(Tags.EntityTypes.BOATS)
			.add(
				ESEntities.BOAT.get(),
				ESEntities.CHEST_BOAT.get()
			);
		tag(ESTags.EntityTypes.AFFECTS_PROGRESSION)
			.add(
				ESEntities.THIRST_WALKER.get(),
				ESEntities.STRANGHOUL.get(),
				ESEntities.SHIMMER_LACEWING.get(),
				ESEntities.TWILIGHT_GAZE.get(),
				ESEntities.FREEZE.get(),
				ESEntities.PERMAFROST.get(),
				ESEntities.TANGLED.get()
			);
		tag(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLIES)
			.add(
				ESEntities.FREEZE.get(),
				ESEntities.PERMAFROST.get(),
				ESEntities.STARLIGHT_GOLEM.get()
			);
		tag(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES)
			.add(
				ESEntities.TANGLED.get(),
				ESEntities.TANGLED_SKULL.get(),
				ESEntities.LUNAR_MONSTROSITY.get()
			);
		tag(ESTags.EntityTypes.ABYSSAL_FIRE_IMMUNE)
			.add(
				ESEntities.LUMINOFISH.get(),
				ESEntities.LUMINARIS.get(),
				ESEntities.TWILIGHT_GAZE.get()
			);
		tag(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB)
			.add(
				EntityType.BAT,
				EntityType.WARDEN
			);
		tag(ESTags.EntityTypes.GLEECH_IMMUNE)
			.add(
				ESEntities.GLEECH.get(),
				ESEntities.NIGHTFALL_SPIDER.get(),
				EntityType.SILVERFISH,
				EntityType.SPIDER,
				EntityType.CAVE_SPIDER
			);
		tag(ESTags.EntityTypes.CHAIN_OF_SOULS_CANNOT_PULL)
			.addTag(Tags.EntityTypes.BOSSES);
		tag(ESTags.EntityTypes.TEARY_IMMUNE)
			.addTag(Tags.EntityTypes.BOSSES);
		tag(ESTags.EntityTypes.STRANGHOUL_PREYS)
			.addTag(EntityTypeTags.ZOMBIES);
		tag(ESTags.EntityTypes.STRANGHOUL_CANNOT_HUNT)
			.addTag(Tags.EntityTypes.BOSSES);
		tag(ESTags.EntityTypes.STARFIRE_BIRD_AFRAID_OF)
			.add(
				EntityType.PLAYER
			)
			.addTag(EntityTypeTags.ARTHROPOD);
		tag(ESTags.EntityTypes.AETHERSENT_GOLEM_TARGETS)
			.add(
				ESEntities.SEEKER.get(),
				ESEntities.TINY_CRETEOR.get(),
				EntityType.PHANTOM
			);
		tag(ESTags.EntityTypes.SOLARIS_ISLES_INHABITANTS)
			.add(
				ESEntities.ENT.get(),
				ESEntities.RATLIN.get(),
				ESEntities.SHADOW_SNAIL.get()
			);
		tag(EntityTypeTags.SKELETONS)
			.add(
				ESEntities.LONESTAR_SKELETON.get(),
				ESEntities.TANGLED.get()
			);
		tag(EntityTypeTags.ZOMBIES)
			.add(
				ESEntities.ZOMBIFIED_RATLIN.get()
			);
		tag(EntityTypeTags.ARROWS)
			.add(
				ESEntities.THIOQUARTZ_ARROW.get(),
				ESEntities.AETHERSENT_ARROW.get(),
				ESEntities.GLACITE_ARROW.get(),
				ESEntities.MALARITE_ARROW.get(),
				ESEntities.AMARAMBER_ARROW.get(),
				ESEntities.VORACIOUS_ARROW.get(),
				ESEntities.AIR_SAC_ARROW.get()
			);
		tag(EntityTypeTags.IMPACT_PROJECTILES)
			.add(
				ESEntities.AETHERSENT_METEOR.get(),
				ESEntities.AETHERSTRIKE_ROCKET.get(),
				ESEntities.GLEECH_EGG.get(),
				ESEntities.FROZEN_TUBE.get(),
				ESEntities.LUNAR_SPORE.get(),
				ESEntities.SHATTERED_BLADE.get(),
				ESEntities.MALARITE_SPEAR.get(),
				ESEntities.PUNGENCY_FRUIT_SPEAR.get(),
				ESEntities.THIOQUARTZ_SHARD.get(),
				ESEntities.SONAR_BOMB.get(),
				ESEntities.ASHEN_SNOWBALL.get(),
				ESEntities.FROZEN_BOMB.get(),
				ESEntities.WILTED_PETAL.get(),
				ESEntities.SHOT_SEEDS.get()
			);
		tag(EntityTypeTags.UNDEAD)
			.add(
				ESEntities.LONESTAR_SKELETON.get(),
				ESEntities.TANGLED.get(),
				ESEntities.TANGLED_SKULL.get()
			);
		tag(EntityTypeTags.ARTHROPOD)
			.add(
				ESEntities.NIGHTFALL_SPIDER.get(),
				ESEntities.GLEECH.get()
			);
		tag(EntityTypeTags.AQUATIC)
			.add(
				ESEntities.ROOKFISH.get(),
				ESEntities.LUMINOFISH.get(),
				ESEntities.LUMINARIS.get(),
				ESEntities.TWILIGHT_GAZE.get()
			);
		tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(
				ESEntities.ROOKFISH.get(),
				ESEntities.LUMINOFISH.get(),
				ESEntities.LUMINARIS.get(),
				ESEntities.TWILIGHT_GAZE.get()
			);
		tag(EntityTypeTags.IMMUNE_TO_INFESTED)
			.add(
				ESEntities.GLEECH.get()
			);
		tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(
				ESEntities.FREEZE.get(),
				ESEntities.PERMAFROST.get()
			);
		tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
			.add(
				ESEntities.ASTRAL_GOLEM.get(),
				ESEntities.SEEKER.get(),
				ESEntities.CRETEOR.get(),
				ESEntities.TINY_CRETEOR.get(),
				ESEntities.CRYSTALLIZED_MOTH.get(),
				ESEntities.SHIMMER_LACEWING.get(),
				ESEntities.STARFIRE_BIRD.get(),
				ESEntities.GRIMSTONE_GOLEM.get(),
				ESEntities.AETHERSENT_GOLEM.get(),
				ESEntities.THE_GATEKEEPER.get(),
				ESEntities.STARLIGHT_GOLEM.get(),
				ESEntities.FREEZE.get(),
				ESEntities.PERMAFROST.get(),
				ESEntities.LUNAR_MONSTROSITY.get(),
				ESEntities.TANGLED_SKULL.get(),
				ESEntities.SOLAR_CREEPER.get()
			);
	}
}
