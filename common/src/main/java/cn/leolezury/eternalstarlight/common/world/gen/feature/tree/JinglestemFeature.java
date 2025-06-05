package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class JinglestemFeature extends Feature<JinglestemFeature.Configuration> {
	private static final List<Direction> ATTACHMENT_FACES = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

	public JinglestemFeature(Codec<Configuration> codec) {
		super(codec);
	}

	private void placeBlockLine(BlockPos from, BlockPos to, Consumer<BlockPos> placer) {
		List<int[]> points = ESMathUtil.getBresenham3DPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
		for (int[] point : points) {
			BlockPos trunkPos = new BlockPos(point[0], point[1], point[2]);
			placer.accept(trunkPos);
		}
	}

	private void placeBranches(BlockPos pos, int num, int len, Consumer<BlockPos> placer) {
		for (int i = 0; i < num; i++) {
			Vec3 endVec = ESMathUtil.rotationToPosition(pos.getCenter(), len, 40, (360f / (float) num) * i);
			BlockPos endPos = new BlockPos((int) endVec.x, (int) endVec.y, (int) endVec.z);
			placeBlockLine(pos, endPos, placer);
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		List<BlockPos> trunkPositions = new ArrayList<>();
		Configuration config = context.config();
		BlockState attachmentState = ESBlocks.ALGALEAVES.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
		// make a trunk
		int height = config.trunkHeight().sample(random);
		BlockPos topPos = pos.offset(random.nextInt(5) - 2, height, random.nextInt(5) - 2);
		placeBlockLine(pos, topPos, trunkPositions::add);
		// make branches
		int num = config.branchAmount().sample(random);
		int len = config.branchLength().sample(random);
		placeBranches(topPos, num, len, trunkPositions::add);
		for (BlockPos blockPos : trunkPositions) {
			if (!level.getBlockState(blockPos).is(Blocks.WATER)) {
				return false;
			}
		}
		for (BlockPos blockPos : trunkPositions) {
			setBlock(level, blockPos, ESBlocks.JINGLESTEM_LOG.get().defaultBlockState());
		}
		if (config.hasLeaves()) {
			for (BlockPos blockPos : trunkPositions) {
				if (random.nextInt(10) != 0) {
					int l = config.leavesHeight().sample(random);
					for (int i = 1; i <= l; i++) {
						if (level.getBlockState(blockPos.below(i)).is(Blocks.WATER)) {
							setBlock(level, blockPos.below(i), ESBlocks.HANGING_ALGALEAVES_PLANT.get().defaultBlockState());
							if (i == l) {
								setBlock(level, blockPos.below(i), ESBlocks.HANGING_ALGALEAVES.get().defaultBlockState());
							}
						} else {
							if (i != 1) {
								setBlock(level, blockPos.below(i - 1), ESBlocks.HANGING_ALGALEAVES.get().defaultBlockState());
							}
							break;
						}
					}
				}
				float chance = 1.25f * (blockPos.getY() - pos.getY()) / height;
				if (random.nextFloat() < chance) {
					for (Direction attachDir : ATTACHMENT_FACES) {
						BlockPos placePos = blockPos.relative(attachDir);
						if (level.getBlockState(placePos).is(Blocks.WATER)) {
							List<Direction> possibleDirs = new ArrayList<>();
							for (Direction direction : Direction.values()) {
								BlockPos relativePos = placePos.relative(direction);
								if (level.getBlockState(relativePos).is(ESBlocks.JINGLESTEM_LOG.get())) {
									possibleDirs.add(direction);
								}
							}
							for (Direction direction : Direction.values()) {
								if (possibleDirs.contains(direction)) {
									attachmentState = attachmentState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction), random.nextBoolean());
								} else {
									attachmentState = attachmentState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction), false);
								}
							}
							BlockState finalAttachmentState = attachmentState;
							if (Arrays.stream(Direction.values()).anyMatch(direction -> finalAttachmentState.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction)))) {
								setBlock(level, placePos, finalAttachmentState);
							}
						}
					}
				}
			}
		}
		return true;
	}

	public record Configuration(IntProvider trunkHeight, IntProvider branchAmount, IntProvider branchLength, IntProvider leavesHeight, boolean hasLeaves) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			IntProvider.CODEC.fieldOf("trunk_height").forGetter(Configuration::trunkHeight),
			IntProvider.CODEC.fieldOf("branch_amount").forGetter(Configuration::branchAmount),
			IntProvider.CODEC.fieldOf("branch_length").forGetter(Configuration::branchLength),
			IntProvider.CODEC.fieldOf("leaves_height").forGetter(Configuration::leavesHeight),
			Codec.BOOL.fieldOf("has_leaves").forGetter(Configuration::hasLeaves)
		).apply(instance, Configuration::new));
	}
}
