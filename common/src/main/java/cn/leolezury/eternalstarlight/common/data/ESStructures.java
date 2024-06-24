package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.structure.CursedGardenStructure;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.ESRandomSpreadStructurePlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
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
	public static final ResourceKey<Structure> GOLEM_FORGE = create("golem_forge");
	public static final ResourceKey<StructureSet> GOLEM_FORGE_SET = createSet("golem_forge");
	public static final ResourceKey<Structure> CURSED_GARDEN = create("cursed_garden");
	public static final ResourceKey<StructureSet> CURSED_GARDEN_SET = createSet("cursed_garden");

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
		context.register(GOLEM_FORGE, new JigsawStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_GOLEM_FORGE),
				Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(ESEntities.FREEZE.get(), 10, 1, 4)))),
				GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
				TerrainAdjustment.BEARD_THIN),
			poolGetter.getOrThrow(ESTemplatePools.GOLEM_FORGE_BOSS), Optional.empty(), 20,
			ConstantHeight.of(VerticalAnchor.aboveBottom(48)), false, Optional.empty(), 116, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
		context.register(CURSED_GARDEN, new CursedGardenStructure(
			new Structure.StructureSettings(
				biomeGetter.getOrThrow(ESTags.Biomes.HAS_CURSED_GARDEN),
				Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(ESEntities.TANGLED.get(), 20, 1, 2)))),
				GenerationStep.Decoration.SURFACE_STRUCTURES,
				TerrainAdjustment.BEARD_THIN)
		));
	}

	public static void bootstrapSets(BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structureGetter = context.lookup(Registries.STRUCTURE);
		context.register(PORTAL_RUINS_COMMON_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_COMMON), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 659853901)));
		context.register(PORTAL_RUINS_FOREST_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_FOREST), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 559826911)));
		context.register(PORTAL_RUINS_DESERT_SET, new StructureSet(structureGetter.getOrThrow(PORTAL_RUINS_DESERT), new RandomSpreadStructurePlacement(36, 30, RandomSpreadType.LINEAR, 855823921)));
		context.register(GOLEM_FORGE_SET, new StructureSet(structureGetter.getOrThrow(GOLEM_FORGE), new ESRandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, 239823921, Optional.empty(), 24, 20, 20)));
		context.register(CURSED_GARDEN_SET, new StructureSet(structureGetter.getOrThrow(CURSED_GARDEN), new ESRandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, 535637356, Optional.empty(), 28, 24, 25)));
	}

	public static ResourceKey<Structure> create(String name) {
		return ResourceKey.create(Registries.STRUCTURE, EternalStarlight.id(name));
	}

	public static ResourceKey<StructureSet> createSet(String name) {
		return ResourceKey.create(Registries.STRUCTURE_SET, EternalStarlight.id(name));
	}
}
