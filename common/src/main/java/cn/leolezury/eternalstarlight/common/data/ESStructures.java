package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.structure.StranghoulDenStructure;
import cn.leolezury.eternalstarlight.common.world.gen.structure.garden.CursedGardenStructure;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.AvoidLandmarkStructurePlacement;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.LandmarkStructurePlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ESStructures {
	public static final ResourceKey<Structure> PORTAL_RUINS_COMMON = create("portal_ruins_common");
	public static final ResourceKey<StructureSet> PORTAL_RUINS_COMMON_SET = createSet("portal_ruins_common");
	public static final ResourceKey<Structure> PORTAL_RUINS_FOREST = create("portal_ruins_forest");
	public static final ResourceKey<StructureSet> PORTAL_RUINS_FOREST_SET = createSet("portal_ruins_forest");
	public static final ResourceKey<Structure> PORTAL_RUINS_DESERT = create("portal_ruins_desert");
	public static final ResourceKey<StructureSet> PORTAL_RUINS_DESERT_SET = createSet("portal_ruins_desert");
	public static final ResourceKey<Structure> PORTAL_RUINS_JUNGLE = create("portal_ruins_jungle");
	public static final ResourceKey<StructureSet> PORTAL_RUINS_JUNGLE_SET = createSet("portal_ruins_jungle");
	public static final ResourceKey<Structure> PORTAL_RUINS_COLD = create("portal_ruins_cold");
	public static final ResourceKey<StructureSet> PORTAL_RUINS_COLD_SET = createSet("portal_ruins_cold");
	public static final ResourceKey<Structure> GOLEM_FORGE = create("golem_forge");
	public static final ResourceKey<StructureSet> GOLEM_FORGE_SET = createSet("golem_forge");
	public static final ResourceKey<Structure> CURSED_GARDEN = create("cursed_garden");
	public static final ResourceKey<StructureSet> CURSED_GARDEN_SET = createSet("cursed_garden");
	public static final ResourceKey<Structure> STRANGHOUL_DEN = create("stranghoul_den");
	public static final ResourceKey<StructureSet> STRANGHOUL_DEN_SET = createSet("stranghoul_den");

	public static void bootstrap(BootstrapContext<Structure> context) {
		HolderGetter<StructureTemplatePool> poolGetter = context.lookup(Registries.TEMPLATE_POOL);
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);

		context.register(PORTAL_RUINS_COMMON, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_PORTAL_RUINS_COMMON),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.PORTAL_RUINS_COMMON), Optional.empty(), 1,
			ConstantHeight.of(VerticalAnchor.absolute(0)), false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 50, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(PORTAL_RUINS_FOREST, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_PORTAL_RUINS_FOREST),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.PORTAL_RUINS_FOREST), Optional.empty(), 1,
			ConstantHeight.of(VerticalAnchor.absolute(0)), false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 50, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(PORTAL_RUINS_DESERT, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_PORTAL_RUINS_DESERT),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.PORTAL_RUINS_DESERT), Optional.empty(), 1,
			ConstantHeight.of(VerticalAnchor.absolute(0)), false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 50, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(PORTAL_RUINS_JUNGLE, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_PORTAL_RUINS_JUNGLE),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.PORTAL_RUINS_JUNGLE), Optional.empty(), 1,
			ConstantHeight.of(VerticalAnchor.absolute(0)), false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 50, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(PORTAL_RUINS_COLD, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_PORTAL_RUINS_COLD),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.PORTAL_RUINS_COLD), Optional.empty(), 1,
			ConstantHeight.of(VerticalAnchor.absolute(0)), false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 50, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(GOLEM_FORGE, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_GOLEM_FORGE),
				Map.of(),
				GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.GOLEM_FORGE_BOSS), Optional.empty(), 20,
			ConstantHeight.of(VerticalAnchor.aboveBottom(48)), false, Optional.empty(), 116, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(CURSED_GARDEN, new CursedGardenStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_CURSED_GARDEN),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN)
		));
		context.register(STRANGHOUL_DEN, new StranghoulDenStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_STRANGHOUL_DEN),
				Map.of(),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.NONE)
		));
	}

	public static void bootstrapSets(BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structureGetter = context.lookup(Registries.STRUCTURE);
		context.register(PORTAL_RUINS_COMMON_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_COMMON), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 958853901)));
		context.register(PORTAL_RUINS_FOREST_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_FOREST), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 789224182)));
		context.register(PORTAL_RUINS_DESERT_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_DESERT), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 525823926)));
		context.register(PORTAL_RUINS_JUNGLE_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_JUNGLE), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 391037419)));
		context.register(PORTAL_RUINS_COLD_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_COLD), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 107391749)));
		context.register(GOLEM_FORGE_SET, new StructureSet(structureGetter.getOrThrow(GOLEM_FORGE), new LandmarkStructurePlacement(GOLEM_FORGE)));
		context.register(CURSED_GARDEN_SET, new StructureSet(structureGetter.getOrThrow(CURSED_GARDEN), new LandmarkStructurePlacement(CURSED_GARDEN)));
		context.register(STRANGHOUL_DEN_SET, new StructureSet(structureGetter.getOrThrow(STRANGHOUL_DEN), new AvoidLandmarkStructurePlacement(20, 8, RandomSpreadType.LINEAR, 615391630)));
	}

	public static ResourceKey<Structure> create(String name) {
		return ResourceKey.create(Registries.STRUCTURE, EternalStarlight.id(name));
	}

	public static ResourceKey<StructureSet> createSet(String name) {
		return ResourceKey.create(Registries.STRUCTURE_SET, EternalStarlight.id(name));
	}
}
