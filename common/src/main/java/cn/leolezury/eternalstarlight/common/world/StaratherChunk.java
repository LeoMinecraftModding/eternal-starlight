package cn.leolezury.eternalstarlight.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.Random;

public class StaratherChunk {
    public static boolean isStaratherChunk(long seed, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return new Random(seed + new BlockPos(chunkPos.x, 0, chunkPos.z).asLong()).nextInt(20) == 0;
    }
}
