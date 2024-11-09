package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HangingPlantDecorator extends TreeDecorator {
	private final Holder<Block> plantHead;
	private final Holder<Block> plantBody;
	private final boolean leaves;
	private final float chance;
	private final IntProvider length;

	public static final MapCodec<HangingPlantDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("plant_head").forGetter(o -> o.plantHead),
		BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("plant_body").forGetter(o -> o.plantBody),
		Codec.BOOL.fieldOf("leaves").forGetter(o -> o.leaves),
		Codec.FLOAT.fieldOf("chance").forGetter(o -> o.chance),
		IntProvider.codec(1, 16).fieldOf("length").forGetter(o -> o.length)
	).apply(instance, HangingPlantDecorator::new));

	public HangingPlantDecorator(Holder<Block> plantHead, Holder<Block> plantBody, boolean leaves, float chance, IntProvider length) {
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
		Block head = plantHead.value();
		Block body = plantBody.value();
		(leaves ? context.leaves() : context.logs()).forEach((pos) -> {
			if (random.nextFloat() < chance) {
				int l = length.sample(random);
				for (int i = 1; i <= l; i++) {
					if (context.isAir(pos.below(i))) {
						context.setBlock(pos.below(i), body.defaultBlockState());
						if (i == l) {
							context.setBlock(pos.below(i), head.defaultBlockState());
						}
					} else {
						if (i != 1) {
							context.setBlock(pos.below(i - 1), head.defaultBlockState());
						}
						break;
					}
				}
			}
		});
	}
}
