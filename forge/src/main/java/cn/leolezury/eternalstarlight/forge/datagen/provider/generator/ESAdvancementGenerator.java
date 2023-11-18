package cn.leolezury.eternalstarlight.forge.datagen.provider.generator;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.DimensionInit;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ESAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper helper) {
        Advancement root = Advancement.Builder.advancement().display(
                        BlockInit.LUNAR_LOG.get(),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".enter_starlight.title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + ".enter_starlight.description"),
                        new ResourceLocation(EternalStarlight.MOD_ID, "textures/block/lunar_log.png"),
                        FrameType.TASK,
                        true, false, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("in_dim",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.inDimension(DimensionInit.STARLIGHT_KEY)))
                .save(consumer, EternalStarlight.MOD_ID + ":enter_starlight");

        Advancement swampSilver = addItemObtain(consumer, root, "obtain_swamp_silver", ItemInit.SWAMP_SILVER_INGOT.get());

        Advancement starlightFlower = addItemObtain(consumer, root, "obtain_starlight_flower", ItemInit.STARLIGHT_FLOWER.get());

        Advancement seekingEye = addItemObtain(consumer, starlightFlower, "obtain_seeking_eye", ItemInit.SEEKING_EYE.get());

        Advancement killGolem = addEntityKill(consumer, seekingEye, "kill_golem", EntityInit.STARLIGHT_GOLEM.get(), ItemInit.ENERGY_BLOCK.get());

        Advancement golemSteelIngot = addItemObtain(consumer, killGolem, "obtain_golem_steel_ingot", ItemInit.GOLEM_STEEL_INGOT.get());

        Advancement thermalSpringstone = addItemObtain(consumer, killGolem, "obtain_thermal_springstone", ItemInit.THERMAL_SPRINGSTONE.get());

        Advancement killLunarMonstrosity = addEntityKill(consumer, thermalSpringstone, "kill_lunar_monstrosity", EntityInit.LUNAR_MONSTROSITY.get(), ItemInit.PARASOL_GRASS.get());
    }

    private static Advancement addItemObtain(Consumer<Advancement> consumer, Advancement parent, String id, Item item) {
        return Advancement.Builder.advancement().parent(parent).display(
                        item,
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".description"),
                        null, FrameType.GOAL, true, true, false)
                .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item))
                .save(consumer, EternalStarlight.MOD_ID + ":" + id);
    }

    private static Advancement addEntityKill(Consumer<Advancement> consumer, Advancement parent, String id, EntityType<?> entity, Item item) {
        return addEntityKill(consumer, parent, id, EntityPredicate.Builder.entity().of(entity).build(), item);
    }

    private static Advancement addEntityKill(Consumer<Advancement> consumer, Advancement parent, String id, EntityPredicate predicate, Item item) {
        return Advancement.Builder.advancement().parent(parent).display(
                        item,
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".title"),
                        Component.translatable("advancements." + EternalStarlight.MOD_ID + "." + id + ".description"),
                        null, FrameType.GOAL, true, true, false)
                .addCriterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(predicate))
                .save(consumer, EternalStarlight.MOD_ID + ":" + id);
    }
}
