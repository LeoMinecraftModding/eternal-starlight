package cn.leolezury.eternalstarlight.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class StaratherChunk {
    public static RandomSource staratherChunkSpawn(int i, int j, long l, long m) {
        return RandomSource.create(l + (long)(i * i * 4987142) + (long)(i * 5947611) + (long)(j * j) * 4392871L + (long)(j * 389711) ^ m);
    }

    public static boolean isStaratherChunk(LevelAccessor accessor, BlockPos pos, RandomSource randomSource) {
        ChunkPos chunkpos = new ChunkPos(pos);
        boolean flag = WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((WorldGenLevel)accessor).getSeed(), 987234911L).nextInt(10) == 0;
        if (randomSource.nextInt(10) == 0 && flag && pos.getY() < 40) {
            return true;
        }
        return false;
    }
}
