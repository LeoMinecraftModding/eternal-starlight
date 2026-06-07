package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.*;
import cn.leolezury.eternalstarlight.common.block.spawner.LunarMonstrositySpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.PermafrostSpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.StarlightGolemSpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.TheGatekeeperSpawnerBlock;
import cn.leolezury.eternalstarlight.common.data.ESConfiguredFeatures;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Optional;

public class ESBlocks {
	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, EternalStarlight.ID);

	// lunar wood
	public static final RegistryObject<Block, LeavesBlock> LUNAR_LEAVES = BLOCKS.register("lunar_leaves",
		() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block, LeavesBlock> CYAN_LUNAR_LEAVES = BLOCKS.register("cyan_lunar_leaves",
		() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_CYAN)));
	public static final RegistryObject<Block, LeavesBlock> PURPLE_LUNAR_LEAVES = BLOCKS.register("purple_lunar_leaves",
		() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, RotatedPillarBlock> LUNAR_LOG = BLOCKS.register("lunar_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> LUNAR_WOOD = BLOCKS.register("lunar_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> LUNAR_PLANKS = BLOCKS.register("lunar_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_LUNAR_LOG = BLOCKS.register("stripped_lunar_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_LUNAR_WOOD = BLOCKS.register("stripped_lunar_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BLACK)));
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
	public static final RegistryObject<Block, StandingSignBlock> LUNAR_SIGN = BLOCKS.register("lunar_sign",
		() -> new StandingSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallSignBlock> LUNAR_WALL_SIGN = BLOCKS.register("lunar_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> LUNAR_HANGING_SIGN = BLOCKS.register("lunar_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallHangingSignBlock> LUNAR_WALL_HANGING_SIGN = BLOCKS.register("lunar_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.LUNAR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SaplingBlock> LUNAR_SAPLING = BLOCKS.register("lunar_sapling", () -> new SaplingBlock(new TreeGrower("lunar", Optional.of(ESConfiguredFeatures.LUNAR_COLORED_HUGE), Optional.of(ESConfiguredFeatures.LUNAR_COLORED), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_LUNAR_SAPLING = BLOCKS.register("potted_lunar_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUNAR_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

	// lunar extras: desert
	public static final RegistryObject<Block, RotatedPillarBlock> DEAD_LUNAR_LOG = BLOCKS.register("dead_lunar_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> RED_CRYSTALLIZED_LUNAR_LOG = BLOCKS.register("red_crystallized_lunar_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> BLUE_CRYSTALLIZED_LUNAR_LOG = BLOCKS.register("blue_crystallized_lunar_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK)));

	// northland wood
	public static final RegistryObject<Block, SnowyLeavesBlock> NORTHLAND_LEAVES = BLOCKS.register("northland_leaves",
		() -> new SnowyLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block, RotatedPillarBlock> NORTHLAND_LOG = BLOCKS.register("northland_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, RotatedPillarBlock> NORTHLAND_WOOD = BLOCKS.register("northland_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, Block> NORTHLAND_PLANKS = BLOCKS.register("northland_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_NORTHLAND_LOG = BLOCKS.register("stripped_northland_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_NORTHLAND_WOOD = BLOCKS.register("stripped_northland_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BROWN)));
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
	public static final RegistryObject<Block, StandingSignBlock> NORTHLAND_SIGN = BLOCKS.register("northland_sign",
		() -> new StandingSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallSignBlock> NORTHLAND_WALL_SIGN = BLOCKS.register("northland_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> NORTHLAND_HANGING_SIGN = BLOCKS.register("northland_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallHangingSignBlock> NORTHLAND_WALL_HANGING_SIGN = BLOCKS.register("northland_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.NORTHLAND, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SaplingBlock> NORTHLAND_SAPLING = BLOCKS.register("northland_sapling", () -> new SaplingBlock(new TreeGrower("northland", Optional.of(ESConfiguredFeatures.NORTHLAND), Optional.empty(), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_NORTHLAND_SAPLING = BLOCKS.register("potted_northland_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NORTHLAND_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

	// banyin wood
	public static final RegistryObject<Block, LeavesBlock> BANYIN_LEAVES = BLOCKS.register("banyin_leaves",
		() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block, RotatedPillarBlock> BANYIN_LOG = BLOCKS.register("banyin_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> BANYIN_WOOD = BLOCKS.register("banyin_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, Block> BANYIN_PLANKS = BLOCKS.register("banyin_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_BANYIN_LOG = BLOCKS.register("stripped_banyin_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_BANYIN_WOOD = BLOCKS.register("stripped_banyin_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, DoorBlock> BANYIN_DOOR = BLOCKS.register("banyin_door",
		() -> new DoorBlock(ESWoodTypes.BANYIN_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, TrapDoorBlock> BANYIN_TRAPDOOR = BLOCKS.register("banyin_trapdoor",
		() -> new TrapDoorBlock(ESWoodTypes.BANYIN_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, PressurePlateBlock> BANYIN_PRESSURE_PLATE = BLOCKS.register("banyin_pressure_plate",
		() -> new PressurePlateBlock(ESWoodTypes.BANYIN_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, ButtonBlock> BANYIN_BUTTON = BLOCKS.register("banyin_button",
		() -> new ButtonBlock(ESWoodTypes.BANYIN_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FenceBlock> BANYIN_FENCE = BLOCKS.register("banyin_fence",
		() -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FenceGateBlock> BANYIN_FENCE_GATE = BLOCKS.register("banyin_fence_gate",
		() -> new FenceGateBlock(ESWoodTypes.BANYIN, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, SlabBlock> BANYIN_SLAB = BLOCKS.register("banyin_slab",
		() -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, StairBlock> BANYIN_STAIRS = BLOCKS.register("banyin_stairs",
		() -> new StairBlock(BANYIN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, StandingSignBlock> BANYIN_SIGN = BLOCKS.register("banyin_sign",
		() -> new StandingSignBlock(ESWoodTypes.BANYIN, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, WallSignBlock> BANYIN_WALL_SIGN = BLOCKS.register("banyin_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.BANYIN, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> BANYIN_HANGING_SIGN = BLOCKS.register("banyin_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.BANYIN, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, WallHangingSignBlock> BANYIN_WALL_HANGING_SIGN = BLOCKS.register("banyin_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.BANYIN, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, SaplingBlock> BANYIN_SAPLING = BLOCKS.register("banyin_sapling", () -> new SaplingBlock(new TreeGrower("banyin", Optional.of(ESConfiguredFeatures.BANYIN_HUGE), Optional.of(ESConfiguredFeatures.BANYIN), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_BANYIN_SAPLING = BLOCKS.register("potted_banyin_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BANYIN_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, MangroveRootsBlock> BANYIN_ROOTS = BLOCKS.register("banyin_roots", () -> new MangroveRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_ROOTS)));
	public static final RegistryObject<Block, RotatedPillarBlock> MUDDY_BANYIN_ROOTS = BLOCKS.register("muddy_banyin_roots", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUDDY_MANGROVE_ROOTS)));

	// scarlet wood
	public static final RegistryObject<Block, ScarletLeavesBlock> SCARLET_LEAVES = BLOCKS.register("scarlet_leaves",
		() -> new ScarletLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, LayeredBlock> SCARLET_LEAVES_PILE = BLOCKS.register("scarlet_leaves_pile",
		() -> new LayeredBlock(BlockBehaviour.Properties.of().replaceable().forceSolidOff().strength(0.1F).isViewBlocking((state, level, pos) -> state.getValue(LayeredBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.GRASS).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> SCARLET_LOG = BLOCKS.register("scarlet_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> SCARLET_WOOD = BLOCKS.register("scarlet_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, Block> SCARLET_PLANKS = BLOCKS.register("scarlet_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_SCARLET_LOG = BLOCKS.register("stripped_scarlet_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_SCARLET_WOOD = BLOCKS.register("stripped_scarlet_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED)));
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
	public static final RegistryObject<Block, StandingSignBlock> SCARLET_SIGN = BLOCKS.register("scarlet_sign",
		() -> new StandingSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, WallSignBlock> SCARLET_WALL_SIGN = BLOCKS.register("scarlet_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> SCARLET_HANGING_SIGN = BLOCKS.register("scarlet_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, WallHangingSignBlock> SCARLET_WALL_HANGING_SIGN = BLOCKS.register("scarlet_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.SCARLET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, SaplingBlock> SCARLET_SAPLING = BLOCKS.register("scarlet_sapling", () -> new SaplingBlock(new TreeGrower("scarlet", Optional.empty(), Optional.of(ESConfiguredFeatures.SCARLET), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SCARLET_SAPLING = BLOCKS.register("potted_scarlet_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SCARLET_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

	// torreya wood
	public static final RegistryObject<Block, LeavesBlock> TORREYA_LEAVES = BLOCKS.register("torreya_leaves",
		() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> TORREYA_LOG = BLOCKS.register("torreya_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> TORREYA_WOOD = BLOCKS.register("torreya_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> TORREYA_PLANKS = BLOCKS.register("torreya_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_TORREYA_LOG = BLOCKS.register("stripped_torreya_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_TORREYA_WOOD = BLOCKS.register("stripped_torreya_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, DoorBlock> TORREYA_DOOR = BLOCKS.register("torreya_door",
		() -> new DoorBlock(ESWoodTypes.TORREYA_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, TrapDoorBlock> TORREYA_TRAPDOOR = BLOCKS.register("torreya_trapdoor",
		() -> new TrapDoorBlock(ESWoodTypes.TORREYA_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, PressurePlateBlock> TORREYA_PRESSURE_PLATE = BLOCKS.register("torreya_pressure_plate",
		() -> new PressurePlateBlock(ESWoodTypes.TORREYA_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, ButtonBlock> TORREYA_BUTTON = BLOCKS.register("torreya_button",
		() -> new ButtonBlock(ESWoodTypes.TORREYA_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, FenceBlock> TORREYA_FENCE = BLOCKS.register("torreya_fence",
		() -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, FenceGateBlock> TORREYA_FENCE_GATE = BLOCKS.register("torreya_fence_gate",
		() -> new FenceGateBlock(ESWoodTypes.TORREYA, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> TORREYA_SLAB = BLOCKS.register("torreya_slab",
		() -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> TORREYA_STAIRS = BLOCKS.register("torreya_stairs",
		() -> new StairBlock(TORREYA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StandingSignBlock> TORREYA_SIGN = BLOCKS.register("torreya_sign",
		() -> new StandingSignBlock(ESWoodTypes.TORREYA, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallSignBlock> TORREYA_WALL_SIGN = BLOCKS.register("torreya_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.TORREYA, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> TORREYA_HANGING_SIGN = BLOCKS.register("torreya_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.TORREYA, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallHangingSignBlock> TORREYA_WALL_HANGING_SIGN = BLOCKS.register("torreya_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.TORREYA, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SaplingBlock> TORREYA_SAPLING = BLOCKS.register("torreya_sapling", () -> new SaplingBlock(new TreeGrower("torreya", Optional.of(ESConfiguredFeatures.TORREYA), Optional.empty(), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_TORREYA_SAPLING = BLOCKS.register("potted_torreya_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TORREYA_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, TorreyaVinesBlock> TORREYA_VINES = BLOCKS.register("torreya_vines", () -> new TorreyaVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 15).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, TorreyaVinesPlantBlock> TORREYA_VINES_PLANT = BLOCKS.register("torreya_vines_plant", () -> new TorreyaVinesPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 0).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, TorreyaCampfireBlock> TORREYA_CAMPFIRE = BLOCKS.register("torreya_campfire", () -> new TorreyaCampfireBlock(true, 1, BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE)));

	// jinglestem wood
	public static final RegistryObject<Block, HangingAlgaleavesBlock> HANGING_ALGALEAVES = BLOCKS.register("hanging_algaleaves",
		() -> new HangingAlgaleavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 5).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, HangingAlgaleavesPlantBlock> HANGING_ALGALEAVES_PLANT = BLOCKS.register("hanging_algaleaves_plant",
		() -> new HangingAlgaleavesPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 5).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, SimpleMultifaceBlock> ALGALEAVES = BLOCKS.register("algaleaves", () -> new SimpleMultifaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, JinglestemLogBlock> JINGLESTEM_LOG = BLOCKS.register("jinglestem_log",
		() -> new JinglestemLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, RotatedPillarBlock> JINGLESTEM_WOOD = BLOCKS.register("jinglestem_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, Block> JINGLESTEM_PLANKS = BLOCKS.register("jinglestem_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_JINGLESTEM_LOG = BLOCKS.register("stripped_jinglestem_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_JINGLESTEM_WOOD = BLOCKS.register("stripped_jinglestem_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, DoorBlock> JINGLESTEM_DOOR = BLOCKS.register("jinglestem_door",
		() -> new DoorBlock(ESWoodTypes.JINGLESTEM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, TrapDoorBlock> JINGLESTEM_TRAPDOOR = BLOCKS.register("jinglestem_trapdoor",
		() -> new TrapDoorBlock(ESWoodTypes.JINGLESTEM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, PressurePlateBlock> JINGLESTEM_PRESSURE_PLATE = BLOCKS.register("jinglestem_pressure_plate",
		() -> new PressurePlateBlock(ESWoodTypes.JINGLESTEM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, ButtonBlock> JINGLESTEM_BUTTON = BLOCKS.register("jinglestem_button",
		() -> new ButtonBlock(ESWoodTypes.JINGLESTEM_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, FenceBlock> JINGLESTEM_FENCE = BLOCKS.register("jinglestem_fence",
		() -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, FenceGateBlock> JINGLESTEM_FENCE_GATE = BLOCKS.register("jinglestem_fence_gate",
		() -> new FenceGateBlock(ESWoodTypes.JINGLESTEM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, SlabBlock> JINGLESTEM_SLAB = BLOCKS.register("jinglestem_slab",
		() -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, StairBlock> JINGLESTEM_STAIRS = BLOCKS.register("jinglestem_stairs",
		() -> new StairBlock(JINGLESTEM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, StandingSignBlock> JINGLESTEM_SIGN = BLOCKS.register("jinglestem_sign",
		() -> new StandingSignBlock(ESWoodTypes.JINGLESTEM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, WallSignBlock> JINGLESTEM_WALL_SIGN = BLOCKS.register("jinglestem_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.JINGLESTEM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> JINGLESTEM_HANGING_SIGN = BLOCKS.register("jinglestem_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.JINGLESTEM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, WallHangingSignBlock> JINGLESTEM_WALL_HANGING_SIGN = BLOCKS.register("jinglestem_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.JINGLESTEM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, JinglestemSaplingBlock> JINGLESTEM_SAPLING = BLOCKS.register("jinglestem_sapling", () -> new JinglestemSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_JINGLESTEM_SAPLING = BLOCKS.register("potted_jinglestem_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, JINGLESTEM_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

	// cradlewood
	public static final RegistryObject<Block, CradlewoodLeavesBlock> CRADLEWOOD_LEAVES = BLOCKS.register("cradlewood_leaves",
		() -> new CradlewoodLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, RotatedPillarBlock> CRADLEWOOD_LOG = BLOCKS.register("cradlewood_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, RotatedPillarBlock> CRADLEWOOD_WOOD = BLOCKS.register("cradlewood_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, Block> CRADLEWOOD_PLANKS = BLOCKS.register("cradlewood_planks",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_CRADLEWOOD_LOG = BLOCKS.register("stripped_cradlewood_log",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_CRADLEWOOD_WOOD = BLOCKS.register("stripped_cradlewood_wood",
		() -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, DoorBlock> CRADLEWOOD_DOOR = BLOCKS.register("cradlewood_door",
		() -> new DoorBlock(ESWoodTypes.CRADLEWOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, TrapDoorBlock> CRADLEWOOD_TRAPDOOR = BLOCKS.register("cradlewood_trapdoor",
		() -> new TrapDoorBlock(ESWoodTypes.CRADLEWOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, PressurePlateBlock> CRADLEWOOD_PRESSURE_PLATE = BLOCKS.register("cradlewood_pressure_plate",
		() -> new PressurePlateBlock(ESWoodTypes.CRADLEWOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, ButtonBlock> CRADLEWOOD_BUTTON = BLOCKS.register("cradlewood_button",
		() -> new ButtonBlock(ESWoodTypes.CRADLEWOOD_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, FenceBlock> CRADLEWOOD_FENCE = BLOCKS.register("cradlewood_fence",
		() -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, FenceGateBlock> CRADLEWOOD_FENCE_GATE = BLOCKS.register("cradlewood_fence_gate",
		() -> new FenceGateBlock(ESWoodTypes.CRADLEWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, SlabBlock> CRADLEWOOD_SLAB = BLOCKS.register("cradlewood_slab",
		() -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StairBlock> CRADLEWOOD_STAIRS = BLOCKS.register("cradlewood_stairs",
		() -> new StairBlock(CRADLEWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StandingSignBlock> CRADLEWOOD_SIGN = BLOCKS.register("cradlewood_sign",
		() -> new StandingSignBlock(ESWoodTypes.CRADLEWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, WallSignBlock> CRADLEWOOD_WALL_SIGN = BLOCKS.register("cradlewood_wall_sign",
		() -> new WallSignBlock(ESWoodTypes.CRADLEWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, CeilingHangingSignBlock> CRADLEWOOD_HANGING_SIGN = BLOCKS.register("cradlewood_hanging_sign",
		() -> new CeilingHangingSignBlock(ESWoodTypes.CRADLEWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, WallHangingSignBlock> CRADLEWOOD_WALL_HANGING_SIGN = BLOCKS.register("cradlewood_wall_hanging_sign",
		() -> new WallHangingSignBlock(ESWoodTypes.CRADLEWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, CradlewoodSaplingBlock> CRADLEWOOD_SAPLING = BLOCKS.register("cradlewood_sapling", () -> new CradlewoodSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_CRADLEWOOD_SAPLING = BLOCKS.register("potted_cradlewood_sapling", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CRADLEWOOD_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

	// grimstone
	public static final RegistryObject<Block, Block> GRIMSTONE = BLOCKS.register("grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
	public static final RegistryObject<Block, SlabBlock> GRIMSTONE_SLAB = BLOCKS.register("grimstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> GRIMSTONE_STAIRS = BLOCKS.register("grimstone_stairs", () -> new StairBlock(GRIMSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> GRIMSTONE_WALL = BLOCKS.register("grimstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
	public static final RegistryObject<Block, Block> COBBLED_GRIMSTONE = BLOCKS.register("cobbled_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)));
	public static final RegistryObject<Block, SlabBlock> COBBLED_GRIMSTONE_SLAB = BLOCKS.register("cobbled_grimstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> COBBLED_GRIMSTONE_STAIRS = BLOCKS.register("cobbled_grimstone_stairs", () -> new StairBlock(COBBLED_GRIMSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> COBBLED_GRIMSTONE_WALL = BLOCKS.register("cobbled_grimstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
	public static final RegistryObject<Block, Block> GRIMSTONE_BRICKS = BLOCKS.register("grimstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block, SlabBlock> GRIMSTONE_BRICK_SLAB = BLOCKS.register("grimstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> GRIMSTONE_BRICK_STAIRS = BLOCKS.register("grimstone_brick_stairs", () -> new StairBlock(GRIMSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> GRIMSTONE_BRICK_WALL = BLOCKS.register("grimstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
	public static final RegistryObject<Block, Block> CRACKED_GRIMSTONE_BRICKS = BLOCKS.register("cracked_grimstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_STONE_BRICKS)));
	public static final RegistryObject<Block, Block> POLISHED_GRIMSTONE = BLOCKS.register("polished_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_GRIMSTONE_SLAB = BLOCKS.register("polished_grimstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> POLISHED_GRIMSTONE_STAIRS = BLOCKS.register("polished_grimstone_stairs", () -> new StairBlock(POLISHED_GRIMSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> POLISHED_GRIMSTONE_WALL = BLOCKS.register("polished_grimstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
	public static final RegistryObject<Block, Block> GRIMSTONE_TILES = BLOCKS.register("grimstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block, SlabBlock> GRIMSTONE_TILE_SLAB = BLOCKS.register("grimstone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> GRIMSTONE_TILE_STAIRS = BLOCKS.register("grimstone_tile_stairs", () -> new StairBlock(GRIMSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> GRIMSTONE_TILE_WALL = BLOCKS.register("grimstone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
	public static final RegistryObject<Block, Block> CRACKED_GRIMSTONE_TILES = BLOCKS.register("cracked_grimstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_STONE_BRICKS)));
	public static final RegistryObject<Block, Block> POLISHED_GRIMSTONE_TILES = BLOCKS.register("polished_grimstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_GRIMSTONE_TILE_SLAB = BLOCKS.register("polished_grimstone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> POLISHED_GRIMSTONE_TILE_STAIRS = BLOCKS.register("polished_grimstone_tile_stairs", () -> new StairBlock(POLISHED_GRIMSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> POLISHED_GRIMSTONE_TILE_WALL = BLOCKS.register("polished_grimstone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)));
	public static final RegistryObject<Block, Block> CHISELED_GRIMSTONE = BLOCKS.register("chiseled_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block, Block> GLOWING_GRIMSTONE = BLOCKS.register("glowing_grimstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).lightLevel(state -> 10)));

	// voidstone
	public static final RegistryObject<Block, Block> VOIDSTONE = BLOCKS.register("voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> VOIDSTONE_SLAB = BLOCKS.register("voidstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> VOIDSTONE_STAIRS = BLOCKS.register("voidstone_stairs", () -> new StairBlock(VOIDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> VOIDSTONE_WALL = BLOCKS.register("voidstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> COBBLED_VOIDSTONE = BLOCKS.register("cobbled_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> COBBLED_VOIDSTONE_SLAB = BLOCKS.register("cobbled_voidstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> COBBLED_VOIDSTONE_STAIRS = BLOCKS.register("cobbled_voidstone_stairs", () -> new StairBlock(COBBLED_VOIDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> COBBLED_VOIDSTONE_WALL = BLOCKS.register("cobbled_voidstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> VOIDSTONE_BRICKS = BLOCKS.register("voidstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> VOIDSTONE_BRICK_SLAB = BLOCKS.register("voidstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> VOIDSTONE_BRICK_STAIRS = BLOCKS.register("voidstone_brick_stairs", () -> new StairBlock(VOIDSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> VOIDSTONE_BRICK_WALL = BLOCKS.register("voidstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> CRACKED_VOIDSTONE_BRICKS = BLOCKS.register("cracked_voidstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> POLISHED_VOIDSTONE = BLOCKS.register("polished_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_VOIDSTONE_SLAB = BLOCKS.register("polished_voidstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> POLISHED_VOIDSTONE_STAIRS = BLOCKS.register("polished_voidstone_stairs", () -> new StairBlock(POLISHED_VOIDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> POLISHED_VOIDSTONE_WALL = BLOCKS.register("polished_voidstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> VOIDSTONE_TILES = BLOCKS.register("voidstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> VOIDSTONE_TILE_SLAB = BLOCKS.register("voidstone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> VOIDSTONE_TILE_STAIRS = BLOCKS.register("voidstone_tile_stairs", () -> new StairBlock(VOIDSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> VOIDSTONE_TILE_WALL = BLOCKS.register("voidstone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> CRACKED_VOIDSTONE_TILES = BLOCKS.register("cracked_voidstone_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> CHISELED_VOIDSTONE = BLOCKS.register("chiseled_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> GLOWING_VOIDSTONE = BLOCKS.register("glowing_voidstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.COLOR_BLACK).lightLevel(state -> 10)));
	public static final RegistryObject<Block, VoidstoneSpikeBlock> VOIDSTONE_SPIKE = BLOCKS.register("voidstone_spike", () -> new VoidstoneSpikeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE).mapColor(MapColor.COLOR_BLACK)));

	// eternal ice
	public static final RegistryObject<Block, Block> ETERNAL_ICE = BLOCKS.register("eternal_ice", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, HalfTransparentBlock> THIN_ETERNAL_ICE = BLOCKS.register("thin_eternal_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE).noOcclusion()));
	public static final RegistryObject<Block, Block> ETERNAL_ICE_BRICKS = BLOCKS.register("eternal_ice_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, SlabBlock> ETERNAL_ICE_BRICK_SLAB = BLOCKS.register("eternal_ice_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, StairBlock> ETERNAL_ICE_BRICK_STAIRS = BLOCKS.register("eternal_ice_brick_stairs", () -> new StairBlock(ETERNAL_ICE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, WallBlock> ETERNAL_ICE_BRICK_WALL = BLOCKS.register("eternal_ice_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, EternalIceLanternBlock> ETERNAL_ICE_LANTERN = BLOCKS.register("eternal_ice_lantern", () -> new EternalIceLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, Block> HAZE_ICE = BLOCKS.register("haze_ice", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, Block> HAZE_ICE_BRICKS = BLOCKS.register("haze_ice_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, SlabBlock> HAZE_ICE_BRICK_SLAB = BLOCKS.register("haze_ice_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, StairBlock> HAZE_ICE_BRICK_STAIRS = BLOCKS.register("haze_ice_brick_stairs", () -> new StairBlock(HAZE_ICE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, WallBlock> HAZE_ICE_BRICK_WALL = BLOCKS.register("haze_ice_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, HazeIceLanternBlock> HAZE_ICE_LANTERN = BLOCKS.register("haze_ice_lantern", () -> new HazeIceLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, HalfTransparentBlock> REINFORCED_ICE = BLOCKS.register("reinforced_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
	public static final RegistryObject<Block, IronBarsBlock> REINFORCED_ICE_PANE = BLOCKS.register("reinforced_ice_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE)));
	public static final RegistryObject<Block, IcicleBlock> ICICLE = BLOCKS.register("icicle", () -> new IcicleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE).sound(SoundType.GLASS).mapColor(MapColor.ICE)));
	public static final RegistryObject<Block, AshenSnowBlock> ASHEN_SNOW = BLOCKS.register("ashen_snow", () -> new AshenSnowBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW).noCollission()));

	// nebulaite
	public static final RegistryObject<Block, Block> NEBULAITE = BLOCKS.register("nebulaite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> NEBULAITE_BRICKS = BLOCKS.register("nebulaite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> NEBULAITE_BRICK_SLAB = BLOCKS.register("nebulaite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> NEBULAITE_BRICK_STAIRS = BLOCKS.register("nebulaite_brick_stairs", () -> new StairBlock(NEBULAITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> NEBULAITE_BRICK_WALL = BLOCKS.register("nebulaite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> CHISELED_NEBULAITE_BRICKS = BLOCKS.register("chiseled_nebulaite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK)));

	// solar
	public static final RegistryObject<Block, StarcoreBlock> STARCORE_BLOCK = BLOCKS.register("starcore_block", () -> new StarcoreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 10)));
	public static final RegistryObject<Block, BlazingStarcoreBlock> BLAZING_STARCORE_BLOCK = BLOCKS.register("blazing_starcore_block", () -> new BlazingStarcoreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 12)));
	public static final RegistryObject<Block, Block> STARCORE_LIGHT = BLOCKS.register("starcore_light", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OCHRE_FROGLIGHT).sound(SoundType.STONE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DropExperienceBlock> GRIMSTONE_STARCORE_ORE = BLOCKS.register("grimstone_starcore_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).lightLevel(state -> 8)));
	public static final RegistryObject<Block, DropExperienceBlock> VOIDSTONE_STARCORE_ORE = BLOCKS.register("voidstone_starcore_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).lightLevel(state -> 4)));
	public static final RegistryObject<Block, DropExperienceBlock> ETERNAL_ICE_STARCORE_ORE = BLOCKS.register("eternal_ice_starcore_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS).lightLevel(state -> 3)));
	public static final RegistryObject<Block, DropExperienceBlock> HAZE_ICE_STARCORE_ORE = BLOCKS.register("haze_ice_starcore_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS).lightLevel(state -> 2)));
	public static final RegistryObject<Block, Block> RADIANITE = BLOCKS.register("radianite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, SlabBlock> RADIANITE_SLAB = BLOCKS.register("radianite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, StairBlock> RADIANITE_STAIRS = BLOCKS.register("radianite_stairs", () -> new StairBlock(RADIANITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, WallBlock> RADIANITE_WALL = BLOCKS.register("radianite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, Block> COBBLED_RADIANITE = BLOCKS.register("cobbled_radianite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, SlabBlock> COBBLED_RADIANITE_SLAB = BLOCKS.register("cobbled_radianite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, StairBlock> COBBLED_RADIANITE_STAIRS = BLOCKS.register("cobbled_radianite_stairs", () -> new StairBlock(COBBLED_RADIANITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, WallBlock> COBBLED_RADIANITE_WALL = BLOCKS.register("cobbled_radianite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, RotatedPillarBlock> RADIANITE_PILLAR = BLOCKS.register("radianite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, Block> POLISHED_RADIANITE = BLOCKS.register("polished_radianite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_RADIANITE_SLAB = BLOCKS.register("polished_radianite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, StairBlock> POLISHED_RADIANITE_STAIRS = BLOCKS.register("polished_radianite_stairs", () -> new StairBlock(POLISHED_RADIANITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, WallBlock> POLISHED_RADIANITE_WALL = BLOCKS.register("polished_radianite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, Block> RADIANITE_BRICKS = BLOCKS.register("radianite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, SlabBlock> RADIANITE_BRICK_SLAB = BLOCKS.register("radianite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, StairBlock> RADIANITE_BRICK_STAIRS = BLOCKS.register("radianite_brick_stairs", () -> new StairBlock(RADIANITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, WallBlock> RADIANITE_BRICK_WALL = BLOCKS.register("radianite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, Block> CHISELED_RADIANITE = BLOCKS.register("chiseled_radianite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, Block> FLARE_BRICKS = BLOCKS.register("flare_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SlabBlock> FLARE_BRICK_SLAB = BLOCKS.register("flare_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, StairBlock> FLARE_BRICK_STAIRS = BLOCKS.register("flare_brick_stairs", () -> new StairBlock(FLARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallBlock> FLARE_BRICK_WALL = BLOCKS.register("flare_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, Block> CUT_FLARE_BRICKS = BLOCKS.register("cut_flare_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SlabBlock> CUT_FLARE_BRICK_SLAB = BLOCKS.register("cut_flare_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, StairBlock> CUT_FLARE_BRICK_STAIRS = BLOCKS.register("cut_flare_brick_stairs", () -> new StairBlock(CUT_FLARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallBlock> CUT_FLARE_BRICK_WALL = BLOCKS.register("cut_flare_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, Block> FLARE_TILES = BLOCKS.register("flare_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SlabBlock> FLARE_TILE_SLAB = BLOCKS.register("flare_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, StairBlock> FLARE_TILE_STAIRS = BLOCKS.register("flare_tile_stairs", () -> new StairBlock(FLARE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallBlock> FLARE_TILE_WALL = BLOCKS.register("flare_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, Block> CUT_FLARE_TILES = BLOCKS.register("cut_flare_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SlabBlock> CUT_FLARE_TILE_SLAB = BLOCKS.register("cut_flare_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, StairBlock> CUT_FLARE_TILE_STAIRS = BLOCKS.register("cut_flare_tile_stairs", () -> new StairBlock(CUT_FLARE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallBlock> CUT_FLARE_TILE_WALL = BLOCKS.register("cut_flare_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, RotatedPillarBlock> CHISELED_FLARE_PILLAR = BLOCKS.register("chiseled_flare_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR).mapColor(MapColor.COLOR_BROWN)));

	// stellagmite
	public static final RegistryObject<Block, StellagmiteBlock> STELLAGMITE = BLOCKS.register("stellagmite", () -> new StellagmiteBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StellagmiteSlabBlock> STELLAGMITE_SLAB = BLOCKS.register("stellagmite_slab", () -> new StellagmiteSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StellagmiteStairBlock> STELLAGMITE_STAIRS = BLOCKS.register("stellagmite_stairs", () -> new StellagmiteStairBlock(STELLAGMITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StellagmiteWallBlock> STELLAGMITE_WALL = BLOCKS.register("stellagmite_wall", () -> new StellagmiteWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StellagmiteBlock> MOLTEN_STELLAGMITE = BLOCKS.register("molten_stellagmite", () -> new StellagmiteBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12)));
	public static final RegistryObject<Block, StellagmiteSlabBlock> MOLTEN_STELLAGMITE_SLAB = BLOCKS.register("molten_stellagmite_slab", () -> new StellagmiteSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12)));
	public static final RegistryObject<Block, StellagmiteStairBlock> MOLTEN_STELLAGMITE_STAIRS = BLOCKS.register("molten_stellagmite_stairs", () -> new StellagmiteStairBlock(MOLTEN_STELLAGMITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12)));
	public static final RegistryObject<Block, StellagmiteWallBlock> MOLTEN_STELLAGMITE_WALL = BLOCKS.register("molten_stellagmite_wall", () -> new StellagmiteWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12)));
	public static final RegistryObject<Block, Block> POLISHED_STELLAGMITE = BLOCKS.register("polished_stellagmite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_STELLAGMITE_SLAB = BLOCKS.register("polished_stellagmite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, StairBlock> POLISHED_STELLAGMITE_STAIRS = BLOCKS.register("polished_stellagmite_stairs", () -> new StairBlock(POLISHED_STELLAGMITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block, WallBlock> POLISHED_STELLAGMITE_WALL = BLOCKS.register("polished_stellagmite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE)));

	// the abyss
	public static final RegistryObject<Block, AbyssalFireBlock> ABYSSAL_FIRE = BLOCKS.register("abyssal_fire", () -> new AbyssalFireBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 10)));
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
	public static final RegistryObject<Block, MagmaBlock> ABYSSAL_MAGMA_BLOCK = BLOCKS.register("abyssal_magma_block", () -> new MagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.DEEPSLATE)));
	public static final RegistryObject<Block, AbyssalGeyserBlock> ABYSSAL_GEYSER = BLOCKS.register("abyssal_geyser", () -> new AbyssalGeyserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));
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
	public static final RegistryObject<Block, MagmaBlock> THERMABYSSAL_MAGMA_BLOCK = BLOCKS.register("thermabyssal_magma_block", () -> new MagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK)));
	public static final RegistryObject<Block, AbyssalGeyserBlock> THERMABYSSAL_GEYSER = BLOCKS.register("thermabyssal_geyser", () -> new AbyssalGeyserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.NETHER)));
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
	public static final RegistryObject<Block, MagmaBlock> CRYOBYSSAL_MAGMA_BLOCK = BLOCKS.register("cryobyssal_magma_block", () -> new MagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.DEEPSLATE)));
	public static final RegistryObject<Block, AbyssalGeyserBlock> CRYOBYSSAL_GEYSER = BLOCKS.register("cryobyssal_geyser", () -> new AbyssalGeyserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

	// mud
	public static final RegistryObject<Block, NightfallMudBlock> NIGHTFALL_MUD = BLOCKS.register("nightfall_mud", () -> new NightfallMudBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD)));
	public static final RegistryObject<Block, NightfallMudBlock> GLOWING_NIGHTFALL_MUD = BLOCKS.register("glowing_nightfall_mud", () -> new NightfallMudBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).lightLevel(state -> 15)));
	public static final RegistryObject<Block, Block> PACKED_NIGHTFALL_MUD = BLOCKS.register("packed_nightfall_mud", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD)));
	public static final RegistryObject<Block, Block> NIGHTFALL_MUD_BRICKS = BLOCKS.register("nightfall_mud_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS)));
	public static final RegistryObject<Block, SlabBlock> NIGHTFALL_MUD_BRICK_SLAB = BLOCKS.register("nightfall_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_SLAB)));
	public static final RegistryObject<Block, StairBlock> NIGHTFALL_MUD_BRICK_STAIRS = BLOCKS.register("nightfall_mud_brick_stairs", () -> new StairBlock(NIGHTFALL_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_STAIRS)));
	public static final RegistryObject<Block, WallBlock> NIGHTFALL_MUD_BRICK_WALL = BLOCKS.register("nightfall_mud_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_WALL)));

	// sand
	public static final RegistryObject<Block, ColoredFallingBlock> TWILIGHT_SAND = BLOCKS.register("twilight_sand", () -> new ColoredFallingBlock(new ColorRGBA(0x907e9b), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, Block> TWILIGHT_SANDSTONE = BLOCKS.register("twilight_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, SlabBlock> TWILIGHT_SANDSTONE_SLAB = BLOCKS.register("twilight_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, StairBlock> TWILIGHT_SANDSTONE_STAIRS = BLOCKS.register("twilight_sandstone_stairs", () -> new StairBlock(TWILIGHT_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, WallBlock> TWILIGHT_SANDSTONE_WALL = BLOCKS.register("twilight_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, Block> CUT_TWILIGHT_SANDSTONE = BLOCKS.register("cut_twilight_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, SlabBlock> CUT_TWILIGHT_SANDSTONE_SLAB = BLOCKS.register("cut_twilight_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, StairBlock> CUT_TWILIGHT_SANDSTONE_STAIRS = BLOCKS.register("cut_twilight_sandstone_stairs", () -> new StairBlock(CUT_TWILIGHT_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, WallBlock> CUT_TWILIGHT_SANDSTONE_WALL = BLOCKS.register("cut_twilight_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, Block> CHISELED_TWILIGHT_SANDSTONE = BLOCKS.register("chiseled_twilight_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));

	// gravel
	public static final RegistryObject<Block, ColoredFallingBlock> DUSTED_GRAVEL = BLOCKS.register("dusted_gravel", () -> new ColoredFallingBlock(new ColorRGBA(0x53415e), BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, Block> DUSTED_BRICKS = BLOCKS.register("dusted_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, SlabBlock> DUSTED_BRICK_SLAB = BLOCKS.register("dusted_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, StairBlock> DUSTED_BRICK_STAIRS = BLOCKS.register("dusted_brick_stairs", () -> new StairBlock(DUSTED_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, WallBlock> DUSTED_BRICK_WALL = BLOCKS.register("dusted_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, Block> MOSSY_DUSTED_GRAVEL = BLOCKS.register("mossy_dusted_gravel", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).mapColor(MapColor.GRASS)));
	public static final RegistryObject<Block, Block> GLOWING_MOSSY_DUSTED_GRAVEL = BLOCKS.register("glowing_mossy_dusted_gravel", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).mapColor(MapColor.GRASS).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ESBrushableBlock> SUSPICIOUS_DUSTED_GRAVEL = BLOCKS.register("suspicious_dusted_gravel", () -> new ESBrushableBlock(DUSTED_GRAVEL.get(), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_GRAVEL).mapColor(MapColor.COLOR_PURPLE)));

	// slag
	public static final RegistryObject<Block, ColoredFallingBlock> DIMSLAG = BLOCKS.register("dimslag", () -> new ColoredFallingBlock(new ColorRGBA(0x514c5d), BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, ESBrushableBlock> SUSPICIOUS_DIMSLAG = BLOCKS.register("suspicious_dimslag", () -> new ESBrushableBlock(DIMSLAG.get(), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_GRAVEL).mapColor(MapColor.COLOR_PURPLE)));

	// common plant
	public static final RegistryObject<Block, FlowerBlock> STARLIGHT_FLOWER = BLOCKS.register("starlight_flower", () -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_STARLIGHT_FLOWER = BLOCKS.register("potted_starlight_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_FLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerBlock> AUREATE_FLOWER = BLOCKS.register("aureate_flower", () -> new FlowerBlock(MobEffects.ABSORPTION, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_AUREATE_FLOWER = BLOCKS.register("potted_aureate_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AUREATE_FLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, FlowerBlock> CONEBLOOM = BLOCKS.register("conebloom", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_CONEBLOOM = BLOCKS.register("potted_conebloom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CONEBLOOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, FlowerBlock> NIGHTFAN = BLOCKS.register("nightfan", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_NIGHTFAN = BLOCKS.register("potted_nightfan", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NIGHTFAN, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, FlowerBlock> PINK_ROSE = BLOCKS.register("pink_rose", () -> new FlowerBlock(MobEffects.DIG_SPEED, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PINK)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_PINK_ROSE = BLOCKS.register("potted_pink_rose", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_ROSE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, FlowerBlock> STARLIGHT_TORCHFLOWER = BLOCKS.register("starlight_torchflower", () -> new FlowerBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PINK).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_STARLIGHT_TORCHFLOWER = BLOCKS.register("potted_starlight_torchflower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_TORCHFLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DoublePlantBlock> NIGHTFAN_BUSH = BLOCKS.register("nightfan_bush", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, DoublePlantBlock> PINK_ROSE_BUSH = BLOCKS.register("pink_rose_bush", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_PINK)));
	public static final RegistryObject<Block, ShortBushBlock> NIGHT_SPROUTS = BLOCKS.register("night_sprouts", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, ShortBushBlock> SMALL_NIGHT_SPROUTS = BLOCKS.register("small_night_sprouts", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, ShortBushBlock> GLOWING_NIGHT_SPROUTS = BLOCKS.register("glowing_night_sprouts", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> SMALL_GLOWING_NIGHT_SPROUTS = BLOCKS.register("small_glowing_night_sprouts", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> LUNAR_GRASS = BLOCKS.register("lunar_grass", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, ShortBushBlock> GLOWING_LUNAR_GRASS = BLOCKS.register("glowing_lunar_grass", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> CRESCENT_GRASS = BLOCKS.register("crescent_grass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_CRESCENT_GRASS = BLOCKS.register("potted_crescent_grass", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CRESCENT_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, ShortBushBlock> GLOWING_CRESCENT_GRASS = BLOCKS.register("glowing_crescent_grass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLOWING_CRESCENT_GRASS = BLOCKS.register("potted_glowing_crescent_grass", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWING_CRESCENT_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> PARASOL_GRASS = BLOCKS.register("parasol_grass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_PARASOL_GRASS = BLOCKS.register("potted_parasol_grass", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PARASOL_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, ShortBushBlock> GLOWING_PARASOL_GRASS = BLOCKS.register("glowing_parasol_grass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLOWING_PARASOL_GRASS = BLOCKS.register("potted_glowing_parasol_grass", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWING_PARASOL_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> LUNAR_BUSH = BLOCKS.register("lunar_bush", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, ShortBushBlock> GLOWING_LUNAR_BUSH = BLOCKS.register("glowing_lunar_bush", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_CRESCENT_GRASS = BLOCKS.register("tall_crescent_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_GLOWING_CRESCENT_GRASS = BLOCKS.register("tall_glowing_crescent_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DoublePlantOnSandBlock> LUNAR_REED = BLOCKS.register("lunar_reed", () -> new DoublePlantOnSandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> WHISPERBLOOM = BLOCKS.register("whisperbloom", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PINK)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_WHISPERBLOOM = BLOCKS.register("potted_whisperbloom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHISPERBLOOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, ShortBushBlock> GLADESPIKE = BLOCKS.register("gladespike", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLADESPIKE = BLOCKS.register("potted_gladespike", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLADESPIKE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, ShortBushBlock> VIVIDSTALK = BLOCKS.register("vividstalk", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_VIVIDSTALK = BLOCKS.register("potted_vividstalk", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, VIVIDSTALK, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_GLADESPIKE = BLOCKS.register("tall_gladespike", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, ShortBushBlock> MOONLIGHT_BUSH = BLOCKS.register("moonlight_bush", () -> new MoonlightBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).randomTicks().lightLevel(state -> state.getValue(MoonlightBushBlock.BERRIES) ? 15 : 0)));
	public static final RegistryObject<Block, DoublePlantBlock> GLINTGRASS = BLOCKS.register("glintgrass", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 10)));
	public static final RegistryObject<Block, MushroomBlock> GLOWING_MUSHROOM = BLOCKS.register("glowing_mushroom", () -> new MushroomBlock(ESConfiguredFeatures.HUGE_GLOWING_MUSHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLOWING_MUSHROOM = BLOCKS.register("potted_glowing_mushroom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWING_MUSHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, HugeMushroomBlock> GLOWING_MUSHROOM_BLOCK = BLOCKS.register("glowing_mushroom_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, HugeMushroomBlock> GLOWING_MUSHROOM_STEM = BLOCKS.register("glowing_mushroom_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, MushroomBlock> SHINING_MUSHROOM = BLOCKS.register("shining_mushroom", () -> new MushroomBlock(ESConfiguredFeatures.HUGE_SHINING_MUSHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.TERRACOTTA_RED).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SHINING_MUSHROOM = BLOCKS.register("potted_shining_mushroom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHINING_MUSHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, HugeMushroomBlock> SHINING_MUSHROOM_BLOCK = BLOCKS.register("shining_mushroom_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_RED).lightLevel(state -> 15)));
	public static final RegistryObject<Block, HugeMushroomBlock> SHINING_MUSHROOM_STEM = BLOCKS.register("shining_mushroom_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_RED)));
	public static final RegistryObject<Block, BerriesVinesBlock> BERRIES_VINES = BLOCKS.register("berries_vines", () -> new BerriesVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block, BerriesVinesPlantBlock> BERRIES_VINES_PLANT = BLOCKS.register("berries_vines_plant", () -> new BerriesVinesPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block, CaveMossBlock> CAVE_MOSS = BLOCKS.register("cave_moss", () -> new CaveMossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 7).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Block, CaveMossPlantBlock> CAVE_MOSS_PLANT = BLOCKS.register("cave_moss_plant", () -> new CaveMossPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 7).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Block, CaveMossVeinBlock> CAVE_MOSS_VEIN = BLOCKS.register("cave_moss_vein", () -> new CaveMossVeinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(state -> 7).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Block, CaveMossFullBlock> CAVE_MOSS_BLOCK = BLOCKS.register("cave_moss_block", () -> new CaveMossFullBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, CaveMossCarpetBlock> CAVE_MOSS_CARPET = BLOCKS.register("cave_moss_carpet", () -> new CaveMossCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, BouldershroomBlock> BOULDERSHROOM = BLOCKS.register("bouldershroom", () -> new BouldershroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_PINK)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_BOULDERSHROOM = BLOCKS.register("potted_bouldershroom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BOULDERSHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, HugeMushroomBlock> BOULDERSHROOM_BLOCK = BLOCKS.register("bouldershroom_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_PINK)));
	public static final RegistryObject<Block, HugeMushroomBlock> BOULDERSHROOM_STEM = BLOCKS.register("bouldershroom_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM)));
	public static final RegistryObject<Block, BouldershroomRootsBlock> BOULDERSHROOM_ROOTS = BLOCKS.register("bouldershroom_roots", () -> new BouldershroomRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, BouldershroomRootsPlantBlock> BOULDERSHROOM_ROOTS_PLANT = BLOCKS.register("bouldershroom_roots_plant", () -> new BouldershroomRootsPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

	// swamp plant
	public static final RegistryObject<Block, FlowerBlock> SWAMP_ROSE = BLOCKS.register("swamp_rose", () -> new FlowerBlock(MobEffects.POISON, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SWAMP_ROSE = BLOCKS.register("potted_swamp_rose", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SWAMP_ROSE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, ShortBushBlock> FANTABUD = BLOCKS.register("fantabud", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, ShortBushBlock> GREEN_FANTABUD = BLOCKS.register("green_fantabud", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, ShortBushBlock> FANTAFERN = BLOCKS.register("fantafern", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_FANTAFERN = BLOCKS.register("potted_fantafern", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FANTAFERN, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> GREEN_FANTAFERN = BLOCKS.register("green_fantafern", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GREEN_FANTAFERN = BLOCKS.register("potted_green_fantafern", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREEN_FANTAFERN, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, ShortBushBlock> FANTAGRASS = BLOCKS.register("fantagrass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> GREEN_FANTAGRASS = BLOCKS.register("green_fantagrass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, HangingFantagrassBlock> HANGING_FANTAGRASS = BLOCKS.register("hanging_fantagrass", () -> new HangingFantagrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 0).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Block, HangingFantagrassPlantBlock> HANGING_FANTAGRASS_PLANT = BLOCKS.register("hanging_fantagrass_plant", () -> new HangingFantagrassPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 0).mapColor(MapColor.PLANT)));

	// scarlet forest plant
	public static final RegistryObject<Block, ShortBushBlock> ORANGE_SCARLET_BUD = BLOCKS.register("orange_scarlet_bud", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE)));
	public static final RegistryObject<Block, ShortBushBlock> PURPLE_SCARLET_BUD = BLOCKS.register("purple_scarlet_bud", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, ShortBushBlock> RED_SCARLET_BUD = BLOCKS.register("red_scarlet_bud", () -> new ShortBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, ShortBushBlock> SCARLET_GRASS = BLOCKS.register("scarlet_grass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, DesertBushBlock> MAUVE_FERN = BLOCKS.register("mauve_fern", () -> new DesertBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE)));

	// torreya forest plant
	public static final RegistryObject<Block, FlowerBlock> WITHERED_STARLIGHT_FLOWER = BLOCKS.register("withered_starlight_flower", () -> new FlowerBlock(MobEffects.WITHER, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_ORANGE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_WITHERED_STARLIGHT_FLOWER = BLOCKS.register("potted_withered_starlight_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WITHERED_STARLIGHT_FLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, AlwaysSurvivingBushBlock> AMARAMBER_GRASS = BLOCKS.register("amaramber_grass", () -> new AlwaysSurvivingBushBlock(12, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_AMARAMBER_GRASS = BLOCKS.register("potted_amaramber_grass", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AMARAMBER_GRASS, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, AlwaysSurvivingBushBlock> AMARAMBER_GRASS_BUSH = BLOCKS.register("amaramber_grass_bush", () -> new AlwaysSurvivingBushBlock(12, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> GLOOMCANDLE_ROOT = BLOCKS.register("gloomcandle_root", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_GREEN).lightLevel(state -> 10)));

	// desert stuff
	public static final RegistryObject<Block, Block> RED_STARLIGHT_CRYSTAL_BLOCK = BLOCKS.register("red_starlight_crystal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().lightLevel(state -> 10).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, Block> BLUE_STARLIGHT_CRYSTAL_BLOCK = BLOCKS.register("blue_starlight_crystal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().lightLevel(state -> 10).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, DirectionalBudBlock> RED_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("red_starlight_crystal_cluster", () -> new DirectionalBudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, DirectionalBudBlock> BLUE_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("blue_starlight_crystal_cluster", () -> new DirectionalBudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, Block> RED_STARLIGHT_CRYSTAL_LANTERN = BLOCKS.register("red_starlight_crystal_lantern", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().lightLevel(state -> 15).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, Block> BLUE_STARLIGHT_CRYSTAL_LANTERN = BLOCKS.register("blue_starlight_crystal_lantern", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().lightLevel(state -> 15).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, DeadBushBlock> DEAD_LUNAR_BUSH = BLOCKS.register("dead_lunar_bush", () -> new DeadBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_DEAD_LUNAR_BUSH = BLOCKS.register("potted_dead_lunar_bush", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DEAD_LUNAR_BUSH, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_DEAD_BUSH)));
	public static final RegistryObject<Block, DesertFlowerBlock> DESERT_AMETHYSIA = BLOCKS.register("desert_amethysia", () -> new DesertFlowerBlock(ESMobEffects.CRYSTAL_INFECTION.asHolder(), 4f, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_DESERT_AMETHYSIA = BLOCKS.register("potted_desert_amethysia", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DESERT_AMETHYSIA, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, DesertFlowerBlock> WITHERED_DESERT_AMETHYSIA = BLOCKS.register("withered_desert_amethysia", () -> new DesertFlowerBlock(ESMobEffects.CRYSTAL_INFECTION.asHolder(), 4f, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_WITHERED_DESERT_AMETHYSIA = BLOCKS.register("potted_withered_desert_amethysia", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WITHERED_DESERT_AMETHYSIA, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, DesertBushBlock> SUNSET_THORNBLOOM = BLOCKS.register("sunset_thornbloom", () -> new DesertBushBlock(10, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH).mapColor(MapColor.COLOR_ORANGE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SUNSET_THORNBLOOM = BLOCKS.register("potted_sunset_thornbloom", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SUNSET_THORNBLOOM, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, DesertBushBlock> AMETHYSIA_GRASS = BLOCKS.register("amethysia_grass", () -> new DesertBushBlock(10, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, LunarisCactusBlock> LUNARIS_CACTUS = BLOCKS.register("lunaris_cactus", () -> new LunarisCactusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, LunarisCactusGelBlock> LUNARIS_CACTUS_GEL_BLOCK = BLOCKS.register("lunaris_cactus_gel_block", () -> new LunarisCactusGelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, EquipableCarvedLunarisCactusFruitBlock> CARVED_LUNARIS_CACTUS_FRUIT = BLOCKS.register("carved_lunaris_cactus_fruit", () -> new EquipableCarvedLunarisCactusFruitBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, CarvedLunarisCactusFruitBlock> LUNARIS_CACTUS_FRUIT_LANTERN = BLOCKS.register("lunaris_cactus_fruit_lantern", () -> new CarvedLunarisCactusFruitBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, DirectionalBudBlock> BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("blooming_red_starlight_crystal_cluster", () -> new DirectionalBudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, DirectionalBudBlock> BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER = BLOCKS.register("blooming_blue_starlight_crystal_cluster", () -> new DirectionalBudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10).sound(SoundType.AMETHYST)));
	public static final RegistryObject<Block, DesertFlowerBlock> RED_CRYSTALFLEUR = BLOCKS.register("red_crystalfleur", () -> new DesertFlowerBlock(ESMobEffects.CRYSTAL_INFECTION.asHolder(), 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_RED_CRYSTALFLEUR = BLOCKS.register("potted_red_crystalfleur", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_CRYSTALFLEUR, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, DesertFlowerBlock> BLUE_CRYSTALFLEUR = BLOCKS.register("blue_crystalfleur", () -> new DesertFlowerBlock(ESMobEffects.CRYSTAL_INFECTION.asHolder(), 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_BLUE_CRYSTALFLEUR = BLOCKS.register("potted_blue_crystalfleur", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUE_CRYSTALFLEUR, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
	public static final RegistryObject<Block, CrystalfleurVineBlock> RED_CRYSTALFLEUR_VINE = BLOCKS.register("red_crystalfleur_vine", () -> new CrystalfleurVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(state -> 0).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, CrystalfleurVineBlock> BLUE_CRYSTALFLEUR_VINE = BLOCKS.register("blue_crystalfleur_vine", () -> new CrystalfleurVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(state -> 0).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, ESMossBlock> RED_CRYSTAL_MOSS_BLOCK = BLOCKS.register("red_crystal_moss_block", () -> new ESMossBlock(ESConfiguredFeatures.RED_CRYSTAL_MOSS_PATCH_BONEMEAL, ESParticles.FALLING_RED_CRYSTAL_MOSS.asHolder(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, ESMossBlock> BLUE_CRYSTAL_MOSS_BLOCK = BLOCKS.register("blue_crystal_moss_block", () -> new ESMossBlock(ESConfiguredFeatures.BLUE_CRYSTAL_MOSS_PATCH_BONEMEAL, ESParticles.FALLING_BLUE_CRYSTAL_MOSS.asHolder(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, CarpetBlock> RED_CRYSTAL_MOSS_CARPET = BLOCKS.register("red_crystal_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY).lightLevel(state -> 10)));
	public static final RegistryObject<Block, CarpetBlock> BLUE_CRYSTAL_MOSS_CARPET = BLOCKS.register("blue_crystal_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY).lightLevel(state -> 10)));

	// beach plant
	public static final RegistryObject<Block, DesertFlowerBlock> FIRE_ORCHID = BLOCKS.register("fire_orchid", () -> new DesertFlowerBlock(MobEffects.FIRE_RESISTANCE, 60, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_FIRE_ORCHID = BLOCKS.register("potted_fire_orchid", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FIRE_ORCHID, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DesertBushBlock> BLAZEBANK_GRASS = BLOCKS.register("blazebank_grass", () -> new DesertBushBlock(10, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_RED)));

	// water plant
	public static final RegistryObject<Block, WaterlilyBlock> MOONLIGHT_LILY_PAD = BLOCKS.register("moonlight_lily_pad", () -> new WaterlilyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)));
	public static final RegistryObject<Block, WaterlilyWithFlowerBlock> STARLIT_LILY_PAD = BLOCKS.register("starlit_lily_pad", () -> new WaterlilyWithFlowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).lightLevel(state -> state.getValue(WaterlilyWithFlowerBlock.LIT) ? 15 : 0)));
	public static final RegistryObject<Block, WaterlilyBlock> MOONLIGHT_DUCKWEED = BLOCKS.register("moonlight_duckweed", () -> new WaterlilyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollission()));

	// sea stuff
	public static final RegistryObject<Block, AbyssalKelpBlock> ABYSSAL_KELP = BLOCKS.register("abyssal_kelp", () -> new AbyssalKelpBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP).lightLevel(CaveVines.emission(14))));
	public static final RegistryObject<Block, AbyssalKelpPlantBlock> ABYSSAL_KELP_PLANT = BLOCKS.register("abyssal_kelp_plant", () -> new AbyssalKelpPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT).lightLevel(CaveVines.emission(14))));
	public static final RegistryObject<Block, OrbfloraBlock> ORBFLORA = BLOCKS.register("orbflora", () -> new OrbfloraBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP).lightLevel(state -> 10)));
	public static final RegistryObject<Block, OrbfloraPlantBlock> ORBFLORA_PLANT = BLOCKS.register("orbflora_plant", () -> new OrbfloraPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT)));
	public static final RegistryObject<Block, Block> ORBFLORA_LIGHT = BLOCKS.register("orbflora_light", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OCHRE_FROGLIGHT)));
	public static final RegistryObject<Block, SpiralKelpBlock> SPIRAL_KELP = BLOCKS.register("spiral_kelp", () -> new SpiralKelpBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP)));
	public static final RegistryObject<Block, SpiralKelpPlantBlock> SPIRAL_KELP_PLANT = BLOCKS.register("spiral_kelp_plant", () -> new SpiralKelpPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT)));
	public static final RegistryObject<Block, SimpleMultifaceBlock> SEA_ROSA = BLOCKS.register("sea_rosa", () -> new SimpleMultifaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Block, WickGrassBlock> WICK_GRASS = BLOCKS.register("wick_grass", () -> new WickGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_SEAGRASS).lightLevel(state -> 12)));
	public static final RegistryObject<Block, LumenstemBlock> LUMENSTEM = BLOCKS.register("lumenstem", () -> new LumenstemBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 8)));
	public static final RegistryObject<Block, LumenstemPlantBlock> LUMENSTEM_PLANT = BLOCKS.register("lumenstem_plant", () -> new LumenstemPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 8)));
	public static final RegistryObject<Block, MarimoldBlock> MARIMOLD = BLOCKS.register("marimold", () -> new MarimoldBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 12)));
	public static final RegistryObject<Block, TransparentBlock> MARIMOLD_BLOCK = BLOCKS.register("marimold_block", () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.SLIME_BLOCK).noOcclusion().lightLevel(state -> 12)));
	public static final RegistryObject<Block, HugeMushroomBlock> MARIMOLD_STEM = BLOCKS.register("marimold_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.SLIME_BLOCK)));
	public static final RegistryObject<Block, SeaBushBlock> CIRCULUSH = BLOCKS.register("circulush", () -> new SeaBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 8)));
	public static final RegistryObject<Block, SeaBushBlock> STONETT = BLOCKS.register("stonett", () -> new SeaBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 8)));
	public static final RegistryObject<Block, SeaBushBlock> LUMINIS = BLOCKS.register("luminis", () -> new SeaBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 12)));
	public static final RegistryObject<Block, SeaBushBlock> GLOWLIS = BLOCKS.register("glowlis", () -> new SeaBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 12)));
	public static final RegistryObject<Block, SeaBushBlock> GLOREED = BLOCKS.register("gloreed", () -> new SeaBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 12)));
	public static final RegistryObject<Block, SeaBushBlock> STARLIGHT_SEAGRASS = BLOCKS.register("starlight_seagrass", () -> new SeaBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)));
	public static final RegistryObject<Block, JinglingPickleBlock> JINGLING_PICKLE = BLOCKS.register("jingling_pickle", () -> new JinglingPickleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> JinglingPickleBlock.isDead(state) ? 0 : 3).sound(SoundType.SLIME_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, BaseCoralPlantBlock> DEAD_TENTACLES_CORAL = BLOCKS.register("dead_tentacles_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL)));
	public static final RegistryObject<Block, CoralPlantBlock> TENTACLES_CORAL = BLOCKS.register("tentacles_coral", () -> new CoralPlantBlock(DEAD_TENTACLES_CORAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, BaseCoralFanBlock> DEAD_TENTACLES_CORAL_FAN = BLOCKS.register("dead_tentacles_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN)));
	public static final RegistryObject<Block, CoralFanBlock> TENTACLES_CORAL_FAN = BLOCKS.register("tentacles_coral_fan", () -> new CoralFanBlock(DEAD_TENTACLES_CORAL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, BaseCoralWallFanBlock> DEAD_TENTACLES_CORAL_WALL_FAN = BLOCKS.register("dead_tentacles_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN).dropsLike(DEAD_TENTACLES_CORAL_FAN.get())));
	public static final RegistryObject<Block, CoralWallFanBlock> TENTACLES_CORAL_WALL_FAN = BLOCKS.register("tentacles_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_TENTACLES_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_RED).dropsLike(TENTACLES_CORAL_FAN.get())));
	public static final RegistryObject<Block, Block> DEAD_TENTACLES_CORAL_BLOCK = BLOCKS.register("dead_tentacles_coral_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK)));
	public static final RegistryObject<Block, CoralBlock> TENTACLES_CORAL_BLOCK = BLOCKS.register("tentacles_coral_block", () -> new CoralBlock(DEAD_TENTACLES_CORAL_BLOCK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, BaseCoralPlantBlock> DEAD_GOLDEN_CORAL = BLOCKS.register("dead_golden_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL)));
	public static final RegistryObject<Block, CoralPlantBlock> GOLDEN_CORAL = BLOCKS.register("golden_coral", () -> new CoralPlantBlock(DEAD_GOLDEN_CORAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, BaseCoralFanBlock> DEAD_GOLDEN_CORAL_FAN = BLOCKS.register("dead_golden_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN)));
	public static final RegistryObject<Block, CoralFanBlock> GOLDEN_CORAL_FAN = BLOCKS.register("golden_coral_fan", () -> new CoralFanBlock(DEAD_GOLDEN_CORAL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, BaseCoralWallFanBlock> DEAD_GOLDEN_CORAL_WALL_FAN = BLOCKS.register("dead_golden_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN).dropsLike(DEAD_GOLDEN_CORAL_FAN.get())));
	public static final RegistryObject<Block, CoralWallFanBlock> GOLDEN_CORAL_WALL_FAN = BLOCKS.register("golden_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_GOLDEN_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_YELLOW).dropsLike(GOLDEN_CORAL_FAN.get())));
	public static final RegistryObject<Block, Block> DEAD_GOLDEN_CORAL_BLOCK = BLOCKS.register("dead_golden_coral_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK)));
	public static final RegistryObject<Block, CoralBlock> GOLDEN_CORAL_BLOCK = BLOCKS.register("golden_coral_block", () -> new CoralBlock(DEAD_GOLDEN_CORAL_BLOCK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, BaseCoralPlantBlock> DEAD_CRYSTALLUM_CORAL = BLOCKS.register("dead_crystallum_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL)));
	public static final RegistryObject<Block, CoralPlantBlock> CRYSTALLUM_CORAL = BLOCKS.register("crystallum_coral", () -> new CoralPlantBlock(DEAD_CRYSTALLUM_CORAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_CYAN)));
	public static final RegistryObject<Block, BaseCoralFanBlock> DEAD_CRYSTALLUM_CORAL_FAN = BLOCKS.register("dead_crystallum_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN)));
	public static final RegistryObject<Block, CoralFanBlock> CRYSTALLUM_CORAL_FAN = BLOCKS.register("crystallum_coral_fan", () -> new CoralFanBlock(DEAD_CRYSTALLUM_CORAL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_CYAN)));
	public static final RegistryObject<Block, BaseCoralWallFanBlock> DEAD_CRYSTALLUM_CORAL_WALL_FAN = BLOCKS.register("dead_crystallum_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN).dropsLike(DEAD_CRYSTALLUM_CORAL_FAN.get())));
	public static final RegistryObject<Block, CoralWallFanBlock> CRYSTALLUM_CORAL_WALL_FAN = BLOCKS.register("crystallum_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_CYAN).dropsLike(CRYSTALLUM_CORAL_FAN.get())));
	public static final RegistryObject<Block, Block> DEAD_CRYSTALLUM_CORAL_BLOCK = BLOCKS.register("dead_crystallum_coral_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK)));
	public static final RegistryObject<Block, CoralBlock> CRYSTALLUM_CORAL_BLOCK = BLOCKS.register("crystallum_coral_block", () -> new CoralBlock(DEAD_CRYSTALLUM_CORAL_BLOCK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_CYAN)));
	public static final RegistryObject<Block, VelvetumossBlock> VELVETUMOSS = BLOCKS.register("velvetumoss", () -> new VelvetumossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).randomTicks().sound(SoundType.SLIME_BLOCK)));
	public static final RegistryObject<Block, VelvetumossVilliBlock> VELVETUMOSS_VILLI = BLOCKS.register("velvetumoss_villi", () -> new VelvetumossVilliBlock(VELVETUMOSS.asHolder(), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).noCollission().randomTicks()));
	public static final RegistryObject<Block, RedVelvetumossBlock> RED_VELVETUMOSS = BLOCKS.register("red_velvetumoss", () -> new RedVelvetumossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).randomTicks().mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, VelvetumossVilliBlock> RED_VELVETUMOSS_VILLI = BLOCKS.register("red_velvetumoss_villi", () -> new VelvetumossVilliBlock(RED_VELVETUMOSS.asHolder(), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).noCollission().randomTicks().mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, AquaticFlowerBlock> RED_VELVETUMOSS_FLOWER = BLOCKS.register("red_velvetumoss_flower", () -> new AquaticFlowerBlock(MobEffects.WATER_BREATHING, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_RED_VELVETUMOSS_FLOWER = BLOCKS.register("potted_red_velvetumoss_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_VELVETUMOSS_FLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

	// crystal caves plant
	public static final RegistryObject<Block, DesertBushBlock> CRYSTALLIZED_LUNAR_GRASS = BLOCKS.register("crystallized_lunar_grass", () -> new DesertBushBlock(8, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(DyeColor.RED)));
	public static final RegistryObject<Block, DesertBushBlock> RED_CRYSTAL_ROOTS = BLOCKS.register("red_crystal_roots", () -> new DesertBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(DyeColor.RED)));
	public static final RegistryObject<Block, DesertBushBlock> BLUE_CRYSTAL_ROOTS = BLOCKS.register("blue_crystal_roots", () -> new DesertBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(DyeColor.BLUE)));
	public static final RegistryObject<Block, DoublePlantOnStoneBlock> TWILVEWRYM_HERB = BLOCKS.register("twilvewyrm_herb", () -> new DoublePlantOnStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(DyeColor.BLUE)));
	public static final RegistryObject<Block, DoublePlantOnStoneBlock> STELLAFLY_BUSH = BLOCKS.register("stellafly_bush", () -> new DoublePlantOnStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(DyeColor.BLUE)));
	public static final RegistryObject<Block, DoublePlantOnStoneBlock> GLIMMERFLY_BUSH = BLOCKS.register("glimmerfly_bush", () -> new DoublePlantOnStoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(DyeColor.RED).lightLevel(state -> 10)));

	// solaris isles plant
	public static final RegistryObject<Block, FlowerBlock> SACRED_STARLIGHT_FLOWER = BLOCKS.register("sacred_starlight_flower", () -> new FlowerBlock(MobEffects.JUMP, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SACRED_STARLIGHT_FLOWER = BLOCKS.register("potted_sacred_starlight_flower", () -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SACRED_STARLIGHT_FLOWER, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ShortBushBlock> CRESCENTLEAF = BLOCKS.register("crescentleaf", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, ShortBushBlock> GOLDEN_GRASS = BLOCKS.register("golden_grass", () -> new ShortBushBlock(13, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_GOLDEN_GRASS = BLOCKS.register("tall_golden_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, SacredLanternvineBlock> SACRED_LANTERNVINE = BLOCKS.register("sacred_lanternvine", () -> new SacredLanternvineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 15)));
	public static final RegistryObject<Block, SacredLanternvinePlantBlock> SACRED_LANTERNVINE_PLANT = BLOCKS.register("sacred_lanternvine_plant", () -> new SacredLanternvinePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block, HangingSacredLanternvineBlock> HANGING_SACRED_LANTERNVINE = BLOCKS.register("hanging_sacred_lanternvine", () -> new HangingSacredLanternvineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 15)));
	public static final RegistryObject<Block, HangingSacredLanternvinePlantBlock> HANGING_SACRED_LANTERNVINE_PLANT = BLOCKS.register("hanging_sacred_lanternvine_plant", () -> new HangingSacredLanternvinePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES_PLANT).mapColor(MapColor.COLOR_YELLOW)));

	// dirt & grass blocks
	public static final RegistryObject<Block, Block> NIGHTFALL_DIRT = BLOCKS.register("nightfall_dirt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
	public static final RegistryObject<Block, FarmBlock> NIGHTFALL_FARMLAND = BLOCKS.register("nightfall_farmland", () -> new FarmBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND)));
	public static final RegistryObject<Block, DirtPathBlock> NIGHTFALL_DIRT_PATH = BLOCKS.register("nightfall_dirt_path", () -> new DirtPathBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH)));
	public static final RegistryObject<Block, ESGrassBlock> NIGHTFALL_GRASS_BLOCK = BLOCKS.register("nightfall_grass_block", () -> new ESGrassBlock(NIGHTFALL_DIRT.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, SnowyDirtBlock> NIGHTFALL_PODZOL = BLOCKS.register("nightfall_podzol", () -> new SnowyDirtBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL)));
	public static final RegistryObject<Block, ESGrassBlock> TENACIOUS_NIGHTFALL_GRASS_BLOCK = BLOCKS.register("tenacious_nightfall_grass_block", () -> new ESGrassBlock(NIGHTFALL_DIRT.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, ESGrassBlock> FANTASY_GRASS_BLOCK = BLOCKS.register("fantasy_grass_block", () -> new ESGrassBlock(NIGHTFALL_MUD.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, CarpetBlock> FANTASY_GRASS_CARPET = BLOCKS.register("fantasy_grass_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, ESGrassBlock> GOLDEN_GRASS_BLOCK = BLOCKS.register("golden_grass_block", () -> new ESGrassBlock(NIGHTFALL_DIRT.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.GOLD)));

	// crops
	public static final RegistryObject<Block, CrinoaBlock> CRINOA = BLOCKS.register("crinoa", () -> new CrinoaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, CrinoaBaleBlock> CRINOA_BALE = BLOCKS.register("crinoa_bale", () -> new CrinoaBaleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, NocturnalMilletTopBlock> NOCTURNAL_MILLET_PANICLE = BLOCKS.register("nocturnal_millet_panicle", () -> new NocturnalMilletTopBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, NocturnalMilletBottomBlock> NOCTURNAL_MILLET_STALK = BLOCKS.register("nocturnal_millet_stalk", () -> new NocturnalMilletBottomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).mapColor(MapColor.COLOR_GRAY)));

	// aethersent
	public static final RegistryObject<Block, Block> RAW_AETHERSENT_BLOCK = BLOCKS.register("raw_aethersent_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, Block> AETHERSENT_BLOCK = BLOCKS.register("aethersent_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE)));

	// thermal springstone
	public static final RegistryObject<Block, Block> SPRINGSTONE = BLOCKS.register("springstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, SlabBlock> SPRINGSTONE_SLAB = BLOCKS.register("springstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, StairBlock> SPRINGSTONE_STAIRS = BLOCKS.register("springstone_stairs", () -> new StairBlock(SPRINGSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, WallBlock> SPRINGSTONE_WALL = BLOCKS.register("springstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, Block> SPRINGSTONE_BRICKS = BLOCKS.register("springstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, SlabBlock> SPRINGSTONE_BRICK_SLAB = BLOCKS.register("springstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, StairBlock> SPRINGSTONE_BRICK_STAIRS = BLOCKS.register("springstone_brick_stairs", () -> new StairBlock(SPRINGSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, WallBlock> SPRINGSTONE_BRICK_WALL = BLOCKS.register("springstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, Block> POLISHED_SPRINGSTONE = BLOCKS.register("polished_springstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_SPRINGSTONE_SLAB = BLOCKS.register("polished_springstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, StairBlock> POLISHED_SPRINGSTONE_STAIRS = BLOCKS.register("polished_springstone_stairs", () -> new StairBlock(POLISHED_SPRINGSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, WallBlock> POLISHED_SPRINGSTONE_WALL = BLOCKS.register("polished_springstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, Block> CHISELED_SPRINGSTONE = BLOCKS.register("chiseled_springstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, ThermalSpringstoneBlock> THERMAL_SPRINGSTONE = BLOCKS.register("thermal_springstone", () -> new ThermalSpringstoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SlabBlock> THERMAL_SPRINGSTONE_SLAB = BLOCKS.register("thermal_springstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, StairBlock> THERMAL_SPRINGSTONE_STAIRS = BLOCKS.register("thermal_springstone_stairs", () -> new StairBlock(THERMAL_SPRINGSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, WallBlock> THERMAL_SPRINGSTONE_WALL = BLOCKS.register("thermal_springstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, Block> THERMAL_SPRINGSTONE_BRICKS = BLOCKS.register("thermal_springstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, SlabBlock> THERMAL_SPRINGSTONE_BRICK_SLAB = BLOCKS.register("thermal_springstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, StairBlock> THERMAL_SPRINGSTONE_BRICK_STAIRS = BLOCKS.register("thermal_springstone_brick_stairs", () -> new StairBlock(THERMAL_SPRINGSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));
	public static final RegistryObject<Block, WallBlock> THERMAL_SPRINGSTONE_BRICK_WALL = BLOCKS.register("thermal_springstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F)));

	// glacite
	public static final RegistryObject<Block, DropExperienceBlock> GLACITE = BLOCKS.register("glacite", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).mapColor(MapColor.SNOW).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> GLACITE_BLOCK = BLOCKS.register("glacite_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.SNOW).sound(SoundType.GLASS)));

	// starlit diamond
	public static final RegistryObject<Block, DropExperienceBlock> GRIMSTONE_STARLIT_DIAMOND_ORE = BLOCKS.register("grimstone_starlit_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)));
	public static final RegistryObject<Block, DropExperienceBlock> VOIDSTONE_STARLIT_DIAMOND_ORE = BLOCKS.register("voidstone_starlit_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)));
	public static final RegistryObject<Block, DropExperienceBlock> ETERNAL_ICE_STARLIT_DIAMOND_ORE = BLOCKS.register("eternal_ice_starlit_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, DropExperienceBlock> HAZE_ICE_STARLIT_DIAMOND_ORE = BLOCKS.register("haze_ice_starlit_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> STARLIT_DIAMOND_BLOCK = BLOCKS.register("starlit_diamond_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE)));

	// deepsilver
	public static final RegistryObject<Block, Block> GRIMSTONE_DEEPSILVER_ORE = BLOCKS.register("grimstone_deepsilver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block, Block> VOIDSTONE_DEEPSILVER_ORE = BLOCKS.register("voidstone_deepsilver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE)));
	public static final RegistryObject<Block, Block> ETERNAL_ICE_DEEPSILVER_ORE = BLOCKS.register("eternal_ice_deepsilver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> HAZE_ICE_DEEPSILVER_ORE = BLOCKS.register("haze_ice_deepsilver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> NIGHTFALL_MUD_DEEPSILVER_ORE = BLOCKS.register("nightfall_mud_deepsilver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).strength(3.0F).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block, Block> PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE = BLOCKS.register("packed_nightfall_mud_deepsilver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD).strength(4.0F).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block, Block> RAW_DEEPSILVER_BLOCK = BLOCKS.register("raw_deepsilver_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).strength(5.0F, 3.5F)));
	public static final RegistryObject<Block, Block> DEEPSILVER_BLOCK = BLOCKS.register("deepsilver_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 3.5F)));
	public static final RegistryObject<Block, WaterloggedTransparentBlock> DEEPSILVER_GRATE = BLOCKS.register("deepsilver_grate", () -> new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).strength(5.0F, 3.5F).mapColor(MapColor.METAL)));
	public static final RegistryObject<Block, IronBarsBlock> DEEPSILVER_BARS = BLOCKS.register("deepsilver_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(5.0F, 3.5F)));

	public static final RegistryObject<Block, Block> UNREALIUM_BLOCK = BLOCKS.register("unrealium_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 3.5F).noCollission()));
	public static final RegistryObject<Block, IronBarsBlock> UNREALIUM_BARS = BLOCKS.register("unrealium_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(5.0F, 3.5F)));

	public static final RegistryObject<Block, DropExperienceBlock> GRIMSTONE_MALARITE_ORE = BLOCKS.register("grimstone_malarite_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block, DropExperienceBlock> VOIDSTONE_MALARITE_ORE = BLOCKS.register("voidstone_malarite_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE)));
	public static final RegistryObject<Block, DropExperienceBlock> NIGHTFALL_MUD_MALARITE_ORE = BLOCKS.register("nightfall_mud_malarite_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).strength(3.0F).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block, DropExperienceBlock> PACKED_NIGHTFALL_MUD_MALARITE_ORE = BLOCKS.register("packed_nightfall_mud_malarite_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD).strength(4.0F).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block, Block> MALARITE_BLOCK = BLOCKS.register("malarite_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(2.0F, 3.5F)));

	public static final RegistryObject<Block, PungencyFruitVinesBlock> PUNGENCY_FRUIT_VINES = BLOCKS.register("pungency_fruit_vines", () -> new PungencyFruitVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, TearBombBlock> TEAR_BOMB = BLOCKS.register("tear_bomb", () -> ESPlatform.INSTANCE.createTearBombBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TNT).mapColor(MapColor.COLOR_PURPLE)));

	public static final RegistryObject<Block, DryingRackBlock> DRYING_RACK = BLOCKS.register("drying_rack", () -> new DryingRackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).noOcclusion().sound(SoundType.WOOD)));

	public static final RegistryObject<Block, StarfireBirdNestBlock> STARFIRE_BIRD_NEST = BLOCKS.register("starfire_bird_nest", () -> new StarfireBirdNestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).strength(0.2F).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> OAK_STARFIRE_BIRD_AVIARY = BLOCKS.register("oak_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.WOOD).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> SPRUCE_STARFIRE_BIRD_AVIARY = BLOCKS.register("spruce_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.SPRUCE, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.PODZOL).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> BIRCH_STARFIRE_BIRD_AVIARY = BLOCKS.register("birch_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.BIRCH, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.SAND).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> ACACIA_STARFIRE_BIRD_AVIARY = BLOCKS.register("acacia_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.ACACIA, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_ORANGE).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> CHERRY_STARFIRE_BIRD_AVIARY = BLOCKS.register("cherry_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.CHERRY_WOOD).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> JUNGLE_STARFIRE_BIRD_AVIARY = BLOCKS.register("jungle_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.JUNGLE, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.DIRT).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> DARK_OAK_STARFIRE_BIRD_AVIARY = BLOCKS.register("dark_oak_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.DARK_OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_BROWN).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> CRIMSON_STARFIRE_BIRD_AVIARY = BLOCKS.register("crimson_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.CRIMSON_STEM).sound(SoundType.NETHER_WOOD).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> WARPED_STARFIRE_BIRD_AVIARY = BLOCKS.register("warped_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.WARPED, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.WARPED_STEM).sound(SoundType.NETHER_WOOD).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> MANGROVE_STARFIRE_BIRD_AVIARY = BLOCKS.register("mangrove_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_RED).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> BAMBOO_STARFIRE_BIRD_AVIARY = BLOCKS.register("bamboo_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(BlockSetType.BAMBOO, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.BAMBOO_WOOD).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> LUNAR_STARFIRE_BIRD_AVIARY = BLOCKS.register("lunar_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.LUNAR_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_BLACK).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> NORTHLAND_STARFIRE_BIRD_AVIARY = BLOCKS.register("northland_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.NORTHLAND_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_BROWN).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> BANYIN_STARFIRE_BIRD_AVIARY = BLOCKS.register("banyin_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.BANYIN_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_RED).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> SCARLET_STARFIRE_BIRD_AVIARY = BLOCKS.register("scarlet_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.SCARLET_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_RED).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> TORREYA_STARFIRE_BIRD_AVIARY = BLOCKS.register("torreya_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.TORREYA_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_BLACK).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> JINGLESTEM_STARFIRE_BIRD_AVIARY = BLOCKS.register("jinglestem_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.JINGLESTEM_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_GREEN).noOcclusion()));
	public static final RegistryObject<Block, StarfireBirdAviaryBlock> CRADLEWOOD_STARFIRE_BIRD_AVIARY = BLOCKS.register("cradlewood_starfire_bird_aviary", () -> new StarfireBirdAviaryBlock(ESWoodTypes.CRADLEWOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion()));

	public static final RegistryObject<Block, Block> RAW_FLOWGLAZE = BLOCKS.register("raw_flowglaze", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).strength(0.3F).sound(SoundType.GLASS).lightLevel(state -> 10)));
	public static final RegistryObject<Block, TransparentBlock> FLOWGLAZE = BLOCKS.register("flowglaze", () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).lightLevel(state -> 10)));
	public static final RegistryObject<Block, IronBarsBlock> FLOWGLAZE_PANE = BLOCKS.register("flowglaze_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).lightLevel(state -> 10)));
	public static final RegistryObject<Block, Block> FLOWGLAZE_BRICKS = BLOCKS.register("flowglaze_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).sound(SoundType.GLASS).mapColor(MapColor.TERRACOTTA_CYAN)));
	public static final RegistryObject<Block, SlabBlock> FLOWGLAZE_BRICK_SLAB = BLOCKS.register("flowglaze_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).sound(SoundType.GLASS).mapColor(MapColor.TERRACOTTA_CYAN)));
	public static final RegistryObject<Block, StairBlock> FLOWGLAZE_BRICK_STAIRS = BLOCKS.register("flowglaze_brick_stairs", () -> new StairBlock(FLOWGLAZE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).sound(SoundType.GLASS).mapColor(MapColor.TERRACOTTA_CYAN)));
	public static final RegistryObject<Block, WallBlock> FLOWGLAZE_BRICK_WALL = BLOCKS.register("flowglaze_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).sound(SoundType.GLASS).mapColor(MapColor.TERRACOTTA_CYAN)));

	public static final RegistryObject<Block, DropExperienceBlock> GRIMSTONE_SALTPETER_ORE = BLOCKS.register("grimstone_saltpeter_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block, DropExperienceBlock> VOIDSTONE_SALTPETER_ORE = BLOCKS.register("voidstone_saltpeter_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE)));
	public static final RegistryObject<Block, DropExperienceBlock> ETERNAL_ICE_SALTPETER_ORE = BLOCKS.register("eternal_ice_saltpeter_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, DropExperienceBlock> HAZE_ICE_SALTPETER_ORE = BLOCKS.register("haze_ice_saltpeter_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> SALTPETER_BLOCK = BLOCKS.register("saltpeter_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).mapColor(MapColor.COLOR_YELLOW)));

	public static final RegistryObject<Block, RawAmaramberBlock> RAW_AMARAMBER_BLOCK = BLOCKS.register("raw_amaramber_block", () -> new RawAmaramberBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).mapColor(MapColor.COLOR_RED)));
	public static final RegistryObject<Block, LanternBlock> AMARAMBER_LANTERN = BLOCKS.register("amaramber_lantern", () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
	public static final RegistryObject<Block, AmaramberCandleBlock> AMARAMBER_CANDLE = BLOCKS.register("amaramber_candle", () -> new AmaramberCandleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CANDLE)));
	public static final RegistryObject<Block, AmaramberCandleCakeBlock> AMARAMBER_CANDLE_CAKE = BLOCKS.register("amaramber_candle_cake", () -> new AmaramberCandleCakeBlock(AMARAMBER_CANDLE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE_CAKE)));
	public static final RegistryObject<Block, AmaramberFireBlock> AMARAMBER_FIRE = BLOCKS.register("amaramber_fire", () -> new AmaramberFireBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE)));
	public static final RegistryObject<Block, Block> AMARAMBER_BRICKS = BLOCKS.register("amaramber_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> AMARAMBER_BRICK_SLAB = BLOCKS.register("amaramber_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> AMARAMBER_BRICK_STAIRS = BLOCKS.register("amaramber_brick_stairs", () -> new StairBlock(AMARAMBER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> AMARAMBER_BRICK_WALL = BLOCKS.register("amaramber_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, Block> TORREYA_TILES = BLOCKS.register("torreya_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SlabBlock> TORREYA_TILE_SLAB = BLOCKS.register("torreya_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StairBlock> TORREYA_TILE_STAIRS = BLOCKS.register("torreya_tile_stairs", () -> new StairBlock(TORREYA_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, WallBlock> TORREYA_TILE_WALL = BLOCKS.register("torreya_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK)));

	// ether
	public static final RegistryObject<Block, LiquidBlock> ETHER = BLOCKS.register("ether", () -> new LiquidBlock(ESFluids.ETHER_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).mapColor(MapColor.SNOW)));
	public static final RegistryObject<Block, Block> THIOQUARTZ_BLOCK = BLOCKS.register("thioquartz_block", () -> new ThioquartzBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, BuddingSulfurQuartzBlock> BUDDING_THIOQUARTZ = BLOCKS.register("budding_thioquartz", () -> new BuddingSulfurQuartzBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BUDDING_AMETHYST).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, DirectionalBudBlock> THIOQUARTZ_CLUSTER = BLOCKS.register("thioquartz_cluster", () -> new DirectionalBudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 5).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> TOXITE = BLOCKS.register("toxite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, SlabBlock> TOXITE_SLAB = BLOCKS.register("toxite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, StairBlock> TOXITE_STAIRS = BLOCKS.register("toxite_stairs", () -> new StairBlock(TOXITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, WallBlock> TOXITE_WALL = BLOCKS.register("toxite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, Block> TOXITE_BRICKS = BLOCKS.register("toxite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, SlabBlock> TOXITE_BRICK_SLAB = BLOCKS.register("toxite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, StairBlock> TOXITE_BRICK_STAIRS = BLOCKS.register("toxite_brick_stairs", () -> new StairBlock(TOXITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, WallBlock> TOXITE_BRICK_WALL = BLOCKS.register("toxite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, PolishedToxiteBlock> POLISHED_TOXITE = BLOCKS.register("polished_toxite", () -> new PolishedToxiteBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_TOXITE_SLAB = BLOCKS.register("polished_toxite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, StairBlock> POLISHED_TOXITE_STAIRS = BLOCKS.register("polished_toxite_stairs", () -> new StairBlock(TOXITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, WallBlock> POLISHED_TOXITE_WALL = BLOCKS.register("polished_toxite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block, Block> CHISELED_TOXITE = BLOCKS.register("chiseled_toxite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_LIGHT_GREEN)));

	public static final RegistryObject<Block, RedStoneOreBlock> GRIMSTONE_REDSTONE_ORE = BLOCKS.register("grimstone_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_ORE)));
	public static final RegistryObject<Block, RedStoneOreBlock> VOIDSTONE_REDSTONE_ORE = BLOCKS.register("voidstone_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_REDSTONE_ORE)));
	public static final RegistryObject<Block, Block> ETERNAL_ICE_REDSTONE_ORE = BLOCKS.register("eternal_ice_redstone_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));
	public static final RegistryObject<Block, Block> HAZE_ICE_REDSTONE_ORE = BLOCKS.register("haze_ice_redstone_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).mapColor(MapColor.ICE).sound(SoundType.GLASS)));

	// mob stuff
	public static final RegistryObject<Block, YetiFurBlock> WHITE_YETI_FUR = BLOCKS.register("white_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> ORANGE_YETI_FUR = BLOCKS.register("orange_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> MAGENTA_YETI_FUR = BLOCKS.register("magenta_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> LIGHT_BLUE_YETI_FUR = BLOCKS.register("light_blue_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> YELLOW_YETI_FUR = BLOCKS.register("yellow_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> LIME_YETI_FUR = BLOCKS.register("lime_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> PINK_YETI_FUR = BLOCKS.register("pink_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> GRAY_YETI_FUR = BLOCKS.register("gray_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> LIGHT_GRAY_YETI_FUR = BLOCKS.register("light_gray_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> CYAN_YETI_FUR = BLOCKS.register("cyan_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> PURPLE_YETI_FUR = BLOCKS.register("purple_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> BLUE_YETI_FUR = BLOCKS.register("blue_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> BROWN_YETI_FUR = BLOCKS.register("brown_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> GREEN_YETI_FUR = BLOCKS.register("green_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> RED_YETI_FUR = BLOCKS.register("red_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL)));
	public static final RegistryObject<Block, YetiFurBlock> BLACK_YETI_FUR = BLOCKS.register("black_yeti_fur", () -> new YetiFurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL)));

	public static final RegistryObject<Block, CarpetBlock> WHITE_YETI_FUR_CARPET = BLOCKS.register("white_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> ORANGE_YETI_FUR_CARPET = BLOCKS.register("orange_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> MAGENTA_YETI_FUR_CARPET = BLOCKS.register("magenta_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> LIGHT_BLUE_YETI_FUR_CARPET = BLOCKS.register("light_blue_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> YELLOW_YETI_FUR_CARPET = BLOCKS.register("yellow_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> LIME_YETI_FUR_CARPET = BLOCKS.register("lime_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> PINK_YETI_FUR_CARPET = BLOCKS.register("pink_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> GRAY_YETI_FUR_CARPET = BLOCKS.register("gray_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> LIGHT_GRAY_YETI_FUR_CARPET = BLOCKS.register("light_gray_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> CYAN_YETI_FUR_CARPET = BLOCKS.register("cyan_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> PURPLE_YETI_FUR_CARPET = BLOCKS.register("purple_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> BLUE_YETI_FUR_CARPET = BLOCKS.register("blue_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> BROWN_YETI_FUR_CARPET = BLOCKS.register("brown_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> GREEN_YETI_FUR_CARPET = BLOCKS.register("green_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> RED_YETI_FUR_CARPET = BLOCKS.register("red_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET)));
	public static final RegistryObject<Block, CarpetBlock> BLACK_YETI_FUR_CARPET = BLOCKS.register("black_yeti_fur_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_CARPET)));

	public static final RegistryObject<Block, SkullBlock> TANGLED_SKULL = BLOCKS.register("tangled_skull", () -> new SkullBlock(ESSkullType.TANGLED, BlockBehaviour.Properties.ofFullCopy(Blocks.SKELETON_SKULL)));
	public static final RegistryObject<Block, WallSkullBlock> TANGLED_WALL_SKULL = BLOCKS.register("tangled_wall_skull", () -> new WallSkullBlock(ESSkullType.TANGLED, BlockBehaviour.Properties.ofFullCopy(Blocks.SKELETON_SKULL)));

	public static final RegistryObject<Block, Block> TOOTH_OF_HUNGER_TILES = BLOCKS.register("tooth_of_hunger_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, SlabBlock> TOOTH_OF_HUNGER_TILE_SLAB = BLOCKS.register("tooth_of_hunger_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, StairBlock> TOOTH_OF_HUNGER_TILE_STAIRS = BLOCKS.register("tooth_of_hunger_tile_stairs", () -> new StairBlock(TOOTH_OF_HUNGER_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, WallBlock> TOOTH_OF_HUNGER_TILE_WALL = BLOCKS.register("tooth_of_hunger_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, Block> CHISELED_TOOTH_OF_HUNGER_TILES = BLOCKS.register("chiseled_tooth_of_hunger_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, CrystalbornCatalystBlock> CRYSTALBORN_CATALYST = BLOCKS.register("crystalborn_catalyst", () -> new CrystalbornCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN)));
	public static final RegistryObject<Block, Block> CRYSTALLIZED_SAND = BLOCKS.register("crystallized_sand", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_PURPLE)));

	// loot chest
	public static final RegistryObject<Block, LootChestBlock> LOOT_CHEST = BLOCKS.register("loot_chest", () -> new LootChestBlock(BlockBehaviour.Properties.of().strength(50.0F).noOcclusion().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));

	// boss spawners
	public static final RegistryObject<Block, TheGatekeeperSpawnerBlock> THE_GATEKEEPER_SPAWNER = BLOCKS.register("the_gatekeeper_spawner", () -> new TheGatekeeperSpawnerBlock(BlockBehaviour.Properties.of().strength(50.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, StarlightGolemSpawnerBlock> STARLIGHT_GOLEM_SPAWNER = BLOCKS.register("starlight_golem_spawner", () -> new StarlightGolemSpawnerBlock(BlockBehaviour.Properties.of().strength(50.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, PermafrostSpawnerBlock> PERMAFROST_SPAWNER = BLOCKS.register("permafrost_spawner", () -> new PermafrostSpawnerBlock(BlockBehaviour.Properties.of().strength(50.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, LunarMonstrositySpawnerBlock> LUNAR_MONSTROSITY_SPAWNER = BLOCKS.register("lunar_monstrosity_spawner", () -> new LunarMonstrositySpawnerBlock(BlockBehaviour.Properties.of().strength(50.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block, SolarEggBlock> SOLAR_EGG = BLOCKS.register("solar_egg", () -> new SolarEggBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noOcclusion().mapColor(MapColor.COLOR_PURPLE)));

	// starlight golem
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> GOLEM_STEEL_BLOCK = BLOCKS.register("golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> WAXED_GOLEM_STEEL_BLOCK = BLOCKS.register("waxed_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> OXIDIZED_GOLEM_STEEL_BLOCK = BLOCKS.register("oxidized_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> GOLEM_STEEL_SLAB = BLOCKS.register("golem_steel_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> WAXED_GOLEM_STEEL_SLAB = BLOCKS.register("waxed_golem_steel_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> OXIDIZED_GOLEM_STEEL_SLAB = BLOCKS.register("oxidized_golem_steel_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> GOLEM_STEEL_STAIRS = BLOCKS.register("golem_steel_stairs", () -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> WAXED_GOLEM_STEEL_STAIRS = BLOCKS.register("waxed_golem_steel_stairs", () -> new WeatheringGolemSteelStairBlock(WAXED_GOLEM_STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_CUT_COPPER_STAIRS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> OXIDIZED_GOLEM_STEEL_STAIRS = BLOCKS.register("oxidized_golem_steel_stairs", () -> new WeatheringGolemSteelStairBlock(OXIDIZED_GOLEM_STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> GOLEM_STEEL_TILES = BLOCKS.register("golem_steel_tiles", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> WAXED_GOLEM_STEEL_TILES = BLOCKS.register("waxed_golem_steel_tiles", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> OXIDIZED_GOLEM_STEEL_TILES = BLOCKS.register("oxidized_golem_steel_tiles", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> GOLEM_STEEL_TILE_SLAB = BLOCKS.register("golem_steel_tile_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> WAXED_GOLEM_STEEL_TILE_SLAB = BLOCKS.register("waxed_golem_steel_tile_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> OXIDIZED_GOLEM_STEEL_TILE_SLAB = BLOCKS.register("oxidized_golem_steel_tile_slab", () -> new WeatheringGolemSteelSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> GOLEM_STEEL_TILE_STAIRS = BLOCKS.register("golem_steel_tile_stairs", () -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> WAXED_GOLEM_STEEL_TILE_STAIRS = BLOCKS.register("waxed_golem_steel_tile_stairs", () -> new WeatheringGolemSteelStairBlock(WAXED_GOLEM_STEEL_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_CUT_COPPER_STAIRS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> OXIDIZED_GOLEM_STEEL_TILE_STAIRS = BLOCKS.register("oxidized_golem_steel_tile_stairs", () -> new WeatheringGolemSteelStairBlock(OXIDIZED_GOLEM_STEEL_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelGrateBlock> GOLEM_STEEL_GRATE = BLOCKS.register("golem_steel_grate", () -> new WeatheringGolemSteelGrateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelGrateBlock> WAXED_GOLEM_STEEL_GRATE = BLOCKS.register("waxed_golem_steel_grate", () -> new WeatheringGolemSteelGrateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_GRATE).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelGrateBlock> OXIDIZED_GOLEM_STEEL_GRATE = BLOCKS.register("oxidized_golem_steel_grate", () -> new WeatheringGolemSteelGrateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelPillarBlock> GOLEM_STEEL_PILLAR = BLOCKS.register("golem_steel_pillar", () -> new WeatheringGolemSteelPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 10)));
	public static final RegistryObject<Block, WeatheringGolemSteelPillarBlock> WAXED_GOLEM_STEEL_PILLAR = BLOCKS.register("waxed_golem_steel_pillar", () -> new WeatheringGolemSteelPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 10)));
	public static final RegistryObject<Block, WeatheringGolemSteelPillarBlock> OXIDIZED_GOLEM_STEEL_PILLAR = BLOCKS.register("oxidized_golem_steel_pillar", () -> new WeatheringGolemSteelPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 10)));
	public static final RegistryObject<Block, WeatheringGolemSteelBarsBlock> GOLEM_STEEL_BARS = BLOCKS.register("golem_steel_bars", () -> new WeatheringGolemSteelBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelBarsBlock> WAXED_GOLEM_STEEL_BARS = BLOCKS.register("waxed_golem_steel_bars", () -> new WeatheringGolemSteelBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelBarsBlock> OXIDIZED_GOLEM_STEEL_BARS = BLOCKS.register("oxidized_golem_steel_bars", () -> new WeatheringGolemSteelBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> CHISELED_GOLEM_STEEL_BLOCK = BLOCKS.register("chiseled_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> WAXED_CHISELED_GOLEM_STEEL_BLOCK = BLOCKS.register("waxed_chiseled_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK = BLOCKS.register("oxidized_chiseled_golem_steel_block", () -> new WeatheringGolemSteelFullBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, WeatheringGolemSteelJetBlock> GOLEM_STEEL_JET = BLOCKS.register("golem_steel_jet", () -> new WeatheringGolemSteelJetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelJetBlock> WAXED_GOLEM_STEEL_JET = BLOCKS.register("waxed_golem_steel_jet", () -> new WeatheringGolemSteelJetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, WeatheringGolemSteelJetBlock> OXIDIZED_GOLEM_STEEL_JET = BLOCKS.register("oxidized_golem_steel_jet", () -> new WeatheringGolemSteelJetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, CrateBlock> GOLEM_STEEL_CRATE = BLOCKS.register("golem_steel_crate", () -> new CrateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).mapColor(MapColor.COLOR_GRAY).strength(50.0F, 1200.0F)));
	public static final RegistryObject<Block, EnergyTransmitterBlock> ENERGY_TRANSMITTER = BLOCKS.register("energy_transmitter", () -> new EnergyTransmitterBlock(BlockBehaviour.Properties.of().instabreak().lightLevel(state -> state.getValue(EnergyTransmitterBlock.POWERED) ? 7 : 0).sound(SoundType.STONE).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block, AccumulatorBlock> ACCUMULATOR = BLOCKS.register("accumulator", () -> new AccumulatorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).lightLevel(state -> state.getValue(AccumulatorBlock.POWER)).isRedstoneConductor(ESBlocks::never)));
	public static final RegistryObject<Block, MechanicalSpawnerBlock> MECHANICAL_SPAWNER = BLOCKS.register("mechanical_spawner", () -> new MechanicalSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(50.0F, 1200.0F).mapColor(MapColor.COLOR_BROWN).noOcclusion().lightLevel(state -> state.getValue(MechanicalSpawnerBlock.POWER))));
	public static final RegistryObject<Block, AlloyFurnaceBlock> ALLOY_FURNACE = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).noOcclusion().mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, AlloyFurnaceBlock> WAXED_ALLOY_FURNACE = BLOCKS.register("waxed_alloy_furnace", () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).strength(4.0F, 1200.0F).noOcclusion().mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, AlloyFurnaceBlock> OXIDIZED_ALLOY_FURNACE = BLOCKS.register("oxidized_alloy_furnace", () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).strength(4.0F, 1200.0F).noOcclusion().mapColor(MapColor.COLOR_GRAY)));
	public static final RegistryObject<Block, EnergyBlock> ENERGY_BLOCK = BLOCKS.register("energy_block", () -> new EnergyBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).pushReaction(PushReaction.IGNORE).mapColor(MapColor.COLOR_LIGHT_BLUE)));

	// lunar monstrosity
	public static final RegistryObject<Block, ShadegrieveBlock> SHADEGRIEVE = BLOCKS.register("shadegrieve", () -> new ShadegrieveBlock(false, BlockBehaviour.Properties.of().strength(25F).sound(SoundType.AZALEA_LEAVES).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, ShadegrieveBlock> BLOOMING_SHADEGRIEVE = BLOCKS.register("blooming_shadegrieve", () -> new ShadegrieveBlock(true, BlockBehaviour.Properties.of().strength(25F).sound(SoundType.AZALEA_LEAVES).mapColor(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block, LunarVineBlock> LUNAR_VINE = BLOCKS.register("lunar_vine", () -> new LunarVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)));
	public static final RegistryObject<Block, Block> LUNAR_MOSAIC = BLOCKS.register("lunar_mosaic", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, SlabBlock> LUNAR_MOSAIC_SLAB = BLOCKS.register("lunar_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, StairBlock> LUNAR_MOSAIC_STAIRS = BLOCKS.register("lunar_mosaic_stairs", () -> new StairBlock(LUNAR_MOSAIC.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, FenceBlock> LUNAR_MOSAIC_FENCE = BLOCKS.register("lunar_mosaic_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, FenceGateBlock> LUNAR_MOSAIC_FENCE_GATE = BLOCKS.register("lunar_mosaic_fence_gate", () -> new FenceGateBlock(ESWoodTypes.LUNAR_MOSAIC, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLUE)));
	public static final RegistryObject<Block, CarpetBlock> LUNAR_MAT = BLOCKS.register("lunar_mat", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_BLUE)));

	// solar creeper
	public static final RegistryObject<Block, TransparentBlock> DUSK_GLASS = BLOCKS.register("dusk_glass", () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).lightLevel(state -> 12)));
	public static final RegistryObject<Block, DuskLightBlock> DUSK_LIGHT = BLOCKS.register("dusk_light", () -> new DuskLightBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(50.0F, 1200.0F).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, ReinforcedDuskLightBlock> REINFORCED_DUSK_LIGHT = BLOCKS.register("reinforced_dusk_light", () -> new ReinforcedDuskLightBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(50.0F, 1200.0F).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DuskEmitterBlock> DUSK_EMITTER = BLOCKS.register("dusk_emitter", () -> new DuskEmitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(50.0F, 1200.0F).mapColor(MapColor.COLOR_BROWN).lightLevel(state -> 15)));
	public static final RegistryObject<Block, DuskLockboxBlock> DUSK_LOCKBOX = BLOCKS.register("dusk_lockbox", () -> new DuskLockboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(50.0F, 1200.0F).mapColor(MapColor.COLOR_BROWN).lightLevel(state -> 15)));
	public static final RegistryObject<Block, FlareSpawnerBlock> FLARE_SPAWNER = BLOCKS.register("flare_spawner", () -> new FlareSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(50.0F, 1200.0F).mapColor(MapColor.COLOR_BROWN).noOcclusion().lightLevel(state -> state.getValue(FlareSpawnerBlock.LIT) ? 15 : 0)));
	public static final RegistryObject<Block, EclipseCoreBlock> ECLIPSE_CORE = BLOCKS.register("eclipse_core", () -> new EclipseCoreBlock(BlockBehaviour.Properties.of().strength(-1F).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 15)));

	// doomeden
	public static final RegistryObject<Block, TorchBlock> DOOMED_TORCH = BLOCKS.register("doomed_torch", () -> new TorchBlock(ParticleTypes.FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
	public static final RegistryObject<Block, WallTorchBlock> WALL_DOOMED_TORCH = BLOCKS.register("wall_doomed_torch", () -> new WallTorchBlock(ParticleTypes.FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)));
	public static final RegistryObject<Block, DoomedenRedstoneTorchBlock> DOOMED_REDSTONE_TORCH = BLOCKS.register("doomed_redstone_torch", () -> new DoomedenRedstoneTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH)));
	public static final RegistryObject<Block, DoomedenRedstoneWallTorchBlock> WALL_DOOMED_REDSTONE_TORCH = BLOCKS.register("wall_doomed_redstone_torch", () -> new DoomedenRedstoneWallTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_WALL_TORCH)));
	public static final RegistryObject<Block, Block> DOOMEDEN_BRICKS = BLOCKS.register("doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, SlabBlock> DOOMEDEN_BRICK_SLAB = BLOCKS.register("doomeden_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, StairBlock> DOOMEDEN_BRICK_STAIRS = BLOCKS.register("doomeden_brick_stairs", () -> new StairBlock(DOOMEDEN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, WallBlock> DOOMEDEN_BRICK_WALL = BLOCKS.register("doomeden_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, Block> POLISHED_DOOMEDEN_BRICKS = BLOCKS.register("polished_doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, SlabBlock> POLISHED_DOOMEDEN_BRICK_SLAB = BLOCKS.register("polished_doomeden_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, StairBlock> POLISHED_DOOMEDEN_BRICK_STAIRS = BLOCKS.register("polished_doomeden_brick_stairs", () -> new StairBlock(POLISHED_DOOMEDEN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, WallBlock> POLISHED_DOOMEDEN_BRICK_WALL = BLOCKS.register("polished_doomeden_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, Block> DOOMEDEN_TILES = BLOCKS.register("doomeden_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, SlabBlock> DOOMEDEN_TILE_SLAB = BLOCKS.register("doomeden_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, StairBlock> DOOMEDEN_TILE_STAIRS = BLOCKS.register("doomeden_tile_stairs", () -> new StairBlock(DOOMEDEN_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, WallBlock> DOOMEDEN_TILE_WALL = BLOCKS.register("doomeden_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, Block> CHISELED_POLISHED_DOOMEDEN_BRICKS = BLOCKS.register("chiseled_polished_doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, Block> CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS = BLOCKS.register("charged_chiseled_polished_doomeden_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).lightLevel(state -> 15).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, RedstoneLampBlock> DOOMEDEN_LIGHT = BLOCKS.register("doomeden_light", () -> new RedstoneLampBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, DoomedenKeyholeBlock> DOOMEDEN_KEYHOLE = BLOCKS.register("doomeden_keyhole", () -> new DoomedenKeyholeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block, RedstoneDoomedenKeyholeBlock> REDSTONE_DOOMEDEN_KEYHOLE = BLOCKS.register("redstone_doomeden_keyhole", () -> new RedstoneDoomedenKeyholeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_GREEN)));

	// magic
	public static final RegistryObject<Block, StellarRackBlock> STELLAR_RACK = BLOCKS.register("stellar_rack", () -> new StellarRackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN).lightLevel(state -> 12)));
	public static final RegistryObject<Block, EnchantedGrimstoneBricksBlock> ENCHANTED_GRIMSTONE_BRICKS = BLOCKS.register("enchanted_grimstone_bricks", () -> new EnchantedGrimstoneBricksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)));
	public static final RegistryObject<Block, ESPortalBlock> STARLIGHT_PORTAL = BLOCKS.register("starlight_portal", () -> new ESPortalBlock(BlockBehaviour.Properties.of().strength(-1F).noCollission().lightLevel(state -> 10)));

	private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return false;
	}

	public static void loadClass() {
	}
}
