package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.*;
import cn.leolezury.eternalstarlight.common.data.ConfiguredFeatureInit;
import cn.leolezury.eternalstarlight.common.data.PlacedFeatureInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class BlockInit {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, EternalStarlight.MOD_ID);

    public static final RegistryObject<Block, Block> BERRIES_VINES = BLOCKS.register("berries_vines", () -> new BerriesVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, Block> BERRIES_VINES_PLANT = BLOCKS.register("berries_vines_plant", () -> new BerriesVinePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, Block> ABYSSAL_KELP = BLOCKS.register("abyssal_kelp", () -> new AbyssalKelpBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP).lightLevel(CaveVines.emission(14))));
    public static final RegistryObject<Block, Block> ABYSSAL_KELP_PLANT = BLOCKS.register("abyssal_kelp_plant", () -> new AbyssalKelpPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT).lightLevel(CaveVines.emission(14))));
    public static final RegistryObject<Block, Block> RED_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("red_starlight_crystal_cluster", () -> new StarlightCrystalClusterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> 15).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block, Block> BLUE_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("blue_starlight_crystal_cluster", () -> new StarlightCrystalClusterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> 15).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block, Block> RED_STARLIGHT_CRYSTAL_BLOCK = BLOCKS.register("red_starlight_crystal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().lightLevel((state) -> 15).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block, Block> BLUE_STARLIGHT_CRYSTAL_BLOCK = BLOCKS.register("blue_starlight_crystal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().lightLevel((state) -> 15).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block, Block> RED_CRYSTAL_MOSS_CARPET = BLOCKS.register("red_crystal_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.MOSS_CARPET).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> BLUE_CRYSTAL_MOSS_CARPET = BLOCKS.register("blue_crystal_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.1F).sound(SoundType.MOSS_CARPET).lightLevel((state) -> 15)));

    // coral
    public static final RegistryObject<Block, Block> DEAD_TENTACLES_CORAL = BLOCKS.register("dead_tentacles_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL)));
    public static final RegistryObject<Block, Block> TENTACLES_CORAL = BLOCKS.register("tentacles_coral", () -> new CoralPlantBlock(DEAD_TENTACLES_CORAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> DEAD_TENTACLES_CORAL_FAN = BLOCKS.register("dead_tentacles_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN)));
    public static final RegistryObject<Block, Block> TENTACLES_CORAL_FAN = BLOCKS.register("tentacles_coral_fan", () -> new CoralFanBlock(DEAD_TENTACLES_CORAL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> DEAD_TENTACLES_CORAL_WALL_FAN = BLOCKS.register("dead_tentacles_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN)));
    public static final RegistryObject<Block, Block> TENTACLES_CORAL_WALL_FAN = BLOCKS.register("tentacles_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_TENTACLES_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> DEAD_TENTACLES_CORAL_BLOCK = BLOCKS.register("dead_tentacles_coral_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK)));
    public static final RegistryObject<Block, Block> TENTACLES_CORAL_BLOCK = BLOCKS.register("tentacles_coral_block", () -> new CoralBlock(DEAD_TENTACLES_CORAL_BLOCK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> DEAD_GOLDEN_CORAL = BLOCKS.register("dead_golden_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL)));
    public static final RegistryObject<Block, Block> GOLDEN_CORAL = BLOCKS.register("golden_coral", () -> new CoralPlantBlock(DEAD_GOLDEN_CORAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block, Block> DEAD_GOLDEN_CORAL_FAN = BLOCKS.register("dead_golden_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN)));
    public static final RegistryObject<Block, Block> GOLDEN_CORAL_FAN = BLOCKS.register("golden_coral_fan", () -> new CoralFanBlock(DEAD_GOLDEN_CORAL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block, Block> DEAD_GOLDEN_CORAL_WALL_FAN = BLOCKS.register("dead_golden_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN)));
    public static final RegistryObject<Block, Block> GOLDEN_CORAL_WALL_FAN = BLOCKS.register("golden_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_GOLDEN_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block, Block> DEAD_GOLDEN_CORAL_BLOCK = BLOCKS.register("dead_golden_coral_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK)));
    public static final RegistryObject<Block, Block> GOLDEN_CORAL_BLOCK = BLOCKS.register("golden_coral_block", () -> new CoralBlock(DEAD_GOLDEN_CORAL_BLOCK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block, Block> DEAD_CRYSTALLUM_CORAL = BLOCKS.register("dead_crystallum_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL)));
    public static final RegistryObject<Block, Block> CRYSTALLUM_CORAL = BLOCKS.register("crystallum_coral", () -> new CoralPlantBlock(DEAD_CRYSTALLUM_CORAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block, Block> DEAD_CRYSTALLUM_CORAL_FAN = BLOCKS.register("dead_crystallum_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN)));
    public static final RegistryObject<Block, Block> CRYSTALLUM_CORAL_FAN = BLOCKS.register("crystallum_coral_fan", () -> new CoralFanBlock(DEAD_CRYSTALLUM_CORAL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block, Block> DEAD_CRYSTALLUM_CORAL_WALL_FAN = BLOCKS.register("dead_crystallum_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN)));
    public static final RegistryObject<Block, Block> CRYSTALLUM_CORAL_WALL_FAN = BLOCKS.register("crystallum_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block, Block> DEAD_CRYSTALLUM_CORAL_BLOCK = BLOCKS.register("dead_crystallum_coral_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK)));
    public static final RegistryObject<Block, Block> CRYSTALLUM_CORAL_BLOCK = BLOCKS.register("crystallum_coral_block", () -> new CoralBlock(DEAD_CRYSTALLUM_CORAL_BLOCK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_CYAN)));

    // lunar wood
    public static final RegistryObject<Block, Block> LUNAR_LEAVES = BLOCKS.register("lunar_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, RotatedPillarBlock> LUNAR_LOG = BLOCKS.register("lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_WOOD = BLOCKS.register("lunar_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_PLANKS = BLOCKS.register("lunar_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_LUNAR_LOG = BLOCKS.register("stripped_lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> STRIPPED_LUNAR_WOOD = BLOCKS.register("stripped_lunar_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, DoorBlock> LUNAR_DOOR = BLOCKS.register("lunar_door",
            () -> new DoorBlock(ESWoodTypes.LUNAR_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, TrapDoorBlock> LUNAR_TRAPDOOR = BLOCKS.register("lunar_trapdoor",
            () -> new TrapDoorBlock(ESWoodTypes.LUNAR_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, PressurePlateBlock> LUNAR_PRESSURE_PLATE = BLOCKS.register("lunar_pressure_plate",
            () -> new PressurePlateBlock(ESWoodTypes.LUNAR_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, ButtonBlock> LUNAR_BUTTON = BLOCKS.register("lunar_button",
            () -> new ButtonBlock(ESWoodTypes.LUNAR_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, FenceBlock> LUNAR_FENCE = BLOCKS.register("lunar_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, FenceGateBlock> LUNAR_FENCE_GATE = BLOCKS.register("lunar_fence_gate",
            () -> new FenceGateBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, SlabBlock> LUNAR_SLAB = BLOCKS.register("lunar_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, StairBlock> LUNAR_STAIRS = BLOCKS.register("lunar_stairs",
            () -> new StairBlock(LUNAR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_SIGN = BLOCKS.register("lunar_sign",
            () -> new ESStandingSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_WALL_SIGN = BLOCKS.register("lunar_wall_sign",
            () -> new ESWallSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_HANGING_SIGN = BLOCKS.register("lunar_hanging_sign",
            () -> new ESCeilingHangingSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_WALL_HANGING_SIGN = BLOCKS.register("lunar_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_SAPLING = BLOCKS.register("lunar_sapling", () -> new SaplingBlock(new TreeGrower("lunar", 0.2f, Optional.empty(), Optional.empty(), Optional.of(ConfiguredFeatureInit.LUNAR), Optional.of(ConfiguredFeatureInit.LUNAR_HUGE), Optional.empty(), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, Block> POTTED_LUNAR_SAPLING = BLOCKS.register("potted_lunar_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUNAR_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    // lunar extras: desert
    public static final RegistryObject<Block, RotatedPillarBlock> DEAD_LUNAR_LOG = BLOCKS.register("dead_lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, RotatedPillarBlock> RED_CRYSTALLIZED_LUNAR_LOG = BLOCKS.register("red_crystallized_lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, RotatedPillarBlock> BLUE_CRYSTALLIZED_LUNAR_LOG = BLOCKS.register("blue_crystallized_lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK)));


    // northland wood
    public static final RegistryObject<Block, Block> NORTHLAND_LEAVES = BLOCKS.register("northland_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, RotatedPillarBlock> NORTHLAND_LOG = BLOCKS.register("northland_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_WOOD = BLOCKS.register("northland_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_PLANKS = BLOCKS.register("northland_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_NORTHLAND_LOG = BLOCKS.register("stripped_northland_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> STRIPPED_NORTHLAND_WOOD = BLOCKS.register("stripped_northland_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, DoorBlock> NORTHLAND_DOOR = BLOCKS.register("northland_door",
            () -> new DoorBlock(ESWoodTypes.NORTHLAND_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, TrapDoorBlock> NORTHLAND_TRAPDOOR = BLOCKS.register("northland_trapdoor",
            () -> new TrapDoorBlock(ESWoodTypes.NORTHLAND_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, PressurePlateBlock> NORTHLAND_PRESSURE_PLATE = BLOCKS.register("northland_pressure_plate",
            () -> new PressurePlateBlock(ESWoodTypes.NORTHLAND_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, ButtonBlock> NORTHLAND_BUTTON = BLOCKS.register("northland_button",
            () -> new ButtonBlock(ESWoodTypes.NORTHLAND_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, FenceBlock> NORTHLAND_FENCE = BLOCKS.register("northland_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, FenceGateBlock> NORTHLAND_FENCE_GATE = BLOCKS.register("northland_fence_gate",
            () -> new FenceGateBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, SlabBlock> NORTHLAND_SLAB = BLOCKS.register("northland_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, StairBlock> NORTHLAND_STAIRS = BLOCKS.register("northland_stairs",
            () -> new StairBlock(NORTHLAND_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_SIGN = BLOCKS.register("northland_sign",
            () -> new ESStandingSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_WALL_SIGN = BLOCKS.register("northland_wall_sign",
            () -> new ESWallSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_HANGING_SIGN = BLOCKS.register("northland_hanging_sign",
            () -> new ESCeilingHangingSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_WALL_HANGING_SIGN = BLOCKS.register("northland_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NORTHLAND_SAPLING = BLOCKS.register("northland_sapling", () -> new SaplingBlock(new TreeGrower("northland", Optional.of(ConfiguredFeatureInit.NORTHLAND), Optional.empty(), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, Block> POTTED_NORTHLAND_SAPLING = BLOCKS.register("potted_northland_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NORTHLAND_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    // starlight mangrove wood
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_LEAVES = BLOCKS.register("starlight_mangrove_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block, RotatedPillarBlock> STARLIGHT_MANGROVE_LOG = BLOCKS.register("starlight_mangrove_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_WOOD = BLOCKS.register("starlight_mangrove_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_PLANKS = BLOCKS.register("starlight_mangrove_planks",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_STARLIGHT_MANGROVE_LOG = BLOCKS.register("stripped_starlight_mangrove_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STRIPPED_STARLIGHT_MANGROVE_WOOD = BLOCKS.register("stripped_starlight_mangrove_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, DoorBlock> STARLIGHT_MANGROVE_DOOR = BLOCKS.register("starlight_mangrove_door",
            () -> new DoorBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, TrapDoorBlock> STARLIGHT_MANGROVE_TRAPDOOR = BLOCKS.register("starlight_mangrove_trapdoor",
            () -> new TrapDoorBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, PressurePlateBlock> STARLIGHT_MANGROVE_PRESSURE_PLATE = BLOCKS.register("starlight_mangrove_pressure_plate",
            () -> new PressurePlateBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, ButtonBlock> STARLIGHT_MANGROVE_BUTTON = BLOCKS.register("starlight_mangrove_button",
            () -> new ButtonBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, FenceBlock> STARLIGHT_MANGROVE_FENCE = BLOCKS.register("starlight_mangrove_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, FenceGateBlock> STARLIGHT_MANGROVE_FENCE_GATE = BLOCKS.register("starlight_mangrove_fence_gate",
            () -> new FenceGateBlock(ESWoodTypes.STARLIGHT_MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, SlabBlock> STARLIGHT_MANGROVE_SLAB = BLOCKS.register("starlight_mangrove_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, StairBlock> STARLIGHT_MANGROVE_STAIRS = BLOCKS.register("starlight_mangrove_stairs",
            () -> new StairBlock(STARLIGHT_MANGROVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_SIGN = BLOCKS.register("starlight_mangrove_sign",
            () -> new ESStandingSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_WALL_SIGN = BLOCKS.register("starlight_mangrove_wall_sign",
            () -> new ESWallSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_HANGING_SIGN = BLOCKS.register("starlight_mangrove_hanging_sign",
            () -> new ESCeilingHangingSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_WALL_HANGING_SIGN = BLOCKS.register("starlight_mangrove_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_SAPLING = BLOCKS.register("starlight_mangrove_sapling", () -> new SaplingBlock(new TreeGrower("starlight_mangrove", Optional.empty(), Optional.of(ConfiguredFeatureInit.STARLIGHT_MANGROVE), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> POTTED_STARLIGHT_MANGROVE_SAPLING = BLOCKS.register("potted_starlight_mangrove_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_MANGROVE_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_ROOTS = BLOCKS.register("starlight_mangrove_roots", () -> new MangroveRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_ROOTS)));
    public static final RegistryObject<Block, Block> MUDDY_STARLIGHT_MANGROVE_ROOTS = BLOCKS.register("muddy_starlight_mangrove_roots", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUDDY_MANGROVE_ROOTS)));

    // scarlet wood
    public static final RegistryObject<Block, Block> SCARLET_LEAVES = BLOCKS.register("scarlet_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, RotatedPillarBlock> SCARLET_LOG = BLOCKS.register("scarlet_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_WOOD = BLOCKS.register("scarlet_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_PLANKS = BLOCKS.register("scarlet_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_SCARLET_LOG = BLOCKS.register("stripped_scarlet_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> STRIPPED_SCARLET_WOOD = BLOCKS.register("stripped_scarlet_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, DoorBlock> SCARLET_DOOR = BLOCKS.register("scarlet_door",
            () -> new DoorBlock(ESWoodTypes.SCARLET_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, TrapDoorBlock> SCARLET_TRAPDOOR = BLOCKS.register("scarlet_trapdoor",
            () -> new TrapDoorBlock(ESWoodTypes.SCARLET_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, PressurePlateBlock> SCARLET_PRESSURE_PLATE = BLOCKS.register("scarlet_pressure_plate",
            () -> new PressurePlateBlock(ESWoodTypes.SCARLET_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, ButtonBlock> SCARLET_BUTTON = BLOCKS.register("scarlet_button",
            () -> new ButtonBlock(ESWoodTypes.SCARLET_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, FenceBlock> SCARLET_FENCE = BLOCKS.register("scarlet_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, FenceGateBlock> SCARLET_FENCE_GATE = BLOCKS.register("scarlet_fence_gate",
            () -> new FenceGateBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, SlabBlock> SCARLET_SLAB = BLOCKS.register("scarlet_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, StairBlock> SCARLET_STAIRS = BLOCKS.register("scarlet_stairs",
            () -> new StairBlock(SCARLET_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_SIGN = BLOCKS.register("scarlet_sign",
            () -> new ESStandingSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_WALL_SIGN = BLOCKS.register("scarlet_wall_sign",
            () -> new ESWallSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_HANGING_SIGN = BLOCKS.register("scarlet_hanging_sign",
            () -> new ESCeilingHangingSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_WALL_HANGING_SIGN = BLOCKS.register("scarlet_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> SCARLET_SAPLING = BLOCKS.register("scarlet_sapling", () -> new SaplingBlock(new TreeGrower("scarlet", Optional.empty(), Optional.of(ConfiguredFeatureInit.SCARLET), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> POTTED_SCARLET_SAPLING = BLOCKS.register("potted_scarlet_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SCARLET_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_RED)));

    // grimstone
    public static final RegistryObject<Block, Block> GRIMSTONE = BLOCKS.register("grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> COBBLED_GRIMSTONE = BLOCKS.register("cobbled_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block, SlabBlock> COBBLED_GRIMSTONE_SLAB = BLOCKS.register("cobbled_grimstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> COBBLED_GRIMSTONE_STAIRS = BLOCKS.register("cobbled_grimstone_stairs", () -> new StairBlock(COBBLED_GRIMSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> COBBLED_GRIMSTONE_WALL = BLOCKS.register("cobbled_grimstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> GRIMSTONE_BRICKS = BLOCKS.register("grimstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> GRIMSTONE_BRICK_SLAB = BLOCKS.register("grimstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> GRIMSTONE_BRICK_STAIRS = BLOCKS.register("grimstone_brick_stairs", () -> new StairBlock(GRIMSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> GRIMSTONE_BRICK_WALL = BLOCKS.register("grimstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> POLISHED_GRIMSTONE = BLOCKS.register("polished_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_GRIMSTONE_SLAB = BLOCKS.register("polished_grimstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> POLISHED_GRIMSTONE_STAIRS = BLOCKS.register("polished_grimstone_stairs", () -> new StairBlock(POLISHED_GRIMSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> POLISHED_GRIMSTONE_WALL = BLOCKS.register("polished_grimstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> GRIMSTONE_TILES = BLOCKS.register("grimstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> GRIMSTONE_TILE_SLAB = BLOCKS.register("grimstone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> GRIMSTONE_TILE_STAIRS = BLOCKS.register("grimstone_tile_stairs", () -> new StairBlock(GRIMSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> GRIMSTONE_TILE_WALL = BLOCKS.register("grimstone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> CHISELED_GRIMSTONE = BLOCKS.register("chiseled_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, Block> GLOWING_GRIMSTONE = BLOCKS.register("glowing_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).lightLevel((state) -> 10)));

    // voidstone
    public static final RegistryObject<Block, Block> VOIDSTONE = BLOCKS.register("voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> COBBLED_VOIDSTONE = BLOCKS.register("cobbled_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block, SlabBlock> COBBLED_VOIDSTONE_SLAB = BLOCKS.register("cobbled_voidstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> COBBLED_VOIDSTONE_STAIRS = BLOCKS.register("cobbled_voidstone_stairs", () -> new StairBlock(COBBLED_VOIDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> COBBLED_VOIDSTONE_WALL = BLOCKS.register("cobbled_voidstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> VOIDSTONE_BRICKS = BLOCKS.register("voidstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> VOIDSTONE_BRICK_SLAB = BLOCKS.register("voidstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> VOIDSTONE_BRICK_STAIRS = BLOCKS.register("voidstone_brick_stairs", () -> new StairBlock(VOIDSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> VOIDSTONE_BRICK_WALL = BLOCKS.register("voidstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> POLISHED_VOIDSTONE = BLOCKS.register("polished_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_VOIDSTONE_SLAB = BLOCKS.register("polished_voidstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> POLISHED_VOIDSTONE_STAIRS = BLOCKS.register("polished_voidstone_stairs", () -> new StairBlock(POLISHED_VOIDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> POLISHED_VOIDSTONE_WALL = BLOCKS.register("polished_voidstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> VOIDSTONE_TILES = BLOCKS.register("voidstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> VOIDSTONE_TILE_SLAB = BLOCKS.register("voidstone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> VOIDSTONE_TILE_STAIRS = BLOCKS.register("voidstone_tile_stairs", () -> new StairBlock(VOIDSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> VOIDSTONE_TILE_WALL = BLOCKS.register("voidstone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> CHISELED_VOIDSTONE = BLOCKS.register("chiseled_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, Block> GLOWING_VOIDSTONE = BLOCKS.register("glowing_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).lightLevel((state) -> 10)));

    // the abyss
    public static final RegistryObject<Block, Block> ABYSSLATE = BLOCKS.register("abysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));
    public static final RegistryObject<Block, Block> POLISHED_ABYSSLATE = BLOCKS.register("polished_abysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_ABYSSLATE_SLAB = BLOCKS.register("polished_abysslate_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB)));
    public static final RegistryObject<Block, StairBlock> POLISHED_ABYSSLATE_STAIRS = BLOCKS.register("polished_abysslate_stairs", () -> new StairBlock(POLISHED_ABYSSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS)));
    public static final RegistryObject<Block, WallBlock> POLISHED_ABYSSLATE_WALL = BLOCKS.register("polished_abysslate_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL)));
    public static final RegistryObject<Block, Block> POLISHED_ABYSSLATE_BRICKS = BLOCKS.register("polished_abysslate_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_ABYSSLATE_BRICK_SLAB = BLOCKS.register("polished_abysslate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> POLISHED_ABYSSLATE_BRICK_STAIRS = BLOCKS.register("polished_abysslate_brick_stairs", () -> new StairBlock(POLISHED_ABYSSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> POLISHED_ABYSSLATE_BRICK_WALL = BLOCKS.register("polished_abysslate_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> CHISELED_POLISHED_ABYSSLATE = BLOCKS.register("chiseled_polished_abysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE)));
    public static final RegistryObject<Block, Block> ABYSSAL_MAGMA_BLOCK = BLOCKS.register("abyssal_magma_block", () -> new MagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block, Block> ABYSSAL_GEYSER = BLOCKS.register("abyssal_geyser", () -> new AbyssalGeyserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    public static final RegistryObject<Block, Block> THERMABYSSLATE = BLOCKS.register("thermabysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, Block> POLISHED_THERMABYSSLATE = BLOCKS.register("polished_thermabysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_THERMABYSSLATE_SLAB = BLOCKS.register("polished_thermabysslate_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, StairBlock> POLISHED_THERMABYSSLATE_STAIRS = BLOCKS.register("polished_thermabysslate_stairs", () -> new StairBlock(POLISHED_THERMABYSSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, WallBlock> POLISHED_THERMABYSSLATE_WALL = BLOCKS.register("polished_thermabysslate_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, Block> POLISHED_THERMABYSSLATE_BRICKS = BLOCKS.register("polished_thermabysslate_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_THERMABYSSLATE_BRICK_SLAB = BLOCKS.register("polished_thermabysslate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, StairBlock> POLISHED_THERMABYSSLATE_BRICK_STAIRS = BLOCKS.register("polished_thermabysslate_brick_stairs", () -> new StairBlock(POLISHED_THERMABYSSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, WallBlock> POLISHED_THERMABYSSLATE_BRICK_WALL = BLOCKS.register("polished_thermabysslate_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.NETHER)));
    public static final RegistryObject<Block, Block> CHISELED_POLISHED_THERMABYSSLATE = BLOCKS.register("chiseled_polished_thermabysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE)));
    public static final RegistryObject<Block, Block> THERMABYSSAL_MAGMA_BLOCK = BLOCKS.register("thermabyssal_magma_block", () -> new MagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK)));
    public static final RegistryObject<Block, Block> THERMABYSSAL_GEYSER = BLOCKS.register("thermabyssal_geyser", () -> new AbyssalGeyserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block, Block> CRYOBYSSLATE = BLOCKS.register("cryobysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));
    public static final RegistryObject<Block, Block> POLISHED_CRYOBYSSLATE = BLOCKS.register("polished_cryobysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_CRYOBYSSLATE_SLAB = BLOCKS.register("polished_cryobysslate_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB)));
    public static final RegistryObject<Block, StairBlock> POLISHED_CRYOBYSSLATE_STAIRS = BLOCKS.register("polished_cryobysslate_stairs", () -> new StairBlock(POLISHED_CRYOBYSSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS)));
    public static final RegistryObject<Block, WallBlock> POLISHED_CRYOBYSSLATE_WALL = BLOCKS.register("polished_cryobysslate_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL)));
    public static final RegistryObject<Block, Block> POLISHED_CRYOBYSSLATE_BRICKS = BLOCKS.register("polished_cryobysslate_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_CRYOBYSSLATE_BRICK_SLAB = BLOCKS.register("polished_cryobysslate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> POLISHED_CRYOBYSSLATE_BRICK_STAIRS = BLOCKS.register("polished_cryobysslate_brick_stairs", () -> new StairBlock(POLISHED_CRYOBYSSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> POLISHED_CRYOBYSSLATE_BRICK_WALL = BLOCKS.register("polished_cryobysslate_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL)));
    public static final RegistryObject<Block, Block> CHISELED_POLISHED_CRYOBYSSLATE = BLOCKS.register("chiseled_polished_cryobysslate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE)));
    public static final RegistryObject<Block, Block> CRYOBYSSAL_MAGMA_BLOCK = BLOCKS.register("cryobyssal_magma_block", () -> new MagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block, Block> CRYOBYSSAL_GEYSER = BLOCKS.register("cryobyssal_geyser", () -> new AbyssalGeyserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    // mud
    public static final RegistryObject<Block, Block> NIGHTSHADE_MUD = BLOCKS.register("nightshade_mud", () -> new MudBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD)));
    public static final RegistryObject<Block, Block> GLOWING_NIGHTSHADE_MUD = BLOCKS.register("glowing_nightshade_mud", () -> new MudBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> PACKED_NIGHTSHADE_MUD = BLOCKS.register("packed_nightshade_mud", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD)));
    public static final RegistryObject<Block, Block> NIGHTSHADE_MUD_BRICKS = BLOCKS.register("nightshade_mud_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> NIGHTSHADE_MUD_BRICK_SLAB = BLOCKS.register("nightshade_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> NIGHTSHADE_MUD_BRICK_STAIRS = BLOCKS.register("nightshade_mud_brick_stairs", () -> new StairBlock(NIGHTSHADE_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_STAIRS)));
    public static final RegistryObject<Block, WallBlock> NIGHTSHADE_MUD_BRICK_WALL = BLOCKS.register("nightshade_mud_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_WALL)));

    // sand
    public static final RegistryObject<Block, Block> TWILIGHT_SAND = BLOCKS.register("twilight_sand", () -> new ColoredFallingBlock(new ColorRGBA(0x907e9b), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> TWILIGHT_SANDSTONE = BLOCKS.register("twilight_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, SlabBlock> TWILIGHT_SANDSTONE_SLAB = BLOCKS.register("twilight_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, StairBlock> TWILIGHT_SANDSTONE_STAIRS = BLOCKS.register("twilight_sandstone_stairs", () -> new StairBlock(TWILIGHT_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, WallBlock> TWILIGHT_SANDSTONE_WALL = BLOCKS.register("twilight_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> CUT_TWILIGHT_SANDSTONE = BLOCKS.register("cut_twilight_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, SlabBlock> CUT_TWILIGHT_SANDSTONE_SLAB = BLOCKS.register("cut_twilight_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, StairBlock> CUT_TWILIGHT_SANDSTONE_STAIRS = BLOCKS.register("cut_twilight_sandstone_stairs", () -> new StairBlock(CUT_TWILIGHT_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, WallBlock> CUT_TWILIGHT_SANDSTONE_WALL = BLOCKS.register("cut_twilight_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> CHISELED_TWILIGHT_SANDSTONE = BLOCKS.register("chiseled_twilight_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));

    // golem steel
    public static final RegistryObject<Block, Block> GOLEM_STEEL_BLOCK = BLOCKS.register("golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, Block> OXIDIZED_GOLEM_STEEL_BLOCK = BLOCKS.register("oxidized_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> GOLEM_STEEL_SLAB = BLOCKS.register("golem_steel_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, SlabBlock> OXIDIZED_GOLEM_STEEL_SLAB = BLOCKS.register("oxidized_golem_steel_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> GOLEM_STEEL_STAIRS = BLOCKS.register("golem_steel_stairs", () -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, StairBlock> OXIDIZED_GOLEM_STEEL_STAIRS = BLOCKS.register("oxidized_golem_steel_stairs", () -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, Block> GOLEM_STEEL_TILES = BLOCKS.register("golem_steel_tiles", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, Block> OXIDIZED_GOLEM_STEEL_TILES = BLOCKS.register("oxidized_golem_steel_tiles", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, SlabBlock> GOLEM_STEEL_TILE_SLAB = BLOCKS.register("golem_steel_tile_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, SlabBlock> OXIDIZED_GOLEM_STEEL_TILE_SLAB = BLOCKS.register("oxidized_golem_steel_tile_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block, StairBlock> GOLEM_STEEL_TILE_STAIRS = BLOCKS.register("golem_steel_tile_stairs", () -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, StairBlock> OXIDIZED_GOLEM_STEEL_TILE_STAIRS = BLOCKS.register("oxidized_golem_steel_tile_stairs", () -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block, Block> CHISELED_GOLEM_STEEL_BLOCK = BLOCKS.register("chiseled_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block, Block> OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK = BLOCKS.register("oxidized_chiseled_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));

    // doomeden
    public static final RegistryObject<Block, Block> DOOMED_TORCH = BLOCKS.register("doomed_torch", () -> new TorchBlock(ParticleTypes.FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
    public static final RegistryObject<Block, Block> WALL_DOOMED_TORCH = BLOCKS.register("wall_doomed_torch", () -> new WallTorchBlock(ParticleTypes.FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)));
    public static final RegistryObject<Block, Block> DOOMED_REDSTONE_TORCH = BLOCKS.register("doomed_redstone_torch", () -> new DoomedenRedstoneTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH)));
    public static final RegistryObject<Block, Block> WALL_DOOMED_REDSTONE_TORCH = BLOCKS.register("wall_doomed_redstone_torch", () -> new DoomedenRedstoneWallTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_WALL_TORCH)));
    public static final RegistryObject<Block, Block> DOOMEDEN_BRICKS = BLOCKS.register("doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, SlabBlock> DOOMEDEN_BRICK_SLAB = BLOCKS.register("doomeden_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, StairBlock> DOOMEDEN_BRICK_STAIRS = BLOCKS.register("doomeden_brick_stairs", () -> new StairBlock(DOOMEDEN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, WallBlock> DOOMEDEN_BRICK_WALL = BLOCKS.register("doomeden_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> POLISHED_DOOMEDEN_BRICKS = BLOCKS.register("polished_doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, SlabBlock> POLISHED_DOOMEDEN_BRICK_SLAB = BLOCKS.register("polished_doomeden_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, StairBlock> POLISHED_DOOMEDEN_BRICK_STAIRS = BLOCKS.register("polished_doomeden_brick_stairs", () -> new StairBlock(POLISHED_DOOMEDEN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, WallBlock> POLISHED_DOOMEDEN_BRICK_WALL = BLOCKS.register("polished_doomeden_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> DOOMEDEN_TILE = BLOCKS.register("doomeden_tile", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> CHISELED_POLISHED_DOOMEDEN_BRICKS = BLOCKS.register("chiseled_polished_doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS = BLOCKS.register("charged_chiseled_polished_doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> DOOMEDEN_LIGHT = BLOCKS.register("doomeden_light", () -> new RedstoneLampBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> DOOMEDEN_KEYHOLE = BLOCKS.register("doomeden_keyhole", () -> new DoomedenKeyholeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> REDSTONE_DOOMEDEN_KEYHOLE = BLOCKS.register("redstone_doomeden_keyhole", () -> new DoomedenKeyholeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN)));

    // common grass
    public static final RegistryObject<Block, Block> STARLIGHT_FLOWER = BLOCKS.register("starlight_flower", () -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> POTTED_STARLIGHT_FLOWER = BLOCKS.register("potted_starlight_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_FLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> CONEBLOOM = BLOCKS.register("conebloom", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> POTTED_CONEBLOOM = BLOCKS.register("potted_conebloom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CONEBLOOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> NIGHTFAN = BLOCKS.register("nightfan", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> POTTED_NIGHTFAN = BLOCKS.register("potted_nightfan", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NIGHTFAN, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> PINK_ROSE = BLOCKS.register("pink_rose", () -> new FlowerBlock(MobEffects.DIG_SPEED, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block, Block> POTTED_PINK_ROSE = BLOCKS.register("potted_pink_rose", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_ROSE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block, Block> STARLIGHT_TORCHFLOWER = BLOCKS.register("starlight_torchflower", () -> new FlowerBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_YELLOW).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> POTTED_STARLIGHT_TORCHFLOWER = BLOCKS.register("potted_starlight_torchflower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_TORCHFLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_YELLOW).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> NIGHTFAN_BUSH = BLOCKS.register("nightfan_bush", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> PINK_ROSE_BUSH = BLOCKS.register("pink_rose_bush", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block, Block> NIGHT_SPROUTS = BLOCKS.register("night_sprouts", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> SMALL_NIGHT_SPROUTS = BLOCKS.register("small_night_sprouts", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> GLOWING_NIGHT_SPROUTS = BLOCKS.register("glowing_night_sprouts", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> SMALL_GLOWING_NIGHT_SPROUTS = BLOCKS.register("small_glowing_night_sprouts", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> LUNAR_GRASS = BLOCKS.register("lunar_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> GLOWING_LUNAR_GRASS = BLOCKS.register("glowing_lunar_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> CRESCENT_GRASS = BLOCKS.register("crescent_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block, Block> GLOWING_CRESCENT_GRASS = BLOCKS.register("glowing_crescent_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> PARASOL_GRASS = BLOCKS.register("parasol_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block, Block> GLOWING_PARASOL_GRASS = BLOCKS.register("glowing_parasol_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> LUNAR_BUSH = BLOCKS.register("lunar_bush", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block, Block> GLOWING_LUNAR_BUSH = BLOCKS.register("glowing_lunar_bush", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> TALL_CRESCENT_GRASS = BLOCKS.register("tall_crescent_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block, Block> TALL_GLOWING_CRESCENT_GRASS = BLOCKS.register("tall_glowing_crescent_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> LUNAR_REED = BLOCKS.register("lunar_reed", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> WHISPERBLOOM = BLOCKS.register("whisperbloom", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block, Block> GLADESPIKE = BLOCKS.register("gladespike", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block, Block> VIVIDSTALK = BLOCKS.register("vividstalk", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block, Block> TALL_GLADESPIKE = BLOCKS.register("tall_gladespike", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block, Block> GLOWING_MUSHROOM = BLOCKS.register("glowing_mushroom", () -> new MushroomBlock(ConfiguredFeatureInit.HUGE_GLOWING_MUSHROOM, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(BlockInit::always).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> GLOWING_MUSHROOM_BLOCK = BLOCKS.register("glowing_mushroom_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 15)));

    // swamp grass
    public static final RegistryObject<Block, Block> SWAMP_ROSE = BLOCKS.register("swamp_rose", () -> new FlowerBlock(MobEffects.POISON, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> POTTED_SWAMP_ROSE = BLOCKS.register("potted_swamp_rose", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SWAMP_ROSE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> FANTABUD = BLOCKS.register("fantabud", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> GREEN_FANTABUD = BLOCKS.register("green_fantabud", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_GREEN)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> FANTAFERN = BLOCKS.register("fantafern", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> GREEN_FANTAFERN = BLOCKS.register("green_fantafern", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block, Block> FANTAGRASS = BLOCKS.register("fantagrass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel((state) -> 15)));
    public static final RegistryObject<Block, Block> GREEN_FANTAGRASS = BLOCKS.register("green_fantagrass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)));

    // scarlet forest grass
    public static final RegistryObject<Block, Block> ORANGE_SCARLET_BUD = BLOCKS.register("orange_scarlet_bud", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> PURPLE_SCARLET_BUD = BLOCKS.register("purple_scarlet_bud", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> RED_SCARLET_BUD = BLOCKS.register("red_scarlet_bud", () -> new ESBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_RED)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block, Block> SCARLET_GRASS = BLOCKS.register("scarlet_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_RED)));

    // desert grass
    public static final RegistryObject<Block, Block> DEAD_LUNAR_BUSH = BLOCKS.register("dead_lunar_bush", () -> new DeadBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)));

    public static final RegistryObject<Block, Block> FANTASY_GRASS_BLOCK = BLOCKS.register("fantasy_grass_block", () -> new ESGrassBlock(NIGHTSHADE_MUD.get(), PlacedFeatureInit.SWAMP_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> NIGHTSHADE_DIRT = BLOCKS.register("nightshade_dirt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final RegistryObject<Block, Block> NIGHTSHADE_GRASS_BLOCK = BLOCKS.register("nightshade_grass_block", () -> new ESGrassBlock(NIGHTSHADE_DIRT.get(), PlacedFeatureInit.FOREST_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block, Block> AETHERSENT_BLOCK = BLOCKS.register("aethersent_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block, Block> SPRINGSTONE = BLOCKS.register("springstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
    public static final RegistryObject<Block, Block> THERMAL_SPRINGSTONE = BLOCKS.register("thermal_springstone", () -> new ThermalSpringStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block, Block> SWAMP_SILVER_ORE = BLOCKS.register("swamp_silver_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().strength(3.0F, 3.0F)));
    public static final RegistryObject<Block, Block> SWAMP_SILVER_BLOCK = BLOCKS.register("swamp_silver_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).mapColor(MapColor.STONE).strength(5.0F, 3.5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block, Block> ENERGY_BLOCK = BLOCKS.register("energy_block", () -> new EnergyBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block, Block> STARLIGHT_GOLEM_SPAWNER = BLOCKS.register("starlight_golem_spawner", () -> new StarlightGolemSpawnerBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> LUNAR_MONSTROSITY_SPAWNER = BLOCKS.register("lunar_monstrosity_spawner", () -> new LunarMonstrositySpawnerBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block, Block> STARLIGHT_PORTAL = BLOCKS.register("starlight_portal", () -> new ESPortalBlock(BlockBehaviour.Properties.of().strength(-1F).noCollission().lightLevel((state) -> 10)));

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }
    public static void loadClass() {}
}
