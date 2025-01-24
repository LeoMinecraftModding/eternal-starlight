package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RawAmaramberBlock extends Block {
	public static final MapCodec<RawAmaramberBlock> CODEC = simpleCodec(RawAmaramberBlock::new);

	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public RawAmaramberBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(TOP, true));
	}

	@Override
	protected MapCodec<RawAmaramberBlock> codec() {
		return CODEC;
	}

	@Override
	protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		return blockState.setValue(TOP, !levelAccessor.getBlockState(blockPos.above()).is(this));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TOP);
	}
}
