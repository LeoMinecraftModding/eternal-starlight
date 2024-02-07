package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ESArmorMaterials;
import cn.leolezury.eternalstarlight.common.item.armor.SwampSilverArmorItem;
import cn.leolezury.eternalstarlight.common.item.magic.LivingArmItem;
import cn.leolezury.eternalstarlight.common.item.magic.SimpleSpellItem;
import cn.leolezury.eternalstarlight.common.item.misc.*;
import cn.leolezury.eternalstarlight.common.item.weapon.*;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, EternalStarlight.MOD_ID);
    private static final Rarity STARLIGHT = ESPlatform.INSTANCE.getStarlightRarity();
    private static final Rarity DEMON = ESPlatform.INSTANCE.getDemonRarity();

    public static final List<ResourceKey<Item>> REGISTERED_ITEMS = new ArrayList<>();
    private static RegistryObject<Item, Item> registerItem(String name, Supplier<? extends Item> supplier) {
        REGISTERED_ITEMS.add(ResourceKey.create(Registries.ITEM, new ResourceLocation(EternalStarlight.MOD_ID, name)));
        return ITEMS.register(name, supplier);
    }
    public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_BLOCK = registerItem("red_starlight_crystal_block", () -> new BlockItem(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_BLOCK = registerItem("blue_starlight_crystal_block", () -> new BlockItem(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_CLUSTER = registerItem("red_starlight_crystal_cluster", () -> new BlockItem(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_CLUSTER = registerItem("blue_starlight_crystal_cluster", () -> new BlockItem(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_CRYSTAL_MOSS_CARPET = registerItem("red_crystal_moss_carpet", () -> new BlockItem(BlockInit.RED_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_CRYSTAL_MOSS_CARPET = registerItem("blue_crystal_moss_carpet", () -> new BlockItem(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_SHARD = registerItem("red_starlight_crystal_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_SHARD = registerItem("blue_starlight_crystal_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_BERRIES = registerItem("lunar_berries", () -> new ItemNameBlockItem(BlockInit.BERRIES_VINES.get(), (new Item.Properties()).food((new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).build())));
    public static final RegistryObject<Item, Item> CAVE_MOSS = registerItem("cave_moss", () -> new BlockItem(BlockInit.CAVE_MOSS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> ABYSSAL_FRUIT = registerItem("abyssal_fruit", () -> new ItemNameBlockItem(BlockInit.ABYSSAL_KELP.get(), (new Item.Properties()).food((new FoodProperties.Builder()).nutrition(5).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.GLOWING, 600, 0), 0.3F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0), 0.3F).build())));

    public static final RegistryObject<Item, Item> DEAD_TENTACLES_CORAL = registerItem("dead_tentacles_coral", () -> new BlockItem(BlockInit.DEAD_TENTACLES_CORAL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TENTACLES_CORAL = registerItem("tentacles_coral", () -> new BlockItem(BlockInit.TENTACLES_CORAL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DEAD_TENTACLES_CORAL_FAN = registerItem("dead_tentacles_coral_fan", () -> new StandingAndWallBlockItem(BlockInit.DEAD_TENTACLES_CORAL_FAN.get(), BlockInit.DEAD_TENTACLES_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> TENTACLES_CORAL_FAN = registerItem("tentacles_coral_fan", () -> new StandingAndWallBlockItem(BlockInit.TENTACLES_CORAL_FAN.get(), BlockInit.TENTACLES_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> DEAD_TENTACLES_CORAL_BLOCK = registerItem("dead_tentacles_coral_block", () -> new BlockItem(BlockInit.DEAD_TENTACLES_CORAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TENTACLES_CORAL_BLOCK = registerItem("tentacles_coral_block", () -> new BlockItem(BlockInit.TENTACLES_CORAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DEAD_GOLDEN_CORAL = registerItem("dead_golden_coral", () -> new BlockItem(BlockInit.DEAD_GOLDEN_CORAL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLDEN_CORAL = registerItem("golden_coral", () -> new BlockItem(BlockInit.GOLDEN_CORAL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DEAD_GOLDEN_CORAL_FAN = registerItem("dead_golden_coral_fan", () -> new StandingAndWallBlockItem(BlockInit.DEAD_GOLDEN_CORAL_FAN.get(), BlockInit.DEAD_GOLDEN_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> GOLDEN_CORAL_FAN = registerItem("golden_coral_fan", () -> new StandingAndWallBlockItem(BlockInit.GOLDEN_CORAL_FAN.get(), BlockInit.GOLDEN_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> DEAD_GOLDEN_CORAL_BLOCK = registerItem("dead_golden_coral_block", () -> new BlockItem(BlockInit.DEAD_GOLDEN_CORAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLDEN_CORAL_BLOCK = registerItem("golden_coral_block", () -> new BlockItem(BlockInit.GOLDEN_CORAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DEAD_CRYSTALLUM_CORAL = registerItem("dead_crystallum_coral", () -> new BlockItem(BlockInit.DEAD_CRYSTALLUM_CORAL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CRYSTALLUM_CORAL = registerItem("crystallum_coral", () -> new BlockItem(BlockInit.CRYSTALLUM_CORAL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DEAD_CRYSTALLUM_CORAL_FAN = registerItem("dead_crystallum_coral_fan", () -> new StandingAndWallBlockItem(BlockInit.DEAD_CRYSTALLUM_CORAL_FAN.get(), BlockInit.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> CRYSTALLUM_CORAL_FAN = registerItem("crystallum_coral_fan", () -> new StandingAndWallBlockItem(BlockInit.CRYSTALLUM_CORAL_FAN.get(), BlockInit.CRYSTALLUM_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> DEAD_CRYSTALLUM_CORAL_BLOCK = registerItem("dead_crystallum_coral_block", () -> new BlockItem(BlockInit.DEAD_CRYSTALLUM_CORAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CRYSTALLUM_CORAL_BLOCK = registerItem("crystallum_coral_block", () -> new BlockItem(BlockInit.CRYSTALLUM_CORAL_BLOCK.get(), new Item.Properties()));

    // lunar wood
    public static final RegistryObject<Item, Item> LUNAR_SAPLING = registerItem("lunar_sapling", () -> new BlockItem(BlockInit.LUNAR_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_LEAVES = registerItem("lunar_leaves", () -> new BlockItem(BlockInit.LUNAR_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_LOG = registerItem("lunar_log", () -> new BlockItem(BlockInit.LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_WOOD = registerItem("lunar_wood", () -> new BlockItem(BlockInit.LUNAR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_PLANKS = registerItem("lunar_planks", () -> new BlockItem(BlockInit.LUNAR_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_LUNAR_LOG = registerItem("stripped_lunar_log", () -> new BlockItem(BlockInit.STRIPPED_LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_LUNAR_WOOD = registerItem("stripped_lunar_wood", () -> new BlockItem(BlockInit.STRIPPED_LUNAR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_DOOR = registerItem("lunar_door", () -> new BlockItem(BlockInit.LUNAR_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_TRAPDOOR = registerItem("lunar_trapdoor", () -> new BlockItem(BlockInit.LUNAR_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_PRESSURE_PLATE = registerItem("lunar_pressure_plate", () -> new BlockItem(BlockInit.LUNAR_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_BUTTON = registerItem("lunar_button", () -> new BlockItem(BlockInit.LUNAR_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_FENCE = registerItem("lunar_fence", () -> new BlockItem(BlockInit.LUNAR_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_FENCE_GATE = registerItem("lunar_fence_gate", () -> new BlockItem(BlockInit.LUNAR_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_SLAB = registerItem("lunar_slab", () -> new BlockItem(BlockInit.LUNAR_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_STAIRS = registerItem("lunar_stairs", () -> new BlockItem(BlockInit.LUNAR_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DEAD_LUNAR_LOG = registerItem("dead_lunar_log", () -> new BlockItem(BlockInit.DEAD_LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_CRYSTALLIZED_LUNAR_LOG = registerItem("red_crystallized_lunar_log", () -> new BlockItem(BlockInit.RED_CRYSTALLIZED_LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_CRYSTALLIZED_LUNAR_LOG = registerItem("blue_crystallized_lunar_log", () -> new BlockItem(BlockInit.BLUE_CRYSTALLIZED_LUNAR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_SIGN = registerItem("lunar_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.LUNAR_SIGN.get(), BlockInit.LUNAR_WALL_SIGN.get()));
    public static final RegistryObject<Item, Item> LUNAR_HANGING_SIGN = registerItem("lunar_hanging_sign", () -> new HangingSignItem(BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.LUNAR_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item, Item> LUNAR_BOAT = registerItem("lunar_boat", () -> new ESBoatItem(false, ESBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> LUNAR_CHEST_BOAT = registerItem("lunar_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));

    // northland wood
    public static final RegistryObject<Item, Item> NORTHLAND_SAPLING = registerItem("northland_sapling", () -> new BlockItem(BlockInit.NORTHLAND_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_LEAVES = registerItem("northland_leaves", () -> new BlockItem(BlockInit.NORTHLAND_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_LOG = registerItem("northland_log", () -> new BlockItem(BlockInit.NORTHLAND_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_WOOD = registerItem("northland_wood", () -> new BlockItem(BlockInit.NORTHLAND_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_PLANKS = registerItem("northland_planks", () -> new BlockItem(BlockInit.NORTHLAND_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_NORTHLAND_LOG = registerItem("stripped_northland_log", () -> new BlockItem(BlockInit.STRIPPED_NORTHLAND_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_NORTHLAND_WOOD = registerItem("stripped_northland_wood", () -> new BlockItem(BlockInit.STRIPPED_NORTHLAND_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_DOOR = registerItem("northland_door", () -> new BlockItem(BlockInit.NORTHLAND_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_TRAPDOOR = registerItem("northland_trapdoor", () -> new BlockItem(BlockInit.NORTHLAND_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_PRESSURE_PLATE = registerItem("northland_pressure_plate", () -> new BlockItem(BlockInit.NORTHLAND_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_BUTTON = registerItem("northland_button", () -> new BlockItem(BlockInit.NORTHLAND_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_FENCE = registerItem("northland_fence", () -> new BlockItem(BlockInit.NORTHLAND_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_FENCE_GATE = registerItem("northland_fence_gate", () -> new BlockItem(BlockInit.NORTHLAND_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_SLAB = registerItem("northland_slab", () -> new BlockItem(BlockInit.NORTHLAND_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_STAIRS = registerItem("northland_stairs", () -> new BlockItem(BlockInit.NORTHLAND_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NORTHLAND_SIGN = registerItem("northland_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get()));
    public static final RegistryObject<Item, Item> NORTHLAND_HANGING_SIGN = registerItem("northland_hanging_sign", () -> new HangingSignItem(BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item, Item> NORTHLAND_BOAT = registerItem("northland_boat", () -> new ESBoatItem(false, ESBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> NORTHLAND_CHEST_BOAT = registerItem("northland_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));

    // starlight mangrove wood
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_SAPLING = registerItem("starlight_mangrove_sapling", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_LEAVES = registerItem("starlight_mangrove_leaves", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_LOG = registerItem("starlight_mangrove_log", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_WOOD = registerItem("starlight_mangrove_wood", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_PLANKS = registerItem("starlight_mangrove_planks", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_STARLIGHT_MANGROVE_LOG = registerItem("stripped_starlight_mangrove_log", () -> new BlockItem(BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_STARLIGHT_MANGROVE_WOOD = registerItem("stripped_starlight_mangrove_wood", () -> new BlockItem(BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_DOOR = registerItem("starlight_mangrove_door", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_TRAPDOOR = registerItem("starlight_mangrove_trapdoor", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_PRESSURE_PLATE = registerItem("starlight_mangrove_pressure_plate", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_BUTTON = registerItem("starlight_mangrove_button", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_FENCE = registerItem("starlight_mangrove_fence", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_FENCE_GATE = registerItem("starlight_mangrove_fence_gate", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_SLAB = registerItem("starlight_mangrove_slab", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_STAIRS = registerItem("starlight_mangrove_stairs", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_ROOTS = registerItem("starlight_mangrove_roots", () -> new BlockItem(BlockInit.STARLIGHT_MANGROVE_ROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> MUDDY_STARLIGHT_MANGROVE_ROOTS = registerItem("muddy_starlight_mangrove_roots", () -> new BlockItem(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_SIGN = registerItem("starlight_mangrove_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get()));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_HANGING_SIGN = registerItem("starlight_mangrove_hanging_sign", () -> new HangingSignItem(BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_BOAT = registerItem("starlight_mangrove_boat", () -> new ESBoatItem(false, ESBoat.Type.STARLIGHT_MANGROVE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> STARLIGHT_MANGROVE_CHEST_BOAT = registerItem("starlight_mangrove_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.STARLIGHT_MANGROVE, new Item.Properties().stacksTo(1)));

    // scarlet wood
    public static final RegistryObject<Item, Item> SCARLET_SAPLING = registerItem("scarlet_sapling", () -> new BlockItem(BlockInit.SCARLET_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_LEAVES = registerItem("scarlet_leaves", () -> new BlockItem(BlockInit.SCARLET_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_LEAVES_PILE = registerItem("scarlet_leaves_pile", () -> new BlockItem(BlockInit.SCARLET_LEAVES_PILE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_LOG = registerItem("scarlet_log", () -> new BlockItem(BlockInit.SCARLET_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_WOOD = registerItem("scarlet_wood", () -> new BlockItem(BlockInit.SCARLET_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_PLANKS = registerItem("scarlet_planks", () -> new BlockItem(BlockInit.SCARLET_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_SCARLET_LOG = registerItem("stripped_scarlet_log", () -> new BlockItem(BlockInit.STRIPPED_SCARLET_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_SCARLET_WOOD = registerItem("stripped_scarlet_wood", () -> new BlockItem(BlockInit.STRIPPED_SCARLET_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_DOOR = registerItem("scarlet_door", () -> new BlockItem(BlockInit.SCARLET_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_TRAPDOOR = registerItem("scarlet_trapdoor", () -> new BlockItem(BlockInit.SCARLET_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_PRESSURE_PLATE = registerItem("scarlet_pressure_plate", () -> new BlockItem(BlockInit.SCARLET_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_BUTTON = registerItem("scarlet_button", () -> new BlockItem(BlockInit.SCARLET_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_FENCE = registerItem("scarlet_fence", () -> new BlockItem(BlockInit.SCARLET_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_FENCE_GATE = registerItem("scarlet_fence_gate", () -> new BlockItem(BlockInit.SCARLET_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_SLAB = registerItem("scarlet_slab", () -> new BlockItem(BlockInit.SCARLET_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_STAIRS = registerItem("scarlet_stairs", () -> new BlockItem(BlockInit.SCARLET_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_SIGN = registerItem("scarlet_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.SCARLET_SIGN.get(), BlockInit.SCARLET_WALL_SIGN.get()));
    public static final RegistryObject<Item, Item> SCARLET_HANGING_SIGN = registerItem("scarlet_hanging_sign", () -> new HangingSignItem(BlockInit.SCARLET_HANGING_SIGN.get(), BlockInit.SCARLET_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item, Item> SCARLET_BOAT = registerItem("scarlet_boat", () -> new ESBoatItem(false, ESBoat.Type.SCARLET, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> SCARLET_CHEST_BOAT = registerItem("scarlet_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.SCARLET, new Item.Properties().stacksTo(1)));

    // torreya wood
    public static final RegistryObject<Item, Item> TORREYA_SAPLING = registerItem("torreya_sapling", () -> new BlockItem(BlockInit.TORREYA_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_LEAVES = registerItem("torreya_leaves", () -> new BlockItem(BlockInit.TORREYA_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_LOG = registerItem("torreya_log", () -> new BlockItem(BlockInit.TORREYA_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_WOOD = registerItem("torreya_wood", () -> new BlockItem(BlockInit.TORREYA_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_PLANKS = registerItem("torreya_planks", () -> new BlockItem(BlockInit.TORREYA_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_TORREYA_LOG = registerItem("stripped_torreya_log", () -> new BlockItem(BlockInit.STRIPPED_TORREYA_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STRIPPED_TORREYA_WOOD = registerItem("stripped_torreya_wood", () -> new BlockItem(BlockInit.STRIPPED_TORREYA_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_DOOR = registerItem("torreya_door", () -> new BlockItem(BlockInit.TORREYA_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_TRAPDOOR = registerItem("torreya_trapdoor", () -> new BlockItem(BlockInit.TORREYA_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_PRESSURE_PLATE = registerItem("torreya_pressure_plate", () -> new BlockItem(BlockInit.TORREYA_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_BUTTON = registerItem("torreya_button", () -> new BlockItem(BlockInit.TORREYA_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_FENCE = registerItem("torreya_fence", () -> new BlockItem(BlockInit.TORREYA_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_FENCE_GATE = registerItem("torreya_fence_gate", () -> new BlockItem(BlockInit.TORREYA_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_SLAB = registerItem("torreya_slab", () -> new BlockItem(BlockInit.TORREYA_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_STAIRS = registerItem("torreya_stairs", () -> new BlockItem(BlockInit.TORREYA_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TORREYA_SIGN = registerItem("torreya_sign", () -> new SignItem(new Item.Properties().stacksTo(32), BlockInit.TORREYA_SIGN.get(), BlockInit.TORREYA_WALL_SIGN.get()));
    public static final RegistryObject<Item, Item> TORREYA_HANGING_SIGN = registerItem("torreya_hanging_sign", () -> new HangingSignItem(BlockInit.TORREYA_HANGING_SIGN.get(), BlockInit.TORREYA_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(32)));
    public static final RegistryObject<Item, Item> TORREYA_BOAT = registerItem("torreya_boat", () -> new ESBoatItem(false, ESBoat.Type.TORREYA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> TORREYA_CHEST_BOAT = registerItem("torreya_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.TORREYA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> TORREYA_VINES = registerItem("torreya_vines", () -> new BlockItem(BlockInit.TORREYA_VINES.get(), new Item.Properties()));

    // grimstone
    public static final RegistryObject<Item, Item> GRIMSTONE = registerItem("grimstone", () -> new BlockItem(BlockInit.GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE = registerItem("cobbled_grimstone", () -> new BlockItem(BlockInit.COBBLED_GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE_SLAB = registerItem("cobbled_grimstone_slab", () -> new BlockItem(BlockInit.COBBLED_GRIMSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE_STAIRS = registerItem("cobbled_grimstone_stairs", () -> new BlockItem(BlockInit.COBBLED_GRIMSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE_WALL = registerItem("cobbled_grimstone_wall", () -> new BlockItem(BlockInit.COBBLED_GRIMSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_BRICKS = registerItem("grimstone_bricks", () -> new BlockItem(BlockInit.GRIMSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_BRICK_SLAB = registerItem("grimstone_brick_slab", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_BRICK_STAIRS = registerItem("grimstone_brick_stairs", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_BRICK_WALL = registerItem("grimstone_brick_wall", () -> new BlockItem(BlockInit.GRIMSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE = registerItem("polished_grimstone", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_SLAB = registerItem("polished_grimstone_slab", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_STAIRS = registerItem("polished_grimstone_stairs", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_WALL = registerItem("polished_grimstone_wall", () -> new BlockItem(BlockInit.POLISHED_GRIMSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_TILES = registerItem("grimstone_tiles", () -> new BlockItem(BlockInit.GRIMSTONE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_TILE_SLAB = registerItem("grimstone_tile_slab", () -> new BlockItem(BlockInit.GRIMSTONE_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_TILE_STAIRS = registerItem("grimstone_tile_stairs", () -> new BlockItem(BlockInit.GRIMSTONE_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRIMSTONE_TILE_WALL = registerItem("grimstone_tile_wall", () -> new BlockItem(BlockInit.GRIMSTONE_TILE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_GRIMSTONE = registerItem("chiseled_grimstone", () -> new BlockItem(BlockInit.CHISELED_GRIMSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_GRIMSTONE = registerItem("glowing_grimstone", () -> new BlockItem(BlockInit.GLOWING_GRIMSTONE.get(), new Item.Properties()));

    // voidstone
    public static final RegistryObject<Item, Item> VOIDSTONE = registerItem("voidstone", () -> new BlockItem(BlockInit.VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE = registerItem("cobbled_voidstone", () -> new BlockItem(BlockInit.COBBLED_VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE_SLAB = registerItem("cobbled_voidstone_slab", () -> new BlockItem(BlockInit.COBBLED_VOIDSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE_STAIRS = registerItem("cobbled_voidstone_stairs", () -> new BlockItem(BlockInit.COBBLED_VOIDSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE_WALL = registerItem("cobbled_voidstone_wall", () -> new BlockItem(BlockInit.COBBLED_VOIDSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_BRICKS = registerItem("voidstone_bricks", () -> new BlockItem(BlockInit.VOIDSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_BRICK_SLAB = registerItem("voidstone_brick_slab", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_BRICK_STAIRS = registerItem("voidstone_brick_stairs", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_BRICK_WALL = registerItem("voidstone_brick_wall", () -> new BlockItem(BlockInit.VOIDSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE = registerItem("polished_voidstone", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE_SLAB = registerItem("polished_voidstone_slab", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE_STAIRS = registerItem("polished_voidstone_stairs", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE_WALL = registerItem("polished_voidstone_wall", () -> new BlockItem(BlockInit.POLISHED_VOIDSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_TILES = registerItem("voidstone_tiles", () -> new BlockItem(BlockInit.VOIDSTONE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_TILE_SLAB = registerItem("voidstone_tile_slab", () -> new BlockItem(BlockInit.VOIDSTONE_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_TILE_STAIRS = registerItem("voidstone_tile_stairs", () -> new BlockItem(BlockInit.VOIDSTONE_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_TILE_WALL = registerItem("voidstone_tile_wall", () -> new BlockItem(BlockInit.VOIDSTONE_TILE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_VOIDSTONE = registerItem("chiseled_voidstone", () -> new BlockItem(BlockInit.CHISELED_VOIDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_VOIDSTONE = registerItem("glowing_voidstone", () -> new BlockItem(BlockInit.GLOWING_VOIDSTONE.get(), new Item.Properties()));

    // the abyss
    public static final RegistryObject<Item, Item> ABYSSLATE = registerItem("abysslate", () -> new BlockItem(BlockInit.ABYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE = registerItem("polished_abysslate", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_SLAB = registerItem("polished_abysslate_slab", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_STAIRS = registerItem("polished_abysslate_stairs", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_WALL = registerItem("polished_abysslate_wall", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICKS = registerItem("polished_abysslate_bricks", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICK_SLAB = registerItem("polished_abysslate_brick_slab", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICK_STAIRS = registerItem("polished_abysslate_brick_stairs", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICK_WALL = registerItem("polished_abysslate_brick_wall", () -> new BlockItem(BlockInit.POLISHED_ABYSSLATE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_POLISHED_ABYSSLATE = registerItem("chiseled_polished_abysslate", () -> new BlockItem(BlockInit.CHISELED_POLISHED_ABYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> ABYSSAL_MAGMA_BLOCK = registerItem("abyssal_magma_block", () -> new BlockItem(BlockInit.ABYSSAL_MAGMA_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> ABYSSAL_GEYSER = registerItem("abyssal_geyser", () -> new BlockItem(BlockInit.ABYSSAL_GEYSER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMABYSSLATE = registerItem("thermabysslate", () -> new BlockItem(BlockInit.THERMABYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE = registerItem("polished_thermabysslate", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_SLAB = registerItem("polished_thermabysslate_slab", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_STAIRS = registerItem("polished_thermabysslate_stairs", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_WALL = registerItem("polished_thermabysslate_wall", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICKS = registerItem("polished_thermabysslate_bricks", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICK_SLAB = registerItem("polished_thermabysslate_brick_slab", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICK_STAIRS = registerItem("polished_thermabysslate_brick_stairs", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICK_WALL = registerItem("polished_thermabysslate_brick_wall", () -> new BlockItem(BlockInit.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_POLISHED_THERMABYSSLATE = registerItem("chiseled_polished_thermabysslate", () -> new BlockItem(BlockInit.CHISELED_POLISHED_THERMABYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMABYSSAL_MAGMA_BLOCK = registerItem("thermabyssal_magma_block", () -> new BlockItem(BlockInit.THERMABYSSAL_MAGMA_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMABYSSAL_GEYSER = registerItem("thermabyssal_geyser", () -> new BlockItem(BlockInit.THERMABYSSAL_GEYSER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CRYOBYSSLATE = registerItem("cryobysslate", () -> new BlockItem(BlockInit.CRYOBYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE = registerItem("polished_cryobysslate", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_SLAB = registerItem("polished_cryobysslate_slab", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_STAIRS = registerItem("polished_cryobysslate_stairs", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_WALL = registerItem("polished_cryobysslate_wall", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICKS = registerItem("polished_cryobysslate_bricks", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICK_SLAB = registerItem("polished_cryobysslate_brick_slab", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICK_STAIRS = registerItem("polished_cryobysslate_brick_stairs", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICK_WALL = registerItem("polished_cryobysslate_brick_wall", () -> new BlockItem(BlockInit.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_POLISHED_CRYOBYSSLATE = registerItem("chiseled_polished_cryobysslate", () -> new BlockItem(BlockInit.CHISELED_POLISHED_CRYOBYSSLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CRYOBYSSAL_MAGMA_BLOCK = registerItem("cryobyssal_magma_block", () -> new BlockItem(BlockInit.CRYOBYSSAL_MAGMA_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CRYOBYSSAL_GEYSER = registerItem("cryobyssal_geyser", () -> new BlockItem(BlockInit.CRYOBYSSAL_GEYSER.get(), new Item.Properties()));

    // mud
    public static final RegistryObject<Item, Item> NIGHTSHADE_MUD = registerItem("nightshade_mud", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_NIGHTSHADE_MUD = registerItem("glowing_nightshade_mud", () -> new BlockItem(BlockInit.GLOWING_NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PACKED_NIGHTSHADE_MUD = registerItem("packed_nightshade_mud", () -> new BlockItem(BlockInit.PACKED_NIGHTSHADE_MUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTSHADE_MUD_BRICKS = registerItem("nightshade_mud_bricks", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTSHADE_MUD_BRICK_SLAB = registerItem("nightshade_mud_brick_slab", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTSHADE_MUD_BRICK_STAIRS = registerItem("nightshade_mud_brick_stairs", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTSHADE_MUD_BRICK_WALL = registerItem("nightshade_mud_brick_wall", () -> new BlockItem(BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), new Item.Properties()));

    // sand
    public static final RegistryObject<Item, Item> TWILIGHT_SAND = registerItem("twilight_sand", () -> new BlockItem(BlockInit.TWILIGHT_SAND.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE = registerItem("twilight_sandstone", () -> new BlockItem(BlockInit.TWILIGHT_SANDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE_SLAB = registerItem("twilight_sandstone_slab", () -> new BlockItem(BlockInit.TWILIGHT_SANDSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE_STAIRS = registerItem("twilight_sandstone_stairs", () -> new BlockItem(BlockInit.TWILIGHT_SANDSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE_WALL = registerItem("twilight_sandstone_wall", () -> new BlockItem(BlockInit.TWILIGHT_SANDSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE = registerItem("cut_twilight_sandstone", () -> new BlockItem(BlockInit.CUT_TWILIGHT_SANDSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE_SLAB = registerItem("cut_twilight_sandstone_slab", () -> new BlockItem(BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE_STAIRS = registerItem("cut_twilight_sandstone_stairs", () -> new BlockItem(BlockInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE_WALL = registerItem("cut_twilight_sandstone_wall", () -> new BlockItem(BlockInit.CUT_TWILIGHT_SANDSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_TWILIGHT_SANDSTONE = registerItem("chiseled_twilight_sandstone", () -> new BlockItem(BlockInit.CHISELED_TWILIGHT_SANDSTONE.get(), new Item.Properties()));

    // golem steel
    public static final RegistryObject<Item, Item> GOLEM_STEEL_BLOCK = registerItem("golem_steel_block", () -> new BlockItem(BlockInit.GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_BLOCK = registerItem("oxidized_golem_steel_block", () -> new BlockItem(BlockInit.OXIDIZED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLEM_STEEL_SLAB = registerItem("golem_steel_slab", () -> new BlockItem(BlockInit.GOLEM_STEEL_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_SLAB = registerItem("oxidized_golem_steel_slab", () -> new BlockItem(BlockInit.OXIDIZED_GOLEM_STEEL_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLEM_STEEL_STAIRS = registerItem("golem_steel_stairs", () -> new BlockItem(BlockInit.GOLEM_STEEL_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_STAIRS = registerItem("oxidized_golem_steel_stairs", () -> new BlockItem(BlockInit.OXIDIZED_GOLEM_STEEL_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLEM_STEEL_TILES = registerItem("golem_steel_tiles", () -> new BlockItem(BlockInit.GOLEM_STEEL_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_TILES = registerItem("oxidized_golem_steel_tiles", () -> new BlockItem(BlockInit.OXIDIZED_GOLEM_STEEL_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLEM_STEEL_TILE_SLAB = registerItem("golem_steel_tile_slab", () -> new BlockItem(BlockInit.GOLEM_STEEL_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_TILE_SLAB = registerItem("oxidized_golem_steel_tile_slab", () -> new BlockItem(BlockInit.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GOLEM_STEEL_TILE_STAIRS = registerItem("golem_steel_tile_stairs", () -> new BlockItem(BlockInit.GOLEM_STEEL_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_TILE_STAIRS = registerItem("oxidized_golem_steel_tile_stairs", () -> new BlockItem(BlockInit.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_GOLEM_STEEL_BLOCK = registerItem("chiseled_golem_steel_block", () -> new BlockItem(BlockInit.CHISELED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK = registerItem("oxidized_chiseled_golem_steel_block", () -> new BlockItem(BlockInit.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));

    // tenacious petal
    public static final RegistryObject<Item, Item> LUNAR_MOSAIC = registerItem("lunar_mosaic", () -> new BlockItem(BlockInit.LUNAR_MOSAIC.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MOSAIC_SLAB = registerItem("lunar_mosaic_slab", () -> new BlockItem(BlockInit.LUNAR_MOSAIC_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MOSAIC_STAIRS = registerItem("lunar_mosaic_stairs", () -> new BlockItem(BlockInit.LUNAR_MOSAIC_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MOSAIC_FENCE = registerItem("lunar_mosaic_fence", () -> new BlockItem(BlockInit.LUNAR_MOSAIC_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MOSAIC_FENCE_GATE = registerItem("lunar_mosaic_fence_gate", () -> new BlockItem(BlockInit.LUNAR_MOSAIC_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MAT = registerItem("lunar_mat", () -> new BlockItem(BlockInit.LUNAR_MAT.get(), new Item.Properties()));

    // doomeden
    public static final RegistryObject<Item, Item> BROKEN_DOOMEDEN_BONE = registerItem("broken_doomeden_bone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> BONEMORE_BROADSWORD = registerItem("bonemore_broadsword", () -> new GreatswordItem(ESItemTiers.DOOMEDEN, 6, -2.8F, new Item.Properties().rarity(DEMON)));
    public static final RegistryObject<Item, Item> BOW_OF_BLOOD = registerItem("bow_of_blood", () -> new BloodBowItem(new Item.Properties().stacksTo(1).rarity(DEMON)));
    public static final RegistryObject<Item, Item> LIVING_ARM = registerItem("living_arm", () -> new LivingArmItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> DOOMED_TORCH = registerItem("doomed_torch", () -> new StandingAndWallBlockItem(BlockInit.DOOMED_TORCH.get(), BlockInit.WALL_DOOMED_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> DOOMED_REDSTONE_TORCH = registerItem("doomed_redstone_torch", () -> new StandingAndWallBlockItem(BlockInit.DOOMED_REDSTONE_TORCH.get(), BlockInit.WALL_DOOMED_REDSTONE_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item, Item> DOOMEDEN_CARRION = registerItem("doomeden_carrion", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F).meat().build())));
    public static final RegistryObject<Item, Item> ROTTEN_HAM = registerItem("rotten_ham", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8f).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build())));
    public static final RegistryObject<Item, Item> EYE_OF_DOOM = registerItem("eye_of_doom", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_RAG = registerItem("doomeden_rag", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_FLESH_GRINDER = registerItem("doomeden_flesh_grinder", () -> new DoomedenGrinderItem(ESItemTiers.DOOMEDEN, 5, -2.0F, new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_SWORD = registerItem("doomeden_sword", () -> new DoomedenSwordItem(ESItemTiers.DOOMEDEN, 4, -1.8F, new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_BRICKS = registerItem("doomeden_bricks", () -> new BlockItem(BlockInit.DOOMEDEN_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_BRICK_SLAB = registerItem("doomeden_brick_slab", () -> new BlockItem(BlockInit.DOOMEDEN_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_BRICK_STAIRS = registerItem("doomeden_brick_stairs", () -> new BlockItem(BlockInit.DOOMEDEN_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_BRICK_WALL = registerItem("doomeden_brick_wall", () -> new BlockItem(BlockInit.DOOMEDEN_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICKS = registerItem("polished_doomeden_bricks", () -> new BlockItem(BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICK_SLAB = registerItem("polished_doomeden_brick_slab", () -> new BlockItem(BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICK_STAIRS = registerItem("polished_doomeden_brick_stairs", () -> new BlockItem(BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICK_WALL = registerItem("polished_doomeden_brick_wall", () -> new BlockItem(BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_TILES = registerItem("doomeden_tiles", () -> new BlockItem(BlockInit.DOOMEDEN_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_TILE_SLAB = registerItem("doomeden_tile_slab", () -> new BlockItem(BlockInit.DOOMEDEN_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_TILE_STAIRS = registerItem("doomeden_tile_stairs", () -> new BlockItem(BlockInit.DOOMEDEN_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_TILE_WALL = registerItem("doomeden_tile_wall", () -> new BlockItem(BlockInit.DOOMEDEN_TILE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHISELED_POLISHED_DOOMEDEN_BRICKS = registerItem("chiseled_polished_doomeden_bricks", () -> new BlockItem(BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS = registerItem("charged_chiseled_polished_doomeden_bricks", () -> new BlockItem(BlockInit.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_LIGHT = registerItem("doomeden_light", () -> new BlockItem(BlockInit.DOOMEDEN_LIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> DOOMEDEN_KEYHOLE = registerItem("doomeden_keyhole", () -> new BlockItem(BlockInit.DOOMEDEN_KEYHOLE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> REDSTONE_DOOMEDEN_KEYHOLE = registerItem("redstone_doomeden_keyhole", () -> new BlockItem(BlockInit.REDSTONE_DOOMEDEN_KEYHOLE.get(), new Item.Properties()));

    // common grass
    public static final RegistryObject<Item, Item> STARLIGHT_FLOWER = registerItem("starlight_flower", () -> new BlockItem(BlockInit.STARLIGHT_FLOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CONEBLOOM = registerItem("conebloom", () -> new BlockItem(BlockInit.CONEBLOOM.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTFAN = registerItem("nightfan", () -> new BlockItem(BlockInit.NIGHTFAN.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PINK_ROSE = registerItem("pink_rose", () -> new BlockItem(BlockInit.PINK_ROSE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_TORCHFLOWER = registerItem("starlight_torchflower", () -> new BlockItem(BlockInit.STARLIGHT_TORCHFLOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTFAN_BUSH = registerItem("nightfan_bush", () -> new BlockItem(BlockInit.NIGHTFAN_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PINK_ROSE_BUSH = registerItem("pink_rose_bush", () -> new BlockItem(BlockInit.PINK_ROSE_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHT_SPROUTS = registerItem("night_sprouts", () -> new BlockItem(BlockInit.NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_NIGHT_SPROUTS = registerItem("glowing_night_sprouts", () -> new BlockItem(BlockInit.GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SMALL_NIGHT_SPROUTS = registerItem("small_night_sprouts", () -> new BlockItem(BlockInit.SMALL_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SMALL_GLOWING_NIGHT_SPROUTS = registerItem("small_glowing_night_sprouts", () -> new BlockItem(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_GRASS = registerItem("lunar_grass", () -> new BlockItem(BlockInit.LUNAR_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_LUNAR_GRASS = registerItem("glowing_lunar_grass", () -> new BlockItem(BlockInit.GLOWING_LUNAR_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CRESCENT_GRASS = registerItem("crescent_grass", () -> new BlockItem(BlockInit.CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_CRESCENT_GRASS = registerItem("glowing_crescent_grass", () -> new BlockItem(BlockInit.GLOWING_CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PARASOL_GRASS = registerItem("parasol_grass", () -> new BlockItem(BlockInit.PARASOL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_PARASOL_GRASS = registerItem("glowing_parasol_grass", () -> new BlockItem(BlockInit.GLOWING_PARASOL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_BUSH = registerItem("lunar_bush", () -> new BlockItem(BlockInit.LUNAR_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_LUNAR_BUSH = registerItem("glowing_lunar_bush", () -> new BlockItem(BlockInit.GLOWING_LUNAR_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TALL_CRESCENT_GRASS = registerItem("tall_crescent_grass", () -> new BlockItem(BlockInit.TALL_CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TALL_GLOWING_CRESCENT_GRASS = registerItem("tall_glowing_crescent_grass", () -> new BlockItem(BlockInit.TALL_GLOWING_CRESCENT_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_REED = registerItem("lunar_reed", () -> new BlockItem(BlockInit.LUNAR_REED.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> WHISPERBLOOM = registerItem("whisperbloom", () -> new BlockItem(BlockInit.WHISPERBLOOM.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLADESPIKE = registerItem("gladespike", () -> new BlockItem(BlockInit.GLADESPIKE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VIVIDSTALK = registerItem("vividstalk", () -> new BlockItem(BlockInit.VIVIDSTALK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TALL_GLADESPIKE = registerItem("tall_gladespike", () -> new BlockItem(BlockInit.TALL_GLADESPIKE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_MUSHROOM = registerItem("glowing_mushroom", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_MUSHROOM_BLOCK = registerItem("glowing_mushroom_block", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GLOWING_MUSHROOM_STEM = registerItem("glowing_mushroom_stem", () -> new BlockItem(BlockInit.GLOWING_MUSHROOM_STEM.get(), new Item.Properties()));

    // swamp grass
    public static final RegistryObject<Item, Item> SWAMP_ROSE = registerItem("swamp_rose", () -> new BlockItem(BlockInit.SWAMP_ROSE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> FANTABUD = registerItem("fantabud", () -> new BlockItem(BlockInit.FANTABUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GREEN_FANTABUD = registerItem("green_fantabud", () -> new BlockItem(BlockInit.GREEN_FANTABUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> FANTAFERN = registerItem("fantafern", () -> new BlockItem(BlockInit.FANTAFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GREEN_FANTAFERN = registerItem("green_fantafern", () -> new BlockItem(BlockInit.GREEN_FANTAFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> FANTAGRASS = registerItem("fantagrass", () -> new BlockItem(BlockInit.FANTAGRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GREEN_FANTAGRASS = registerItem("green_fantagrass", () -> new BlockItem(BlockInit.GREEN_FANTAGRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item, Item> ORANGE_SCARLET_BUD = registerItem("orange_scarlet_bud", () -> new BlockItem(BlockInit.ORANGE_SCARLET_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PURPLE_SCARLET_BUD = registerItem("purple_scarlet_bud", () -> new BlockItem(BlockInit.PURPLE_SCARLET_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_SCARLET_BUD = registerItem("red_scarlet_bud", () -> new BlockItem(BlockInit.RED_SCARLET_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SCARLET_GRASS = registerItem("scarlet_grass", () -> new BlockItem(BlockInit.SCARLET_GRASS.get(), new Item.Properties()));

    // torreya forest grass
    public static final RegistryObject<Item, Item> WITHERED_STARLIGHT_FLOWER = registerItem("withered_starlight_flower", () -> new BlockItem(BlockInit.WITHERED_STARLIGHT_FLOWER.get(), new Item.Properties()));

    // desert grass
    public static final RegistryObject<Item, Item> DEAD_LUNAR_BUSH = registerItem("dead_lunar_bush", () -> new BlockItem(BlockInit.DEAD_LUNAR_BUSH.get(), new Item.Properties()));

    // water grass
    public static final RegistryObject<Item, Item> MOONLIGHT_LILY_PAD = registerItem("moonlight_lily_pad", () -> new PlaceOnWaterBlockItem(BlockInit.MOONLIGHT_LILY_PAD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIT_LILY_PAD = registerItem("starlit_lily_pad", () -> new PlaceOnWaterBlockItem(BlockInit.STARLIT_LILY_PAD.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> MOONLIGHT_DUCKWEED = registerItem("moonlight_duckweed", () -> new PlaceOnWaterBlockItem(BlockInit.MOONLIGHT_DUCKWEED.get(), new Item.Properties()));

    public static final RegistryObject<Item, Item> NIGHTSHADE_DIRT = registerItem("nightshade_dirt", () -> new BlockItem(BlockInit.NIGHTSHADE_DIRT.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> NIGHTSHADE_GRASS_BLOCK = registerItem("nightshade_grass_block", () -> new BlockItem(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> FANTASY_GRASS_BLOCK = registerItem("fantasy_grass_block", () -> new BlockItem(BlockInit.FANTASY_GRASS_BLOCK.get(), new Item.Properties()));

    // aethersent
    public static final RegistryObject<Item, Item> AETHERSENT_BLOCK = registerItem("aethersent_block", () -> new BlockItem(BlockInit.AETHERSENT_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> AETHERSENT_INGOT = registerItem("aethersent_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> RAGE_OF_STARS = registerItem("rage_of_stars", () -> new RageOfStarsItem(ESItemTiers.AETHERSENT, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item, Item> STARFALL_LONGBOW = registerItem("starfall_longbow", () -> new StarfallLongbowItem(new Item.Properties().stacksTo(1).defaultDurability(2000)));
    public static final RegistryObject<Item, Item> AETHERSENT_HOOD = registerItem("aethersent_hood",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item, Item> AETHERSENT_CAPE = registerItem("aethersent_cape",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item, Item> AETHERSENT_BOTTOMS = registerItem("aethersent_bottoms",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item, Item> AETHERSENT_BOOTS = registerItem("aethersent_boots",
            () -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT, ArmorItem.Type.BOOTS, new Item.Properties()));


    // thermal springstone
    public static final RegistryObject<Item, Item> SPRINGSTONE = registerItem("springstone", () -> new BlockItem(BlockInit.SPRINGSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE = registerItem("thermal_springstone", () -> new BlockItem(BlockInit.THERMAL_SPRINGSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_INGOT = registerItem("thermal_springstone_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_SWORD = registerItem("thermal_springstone_sword",
            () -> new SwordItem(ESItemTiers.THERMAL_SPRINGSTONE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_PICKAXE = registerItem("thermal_springstone_pickaxe",
            () -> new PickaxeItem(ESItemTiers.THERMAL_SPRINGSTONE,1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_AXE = registerItem("thermal_springstone_axe",
            () -> new AxeItem(ESItemTiers.THERMAL_SPRINGSTONE, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_SCYTHE = registerItem("thermal_springstone_scythe",
            () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.THERMAL_SPRINGSTONE, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_HAMMER = registerItem("thermal_springstone_hammer",
            () -> ESPlatform.INSTANCE.createHammer(ESItemTiers.THERMAL_SPRINGSTONE, 8, -3.5F, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_HELMET = registerItem("thermal_springstone_helmet",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_CHESTPLATE = registerItem("thermal_springstone_chestplate",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_LEGGINGS = registerItem("thermal_springstone_leggings",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_BOOTS = registerItem("thermal_springstone_boots",
            () -> ESPlatform.INSTANCE.createThermalSpringStoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE, ArmorItem.Type.BOOTS, new Item.Properties()));

    // swamp silver
    public static final RegistryObject<Item, Item> SWAMP_SILVER_ORE = registerItem("swamp_silver_ore", () -> new BlockItem(BlockInit.SWAMP_SILVER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_BLOCK = registerItem("swamp_silver_block", () -> new BlockItem(BlockInit.SWAMP_SILVER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_INGOT = registerItem("swamp_silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_NUGGET = registerItem("swamp_silver_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_SWORD = registerItem("swamp_silver_sword",
            () -> new SwordItem(ESItemTiers.SWAMP_SILVER, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_PICKAXE = registerItem("swamp_silver_pickaxe",
            () -> new PickaxeItem(ESItemTiers.SWAMP_SILVER, 1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_AXE = registerItem("swamp_silver_axe",
            () -> new AxeItem(ESItemTiers.SWAMP_SILVER, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_SICKLE = registerItem("swamp_silver_sickle",
            () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.SWAMP_SILVER, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_HELMET = registerItem("swamp_silver_helmet",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_CHESTPLATE = registerItem("swamp_silver_chestplate",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_LEGGINGS = registerItem("swamp_silver_leggings",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item, Item> SWAMP_SILVER_BOOTS = registerItem("swamp_silver_boots",
            () -> new SwampSilverArmorItem(ESArmorMaterials.SWAMP_SILVER, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item, Item> GRIMSTONE_REDSTONE_ORE = registerItem("grimstone_redstone_ore", () -> new BlockItem(BlockInit.GRIMSTONE_REDSTONE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_REDSTONE_ORE = registerItem("voidstone_redstone_ore", () -> new BlockItem(BlockInit.VOIDSTONE_REDSTONE_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item, Item> GRIMSTONE_SALTPETER_ORE = registerItem("grimstone_saltpeter_ore", () -> new BlockItem(BlockInit.GRIMSTONE_SALTPETER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> VOIDSTONE_SALTPETER_ORE = registerItem("voidstone_saltpeter_ore", () -> new BlockItem(BlockInit.VOIDSTONE_SALTPETER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SALTPETER_BLOCK = registerItem("saltpeter_block", () -> new BlockItem(BlockInit.SALTPETER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> SALTPETER_POWDER = registerItem("saltpeter_powder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> SALTPETER_MATCHBOX = registerItem("saltpeter_matchbox", () -> new FlintAndSteelItem(new Item.Properties().durability(300)));

    // boss materials
    public static final RegistryObject<Item, Item> SHATTERED_SWORD = registerItem("shattered_sword", () -> new ShatteredSwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> SHATTERED_SWORD_BLADE = registerItem("shattered_sword_blade", () -> new Item(new Item.Properties().stacksTo(1).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> GOLEM_STEEL_INGOT = registerItem("golem_steel_ingot", () -> new Item(new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_INGOT = registerItem("oxidized_golem_steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> ENERGY_SWORD = registerItem("energy_sword", () -> new EnergySwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> TENACIOUS_PETAL = registerItem("tenacious_petal", () -> new Item(new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> CRYSTAL_CROSSBOW = registerItem("crystal_crossbow", () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(2000).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> MOONRING_BOW = registerItem("moonring_bow", () -> new MoonringBowItem(new Item.Properties().stacksTo(1).durability(2000).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> MOONRING_GREATSWORD = registerItem("moonring_greatsword", () -> new MoonringGreatswordItem(ESItemTiers.PETAL, 6, -2.8F, new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> PETAL_SCYTHE = registerItem("petal_scythe", () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.PETAL, 3, -1.0F, new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> WAND_OF_TELEPORTATION = registerItem("wand_of_teleportation", () -> new SimpleSpellItem(SpellInit.TELEPORTATION, new Item.Properties().durability(1000).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> SEEKING_EYE = registerItem("seeking_eye", () -> new SeekingEyeItem(new Item.Properties()));

    // mob stuff
    public static final RegistryObject<Item, Item> LUMINOFISH_BUCKET = registerItem("luminofish_bucket", () -> ESPlatform.INSTANCE.createMobBucket(EntityInit.LUMINOFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> LUMINOFISH = registerItem("luminofish", () -> new Item(new Item.Properties().food(Foods.SALMON)));
    public static final RegistryObject<Item, Item> COOKED_LUMINOFISH = registerItem("cooked_luminofish", () -> new Item(new Item.Properties().food(Foods.COOKED_SALMON)));
    public static final RegistryObject<Item, Item> LUMINARIS_BUCKET = registerItem("luminaris_bucket", () -> ESPlatform.INSTANCE.createMobBucket(EntityInit.LUMINARIS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> LUMINARIS = registerItem("luminaris", () -> new Item(new Item.Properties().food(Foods.SALMON)));
    public static final RegistryObject<Item, Item> COOKED_LUMINARIS = registerItem("cooked_luminaris", () -> new Item(new Item.Properties().food(Foods.COOKED_SALMON)));

    public static final RegistryObject<Item, Item> WHITE_YETI_FUR = registerItem("white_yeti_fur", () -> new BlockItem(BlockInit.WHITE_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> ORANGE_YETI_FUR = registerItem("orange_yeti_fur", () -> new BlockItem(BlockInit.ORANGE_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> MAGENTA_YETI_FUR = registerItem("magenta_yeti_fur", () -> new BlockItem(BlockInit.MAGENTA_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LIGHT_BLUE_YETI_FUR = registerItem("light_blue_yeti_fur", () -> new BlockItem(BlockInit.LIGHT_BLUE_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> YELLOW_YETI_FUR = registerItem("yellow_yeti_fur", () -> new BlockItem(BlockInit.YELLOW_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LIME_YETI_FUR = registerItem("lime_yeti_fur", () -> new BlockItem(BlockInit.LIME_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PINK_YETI_FUR = registerItem("pink_yeti_fur", () -> new BlockItem(BlockInit.PINK_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRAY_YETI_FUR = registerItem("gray_yeti_fur", () -> new BlockItem(BlockInit.GRAY_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LIGHT_GRAY_YETI_FUR = registerItem("light_gray_yeti_fur", () -> new BlockItem(BlockInit.LIGHT_GRAY_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CYAN_YETI_FUR = registerItem("cyan_yeti_fur", () -> new BlockItem(BlockInit.CYAN_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PURPLE_YETI_FUR = registerItem("purple_yeti_fur", () -> new BlockItem(BlockInit.PURPLE_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_YETI_FUR = registerItem("blue_yeti_fur", () -> new BlockItem(BlockInit.BLUE_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BROWN_YETI_FUR = registerItem("brown_yeti_fur", () -> new BlockItem(BlockInit.BROWN_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GREEN_YETI_FUR = registerItem("green_yeti_fur", () -> new BlockItem(BlockInit.GREEN_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_YETI_FUR = registerItem("red_yeti_fur", () -> new BlockItem(BlockInit.RED_YETI_FUR.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLACK_YETI_FUR = registerItem("black_yeti_fur", () -> new BlockItem(BlockInit.BLACK_YETI_FUR.get(), new Item.Properties()));

    public static final RegistryObject<Item, Item> WHITE_YETI_FUR_CARPET = registerItem("white_yeti_fur_carpet", () -> new BlockItem(BlockInit.WHITE_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> ORANGE_YETI_FUR_CARPET = registerItem("orange_yeti_fur_carpet", () -> new BlockItem(BlockInit.ORANGE_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> MAGENTA_YETI_FUR_CARPET = registerItem("magenta_yeti_fur_carpet", () -> new BlockItem(BlockInit.MAGENTA_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LIGHT_BLUE_YETI_FUR_CARPET = registerItem("light_blue_yeti_fur_carpet", () -> new BlockItem(BlockInit.LIGHT_BLUE_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> YELLOW_YETI_FUR_CARPET = registerItem("yellow_yeti_fur_carpet", () -> new BlockItem(BlockInit.YELLOW_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LIME_YETI_FUR_CARPET = registerItem("lime_yeti_fur_carpet", () -> new BlockItem(BlockInit.LIME_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PINK_YETI_FUR_CARPET = registerItem("pink_yeti_fur_carpet", () -> new BlockItem(BlockInit.PINK_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GRAY_YETI_FUR_CARPET = registerItem("gray_yeti_fur_carpet", () -> new BlockItem(BlockInit.GRAY_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LIGHT_GRAY_YETI_FUR_CARPET = registerItem("light_gray_yeti_fur_carpet", () -> new BlockItem(BlockInit.LIGHT_GRAY_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CYAN_YETI_FUR_CARPET = registerItem("cyan_yeti_fur_carpet", () -> new BlockItem(BlockInit.CYAN_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> PURPLE_YETI_FUR_CARPET = registerItem("purple_yeti_fur_carpet", () -> new BlockItem(BlockInit.PURPLE_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_YETI_FUR_CARPET = registerItem("blue_yeti_fur_carpet", () -> new BlockItem(BlockInit.BLUE_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BROWN_YETI_FUR_CARPET = registerItem("brown_yeti_fur_carpet", () -> new BlockItem(BlockInit.BROWN_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> GREEN_YETI_FUR_CARPET = registerItem("green_yeti_fur_carpet", () -> new BlockItem(BlockInit.GREEN_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> RED_YETI_FUR_CARPET = registerItem("red_yeti_fur_carpet", () -> new BlockItem(BlockInit.RED_YETI_FUR_CARPET.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BLACK_YETI_FUR_CARPET = registerItem("black_yeti_fur_carpet", () -> new BlockItem(BlockInit.BLACK_YETI_FUR_CARPET.get(), new Item.Properties()));

    public static final RegistryObject<Item, Item> AURORA_DEER_ANTLER = registerItem("aurora_deer_antler",
            () -> ESPlatform.INSTANCE.createScythe(ESItemTiers.AURORA_DEER_ANTLER, 3, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item, Item> AURORA_DEER_STEAK = registerItem("aurora_deer_steak", () -> new Item(new Item.Properties().food(Foods.BEEF)));
    public static final RegistryObject<Item, Item> COOKED_AURORA_DEER_STEAK = registerItem("cooked_aurora_deer_steak", () -> new Item(new Item.Properties().food(Foods.COOKED_BEEF)));

    // magic
    public static final RegistryObject<Item, Item> ORB_OF_PROPHECY = registerItem("orb_of_prophecy", () -> new OrbOfProphecyItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> ENCHANTED_GRIMSTONE_BRICKS = registerItem("enchanted_grimstone_bricks", () -> new BlockItem(BlockInit.ENCHANTED_GRIMSTONE_BRICKS.get(), new Item.Properties().stacksTo(1).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> TERRA_CRYSTAL = registerItem("terra_crystal", () -> new Item(new Item.Properties().durability(300).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> WIND_CRYSTAL = registerItem("wind_crystal", () -> new Item(new Item.Properties().durability(300).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> WATER_CRYSTAL = registerItem("water_crystal", () -> new Item(new Item.Properties().durability(300).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> LUNAR_CRYSTAL = registerItem("lunar_crystal", () -> new Item(new Item.Properties().durability(300).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> BLAZE_CRYSTAL = registerItem("blaze_crystal", () -> new Item(new Item.Properties().durability(300).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> LIGHT_CRYSTAL = registerItem("light_crystal", () -> new Item(new Item.Properties().durability(300).rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> MANA_CRYSTAL_SHARD = registerItem("mana_crystal_shard", () -> new Item(new Item.Properties().rarity(STARLIGHT)));

    // misc
    public static final RegistryObject<Item, Item> ETHER_BUCKET = registerItem("ether_bucket", () -> ESPlatform.INSTANCE.createBucket(FluidInit.ETHER_STILL, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> ENERGY_BLOCK = registerItem("energy_block", () -> new BlockItem(BlockInit.ENERGY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_GOLEM_SPAWNER = registerItem("starlight_golem_spawner", () -> new BlockItem(BlockInit.STARLIGHT_GOLEM_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MONSTROSITY_SPAWNER = registerItem("lunar_monstrosity_spawner", () -> new BlockItem(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> STARLIGHT_SILVER_COIN = registerItem("starlight_silver_coin", () -> new Item(new Item.Properties().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> LOOT_BAG = registerItem("loot_bag", () -> new LootBagItem(new Item.Properties().fireResistant().rarity(STARLIGHT)));
    public static final RegistryObject<Item, Item> BOOK = registerItem("book", () -> new ESBookItem(new Item.Properties().rarity(STARLIGHT)));
    public static void loadClass() {}
}
