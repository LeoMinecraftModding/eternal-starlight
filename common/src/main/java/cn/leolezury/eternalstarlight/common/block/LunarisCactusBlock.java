package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Iterator;

// Copy of CactusBlock
public class LunarisCactusBlock extends Block {
	public static final MapCodec<LunarisCactusBlock> CODEC = simpleCodec(LunarisCactusBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
	public static final BooleanProperty FRUIT = BooleanProperty.create("fruit");
	protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

	public MapCodec<LunarisCactusBlock> codec() {
		return CODEC;
	}

	public LunarisCactusBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(FRUIT, false));
	}

	protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (!blockState.canSurvive(serverLevel, blockPos)) {
			serverLevel.destroyBlock(blockPos, true);
		}
	}

	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockPos blockPos2 = blockPos.above();
		if (serverLevel.isEmptyBlock(blockPos2) && !blockState.getValue(FRUIT)) {
			int i;

			for (i = 1; serverLevel.getBlockState(blockPos.below(i)).is(this); ++i) {
			}

			if (i <= 3) {
				int j = blockState.getValue(AGE);
				if (j == 15) {
					serverLevel.setBlockAndUpdate(blockPos2, this.defaultBlockState().setValue(FRUIT, i == 3));
					BlockState blockState2 = blockState.setValue(AGE, 0);
					serverLevel.setBlock(blockPos, blockState2, 4);
					serverLevel.neighborChanged(blockState2, blockPos2, this, blockPos, false);
				} else {
					serverLevel.setBlock(blockPos, blockState.setValue(AGE, j + 1), 4);
				}
			}
		}
	}

	protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return COLLISION_SHAPE;
	}

	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return OUTLINE_SHAPE;
	}

	protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (!blockState.canSurvive(levelAccessor, blockPos)) {
			levelAccessor.scheduleTick(blockPos, this, 1);
		}

		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		Iterator<Direction> var4 = Plane.HORIZONTAL.iterator();

		Direction direction;
		BlockState blockState2;
		do {
			if (!var4.hasNext()) {
				BlockState blockState3 = levelReader.getBlockState(blockPos.below());
				// ES: Check if there's a fruit
				boolean validCactus = blockState3.is(this) && !blockState3.getValue(FRUIT);
				return (validCactus || blockState3.is(BlockTags.SAND)) && !levelReader.getBlockState(blockPos.above()).liquid();
			}

			direction = var4.next();
			blockState2 = levelReader.getBlockState(blockPos.relative(direction));
		} while (!blockState2.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA));

		return false;
	}

	protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
		entity.hurt(level.damageSources().cactus(), 1.0F);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE, FRUIT);
	}

	protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
		return false;
	}
}

