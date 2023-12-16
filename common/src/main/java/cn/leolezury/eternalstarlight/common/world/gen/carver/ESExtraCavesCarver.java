package cn.leolezury.eternalstarlight.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

import java.util.function.Function;

public class ESExtraCavesCarver extends WorldCarver<CarverConfiguration> {
    public ESExtraCavesCarver(Codec<CarverConfiguration> codec) {
        super(codec);
    }

    private SimplexNoise noise;

    public void setSeed(long seed) {
        this.noise = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(seed)));
    }

    @Override
    public boolean carve(CarvingContext carvingContext, CarverConfiguration carverConfiguration, ChunkAccess chunkAccess, Function<BlockPos, Holder<Biome>> function, RandomSource randomSource, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        if (this.noise == null) {
            this.noise = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(4096)));
        }
        boolean success = chunkAccess.getPos().x == chunkPos.x && chunkAccess.getPos().z == chunkPos.z;
        if (success) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = Math.min(chunkAccess.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z) - 20, 80); y > -64; y--) {
                        int worldX = chunkPos.x * 16 + x;
                        int worldZ = chunkPos.z * 16 + z;
                        BlockPos pos = new BlockPos(worldX, y, worldZ);
                        if (noise.getValue(worldX / 50d, y / 30d, worldZ / 50d) < -0.4) {
                            BlockState state = chunkAccess.getBlockState(pos);
                            if (state.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES) && state.getFluidState().isEmpty()) {
                                chunkAccess.setBlockState(pos, y > carverConfiguration.lavaLevel.resolveY(carvingContext) ? AIR : LAVA.createLegacyBlock(), false);
                            }
                        }
                    }
                }
            }
        }
        return success;
    }

    @Override
    public boolean isStartChunk(CarverConfiguration carverConfiguration, RandomSource randomSource) {
        return true;
    }
}
