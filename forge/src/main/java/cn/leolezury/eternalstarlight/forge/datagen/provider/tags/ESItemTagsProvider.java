package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ESItemTagsProvider extends ItemTagsProvider {
    public ESItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, EternalStarlight.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        // mod tags
        copy(ESTags.Blocks.LUNAR_LOGS, ESTags.Items.LUNAR_LOGS);
        copy(ESTags.Blocks.NORTHLAND_LOGS, ESTags.Items.NORTHLAND_LOGS);
        copy(ESTags.Blocks.STARLIGHT_MANGROVE_LOGS, ESTags.Items.STARLIGHT_MANGROVE_LOGS);
        copy(ESTags.Blocks.SCARLET_LOGS, ESTags.Items.SCARLET_LOGS);
        copy(ESTags.Blocks.TORREYA_LOGS, ESTags.Items.TORREYA_LOGS);
        copy(ESTags.Blocks.YETI_FUR, ESTags.Items.YETI_FUR);
        copy(ESTags.Blocks.YETI_FUR_CARPETS, ESTags.Items.YETI_FUR_CARPETS);
        tag(ESTags.Items.THERMAL_SPRINGSTONE_WEAPONS).add(
                ItemInit.THERMAL_SPRINGSTONE_SWORD.get(),
                ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get(),
                ItemInit.THERMAL_SPRINGSTONE_AXE.get(),
                ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get(),
                ItemInit.THERMAL_SPRINGSTONE_HAMMER.get()
        );
        tag(ESTags.Items.DOOMEDEN_KEYS).add(
                ItemInit.EYE_OF_DOOM.get(),
                ItemInit.LIVING_ARM.get(),
                ItemInit.DOOMEDEN_SWORD.get()
        );
        tag(ESTags.Items.MANA_CRYSTAL_INGREDIENTS).add(
                ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get()
        );
        tag(ESTags.Items.MANA_CRYSTALS).addTags(
                ESTags.Items.TERRA_CRYSTALS,
                ESTags.Items.WIND_CRYSTALS,
                ESTags.Items.WATER_CRYSTALS,
                ESTags.Items.LUNAR_CRYSTALS,
                ESTags.Items.BLAZE_CRYSTALS,
                ESTags.Items.LIGHT_CRYSTALS
        );
        tag(ESTags.Items.TERRA_CRYSTALS).add(
                ItemInit.TERRA_CRYSTAL.get(),
                ItemInit.MANA_CRYSTAL.get()
        );
        tag(ESTags.Items.WIND_CRYSTALS).add(
                ItemInit.WIND_CRYSTAL.get(),
                ItemInit.MANA_CRYSTAL.get()
        );
        tag(ESTags.Items.WATER_CRYSTALS).add(
                ItemInit.WATER_CRYSTAL.get(),
                ItemInit.MANA_CRYSTAL.get()
        );
        tag(ESTags.Items.LUNAR_CRYSTALS).add(
                ItemInit.LUNAR_CRYSTAL.get(),
                ItemInit.MANA_CRYSTAL.get()
        );
        tag(ESTags.Items.BLAZE_CRYSTALS).add(
                ItemInit.BLAZE_CRYSTAL.get(),
                ItemInit.MANA_CRYSTAL.get()
        );
        tag(ESTags.Items.LIGHT_CRYSTALS).add(
                ItemInit.LIGHT_CRYSTAL.get(),
                ItemInit.MANA_CRYSTAL.get()
        );
        // mc tags
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        copy(BlockTags.SAND, ItemTags.SAND);
        copy(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.CANDLES, ItemTags.CANDLES);
        tag(ItemTags.BOATS).add(
                ItemInit.LUNAR_BOAT.get(),
                ItemInit.NORTHLAND_BOAT.get(),
                ItemInit.STARLIGHT_MANGROVE_BOAT.get(),
                ItemInit.SCARLET_BOAT.get(),
                ItemInit.TORREYA_BOAT.get()
        );
        tag(ItemTags.CHEST_BOATS).add(
                ItemInit.LUNAR_CHEST_BOAT.get(),
                ItemInit.NORTHLAND_CHEST_BOAT.get(),
                ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT.get(),
                ItemInit.SCARLET_CHEST_BOAT.get(),
                ItemInit.TORREYA_CHEST_BOAT.get()
        );
        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
                ItemInit.THERMAL_SPRINGSTONE_HELMET.get(),
                ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get(),
                ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get(),
                ItemInit.THERMAL_SPRINGSTONE_BOOTS.get()
        );
        tag(ItemTags.SWORDS).add(
                ItemInit.RAGE_OF_STARS.get(),
                ItemInit.THERMAL_SPRINGSTONE_SWORD.get(),
                ItemInit.SWAMP_SILVER_SWORD.get(),
                ItemInit.BONEMORE_BROADSWORD.get(),
                ItemInit.DOOMEDEN_SWORD.get(),
                ItemInit.SHATTERED_SWORD.get(),
                ItemInit.ENERGY_SWORD.get(),
                ItemInit.MOONRING_GREATSWORD.get()
        );
        tag(ItemTags.PICKAXES).add(
                ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get(),
                ItemInit.SWAMP_SILVER_PICKAXE.get()
        );
        tag(ItemTags.AXES).add(
                ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get(),
                ItemInit.SWAMP_SILVER_AXE.get()
        );
        tag(ItemTags.HOES).add(
                ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get(),
                ItemInit.SWAMP_SILVER_SICKLE.get(),
                ItemInit.PETAL_SCYTHE.get(),
                ItemInit.AURORA_DEER_ANTLER.get()
        );
        // No shovel in the mod!?
        /*tag(ItemTags.SHOVELS).add(

        );*/
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                ItemInit.AETHERSENT_HOOD.get(),
                ItemInit.SWAMP_SILVER_HELMET.get(),
                ItemInit.SWAMP_SILVER_CHESTPLATE.get()
        );
        tag(ItemTags.TRIM_MATERIALS).add(
                ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get(),
                ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get(),
                ItemInit.AETHERSENT_INGOT.get(),
                ItemInit.THERMAL_SPRINGSTONE_INGOT.get(),
                ItemInit.SWAMP_SILVER_INGOT.get(),
                ItemInit.GOLEM_STEEL_INGOT.get(),
                ItemInit.TENACIOUS_PETAL.get(),
                ItemInit.BROKEN_DOOMEDEN_BONE.get()
        );
        tag(ItemTags.STONE_TOOL_MATERIALS).add(
                ItemInit.GRIMSTONE.get(),
                ItemInit.COBBLED_GRIMSTONE.get(),
                ItemInit.VOIDSTONE.get(),
                ItemInit.COBBLED_VOIDSTONE.get()
        );
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(
                ItemInit.GRIMSTONE.get(),
                ItemInit.COBBLED_GRIMSTONE.get(),
                ItemInit.VOIDSTONE.get(),
                ItemInit.COBBLED_VOIDSTONE.get()
        );
    }
}
