package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeModeTabInit {
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, EternalStarlight.MOD_ID);

    // We need to split this to Forge, Fabric and Quilt later...
    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = TABS.register("building_blocks", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".building_blocks"))
            .icon(() -> new ItemStack(ItemInit.CHISELED_GRIMSTONE.get()))
            .displayItems((parameters, output) -> {
                output
                output.accept(ItemInit.RED_STARLIGHT_CRYSTAL_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().getDefaultInstance());
                output.accept(ItemInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().getDefaultInstance());
                output.accept(ItemInit.RED_CRYSTAL_MOSS_CARPET.get().getDefaultInstance());
                output.accept(ItemInit.BLUE_CRYSTAL_MOSS_CARPET.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_LEAVES.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_LOG.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_WOOD.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_PLANKS.get().getDefaultInstance());
                output.accept(ItemInit.STRIPPED_LUNAR_LOG.get().getDefaultInstance());
                output.accept(ItemInit.STRIPPED_LUNAR_WOOD.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_DOOR.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_TRAPDOOR.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_PRESSURE_PLATE.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_BUTTON.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_FENCE.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_FENCE_GATE.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_LEAVES.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_LOG.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_WOOD.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_PLANKS.get().getDefaultInstance());
                output.accept(ItemInit.STRIPPED_NORTHLAND_LOG.get().getDefaultInstance());
                output.accept(ItemInit.STRIPPED_NORTHLAND_WOOD.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_DOOR.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_TRAPDOOR.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_PRESSURE_PLATE.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_BUTTON.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_FENCE.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_FENCE_GATE.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_LEAVES.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_LOG.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_WOOD.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_PLANKS.get().getDefaultInstance());
                output.accept(ItemInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get().getDefaultInstance());
                output.accept(ItemInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_DOOR.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_TRAPDOOR.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_BUTTON.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_FENCE.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_FENCE_GATE.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_MUSHROOM_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.FANTASY_GRASS_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_GRASS_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_DIRT.get().getDefaultInstance());
                output.accept(ItemInit.GRIMSTONE.get().getDefaultInstance());
                output.accept(ItemInit.GRIMSTONE_BRICKS.get().getDefaultInstance());
                output.accept(ItemInit.GRIMSTONE_BRICK_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.GRIMSTONE_BRICK_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.GRIMSTONE_BRICK_WALL.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_GRIMSTONE.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_GRIMSTONE_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_GRIMSTONE_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_GRIMSTONE_WALL.get().getDefaultInstance());
                output.accept(ItemInit.CHISELED_GRIMSTONE.get().getDefaultInstance());
                output.accept(ItemInit.VOIDSTONE.get().getDefaultInstance());
                output.accept(ItemInit.VOIDSTONE_BRICKS.get().getDefaultInstance());
                output.accept(ItemInit.VOIDSTONE_BRICK_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.VOIDSTONE_BRICK_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.VOIDSTONE_BRICK_WALL.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_VOIDSTONE.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_VOIDSTONE_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_VOIDSTONE_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.POLISHED_VOIDSTONE_WALL.get().getDefaultInstance());
                output.accept(ItemInit.CHISELED_VOIDSTONE.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_NIGHTSHADE_MUD.get().getDefaultInstance());
                output.accept(ItemInit.PACKED_NIGHTSHADE_MUD.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD_BRICKS.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD_BRICK_SLAB.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD_BRICK_STAIRS.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD_BRICK_WALL.get().getDefaultInstance());
                output.accept(ItemInit.SPRINGSTONE.get().getDefaultInstance());
                output.accept(ItemInit.AETHERSENT_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.ENERGY_BLOCK.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> NATURE_BLOCKS = TABS.register("nature_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(BUILDING_BLOCKS.getKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".nature_blocks"))
            .icon(() -> new ItemStack(ItemInit.STARLIGHT_MANGROVE_SAPLING.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.GRIMSTONE.get().getDefaultInstance());
                output.accept(ItemInit.VOIDSTONE.get().getDefaultInstance());
                output.accept(ItemInit.SPRINGSTONE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_ORE.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_MUSHROOM.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_MUSHROOM_BLOCK.get().getDefaultInstance());
                output.accept(ItemInit.CRESCENT_GRASS.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_GRASS.get().getDefaultInstance());
                output.accept(ItemInit.PARASOL_GRASS.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_REED.get().getDefaultInstance());
                output.accept(ItemInit.NIGHT_SPROUTS.get().getDefaultInstance());
                output.accept(ItemInit.SMALL_NIGHT_SPROUTS.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_CRESCENT_GRASS.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_LUNAR_GRASS.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_PARASOL_GRASS.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_NIGHT_SPROUTS.get().getDefaultInstance());
                output.accept(ItemInit.SMALL_GLOWING_NIGHT_SPROUTS.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_ROSE.get().getDefaultInstance());
                output.accept(ItemInit.FANTABUD.get().getDefaultInstance());
                output.accept(ItemInit.GREEN_FANTABUD.get().getDefaultInstance());
                output.accept(ItemInit.FANTAFERN.get().getDefaultInstance());
                output.accept(ItemInit.GREEN_FANTAFERN.get().getDefaultInstance());
                output.accept(ItemInit.FANTAGRASS.get().getDefaultInstance());
                output.accept(ItemInit.GREEN_FANTAGRASS.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_SAPLING.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_SAPLING.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_SAPLING.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD.get().getDefaultInstance());
                output.accept(ItemInit.GLOWING_NIGHTSHADE_MUD.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_ROOTS.get().getDefaultInstance());
                output.accept(ItemInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> FUNCTIONAL_BLOCKS = TABS.register("functional_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(NATURE_BLOCKS.getKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".functional_blocks"))
            .icon(() -> new ItemStack(ItemInit.LUNAR_HANGING_SIGN.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.LUNAR_SIGN.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_SIGN.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_SIGN.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_HANGING_SIGN.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_HANGING_SIGN.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_HANGING_SIGN.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> COMBAT = TABS.register("combat", () -> CreativeModeTab.builder()
            .withTabsBefore(FUNCTIONAL_BLOCKS.getKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".combat"))
            .icon(() -> new ItemStack(ItemInit.MOONRING_GREATSWORD.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.RAGE_OF_STARS.get().getDefaultInstance());
                output.accept(ItemInit.STARFALL_LONGBOW.get().getDefaultInstance());
                output.accept(ItemInit.AETHERSENT_HOOD.get().getDefaultInstance());
                output.accept(ItemInit.AETHERSENT_CAPE.get().getDefaultInstance());
                output.accept(ItemInit.AETHERSENT_BOTTOMS.get().getDefaultInstance());
                output.accept(ItemInit.AETHERSENT_BOOTS.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_HELMET.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_BOOTS.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_HAMMER.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_SWORD.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_HELMET.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_CHESTPLATE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_LEGGINGS.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_BOOTS.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_SCYTHE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_SWORD.get().getDefaultInstance());
                output.accept(ItemInit.MOONRING_GREATSWORD.get().getDefaultInstance());
                output.accept(ItemInit.PETAL_SCYTHE.get().getDefaultInstance());
                output.accept(ItemInit.CRYSTAL_CROSSBOW.get().getDefaultInstance());
                output.accept(ItemInit.MOONRING_BOW.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> TOOLS = TABS.register("tools", () -> CreativeModeTab.builder()
            .withTabsBefore(COMBAT.getKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".tools"))
            .icon(() -> new ItemStack(ItemInit.SWAMP_SILVER_AXE.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.BOOK.get().getDefaultInstance());
                output.accept(ItemInit.SEEKING_EYE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_HAMMER.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_AXE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_SCYTHE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_AXE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_PICKAXE.get().getDefaultInstance());
                output.accept(ItemInit.PETAL_SCYTHE.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_BOAT.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_CHEST_BOAT.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_BOAT.get().getDefaultInstance());
                output.accept(ItemInit.NORTHLAND_CHEST_BOAT.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_BOAT.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> INGREDIENTS = TABS.register("ingredients", () -> CreativeModeTab.builder()
            .withTabsBefore(TOOLS.getKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".ingredients"))
            .icon(() -> new ItemStack(ItemInit.TENACIOUS_PETAL.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get().getDefaultInstance());
                output.accept(ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get().getDefaultInstance());
                output.accept(ItemInit.AETHERSENT_INGOT.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE.get().getDefaultInstance());
                output.accept(ItemInit.THERMAL_SPRINGSTONE_INGOT.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_ORE.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_INGOT.get().getDefaultInstance());
                output.accept(ItemInit.SWAMP_SILVER_NUGGET.get().getDefaultInstance());
                output.accept(ItemInit.TENACIOUS_PETAL.get().getDefaultInstance());
                output.accept(ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get().getDefaultInstance());
                output.accept(ItemInit.GOLEM_STEEL_INGOT.get().getDefaultInstance());
                output.accept(ItemInit.STARLIGHT_FLOWER.get().getDefaultInstance());
                output.accept(ItemInit.NIGHTSHADE_MUD.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> FOODS = TABS.register("foods", () -> CreativeModeTab.builder()
            .withTabsBefore(INGREDIENTS.getKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".foods"))
            .icon(() -> new ItemStack(ItemInit.LUNAR_BERRIES.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.LUNAR_BERRIES.get().getDefaultInstance());
            }).build());

    public static final RegistryObject<CreativeModeTab> SPAWN_EGGS = TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .withTabsBefore(FOODS.getResourceKey())
            .title(Component.translatable("itemGroup." + EternalStarlight.MOD_ID + ".spawn_eggs"))
            .icon(() -> new ItemStack(Items.PHANTOM_SPAWN_EGG))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.STARLIGHT_GOLEM_SPAWNER.get().getDefaultInstance());
                output.accept(ItemInit.LUNAR_MONSTROSITY_SPAWNER.get().getDefaultInstance());
            }).build());
}
