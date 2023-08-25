package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.block.*;
import cn.leolezury.eternalstarlight.block.entity.ESWoodTypes;
import cn.leolezury.eternalstarlight.block.modifier.ESFlammabilityModifier;
import cn.leolezury.eternalstarlight.datagen.ConfiguredFeatureInit;
import cn.leolezury.eternalstarlight.mixins.access.*;
import cn.leolezury.eternalstarlight.platform.ESPlatform;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.world.feature.tree.LunarTreeGrower;
import cn.leolezury.eternalstarlight.world.feature.tree.NorthlandTreeGrower;
import cn.leolezury.eternalstarlight.world.feature.tree.StarlightMangroveTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockInit {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, EternalStarlight.MOD_ID);

    public static final RegistryObject<Block> BERRIES_VINES = BLOCKS.register("berries_vines", () -> new BerriesVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks().noCollission().lightLevel(CaveVines.emission(14)).instabreak().sound(SoundType.CAVE_VINES)));
    public static final RegistryObject<Block> BERRIES_VINES_PLANT = BLOCKS.register("berries_vines_plant", () -> new BerriesVinePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().lightLevel(CaveVines.emission(14)).instabreak().sound(SoundType.CAVE_VINES)));
    public static final RegistryObject<Block> RED_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("red_starlight_crystal_cluster", () -> new StarlightCrystalClusterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> {
        return 15;
    }).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> BLUE_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("blue_starlight_crystal_cluster", () -> new StarlightCrystalClusterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> {
        return 15;
    }).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RED_STARLIGHT_CRYSTAL_BLOCK = BLOCKS.register("red_starlight_crystal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().lightLevel((state) -> {
        return 15;
    }).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> BLUE_STARLIGHT_CRYSTAL_BLOCK = BLOCKS.register("blue_starlight_crystal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().lightLevel((state) -> {
        return 15;
    }).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RED_CRYSTAL_MOSS_CARPET = BLOCKS.register("red_crystal_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.MOSS_CARPET).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> BLUE_CRYSTAL_MOSS_CARPET = BLOCKS.register("blue_crystal_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.1F).sound(SoundType.MOSS_CARPET).lightLevel((state) -> {
        return 15;
    })));

    //lunar wood
    public static final RegistryObject<Block> LUNAR_LEAVES = BLOCKS.register("lunar_leaves",
            () -> new ESLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE)).modifiers(ESFlammabilityModifier.LEAVES));
//            {
//                @Override
//                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction){
//                    return true;
//                }
//                @Override
//                public int getFlammability(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 5;
//                }
//                @Override
//                public int getFireSpreadSpeed(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 20;
//                }
//            });
    public static final RegistryObject<Block> LUNAR_LOG = BLOCKS.register("lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> LUNAR_WOOD = BLOCKS.register("lunar_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> LUNAR_PLANKS = BLOCKS.register("lunar_planks",
            () -> new ESModifiedBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK)).modifiers(ESFlammabilityModifier.WOOD));
