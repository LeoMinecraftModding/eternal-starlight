package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DeadLunarTreeFeature extends Feature<NoneFeatureConfiguration> {
	public DeadLunarTreeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private BlockState getBlockToPlace(RandomSource randomSource, BlockPos pos) {
		WeightedStateProvider stateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.DEAD_LUNAR_LOG.get().defaultBlockState(), 10).add(ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get().defaultBlockState(), 1).add(ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get().defaultBlockState(), 1).build());
		return stateProvider.getState(randomSource, pos);
	}

	private void placeBlockLine(BlockPos from, BlockPos to, RandomSource random, Consumer<BlockPos> placer) {
		List<int[]> points = ESMathUtil.getBresenham3DPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
		for (int[] point : points) {
			BlockPos trunkPos = new BlockPos(point[0], point[1], point[2]);
			placer.accept(trunkPos);
		}
	}

	private void placeBranches(BlockPos pos, RandomSource random, Consumer<BlockPos> placer) {
		int num = random.nextInt(3, 6);
		int len = random.nextInt(5, 8);
		for (int i = 0; i < num; i++) {
			Vec3 endVec = ESMathUtil.rotationToPosition(pos.getCenter(), len, 40, (360f / (float) num) * i);
			BlockPos endPos = new BlockPos((int) endVec.x, (int) endVec.y, (int) endVec.z);
			placeBlockLine(pos, endPos, random, placer);
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		List<BlockPos> blocksToPlace = new ArrayList<>();
		// make a trunk
		BlockPos topPos = pos.offset(random.nextInt(5) - 2, random.nextInt(10, 15), random.nextInt(5) - 2);
		placeBlockLine(pos, topPos, random, blocksToPlace::add);
		// make branches
		placeBranches(topPos, random, blocksToPlace::add);
		placeBranches(new BlockPos(Mth.lerpInt(0.75f, pos.getX(), topPos.getX()), Mth.lerpInt(0.75f, pos.getY(), topPos.getY()), Mth.lerpInt(0.75f, pos.getZ(), topPos.getZ())), random, blocksToPlace::add);
		for (BlockPos blockPos : blocksToPlace) {
			if (!level.isEmptyBlock(blockPos) && level.getBlockState(blockPos).getBlock() != Blocks.WATER) {
				return false;
			}
		}
		for (BlockPos blockPos : blocksToPlace) {
			setBlock(level, blockPos, getBlockToPlace(random, blockPos));
		}
		return true;
	}
}
