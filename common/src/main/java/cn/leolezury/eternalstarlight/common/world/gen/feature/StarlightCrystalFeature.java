package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.init.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
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
        BlockState crystalState = isRed ? ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState();
        BlockState carpetState = isRed ? ESBlocks.RED_CRYSTAL_MOSS_CARPET.get().defaultBlockState() : ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get().defaultBlockState();
        // generate the spike
        int baseHeight = random.nextInt(15, 20);
        placeCrystalPillar(level, pos, crystalState, random, baseHeight);
        int lowerHeight = baseHeight - random.nextInt(5, 10);
        placeCrystalPillar(level, pos.offset(1, 0, 0), crystalState, random, lowerHeight);
        lowerHeight = baseHeight - random.nextInt(5, 10);
        placeCrystalPillar(level, pos.offset(-1, 0, 0), crystalState, random, lowerHeight);
        lowerHeight = baseHeight - random.nextInt(5, 10);
        placeCrystalPillar(level, pos.offset(0, 0, 1), crystalState, random, lowerHeight);
        lowerHeight = baseHeight - random.nextInt(5, 10);
        placeCrystalPillar(level, pos.offset(0, 0, -1), crystalState, random, lowerHeight);
        int lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(1, 0, 1), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(1, 0, -1), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(-1, 0, 1), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(-1, 0, -1), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(2, 0, 0), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(-2, 0, 0), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(0, 0, 2), crystalState, random, lowestHeight);
        lowestHeight = Math.max(lowerHeight, 8) / 2 - random.nextInt(2, 4);
        placeCrystalPillar(level, pos.offset(0, 0, -2), crystalState, random, lowestHeight);
        // randomly place decorations
        for (int x = -7; x <= 7; x++) {
            for (int y = -10; y <= 10; y++) {
                for (int z = -7; z <= 7; z++) {
                    if (x * x + z * z < 7 * 7) {
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
                                BlockState clusterState = isRed ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction) : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction);
                                setBlockIfEmpty(level, pos.offset(x, y, z), clusterState);
                            }
                            BlockPos relativePos = pos.offset(x, y - 1, z);
                            if (level.getBlockState(relativePos).isFaceSturdy(level, relativePos, Direction.UP)) {
                                BlockState clusterState = random.nextBoolean() ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState();
                                setBlockIfEmpty(level, pos.offset(x, y, z), clusterState);
                            }
                        } else {
                            BlockPos relativePos = pos.offset(x, y - 1, z);
                            if (level.getBlockState(relativePos).is(ESTags.Blocks.BASE_STONE_STARLIGHT)) {
                                setBlockIfEmpty(level, pos.offset(x, y, z), carpetState);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeCrystalPillar(WorldGenLevel level, BlockPos pos, BlockState crystalState, RandomSource random, int height) {
        for (int y = 0; y <= height; y++) {
            setBlockIfEmpty(level, pos.offset(0, y, 0), crystalState);
            for (Direction direction : Direction.values()) {
                if (random.nextInt(5) == 0) {
                    setBlockIfEmpty(level, pos.offset(0, y, 0).relative(direction), crystalState);
                }
            }
        }
    }
}

