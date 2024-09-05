package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.LandmarkStructurePlacement;
import cn.leolezury.eternalstarlight.common.world.gen.system.BiomeData;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class ESChunkGenerator extends NoiseBasedChunkGenerator {
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private static final BlockState LAVA = Blocks.LAVA.defaultBlockState();
	private final BlockState defaultBlock;
	private final int seaLevel;

	public long seed = 0;

	private SimplexNoise noise = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(seed)));

	public void setSeed(long newSeed) {
		if (seed != newSeed) {
			this.noise = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(newSeed)));
			seed = newSeed;
		}
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
			this.seaLevel = 50;
		}
	}

	@Override
	public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> holderLookup, RandomState randomState, long seed) {
		setSeed(seed);
		if (biomeSource instanceof ESBiomeSource source) {
			source.setSeed(seed);
		}
		return super.createState(holderLookup, randomState, seed);
	}

	@Override
	protected MapCodec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	// Copied from vanilla NoiseBasedChunkGenerator, only to use our custom doFill
	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
		if (biomeSource instanceof ESBiomeSource source) {
			source.setRegistryAccess(structureManager.registryAccess());
		}
		NoiseSettings noiseSettings = generatorSettings().value().noiseSettings().clampToHeightAccessor(chunkAccess.getHeightAccessorForGeneration());
		int i = noiseSettings.minY();
		int j = Mth.floorDiv(i, 16);
		int k = Mth.floorDiv(noiseSettings.height(), 16);
		if (k <= 0) {
			return CompletableFuture.completedFuture(chunkAccess);
		} else {
			int l = chunkAccess.getSectionIndex(k * 16 - 1 + i);
			int m = chunkAccess.getSectionIndex(i);
			Set<LevelChunkSection> set = Sets.newHashSet();

			for (int n = l; n >= m; --n) {
				LevelChunkSection levelChunkSection = chunkAccess.getSection(n);
				levelChunkSection.acquire();
				set.add(levelChunkSection);
			}

			return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("wgen_fill_noise", () -> this.doFill(structureManager, chunkAccess, j)), Util.backgroundExecutor()).whenCompleteAsync((access, throwable) -> {
				for (LevelChunkSection levelChunkSection : set) {
					levelChunkSection.release();
				}
			});
		}
	}

	private ChunkAccess doFill(StructureManager structureManager, ChunkAccess chunkAccess, int minYSec) {
		ChunkPos chunkPos = chunkAccess.getPos();
		Beardifier beardifier = Beardifier.forStructuresInChunk(structureManager, chunkPos);
		Heightmap oceanFloorMap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
		Heightmap worldSurfaceMap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
		int minBlockX = chunkPos.getMinBlockX();
		int minBlockZ = chunkPos.getMinBlockZ();
		int minY = getMinY();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		int cellWidth = 16;
		int cellHeight = 16;
		int numSec = chunkAccess.getSections().length;

		for (int secY = numSec - 1; secY >= 0; --secY) {
			LevelChunkSection levelChunkSection = chunkAccess.getSection(secY);
			for (int relativeY = cellHeight - 1; relativeY >= 0; --relativeY) {
				int worldY = (minYSec + secY) * cellHeight + relativeY;
				int blockYInCell = worldY & 15;

				for (int cellBlockX = 0; cellBlockX < cellWidth; ++cellBlockX) {
					int worldX = minBlockX + cellBlockX;
					int blockXInCell = worldX & 15;

					for (int cellBlockZ = 0; cellBlockZ < cellWidth; ++cellBlockZ) {
						int worldZ = minBlockZ + cellBlockZ;
						int blockZInCell = worldZ & 15;

						int surfaceHeight = getSurfaceHeight(worldX, worldZ);
						BlockState blockState = getStateAt(worldY, surfaceHeight, getBiomeDataAt(worldX, worldZ));

						double noiseVal = noise.getValue(worldX / 50d, worldY / 30d, worldZ / 50d);
						if (worldY < surfaceHeight - 15 && worldY > minY + 2 && (worldY > minY + 4 || (int) (noiseVal * 200) % 5 == 0) && noiseVal < -0.3) {
							blockState = worldY > minY + 8 ? AIR : LAVA;
						}

						double beard = beardifier.compute(new DensityFunction.SinglePointContext(worldX, worldY, worldZ));
						if (beard > 0.1) {
							blockState = defaultBlock;
						}

						if (blockState != AIR) {
							levelChunkSection.setBlockState(blockXInCell, blockYInCell, blockZInCell, blockState, false);
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
		return chunkAccess;
	}

	// From the vanilla ChunkGenerator, NoiseBasedChunkGenerator is doing something useless for us
	@Override
	public CompletableFuture<ChunkAccess> createBiomes(RandomState randomState, Blender blender, StructureManager structureManager, ChunkAccess chunkAccess) {
		if (biomeSource instanceof ESBiomeSource source) {
			source.setRegistryAccess(structureManager.registryAccess());
		}
		return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
			chunkAccess.fillBiomesFromNoise(this.biomeSource, randomState.sampler());
			return chunkAccess;
		}), Util.backgroundExecutor());
	}

	@Override
	public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
		if (randomState.surfaceSystem() instanceof StarlightSurfaceSystem system) {
			system.setStarlightChunkGenerator(this);
		}
		super.buildSurface(worldGenRegion, structureManager, randomState, chunkAccess);
	}

	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
		return getSurfaceHeight(x, z);
	}

	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
		BlockState[] states = new BlockState[generatorSettings().value().noiseSettings().clampToHeightAccessor(levelHeightAccessor).height()];
		iterateTerrainColumn(x, z, states, null, levelHeightAccessor);
		return new NoiseColumn(levelHeightAccessor.getMinBuildHeight(), states);
	}

	private int iterateTerrainColumn(int x, int z, BlockState[] states, @Nullable Predicate<BlockState> statePredicate, LevelHeightAccessor level) {
		int surfaceHeight = getSurfaceHeight(x, z);
		int maxHeight = level.getMaxBuildHeight();
		int height = level.getMinBuildHeight();
		BiomeData data = getBiomeDataAt(x, z);
		int index = 0;
		while (height < maxHeight) {
			BlockState state = getStateAt(height, surfaceHeight, data);
			if (statePredicate == null || statePredicate.test(state)) {
				states[index] = state;
				index++;
				height++;
			}
		}
		return states.length;
	}

	private BlockState getStateAt(int y, int surfaceHeight, BiomeData data) {
		BlockState state;
		if (y <= surfaceHeight) {
			state = defaultBlock;
		} else if (y <= seaLevel) {
			state = data.fluidBlock().value().defaultBlockState();
		} else {
			state = AIR;
		}
		return state;
	}

	public int getSurfaceHeight(int x, int z) {
		if (biomeSource instanceof ESBiomeSource source) {
			return source.getHeight(x, z);
		}
		return 0;
	}

	private BiomeData getBiomeDataAt(int x, int z) {
		if (biomeSource instanceof ESBiomeSource source) {
			return source.getBiomeData(x, z);
		}
		return null;
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
