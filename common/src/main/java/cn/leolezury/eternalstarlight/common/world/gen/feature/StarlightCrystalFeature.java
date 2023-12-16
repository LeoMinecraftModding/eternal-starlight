package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class StarlightCrystalFeature extends ESFeature<NoneFeatureConfiguration> {
    public StarlightCrystalFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        boolean isRed = random.nextBoolean();
        BlockState crystalState = isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState() : BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState();
        BlockState carpetState = isRed ? BlockInit.RED_CRYSTAL_MOSS_CARPET.get().defaultBlockState() : BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get().defaultBlockState();
        // generate a sphere
        for (int x = -4; x <= 4; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -4; z <= 4; z++) {
                    if (ESUtil.isPointInEllipsoid(x, y, z, 5 + random.nextInt(3) - 1, 3 + random.nextInt(3) - 1, 5 + random.nextInt(3) - 1)) {
                        setBlockIfEmpty(level, pos.offset(x, y, z), crystalState);
                    }
                }
            }
        }
        // generate the spike
        for (int y = 0; y <= 10; y++) {
            int radius = (int) Math.round(16d / (y + 4));
            int radiusOffset = radius <= 2 ? 0 : random.nextInt(3) - 1;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + z * z <= Math.pow(radius - 1 + radiusOffset, 2)) {
                        setBlockIfEmpty(level, pos.offset(x, y, z), crystalState);
                    }
                }
            }
        }
        // randomly place decorations
        for (int x = -7; x <= 7; x++) {
            for (int y = -5; y <= 12; y++) {
                for (int z = -7; z <= 7; z++) {
                    if (random.nextBoolean()) {
                        List<Direction> possibleDirs = new ArrayList<>();
                        for (Direction direction : Direction.values()) {
                            BlockPos relativePos = pos.offset(x, y, z).relative(direction);
                            if (level.getBlockState(relativePos).is(crystalState.getBlock())) {
                                possibleDirs.add(direction);
                            }
                        }
                        if (!possibleDirs.isEmpty()) {
                            Direction direction = possibleDirs.get(random.nextInt(possibleDirs.size())).getOpposite();
                            BlockState clusterState = isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction) : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction);
                            setBlockIfEmpty(level, pos.offset(x, y, z), clusterState);
                        }
                    } else {
                        BlockPos relativePos = pos.offset(x, y - 1, z);
                        if (x * x + z * z < 7 * 7 && level.getBlockState(relativePos).is(ESTags.Blocks.BASE_STONE_STARLIGHT)) {
                            setBlockIfEmpty(level, pos.offset(x, y, z), carpetState);
                        }
                    }
                }
            }
        }
        return true;
    }
}

