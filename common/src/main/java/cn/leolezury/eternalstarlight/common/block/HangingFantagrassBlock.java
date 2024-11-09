package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingFantagrassBlock extends GrowingPlantHeadBlock {
	public static final MapCodec<HangingFantagrassBlock> CODEC = simpleCodec(HangingFantagrassBlock::new);
	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public HangingFantagrassBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, false, 0.02);
	}

	@Override
	protected MapCodec<HangingFantagrassBlock> codec() {
		return CODEC;
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
		return 1;
	}

	@Override
	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos attachPos = pos.relative(this.growthDirection.getOpposite());
		BlockState attachState = level.getBlockState(attachPos);
		if (!this.canAttachTo(attachState)) {
			return false;
		} else {
			return attachState.is(this.getHeadBlock()) || attachState.is(this.getBodyBlock()) || attachState.is(BlockTags.LEAVES) || attachState.isFaceSturdy(level, attachPos, this.growthDirection);
		}
	}

	@Override
	protected Block getBodyBlock() {
		return ESBlocks.HANGING_FANTAGRASS_PLANT.get();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
		BlockPos growthDest = pos.relative(this.growthDirection);
		if (this.canGrowInto(serverLevel.getBlockState(growthDest))) {
			serverLevel.setBlockAndUpdate(growthDest, this.getGrowIntoState(state, serverLevel.random));
		}
	}
}
