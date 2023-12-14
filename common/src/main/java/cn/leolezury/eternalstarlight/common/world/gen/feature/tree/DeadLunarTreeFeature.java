package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import cn.leolezury.eternalstarlight.common.world.gen.feature.ESFeature;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DeadLunarTreeFeature extends ESFeature<NoneFeatureConfiguration> {
    public DeadLunarTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private BlockState getBlockToPlace(RandomSource randomSource, BlockPos pos) {
        WeightedStateProvider stateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.DEAD_LUNAR_LOG.get().defaultBlockState(), 10).add(BlockInit.RED_CRYSTALLIZED_LUNAR_LOG.get().defaultBlockState(), 1).add(BlockInit.BLUE_CRYSTALLIZED_LUNAR_LOG.get().defaultBlockState(), 1).build());
        return stateProvider.getState(randomSource, pos);
    }

    private void placeBlockLine(WorldGenLevel level, BlockPos from, BlockPos to, RandomSource random) {
        List<int[]> points = ESUtil.getBresenham3DPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
        for (int[] point : points) {
            BlockPos trunkPos = new BlockPos(point[0], point[1], point[2]);
            setBlockIfEmpty(level, trunkPos, getBlockToPlace(random, trunkPos));
        }
    }

    private void placeBranches(WorldGenLevel level, BlockPos pos, RandomSource random) {
        int num = random.nextInt(3, 6);
        int len = random.nextInt(5, 8);
        for (int i = 0; i < num; i++) {
            Vec3 endVec = ESUtil.rotationToPosition(pos.getCenter(), len, 40, (360f / (float) num) * i);
            BlockPos endPos = new BlockPos((int) endVec.x, (int) endVec.y, (int) endVec.z);
            placeBlockLine(level, pos, endPos, random);
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        // make a trunk
        BlockPos topPos = pos.offset(random.nextInt(5) - 2, random.nextInt(10, 15), random.nextInt(5) - 2);
        placeBlockLine(level, pos, topPos, random);
        // make branches and we're done
        placeBranches(level, topPos, random);
        placeBranches(level, new BlockPos(Mth.lerpInt(0.75f, pos.getX(), topPos.getX()), Mth.lerpInt(0.75f, pos.getY(), topPos.getY()), Mth.lerpInt(0.75f, pos.getZ(), topPos.getZ())), random);
        return true;
    }
}
