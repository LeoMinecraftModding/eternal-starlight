package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BlockUtil {
    public static boolean isEntityInBlock(Entity entity, Block block) {
        AABB box = entity.getBoundingBox();
        BlockPos fromPos = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
        BlockPos toPos = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = fromPos.getX(); i <= toPos.getX(); ++i) {
            for (int j = fromPos.getY(); j <= toPos.getY(); ++j) {
                for (int k = fromPos.getZ(); k <= toPos.getZ(); ++k) {
                    mutableBlockPos.set(i, j, k);
                    BlockState blockState = entity.level().getBlockState(mutableBlockPos);
                    if (blockState.is(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
