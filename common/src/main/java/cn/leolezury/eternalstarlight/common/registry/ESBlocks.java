package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.*;
import cn.leolezury.eternalstarlight.common.block.spawner.LunarMonstrositySpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.StarlightGolemSpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.TangledHatredSpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.TheGatekeeperSpawnerBlock;
import cn.leolezury.eternalstarlight.common.data.ESConfiguredFeatures;
import cn.leolezury.eternalstarlight.common.data.ESPlacedFeatures;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Optional;
import java.util.function.Function;

public class ESBlocks {
	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, EternalStarlight.ID);

	public static final RegistryObject<Block, BerriesVinesBlock> BERRIES_VINES = register("berries_vines", BerriesVinesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, BerriesVinesPlantBlock> BERRIES_VINES_PLANT = register("berries_vines_plant", BerriesVinesPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, CaveMossBlock> CAVE_MOSS = register("cave_moss", CaveMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 7).mapColor(MapColor.PLANT));
	public static final RegistryObject<Block, CaveMossPlantBlock> CAVE_MOSS_PLANT = register("cave_moss_plant", CaveMossPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 7).mapColor(MapColor.PLANT));
	public static final RegistryObject<Block, CaveMossVeinBlock> CAVE_MOSS_VEIN = register("cave_moss_vein", CaveMossVeinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(state -> 7).mapColor(MapColor.PLANT));
	public static final RegistryObject<Block, AbyssalKelpBlock> ABYSSAL_KELP = register("abyssal_kelp", AbyssalKelpBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.KELP).lightLevel(CaveVines.emission(14)));
	public static final RegistryObject<Block, AbyssalKelpPlantBlock> ABYSSAL_KELP_PLANT = register("abyssal_kelp_plant", AbyssalKelpPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT).lightLevel(CaveVines.emission(14)));
	public static final RegistryObject<Block, OrbfloraBlock> ORBFLORA = register("orbflora", OrbfloraBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.KELP));
	public static final RegistryObject<Block, OrbfloraPlantBlock> ORBFLORA_PLANT = register("orbflora_plant", OrbfloraPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT));
	public static final RegistryObject<Block, Block> ORBFLORA_LIGHT = register("orbflora_light", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OCHRE_FROGLIGHT));
	public static final RegistryObject<Block, DirectionalBudBlock> RED_STARLIGHT_CRYSTAL_CLUSTER = register("red_starlight_crystal_cluster", DirectionalBudBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10).sound(SoundType.AMETHYST));
	public static final RegistryObject<Block, DirectionalBudBlock> BLUE_STARLIGHT_CRYSTAL_CLUSTER = register("blue_starlight_crystal_cluster", DirectionalBudBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10).sound(SoundType.AMETHYST));
	public static final RegistryObject<Block, Block> RED_STARLIGHT_CRYSTAL_BLOCK = register("red_starlight_crystal_block", Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).requiresCorrectToolForDrops().lightLevel(state -> 10).sound(SoundType.AMETHYST));
	public static final RegistryObject<Block, Block> BLUE_STARLIGHT_CRYSTAL_BLOCK = register("blue_starlight_crystal_block", Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).requiresCorrectToolForDrops().lightLevel(state -> 10).sound(SoundType.AMETHYST));
	public static final RegistryObject<Block, CarpetBlock> RED_CRYSTAL_MOSS_CARPET = register("red_crystal_moss_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.MOSS_CARPET).lightLevel(state -> 10));
	public static final RegistryObject<Block, CarpetBlock> BLUE_CRYSTAL_MOSS_CARPET = register("blue_crystal_moss_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.1F).sound(SoundType.MOSS_CARPET).lightLevel(state -> 10));

	// coral
	public static final RegistryObject<Block, JinglingPickleBlock> JINGLING_PICKLE = register("jingling_pickle", JinglingPickleBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> JinglingPickleBlock.isDead(state) ? 0 : 3).sound(SoundType.SLIME_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY));
	public static final RegistryObject<Block, BaseCoralPlantBlock> DEAD_TENTACLES_CORAL = register("dead_tentacles_coral", BaseCoralPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL));
	public static final RegistryObject<Block, CoralPlantBlock> TENTACLES_CORAL = register("tentacles_coral", properties -> new CoralPlantBlock(DEAD_TENTACLES_CORAL.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, BaseCoralFanBlock> DEAD_TENTACLES_CORAL_FAN = register("dead_tentacles_coral_fan", BaseCoralFanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN));
	public static final RegistryObject<Block, CoralFanBlock> TENTACLES_CORAL_FAN = register("tentacles_coral_fan", properties -> new CoralFanBlock(DEAD_TENTACLES_CORAL_FAN.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, BaseCoralWallFanBlock> DEAD_TENTACLES_CORAL_WALL_FAN = registerWallVariant("dead_tentacles_coral_wall_fan", DEAD_TENTACLES_CORAL_FAN, BaseCoralWallFanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN));
	public static final RegistryObject<Block, CoralWallFanBlock> TENTACLES_CORAL_WALL_FAN = registerWallVariant("tentacles_coral_wall_fan", TENTACLES_CORAL_FAN, properties -> new CoralWallFanBlock(DEAD_TENTACLES_CORAL_WALL_FAN.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, Block> DEAD_TENTACLES_CORAL_BLOCK = register("dead_tentacles_coral_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK));
	public static final RegistryObject<Block, CoralBlock> TENTACLES_CORAL_BLOCK = register("tentacles_coral_block", properties -> new CoralBlock(DEAD_TENTACLES_CORAL_BLOCK.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, BaseCoralPlantBlock> DEAD_GOLDEN_CORAL = register("dead_golden_coral", BaseCoralPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL));
	public static final RegistryObject<Block, CoralPlantBlock> GOLDEN_CORAL = register("golden_coral", properties -> new CoralPlantBlock(DEAD_GOLDEN_CORAL.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, BaseCoralFanBlock> DEAD_GOLDEN_CORAL_FAN = register("dead_golden_coral_fan", BaseCoralFanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN));
	public static final RegistryObject<Block, CoralFanBlock> GOLDEN_CORAL_FAN = register("golden_coral_fan", properties -> new CoralFanBlock(DEAD_GOLDEN_CORAL_FAN.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, BaseCoralWallFanBlock> DEAD_GOLDEN_CORAL_WALL_FAN = registerWallVariant("dead_golden_coral_wall_fan", DEAD_GOLDEN_CORAL_FAN, BaseCoralWallFanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN));
	public static final RegistryObject<Block, CoralWallFanBlock> GOLDEN_CORAL_WALL_FAN = registerWallVariant("golden_coral_wall_fan", GOLDEN_CORAL_FAN, properties -> new CoralWallFanBlock(DEAD_GOLDEN_CORAL_WALL_FAN.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, Block> DEAD_GOLDEN_CORAL_BLOCK = register("dead_golden_coral_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK));
	public static final RegistryObject<Block, CoralBlock> GOLDEN_CORAL_BLOCK = register("golden_coral_block", properties -> new CoralBlock(DEAD_GOLDEN_CORAL_BLOCK.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, BaseCoralPlantBlock> DEAD_CRYSTALLUM_CORAL = register("dead_crystallum_coral", BaseCoralPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL));
	public static final RegistryObject<Block, CoralPlantBlock> CRYSTALLUM_CORAL = register("crystallum_coral", properties -> new CoralPlantBlock(DEAD_CRYSTALLUM_CORAL.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL).mapColor(MapColor.COLOR_CYAN));
	public static final RegistryObject<Block, BaseCoralFanBlock> DEAD_CRYSTALLUM_CORAL_FAN = register("dead_crystallum_coral_fan", BaseCoralFanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_FAN));
	public static final RegistryObject<Block, CoralFanBlock> CRYSTALLUM_CORAL_FAN = register("crystallum_coral_fan", properties -> new CoralFanBlock(DEAD_CRYSTALLUM_CORAL_FAN.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN).mapColor(MapColor.COLOR_CYAN));
	public static final RegistryObject<Block, BaseCoralWallFanBlock> DEAD_CRYSTALLUM_CORAL_WALL_FAN = registerWallVariant("dead_crystallum_coral_wall_fan", DEAD_CRYSTALLUM_CORAL_FAN, BaseCoralWallFanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN));
	public static final RegistryObject<Block, CoralWallFanBlock> CRYSTALLUM_CORAL_WALL_FAN = registerWallVariant("crystallum_coral_wall_fan", CRYSTALLUM_CORAL_FAN, properties -> new CoralWallFanBlock(DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_WALL_FAN).mapColor(MapColor.COLOR_CYAN));
	public static final RegistryObject<Block, Block> DEAD_CRYSTALLUM_CORAL_BLOCK = register("dead_crystallum_coral_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BRAIN_CORAL_BLOCK));
	public static final RegistryObject<Block, CoralBlock> CRYSTALLUM_CORAL_BLOCK = register("crystallum_coral_block", properties -> new CoralBlock(DEAD_CRYSTALLUM_CORAL_BLOCK.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK).mapColor(MapColor.COLOR_CYAN));

	// abyssal plant
	public static final RegistryObject<Block, VelvetumossBlock> VELVETUMOSS = register("velvetumoss", VelvetumossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).randomTicks().sound(SoundType.SLIME_BLOCK));
	public static final RegistryObject<Block, VelvetumossVilliBlock> VELVETUMOSS_VILLI = register("velvetumoss_villi", properties -> new VelvetumossVilliBlock(VELVETUMOSS.asHolder(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).noCollission().randomTicks());
	public static final RegistryObject<Block, RedVelvetumossBlock> RED_VELVETUMOSS = register("red_velvetumoss", RedVelvetumossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).randomTicks().mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, VelvetumossVilliBlock> RED_VELVETUMOSS_VILLI = register("red_velvetumoss_villi", properties -> new VelvetumossVilliBlock(RED_VELVETUMOSS.asHolder(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).noCollission().randomTicks().mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, AquaticFlowerBlock> RED_VELVETUMOSS_FLOWER = register("red_velvetumoss_flower", properties -> new AquaticFlowerBlock(MobEffects.WATER_BREATHING, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_RED));

	// lunar wood
	public static final RegistryObject<Block, LeavesBlock> LUNAR_LEAVES = register("lunar_leaves",
		LeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, RotatedPillarBlock> LUNAR_LOG = register("lunar_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> LUNAR_WOOD = register("lunar_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> LUNAR_PLANKS = register("lunar_planks",
		Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_LUNAR_LOG = register("stripped_lunar_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_LUNAR_WOOD = register("stripped_lunar_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, DoorBlock> LUNAR_DOOR = register("lunar_door",
		properties -> new DoorBlock(ESWoodTypes.LUNAR_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, TrapDoorBlock> LUNAR_TRAPDOOR = register("lunar_trapdoor",
		properties -> new TrapDoorBlock(ESWoodTypes.LUNAR_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, PressurePlateBlock> LUNAR_PRESSURE_PLATE = register("lunar_pressure_plate",
		properties -> new PressurePlateBlock(ESWoodTypes.LUNAR_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, ButtonBlock> LUNAR_BUTTON = register("lunar_button",
		properties -> new ButtonBlock(ESWoodTypes.LUNAR_SET, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, FenceBlock> LUNAR_FENCE = register("lunar_fence",
		FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, FenceGateBlock> LUNAR_FENCE_GATE = register("lunar_fence_gate",
		properties -> new FenceGateBlock(ESWoodTypes.LUNAR, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> LUNAR_SLAB = register("lunar_slab",
		SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> LUNAR_STAIRS = register("lunar_stairs",
		properties -> new StairBlock(LUNAR_PLANKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StandingSignBlock> LUNAR_SIGN = register("lunar_sign",
		properties -> new StandingSignBlock(ESWoodTypes.LUNAR, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallSignBlock> LUNAR_WALL_SIGN = registerWallVariant("lunar_wall_sign", LUNAR_SIGN,
		properties -> new WallSignBlock(ESWoodTypes.LUNAR, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, CeilingHangingSignBlock> LUNAR_HANGING_SIGN = register("lunar_hanging_sign",
		properties -> new CeilingHangingSignBlock(ESWoodTypes.LUNAR, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallHangingSignBlock> LUNAR_WALL_HANGING_SIGN = register("lunar_wall_hanging_sign",
		properties -> new WallHangingSignBlock(ESWoodTypes.LUNAR, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SaplingBlock> LUNAR_SAPLING = register("lunar_sapling", properties -> new SaplingBlock(new TreeGrower("lunar", 0.2f, Optional.empty(), Optional.empty(), Optional.of(ESConfiguredFeatures.LUNAR), Optional.of(ESConfiguredFeatures.LUNAR_HUGE), Optional.empty(), Optional.empty()), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_LUNAR_SAPLING = register("potted_lunar_sapling", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUNAR_SAPLING, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE));

	// lunar extras: desert
	public static final RegistryObject<Block, RotatedPillarBlock> DEAD_LUNAR_LOG = register("dead_lunar_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> RED_CRYSTALLIZED_LUNAR_LOG = register("red_crystallized_lunar_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> BLUE_CRYSTALLIZED_LUNAR_LOG = register("blue_crystallized_lunar_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(4.0F).mapColor(MapColor.COLOR_BLACK));

	// northland wood
	public static final RegistryObject<Block, SnowyLeavesBlock> NORTHLAND_LEAVES = register("northland_leaves",
		SnowyLeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, RotatedPillarBlock> NORTHLAND_LOG = register("northland_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, RotatedPillarBlock> NORTHLAND_WOOD = register("northland_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, Block> NORTHLAND_PLANKS = register("northland_planks",
		Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_NORTHLAND_LOG = register("stripped_northland_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_NORTHLAND_WOOD = register("stripped_northland_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, DoorBlock> NORTHLAND_DOOR = register("northland_door",
		properties -> new DoorBlock(ESWoodTypes.NORTHLAND_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, TrapDoorBlock> NORTHLAND_TRAPDOOR = register("northland_trapdoor",
		properties -> new TrapDoorBlock(ESWoodTypes.NORTHLAND_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, PressurePlateBlock> NORTHLAND_PRESSURE_PLATE = register("northland_pressure_plate",
		properties -> new PressurePlateBlock(ESWoodTypes.NORTHLAND_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, ButtonBlock> NORTHLAND_BUTTON = register("northland_button",
		properties -> new ButtonBlock(ESWoodTypes.NORTHLAND_SET, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, FenceBlock> NORTHLAND_FENCE = register("northland_fence",
		FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, FenceGateBlock> NORTHLAND_FENCE_GATE = register("northland_fence_gate",
		properties -> new FenceGateBlock(ESWoodTypes.NORTHLAND, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, SlabBlock> NORTHLAND_SLAB = register("northland_slab",
		SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, StairBlock> NORTHLAND_STAIRS = register("northland_stairs",
		properties -> new StairBlock(NORTHLAND_PLANKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, StandingSignBlock> NORTHLAND_SIGN = register("northland_sign",
		properties -> new StandingSignBlock(ESWoodTypes.NORTHLAND, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, WallSignBlock> NORTHLAND_WALL_SIGN = registerWallVariant("northland_wall_sign", NORTHLAND_SIGN,
		properties -> new WallSignBlock(ESWoodTypes.NORTHLAND, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, CeilingHangingSignBlock> NORTHLAND_HANGING_SIGN = register("northland_hanging_sign",
		properties -> new CeilingHangingSignBlock(ESWoodTypes.NORTHLAND, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, WallHangingSignBlock> NORTHLAND_WALL_HANGING_SIGN = register("northland_wall_hanging_sign",
		properties -> new WallHangingSignBlock(ESWoodTypes.NORTHLAND, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, SaplingBlock> NORTHLAND_SAPLING = register("northland_sapling", properties -> new SaplingBlock(new TreeGrower("northland", Optional.of(ESConfiguredFeatures.NORTHLAND), Optional.empty(), Optional.empty()), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_NORTHLAND_SAPLING = register("potted_northland_sapling", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NORTHLAND_SAPLING, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE));

	// starlight mangrove wood
	public static final RegistryObject<Block, LeavesBlock> STARLIGHT_MANGROVE_LEAVES = register("starlight_mangrove_leaves",
		LeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
	public static final RegistryObject<Block, RotatedPillarBlock> STARLIGHT_MANGROVE_LOG = register("starlight_mangrove_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> STARLIGHT_MANGROVE_WOOD = register("starlight_mangrove_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, Block> STARLIGHT_MANGROVE_PLANKS = register("starlight_mangrove_planks",
		Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_STARLIGHT_MANGROVE_LOG = register("stripped_starlight_mangrove_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_STARLIGHT_MANGROVE_WOOD = register("stripped_starlight_mangrove_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, DoorBlock> STARLIGHT_MANGROVE_DOOR = register("starlight_mangrove_door",
		properties -> new DoorBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, TrapDoorBlock> STARLIGHT_MANGROVE_TRAPDOOR = register("starlight_mangrove_trapdoor",
		properties -> new TrapDoorBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, PressurePlateBlock> STARLIGHT_MANGROVE_PRESSURE_PLATE = register("starlight_mangrove_pressure_plate",
		properties -> new PressurePlateBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, ButtonBlock> STARLIGHT_MANGROVE_BUTTON = register("starlight_mangrove_button",
		properties -> new ButtonBlock(ESWoodTypes.STARLIGHT_MANGROVE_SET, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FenceBlock> STARLIGHT_MANGROVE_FENCE = register("starlight_mangrove_fence",
		FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FenceGateBlock> STARLIGHT_MANGROVE_FENCE_GATE = register("starlight_mangrove_fence_gate",
		properties -> new FenceGateBlock(ESWoodTypes.STARLIGHT_MANGROVE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, SlabBlock> STARLIGHT_MANGROVE_SLAB = register("starlight_mangrove_slab",
		SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, StairBlock> STARLIGHT_MANGROVE_STAIRS = register("starlight_mangrove_stairs",
		properties -> new StairBlock(STARLIGHT_MANGROVE_PLANKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, StandingSignBlock> STARLIGHT_MANGROVE_SIGN = register("starlight_mangrove_sign",
		properties -> new StandingSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, WallSignBlock> STARLIGHT_MANGROVE_WALL_SIGN = registerWallVariant("starlight_mangrove_wall_sign", STARLIGHT_MANGROVE_SIGN,
		properties -> new WallSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, CeilingHangingSignBlock> STARLIGHT_MANGROVE_HANGING_SIGN = register("starlight_mangrove_hanging_sign",
		properties -> new CeilingHangingSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, WallHangingSignBlock> STARLIGHT_MANGROVE_WALL_HANGING_SIGN = register("starlight_mangrove_wall_hanging_sign",
		properties -> new WallHangingSignBlock(ESWoodTypes.STARLIGHT_MANGROVE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, SaplingBlock> STARLIGHT_MANGROVE_SAPLING = register("starlight_mangrove_sapling", properties -> new SaplingBlock(new TreeGrower("starlight_mangrove", Optional.empty(), Optional.of(ESConfiguredFeatures.STARLIGHT_MANGROVE), Optional.empty()), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_STARLIGHT_MANGROVE_SAPLING = register("potted_starlight_mangrove_sapling", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_MANGROVE_SAPLING, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, MangroveRootsBlock> STARLIGHT_MANGROVE_ROOTS = register("starlight_mangrove_roots", MangroveRootsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_ROOTS));
	public static final RegistryObject<Block, RotatedPillarBlock> MUDDY_STARLIGHT_MANGROVE_ROOTS = register("muddy_starlight_mangrove_roots", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUDDY_MANGROVE_ROOTS));

	// scarlet wood
	public static final RegistryObject<Block, ScarletLeavesBlock> SCARLET_LEAVES = register("scarlet_leaves",
		ScarletLeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, LayeredBlock> SCARLET_LEAVES_PILE = register("scarlet_leaves_pile",
		LayeredBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW).noCollission().sound(SoundType.CHERRY_LEAVES).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> SCARLET_LOG = register("scarlet_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> SCARLET_WOOD = register("scarlet_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, Block> SCARLET_PLANKS = register("scarlet_planks",
		Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_SCARLET_LOG = register("stripped_scarlet_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_SCARLET_WOOD = register("stripped_scarlet_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, DoorBlock> SCARLET_DOOR = register("scarlet_door",
		properties -> new DoorBlock(ESWoodTypes.SCARLET_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, TrapDoorBlock> SCARLET_TRAPDOOR = register("scarlet_trapdoor",
		properties -> new TrapDoorBlock(ESWoodTypes.SCARLET_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, PressurePlateBlock> SCARLET_PRESSURE_PLATE = register("scarlet_pressure_plate",
		properties -> new PressurePlateBlock(ESWoodTypes.SCARLET_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, ButtonBlock> SCARLET_BUTTON = register("scarlet_button",
		properties -> new ButtonBlock(ESWoodTypes.SCARLET_SET, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FenceBlock> SCARLET_FENCE = register("scarlet_fence",
		FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FenceGateBlock> SCARLET_FENCE_GATE = register("scarlet_fence_gate",
		properties -> new FenceGateBlock(ESWoodTypes.SCARLET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, SlabBlock> SCARLET_SLAB = register("scarlet_slab",
		SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, StairBlock> SCARLET_STAIRS = register("scarlet_stairs",
		properties -> new StairBlock(SCARLET_PLANKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, StandingSignBlock> SCARLET_SIGN = register("scarlet_sign",
		properties -> new StandingSignBlock(ESWoodTypes.SCARLET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, WallSignBlock> SCARLET_WALL_SIGN = registerWallVariant("scarlet_wall_sign", SCARLET_SIGN,
		properties -> new WallSignBlock(ESWoodTypes.SCARLET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, CeilingHangingSignBlock> SCARLET_HANGING_SIGN = register("scarlet_hanging_sign",
		properties -> new CeilingHangingSignBlock(ESWoodTypes.SCARLET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, WallHangingSignBlock> SCARLET_WALL_HANGING_SIGN = register("scarlet_wall_hanging_sign",
		properties -> new WallHangingSignBlock(ESWoodTypes.SCARLET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, SaplingBlock> SCARLET_SAPLING = register("scarlet_sapling", properties -> new SaplingBlock(new TreeGrower("scarlet", Optional.empty(), Optional.of(ESConfiguredFeatures.SCARLET), Optional.empty()), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SCARLET_SAPLING = register("potted_scarlet_sapling", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SCARLET_SAPLING, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_RED));

	// torreya wood
	public static final RegistryObject<Block, LeavesBlock> TORREYA_LEAVES = register("torreya_leaves",
		LeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, RotatedPillarBlock> TORREYA_LOG = register("torreya_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> TORREYA_WOOD = register("torreya_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> TORREYA_PLANKS = register("torreya_planks",
		Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_TORREYA_LOG = register("stripped_torreya_log",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, RotatedPillarBlock> STRIPPED_TORREYA_WOOD = register("stripped_torreya_wood",
		RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, DoorBlock> TORREYA_DOOR = register("torreya_door",
		properties -> new DoorBlock(ESWoodTypes.TORREYA_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, TrapDoorBlock> TORREYA_TRAPDOOR = register("torreya_trapdoor",
		properties -> new TrapDoorBlock(ESWoodTypes.TORREYA_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, PressurePlateBlock> TORREYA_PRESSURE_PLATE = register("torreya_pressure_plate",
		properties -> new PressurePlateBlock(ESWoodTypes.TORREYA_SET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, ButtonBlock> TORREYA_BUTTON = register("torreya_button",
		properties -> new ButtonBlock(ESWoodTypes.TORREYA_SET, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, FenceBlock> TORREYA_FENCE = register("torreya_fence",
		FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, FenceGateBlock> TORREYA_FENCE_GATE = register("torreya_fence_gate",
		properties -> new FenceGateBlock(ESWoodTypes.TORREYA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> TORREYA_SLAB = register("torreya_slab",
		SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> TORREYA_STAIRS = register("torreya_stairs",
		properties -> new StairBlock(TORREYA_PLANKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StandingSignBlock> TORREYA_SIGN = register("torreya_sign",
		properties -> new StandingSignBlock(ESWoodTypes.TORREYA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallSignBlock> TORREYA_WALL_SIGN = registerWallVariant("torreya_wall_sign", TORREYA_SIGN,
		properties -> new WallSignBlock(ESWoodTypes.TORREYA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, CeilingHangingSignBlock> TORREYA_HANGING_SIGN = register("torreya_hanging_sign",
		properties -> new CeilingHangingSignBlock(ESWoodTypes.TORREYA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallHangingSignBlock> TORREYA_WALL_HANGING_SIGN = register("torreya_wall_hanging_sign",
		properties -> new WallHangingSignBlock(ESWoodTypes.TORREYA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SaplingBlock> TORREYA_SAPLING = register("torreya_sapling", properties -> new SaplingBlock(new TreeGrower("torreya", Optional.empty(), Optional.of(ESConfiguredFeatures.TORREYA), Optional.empty()), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_TORREYA_SAPLING = register("potted_torreya_sapling", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TORREYA_SAPLING, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, TorreyaVinesBlock> TORREYA_VINES = register("torreya_vines", TorreyaVinesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 15).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, TorreyaVinesPlantBlock> TORREYA_VINES_PLANT = register("torreya_vines_plant", TorreyaVinesPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 0).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, TorreyaCampfireBlock> TORREYA_CAMPFIRE = register("torreya_campfire", properties -> new TorreyaCampfireBlock(true, 1, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE));

	// grimstone
	public static final RegistryObject<Block, Block> GRIMSTONE = register("grimstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
	public static final RegistryObject<Block, Block> COBBLED_GRIMSTONE = register("cobbled_grimstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
	public static final RegistryObject<Block, SlabBlock> COBBLED_GRIMSTONE_SLAB = register("cobbled_grimstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> COBBLED_GRIMSTONE_STAIRS = register("cobbled_grimstone_stairs", properties -> new StairBlock(COBBLED_GRIMSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> COBBLED_GRIMSTONE_WALL = register("cobbled_grimstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));
	public static final RegistryObject<Block, Block> GRIMSTONE_BRICKS = register("grimstone_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS));
	public static final RegistryObject<Block, SlabBlock> GRIMSTONE_BRICK_SLAB = register("grimstone_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> GRIMSTONE_BRICK_STAIRS = register("grimstone_brick_stairs", properties -> new StairBlock(GRIMSTONE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> GRIMSTONE_BRICK_WALL = register("grimstone_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));
	public static final RegistryObject<Block, Block> POLISHED_GRIMSTONE = register("polished_grimstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS));
	public static final RegistryObject<Block, SlabBlock> POLISHED_GRIMSTONE_SLAB = register("polished_grimstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> POLISHED_GRIMSTONE_STAIRS = register("polished_grimstone_stairs", properties -> new StairBlock(POLISHED_GRIMSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> POLISHED_GRIMSTONE_WALL = register("polished_grimstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));
	public static final RegistryObject<Block, Block> GRIMSTONE_TILES = register("grimstone_tiles", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS));
	public static final RegistryObject<Block, SlabBlock> GRIMSTONE_TILE_SLAB = register("grimstone_tile_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> GRIMSTONE_TILE_STAIRS = register("grimstone_tile_stairs", properties -> new StairBlock(GRIMSTONE_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> GRIMSTONE_TILE_WALL = register("grimstone_tile_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));
	public static final RegistryObject<Block, Block> CHISELED_GRIMSTONE = register("chiseled_grimstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS));
	public static final RegistryObject<Block, Block> GLOWING_GRIMSTONE = register("glowing_grimstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).lightLevel(state -> 10));

	// voidstone
	public static final RegistryObject<Block, Block> VOIDSTONE = register("voidstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> COBBLED_VOIDSTONE = register("cobbled_voidstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> COBBLED_VOIDSTONE_SLAB = register("cobbled_voidstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> COBBLED_VOIDSTONE_STAIRS = register("cobbled_voidstone_stairs", properties -> new StairBlock(COBBLED_VOIDSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallBlock> COBBLED_VOIDSTONE_WALL = register("cobbled_voidstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> VOIDSTONE_BRICKS = register("voidstone_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> VOIDSTONE_BRICK_SLAB = register("voidstone_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> VOIDSTONE_BRICK_STAIRS = register("voidstone_brick_stairs", properties -> new StairBlock(VOIDSTONE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallBlock> VOIDSTONE_BRICK_WALL = register("voidstone_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> POLISHED_VOIDSTONE = register("polished_voidstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> POLISHED_VOIDSTONE_SLAB = register("polished_voidstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> POLISHED_VOIDSTONE_STAIRS = register("polished_voidstone_stairs", properties -> new StairBlock(POLISHED_VOIDSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallBlock> POLISHED_VOIDSTONE_WALL = register("polished_voidstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> VOIDSTONE_TILES = register("voidstone_tiles", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> VOIDSTONE_TILE_SLAB = register("voidstone_tile_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> VOIDSTONE_TILE_STAIRS = register("voidstone_tile_stairs", properties -> new StairBlock(VOIDSTONE_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallBlock> VOIDSTONE_TILE_WALL = register("voidstone_tile_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> CHISELED_VOIDSTONE = register("chiseled_voidstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> GLOWING_VOIDSTONE = register("glowing_voidstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK).lightLevel(state -> 10));

	// eternal ice
	public static final RegistryObject<Block, Block> ETERNAL_ICE = register("eternal_ice", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).sound(SoundType.GLASS).mapColor(MapColor.ICE));
	public static final RegistryObject<Block, Block> ETERNAL_ICE_BRICKS = register("eternal_ice_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).sound(SoundType.GLASS).mapColor(MapColor.ICE));
	public static final RegistryObject<Block, SlabBlock> ETERNAL_ICE_BRICK_SLAB = register("eternal_ice_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).sound(SoundType.GLASS).mapColor(MapColor.ICE));
	public static final RegistryObject<Block, StairBlock> ETERNAL_ICE_BRICK_STAIRS = register("eternal_ice_brick_stairs", properties -> new StairBlock(ETERNAL_ICE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).sound(SoundType.GLASS).mapColor(MapColor.ICE));
	public static final RegistryObject<Block, WallBlock> ETERNAL_ICE_BRICK_WALL = register("eternal_ice_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).sound(SoundType.GLASS).mapColor(MapColor.ICE));
	public static final RegistryObject<Block, IcicleBlock> ICICLE = register("icicle", IcicleBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE).sound(SoundType.GLASS).mapColor(MapColor.ICE));
	public static final RegistryObject<Block, AshenSnowBlock> ASHEN_SNOW = register("ashen_snow", AshenSnowBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW).noCollission());

	// nebulaite
	public static final RegistryObject<Block, Block> NEBULAITE = register("nebulaite", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> NEBULAITE_BRICKS = register("nebulaite_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, SlabBlock> NEBULAITE_BRICK_SLAB = register("nebulaite_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StairBlock> NEBULAITE_BRICK_STAIRS = register("nebulaite_brick_stairs", properties -> new StairBlock(NEBULAITE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, WallBlock> NEBULAITE_BRICK_WALL = register("nebulaite_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, Block> CHISELED_NEBULAITE_BRICKS = register("chiseled_nebulaite_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE).strength(4F, 7F).mapColor(MapColor.COLOR_BLACK));

	// solar
	public static final RegistryObject<Block, AtalphaiteBlock> ATALPHAITE_BLOCK = register("atalphaite_block", AtalphaiteBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 10));
	public static final RegistryObject<Block, Block> BLAZING_ATALPHAITE_BLOCK = register("blazing_atalphaite_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 12));
	public static final RegistryObject<Block, Block> ATALPHAITE_LIGHT = register("atalphaite_light", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OCHRE_FROGLIGHT).sound(SoundType.STONE).lightLevel(state -> 15));
	public static final RegistryObject<Block, DropExperienceBlock> GRIMSTONE_ATALPHAITE_ORE = register("grimstone_atalphaite_ore", properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).lightLevel(state -> 8));
	public static final RegistryObject<Block, DropExperienceBlock> VOIDSTONE_ATALPHAITE_ORE = register("voidstone_atalphaite_ore", properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).lightLevel(state -> 4));
	public static final RegistryObject<Block, TransparentBlock> DUSK_GLASS = register("dusk_glass", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).lightLevel(state -> 12));
	public static final RegistryObject<Block, DuskLightBlock> DUSK_LIGHT = register("dusk_light", DuskLightBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).lightLevel(state -> 15));
	public static final RegistryObject<Block, EclipseCoreBlock> ECLIPSE_CORE = register("eclipse_core", EclipseCoreBlock::new, BlockBehaviour.Properties.of().strength(-1F).mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 15));
	public static final RegistryObject<Block, Block> RADIANITE = register("radianite", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, SlabBlock> RADIANITE_SLAB = register("radianite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, StairBlock> RADIANITE_STAIRS = register("radianite_stairs", properties -> new StairBlock(RADIANITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, WallBlock> RADIANITE_WALL = register("radianite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, RotatedPillarBlock> RADIANITE_PILLAR = register("radianite_pillar", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, Block> POLISHED_RADIANITE = register("polished_radianite", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, SlabBlock> POLISHED_RADIANITE_SLAB = register("polished_radianite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, StairBlock> POLISHED_RADIANITE_STAIRS = register("polished_radianite_stairs", properties -> new StairBlock(POLISHED_RADIANITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, WallBlock> POLISHED_RADIANITE_WALL = register("polished_radianite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, Block> RADIANITE_BRICKS = register("radianite_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, SlabBlock> RADIANITE_BRICK_SLAB = register("radianite_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, StairBlock> RADIANITE_BRICK_STAIRS = register("radianite_brick_stairs", properties -> new StairBlock(RADIANITE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, WallBlock> RADIANITE_BRICK_WALL = register("radianite_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, Block> CHISELED_RADIANITE = register("chiseled_radianite", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE));
	public static final RegistryObject<Block, Block> FLARE_BRICKS = register("flare_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, SlabBlock> FLARE_BRICK_SLAB = register("flare_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, StairBlock> FLARE_BRICK_STAIRS = register("flare_brick_stairs", properties -> new StairBlock(FLARE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, WallBlock> FLARE_BRICK_WALL = register("flare_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, Block> FLARE_TILES = register("flare_tiles", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, SlabBlock> FLARE_TILE_SLAB = register("flare_tile_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, StairBlock> FLARE_TILE_STAIRS = register("flare_tile_stairs", properties -> new StairBlock(FLARE_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, WallBlock> FLARE_TILE_WALL = register("flare_tile_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, RotatedPillarBlock> CHISELED_FLARE_PILLAR = register("chiseled_flare_pillar", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR).mapColor(MapColor.COLOR_BROWN));

	// stellagmite
	public static final RegistryObject<Block, StellagmiteBlock> STELLAGMITE = register("stellagmite", StellagmiteBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, StellagmiteSlabBlock> STELLAGMITE_SLAB = register("stellagmite_slab", StellagmiteSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, StellagmiteStairBlock> STELLAGMITE_STAIRS = register("stellagmite_stairs", properties -> new StellagmiteStairBlock(STELLAGMITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, StellagmiteWallBlock> STELLAGMITE_WALL = register("stellagmite_wall", StellagmiteWallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, StellagmiteBlock> MOLTEN_STELLAGMITE = register("molten_stellagmite", StellagmiteBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12));
	public static final RegistryObject<Block, StellagmiteSlabBlock> MOLTEN_STELLAGMITE_SLAB = register("molten_stellagmite_slab", StellagmiteSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12));
	public static final RegistryObject<Block, StellagmiteStairBlock> MOLTEN_STELLAGMITE_STAIRS = register("molten_stellagmite_stairs", properties -> new StellagmiteStairBlock(MOLTEN_STELLAGMITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12));
	public static final RegistryObject<Block, StellagmiteWallBlock> MOLTEN_STELLAGMITE_WALL = register("molten_stellagmite_wall", StellagmiteWallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE).lightLevel(state -> 12));
	public static final RegistryObject<Block, Block> POLISHED_STELLAGMITE = register("polished_stellagmite", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, SlabBlock> POLISHED_STELLAGMITE_SLAB = register("polished_stellagmite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, StairBlock> POLISHED_STELLAGMITE_STAIRS = register("polished_stellagmite_stairs", properties -> new StairBlock(POLISHED_STELLAGMITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block, WallBlock> POLISHED_STELLAGMITE_WALL = register("polished_stellagmite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE));

	// tooth of hunger
	public static final RegistryObject<Block, Block> TOOTH_OF_HUNGER_TILES = register("tooth_of_hunger_tiles", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, SlabBlock> TOOTH_OF_HUNGER_TILE_SLAB = register("tooth_of_hunger_tile_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, StairBlock> TOOTH_OF_HUNGER_TILE_STAIRS = register("tooth_of_hunger_tile_stairs", properties -> new StairBlock(TOOTH_OF_HUNGER_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, WallBlock> TOOTH_OF_HUNGER_TILE_WALL = register("tooth_of_hunger_tile_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, Block> CHISELED_TOOTH_OF_HUNGER_TILES = register("chiseled_tooth_of_hunger_tiles", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS).sound(SoundType.BONE_BLOCK).mapColor(MapColor.COLOR_BROWN));

	// the abyss
	public static final RegistryObject<Block, AbyssalFireBlock> ABYSSAL_FIRE = register("abyssal_fire", AbyssalFireBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 10));
	public static final RegistryObject<Block, Block> ABYSSLATE = register("abysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));
	public static final RegistryObject<Block, Block> POLISHED_ABYSSLATE = register("polished_abysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE));
	public static final RegistryObject<Block, SlabBlock> POLISHED_ABYSSLATE_SLAB = register("polished_abysslate_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB));
	public static final RegistryObject<Block, StairBlock> POLISHED_ABYSSLATE_STAIRS = register("polished_abysslate_stairs", properties -> new StairBlock(POLISHED_ABYSSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS));
	public static final RegistryObject<Block, WallBlock> POLISHED_ABYSSLATE_WALL = register("polished_abysslate_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL));
	public static final RegistryObject<Block, Block> POLISHED_ABYSSLATE_BRICKS = register("polished_abysslate_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS));
	public static final RegistryObject<Block, SlabBlock> POLISHED_ABYSSLATE_BRICK_SLAB = register("polished_abysslate_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> POLISHED_ABYSSLATE_BRICK_STAIRS = register("polished_abysslate_brick_stairs", properties -> new StairBlock(POLISHED_ABYSSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> POLISHED_ABYSSLATE_BRICK_WALL = register("polished_abysslate_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL));
	public static final RegistryObject<Block, Block> CHISELED_POLISHED_ABYSSLATE = register("chiseled_polished_abysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE));
	public static final RegistryObject<Block, MagmaBlock> ABYSSAL_MAGMA_BLOCK = register("abyssal_magma_block", MagmaBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.DEEPSLATE));
	public static final RegistryObject<Block, AbyssalGeyserBlock> ABYSSAL_GEYSER = register("abyssal_geyser", AbyssalGeyserBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));
	public static final RegistryObject<Block, Block> THERMABYSSLATE = register("thermabysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, Block> POLISHED_THERMABYSSLATE = register("polished_thermabysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, SlabBlock> POLISHED_THERMABYSSLATE_SLAB = register("polished_thermabysslate_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, StairBlock> POLISHED_THERMABYSSLATE_STAIRS = register("polished_thermabysslate_stairs", properties -> new StairBlock(POLISHED_THERMABYSSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, WallBlock> POLISHED_THERMABYSSLATE_WALL = register("polished_thermabysslate_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, Block> POLISHED_THERMABYSSLATE_BRICKS = register("polished_thermabysslate_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, SlabBlock> POLISHED_THERMABYSSLATE_BRICK_SLAB = register("polished_thermabysslate_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, StairBlock> POLISHED_THERMABYSSLATE_BRICK_STAIRS = register("polished_thermabysslate_brick_stairs", properties -> new StairBlock(POLISHED_THERMABYSSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, WallBlock> POLISHED_THERMABYSSLATE_BRICK_WALL = register("polished_thermabysslate_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, Block> CHISELED_POLISHED_THERMABYSSLATE = register("chiseled_polished_thermabysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE));
	public static final RegistryObject<Block, MagmaBlock> THERMABYSSAL_MAGMA_BLOCK = register("thermabyssal_magma_block", MagmaBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK));
	public static final RegistryObject<Block, AbyssalGeyserBlock> THERMABYSSAL_GEYSER = register("thermabyssal_geyser", AbyssalGeyserBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.NETHER));
	public static final RegistryObject<Block, Block> CRYOBYSSLATE = register("cryobysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));
	public static final RegistryObject<Block, Block> POLISHED_CRYOBYSSLATE = register("polished_cryobysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE));
	public static final RegistryObject<Block, SlabBlock> POLISHED_CRYOBYSSLATE_SLAB = register("polished_cryobysslate_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB));
	public static final RegistryObject<Block, StairBlock> POLISHED_CRYOBYSSLATE_STAIRS = register("polished_cryobysslate_stairs", properties -> new StairBlock(POLISHED_CRYOBYSSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS));
	public static final RegistryObject<Block, WallBlock> POLISHED_CRYOBYSSLATE_WALL = register("polished_cryobysslate_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL));
	public static final RegistryObject<Block, Block> POLISHED_CRYOBYSSLATE_BRICKS = register("polished_cryobysslate_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS));
	public static final RegistryObject<Block, SlabBlock> POLISHED_CRYOBYSSLATE_BRICK_SLAB = register("polished_cryobysslate_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> POLISHED_CRYOBYSSLATE_BRICK_STAIRS = register("polished_cryobysslate_brick_stairs", properties -> new StairBlock(POLISHED_CRYOBYSSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> POLISHED_CRYOBYSSLATE_BRICK_WALL = register("polished_cryobysslate_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL));
	public static final RegistryObject<Block, Block> CHISELED_POLISHED_CRYOBYSSLATE = register("chiseled_polished_cryobysslate", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE));
	public static final RegistryObject<Block, MagmaBlock> CRYOBYSSAL_MAGMA_BLOCK = register("cryobyssal_magma_block", MagmaBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.DEEPSLATE));
	public static final RegistryObject<Block, AbyssalGeyserBlock> CRYOBYSSAL_GEYSER = register("cryobyssal_geyser", AbyssalGeyserBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));

	// ether
	public static final RegistryObject<Block, Block> THIOQUARTZ_BLOCK = register("thioquartz_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.GLASS));
	public static final RegistryObject<Block, BuddingSulfurQuartzBlock> BUDDING_THIOQUARTZ = register("budding_thioquartz", BuddingSulfurQuartzBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BUDDING_AMETHYST).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.GLASS));
	public static final RegistryObject<Block, DirectionalBudBlock> THIOQUARTZ_CLUSTER = register("thioquartz_cluster", DirectionalBudBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.5F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 5).sound(SoundType.GLASS));
	public static final RegistryObject<Block, Block> TOXITE = register("toxite", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, SlabBlock> TOXITE_SLAB = register("toxite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, StairBlock> TOXITE_STAIRS = register("toxite_stairs", properties -> new StairBlock(TOXITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, WallBlock> TOXITE_WALL = register("toxite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, PolishedToxiteBlock> POLISHED_TOXITE = register("polished_toxite", PolishedToxiteBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, SlabBlock> POLISHED_TOXITE_SLAB = register("polished_toxite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, StairBlock> POLISHED_TOXITE_STAIRS = register("polished_toxite_stairs", properties -> new StairBlock(TOXITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block, WallBlock> POLISHED_TOXITE_WALL = register("polished_toxite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_GREEN));

	// mud
	public static final RegistryObject<Block, MudBlock> NIGHTFALL_MUD = register("nightfall_mud", MudBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD));
	public static final RegistryObject<Block, MudBlock> GLOWING_NIGHTFALL_MUD = register("glowing_nightfall_mud", MudBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).lightLevel(state -> 15));
	public static final RegistryObject<Block, Block> PACKED_NIGHTFALL_MUD = register("packed_nightfall_mud", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD));
	public static final RegistryObject<Block, Block> NIGHTFALL_MUD_BRICKS = register("nightfall_mud_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final RegistryObject<Block, SlabBlock> NIGHTFALL_MUD_BRICK_SLAB = register("nightfall_mud_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_SLAB));
	public static final RegistryObject<Block, StairBlock> NIGHTFALL_MUD_BRICK_STAIRS = register("nightfall_mud_brick_stairs", properties -> new StairBlock(NIGHTFALL_MUD_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_STAIRS));
	public static final RegistryObject<Block, WallBlock> NIGHTFALL_MUD_BRICK_WALL = register("nightfall_mud_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_WALL));

	// sand
	public static final RegistryObject<Block, ColoredFallingBlock> TWILIGHT_SAND = register("twilight_sand", properties -> new ColoredFallingBlock(new ColorRGBA(0x907e9b), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> TWILIGHT_SANDSTONE = register("twilight_sandstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, SlabBlock> TWILIGHT_SANDSTONE_SLAB = register("twilight_sandstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, StairBlock> TWILIGHT_SANDSTONE_STAIRS = register("twilight_sandstone_stairs", properties -> new StairBlock(TWILIGHT_SANDSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, WallBlock> TWILIGHT_SANDSTONE_WALL = register("twilight_sandstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> CUT_TWILIGHT_SANDSTONE = register("cut_twilight_sandstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, SlabBlock> CUT_TWILIGHT_SANDSTONE_SLAB = register("cut_twilight_sandstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, StairBlock> CUT_TWILIGHT_SANDSTONE_STAIRS = register("cut_twilight_sandstone_stairs", properties -> new StairBlock(CUT_TWILIGHT_SANDSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, WallBlock> CUT_TWILIGHT_SANDSTONE_WALL = register("cut_twilight_sandstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> CHISELED_TWILIGHT_SANDSTONE = register("chiseled_twilight_sandstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_SANDSTONE).mapColor(MapColor.COLOR_PURPLE));

	// gravel
	public static final RegistryObject<Block, ColoredFallingBlock> DUSTED_GRAVEL = register("dusted_gravel", properties -> new ColoredFallingBlock(new ColorRGBA(0x53415e), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> DUSTED_BRICKS = register("dusted_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, SlabBlock> DUSTED_BRICK_SLAB = register("dusted_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_SLAB).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, StairBlock> DUSTED_BRICK_STAIRS = register("dusted_brick_stairs", properties -> new StairBlock(DUSTED_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_STAIRS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, WallBlock> DUSTED_BRICK_WALL = register("dusted_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE_WALL).mapColor(MapColor.COLOR_PURPLE));

	// golem steel
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> GOLEM_STEEL_BLOCK = register("golem_steel_block", WeatheringGolemSteelFullBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> OXIDIZED_GOLEM_STEEL_BLOCK = register("oxidized_golem_steel_block", WeatheringGolemSteelFullBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> GOLEM_STEEL_SLAB = register("golem_steel_slab", WeatheringGolemSteelSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> OXIDIZED_GOLEM_STEEL_SLAB = register("oxidized_golem_steel_slab", WeatheringGolemSteelSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> GOLEM_STEEL_STAIRS = register("golem_steel_stairs", properties -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_BLOCK.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> OXIDIZED_GOLEM_STEEL_STAIRS = register("oxidized_golem_steel_stairs", properties -> new WeatheringGolemSteelStairBlock(OXIDIZED_GOLEM_STEEL_BLOCK.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> GOLEM_STEEL_TILES = register("golem_steel_tiles", WeatheringGolemSteelFullBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> OXIDIZED_GOLEM_STEEL_TILES = register("oxidized_golem_steel_tiles", WeatheringGolemSteelFullBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> GOLEM_STEEL_TILE_SLAB = register("golem_steel_tile_slab", WeatheringGolemSteelSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelSlabBlock> OXIDIZED_GOLEM_STEEL_TILE_SLAB = register("oxidized_golem_steel_tile_slab", WeatheringGolemSteelSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> GOLEM_STEEL_TILE_STAIRS = register("golem_steel_tile_stairs", properties -> new WeatheringGolemSteelStairBlock(GOLEM_STEEL_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelStairBlock> OXIDIZED_GOLEM_STEEL_TILE_STAIRS = register("oxidized_golem_steel_tile_stairs", properties -> new WeatheringGolemSteelStairBlock(OXIDIZED_GOLEM_STEEL_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelGrateBlock> GOLEM_STEEL_GRATE = register("golem_steel_grate", WeatheringGolemSteelGrateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelGrateBlock> OXIDIZED_GOLEM_STEEL_GRATE = register("oxidized_golem_steel_grate", WeatheringGolemSteelGrateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelPillarBlock> GOLEM_STEEL_PILLAR = register("golem_steel_pillar", WeatheringGolemSteelPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 10));
	public static final RegistryObject<Block, WeatheringGolemSteelPillarBlock> OXIDIZED_GOLEM_STEEL_PILLAR = register("oxidized_golem_steel_pillar", WeatheringGolemSteelPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 10));
	public static final RegistryObject<Block, WeatheringGolemSteelBarsBlock> GOLEM_STEEL_BARS = register("golem_steel_bars", WeatheringGolemSteelBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelBarsBlock> OXIDIZED_GOLEM_STEEL_BARS = register("oxidized_golem_steel_bars", WeatheringGolemSteelBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> CHISELED_GOLEM_STEEL_BLOCK = register("chiseled_golem_steel_block", WeatheringGolemSteelFullBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelFullBlock> OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK = register("oxidized_chiseled_golem_steel_block", WeatheringGolemSteelFullBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_GRAY));
	public static final RegistryObject<Block, WeatheringGolemSteelJetBlock> GOLEM_STEEL_JET = register("golem_steel_jet", WeatheringGolemSteelJetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, WeatheringGolemSteelJetBlock> OXIDIZED_GOLEM_STEEL_JET = register("oxidized_golem_steel_jet", WeatheringGolemSteelJetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_GRAY));

	// lunar monstrosity
	public static final RegistryObject<Block, ShadegrieveBlock> SHADEGRIEVE = register("shadegrieve", properties -> new ShadegrieveBlock(false, properties), BlockBehaviour.Properties.of().strength(25F).sound(SoundType.AZALEA_LEAVES).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, ShadegrieveBlock> BLOOMING_SHADEGRIEVE = register("blooming_shadegrieve", properties -> new ShadegrieveBlock(true, properties), BlockBehaviour.Properties.of().strength(25F).sound(SoundType.AZALEA_LEAVES).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, LunarVineBlock> LUNAR_VINE = register("lunar_vine", LunarVineBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VINE));
	public static final RegistryObject<Block, Block> LUNAR_MOSAIC = register("lunar_mosaic", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, SlabBlock> LUNAR_MOSAIC_SLAB = register("lunar_mosaic_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, StairBlock> LUNAR_MOSAIC_STAIRS = register("lunar_mosaic_stairs", properties -> new StairBlock(LUNAR_MOSAIC.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, FenceBlock> LUNAR_MOSAIC_FENCE = register("lunar_mosaic_fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, FenceGateBlock> LUNAR_MOSAIC_FENCE_GATE = register("lunar_mosaic_fence_gate", properties -> new FenceGateBlock(ESWoodTypes.LUNAR_MOSAIC, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, CarpetBlock> LUNAR_MAT = register("lunar_mat", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_BLUE));

	// doomeden
	public static final RegistryObject<Block, TorchBlock> DOOMED_TORCH = register("doomed_torch", properties -> new TorchBlock(ParticleTypes.FLAME, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH));
	public static final RegistryObject<Block, WallTorchBlock> WALL_DOOMED_TORCH = registerWallVariant("wall_doomed_torch", DOOMED_TORCH, properties -> new WallTorchBlock(ParticleTypes.FLAME, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH));
	public static final RegistryObject<Block, DoomedenRedstoneTorchBlock> DOOMED_REDSTONE_TORCH = register("doomed_redstone_torch", DoomedenRedstoneTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH));
	public static final RegistryObject<Block, DoomedenRedstoneWallTorchBlock> WALL_DOOMED_REDSTONE_TORCH = registerWallVariant("wall_doomed_redstone_torch", DOOMED_REDSTONE_TORCH, DoomedenRedstoneWallTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_WALL_TORCH));
	public static final RegistryObject<Block, Block> DOOMEDEN_BRICKS = register("doomeden_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, SlabBlock> DOOMEDEN_BRICK_SLAB = register("doomeden_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, StairBlock> DOOMEDEN_BRICK_STAIRS = register("doomeden_brick_stairs", properties -> new StairBlock(DOOMEDEN_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, WallBlock> DOOMEDEN_BRICK_WALL = register("doomeden_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, Block> POLISHED_DOOMEDEN_BRICKS = register("polished_doomeden_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, SlabBlock> POLISHED_DOOMEDEN_BRICK_SLAB = register("polished_doomeden_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, StairBlock> POLISHED_DOOMEDEN_BRICK_STAIRS = register("polished_doomeden_brick_stairs", properties -> new StairBlock(POLISHED_DOOMEDEN_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, WallBlock> POLISHED_DOOMEDEN_BRICK_WALL = register("polished_doomeden_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, Block> DOOMEDEN_TILES = register("doomeden_tiles", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, SlabBlock> DOOMEDEN_TILE_SLAB = register("doomeden_tile_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, StairBlock> DOOMEDEN_TILE_STAIRS = register("doomeden_tile_stairs", properties -> new StairBlock(GRIMSTONE_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, WallBlock> DOOMEDEN_TILE_WALL = register("doomeden_tile_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, Block> CHISELED_POLISHED_DOOMEDEN_BRICKS = register("chiseled_polished_doomeden_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, Block> CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS = register("charged_chiseled_polished_doomeden_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(state -> 15).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, RedstoneLampBlock> DOOMEDEN_LIGHT = register("doomeden_light", RedstoneLampBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, DoomedenKeyholeBlock> DOOMEDEN_KEYHOLE = register("doomeden_keyhole", DoomedenKeyholeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, RedstoneDoomedenKeyholeBlock> REDSTONE_DOOMEDEN_KEYHOLE = register("redstone_doomeden_keyhole", RedstoneDoomedenKeyholeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(MapColor.COLOR_GREEN));

	// common plant
	public static final RegistryObject<Block, FlowerBlock> STARLIGHT_FLOWER = register("starlight_flower", properties -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_STARLIGHT_FLOWER = register("potted_starlight_flower", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_FLOWER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerBlock> AUREATE_FLOWER = register("aureate_flower", properties -> new FlowerBlock(MobEffects.ABSORPTION, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_AUREATE_FLOWER = register("potted_aureate_flower", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AUREATE_FLOWER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, FlowerBlock> CONEBLOOM = register("conebloom", properties -> new FlowerBlock(MobEffects.SLOW_FALLING, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_CONEBLOOM = register("potted_conebloom", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CONEBLOOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, FlowerBlock> NIGHTFAN = register("nightfan", properties -> new FlowerBlock(MobEffects.SLOW_FALLING, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_NIGHTFAN = register("potted_nightfan", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, NIGHTFAN, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, FlowerBlock> PINK_ROSE = register("pink_rose", properties -> new FlowerBlock(MobEffects.DIG_SPEED, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_PINK_ROSE = register("potted_pink_rose", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_ROSE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, FlowerBlock> STARLIGHT_TORCHFLOWER = register("starlight_torchflower", properties -> new FlowerBlock(MobEffects.GLOWING, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PINK).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_STARLIGHT_TORCHFLOWER = register("potted_starlight_torchflower", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, STARLIGHT_TORCHFLOWER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PINK).lightLevel(state -> 15));
	public static final RegistryObject<Block, DoublePlantBlock> NIGHTFAN_BUSH = register("nightfan_bush", DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, DoublePlantBlock> PINK_ROSE_BUSH = register("pink_rose_bush", DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, ESShortBushBlock> NIGHT_SPROUTS = register("night_sprouts", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, ESShortBushBlock> SMALL_NIGHT_SPROUTS = register("small_night_sprouts", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, ESShortBushBlock> GLOWING_NIGHT_SPROUTS = register("glowing_night_sprouts", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> SMALL_GLOWING_NIGHT_SPROUTS = register("small_glowing_night_sprouts", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> LUNAR_GRASS = register("lunar_grass", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, ESShortBushBlock> GLOWING_LUNAR_GRASS = register("glowing_lunar_grass", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> CRESCENT_GRASS = register("crescent_grass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_CRESCENT_GRASS = register("potted_crescent_grass", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CRESCENT_GRASS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, ESShortBushBlock> GLOWING_CRESCENT_GRASS = register("glowing_crescent_grass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLOWING_CRESCENT_GRASS = register("potted_glowing_crescent_grass", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWING_CRESCENT_GRASS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> PARASOL_GRASS = register("parasol_grass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_PARASOL_GRASS = register("potted_parasol_grass", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PARASOL_GRASS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, ESShortBushBlock> GLOWING_PARASOL_GRASS = register("glowing_parasol_grass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLOWING_PARASOL_GRASS = register("potted_glowing_parasol_grass", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWING_PARASOL_GRASS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> LUNAR_BUSH = register("lunar_bush", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, ESShortBushBlock> GLOWING_LUNAR_BUSH = register("glowing_lunar_bush", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_CRESCENT_GRASS = register("tall_crescent_grass", DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_GLOWING_CRESCENT_GRASS = register("tall_glowing_crescent_grass", DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, DoublePlantOnSandBlock> LUNAR_REED = register("lunar_reed", DoublePlantOnSandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> WHISPERBLOOM = register("whisperbloom", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_WHISPERBLOOM = register("potted_whisperbloom", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHISPERBLOOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, ESShortBushBlock> GLADESPIKE = register("gladespike", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLADESPIKE = register("potted_gladespike", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLADESPIKE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, ESShortBushBlock> VIVIDSTALK = register("vividstalk", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_VIVIDSTALK = register("potted_vividstalk", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, VIVIDSTALK, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_GLADESPIKE = register("tall_gladespike", DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, MushroomBlock> GLOWING_MUSHROOM = register("glowing_mushroom", properties -> new MushroomBlock(ESConfiguredFeatures.HUGE_GLOWING_MUSHROOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GLOWING_MUSHROOM = register("potted_glowing_mushroom", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWING_MUSHROOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 15));
	public static final RegistryObject<Block, HugeMushroomBlock> GLOWING_MUSHROOM_BLOCK = register("glowing_mushroom_block", HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.2F).sound(SoundType.WOOD).lightLevel(state -> 15));
	public static final RegistryObject<Block, HugeMushroomBlock> GLOWING_MUSHROOM_STEM = register("glowing_mushroom_stem", HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.COLOR_BLUE));
	public static final RegistryObject<Block, BouldershroomBlock> BOULDERSHROOM = register("bouldershroom", BouldershroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_BOULDERSHROOM = register("potted_bouldershroom", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BOULDERSHROOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PINK));
	public static final RegistryObject<Block, HugeMushroomBlock> BOULDERSHROOM_BLOCK = register("bouldershroom_block", HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_PINK).strength(0.2F).sound(SoundType.WOOD));
	public static final RegistryObject<Block, HugeMushroomBlock> BOULDERSHROOM_STEM = register("bouldershroom_stem", HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM));
	public static final RegistryObject<Block, BouldershroomRootsBlock> BOULDERSHROOM_ROOTS = register("bouldershroom_roots", BouldershroomRootsBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
	public static final RegistryObject<Block, BouldershroomRootsPlantBlock> BOULDERSHROOM_ROOTS_PLANT = register("bouldershroom_roots_plant", BouldershroomRootsPlantBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));

	// swamp plant
	public static final RegistryObject<Block, FlowerBlock> SWAMP_ROSE = register("swamp_rose", properties -> new FlowerBlock(MobEffects.POISON, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SWAMP_ROSE = register("potted_swamp_rose", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SWAMP_ROSE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, ESShortBushBlock> FANTABUD = register("fantabud", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, ESShortBushBlock> GREEN_FANTABUD = register("green_fantabud", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, ESShortBushBlock> FANTAFERN = register("fantafern", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 15));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_FANTAFERN = register("potted_fantafern", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FANTAFERN, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> GREEN_FANTAFERN = register("green_fantafern", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_GREEN_FANTAFERN = register("potted_green_fantafern", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREEN_FANTAFERN, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, ESShortBushBlock> FANTAGRASS = register("fantagrass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 15));
	public static final RegistryObject<Block, ESShortBushBlock> GREEN_FANTAGRASS = register("green_fantagrass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN));
	public static final RegistryObject<Block, HangingFantagrassBlock> HANGING_FANTAGRASS = register("hanging_fantagrass", HangingFantagrassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES).lightLevel(state -> 0).mapColor(MapColor.PLANT));
	public static final RegistryObject<Block, HangingFantagrassPlantBlock> HANGING_FANTAGRASS_PLANT = register("hanging_fantagrass_plant", HangingFantagrassPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT).lightLevel(state -> 0).mapColor(MapColor.PLANT));

	// scarlet forest plant
	public static final RegistryObject<Block, ESShortBushBlock> ORANGE_SCARLET_BUD = register("orange_scarlet_bud", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE));
	public static final RegistryObject<Block, ESShortBushBlock> PURPLE_SCARLET_BUD = register("purple_scarlet_bud", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, ESShortBushBlock> RED_SCARLET_BUD = register("red_scarlet_bud", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, ESShortBushBlock> SCARLET_GRASS = register("scarlet_grass", properties -> new ESShortBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_RED));
	public static final RegistryObject<Block, DesertBushBlock> MAUVE_FERN = register("mauve_fern", properties -> new DesertBushBlock(13, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE));

	// torreya forest plant
	public static final RegistryObject<Block, FlowerBlock> WITHERED_STARLIGHT_FLOWER = register("withered_starlight_flower", properties -> new FlowerBlock(MobEffects.WITHER, 10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_ORANGE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_WITHERED_STARLIGHT_FLOWER = register("potted_withered_starlight_flower", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WITHERED_STARLIGHT_FLOWER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_ORANGE));

	// desert plant
	public static final RegistryObject<Block, DeadBushBlock> DEAD_LUNAR_BUSH = register("dead_lunar_bush", DeadBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_DEAD_LUNAR_BUSH = register("potted_dead_lunar_bush", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DEAD_LUNAR_BUSH, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_DEAD_BUSH));
	public static final RegistryObject<Block, DesertFlowerBlock> DESERT_AMETHYSIA = register("desert_amethysia", properties -> new DesertFlowerBlock(ESMobEffects.CRYSTALLINE_INFECTION.asHolder(), 4f, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_DESERT_AMETHYSIA = register("potted_desert_amethysia", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DESERT_AMETHYSIA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, DesertFlowerBlock> WITHERED_DESERT_AMETHYSIA = register("withered_desert_amethysia", properties -> new DesertFlowerBlock(ESMobEffects.CRYSTALLINE_INFECTION.asHolder(), 4f, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_WITHERED_DESERT_AMETHYSIA = register("potted_withered_desert_amethysia", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WITHERED_DESERT_AMETHYSIA, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, DesertBushBlock> SUNSET_THORNBLOOM = register("sunset_thornbloom", properties -> new DesertBushBlock(10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH).mapColor(MapColor.COLOR_ORANGE));
	public static final RegistryObject<Block, FlowerPotBlock> POTTED_SUNSET_THORNBLOOM = register("potted_sunset_thornbloom", properties -> ESPlatform.INSTANCE.createFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SUNSET_THORNBLOOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_ORANGE));
	public static final RegistryObject<Block, DesertBushBlock> AMETHYSIA_GRASS = register("amethysia_grass", properties -> new DesertBushBlock(10, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, LunarisCactusBlock> LUNARIS_CACTUS = register("lunaris_cactus", LunarisCactusBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, LunarisCactusGelBlock> LUNARIS_CACTUS_GEL_BLOCK = register("lunaris_cactus_gel_block", LunarisCactusGelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, CarvedLunarisCactusFruitBlock> CARVED_LUNARIS_CACTUS_FRUIT = register("carved_lunaris_cactus_fruit", CarvedLunarisCactusFruitBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, CarvedLunarisCactusFruitBlock> LUNARIS_CACTUS_FRUIT_LANTERN = register("lunaris_cactus_fruit_lantern", CarvedLunarisCactusFruitBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_PURPLE));

	// water plant
	public static final RegistryObject<Block, WaterlilyBlock> MOONLIGHT_LILY_PAD = register("moonlight_lily_pad", WaterlilyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD));
	public static final RegistryObject<Block, WaterlilyBlock> STARLIT_LILY_PAD = register("starlit_lily_pad", WaterlilyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).lightLevel(state -> 5));
	public static final RegistryObject<Block, DuckweedBlock> MOONLIGHT_DUCKWEED = register("moonlight_duckweed", DuckweedBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD));

	// crystal caves plant
	public static final RegistryObject<Block, DesertBushBlock> CRYSTALLIZED_LUNAR_GRASS = register("crystallized_lunar_grass", properties -> new DesertBushBlock(8, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(DyeColor.RED));
	public static final RegistryObject<Block, DesertBushBlock> RED_CRYSTAL_ROOTS = register("red_crystal_roots", DesertBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(DyeColor.RED));
	public static final RegistryObject<Block, DesertBushBlock> BLUE_CRYSTAL_ROOTS = register("blue_crystal_roots", DesertBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(DyeColor.BLUE));
	public static final RegistryObject<Block, DoublePlantOnStoneBlock> TWILVEWRYM_HERB = register("twilvewyrm_herb", DoublePlantOnStoneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(DyeColor.BLUE));
	public static final RegistryObject<Block, DoublePlantOnStoneBlock> STELLAFLY_BUSH = register("stellafly_bush", DoublePlantOnStoneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(DyeColor.BLUE));
	public static final RegistryObject<Block, DoublePlantOnStoneBlock> GLIMMERFLY_BUSH = register("glimmerfly_bush", DoublePlantOnStoneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(DyeColor.RED).lightLevel(state -> 10));

	// floating islands plant
	public static final RegistryObject<Block, ESShortBushBlock> GOLDEN_GRASS = register("golden_grass", ESShortBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block, DoublePlantBlock> TALL_GOLDEN_GRASS = register("tall_golden_grass", DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_FERN).mapColor(MapColor.COLOR_YELLOW));

	// dirt & grass blocks
	public static final RegistryObject<Block, ESGrassBlock> FANTASY_GRASS_BLOCK = register("fantasy_grass_block", properties -> new ESGrassBlock(NIGHTFALL_MUD.get(), ESPlacedFeatures.SWAMP_VEGETATION, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, CarpetBlock> FANTASY_GRASS_CARPET = register("fantasy_grass_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> NIGHTFALL_DIRT = register("nightfall_dirt", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
	public static final RegistryObject<Block, FarmBlock> NIGHTFALL_FARMLAND = register("nightfall_farmland", FarmBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND));
	public static final RegistryObject<Block, DirtPathBlock> NIGHTFALL_DIRT_PATH = register("nightfall_dirt_path", DirtPathBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH));
	public static final RegistryObject<Block, ESGrassBlock> NIGHTFALL_GRASS_BLOCK = register("nightfall_grass_block", properties -> new ESGrassBlock(NIGHTFALL_DIRT.get(), ESPlacedFeatures.FOREST_VEGETATION, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, SnowyDirtBlock> NIGHTFALL_PODZOL = register("nightfall_podzol", SnowyDirtBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL));
	public static final RegistryObject<Block, ESGrassBlock> TENACIOUS_NIGHTFALL_GRASS_BLOCK = register("tenacious_nightfall_grass_block", properties -> new ESGrassBlock(NIGHTFALL_DIRT.get(), ESPlacedFeatures.FOREST_VEGETATION, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, ESGrassBlock> GOLDEN_GRASS_BLOCK = register("golden_grass_block", properties -> new ESGrassBlock(NIGHTFALL_DIRT.get(), ESPlacedFeatures.FOREST_VEGETATION, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.GOLD));

	// yeti fur
	public static final RegistryObject<Block, YetiFurBlock> WHITE_YETI_FUR = register("white_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> ORANGE_YETI_FUR = register("orange_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> MAGENTA_YETI_FUR = register("magenta_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> LIGHT_BLUE_YETI_FUR = register("light_blue_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> YELLOW_YETI_FUR = register("yellow_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> LIME_YETI_FUR = register("lime_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> PINK_YETI_FUR = register("pink_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> GRAY_YETI_FUR = register("gray_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> LIGHT_GRAY_YETI_FUR = register("light_gray_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> CYAN_YETI_FUR = register("cyan_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> PURPLE_YETI_FUR = register("purple_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> BLUE_YETI_FUR = register("blue_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> BROWN_YETI_FUR = register("brown_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> GREEN_YETI_FUR = register("green_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> RED_YETI_FUR = register("red_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL));
	public static final RegistryObject<Block, YetiFurBlock> BLACK_YETI_FUR = register("black_yeti_fur", YetiFurBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL));

	public static final RegistryObject<Block, CarpetBlock> WHITE_YETI_FUR_CARPET = register("white_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET));
	public static final RegistryObject<Block, CarpetBlock> ORANGE_YETI_FUR_CARPET = register("orange_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_CARPET));
	public static final RegistryObject<Block, CarpetBlock> MAGENTA_YETI_FUR_CARPET = register("magenta_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_CARPET));
	public static final RegistryObject<Block, CarpetBlock> LIGHT_BLUE_YETI_FUR_CARPET = register("light_blue_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_CARPET));
	public static final RegistryObject<Block, CarpetBlock> YELLOW_YETI_FUR_CARPET = register("yellow_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CARPET));
	public static final RegistryObject<Block, CarpetBlock> LIME_YETI_FUR_CARPET = register("lime_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_CARPET));
	public static final RegistryObject<Block, CarpetBlock> PINK_YETI_FUR_CARPET = register("pink_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_CARPET));
	public static final RegistryObject<Block, CarpetBlock> GRAY_YETI_FUR_CARPET = register("gray_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_CARPET));
	public static final RegistryObject<Block, CarpetBlock> LIGHT_GRAY_YETI_FUR_CARPET = register("light_gray_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_CARPET));
	public static final RegistryObject<Block, CarpetBlock> CYAN_YETI_FUR_CARPET = register("cyan_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_CARPET));
	public static final RegistryObject<Block, CarpetBlock> PURPLE_YETI_FUR_CARPET = register("purple_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CARPET));
	public static final RegistryObject<Block, CarpetBlock> BLUE_YETI_FUR_CARPET = register("blue_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_CARPET));
	public static final RegistryObject<Block, CarpetBlock> BROWN_YETI_FUR_CARPET = register("brown_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_CARPET));
	public static final RegistryObject<Block, CarpetBlock> GREEN_YETI_FUR_CARPET = register("green_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_CARPET));
	public static final RegistryObject<Block, CarpetBlock> RED_YETI_FUR_CARPET = register("red_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET));
	public static final RegistryObject<Block, CarpetBlock> BLACK_YETI_FUR_CARPET = register("black_yeti_fur_carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_CARPET));

	public static final RegistryObject<Block, SkullBlock> TANGLED_SKULL = register("tangled_skull", properties -> new SkullBlock(ESSkullType.TANGLED, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SKELETON_SKULL));
	public static final RegistryObject<Block, WallSkullBlock> TANGLED_WALL_SKULL = registerWallVariant("tangled_wall_skull", TANGLED_SKULL, properties -> new WallSkullBlock(ESSkullType.TANGLED, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SKELETON_WALL_SKULL));

	public static final RegistryObject<Block, Block> RAW_AETHERSENT_BLOCK = register("raw_aethersent_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> AETHERSENT_BLOCK = register("aethersent_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block, Block> SPRINGSTONE = register("springstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, SlabBlock> SPRINGSTONE_SLAB = register("springstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, StairBlock> SPRINGSTONE_STAIRS = register("springstone_stairs", properties -> new StairBlock(SPRINGSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, WallBlock> SPRINGSTONE_WALL = register("springstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, Block> SPRINGSTONE_BRICKS = register("springstone_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, SlabBlock> SPRINGSTONE_BRICK_SLAB = register("springstone_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, StairBlock> SPRINGSTONE_BRICK_STAIRS = register("springstone_brick_stairs", properties -> new StairBlock(SPRINGSTONE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, WallBlock> SPRINGSTONE_BRICK_WALL = register("springstone_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, Block> POLISHED_SPRINGSTONE = register("polished_springstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, SlabBlock> POLISHED_SPRINGSTONE_SLAB = register("polished_springstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, StairBlock> POLISHED_SPRINGSTONE_STAIRS = register("polished_springstone_stairs", properties -> new StairBlock(POLISHED_SPRINGSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, WallBlock> POLISHED_SPRINGSTONE_WALL = register("polished_springstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, Block> CHISELED_SPRINGSTONE = register("chiseled_springstone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, ThermalSpringstoneBlock> THERMAL_SPRINGSTONE = register("thermal_springstone", ThermalSpringstoneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).mapColor(MapColor.COLOR_BROWN));
	public static final RegistryObject<Block, SlabBlock> THERMAL_SPRINGSTONE_SLAB = register("thermal_springstone_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, StairBlock> THERMAL_SPRINGSTONE_STAIRS = register("thermal_springstone_stairs", properties -> new StairBlock(THERMAL_SPRINGSTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, WallBlock> THERMAL_SPRINGSTONE_WALL = register("thermal_springstone_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, Block> THERMAL_SPRINGSTONE_BRICKS = register("thermal_springstone_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, SlabBlock> THERMAL_SPRINGSTONE_BRICK_SLAB = register("thermal_springstone_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, StairBlock> THERMAL_SPRINGSTONE_BRICK_STAIRS = register("thermal_springstone_brick_stairs", properties -> new StairBlock(THERMAL_SPRINGSTONE_BRICKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, WallBlock> THERMAL_SPRINGSTONE_BRICK_WALL = register("thermal_springstone_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BROWN).strength(3.0F, 3.0F));
	public static final RegistryObject<Block, DropExperienceBlock> GLACITE = register("glacite", properties -> new DropExperienceBlock(UniformInt.of(3, 7), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).mapColor(MapColor.SNOW));
	public static final RegistryObject<Block, DropExperienceBlock> SWAMP_SILVER_ORE = register("swamp_silver_ore", properties -> new DropExperienceBlock(UniformInt.of(3, 7), properties), BlockBehaviour.Properties.of().strength(3.0F, 3.0F));
	public static final RegistryObject<Block, Block> SWAMP_SILVER_BLOCK = register("swamp_silver_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 3.5F));
	public static final RegistryObject<Block, RedStoneOreBlock> GRIMSTONE_REDSTONE_ORE = register("grimstone_redstone_ore", RedStoneOreBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_ORE));
	public static final RegistryObject<Block, RedStoneOreBlock> VOIDSTONE_REDSTONE_ORE = register("voidstone_redstone_ore", RedStoneOreBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_REDSTONE_ORE));
	public static final RegistryObject<Block, DropExperienceBlock> GRIMSTONE_SALTPETER_ORE = register("grimstone_saltpeter_ore", properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE));
	public static final RegistryObject<Block, DropExperienceBlock> VOIDSTONE_SALTPETER_ORE = register("voidstone_saltpeter_ore", properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE));
	public static final RegistryObject<Block, Block> SALTPETER_BLOCK = register("saltpeter_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).mapColor(MapColor.COLOR_YELLOW));

	public static final RegistryObject<Block, LanternBlock> AMARAMBER_LANTERN = register("amaramber_lantern", LanternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN));
	public static final RegistryObject<Block, AmaramberCandleBlock> AMARAMBER_CANDLE = register("amaramber_candle", AmaramberCandleBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CANDLE));
	public static final RegistryObject<Block, AmaramberCandleCakeBlock> AMARAMBER_CANDLE_CAKE = register("amaramber_candle_cake", properties -> new AmaramberCandleCakeBlock(AMARAMBER_CANDLE.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE_CAKE));

	// magic
	public static final RegistryObject<Block, StellarRackBlock> STELLAR_RACK = register("stellar_rack", StellarRackBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN).lightLevel(state -> 12));
	public static final RegistryObject<Block, EnchantedGrimstoneBricksBlock> ENCHANTED_GRIMSTONE_BRICKS = register("enchanted_grimstone_bricks", EnchantedGrimstoneBricksBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN));
	public static final RegistryObject<Block, LiquidBlock> ETHER = register("ether", properties -> new LiquidBlock(ESFluids.ETHER_STILL.get(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).mapColor(MapColor.SNOW));
	public static final RegistryObject<Block, CrestPotBlock> CREST_POT = register("crest_pot", CrestPotBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).mapColor(MapColor.COLOR_BLACK).lightLevel(state -> 2));
	public static final RegistryObject<Block, EnergyBlock> ENERGY_BLOCK = register("energy_block", EnergyBlock::new, BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).pushReaction(PushReaction.IGNORE).mapColor(MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block, TheGatekeeperSpawnerBlock> THE_GATEKEEPER_SPAWNER = register("the_gatekeeper_spawner", TheGatekeeperSpawnerBlock::new, BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, StarlightGolemSpawnerBlock> STARLIGHT_GOLEM_SPAWNER = register("starlight_golem_spawner", StarlightGolemSpawnerBlock::new, BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, TangledHatredSpawnerBlock> TANGLED_HATRED_SPAWNER = register("tangled_hatred_spawner", TangledHatredSpawnerBlock::new, BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, LunarMonstrositySpawnerBlock> LUNAR_MONSTROSITY_SPAWNER = register("lunar_monstrosity_spawner", LunarMonstrositySpawnerBlock::new, BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK));
	public static final RegistryObject<Block, ESPortalBlock> STARLIGHT_PORTAL = register("starlight_portal", ESPortalBlock::new, BlockBehaviour.Properties.of().strength(-1F).noCollission().lightLevel(state -> 10));

	private static <T extends Block> RegistryObject<Block, T> register(String id, Function<BlockBehaviour.Properties, T> function, BlockBehaviour.Properties properties) {
		return BLOCKS.register(id, () -> function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, EternalStarlight.id(id)))));
	}

	private static <T extends Block> RegistryObject<Block, T> registerWallVariant(String id, RegistryObject<Block, ? extends Block> parent, Function<BlockBehaviour.Properties, T> function, BlockBehaviour.Properties properties) {
		return BLOCKS.register(id, () -> function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, EternalStarlight.id(id))).overrideDescription(parent.get().getDescriptionId()).overrideLootTable(parent.get().getLootTable())));
	}

	public static void loadClass() {
	}
}
