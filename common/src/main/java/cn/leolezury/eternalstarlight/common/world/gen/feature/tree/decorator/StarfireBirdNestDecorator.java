package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Arrays;
import java.util.List;

public class StarfireBirdNestDecorator extends TreeDecorator {
	public static final MapCodec<StarfireBirdNestDecorator> CODEC = MapCodec.unit(() -> StarfireBirdNestDecorator.INSTANCE);
	public static final StarfireBirdNestDecorator INSTANCE = new StarfireBirdNestDecorator();

	@Override
	protected TreeDecoratorType<?> type() {
		return ESTreeDecorators.STARFIRE_BIRD_NEST.get();
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		context.leaves().forEach((pos) -> {
			boolean nearLog = false;
			for (Direction direction : Direction.values()) {
				if (context.logs().contains(pos.relative(direction))) {
					nearLog = true;
					break;
				}
			}
			if (nearLog) {
				BlockPos nestPos = pos.relative(Direction.DOWN);
				if (context.isAir(nestPos) && random.nextInt(100) == 0) {
					List<Direction> availableDirs = Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() != Direction.Axis.Y && context.level().isStateAtPosition(nestPos.relative(direction), BlockState::isAir)).toList();
					if (!availableDirs.isEmpty()) {
						context.setBlock(nestPos, ESBlocks.STARFIRE_BIRD_NEST.get().defaultBlockState().setValue(StarfireBirdNestBlock.FACING, availableDirs.get(random.nextInt(availableDirs.size()))).setValue(StarfireBirdNestBlock.EGGS, random.nextInt(2)));
						context.level().getBlockEntity(nestPos, ESBlockEntities.STARFIRE_BIRD_NEST.get()).ifPresent(nest -> {
							int count = 1 + random.nextInt(2);
							for (int i = 0; i < count; i++) {
								nest.storeBird(StarfireBirdNestBlockEntity.Occupant.create(random, random.nextInt(599)));
							}
						});
					}
				}
			}
		});
	}
}
