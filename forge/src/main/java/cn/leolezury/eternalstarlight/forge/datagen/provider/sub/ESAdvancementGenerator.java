package cn.leolezury.eternalstarlight.forge.datagen.provider.sub;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

public class ESAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
        HolderLookup.RegistryLookup<Biome> biomes = registries.lookupOrThrow(Registries.BIOME);

        AdvancementHolder root = Advancement.Builder.advancement().display(
                        ESBlocks.LUNAR_LOG.get(),
                        Component.translatable("advancements." + EternalStarlight.ID + ".root.title"),
                        Component.translatable("advancements." + EternalStarlight.ID + ".root.description"),
                        EternalStarlight.id("textures/block/lunar_log.png"),
                        AdvancementType.TASK,
                        false, false, false)
                .requirements(AdvancementRequirements.Strategy.OR)
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
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("challenged", ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                .save(consumer, EternalStarlight.ID + ":challenge_gatekeeper");

        AdvancementHolder enterDim = Advancement.Builder.advancement().parent(challengeGatekeeper).display(
                        ESBlocks.LUNAR_LOG.get(),
                        Component.translatable("advancements." + EternalStarlight.ID + ".enter_starlight.title"),
                        Component.translatable("advancements." + EternalStarlight.ID + ".enter_starlight.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("in_dim",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY)))
                .save(consumer, EternalStarlight.ID + ":enter_starlight");

        AdvancementHolder enterAbyss = addEnterBiome(consumer, enterDim, "enter_abyss", ESItems.ABYSSLATE.get(), biomes.getOrThrow(ESBiomes.THE_ABYSS));

        AdvancementHolder starlightFlower = addItemObtain(consumer, enterDim, "obtain_starlight_flower", ESItems.STARLIGHT_FLOWER.get());

        AdvancementHolder seekingEye = addItemObtain(consumer, starlightFlower, "obtain_seeking_eye", ESItems.SEEKING_EYE.get());

        AdvancementHolder throwGleechEgg = Advancement.Builder.advancement().parent(enterDim).display(
                        ESItems.GLEECH_EGG.get(),
                        Component.translatable("advancements." + EternalStarlight.ID + ".throw_gleech_egg.title"),
                        Component.translatable("advancements." + EternalStarlight.ID + ".throw_gleech_egg.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("thrown", ESCriteriaTriggers.THROW_GLEECH_EGG.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                .save(consumer, EternalStarlight.ID + ":throw_gleech_egg");

        AdvancementHolder swampSilver = addItemObtain(consumer, enterDim, "obtain_swamp_silver", ESItems.SWAMP_SILVER_INGOT.get());

        AdvancementHolder glacite = addItemObtain(consumer, enterDim, "obtain_glacite", ESItems.GLACITE_SHARD.get());

        AdvancementHolder alchemistMask = addItemObtain(consumer, enterDim, "obtain_alchemist_mask", ESItems.ALCHEMIST_MASK.get());

        AdvancementHolder aethersentIngot = addItemObtain(consumer, enterDim, "obtain_aethersent_ingot", ESItems.AETHERSENT_INGOT.get());

        AdvancementHolder rawAmaramber = addItemObtain(consumer, enterDim, "obtain_raw_amaramber", ESItems.RAW_AMARAMBER.get());

        AdvancementHolder killGolem = addEntityKill(consumer, seekingEye, "kill_golem", ESEntities.STARLIGHT_GOLEM.get(), ESItems.ENERGY_BLOCK.get());

        AdvancementHolder golemSteelIngot = addItemObtain(consumer, killGolem, "obtain_golem_steel_ingot", ESItems.GOLEM_STEEL_INGOT.get());

        AdvancementHolder thermalSpringstone = addItemObtain(consumer, killGolem, "obtain_thermal_springstone", ESItems.THERMAL_SPRINGSTONE.get());

        AdvancementHolder igniteLunarMonstrosity = Advancement.Builder.advancement().parent(thermalSpringstone).display(
                        ESItems.SALTPETER_MATCHBOX.get(),
                        Component.translatable("advancements." + EternalStarlight.ID + ".ignite_lunar_monstrosity.title"),
                        Component.translatable("advancements." + EternalStarlight.ID + ".ignite_lunar_monstrosity.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("ignite", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(ESTags.Items.LUNAR_MONSTROSITY_IGNITERS), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(ESEntities.LUNAR_MONSTROSITY.get()).build()))))
                .save(consumer, EternalStarlight.ID + ":ignite_lunar_monstrosity");

        AdvancementHolder killLunarMonstrosity = addEntityKill(consumer, igniteLunarMonstrosity, "kill_lunar_monstrosity", ESEntities.LUNAR_MONSTROSITY.get(), ESItems.PARASOL_GRASS.get());

        AdvancementHolder chainTangledSkullExplosion = Advancement.Builder.advancement().parent(killLunarMonstrosity).display(
                        ESItems.TANGLED_SKULL.get(),
                        Component.translatable("advancements." + EternalStarlight.ID + ".chain_tangled_skull_explosion.title"),
                        Component.translatable("advancements." + EternalStarlight.ID + ".chain_tangled_skull_explosion.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("explode", ESCriteriaTriggers.CHAIN_TANGLED_SKULL_EXPLOSION.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                .save(consumer, EternalStarlight.ID + ":chain_tangled_skull_explosion");

        AdvancementHolder useBlossomOfStars = Advancement.Builder.advancement().parent(enterDim).display(
                        ESItems.BLOSSOM_OF_STARS.get(),
                        Component.translatable("advancements." + EternalStarlight.ID + ".use_blossom_of_stars.title"),
                        Component.translatable("advancements." + EternalStarlight.ID + ".use_blossom_of_stars.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, true)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("use_item",
                        ConsumeItemTrigger.TriggerInstance.usedItem(ESItems.BLOSSOM_OF_STARS.get()))
                .save(consumer, EternalStarlight.ID + ":use_blossom_of_stars");
    }

    private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item item) {
        return Advancement.Builder.advancement().parent(parent).display(
                        item,
                        Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
                        null, AdvancementType.GOAL, true, true, false)
                .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item))
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
                        null, AdvancementType.GOAL, true, true, false)
                .addCriterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(Optional.ofNullable(predicate)))
                .save(consumer, EternalStarlight.ID + ":" + id);
    }

    private static AdvancementHolder addEnterBiome(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, Holder<Biome> biome) {
        return Advancement.Builder.advancement().parent(parent).display(
                        display,
                        Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.ID + "." + id + ".description"),
                        null, AdvancementType.GOAL, true, true, false)
                .addCriterion("in_biome", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biome)))
                .save(consumer, EternalStarlight.ID + ":" + id);
    }
}
