package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.FastNoise;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.structure.StructureTerrainAdaptor;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.LandmarkStructurePlacement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ESChunkGenerator extends NoiseBasedChunkGenerator {
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private static final BlockState LAVA = Blocks.LAVA.defaultBlockState();

	private static final float NOISE_FREQUENCY = 1.5f;

	private final BlockState defaultBlock;
	private final int seaLevel;

	public long seed = 0;

	private final FastNoise caveNoise = makeNoise(seed);

	public void setSeed(long newSeed) {
		if (this.seed != newSeed) {
			this.seed = newSeed;
			this.caveNoise.setSeed((int) seed);
		}
	}

	private FastNoise makeNoise(long seed) {
		FastNoise noise = new FastNoise((int) seed);
		noise.setFrequency(NOISE_FREQUENCY);
		return noise;
	}

	public static final MapCodec<ESChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BiomeSource.CODEC.fieldOf("biome_source").forGetter(o -> o.biomeSource),
		NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(NoiseBasedChunkGenerator::generatorSettings)
	).apply(instance, instance.stable(ESChunkGenerator::new)));

	public ESChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
		super(biomeSource, settings);
		if (settings.isBound()) {
			this.defaultBlock = settings.value().defaultBlock();
			this.seaLevel = settings.value().seaLevel();
		} else {
			this.defaultBlock = Blocks.STONE.defaultBlockState();
			this.seaLevel = ESDimensions.SEA_LEVEL;
		}
	}

	@Override
	public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> holderLookup, RandomState randomState, long seed) {
		this.setSeed(seed);
		if (biomeSource instanceof ESBiomeSource source) {
			source.setSeed(seed);
		}
		return super.createState(holderLookup, randomState, seed);
	}

	@Override
	protected MapCodec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	protected ChunkAccess doFill(Blender blender, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess, int i, int j) {
		ChunkPos chunkPos = chunkAccess.getPos();
		Beardifier beardifier = Beardifier.forStructuresInChunk(structureManager, chunkPos);
		Heightmap oceanFloorMap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
		Heightmap worldSurfaceMap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
		int minBlockX = chunkPos.getMinBlockX();
		int minBlockZ = chunkPos.getMinBlockZ();
		int minY = getMinY();
		int minYSec = Math.floorDiv(minY, 16);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		int cellWidth = 16;
		int cellHeight = 16;
		int numSec = chunkAccess.getSections().length;

		StructureTerrainAdaptor adaptor = null;
		List<BoundingBox> adaptiveBoxes = new ArrayList<>();
		Registry<Structure> registry = structureManager.registryAccess().registryOrThrow(Registries.STRUCTURE);
		Set<Structure> adaptiveStructures = registry.getTag(ESTags.Structures.ADAPTIVE_TERRAIN)
			.map(tag -> tag.stream().map(Holder::value).collect(Collectors.toSet()))
			.orElse(Set.of());
		if (!adaptiveStructures.isEmpty()) {
			for (StructureStart start : structureManager.startsForStructure(chunkPos, s -> adaptiveStructures.contains(s))) {
				for (StructurePiece piece : start.getPieces()) {
					if (piece.isCloseToChunk(chunkPos, 12 + StructureTerrainAdaptor.EDGE_MARGIN)) {
						adaptiveBoxes.add(piece.getBoundingBox());
					}
				}
			}
			if (!adaptiveBoxes.isEmpty()) {
				adaptor = new StructureTerrainAdaptor(seed, adaptiveBoxes);
			}
		}

		if (this.biomeSource instanceof ESBiomeSource source) {
			int[][] cellHeights = new int[cellWidth][cellWidth];
			for (int cellX = 0; cellX < cellWidth; ++cellX) {
				for (int cellZ = 0; cellZ < cellWidth; ++cellZ) {
					int surfaceHeight = source.getHeight(minBlockX + cellX, minBlockZ + cellZ, randomState.sampler());
					cellHeights[cellX][cellZ] = surfaceHeight;
				}
			}
			for (int secY = 0; secY < numSec; ++secY) {
				LevelChunkSection section = chunkAccess.getSection(secY);
				for (int relativeY = 0; relativeY < cellHeight; ++relativeY) {
					int worldY = (minYSec + secY) * cellHeight + relativeY;
					int blockYInCell = worldY & 15;

					for (int cellBlockX = 0; cellBlockX < cellWidth; ++cellBlockX) {
						int worldX = minBlockX + cellBlockX;
						int blockXInCell = worldX & 15;

						for (int cellBlockZ = 0; cellBlockZ < cellWidth; ++cellBlockZ) {
							int worldZ = minBlockZ + cellBlockZ;
							int blockZInCell = worldZ & 15;

							int surfaceHeight = cellHeights[cellBlockX][cellBlockZ];
							BlockState blockState = getTargetBlock(worldX, worldY, worldZ, surfaceHeight, minY, source.getBiomeData(worldX, ESDimensions.SEA_LEVEL, worldZ, randomState.sampler()).value());

							double beard = beardifier.compute(new DensityFunction.SinglePointContext(worldX, worldY, worldZ));
							if (beard > 0.1 || (adaptor != null && adaptor.shouldFill(worldX, worldY, worldZ, surfaceHeight))) {
								blockState = defaultBlock;
							}

							if (blockState != AIR) {
								section.setBlockState(blockXInCell, blockYInCell, blockZInCell, blockState, false);
								oceanFloorMap.update(blockXInCell, worldY, blockZInCell, blockState);
								worldSurfaceMap.update(blockXInCell, worldY, blockZInCell, blockState);
								if (!blockState.getFluidState().isEmpty()) {
									mutableBlockPos.set(worldX, worldY, worldZ);
									chunkAccess.markPosForPostprocessing(mutableBlockPos);
								}
							}
						}
					}
				}
			}
		}

		return chunkAccess;
	}

	@Override
	public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
		if (randomState.surfaceSystem() instanceof StarlightSurfaceSystem system && this.biomeSource instanceof ESBiomeSource source) {
			system.setStarlightBiomeSource(source);
		}
		super.buildSurface(worldGenRegion, structureManager, randomState, chunkAccess);
	}

	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
		if (this.biomeSource instanceof ESBiomeSource source) {
			return source.getHeight(x, z, randomState.sampler());
		}
		return seaLevel;
	}

	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState randomState) {
		BlockState[] states = new BlockState[generatorSettings().value().noiseSettings().clampToHeightAccessor(level).height()];
		iterateTerrainColumn(x, z, states, null, level, randomState);
		return new NoiseColumn(level.getMinBuildHeight(), states);
	}

	private void iterateTerrainColumn(int x, int z, BlockState[] states, @Nullable Predicate<BlockState> statePredicate, LevelHeightAccessor level, RandomState randomState) {
		if (this.biomeSource instanceof ESBiomeSource source) {
			int surfaceHeight = source.getHeight(x, z, randomState.sampler());
			int maxHeight = level.getMaxBuildHeight();
			int minY = getMinY();
			int height = level.getMinBuildHeight();
			BiomeData data = source.getBiomeData(x, height, z, randomState.sampler()).value();
			int index = 0;
			while (height < maxHeight) {
				BlockState state = getTargetBlock(x, height, z, surfaceHeight, minY, data);
				if (statePredicate == null || statePredicate.test(state)) {
					states[index] = state;
					index++;
					height++;
				}
			}
		}
	}

	@Override
	@Nullable
	public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel level, HolderSet<Structure> structureSet, BlockPos pos, int searchRadius, boolean skipKnownStructures) {
		Pair<BlockPos, Holder<Structure>> vanillaResult = super.findNearestMapStructure(level, structureSet, pos, searchRadius, skipKnownStructures);
		if (vanillaResult != null) {
			return vanillaResult;
		}

		ChunkGeneratorStructureState structureState = level.getChunkSource().getGeneratorState();
		Map<LandmarkStructurePlacement, Set<Holder<Structure>>> landmarkPlacements = new Object2ObjectArrayMap<>();

		for (Holder<Structure> structureHolder : structureSet) {
			for (StructurePlacement placement : structureState.getPlacementsForStructure(structureHolder)) {
				if (placement instanceof LandmarkStructurePlacement landmarkPlacement) {
					landmarkPlacements.computeIfAbsent(landmarkPlacement, key -> new ObjectArraySet<>()).add(structureHolder);
				}
			}
		}

		if (landmarkPlacements.isEmpty()) {
			return null;
		}

		StructureManager structureManager = level.structureManager();
		int centerRegionX = SectionPos.blockToSectionCoord(pos.getX()) >> 5;
		int centerRegionZ = SectionPos.blockToSectionCoord(pos.getZ()) >> 5;

		Pair<BlockPos, Holder<Structure>> nearest = null;
		double nearestDistance = Double.MAX_VALUE;

		for (int regionRing = 0; regionRing <= searchRadius; regionRing++) {
			boolean anyFound = false;

			for (int drX = -regionRing; drX <= regionRing; drX++) {
				for (int drZ = -regionRing; drZ <= regionRing; drZ++) {
					if (Math.abs(drX) != regionRing && Math.abs(drZ) != regionRing) {
						continue;
					}

					int regionX = centerRegionX + drX;
					int regionZ = centerRegionZ + drZ;
					int regionChunkX = regionX * 32 + 16;
					int regionChunkZ = regionZ * 32 + 16;

					for (Map.Entry<LandmarkStructurePlacement, Set<Holder<Structure>>> entry : landmarkPlacements.entrySet()) {
						LandmarkStructurePlacement landmarkPlacement = entry.getKey();
						ChunkPos landmarkChunk = LandmarkStructurePlacement.getRegionLandmarkPos(structureState, regionChunkX, regionChunkZ);

						Pair<BlockPos, Holder<Structure>> foundStructure = checkStructureAtLandmark(
							entry.getValue(), level, structureManager, skipKnownStructures, landmarkPlacement, landmarkChunk
						);

						if (foundStructure != null) {
							anyFound = true;
							double dist = pos.distSqr(foundStructure.getFirst());
							if (dist < nearestDistance) {
								nearestDistance = dist;
								nearest = foundStructure;
							}
						}
					}
				}
			}

			if (anyFound) {
				return nearest;
			}
		}

		return nearest;
	}

	@Nullable
	private static Pair<BlockPos, Holder<Structure>> checkStructureAtLandmark(
		Set<Holder<Structure>> structureHolders,
		ServerLevel level,
		StructureManager structureManager,
		boolean skipKnownStructures,
		LandmarkStructurePlacement placement,
		ChunkPos chunkPos
	) {
		for (Holder<Structure> structureHolder : structureHolders) {
			StructureCheckResult checkResult = structureManager.checkStructurePresence(chunkPos, structureHolder.value(), placement, skipKnownStructures);
			if (checkResult != StructureCheckResult.START_NOT_PRESENT) {
				if (!skipKnownStructures && checkResult == StructureCheckResult.START_PRESENT) {
					return Pair.of(placement.getLocatePos(chunkPos), structureHolder);
				}

				ChunkAccess chunk = level.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS);
				StructureStart structureStart = structureManager.getStartForStructure(SectionPos.bottomOf(chunk), structureHolder.value(), chunk);
				if (structureStart != null && structureStart.isValid()) {
					if (!skipKnownStructures || structureStart.canBeReferenced()) {
						if (skipKnownStructures) {
							structureManager.addReference(structureStart);
						}
						return Pair.of(placement.getLocatePos(structureStart.getChunkPos()), structureHolder);
					}
				}
			}
		}

		return null;
	}

	private BlockState getTargetBlock(int x, int y, int z, int surfaceHeight, int minY, BiomeData data) {
		BlockState state;
		if (y <= surfaceHeight) {
			state = defaultBlock;
			double noiseVal = caveNoise.getNoise(x / 50f, y / 30f, z / 50f);
			if (y < surfaceHeight - 15 && y > minY + 2 && (y > minY + 4 || (int) (noiseVal * 200) % 5 == 0) && noiseVal < -0.3) {
				state = y > minY + 8 ? AIR : LAVA;
			}
		} else if (y <= seaLevel) {
			state = data.fluidBlock().value().defaultBlockState();
		} else {
			state = AIR;
		}
		return state;
	}
}
