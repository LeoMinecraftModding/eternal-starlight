package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NocturnalMilletBottomBlock extends CropBlock implements NocturnalMillet{
	public static final MapCodec<NocturnalMilletBottomBlock> CODEC = simpleCodec(NocturnalMilletBottomBlock::new);
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(3.0, 0.0, 3.0, 13.0, 9.0, 13.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

	@Override
	public MapCodec<NocturnalMilletBottomBlock> codec() {
		return CODEC;
	}

	public NocturnalMilletBottomBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(this.getAgeProperty(), 0).setValue(FORGOTTEN, false).setValue(WITHER, false));
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(BlockTags.DIRT) || blockState.is(ESTags.Blocks.FORGOTTEN_NOCTURNAL_MILLET_CONVERTIBLES);
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ESItems.NOCTURNAL_MILLET_SEEDS.get();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state) > 0 ? 1 : 0];
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FORGOTTEN, WITHER);
	}

	@Override
	protected boolean isRandomlyTicking(BlockState blockState) {
		return !blockState.getValue(WITHER);
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockPos above = blockPos.above();
		if (serverLevel.getRawBrightness(blockPos, 0) >= 7) {
			int i = this.getAge(blockState);
			if (i < this.getMaxAge()) {
				float f = getGrowthSpeed(this, serverLevel, blockPos);
				if (randomSource.nextInt((int)(25.0F / f) + 1) == 0) {
					serverLevel.setBlock(blockPos, blockState.setValue(AGE, getAge(blockState) + 1), 2);
				}
			} else if (serverLevel.getBlockState(above).isAir()) {
				serverLevel.setBlockAndUpdate(above, ESBlocks.NOCTURNAL_MILLET_PANICLE.get().defaultBlockState().setValue(FORGOTTEN, blockState.getValue(FORGOTTEN)));
			}
		}
	}

	protected static float getGrowthSpeed(Block block, BlockGetter blockGetter, BlockPos blockPos) {
		float f = 1.0F;
		BlockPos blockPos2 = blockPos.below();

		for(int i = -1; i <= 1; ++i) {
			for(int j = -1; j <= 1; ++j) {
				float g = 0.0F;
				BlockState blockState = blockGetter.getBlockState(blockPos2.offset(i, 0, j));
				if (blockState.is(Blocks.FARMLAND)) {
					g = 1.0F;
					if ((Integer)blockState.getValue(FarmBlock.MOISTURE) > 0) {
						g = 3.0F;
					}
				}

				if (i != 0 || j != 0) {
					g /= 4.0F;
				}

				f += g;
			}
		}

		BlockPos blockPos3 = blockPos.north();
		BlockPos blockPos4 = blockPos.south();
		BlockPos blockPos5 = blockPos.west();
		BlockPos blockPos6 = blockPos.east();
		boolean bl = blockGetter.getBlockState(blockPos5).is(block) || blockGetter.getBlockState(blockPos6).is(block);
		boolean bl2 = blockGetter.getBlockState(blockPos3).is(block) || blockGetter.getBlockState(blockPos4).is(block);
		if (bl && bl2) {
			f /= 2.0F;
		} else {
			boolean bl3 = blockGetter.getBlockState(blockPos5.north()).is(block) || blockGetter.getBlockState(blockPos6.north()).is(block) || blockGetter.getBlockState(blockPos6.south()).is(block) || blockGetter.getBlockState(blockPos5.south()).is(block);
			if (bl3) {
				f /= 2.0F;
			}
		}

		return f;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return blockState.getValue(WITHER) || !blockState.getValue(FORGOTTEN) || super.isValidBonemealTarget(levelReader, blockPos, blockState);
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		BlockPos abovePos = blockPos.above();
		BlockState aboveBlockState = serverLevel.getBlockState(abovePos);
		if (blockState.getValue(WITHER)) {
			serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(WITHER, false));
			if (aboveBlockState.is(ESBlocks.NOCTURNAL_MILLET_PANICLE.get()) && aboveBlockState.getValue(WITHER)) {
				serverLevel.setBlockAndUpdate(abovePos, aboveBlockState.setValue(WITHER, false));
			}
		} else if (serverLevel.getBlockState(blockPos.below()).is(ESTags.Blocks.FORGOTTEN_NOCTURNAL_MILLET_CONVERTIBLES) && !blockState.getValue(FORGOTTEN)) {
			serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(FORGOTTEN, true));
			if (aboveBlockState.is(ESBlocks.NOCTURNAL_MILLET_PANICLE.get()) && !aboveBlockState.getValue(FORGOTTEN)) {
				serverLevel.setBlockAndUpdate(abovePos, aboveBlockState.setValue(FORGOTTEN, true));
			}
		} else {
			this.growCrops(serverLevel, blockPos, blockState);
		}
	}

	@Override
	public void growCrops(Level level, BlockPos blockPos, BlockState blockState) {
		int i = this.getAge(blockState) + this.getBonemealAgeIncrease(level);
		int j = this.getMaxAge();
		if (i > j) {
			i = j;
		}
		level.setBlock(blockPos, blockState.setValue(AGE, i), 2);
	}
}
