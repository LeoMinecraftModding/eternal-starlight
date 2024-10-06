package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.block.BerriesVinesBlock;
import cn.leolezury.eternalstarlight.common.block.BerriesVinesPlantBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TrunkBerriesDecorator extends TreeDecorator {
	private final IntProvider length;

	public static final MapCodec<TrunkBerriesDecorator> CODEC = IntProvider.codec(1, 16).fieldOf("length").xmap(TrunkBerriesDecorator::new, (decorator) -> decorator.length);
	;

	public TrunkBerriesDecorator(IntProvider length) {
		this.length = length;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return ESTreeDecorators.TRUNK_BERRIES.get();
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		context.logs().forEach((pos) -> {
			int l = length.sample(random);
			for (int i = 1; i <= l; i++) {
				if (context.isAir(pos.below(i))) {
					context.setBlock(pos.below(i), ESBlocks.BERRIES_VINES_PLANT.get().defaultBlockState().setValue(BerriesVinesPlantBlock.BERRIES, random.nextInt(4) == 0));
					if (i == l) {
						context.setBlock(pos.below(i), ESBlocks.BERRIES_VINES.get().defaultBlockState().setValue(BerriesVinesBlock.BERRIES, random.nextInt(4) == 0));
					}
				} else {
					if (i != 1) {
						context.setBlock(pos.below(i - 1), ESBlocks.BERRIES_VINES.get().defaultBlockState().setValue(BerriesVinesBlock.BERRIES, random.nextInt(4) == 0));
					}
					break;
				}
			}
		});
	}
}
