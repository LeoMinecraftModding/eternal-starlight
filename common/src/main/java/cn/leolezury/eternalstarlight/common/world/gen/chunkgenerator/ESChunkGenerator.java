package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.util.FastNoise;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.LandmarkStructurePlacement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.*;
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
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

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
							if (beard > 0.1) {
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

	@Override
	public @Nullable Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel level, HolderSet<Structure> structure, BlockPos pos, int searchRadius, boolean skipKnownStructures) {
		Pair<BlockPos, Holder<Structure>> mapStructure = super.findNearestMapStructure(level, structure, pos, searchRadius, skipKnownStructures);
		if (mapStructure != null) {
			return mapStructure;
		}

		ChunkGeneratorStructureState structureState = level.getChunkSource().getGeneratorState();
		Map<StructurePlacement, Set<Holder<Structure>>> placements = new Object2ObjectArrayMap<>();

		for (Holder<Structure> structureHolder : structure) {
			for (StructurePlacement structurePlacement : structureState.getPlacementsForStructure(structureHolder)) {
				placements.computeIfAbsent(structurePlacement, (placement) -> new ObjectArraySet<>()).add(structureHolder);
			}
		}

		if (placements.isEmpty()) {
			return null;
		} else {
			Pair<BlockPos, Holder<Structure>> result = null;
			double currentDist = Double.MAX_VALUE;
			StructureManager structureManager = level.structureManager();
			List<Map.Entry<StructurePlacement, Set<Holder<Structure>>>> list = new ArrayList<>(placements.size());

			for (Map.Entry<StructurePlacement, Set<Holder<Structure>>> entry : placements.entrySet()) {
				if (entry.getKey() instanceof LandmarkStructurePlacement) {
					list.add(entry);
				}
			}

			if (!list.isEmpty()) {
				int chunkX = SectionPos.blockToSectionCoord(pos.getX());
				int chunkZ = SectionPos.blockToSectionCoord(pos.getZ());

				for (int currentRadius = 0; currentRadius <= searchRadius; currentRadius++) {
					boolean found = false;

					for (Map.Entry<StructurePlacement, Set<Holder<Structure>>> entry : list) {
						Pair<BlockPos, Holder<Structure>> currentStructure = null;
						for (int x = -currentRadius; x <= currentRadius; ++x) {
							boolean xSide = x == -currentRadius || x == currentRadius;
							for (int z = -currentRadius; z <= currentRadius; ++z) {
								boolean zSide = z == -currentRadius || z == currentRadius;
								if (xSide || zSide) {
									ChunkPos landmarkPos = LandmarkStructurePlacement.getRegionLandmarkPos(structureState, x + chunkX, z + chunkZ);
									if (landmarkPos.x == x + chunkX && landmarkPos.z == z + chunkZ) {
										currentStructure = getStructureGeneratingAt(entry.getValue(), level, structureManager, skipKnownStructures, entry.getKey(), landmarkPos);
									}
								}
							}
						}
						if (currentStructure != null) {
							found = true;
							double dist = pos.distSqr(currentStructure.getFirst());
							if (dist < currentDist) {
								currentDist = dist;
								result = currentStructure;
							}
						}
					}

					if (found) {
						return result;
					}
				}
			}

			return result;
		}
	}
}
