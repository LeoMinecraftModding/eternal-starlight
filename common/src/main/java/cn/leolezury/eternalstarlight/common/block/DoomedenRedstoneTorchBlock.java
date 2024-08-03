package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class DoomedenRedstoneTorchBlock extends BaseTorchBlock {
	public static final MapCodec<DoomedenRedstoneTorchBlock> CODEC = simpleCodec(DoomedenRedstoneTorchBlock::new);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public DoomedenRedstoneTorchBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, true));
	}

	@Override
	protected MapCodec<? extends BaseTorchBlock> codec() {
		return CODEC;
	}

	@Override
	public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		Direction[] var6 = Direction.values();
		int var7 = var6.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			Direction direction = var6[var8];
			level.updateNeighborsAt(blockPos.relative(direction), this);
		}
	}

	@Override
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!bl) {
			Direction[] var6 = Direction.values();
			int var7 = var6.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				Direction direction = var6[var8];
				level.updateNeighborsAt(blockPos.relative(direction), this);
			}
		}
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return blockState.getValue(LIT) && Direction.UP != direction ? 15 : 0;
	}

	protected boolean hasNeighborSignal(Level level, BlockPos blockPos) {
		return level.hasSignal(blockPos.below(), Direction.DOWN);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		level.setBlockAndUpdate(blockPos, blockState.setValue(LIT, !blockState.getValue(LIT)));
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
		if (blockState.getValue(LIT) == this.hasNeighborSignal(level, blockPos) && !level.getBlockTicks().willTickThisTick(blockPos, this)) {
			level.scheduleTick(blockPos, this, 2);
		}

	}

	@Override
	public int getDirectSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return direction == Direction.DOWN ? blockState.getSignal(blockGetter, blockPos, direction) : 0;
	}

	@Override
	public boolean isSignalSource(BlockState blockState) {
		return true;
	}

	@Override
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
		if (blockState.getValue(LIT)) {
			double d = (double) blockPos.getX() + 0.5 + (randomSource.nextDouble() - 0.5) * 0.2;
			double e = (double) blockPos.getY() + 0.7 + (randomSource.nextDouble() - 0.5) * 0.2;
			double f = (double) blockPos.getZ() + 0.5 + (randomSource.nextDouble() - 0.5) * 0.2;
			level.addParticle(DustParticleOptions.REDSTONE, d, e, f, 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
}
