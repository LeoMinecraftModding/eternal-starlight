package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

public class ESChunkGenerator extends NoiseBasedChunkGenerator {
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private final BlockState defaultBlock;
    private final BlockState defaultFluid;

    public long seed = 0;

    public static final Codec<ESChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(o -> o.biomeSource),
            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(NoiseBasedChunkGenerator::generatorSettings)
    ).apply(instance, instance.stable(ESChunkGenerator::new)));

    public ESChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource, settings);
        if (settings.isBound()) {
            this.defaultBlock = settings.value().defaultBlock();
            this.defaultFluid = settings.value().defaultFluid();
        } else {
            this.defaultBlock = Blocks.STONE.defaultBlockState();
            this.defaultFluid = Blocks.WATER.defaultBlockState();
        }
    }

    @Override
    public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> holderLookup, RandomState randomState, long seed) {
        this.seed = seed;
        if (biomeSource instanceof ESBiomeSource source) {
            source.setSeed(seed);
        }
        return super.createState(holderLookup, randomState, seed);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    // Copied from vanilla NoiseBasedChunkGenerator, only to use our custom doFill
    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
        NoiseSettings noiseSettings = generatorSettings().value().noiseSettings().clampToHeightAccessor(chunkAccess.getHeightAccessorForGeneration());
        int i = noiseSettings.minY();
        int k = Mth.floorDiv(noiseSettings.height(), noiseSettings.getCellHeight());
        if (k <= 0) {
            return CompletableFuture.completedFuture(chunkAccess);
        } else {
            int l = chunkAccess.getSectionIndex(k * noiseSettings.getCellHeight() - 1 + i);
            int m = chunkAccess.getSectionIndex(i);
            Set<LevelChunkSection> set = Sets.newHashSet();

            for(int n = l; n >= m; --n) {
                LevelChunkSection levelChunkSection = chunkAccess.getSection(n);
                levelChunkSection.acquire();
                set.add(levelChunkSection);
            }

            return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("wgen_fill_noise", () -> this.doFill(structureManager, chunkAccess)), Util.backgroundExecutor()).whenCompleteAsync((access, throwable) -> {
                for (LevelChunkSection levelChunkSection : set) {
                    levelChunkSection.release();
                }
            }, executor);
        }
    }

    private ChunkAccess doFill(StructureManager structureManager, ChunkAccess chunkAccess) {
        ChunkPos chunkPos = chunkAccess.getPos();
        Beardifier beardifier = Beardifier.forStructuresInChunk(structureManager, chunkPos);
        Heightmap oceanHeightmap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap surfaceHeightmap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
        for (int x = 0; x < 16; x++) {
            int blockX = chunkPos.getBlockX(x);
            for (int z = 0; z < 16; z++) {
                int blockZ = chunkPos.getBlockZ(z);
                int surfaceHeight = getSurfaceHeight(blockX, blockZ);
                int maxHeight = chunkAccess.getMaxBuildHeight();
                for (int sectionY = (chunkAccess.getSections()).length - 1; sectionY >= 0; sectionY--) {
                    int blockSectionY = sectionY * 16;
                    LevelChunkSection section = chunkAccess.getSection(sectionY);

                    for (int y = 15; y >= 0; y--) {
                        int blockY = blockSectionY + y;
                        if (blockY > maxHeight) {
                            break;
                        }

                        BlockState state = getStateAt(blockX, blockY, blockZ, surfaceHeight, getBiomeDataAt(blockX, blockZ));
                        double beard = beardifier.compute(new DensityFunction.SinglePointContext(blockX, blockY, blockZ));
                        if (beard > 0.1D) {
                            state = defaultBlock;
                        }

                        if (state != AIR) {
                            section.setBlockState(x, y, z, state, false);
                            oceanHeightmap.update(x, blockY, z, state);
                            surfaceHeightmap.update(x, blockY, z, state);

                            if (!state.getFluidState().isEmpty()) {
                                chunkAccess.markPosForPostprocessing(new BlockPos(blockX, blockY, blockZ));
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
    public CompletableFuture<ChunkAccess> createBiomes(Executor executor, RandomState randomState, Blender blender, StructureManager structureManager, ChunkAccess chunkAccess) {
        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
            chunkAccess.fillBiomesFromNoise(this.biomeSource, randomState.sampler());
            return chunkAccess;
        }), Util.backgroundExecutor());
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        BlockState[] states = new BlockState[generatorSettings().value().noiseSettings().clampToHeightAccessor(levelHeightAccessor).height()];
        return levelHeightAccessor.getMinBuildHeight() + iterateTerrainColumn(x, z, states, types.isOpaque(), levelHeightAccessor);
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
            BlockState state = getStateAt(x, height, z, surfaceHeight, data);
            if (statePredicate == null || statePredicate.test(state)) {
                states[index] = state;
                index++;
                height++;
            }
        }
        return states.length;
    }

    private BlockState getStateAt(int x, int y, int z, int surfaceHeight, BiomeData data) {
        BlockState state = AIR;
        if (y <= surfaceHeight) {
            state = defaultBlock;
        } else if (y <= getSeaLevel()) {
            state = defaultFluid;
        }
        return data.blockStateTransformer().apply(state, this, x, y, z, surfaceHeight);
    }

    private int getSurfaceHeight(int x, int z) {
        if (biomeSource instanceof ESBiomeSource esBiomeSource) {
            return esBiomeSource.getHeight(x, z);
        }
        return 0;
    }

    private BiomeData getBiomeDataAt(int x, int z) {
        return BiomeDataRegistry.getBiomeData(getBiomeAt(x, z));
    }

    private ResourceLocation getBiomeAt(int x, int z) {
        if (biomeSource instanceof ESBiomeSource source) {
            return BiomeDataRegistry.getBiomeLocation(source.getBiome(x, z));
        }
        return null;
    }
}
