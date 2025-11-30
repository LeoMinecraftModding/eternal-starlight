package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BanyinRootsDecorator extends TreeDecorator {
	public static final MapCodec<BanyinRootsDecorator> CODEC = MapCodec.unit(() -> BanyinRootsDecorator.INSTANCE);
	public static final BanyinRootsDecorator INSTANCE = new BanyinRootsDecorator();

	@Override
	protected TreeDecoratorType<?> type() {
		return ESTreeDecorators.BANYIN_ROOTS.get();
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		LevelSimulatedReader level = context.level();
		Optional<BlockPos> lowest = context.logs().stream().min(Comparator.comparingInt(Vec3i::getY));
		if (lowest.isPresent()) {
			int lowestY = lowest.get().getY();
			context.logs().forEach((pos) -> {
				if (pos.getY() - lowestY == 2) {
					for (int i = 0; i < random.nextInt(2, 4); i++) {
						BlockPos endPos = pos.offset((random.nextBoolean() ? -1 : 1) * random.nextInt(3, 6), -random.nextInt(6, 10), (random.nextBoolean() ? -1 : 1) * random.nextInt(3, 6));
						List<int[]> points = ESMathUtil.getBresenham3DPoints(pos.getX(), pos.getY(), pos.getZ(), endPos.getX(), endPos.getY(), endPos.getZ());
						for (int[] point : points) {
							BlockPos rootPos = new BlockPos(point[0], point[1], point[2]);
							if (!rootPos.equals(pos)) {
								if (level.isStateAtPosition(rootPos, state -> state.is(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH) || state.is(BlockTags.LEAVES) || state.isAir() || state.canBeReplaced() || state.getBlock() instanceof LiquidBlock)) {
									context.setBlock(rootPos, level.isStateAtPosition(rootPos, state -> state.is(BlockTags.DIRT)) ? ESBlocks.MUDDY_BANYIN_ROOTS.get().defaultBlockState() : ESBlocks.BANYIN_ROOTS.get().defaultBlockState().setValue(MangroveRootsBlock.WATERLOGGED, level.isStateAtPosition(rootPos, state -> state.getBlock() instanceof LiquidBlock)));
									if (context.isAir(rootPos.above()) && random.nextInt(5) == 0) {
										context.setBlock(rootPos.above(), ESBlocks.FANTASY_GRASS_CARPET.get().defaultBlockState());
									}
								} else {
									break;
								}
							}
						}
					}
				}
			});
		}
	}
}
