package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ESArmorMaterials;
import cn.leolezury.eternalstarlight.common.item.armor.SwampSilverArmorItem;
import cn.leolezury.eternalstarlight.common.item.misc.LootBagItem;
import cn.leolezury.eternalstarlight.common.item.misc.SLBoatItem;
import cn.leolezury.eternalstarlight.common.item.misc.SLBookItem;
import cn.leolezury.eternalstarlight.common.item.misc.SeekingEyeItem;
import cn.leolezury.eternalstarlight.common.item.weapon.*;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, EternalStarlight.MOD_ID);
    private static final Rarity STARLIGHT = ESPlatform.INSTANCE.getESRarity();
    
    public static final List<ResourceKey<Item>> REGISTERED_ITEMS = new ArrayList<>();
    private static RegistryObject<Item> registerItem(String name, Supplier<? extends Item> supplier) {
        REGISTERED_ITEMS.add(ResourceKey.create(Registries.ITEM, new ResourceLocation(EternalStarlight.MOD_ID, name)));
        return ITEMS.register(name, supplier);
    }
    
    public static final RegistryObject<Item> RED_STARLIGHT_CRYSTAL_BLOCK = registerItem("red_starlight_crystal_block", () -> new BlockItem(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STARLIGHT_CRYSTAL_BLOCK = registerItem("blue_starlight_crystal_block", () -> new BlockItem(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_STARLIGHT_CRYSTAL_CLUSTER = registerItem("red_starlight_crystal_cluster", () -> new BlockItem(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STARLIGHT_CRYSTAL_CLUSTER = registerItem("blue_starlight_crystal_cluster", () -> new BlockItem(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_CRYSTAL_MOSS_CARPET = registerItem("red_crystal_moss_carpet", () -> new BlockItem(BlockInit.RED_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_CRYSTAL_MOSS_CARPET = registerItem("blue_crystal_moss_carpet", () -> new BlockItem(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_STARLIGHT_CRYSTAL_SHARD = registerItem("red_starlight_crystal_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STARLIGHT_CRYSTAL_SHARD = registerItem("blue_starlight_crystal_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_BERRIES = registerItem("lunar_berries", () -> new ItemNameBlockItem(BlockInit.BERRIES_VINES.get(), (new Item.Properties()).food((new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).build())));

    //lunar wood
    public static final RegistryObject<Item> LUNAR_SAPLING = registerItem("lunar_sapling", () -> new BlockItem(BlockInit.LUNAR_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_LEAVES = registerItem("lunar_leaves", () -> new BlockItem(BlockInit.LUNAR_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_LOG = registerItem("lunar_log", () -> new BlockItem(BlockInit.LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_WOOD = registerItem("lunar_wood", () -> new BlockItem(BlockInit.LUNAR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_PLANKS = registerItem("lunar_planks", () -> new BlockItem(BlockInit.LUNAR_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_LUNAR_LOG = registerItem("stripped_lunar_log", () -> new BlockItem(BlockInit.STRIPPED_LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_LUNAR_WOOD = registerItem("stripped_lunar_wood", () -> new BlockItem(BlockInit.STRIPPED_LUNAR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_DOOR = registerItem("lunar_door", () -> new BlockItem(BlockInit.LUNAR_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_TRAPDOOR = registerItem("lunar_trapdoor", () -> new BlockItem(BlockInit.LUNAR_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_PRESSURE_PLATE = registerItem("lunar_pressure_plate", () -> new BlockItem(BlockInit.LUNAR_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_BUTTON = registerItem("lunar_button", () -> new BlockItem(BlockInit.LUNAR_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_FENCE = registerItem("lunar_fence", () -> new BlockItem(BlockInit.LUNAR_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_FENCE_GATE = registerItem("lunar_fence_gate", () -> new BlockItem(BlockInit.LUNAR_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_SLAB = registerItem("lunar_slab", () -> new BlockItem(BlockInit.LUNAR_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_STAIRS = registerItem("lunar_stairs", () -> new BlockItem(BlockInit.LUNAR_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_SIGN = registerItem("lunar_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.LUNAR_SIGN.get(), BlockInit.LUNAR_WALL_SIGN.get()));
    public static final RegistryObject<Item> LUNAR_HANGING_SIGN = registerItem("lunar_hanging_sign", () -> new HangingSignItem(BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.LUNAR_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> LUNAR_BOAT = registerItem("lunar_boat", () -> new SLBoatItem(false, ESBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LUNAR_CHEST_BOAT = registerItem("lunar_chest_boat", () -> new SLBoatItem(true, ESBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));

    //northland wood
    public static final RegistryObject<Item> NORTHLAND_SAPLING = registerItem("northland_sapling", () -> new BlockItem(BlockInit.NORTHLAND_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_LEAVES = registerItem("northland_leaves", () -> new BlockItem(BlockInit.NORTHLAND_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_LOG = registerItem("northland_log", () -> new BlockItem(BlockInit.NORTHLAND_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_WOOD = registerItem("northland_wood", () -> new BlockItem(BlockInit.NORTHLAND_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_PLANKS = registerItem("northland_planks", () -> new BlockItem(BlockInit.NORTHLAND_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_NORTHLAND_LOG = registerItem("stripped_northland_log", () -> new BlockItem(BlockInit.STRIPPED_NORTHLAND_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_NORTHLAND_WOOD = registerItem("stripped_northland_wood", () -> new BlockItem(BlockInit.STRIPPED_NORTHLAND_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_DOOR = registerItem("northland_door", () -> new BlockItem(BlockInit.NORTHLAND_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_TRAPDOOR = registerItem("northland_trapdoor", () -> new BlockItem(BlockInit.NORTHLAND_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_PRESSURE_PLATE = registerItem("northland_pressure_plate", () -> new BlockItem(BlockInit.NORTHLAND_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_BUTTON = registerItem("northland_button", () -> new BlockItem(BlockInit.NORTHLAND_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_FENCE = registerItem("northland_fence", () -> new BlockItem(BlockInit.NORTHLAND_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_FENCE_GATE = registerItem("northland_fence_gate", () -> new BlockItem(BlockInit.NORTHLAND_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_SLAB = registerItem("northland_slab", () -> new BlockItem(BlockInit.NORTHLAND_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_STAIRS = registerItem("northland_stairs", () -> new BlockItem(BlockInit.NORTHLAND_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NORTHLAND_SIGN = registerItem("northland_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get()));
    public static final RegistryObject<Item> NORTHLAND_HANGING_SIGN = registerItem("northland_hanging_sign", () -> new HangingSignItem(BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> NORTHLAND_BOAT = registerItem("northland_boat", () -> new SLBoatItem(false, ESBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NORTHLAND_CHEST_BOAT = registerItem("northland_chest_boat", () -> new SLBoatItem(true, ESBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));

    //starlight mangrove wood
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_SAPLING = registerItem("starlight_mangrove_sapling", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_LEAVES = registerItem("starlight_mangrove_leaves", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_LOG = registerItem("starlight_mangrove_log", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_WOOD = registerItem("starlight_mangrove_wood", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_PLANKS = registerItem("starlight_mangrove_planks", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_STARLIGHT_MANGROVE_LOG = registerItem("stripped_starlight_mangrove_log", () -> new BlockItem(BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_STARLIGHT_MANGROVE_WOOD = registerItem("stripped_starlight_mangrove_wood", () -> new BlockItem(BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_DOOR = registerItem("starlight_mangrove_door", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_TRAPDOOR = registerItem("starlight_mangrove_trapdoor", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_PRESSURE_PLATE = registerItem("starlight_mangrove_pressure_plate", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_BUTTON = registerItem("starlight_mangrove_button", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_FENCE = registerItem("starlight_mangrove_fence", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_FENCE_GATE = registerItem("starlight_mangrove_fence_gate", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_SLAB = registerItem("starlight_mangrove_slab", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_STAIRS = registerItem("starlight_mangrove_stairs", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_ROOTS = registerItem("starlight_mangrove_roots", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_ROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MUDDY_STARLIGHT_MANGROVE_ROOTS = registerItem("muddy_starlight_mangrove_roots", () -> new BlockItem(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_SIGN = registerItem("starlight_mangrove_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get()));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_HANGING_SIGN = registerItem("starlight_mangrove_hanging_sign", () -> new HangingSignItem(BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_BOAT = registerItem("starlight_mangrove_boat", () -> new SLBoatItem(false, ESBoat.Type.STARLIGHT_MANGROVE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STARLIGHT_MANGROVE_CHEST_BOAT = registerItem("starlight_mangrove_chest_boat", () -> new SLBoatItem(true, ESBoat.Type.STARLIGHT_MANGROVE, new Item.Properties().stacksTo(1)));

    // common grass
    public static final RegistryObject<Item> STARLIGHT_FLOWER = registerItem("starlight_flower", () -> new BlockItem(BlockInit.STARLIGHT_FLOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHT_SPROUTS = registerItem("night_sprouts", () -> new BlockItem(BlockInit.NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_NIGHT_SPROUTS = registerItem("glowing_night_sprouts", () -> new BlockItem(BlockInit.GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMALL_NIGHT_SPROUTS = registerItem("small_night_sprouts", () -> new BlockItem(BlockInit.SMALL_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMALL_GLOWING_NIGHT_SPROUTS = registerItem("small_glowing_night_sprouts", () -> new BlockItem(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_GRASS = registerItem("lunar_grass", () -> new BlockItem(BlockInit.LUNAR_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_LUNAR_GRASS = registerItem("glowing_lunar_grass", () -> new BlockItem(BlockInit.GLOWING_LUNAR_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRESCENT_GRASS = registerItem("crescent_grass", () -> new BlockItem(BlockInit.CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_CRESCENT_GRASS = registerItem("glowing_crescent_grass", () -> new BlockItem(BlockInit.GLOWING_CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PARASOL_GRASS = registerItem("parasol_grass", () -> new BlockItem(BlockInit.PARASOL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_PARASOL_GRASS = registerItem("glowing_parasol_grass", () -> new BlockItem(BlockInit.GLOWING_PARASOL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_REED = registerItem("lunar_reed", () -> new BlockItem(BlockInit.LUNAR_REED.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_MUSHROOM = registerItem("glowing_mushroom", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_MUSHROOM_BLOCK = registerItem("glowing_mushroom_block", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM_BLOCK.get(), new Item.Properties()));

    // swamp grass
    public static final RegistryObject<Item> SWAMP_ROSE = registerItem("swamp_rose", () -> new BlockItem(BlockInit.SWAMP_ROSE.get(), new Item.Properties()));
    public static final RegistryObject<Item> FANTABUD = registerItem("fantabud", () -> new BlockItem(BlockInit.FANTABUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FANTABUD = registerItem("green_fantabud", () -> new BlockItem(BlockInit.GREEN_FANTABUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> FANTAFERN = registerItem("fantafern", () -> new BlockItem(BlockInit.FANTAFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FANTAFERN = registerItem("green_fantafern", () -> new BlockItem(BlockInit.GREEN_FANTAFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> FANTAGRASS = registerItem("fantagrass", () -> new BlockItem(BlockInit.FANTAGRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FANTAGRASS = registerItem("green_fantagrass", () -> new BlockItem(BlockInit.GREEN_FANTAGRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> FANTASY_GRASS_BLOCK = registerItem("fantasy_grass_block", () -> new BlockItem(BlockInit.FANTASY_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_GRASS_BLOCK = registerItem("nightshade_grass_block", () -> new BlockItem(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_DIRT = registerItem("nightshade_dirt", () -> new BlockItem(BlockInit.NIGHTSHADE_DIRT.get(), new Item.Properties()));

    //grimstone
    public static final RegistryObject<Item> GRIMSTONE = registerItem("grimstone", () -> new BlockItem(BlockInit.GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICKS = registerItem("grimstone_bricks", () -> new BlockItem(BlockInit.GRIMSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICK_SLAB = registerItem("grimstone_brick_slab", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICK_STAIRS = registerItem("grimstone_brick_stairs", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRIMSTONE_BRICK_WALL = registerItem("grimstone_brick_wall", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE = registerItem("polished_grimstone", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE_SLAB = registerItem("polished_grimstone_slab", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE_STAIRS = registerItem("polished_grimstone_stairs", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_GRIMSTONE_WALL = registerItem("polished_grimstone_wall", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_GRIMSTONE = registerItem("chiseled_grimstone", () -> new BlockItem(BlockInit.CHISELED_GRIMSTONE.get(), new Item.Properties()));

    //voidstone
    public static final RegistryObject<Item> VOIDSTONE = registerItem("voidstone", () -> new BlockItem(BlockInit.VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICKS = registerItem("voidstone_bricks", () -> new BlockItem(BlockInit.VOIDSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICK_SLAB = registerItem("voidstone_brick_slab", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICK_STAIRS = registerItem("voidstone_brick_stairs", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_BRICK_WALL = registerItem("voidstone_brick_wall", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE = registerItem("polished_voidstone", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE_SLAB = registerItem("polished_voidstone_slab", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE_STAIRS = registerItem("polished_voidstone_stairs", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_VOIDSTONE_WALL = registerItem("polished_voidstone_wall", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_VOIDSTONE = registerItem("chiseled_voidstone", () -> new BlockItem(BlockInit.CHISELED_VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_VOIDSTONE = registerItem("glowing_voidstone", () -> new BlockItem(BlockInit.GLOWING_VOIDSTONE.get(), new Item.Properties()));

    //mud
    public static final RegistryObject<Item> NIGHTSHADE_MUD = registerItem("nightshade_mud", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_NIGHTSHADE_MUD = registerItem("glowing_nightshade_mud", () -> new BlockItem(BlockInit.GLOWING_NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> PACKED_NIGHTSHADE_MUD = registerItem("packed_nightshade_mud", () -> new BlockItem(BlockInit.PACKED_NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICKS = registerItem("nightshade_mud_bricks", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICK_SLAB = registerItem("nightshade_mud_brick_slab", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICK_STAIRS = registerItem("nightshade_mud_brick_stairs", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NIGHTSHADE_MUD_BRICK_WALL = registerItem("nightshade_mud_brick_wall", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), new Item.Properties()));

    //misc
    public static final RegistryObject<Item> ENERGY_BLOCK = registerItem("energy_block", () -> new BlockItem(BlockInit.ENERGY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STARLIGHT_GOLEM_SPAWNER = registerItem("starlight_golem_spawner", () -> new BlockItem(BlockInit.STARLIGHT_GOLEM_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_MONSTROSITY_SPAWNER = registerItem("lunar_monstrosity_spawner", () -> new BlockItem(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LOOT_BAG = registerItem("loot_bag", () -> new LootBagItem(new Item.Properties().fireResistant().rarity(STARLIGHT)));
    public static final RegistryObject<Item> BOOK = registerItem("book", () -> new SLBookItem(new Item.Properties().rarity(STARLIGHT)));

    //aethersent
    public static final RegistryObject<Item> AETHERSENT_BLOCK = registerItem("aethersent_block", () -> new BlockItem(BlockInit.AETHERSENT_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_INGOT = registerItem("aethersent_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAGE_OF_STARS = registerItem("rage_of_stars", () -> new RageOfStarsItem(new Item.Properties().stacksTo(1).defaultDurability(1000)));
    public static final RegistryObject<Item> STARFALL_LONGBOW = registerItem("starfall_longbow", () -> new StarfallLongbowItem(new Item.Properties().stacksTo(1).defaultDurability(2000)));
    public static final RegistryObject<Item> AETHERSENT_HOOD = registerItem("aethersent_hood",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_CAPE = registerItem("aethersent_cape",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_BOTTOMS = registerItem("aethersent_bottoms",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> AETHERSENT_BOOTS = registerItem("aethersent_boots",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.BOOTS, new Item.Properties()));


    //thermal springstone
    public static final RegistryObject<Item> SPRINGSTONE = registerItem("springstone", () -> new BlockItem(BlockInit.SPRINGSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE = registerItem("thermal_springstone", () -> new BlockItem(BlockInit.THERMAL_SPRINGSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_INGOT = registerItem("thermal_springstone_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_SWORD = registerItem("thermal_springstone_sword",
            () -> new SwordItem(ESItemTiers.THERMAL_SPRINGSTONE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_PICKAXE = registerItem("thermal_springstone_pickaxe",
            () -> new PickaxeItem(ESItemTiers.THERMAL_SPRINGSTONE,1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_AXE = registerItem("thermal_springstone_axe",
            () -> new AxeItem(ESItemTiers.THERMAL_SPRINGSTONE, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_SCYTHE = registerItem("thermal_springstone_scythe",
            () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.THERMAL_SPRINGSTONE, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_HAMMER = registerItem("thermal_springstone_hammer",
            () -> ESPlatform.INSTANCE.createHammer(ESItemTiers.THERMAL_SPRINGSTONE, 8, -3.5F, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_HELMET = registerItem("thermal_springstone_helmet",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_CHESTPLATE = registerItem("thermal_springstone_chestplate",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_LEGGINGS = registerItem("thermal_springstone_leggings",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_SPRINGSTONE_BOOTS = registerItem("thermal_springstone_boots",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.BOOTS, new Item.Properties()));

    //swamp silver
    public static final RegistryObject<Item> SWAMP_SILVER_ORE = registerItem("swamp_silver_ore", () -> new BlockItem(BlockInit.SWAMP_SILVER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_BLOCK = registerItem("swamp_silver_block", () -> new BlockItem(BlockInit.SWAMP_SILVER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_INGOT = registerItem("swamp_silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_NUGGET = registerItem("swamp_silver_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_SWORD = registerItem("swamp_silver_sword",
            () -> new SwordItem(ESItemTiers.SWAMP_SILVER, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_PICKAXE = registerItem("swamp_silver_pickaxe",
            () -> new PickaxeItem(ESItemTiers.SWAMP_SILVER,1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_AXE = registerItem("swamp_silver_axe",
            () -> new AxeItem(ESItemTiers.SWAMP_SILVER, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_SICKLE = registerItem("swamp_silver_sickle",
            () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.SWAMP_SILVER, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_HELMET = registerItem("swamp_silver_helmet",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_CHESTPLATE = registerItem("swamp_silver_chestplate",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_LEGGINGS = registerItem("swamp_silver_leggings",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_SILVER_BOOTS = registerItem("swamp_silver_boots",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.BOOTS, new Item.Properties()));

    //boss materials
    public static final RegistryObject<Item> GOLEM_STEEL_INGOT = registerItem("golem_steel_ingot", () -> new Item(new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item> OXIDIZED_GOLEM_STEEL_INGOT = registerItem("oxidized_golem_steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TENACIOUS_PETAL = registerItem("tenacious_petal", () -> new Item(new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item> CRYSTAL_CROSSBOW = registerItem("crystal_crossbow", () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(2000).rarity(STARLIGHT)));
    public static final RegistryObject<Item> MOONRING_BOW = registerItem("moonring_bow", () -> new MoonRingBowItem(new Item.Properties().stacksTo(1).durability(2000).rarity(STARLIGHT)));
    public static final RegistryObject<Item> MOONRING_GREATSWORD = registerItem("moonring_greatsword", () -> new MoonringGreatswordItem(ESItemTiers.PETAL, 6, -2.8F, new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item> PETAL_SCYTHE = registerItem("petal_scythe", () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.PETAL, 3, -1.0F, new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item> SEEKING_EYE = registerItem("seeking_eye", () -> new SeekingEyeItem(new Item.Properties()));
    public static void postRegistry() {}
}
