package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EtherFluidBorderFeature extends ESFeature<NoneFeatureConfiguration> {
    public EtherFluidBorderFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos chunkCoord = getChunkCoordinate(context.origin()).offset(1, 0, 1);

        for (int x = chunkCoord.getX(); x < chunkCoord.getX() + 16; x++) {
            for (int z = chunkCoord.getZ(); z < chunkCoord.getZ() + 16; z++) {
                for (int y = chunkCoord.getY() - 32; y < chunkCoord.getY() + 32; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (level.getBlockState(pos).is(ESBlocks.ETHER.get())) {
                        for (Direction direction : Direction.values()) {
                            BlockPos relativePos = pos.relative(direction);
                            if (!level.getBlockState(relativePos).is(ESBlocks.ETHER.get()) && !level.getFluidState(relativePos).isEmpty()) {
                                setBlock(level, pos, ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState());
                                for (Direction dir : Direction.values()) {
                                    if (context.random().nextInt(5) == 0 && (!level.getFluidState(relativePos.relative(dir)).isEmpty() || level.isEmptyBlock(relativePos.relative(dir)))) {
                                        setBlock(level, relativePos.relative(dir), ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
