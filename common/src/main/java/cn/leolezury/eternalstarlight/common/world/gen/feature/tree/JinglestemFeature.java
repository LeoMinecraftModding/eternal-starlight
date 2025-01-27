package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JinglestemFeature extends Feature<NoneFeatureConfiguration> {
	public JinglestemFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private void placeBlockLine(BlockPos from, BlockPos to, Consumer<BlockPos> placer) {
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
			placeBlockLine(pos, endPos, placer);
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		List<BlockPos> trunkPositions = new ArrayList<>();
		// make a trunk
		BlockPos topPos = pos.offset(random.nextInt(5) - 2, random.nextInt(8, 12), random.nextInt(5) - 2);
		placeBlockLine(pos, topPos, trunkPositions::add);
		// make branches
		placeBranches(topPos, random, trunkPositions::add);
		for (BlockPos blockPos : trunkPositions) {
			if (!level.getBlockState(blockPos).is(Blocks.WATER)) {
				return false;
			}
		}
		for (BlockPos blockPos : trunkPositions) {
			setBlock(level, blockPos, ESBlocks.JINGLESTEM_LOG.get().defaultBlockState());
		}
		for (BlockPos blockPos : trunkPositions) {
			if (random.nextInt(10) != 0) {
				int l = random.nextInt(4, 9);
				for (int i = 1; i <= l; i++) {
					if (level.getBlockState(blockPos.below(i)).is(Blocks.WATER)) {
						setBlock(level, blockPos.below(i), ESBlocks.JINGLESTEM_LEAVES_PLANT.get().defaultBlockState());
						if (i == l) {
							setBlock(level, blockPos.below(i), ESBlocks.JINGLESTEM_LEAVES.get().defaultBlockState());
						}
					} else {
						if (i != 1) {
							setBlock(level, blockPos.below(i - 1), ESBlocks.JINGLESTEM_LEAVES.get().defaultBlockState());
						}
						break;
					}
				}
			}
		}
		return true;
	}
}
