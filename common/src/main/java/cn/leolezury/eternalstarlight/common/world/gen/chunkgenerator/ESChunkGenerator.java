package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.LandmarkStructurePlacement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ESChunkGenerator extends NoiseBasedChunkGenerator {
	// how far above a column's own surface the aquifer can still have sealed a fluid body
	private static final int SEAL_BAND = 64;

	public static final MapCodec<ESChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BiomeSource.CODEC.fieldOf("biome_source").forGetter(o -> o.biomeSource),
		NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(NoiseBasedChunkGenerator::generatorSettings)
	).apply(instance, instance.stable(ESChunkGenerator::new)));

	public ESChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
		super(biomeSource, settings);
	}

	@Override
	protected MapCodec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> holderLookup, RandomState randomState, long seed) {
		bindNoise(randomState);
		return super.createState(holderLookup, randomState, seed);
	}

	@Override
	public CompletableFuture<ChunkAccess> createBiomes(RandomState randomState, Blender blender, StructureManager structureManager, ChunkAccess chunk) {
		// createState already bound it, but binding again is free
		bindNoise(randomState);
		return super.createBiomes(randomState, blender, structureManager, chunk);
	}

	private void bindNoise(RandomState randomState) {
		// only the chunk generator can reach the random state
		if (this.biomeSource instanceof ESBiomeSource source) {
			source.bindNoise(randomState);
		}
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
		return super.fillFromNoise(blender, randomState, structureManager, chunk).thenApply(filled -> {
			fillRiverFluid(filled, randomState);
			return filled;
		});
	}

	/**
	 * Biomes carry their own fluid, which the single default fluid of the noise settings cannot express, and aquifers
	 * leave air pockets at the water surface. So this tops the fluid column up to sea level: water becomes the biome's
	 * fluid, air is filled with it, and anything solid stops the walk. Anything above the column's own surface is fluid
	 * as well, because the aquifer seals the bodies it makes with stone, which would cut a river in half.
	 */
	private void fillRiverFluid(ChunkAccess chunk, RandomState randomState) {
		if (!(this.biomeSource instanceof ESBiomeSource source)) {
			return;
		}
		DensityFunction depth = randomState.sampler().depth();
		BlockState defaultFluid = this.generatorSettings().value().defaultFluid();
		int seaLevel = this.generatorSettings().value().seaLevel();
		int quartY = QuartPos.fromBlock(seaLevel - 1);
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int worldX = chunk.getPos().getMinBlockX() + x;
				int worldZ = chunk.getPos().getMinBlockZ() + z;
				Holder<Block> fluidBlock = source.fluidFor(chunk.getNoiseBiome(QuartPos.fromBlock(worldX), quartY, QuartPos.fromBlock(worldZ)));
				if (fluidBlock == null) {
					continue;
				}
				BlockState fluid = fluidBlock.value().defaultBlockState();
				double surface = ESDimensions.surfaceHeight(depth, worldX, worldZ);
				for (int y = seaLevel - 1; y > this.getMinY(); y--) {
					pos.set(worldX, y, worldZ);
					BlockState state = chunk.getBlockState(pos);
					if (state.isAir() || state.is(defaultFluid.getBlock()) || state.is(fluid.getBlock()) || y >= surface) {
						if (!state.is(fluid.getBlock())) {
							chunk.setBlockState(pos, fluid, false);
						}
					} else {
						break;
					}
				}
				// The aquifer seals its fluid bodies with stone wherever two of its cells disagree about the fluid level,
				// and those seals sit anywhere within about fifty blocks of the levels the cells picked, which for a deep
				// river is well above sea level and out of reach of the loop above. They are not terrain, so the header of
				// the column goes back to air.
				for (int y = Math.max(seaLevel, (int) surface + 1); y <= surface + SEAL_BAND; y++) {
					pos.set(worldX, y, worldZ);
					BlockState state = chunk.getBlockState(pos);
					if (!state.isAir() && !state.is(fluid.getBlock()) && !state.is(defaultFluid.getBlock())) {
						chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), false);
					}
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
}
