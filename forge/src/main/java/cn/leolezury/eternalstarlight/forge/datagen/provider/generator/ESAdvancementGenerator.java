package cn.leolezury.eternalstarlight.forge.datagen.provider.generator;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

public class ESAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
        AdvancementHolder root = Advancement.Builder.advancement().display(
                        ESBlocks.LUNAR_LOG.get(),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".root.title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".root.description"),
                        new ResourceLocation(EternalStarlight.MOD_ID, "textures/block/lunar_log.png"),
                        AdvancementType.TASK,
                        false, false, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("in_dim",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY)))
                .save(consumer, EternalStarlight.MOD_ID + ":root");

        AdvancementHolder challengeGatekeeper = Advancement.Builder.advancement().parent(root).display(
                        Items.DIAMOND_SWORD,
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".challenge_gatekeeper.title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".challenge_gatekeeper.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("challenged", ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                .save(consumer, EternalStarlight.MOD_ID + ":challenge_gatekeeper");

        AdvancementHolder enterDim = Advancement.Builder.advancement().parent(challengeGatekeeper).display(
                        ESBlocks.LUNAR_LOG.get(),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".enter_starlight.title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".enter_starlight.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("in_dim",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY)))
                .save(consumer, EternalStarlight.MOD_ID + ":enter_starlight");

        AdvancementHolder swampSilver = addItemObtain(consumer, enterDim, "obtain_swamp_silver", ESItems.SWAMP_SILVER_INGOT.get());

        AdvancementHolder starlightFlower = addItemObtain(consumer, enterDim, "obtain_starlight_flower", ESItems.STARLIGHT_FLOWER.get());

        AdvancementHolder seekingEye = addItemObtain(consumer, starlightFlower, "obtain_seeking_eye", ESItems.SEEKING_EYE.get());

        AdvancementHolder killGolem = addEntityKill(consumer, seekingEye, "kill_golem", ESEntities.STARLIGHT_GOLEM.get(), ESItems.ENERGY_BLOCK.get());

        AdvancementHolder golemSteelIngot = addItemObtain(consumer, killGolem, "obtain_golem_steel_ingot", ESItems.GOLEM_STEEL_INGOT.get());

        AdvancementHolder thermalSpringstone = addItemObtain(consumer, killGolem, "obtain_thermal_springstone", ESItems.THERMAL_SPRINGSTONE.get());

        AdvancementHolder killLunarMonstrosity = addEntityKill(consumer, thermalSpringstone, "kill_lunar_monstrosity", ESEntities.LUNAR_MONSTROSITY.get(), ESItems.PARASOL_GRASS.get());
    }

    private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item item) {
        return Advancement.Builder.advancement().parent(parent).display(
                        item,
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".description"),
                        null, AdvancementType.GOAL, true, true, false)
                .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item))
                .save(consumer, EternalStarlight.MOD_ID + ":" + id);
    }

    private static AdvancementHolder addEntityKill(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityType<?> entity, Item item) {
        return addEntityKill(consumer, parent, id, EntityPredicate.Builder.entity().of(entity).build(), item);
    }

    private static AdvancementHolder addEntityKill(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityPredicate predicate, Item item) {
        return Advancement.Builder.advancement().parent(parent).display(
                        item,
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".description"),
                        null, AdvancementType.GOAL, true, true, false)
                .addCriterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(Optional.ofNullable(predicate)))
                .save(consumer, EternalStarlight.MOD_ID + ":" + id);
    }
}
