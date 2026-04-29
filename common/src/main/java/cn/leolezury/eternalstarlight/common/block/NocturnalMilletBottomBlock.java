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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NocturnalMilletBottomBlock extends CropBlock {
	public static final MapCodec<NocturnalMilletBottomBlock> CODEC = simpleCodec(NocturnalMilletBottomBlock::new);
	public static final BooleanProperty FORGOTTEN = NocturnalMilletTopBlock.FORGOTTEN;
	public static final BooleanProperty WITHERED = NocturnalMilletTopBlock.WITHERED;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(3.0, 0.0, 3.0, 13.0, 9.0, 13.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

	@Override
	public MapCodec<NocturnalMilletBottomBlock> codec() {
		return CODEC;
	}

	public NocturnalMilletBottomBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(AGE, 0).setValue(FORGOTTEN, false).setValue(WITHERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FORGOTTEN, WITHERED);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.DIRT) || state.is(ESTags.Blocks.CONVERTS_NOCTURNAL_MILLET);
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return !state.getValue(WITHERED);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BlockPos abovePos = pos.above();
		if (level.getRawBrightness(pos, 0) >= 7) {
			int age = this.getAge(state);
			if (age < this.getMaxAge()) {
				float growthSpeed = getGrowthSpeed(this, level, pos);
				if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
					level.setBlock(pos, state.setValue(AGE, getAge(state) + 1), Block.UPDATE_CLIENTS);
				}
			} else if (level.getBlockState(abovePos).isAir()) {
				level.setBlockAndUpdate(abovePos, ESBlocks.NOCTURNAL_MILLET_PANICLE.get().defaultBlockState().setValue(FORGOTTEN, state.getValue(FORGOTTEN)));
			}
		}
	}

	protected static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
		float speed = 1.0F;
		BlockPos belowPos = pos.below();

		for (int x = -1; x <= 1; ++x) {
			for (int z = -1; z <= 1; ++z) {
				float speedAddition = 0.0F;
				BlockState state = level.getBlockState(belowPos.offset(x, 0, z));
				if (state.is(Blocks.FARMLAND)) {
					speedAddition = 1.0F;
					if (state.getValue(FarmBlock.MOISTURE) > 0) {
						speedAddition = 3.0F;
					}
				}

				if (x != 0 || z != 0) {
					speedAddition /= 4.0F;
				}

				speed += speedAddition;
			}
		}

		BlockPos north = pos.north();
		BlockPos south = pos.south();
		BlockPos west = pos.west();
		BlockPos east = pos.east();
		boolean westEast = level.getBlockState(west).is(block) || level.getBlockState(east).is(block);
		boolean northSouth = level.getBlockState(north).is(block) || level.getBlockState(south).is(block);
		if (westEast && northSouth) {
			speed /= 2.0F;
		} else {
			boolean corners = level.getBlockState(west.north()).is(block) || level.getBlockState(east.north()).is(block) || level.getBlockState(east.south()).is(block) || level.getBlockState(west.south()).is(block);
			if (corners) {
				speed /= 2.0F;
			}
		}

		return speed;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return state.getValue(WITHERED) || !state.getValue(FORGOTTEN) || super.isValidBonemealTarget(level, pos, state);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = level.getBlockState(abovePos);
		if (state.getValue(WITHERED)) {
			level.setBlockAndUpdate(pos, state.setValue(WITHERED, false));
			if (aboveState.is(ESBlocks.NOCTURNAL_MILLET_PANICLE.get()) && aboveState.getValue(WITHERED)) {
				level.setBlockAndUpdate(abovePos, aboveState.setValue(WITHERED, false));
			}
		} else if (level.getBlockState(pos.below()).is(ESTags.Blocks.CONVERTS_NOCTURNAL_MILLET) && !state.getValue(FORGOTTEN)) {
			level.setBlockAndUpdate(pos, state.setValue(FORGOTTEN, true));
		} else {
			this.growCrops(level, pos, state);
		}
	}

	@Override
	public void growCrops(Level level, BlockPos pos, BlockState state) {
		int targetAge = this.getAge(state) + this.getBonemealAgeIncrease(level);
		int maxAge = this.getMaxAge();
		if (targetAge > maxAge) {
			targetAge = maxAge;
		}
		level.setBlock(pos, state.setValue(AGE, targetAge), Block.UPDATE_CLIENTS);
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ESItems.NOCTURNAL_MILLET_SEEDS.get();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state) > 0 ? 1 : 0];
	}
}
