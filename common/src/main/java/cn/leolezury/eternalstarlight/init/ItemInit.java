package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.entity.misc.SLBoat;
import cn.leolezury.eternalstarlight.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.item.armor.SLArmorMaterials;
import cn.leolezury.eternalstarlight.item.armor.SwampSilverArmorItem;
import cn.leolezury.eternalstarlight.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.item.misc.LootBagItem;
import cn.leolezury.eternalstarlight.item.misc.SLBoatItem;
import cn.leolezury.eternalstarlight.item.misc.SLBookItem;
import cn.leolezury.eternalstarlight.item.misc.SeekingEyeItem;
import cn.leolezury.eternalstarlight.item.weapon.*;
import cn.leolezury.eternalstarlight.mixins.access.AxeItemAccess;
import cn.leolezury.eternalstarlight.mixins.access.PickaxeItemAccess;
import cn.leolezury.eternalstarlight.platform.ESPlatform;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

public class ItemInit {
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EternalStarlight.MOD_ID);
//    static Rarity STARLIGHT = Rarity.create("Starlight", ChatFormatting.DARK_AQUA);
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, EternalStarlight.MOD_ID);
    public static final RegistryObject<Item> RED_STARLIGHT_CRYSTAL_BLOCK = ITEMS.register("red_starlight_crystal_block", () -> new BlockItem(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STARLIGHT_CRYSTAL_BLOCK = ITEMS.register("blue_starlight_crystal_block", () -> new BlockItem(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_STARLIGHT_CRYSTAL_CLUSTER = ITEMS.register("red_starlight_crystal_cluster", () -> new BlockItem(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STARLIGHT_CRYSTAL_CLUSTER = ITEMS.register("blue_starlight_crystal_cluster", () -> new BlockItem(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_CRYSTAL_MOSS_CARPET = ITEMS.register("red_crystal_moss_carpet", () -> new BlockItem(BlockInit.RED_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_CRYSTAL_MOSS_CARPET = ITEMS.register("blue_crystal_moss_carpet", () -> new BlockItem(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_STARLIGHT_CRYSTAL_SHARD = ITEMS.register("red_starlight_crystal_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STARLIGHT_CRYSTAL_SHARD = ITEMS.register("blue_starlight_crystal_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_BERRIES = ITEMS.register("lunar_berries", () -> new ItemNameBlockItem(BlockInit.BERRIES_VINES.get(), (new Item.Properties()).food((new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).build())));

    //lunar wood
    public static final RegistryObject<Item> LUNAR_SAPLING = ITEMS.register("lunar_sapling", () -> new BlockItem(BlockInit.LUNAR_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_LEAVES = ITEMS.register("lunar_leaves", () -> new BlockItem(BlockInit.LUNAR_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_LOG = ITEMS.register("lunar_log", () -> new BlockItem(BlockInit.LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_WOOD = ITEMS.register("lunar_wood", () -> new BlockItem(BlockInit.LUNAR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_PLANKS = ITEMS.register("lunar_planks", () -> new BlockItem(BlockInit.LUNAR_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_LUNAR_LOG = ITEMS.register("stripped_lunar_log", () -> new BlockItem(BlockInit.STRIPPED_LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_LUNAR_WOOD = ITEMS.register("stripped_lunar_wood", () -> new BlockItem(BlockInit.STRIPPED_LUNAR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_DOOR = ITEMS.register("lunar_door", () -> new BlockItem(BlockInit.LUNAR_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_TRAPDOOR = ITEMS.register("lunar_trapdoor", () -> new BlockItem(BlockInit.LUNAR_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_PRESSURE_PLATE = ITEMS.register("lunar_pressure_plate", () -> new BlockItem(BlockInit.LUNAR_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_BUTTON = ITEMS.register("lunar_button", () -> new BlockItem(BlockInit.LUNAR_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_FENCE = ITEMS.register("lunar_fence", () -> new BlockItem(BlockInit.LUNAR_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_FENCE_GATE = ITEMS.register("lunar_fence_gate", () -> new BlockItem(BlockInit.LUNAR_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_SLAB = ITEMS.register("lunar_slab", () -> new BlockItem(BlockInit.LUNAR_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_STAIRS = ITEMS.register("lunar_stairs", () -> new BlockItem(BlockInit.LUNAR_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_SIGN = ITEMS.register("lunar_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.LUNAR_SIGN.get(), BlockInit.LUNAR_WALL_SIGN.get()));
    public static final RegistryObject<Item> LUNAR_HANGING_SIGN = ITEMS.register("lunar_hanging_sign", () -> new HangingSignItem(BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.LUNAR_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> LUNAR_BOAT = ITEMS.register("lunar_boat", () -> new SLBoatItem(false, SLBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LUNAR_CHEST_BOAT = ITEMS.register("lunar_chest_boat", () -> new SLBoatItem(true, SLBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));

    //northland wood
    public static final RegistryObject<Item> NORTHLAND_SAPLING = ITEMS.register("northland_sapling", () -> new BlockItem(BlockInit.NORTHLAND_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_LEAVES = ITEMS.register("northland_leaves", () -> new BlockItem(BlockInit.NORTHLAND_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_LOG = ITEMS.register("northland_log", () -> new BlockItem(BlockInit.NORTHLAND_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_WOOD = ITEMS.register("northland_wood", () -> new BlockItem(BlockInit.NORTHLAND_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_PLANKS = ITEMS.register("northland_planks", () -> new BlockItem(BlockInit.NORTHLAND_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_NORTHLAND_LOG = ITEMS.register("stripped_northland_log", () -> new BlockItem(BlockInit.STRIPPED_NORTHLAND_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_NORTHLAND_WOOD = ITEMS.register("stripped_northland_wood", () -> new BlockItem(BlockInit.STRIPPED_NORTHLAND_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_DOOR = ITEMS.register("northland_door", () -> new BlockItem(BlockInit.NORTHLAND_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_TRAPDOOR = ITEMS.register("northland_trapdoor", () -> new BlockItem(BlockInit.NORTHLAND_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_PRESSURE_PLATE = ITEMS.register("northland_pressure_plate", () -> new BlockItem(BlockInit.NORTHLAND_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_BUTTON = ITEMS.register("northland_button", () -> new BlockItem(BlockInit.NORTHLAND_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_FENCE = ITEMS.register("northland_fence", () -> new BlockItem(BlockInit.NORTHLAND_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_FENCE_GATE = ITEMS.register("northland_fence_gate", () -> new BlockItem(BlockInit.NORTHLAND_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_SLAB = ITEMS.register("northland_slab", () -> new BlockItem(BlockInit.NORTHLAND_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_STAIRS = ITEMS.register("northland_stairs", () -> new BlockItem(BlockInit.NORTHLAND_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_SIGN = ITEMS.register("northland_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get()));
    public static final RegistryObject<Item> NORTHLAND_HANGING_SIGN = ITEMS.register("northland_hanging_sign", () -> new HangingSignItem(BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> NORTHLAND_BOAT = ITEMS.register("northland_boat", () -> new SLBoatItem(false, SLBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NORTHLAND_CHEST_BOAT = ITEMS.register("northland_chest_boat", () -> new SLBoatItem(true, SLBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));

    //starlight mangrove wood
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_SAPLING = ITEMS.register("starlight_mangrove_sapling", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_LEAVES = ITEMS.register("starlight_mangrove_leaves", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_LOG = ITEMS.register("starlight_mangrove_log", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_WOOD = ITEMS.register("starlight_mangrove_wood", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_PLANKS = ITEMS.register("starlight_mangrove_planks", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_STARLIGHT_MANGROVE_LOG = ITEMS.register("stripped_starlight_mangrove_log", () -> new BlockItem(BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_STARLIGHT_MANGROVE_WOOD = ITEMS.register("stripped_starlight_mangrove_wood", () -> new BlockItem(BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_DOOR = ITEMS.register("starlight_mangrove_door", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_TRAPDOOR = ITEMS.register("starlight_mangrove_trapdoor", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_PRESSURE_PLATE = ITEMS.register("starlight_mangrove_pressure_plate", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_BUTTON = ITEMS.register("starlight_mangrove_button", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_FENCE = ITEMS.register("starlight_mangrove_fence", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_FENCE_GATE = ITEMS.register("starlight_mangrove_fence_gate", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_SLAB = ITEMS.register("starlight_mangrove_slab", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_STAIRS = ITEMS.register("starlight_mangrove_stairs", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_ROOTS = ITEMS.register("starlight_mangrove_roots", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_ROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MUDDY_STARLIGHT_MANGROVE_ROOTS = ITEMS.register("muddy_starlight_mangrove_roots", () -> new BlockItem(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_SIGN = ITEMS.register("starlight_mangrove_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_HANGING_SIGN = ITEMS.register("starlight_mangrove_hanging_sign", () -> new HangingSignItem(BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_BOAT = ITEMS.register("starlight_mangrove_boat", () -> new SLBoatItem(false, SLBoat.Type.STARLIGHT_MANGROVE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_CHEST_BOAT = ITEMS.register("starlight_mangrove_chest_boat", () -> new SLBoatItem(true, SLBoat.Type.STARLIGHT_MANGROVE, new Item.Properties().stacksTo(1)));

    // common grass
    public static final RegistryObject<Item> STARLIGHT_FLOWER = ITEMS.register("starlight_flower", () -> new BlockItem(BlockInit.STARLIGHT_FLOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHT_SPROUTS = ITEMS.register("night_sprouts", () -> new BlockItem(BlockInit.NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_NIGHT_SPROUTS = ITEMS.register("glowing_night_sprouts", () -> new BlockItem(BlockInit.GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMALL_NIGHT_SPROUTS = ITEMS.register("small_night_sprouts", () -> new BlockItem(BlockInit.SMALL_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMALL_GLOWING_NIGHT_SPROUTS = ITEMS.register("small_glowing_night_sprouts", () -> new BlockItem(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_GRASS = ITEMS.register("lunar_grass", () -> new BlockItem(BlockInit.LUNAR_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_LUNAR_GRASS = ITEMS.register("glowing_lunar_grass", () -> new BlockItem(BlockInit.GLOWING_LUNAR_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRESCENT_GRASS = ITEMS.register("crescent_grass", () -> new BlockItem(BlockInit.CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_CRESCENT_GRASS = ITEMS.register("glowing_crescent_grass", () -> new BlockItem(BlockInit.GLOWING_CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PARASOL_GRASS = ITEMS.register("parasol_grass", () -> new BlockItem(BlockInit.PARASOL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_PARASOL_GRASS = ITEMS.register("glowing_parasol_grass", () -> new BlockItem(BlockInit.GLOWING_PARASOL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_REED = ITEMS.register("lunar_reed", () -> new BlockItem(BlockInit.LUNAR_REED.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_MUSHROOM = ITEMS.register("glowing_mushroom", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_MUSHROOM_BLOCK = ITEMS.register("glowing_mushroom_block", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM_BLOCK.get(), new Item.Properties()));

    // swamp grass
    public static final RegistryObject<Item> SWAMP_ROSE = ITEMS.register("swamp_rose", () -> new BlockItem(BlockInit.SWAMP_ROSE.get(), new Item.Properties()));
    public static final RegistryObject<Item> FANTABUD = ITEMS.register("fantabud", () -> new BlockItem(BlockInit.FANTABUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FANTABUD = ITEMS.register("green_fantabud", () -> new BlockItem(BlockInit.GREEN_FANTABUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> FANTAFERN = ITEMS.register("fantafern", () -> new BlockItem(BlockInit.FANTAFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FANTAFERN = ITEMS.register("green_fantafern", () -> new BlockItem(BlockInit.GREEN_FANTAFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> FANTAGRASS = ITEMS.register("fantagrass", () -> new BlockItem(BlockInit.FANTAGRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FANTAGRASS = ITEMS.register("green_fantagrass", () -> new BlockItem(BlockInit.GREEN_FANTAGRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> FANTASY_GRASS_BLOCK = ITEMS.register("fantasy_grass_block", () -> new BlockItem(BlockInit.FANTASY_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_GRASS_BLOCK = ITEMS.register("nightshade_grass_block", () -> new BlockItem(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_DIRT = ITEMS.register("nightshade_dirt", () -> new BlockItem(BlockInit.NIGHTSHADE_DIRT.get(), new Item.Properties()));

    //grimstone
    public static final RegistryObject<Item> GRIMSTONE = ITEMS.register("grimstone", () -> new BlockItem(BlockInit.GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICKS = ITEMS.register("grimstone_bricks", () -> new BlockItem(BlockInit.GRIMSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICK_SLAB = ITEMS.register("grimstone_brick_slab", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICK_STAIRS = ITEMS.register("grimstone_brick_stairs", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICK_WALL = ITEMS.register("grimstone_brick_wall", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE = ITEMS.register("polished_grimstone", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE_SLAB = ITEMS.register("polished_grimstone_slab", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE_STAIRS = ITEMS.register("polished_grimstone_stairs", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE_WALL = ITEMS.register("polished_grimstone_wall", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_GRIMSTONE = ITEMS.register("chiseled_grimstone", () -> new BlockItem(BlockInit.CHISELED_GRIMSTONE.get(), new Item.Properties()));

    //voidstone
    public static final RegistryObject<Item> VOIDSTONE = ITEMS.register("voidstone", () -> new BlockItem(BlockInit.VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICKS = ITEMS.register("voidstone_bricks", () -> new BlockItem(BlockInit.VOIDSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICK_SLAB = ITEMS.register("voidstone_brick_slab", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICK_STAIRS = ITEMS.register("voidstone_brick_stairs", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICK_WALL = ITEMS.register("voidstone_brick_wall", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE = ITEMS.register("polished_voidstone", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE_SLAB = ITEMS.register("polished_voidstone_slab", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE_STAIRS = ITEMS.register("polished_voidstone_stairs", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE_WALL = ITEMS.register("polished_voidstone_wall", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_VOIDSTONE = ITEMS.register("chiseled_voidstone", () -> new BlockItem(BlockInit.CHISELED_VOIDSTONE.get(), new Item.Properties()));

    //mud
    public static final RegistryObject<Item> NIGHTSHADE_MUD = ITEMS.register("nightshade_mud", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_NIGHTSHADE_MUD = ITEMS.register("glowing_nightshade_mud", () -> new BlockItem(BlockInit.GLOWING_NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> PACKED_NIGHTSHADE_MUD = ITEMS.register("packed_nightshade_mud", () -> new BlockItem(BlockInit.PACKED_NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICKS = ITEMS.register("nightshade_mud_bricks", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICK_SLAB = ITEMS.register("nightshade_mud_brick_slab", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICK_STAIRS = ITEMS.register("nightshade_mud_brick_stairs", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICK_WALL = ITEMS.register("nightshade_mud_brick_wall", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), new Item.Properties()));

    //misc
    public static final RegistryObject<Item> ENERGY_BLOCK = ITEMS.register("energy_block", () -> new BlockItem(BlockInit.ENERGY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_GOLEM_SPAWNER = ITEMS.register("starlight_golem_spawner", () -> new BlockItem(BlockInit.STARLIGHT_GOLEM_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_MONSTROSITY_SPAWNER = ITEMS.register("lunar_monstrosity_spawner", () -> new BlockItem(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LOOT_BAG = ITEMS.register("loot_bag", () -> new LootBagItem(new Item.Properties().fireResistant()/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> BOOK = ITEMS.register("book", () -> new SLBookItem(new Item.Properties()/*.rarity(STARLIGHT)*/));

    //aethersent
    public static final RegistryObject<Item> AETHERSENT_BLOCK = ITEMS.register("aethersent_block", () -> new BlockItem(BlockInit.AETHERSENT_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_INGOT = ITEMS.register("aethersent_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAGE_OF_STARS = ITEMS.register("rage_of_stars", () -> new RageOfStarsItem(new Item.Properties().stacksTo(1).defaultDurability(1000)));
    public static final RegistryObject<Item> STARFALL_LONGBOW = ITEMS.register("starfall_longbow", () -> new StarfallLongbowItem(new Item.Properties().stacksTo(1).defaultDurability(2000)));
    public static final RegistryObject<Item> AETHERSENT_HOOD = ITEMS.register("aethersent_hood",
            () -> new AethersentArmorItem(SLArmorMaterials.AETHERSENT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_CAPE = ITEMS.register("aethersent_cape",
            () -> new AethersentArmorItem(SLArmorMaterials.AETHERSENT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_BOTTOMS = ITEMS.register("aethersent_bottoms",
            () -> new AethersentArmorItem(SLArmorMaterials.AETHERSENT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_BOOTS = ITEMS.register("aethersent_boots",
            () -> new AethersentArmorItem(SLArmorMaterials.AETHERSENT, ArmorItem.Type.BOOTS, new Item.Properties()));


    //thermal springstone
    public static final RegistryObject<Item> SPRINGSTONE = ITEMS.register("springstone", () -> new BlockItem(BlockInit.SPRINGSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE = ITEMS.register("thermal_springstone", () -> new BlockItem(BlockInit.THERMAL_SPRINGSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_INGOT = ITEMS.register("thermal_springstone_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_SWORD = ITEMS.register("thermal_springstone_sword",
            () -> new SwordItem(SLItemTiers.THERMAL_SPRINGSTONE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_PICKAXE = ITEMS.register("thermal_springstone_pickaxe",
            () -> PickaxeItemAccess.create(SLItemTiers.THERMAL_SPRINGSTONE,1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_AXE = ITEMS.register("thermal_springstone_axe",
            () -> AxeItemAccess.create(SLItemTiers.THERMAL_SPRINGSTONE, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_SCYTHE = ITEMS.register("thermal_springstone_scythe",
            () -> ESPlatform.INSTANCE.createScythe(SLItemTiers.THERMAL_SPRINGSTONE, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_HAMMER = ITEMS.register("thermal_springstone_hammer",
            () -> ESPlatform.INSTANCE.createHammer(SLItemTiers.THERMAL_SPRINGSTONE, 8, -3.5F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_HELMET = ITEMS.register("thermal_springstone_helmet",
            () -> new ThermalSpringStoneArmorItem(SLArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_CHESTPLATE = ITEMS.register("thermal_springstone_chestplate",
            () -> new ThermalSpringStoneArmorItem(SLArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_LEGGINGS = ITEMS.register("thermal_springstone_leggings",
            () -> new ThermalSpringStoneArmorItem(SLArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_BOOTS = ITEMS.register("thermal_springstone_boots",
            () -> new ThermalSpringStoneArmorItem(SLArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.BOOTS, new Item.Properties()));

    //swamp silver
    public static final RegistryObject<Item> SWAMP_SILVER_ORE = ITEMS.register("swamp_silver_ore", () -> new BlockItem(BlockInit.SWAMP_SILVER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_BLOCK = ITEMS.register("swamp_silver_block", () -> new BlockItem(BlockInit.SWAMP_SILVER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_INGOT = ITEMS.register("swamp_silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_NUGGET = ITEMS.register("swamp_silver_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_SWORD = ITEMS.register("swamp_silver_sword",
            () -> new SwordItem(SLItemTiers.SWAMP_SILVER, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_PICKAXE = ITEMS.register("swamp_silver_pickaxe",
            () -> PickaxeItemAccess.create(SLItemTiers.SWAMP_SILVER,1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_AXE = ITEMS.register("swamp_silver_axe",
            () -> AxeItemAccess.create(SLItemTiers.SWAMP_SILVER, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_SCYTHE = ITEMS.register("swamp_silver_scythe",
            () -> ESPlatform.INSTANCE.createScythe(SLItemTiers.SWAMP_SILVER, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_HELMET = ITEMS.register("swamp_silver_helmet",
            () -> new SwampSilverArmorItem(SLArmorMaterials.SWAMP_SILVER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_CHESTPLATE = ITEMS.register("swamp_silver_chestplate",
            () -> new SwampSilverArmorItem(SLArmorMaterials.SWAMP_SILVER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_LEGGINGS = ITEMS.register("swamp_silver_leggings",
            () -> new SwampSilverArmorItem(SLArmorMaterials.SWAMP_SILVER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_BOOTS = ITEMS.register("swamp_silver_boots",
            () -> new SwampSilverArmorItem(SLArmorMaterials.SWAMP_SILVER, ArmorItem.Type.BOOTS, new Item.Properties()));

    //boss materials
    public static final RegistryObject<Item> GOLEM_STEEL_INGOT = ITEMS.register("golem_steel_ingot", () -> new Item(new Item.Properties()/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> OXIDIZED_GOLEM_STEEL_INGOT = ITEMS.register("oxidized_golem_steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TENACIOUS_PETAL = ITEMS.register("tenacious_petal", () -> new Item(new Item.Properties()/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> CRYSTAL_CROSSBOW = ITEMS.register("crystal_crossbow", () -> new CrystalCrossbowItem(new Item.Properties().stacksTo(1).durability(2000)/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> MOONRING_BOW = ITEMS.register("moonring_bow", () -> new MoonRingBowItem(new Item.Properties().stacksTo(1).durability(2000)/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> MOONRING_GREATSWORD = ITEMS.register("moonring_greatsword", () -> new GreatswordItem(SLItemTiers.PETAL, 6, -2.8F, new Item.Properties()/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> PETAL_SCYTHE = ITEMS.register("petal_scythe", () -> ESPlatform.INSTANCE.createScythe(SLItemTiers.PETAL, 3, -1.0F, new Item.Properties()/*.rarity(STARLIGHT)*/));
    public static final RegistryObject<Item> SEEKING_EYE = ITEMS.register("seeking_eye", () -> new SeekingEyeItem(new Item.Properties()));
}