//            {
//                @Override
//                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction){
//                    return true;
//                }
//                @Override
//                public int getFlammability(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 5;
//                }
//                @Override
//                public int getFireSpreadSpeed(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 20;
//                }
//            });
    public static final RegistryObject<Block> STRIPPED_LUNAR_LOG = BLOCKS.register("stripped_lunar_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BLACK)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> STRIPPED_LUNAR_WOOD = BLOCKS.register("stripped_lunar_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BLACK)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> LUNAR_DOOR = BLOCKS.register("lunar_door",
            () -> DoorBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR_SET));
    public static final RegistryObject<Block> LUNAR_TRAPDOOR = BLOCKS.register("lunar_trapdoor",
            () -> TrapDoorBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR_SET));
    public static final RegistryObject<Block> LUNAR_PRESSURE_PLATE = BLOCKS.register("lunar_pressure_plate",
            () -> PressurePlateBlockAccess.create(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR_SET));
    public static final RegistryObject<Block> LUNAR_BUTTON = BLOCKS.register("lunar_button",
            () -> ButtonBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR_SET, 30, true));
    public static final RegistryObject<Block> LUNAR_FENCE = BLOCKS.register("lunar_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> LUNAR_FENCE_GATE = BLOCKS.register("lunar_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR));
    public static final RegistryObject<Block> LUNAR_SLAB = BLOCKS.register("lunar_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> LUNAR_STAIRS = BLOCKS.register("lunar_stairs",
            () -> StairBlockAccess.create(LUNAR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> LUNAR_SIGN = BLOCKS.register("lunar_sign",
            () -> new ESStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR));
    public static final RegistryObject<Block> LUNAR_WALL_SIGN = BLOCKS.register("lunar_wall_sign",
            () -> new ESWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR));
    public static final RegistryObject<Block> LUNAR_HANGING_SIGN = BLOCKS.register("lunar_hanging_sign",
            () -> new ESCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR));
    public static final RegistryObject<Block> LUNAR_WALL_HANGING_SIGN = BLOCKS.register("lunar_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK), ESWoodTypes.LUNAR));
    public static final RegistryObject<Block> LUNAR_SAPLING = BLOCKS.register("lunar_sapling", () -> SaplingBlockAccess.create(new LunarTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block> POTTED_LUNAR_SAPLING = BLOCKS.register("potted_lunar_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUNAR_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    //northland wood
    public static final RegistryObject<Block> NORTHLAND_LEAVES = BLOCKS.register("northland_leaves",
            () -> new ESLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE)).modifiers(ESFlammabilityModifier.LEAVES));
//            {
//                @Override
//                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction){
//                    return true;
//                }
//                @Override
//                public int getFlammability(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 5;
//                }
//                @Override
//                public int getFireSpreadSpeed(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 20;
//                }
//            });
    public static final RegistryObject<Block> NORTHLAND_LOG = BLOCKS.register("northland_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> NORTHLAND_WOOD = BLOCKS.register("northland_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BROWN)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> NORTHLAND_PLANKS = BLOCKS.register("northland_planks",
            () -> new ESModifiedBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BROWN)).modifiers(ESFlammabilityModifier.WOOD));
