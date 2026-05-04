package cn.leolezury.eternalstarlight.neoforge.datagen.provider.advancement;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.critereon.WitnessWeatherTrigger;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ESAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
	@Override
	public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
		HolderLookup.RegistryLookup<Biome> biomes = registries.lookupOrThrow(Registries.BIOME);
		HolderLookup.RegistryLookup<Structure> structures = registries.lookupOrThrow(Registries.STRUCTURE);
		HolderLookup.RegistryLookup<Fluid> fluids = registries.lookupOrThrow(Registries.FLUID);

		AdvancementHolder root = Advancement.Builder.advancement().display(
				ESBlocks.LUNAR_LOG.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".root.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".root.description"),
				EternalStarlight.id("textures/block/lunar_log.png"),
				AdvancementType.TASK,
				false, false, false)
			.addCriterion("in_dim",
				PlayerTrigger.TriggerInstance.located(
					LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY)))
			.save(consumer, EternalStarlight.ID + ":root");

		AdvancementHolder challengeGatekeeper = Advancement.Builder.advancement().parent(root).display(
				Items.DIAMOND_SWORD,
				Component.translatable("advancements." + EternalStarlight.ID + ".challenge_gatekeeper.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".challenge_gatekeeper.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("challenged", ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":challenge_gatekeeper");

		AdvancementHolder obtainOrbOfProphecy = addItemObtain(consumer, challengeGatekeeper, "obtain_orb_of_prophecy", ESItems.ORB_OF_PROPHECY.get());

		AdvancementHolder enterDim = Advancement.Builder.advancement().parent(obtainOrbOfProphecy).display(
				ESBlocks.LUNAR_LOG.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".enter_starlight.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".enter_starlight.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("in_dim",
				PlayerTrigger.TriggerInstance.located(
					LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY)))
			.save(consumer, EternalStarlight.ID + ":enter_starlight");

		AdvancementHolder seekingEye = addItemObtain(consumer, enterDim, "obtain_seeking_eye", ESItems.SEEKING_EYE.get());

		AdvancementHolder scythe = addItemObtain(consumer, enterDim, "obtain_scythe", ESTags.Items.SCYTHES, ESItems.PETAL_SCYTHE.get());

		AdvancementHolder enterAbyss = addInBiome(consumer, enterDim, "enter_abyss", ESItems.ABYSSLATE.get(), biomes.getOrThrow(ESBiomes.THE_ABYSS));

		AdvancementHolder redVelvetumossFlower = addItemObtain(consumer, enterAbyss, "obtain_red_velvetumoss_flower", ESItems.RED_VELVETUMOSS_FLOWER.get());

		AdvancementHolder underPermafrostForest = Advancement.Builder.advancement().parent(enterDim).display(
				ESBlocks.ICICLE.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".under_permafrost_forest.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".under_permafrost_forest.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("in_biome",
				PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder
					.location()
					.setBiomes(biomes.getOrThrow(ESTags.Biomes.PERMAFROST))
					.setY(MinMaxBounds.Doubles.atMost(-5))))
			.save(consumer, EternalStarlight.ID + ":under_permafrost_forest");

		AdvancementHolder glaciteShard = addItemObtain(consumer, underPermafrostForest, "obtain_glacite_shard", ESItems.GLACITE_SHARD.get());

		AdvancementHolder glaciteArrow = addItemObtain(consumer, glaciteShard, "obtain_glacite_arrow", ESItems.GLACITE_ARROW.get());

		AdvancementHolder frozenBomb = addItemObtain(consumer, underPermafrostForest, "obtain_frozen_bomb", ESItems.FROZEN_BOMB.get());

		AdvancementHolder auroraDeerAntler = addItemObtain(consumer, underPermafrostForest, "obtain_aurora_deer_antler", ESItems.AURORA_DEER_ANTLER.get());

		AdvancementHolder enterCrystallizedDesert = addInBiome(consumer, enterDim, "enter_crystallized_desert", ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get(), biomes.getOrThrow(ESBiomes.CRYSTALLIZED_DESERT));

		AdvancementHolder crinoaSeeds = addItemObtain(consumer, enterCrystallizedDesert, "obtain_crinoa_seeds", ESItems.CRINOA_SEEDS.get());

		AdvancementHolder toothOfHunger = addItemObtain(consumer, enterCrystallizedDesert, "obtain_tooth_of_hunger", ESItems.TOOTH_OF_HUNGER.get());

		AdvancementHolder daggerOfHunger = addItemObtain(consumer, toothOfHunger, "obtain_dagger_of_hunger", ESItems.DAGGER_OF_HUNGER.get());

		AdvancementHolder saturateDaggerOfHunger = Advancement.Builder.advancement().parent(daggerOfHunger).display(
				ESItems.DAGGER_OF_HUNGER.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".saturate_dagger_of_hunger.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".saturate_dagger_of_hunger.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("saturate", ESCriteriaTriggers.SATURATE_DAGGER_OF_HUNGER.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":saturate_dagger_of_hunger");

		AdvancementHolder toothOfHungerBlocks = addItemObtain(consumer, toothOfHunger, "obtain_tooth_of_hunger_blocks", ESTags.Items.TOOTH_OF_HUNGER_BLOCKS, ESItems.CHISELED_TOOTH_OF_HUNGER_TILES.get());

		AdvancementHolder crystalbornCatalyst = addItemObtain(consumer, toothOfHunger, "obtain_crystalborn_catalyst", ESItems.CRYSTALBORN_CATALYST.get());

		AdvancementHolder summonGrimstoneGolem = addEntitySummon(consumer, enterCrystallizedDesert, "summon_grimstone_golem", ESEntities.GRIMSTONE_GOLEM.get(), ESItems.GRIMSTONE_BRICKS.get());

		AdvancementHolder throwGleechEgg = Advancement.Builder.advancement().parent(enterCrystallizedDesert).display(
				ESItems.GLEECH_EGG.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".throw_gleech_egg.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".throw_gleech_egg.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("thrown", ESCriteriaTriggers.THROW_GLEECH_EGG.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":throw_gleech_egg");

		AdvancementHolder tameCrystallizedMoth = Advancement.Builder.advancement().parent(enterCrystallizedDesert).display(
				ESItems.SHIVERING_GEL.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".tame_crystallized_moth.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".tame_crystallized_moth.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("tame", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(ESEntities.CRYSTALLIZED_MOTH.get())))
			.save(consumer, EternalStarlight.ID + ":tame_crystallized_moth");

		AdvancementHolder inEtherFluid = addInFluid(consumer, enterDim, "in_ether_fluid", ESItems.ETHER_BUCKET.get(), fluids.getOrThrow(ESTags.Fluids.ETHER));

		AdvancementHolder forgottenNocturnalMillet = addItemObtain(consumer, inEtherFluid, "obtain_forgotten_nocturnal_millet", ESItems.FORGOTTEN_NOCTURNAL_MILLET.get());

		AdvancementHolder thioquartzShard = addItemObtain(consumer, inEtherFluid, "obtain_thioquartz_shard", ESItems.THIOQUARTZ_SHARD.get());

		AdvancementHolder alchemistMask = addItemObtain(consumer, thioquartzShard, "obtain_alchemist_mask", ESItems.ALCHEMIST_MASK.get());

		AdvancementHolder witnessMeteorShower = Advancement.Builder.advancement().parent(enterDim).display(
				ESItems.RAW_AETHERSENT.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".witness_meteor_shower.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".witness_meteor_shower.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("witnessed", ESCriteriaTriggers.WITNESS_WEATHER.get().createCriterion(new WitnessWeatherTrigger.TriggerInstance(Optional.empty(), ESWeathers.METEOR_SHOWER.asHolder())))
			.save(consumer, EternalStarlight.ID + ":witness_meteor_shower");

		AdvancementHolder aethersentIngot = addItemObtain(consumer, witnessMeteorShower, "obtain_aethersent_ingot", ESItems.AETHERSENT_INGOT.get());

		AdvancementHolder summonAethersentGolem = addEntitySummon(consumer, aethersentIngot, "summon_aethersent_golem", ESEntities.AETHERSENT_GOLEM.get(), ESItems.STARFALL_LONGBOW.get());

		AdvancementHolder killCreteor = addEntityKill(consumer, witnessMeteorShower, "kill_creteor", ESEntities.CRETEOR.get(), ESItems.CRETEOR_HIDE.get());

		AdvancementHolder aetherstrikeRocket = addItemObtain(consumer, killCreteor, "obtain_aetherstrike_rocket", ESItems.AETHERSTRIKE_ROCKET.get());

		AdvancementHolder deepsilverIngot = addItemObtain(consumer, enterDim, "obtain_deepsilver_ingot", ESItems.DEEPSILVER_INGOT.get());

		AdvancementHolder numbnessEffect = Advancement.Builder.advancement().parent(deepsilverIngot).display(
				ESItems.SILVER_PUNGENCY_FRUIT.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".obtain_numbness_effect.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".obtain_numbness_effect.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("obtain", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(ESMobEffects.NUMBNESS.asHolder())))
			.save(consumer, EternalStarlight.ID + ":obtain_numbness_effect");

		AdvancementHolder starlitDiamond = addItemObtain(consumer, enterDim, "obtain_starlit_diamond", ESItems.STARLIT_DIAMOND.get());

		AdvancementHolder igniteTearBomb = Advancement.Builder.advancement().parent(enterDim).display(
				ESItems.TEAR_BOMB.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".ignite_tear_bomb.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".ignite_tear_bomb.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("ignite", ESCriteriaTriggers.IGNITE_TEAR_BOMB.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":ignite_tear_bomb");

		AdvancementHolder witnessStranghoulHunt = Advancement.Builder.advancement().parent(enterDim).display(
				ESItems.MALARITE_SWORD.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".witness_stranghoul_hunt.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".witness_stranghoul_hunt.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("witness", ESCriteriaTriggers.WITNESS_STRANGHOUL_HUNT.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":witness_stranghoul_hunt");

		AdvancementHolder hireStranghoul = Advancement.Builder.advancement().parent(witnessStranghoulHunt).display(
				ESItems.PUNGENCY_STEW.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".hire_stranghoul.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".hire_stranghoul.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("hire", ESCriteriaTriggers.HIRE_STRANGHOUL.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":hire_stranghoul");

		AdvancementHolder hammerCriticalHit = Advancement.Builder.advancement().parent(enterDim).display(
				ESItems.STARFIRE_HAMMER.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".hammer_critical_hit.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".hammer_critical_hit.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("critical_hit", ESCriteriaTriggers.HAMMER_CRITICAL_HIT.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":hammer_critical_hit");

		AdvancementHolder putSeedsIntoStarfireBirdNest = Advancement.Builder.advancement().parent(enterDim).display(
				ESItems.STARFIRE_BIRD_NEST.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".put_seeds_into_starfire_bird_nest.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".put_seeds_into_starfire_bird_nest.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("put_seeds", ESCriteriaTriggers.PUT_SEEDS_INTO_STARFIRE_BIRD_NEST.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":put_seeds_into_starfire_bird_nest");

		AdvancementHolder starfire = addItemObtain(consumer, putSeedsIntoStarfireBirdNest, "obtain_starfire", ESItems.STARFIRE.get());

		AdvancementHolder flowglaze = addItemObtain(consumer, starfire, "obtain_flowglaze", ESItems.FLOWGLAZE.get());

		AdvancementHolder thermalSpringstone = addItemObtain(consumer, enterDim, "obtain_thermal_springstone", ESItems.THERMAL_SPRINGSTONE.get());

		AdvancementHolder rawAmaramber = addItemObtain(consumer, enterDim, "obtain_raw_amaramber", ESItems.RAW_AMARAMBER.get());

		AdvancementHolder stellagmite = addItemObtain(consumer, enterDim, "obtain_stellagmite", ESItems.STELLAGMITE.get());

		AdvancementHolder bouldershroomStew = addItemObtain(consumer, enterDim, "obtain_bouldershroom_stew", ESItems.BOULDERSHROOM_STEW.get());

		AdvancementHolder dustedShard = addItemObtain(consumer, enterDim, "obtain_dusted_shard", ESItems.DUSTED_SHARD.get());

		AdvancementHolder jinglestemSandwich = addItemObtain(consumer, enterDim, "obtain_jinglestem_sandwich", ESItems.JINGLESTEM_SANDWICH.get());

		Advancement.Builder allStarlightBiomesBuilder = Advancement.Builder.advancement().parent(enterDim).display(
				ESBlocks.NIGHTFALL_GRASS_BLOCK.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".all_starlight_biomes.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".all_starlight_biomes.description"),
				null,
				AdvancementType.CHALLENGE,
				true, true, false)
			.requirements(AdvancementRequirements.Strategy.AND)
			.rewards(AdvancementRewards.Builder.experience(500));
		List<ResourceKey<Biome>> biomeIds = biomes.listElementIds().sorted(ResourceKey::compareTo).toList();
		for (ResourceKey<Biome> key : biomeIds) {
			if (key.location().getNamespace().equals(EternalStarlight.ID)) {
				allStarlightBiomesBuilder.addCriterion("in_" + key.location().getPath(), PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(key))));
			}
		}
		AdvancementHolder allStarlightBiomes = allStarlightBiomesBuilder.save(consumer, EternalStarlight.ID + ":all_starlight_biomes");

		AdvancementHolder enterGolemForge = addInStructure(consumer, seekingEye, "enter_golem_forge", ESItems.GOLEM_STEEL_PILLAR.get(), structures.getOrThrow(ESStructures.GOLEM_FORGE));

		AdvancementHolder killPermafrost = addEntityKill(consumer, enterGolemForge, "kill_permafrost", ESEntities.PERMAFROST.get(), ESItems.COLDSNAP.get());

		AdvancementHolder freezeStarlightGolem = Advancement.Builder.advancement().parent(enterGolemForge).display(
				ESItems.FROZEN_TUBE.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".freeze_starlight_golem.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".freeze_starlight_golem.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("freeze", ESCriteriaTriggers.FREEZE_STARLIGHT_GOLEM.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":freeze_starlight_golem");

		AdvancementHolder deactivateEnergyBlock = Advancement.Builder.advancement().parent(freezeStarlightGolem).display(
				ESItems.ENERGY_BLOCK.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".deactivate_energy_block.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".deactivate_energy_block.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("deactivate", ESCriteriaTriggers.DEACTIVATE_ENERGY_BLOCK.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":deactivate_energy_block");

		AdvancementHolder killGolem = addEntityKill(consumer, deactivateEnergyBlock, "kill_golem", ESEntities.STARLIGHT_GOLEM.get(), ESItems.CHISELED_GOLEM_STEEL_BLOCK.get());

		AdvancementHolder golemSteelIngot = addItemObtain(consumer, killGolem, "obtain_golem_steel_ingot", ESItems.GOLEM_STEEL_INGOT.get());

		AdvancementHolder igniteLunarMonstrosity = Advancement.Builder.advancement().parent(killGolem).display(
				ESItems.SALTPETER_MATCHBOX.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".ignite_lunar_monstrosity.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".ignite_lunar_monstrosity.description"),
				null,
				AdvancementType.TASK,
				true, true, false)
			.addCriterion("ignite", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(ESTags.Items.LUNAR_MONSTROSITY_IGNITERS), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(ESEntities.LUNAR_MONSTROSITY.get()).build()))))
			.save(consumer, EternalStarlight.ID + ":ignite_lunar_monstrosity");

		AdvancementHolder killLunarMonstrosity = addEntityKill(consumer, igniteLunarMonstrosity, "kill_lunar_monstrosity", ESEntities.LUNAR_MONSTROSITY.get(), ESItems.PARASOL_GRASS.get());

		AdvancementHolder crescentSpear = addItemObtain(consumer, killLunarMonstrosity, "obtain_crescent_spear", ESItems.CRESCENT_SPEAR.get());

		AdvancementHolder chainTangledSkullExplosion = Advancement.Builder.advancement().parent(killLunarMonstrosity).display(
				ESItems.TANGLED_SKULL.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".chain_tangled_skull_explosion.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".chain_tangled_skull_explosion.description"),
				null,
				AdvancementType.CHALLENGE,
				true, true, false)
			.rewards(AdvancementRewards.Builder.experience(60))
			.addCriterion("explode", ESCriteriaTriggers.CHAIN_TANGLED_SKULL_EXPLOSION.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
			.save(consumer, EternalStarlight.ID + ":chain_tangled_skull_explosion");

		Advancement.Builder.advancement().parent(killLunarMonstrosity).display(
				ESItems.UNREALIUM_HELMET.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".full_unrealium_armor.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".full_unrealium_armor.description"),
				null, AdvancementType.CHALLENGE, true, true, false)
			.rewards(AdvancementRewards.Builder.experience(100))
			.addCriterion("has_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ESItems.UNREALIUM_HELMET.get()))
			.addCriterion("has_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ESItems.UNREALIUM_CHESTPLATE.get()))
			.addCriterion("has_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ESItems.UNREALIUM_LEGGINGS.get()))
			.addCriterion("has_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ESItems.UNREALIUM_BOOTS.get()))
			.save(consumer, EternalStarlight.ID + ":full_unrealium_armor");

		AdvancementHolder useBlossomOfStars = Advancement.Builder.advancement().parent(enterDim).display(
				ESItems.BLOSSOM_OF_STARS.get(),
				Component.translatable("advancements." + EternalStarlight.ID + ".use_blossom_of_stars.title"),
				Component.translatable("advancements." + EternalStarlight.ID + ".use_blossom_of_stars.description"),
				null,
				AdvancementType.CHALLENGE,
				true, true, true)
			.addCriterion("use_item",
				ConsumeItemTrigger.TriggerInstance.usedItem(ESItems.BLOSSOM_OF_STARS.get()))
			.save(consumer, EternalStarlight.ID + ":use_blossom_of_stars");
	}

	private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item item) {
		return Advancement.Builder.advancement().parent(parent).display(
				item,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}

	private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, TagKey<Item> tag, Item item) {
		return Advancement.Builder.advancement().parent(parent).display(
				item,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag)))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}

	private static AdvancementHolder addEntityKill(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityType<?> entity, Item item) {
		return addEntityKill(consumer, parent, id, EntityPredicate.Builder.entity().of(entity).build(), item);
	}

	private static AdvancementHolder addEntityKill(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityPredicate predicate, Item item) {
		return Advancement.Builder.advancement().parent(parent).display(
				item,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(Optional.ofNullable(predicate)))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}

	private static AdvancementHolder addEntitySummon(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityType<?> entity, Item item) {
		return addEntitySummon(consumer, parent, id, EntityPredicate.Builder.entity().of(entity), item);
	}

	private static AdvancementHolder addEntitySummon(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityPredicate.Builder predicate, Item item) {
		return Advancement.Builder.advancement().parent(parent).display(
				item,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("summon", SummonedEntityTrigger.TriggerInstance.summonedEntity(predicate))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}

	private static AdvancementHolder addInBiome(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, Holder<Biome> biome) {
		return Advancement.Builder.advancement().parent(parent).display(
				display,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("in_biome", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biome)))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}

	private static AdvancementHolder addInStructure(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, Holder<Structure> structure) {
		return Advancement.Builder.advancement().parent(parent).display(
				display,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("in_structure", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structure)))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}

	private static AdvancementHolder addInFluid(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, HolderSet<Fluid> fluids) {
		return Advancement.Builder.advancement().parent(parent).display(
				display,
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
				Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
				null, AdvancementType.TASK, true, true, false)
			.addCriterion("in_fluid", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(fluids))))
			.save(consumer, EternalStarlight.ID + ":" + id);
	}
}
