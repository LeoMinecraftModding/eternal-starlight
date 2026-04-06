package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ThioquartzBlock extends Block {
	public static final MapCodec<ThioquartzBlock> CODEC = simpleCodec(ThioquartzBlock::new);
	public static final BooleanProperty SEED = BooleanProperty.create("seed");

	public ThioquartzBlock(Properties properties) {
		super(properties);
		registerDefaultState(this.defaultBlockState().setValue(SEED, false));
	}

	@Override
	protected MapCodec<ThioquartzBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SEED);
	}
}