//            {
//                @Override
//                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction){
//                    return true;
//                }
//                @Override
//                public int getFlammability(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 5;
//                }
//                @Override
//                public int getFireSpreadSpeed(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 20;
//                }
//            });
    public static final RegistryObject<Block> STRIPPED_NORTHLAND_LOG = BLOCKS.register("stripped_northland_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BROWN)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> STRIPPED_NORTHLAND_WOOD = BLOCKS.register("stripped_northland_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BROWN)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> NORTHLAND_DOOR = BLOCKS.register("northland_door",
            () -> DoorBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND_SET));
    public static final RegistryObject<Block> NORTHLAND_TRAPDOOR = BLOCKS.register("northland_trapdoor",
            () -> TrapDoorBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND_SET));
    public static final RegistryObject<Block> NORTHLAND_PRESSURE_PLATE = BLOCKS.register("northland_pressure_plate",
            () -> PressurePlateBlockAccess.create(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND_SET));
    public static final RegistryObject<Block> NORTHLAND_BUTTON = BLOCKS.register("northland_button",
            () -> ButtonBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND_SET, 30, true));
    public static final RegistryObject<Block> NORTHLAND_FENCE = BLOCKS.register("northland_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> NORTHLAND_FENCE_GATE = BLOCKS.register("northland_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND));
    public static final RegistryObject<Block> NORTHLAND_SLAB = BLOCKS.register("northland_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> NORTHLAND_STAIRS = BLOCKS.register("northland_stairs",
            () -> StairBlockAccess.create(NORTHLAND_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> NORTHLAND_SIGN = BLOCKS.register("northland_sign",
            () -> new ESStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND));
    public static final RegistryObject<Block> NORTHLAND_WALL_SIGN = BLOCKS.register("northland_wall_sign",
            () -> new ESWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND));
    public static final RegistryObject<Block> NORTHLAND_HANGING_SIGN = BLOCKS.register("northland_hanging_sign",
            () -> new ESCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND));
    public static final RegistryObject<Block> NORTHLAND_WALL_HANGING_SIGN = BLOCKS.register("northland_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN), ESWoodTypes.NORTHLAND));
    public static final RegistryObject<Block> NORTHLAND_SAPLING = BLOCKS.register("northland_sapling", () -> SaplingBlockAccess.create(new NorthlandTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block> POTTED_NORTHLAND_SAPLING = BLOCKS.register("potted_northland_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NORTHLAND_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    //starlight mangrove wood
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_LEAVES = BLOCKS.register("starlight_mangrove_leaves",
            () -> new ESLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)).modifiers(ESFlammabilityModifier.LEAVES)); //{
//                @Override
//                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction){
//                    return true;
//                }
//                @Override
//                public int getFlammability(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 5;
//                }
//                @Override
//                public int getFireSpreadSpeed(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 20;
//                }
//            });
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_LOG = BLOCKS.register("starlight_mangrove_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_WOOD = BLOCKS.register("starlight_mangrove_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED)).modifiers(ESFlammabilityModifier.WOOD));
//    public static final RegistryObject<Block> STARLIGHT_MANGROVE_PLANKS = BLOCKS.register("starlight_mangrove_planks",
//            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED)) {
//                @Override
//                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction){
//                    return true;
//                }
//                @Override
//                public int getFlammability(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 5;
//                }
//                @Override
//                public int getFireSpreadSpeed(BlockState state,BlockGetter level, BlockPos pos, Direction direction){
//                    return 20;
//                }
//            });
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_PLANKS = BLOCKS.register("starlight_mangrove_planks",
        () -> new ESModifiedBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED)).modifiers(ESFlammabilityModifier.WOOD));
    public static final RegistryObject<Block> STRIPPED_STARLIGHT_MANGROVE_LOG = BLOCKS.register("stripped_starlight_mangrove_log",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> STRIPPED_STARLIGHT_MANGROVE_WOOD = BLOCKS.register("stripped_starlight_mangrove_wood",
            () -> new ESLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_DOOR = BLOCKS.register("starlight_mangrove_door",
            () -> DoorBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE_SET));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_TRAPDOOR = BLOCKS.register("starlight_mangrove_trapdoor",
            () -> TrapDoorBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE_SET));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_PRESSURE_PLATE = BLOCKS.register("starlight_mangrove_pressure_plate",
            () -> PressurePlateBlockAccess.create(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE_SET));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_BUTTON = BLOCKS.register("starlight_mangrove_button",
            () -> ButtonBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE_SET, 30, true));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_FENCE = BLOCKS.register("starlight_mangrove_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_FENCE_GATE = BLOCKS.register("starlight_mangrove_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_SLAB = BLOCKS.register("starlight_mangrove_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_STAIRS = BLOCKS.register("starlight_mangrove_stairs",
            () -> StairBlockAccess.create(STARLIGHT_MANGROVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_SIGN = BLOCKS.register("starlight_mangrove_sign",
            () -> new ESStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_WALL_SIGN = BLOCKS.register("starlight_mangrove_wall_sign",
            () -> new ESWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_HANGING_SIGN = BLOCKS.register("starlight_mangrove_hanging_sign",
            () -> new ESCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_WALL_HANGING_SIGN = BLOCKS.register("starlight_mangrove_wall_hanging_sign",
            () -> new ESWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED), ESWoodTypes.STARLIGHT_MANGROVE));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_SAPLING = BLOCKS.register("starlight_mangrove_sapling", () -> SaplingBlockAccess.create(new StarlightMangroveTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> POTTED_STARLIGHT_MANGROVE_SAPLING = BLOCKS.register("potted_starlight_mangrove_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_MANGROVE_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> STARLIGHT_MANGROVE_ROOTS = BLOCKS.register("starlight_mangrove_roots", () -> MangroveRootsBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.MANGROVE_ROOTS)));
    public static final RegistryObject<Block> MUDDY_STARLIGHT_MANGROVE_ROOTS = BLOCKS.register("muddy_starlight_mangrove_roots", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.MUDDY_MANGROVE_ROOTS)));

    //grimstone
    public static final RegistryObject<Block> GRIMSTONE = BLOCKS.register("grimstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> GRIMSTONE_BRICKS = BLOCKS.register("grimstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> GRIMSTONE_BRICK_SLAB = BLOCKS.register("grimstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block> GRIMSTONE_BRICK_STAIRS = BLOCKS.register("grimstone_brick_stairs", () -> StairBlockAccess.create(BlockInit.GRIMSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> GRIMSTONE_BRICK_WALL = BLOCKS.register("grimstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> POLISHED_GRIMSTONE = BLOCKS.register("polished_grimstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> POLISHED_GRIMSTONE_SLAB = BLOCKS.register("polished_grimstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block> POLISHED_GRIMSTONE_STAIRS = BLOCKS.register("polished_grimstone_stairs", () -> StairBlockAccess.create(BlockInit.POLISHED_GRIMSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> POLISHED_GRIMSTONE_WALL = BLOCKS.register("polished_grimstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> CHISELED_GRIMSTONE = BLOCKS.register("chiseled_grimstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    //voidstone
    public static final RegistryObject<Block> VOIDSTONE = BLOCKS.register("voidstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> VOIDSTONE_BRICKS = BLOCKS.register("voidstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> VOIDSTONE_BRICK_SLAB = BLOCKS.register("voidstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block> VOIDSTONE_BRICK_STAIRS = BLOCKS.register("voidstone_brick_stairs", () -> StairBlockAccess.create(BlockInit.VOIDSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> VOIDSTONE_BRICK_WALL = BLOCKS.register("voidstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> POLISHED_VOIDSTONE = BLOCKS.register("polished_voidstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> POLISHED_VOIDSTONE_SLAB = BLOCKS.register("polished_voidstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block> POLISHED_VOIDSTONE_STAIRS = BLOCKS.register("polished_voidstone_stairs", () -> StairBlockAccess.create(BlockInit.POLISHED_VOIDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> POLISHED_VOIDSTONE_WALL = BLOCKS.register("polished_voidstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> CHISELED_VOIDSTONE = BLOCKS.register("chiseled_voidstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).lightLevel((state) -> {return 15;})));

    // mud
    public static final RegistryObject<Block> NIGHTSHADE_MUD = BLOCKS.register("nightshade_mud", () -> new MudBlock(BlockBehaviour.Properties.copy(Blocks.MUD)));
    public static final RegistryObject<Block> GLOWING_NIGHTSHADE_MUD = BLOCKS.register("glowing_nightshade_mud", () -> new MudBlock(BlockBehaviour.Properties.copy(Blocks.MUD).lightLevel((state) -> {return 15;})));
    public static final RegistryObject<Block> PACKED_NIGHTSHADE_MUD = BLOCKS.register("packed_nightshade_mud", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_MUD)));
    public static final RegistryObject<Block> NIGHTSHADE_MUD_BRICKS = BLOCKS.register("nightshade_mud_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.MUD_BRICKS)));
    public static final RegistryObject<Block> NIGHTSHADE_MUD_BRICK_SLAB = BLOCKS.register("nightshade_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_SLAB)));
    public static final RegistryObject<Block> NIGHTSHADE_MUD_BRICK_STAIRS = BLOCKS.register("nightshade_mud_brick_stairs", () -> StairBlockAccess.create(NIGHTSHADE_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_STAIRS)));
    public static final RegistryObject<Block> NIGHTSHADE_MUD_BRICK_WALL = BLOCKS.register("nightshade_mud_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_WALL)));

    // common grass
    public static final RegistryObject<Block> STARLIGHT_FLOWER = BLOCKS.register("starlight_flower", () -> FlowerBlockAccess.create(MobEffects.DAMAGE_RESISTANCE, 10, BlockBehaviour.Properties.copy(Blocks.POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> POTTED_STARLIGHT_FLOWER = BLOCKS.register("potted_starlight_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_FLOWER, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> NIGHT_SPROUTS = BLOCKS.register("night_sprouts", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> SMALL_NIGHT_SPROUTS = BLOCKS.register("small_night_sprouts", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> GLOWING_NIGHT_SPROUTS = BLOCKS.register("glowing_night_sprouts", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> SMALL_GLOWING_NIGHT_SPROUTS = BLOCKS.register("small_glowing_night_sprouts", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> LUNAR_GRASS = BLOCKS.register("lunar_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> GLOWING_LUNAR_GRASS = BLOCKS.register("glowing_lunar_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> CRESCENT_GRASS = BLOCKS.register("crescent_grass", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block> GLOWING_CRESCENT_GRASS = BLOCKS.register("glowing_crescent_grass", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> PARASOL_GRASS = BLOCKS.register("parasol_grass", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block> GLOWING_PARASOL_GRASS = BLOCKS.register("glowing_parasol_grass", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> LUNAR_REED = BLOCKS.register("lunar_reed", () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> GLOWING_MUSHROOM = BLOCKS.register("glowing_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(BlockInit::always).lightLevel((state) -> {
        return 15;
    }), ConfiguredFeatureInit.HUGE_GLOWING_MUSHROOM));
    public static final RegistryObject<Block> GLOWING_MUSHROOM_BLOCK = BLOCKS.register("glowing_mushroom_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> {
        return 15;
    })));

    // swamp grass
    public static final RegistryObject<Block> SWAMP_ROSE = BLOCKS.register("swamp_rose", () -> FlowerBlockAccess.create(MobEffects.POISON, 10, BlockBehaviour.Properties.copy(Blocks.POPPY).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> POTTED_SWAMP_ROSE = BLOCKS.register("potted_swamp_rose", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SWAMP_ROSE, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> FANTABUD = BLOCKS.register("fantabud", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> GREEN_FANTABUD = BLOCKS.register("green_fantabud", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_GREEN)) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
        }
    });
    public static final RegistryObject<Block> FANTAFERN = BLOCKS.register("fantafern", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> GREEN_FANTAFERN = BLOCKS.register("green_fantafern", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> FANTAGRASS = BLOCKS.register("fantagrass", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> GREEN_FANTAGRASS = BLOCKS.register("green_fantagrass", () -> TallGrassBlockAccess.create(BlockBehaviour.Properties.copy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)));

    public static final RegistryObject<Block> FANTASY_GRASS_BLOCK = BLOCKS.register("fantasy_grass_block", () -> new NightshadeGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> NIGHTSHADE_GRASS_BLOCK = BLOCKS.register("nightshade_grass_block", () -> new NightshadeGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> NIGHTSHADE_DIRT = BLOCKS.register("nightshade_dirt", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));



    public static final RegistryObject<Block> AETHERSENT_BLOCK = BLOCKS.register("aethersent_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SPRINGSTONE = BLOCKS.register("springstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
    public static final RegistryObject<Block> THERMAL_SPRINGSTONE = BLOCKS.register("thermal_springstone", () -> new ThermalSpringStoneBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> SWAMP_SILVER_ORE = BLOCKS.register("swamp_silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().strength(3.0F, 3.0F), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> SWAMP_SILVER_BLOCK = BLOCKS.register("swamp_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.STONE).strength(5.0F, 3.5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ENERGY_BLOCK = BLOCKS.register("energy_block", () -> new EnergyBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<Block> STARLIGHT_GOLEM_SPAWNER = BLOCKS.register("starlight_golem_spawner", () -> new StarlightGolemSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<Block> LUNAR_MONSTROSITY_SPAWNER = BLOCKS.register("lunar_monstrosity_spawner", () -> new LunarMonstrositySpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<Block> STARLIGHT_PORTAL = BLOCKS.register("starlight_portal", ESPortalBlock::new);
    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }
    public static void postRegistry() {}
}
