package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DesertFlowerBlock extends FlowerBlock {
	public static final MapCodec<DesertFlowerBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
		return instance.group(EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects), propertiesCodec()).apply(instance, DesertFlowerBlock::new);
	});

	public DesertFlowerBlock(Holder<MobEffect> holder, float duration, Properties properties) {
		super(holder, duration, properties);
	}

	public DesertFlowerBlock(SuspiciousStewEffects effects, BlockBehaviour.Properties properties) {
		super(effects, properties);
	}

	@Override
	public MapCodec<DesertFlowerBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(BlockTags.SAND) || blockState.is(ESTags.Blocks.BASE_STONE_STARLIGHT);
	}
}
