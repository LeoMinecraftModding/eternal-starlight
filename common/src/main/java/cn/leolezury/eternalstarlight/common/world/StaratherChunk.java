package cn.leolezury.eternalstarlight.common.world;

import cn.leolezury.eternalstarlight.common.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;

public class StaratherChunk {
    public static boolean isStaratherChunk(LevelAccessor accessor, BlockPos pos) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return (new FastNoiseLite((int) ((WorldGenLevel)accessor).getSeed())).GetNoise(chunkpos.x, chunkpos.z) >= 0.3;
    }
}
