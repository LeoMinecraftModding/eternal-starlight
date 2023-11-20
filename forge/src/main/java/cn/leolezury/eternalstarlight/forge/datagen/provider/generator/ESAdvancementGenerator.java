package cn.leolezury.eternalstarlight.forge.datagen.provider.generator;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.DimensionInit;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

public class ESAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
        AdvancementHolder root = Advancement.Builder.advancement().display(
                        BlockInit.LUNAR_LOG.get(),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".enter_starlight.title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".enter_starlight.description"),
                        new ResourceLocation(EternalStarlight.MOD_ID, "textures/block/lunar_log.png"),
                        FrameType.TASK,
                        true, false, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("in_dim",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.Builder.inDimension(DimensionInit.STARLIGHT_KEY)))
                .save(consumer, EternalStarlight.MOD_ID + ":enter_starlight");

        AdvancementHolder swampSilver = addItemObtain(consumer, root, "obtain_swamp_silver", ItemInit.SWAMP_SILVER_INGOT.get());

        AdvancementHolder starlightFlower = addItemObtain(consumer, root, "obtain_starlight_flower", ItemInit.STARLIGHT_FLOWER.get());

        AdvancementHolder seekingEye = addItemObtain(consumer, starlightFlower, "obtain_seeking_eye", ItemInit.SEEKING_EYE.get());

        AdvancementHolder killGolem = addEntityKill(consumer, seekingEye, "kill_golem", EntityInit.STARLIGHT_GOLEM.get(), ItemInit.ENERGY_BLOCK.get());

        AdvancementHolder golemSteelIngot = addItemObtain(consumer, killGolem, "obtain_golem_steel_ingot", ItemInit.GOLEM_STEEL_INGOT.get());

        AdvancementHolder thermalSpringstone = addItemObtain(consumer, killGolem, "obtain_thermal_springstone", ItemInit.THERMAL_SPRINGSTONE.get());

        AdvancementHolder killLunarMonstrosity = addEntityKill(consumer, thermalSpringstone, "kill_lunar_monstrosity", EntityInit.LUNAR_MONSTROSITY.get(), ItemInit.PARASOL_GRASS.get());
    }

    private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item item) {
        return Advancement.Builder.advancement().parent(parent).display(
                        item,
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".description"),
                        null, FrameType.GOAL, true, true, false)
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
                        null, FrameType.GOAL, true, true, false)
                .addCriterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(Optional.ofNullable(predicate)))
                .save(consumer, EternalStarlight.MOD_ID + ":" + id);
    }
}
