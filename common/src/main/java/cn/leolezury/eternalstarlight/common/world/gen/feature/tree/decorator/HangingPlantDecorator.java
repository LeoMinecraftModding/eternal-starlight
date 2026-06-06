package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HangingPlantDecorator extends TreeDecorator {
	private final BlockStateProvider plantHead;
	private final BlockStateProvider plantBody;
	private final boolean leaves;
	private final float chance;
	private final IntProvider length;

	public static final MapCodec<HangingPlantDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("plant_head").forGetter(o -> o.plantHead),
		BlockStateProvider.CODEC.fieldOf("plant_body").forGetter(o -> o.plantBody),
		Codec.BOOL.fieldOf("leaves").forGetter(o -> o.leaves),
		Codec.FLOAT.fieldOf("chance").forGetter(o -> o.chance),
		IntProvider.codec(1, 16).fieldOf("length").forGetter(o -> o.length)
	).apply(instance, HangingPlantDecorator::new));

	public HangingPlantDecorator(BlockStateProvider plantHead, BlockStateProvider plantBody, boolean leaves, float chance, IntProvider length) {
		this.plantHead = plantHead;
		this.plantBody = plantBody;
		this.leaves = leaves;
		this.chance = chance;
		this.length = length;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return ESTreeDecorators.HANGING_PLANT.get();
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		(leaves ? context.leaves() : context.logs()).forEach((pos) -> {
			if (random.nextFloat() < chance) {
				int l = length.sample(random);
				for (int i = 1; i <= l; i++) {
					if (context.isAir(pos.below(i))) {
						context.setBlock(pos.below(i), plantBody.getState(random, pos.below(i)));
						if (i == l) {
							context.setBlock(pos.below(i), plantHead.getState(random, pos.below(i)));
						}
					} else {
						if (i != 1) {
							context.setBlock(pos.below(i - 1), plantHead.getState(random, pos.below(i - 1)));
						}
						break;
					}
				}
			}
		});
	}
}
