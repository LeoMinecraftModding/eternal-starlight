package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

// Backported from 26.1
public class AttachedToLogsDecorator extends TreeDecorator {
	public static final MapCodec<AttachedToLogsDecorator> CODEC = RecordCodecBuilder.mapCodec(
		i -> i.group(
				Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(p -> p.probability),
				BlockStateProvider.CODEC.fieldOf("decoration").forGetter(p -> p.decoration),
				ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter(p -> p.directions)
			)
			.apply(i, AttachedToLogsDecorator::new)
	);
	private final float probability;
	private final BlockStateProvider decoration;
	private final List<Direction> directions;

	public AttachedToLogsDecorator(float probability, BlockStateProvider decoration, List<Direction> directions) {
		this.probability = probability;
		this.decoration = decoration;
		this.directions = directions;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return ESTreeDecorators.ATTACHED_TO_LOGS.get();
	}

	@Override
	public void place(TreeDecorator.Context context) {
		RandomSource random = context.random();

		for (BlockPos logsPos : Util.shuffledCopy(context.logs(), random)) {
			Direction direction = Util.getRandom(this.directions, random);
			BlockPos placementPos = logsPos.relative(direction);
			if (random.nextFloat() <= this.probability && context.isAir(placementPos)) {
				context.setBlock(placementPos, this.decoration.getState(random, placementPos));
			}
		}
	}
}
