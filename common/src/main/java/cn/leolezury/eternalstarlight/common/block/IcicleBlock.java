package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IcicleBlock extends Block implements SimpleWaterloggedBlock {
	public static final MapCodec<IcicleBlock> CODEC = simpleCodec(IcicleBlock::new);
	public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	public static final EnumProperty<IcicleThickness> THICKNESS = EnumProperty.create("icicle_thickness", IcicleThickness.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
	private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
	private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

	public IcicleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, IcicleThickness.TIP).setValue(WATERLOGGED, false));
	}

	@Override
	protected MapCodec<IcicleBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return getSuitableState(defaultBlockState().setValue(TIP_DIRECTION, context.getNearestLookingVerticalDirection().getOpposite()), context.getLevel(), context.getClickedPos(), false);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		BlockState newState = direction.getAxis() == Direction.Axis.Y ? getSuitableState(state, level, pos, true) : state;
		if (newState.isAir() && state.getValue(TIP_DIRECTION) == Direction.DOWN) {
			level.scheduleTick(pos, this, 2);
			newState = state;
		}
		return newState;
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (getSuitableState(state, level, pos, false).isAir()) {
			spawnFallingStalactite(state, level, pos);
		}
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == IcicleThickness.TIP) {
			entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
		} else {
			super.fallOn(level, state, pos, entity, fallDistance);
		}
	}

	private BlockState getSuitableState(BlockState state, LevelAccessor level, BlockPos pos, boolean updateOthers) {
		Direction tipDir = state.getValue(TIP_DIRECTION);
		BlockPos tipPos = findTip(state, level, pos);
		if (tipPos == null) {
			return Blocks.AIR.defaultBlockState();
		}
		BlockPos basePos = findBase(state, level, pos);
		if (basePos == null) {
			return Blocks.AIR.defaultBlockState();
		}
		if (!canSurvive(state, level, pos)) {
			return Blocks.AIR.defaultBlockState();
		}
		switch (tipPos.distManhattan(basePos)) {
			case 0 -> {
				return state.setValue(THICKNESS, IcicleThickness.TIP);
			}
			case 1 -> {
				if (updateOthers) {
					if (pos.equals(tipPos)) {
						BlockState original = level.getBlockState(basePos);
						if (original.is(this) && original.getValue(THICKNESS) != IcicleThickness.TOP) {
							level.setBlock(basePos, original.setValue(THICKNESS, IcicleThickness.TOP), Block.UPDATE_ALL);
						}
					} else {
						BlockState original = level.getBlockState(tipPos);
						if (original.is(this) && original.getValue(THICKNESS) != IcicleThickness.TIP) {
							level.setBlock(tipPos, original.setValue(THICKNESS, IcicleThickness.TIP), Block.UPDATE_ALL);
						}
					}
				}
				return state.setValue(THICKNESS, pos.equals(tipPos) ? IcicleThickness.TIP : IcicleThickness.TOP);
			}
			case 2 -> {
				BlockPos topPos = basePos.relative(tipDir, 1);
				IcicleThickness thickness;
				if (pos.equals(tipPos)) {
					thickness = IcicleThickness.TIP;
				} else if (pos.equals(topPos)) {
					thickness = IcicleThickness.TOP;
				} else {
					thickness = IcicleThickness.BASE;
				}
				if (updateOthers) {
					for (int i = 0; i < 3; i++) {
						BlockPos partPos = basePos.relative(tipDir, i);
						IcicleThickness partThickness = switch (i) {
							case 0 -> IcicleThickness.BASE;
							case 1 -> IcicleThickness.TOP;
							default -> IcicleThickness.TIP;
						};
						if (!pos.equals(partPos)) {
							BlockState original = level.getBlockState(partPos);
							if (original.is(this) && original.getValue(THICKNESS) != partThickness) {
								level.setBlock(partPos, original.setValue(THICKNESS, partThickness), Block.UPDATE_ALL);
							}
						}
					}
				}
				return state.setValue(THICKNESS, thickness);
			}
			case 3 -> {
				BlockPos topPos = basePos.relative(tipDir, 2);
				BlockPos middlePos = basePos.relative(tipDir, 1);
				IcicleThickness thickness;
				if (pos.equals(tipPos)) {
					thickness = IcicleThickness.TIP;
				} else if (pos.equals(topPos)) {
					thickness = IcicleThickness.TOP;
				} else if (pos.equals(middlePos)) {
					thickness = IcicleThickness.MIDDLE;
				} else {
					thickness = IcicleThickness.BASE;
				}
				if (updateOthers) {
					for (int i = 0; i < 4; i++) {
						BlockPos partPos = basePos.relative(tipDir, i);
						IcicleThickness partThickness = switch (i) {
							case 0 -> IcicleThickness.BASE;
							case 1 -> IcicleThickness.MIDDLE;
							case 2 -> IcicleThickness.TOP;
							default -> IcicleThickness.TIP;
						};
						if (!pos.equals(partPos)) {
							BlockState original = level.getBlockState(partPos);
							if (original.is(this) && original.getValue(THICKNESS) != partThickness) {
								level.setBlock(partPos, original.setValue(THICKNESS, partThickness), Block.UPDATE_ALL);
							}
						}
					}
				}
				return state.setValue(THICKNESS, thickness);
			}
		}
		return state;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction tipDir = state.getValue(TIP_DIRECTION);
		BlockPos tipPos = findTip(state, level, pos);
		if (tipPos == null) {
			return false;
		}
		BlockPos basePos = findBase(state, level, pos);
		if (basePos == null) {
			return false;
		}
		BlockState support = level.getBlockState(pos.relative(tipDir.getOpposite()));
		return support.isFaceSturdy(level, pos.relative(tipDir.getOpposite()), tipDir) || (support.is(this) && (!support.hasProperty(TIP_DIRECTION) || support.getValue(TIP_DIRECTION) == state.getValue(TIP_DIRECTION)));
	}

	private BlockPos findTip(BlockState state, LevelReader level, BlockPos pos) {
		Direction tipDir = state.getValue(TIP_DIRECTION);
		BlockPos tipPos = null;
		for (int i = 0; i < 4; i++) {
			if (i != 0 && !level.getBlockState(pos.relative(tipDir, i)).is(this)) {
				break;
			}
			if (i == 0) {
				if ((!level.getBlockState(pos.relative(tipDir, i + 1)).is(this)
					|| (level.getBlockState(pos.relative(tipDir, i + 1)).hasProperty(TIP_DIRECTION)
					&& level.getBlockState(pos.relative(tipDir, i + 1)).getValue(TIP_DIRECTION) != tipDir)
				)) {
					tipPos = pos.relative(tipDir, i);
				}
			} else if (level.getBlockState(pos.relative(tipDir, i)).is(this) && level.getBlockState(pos.relative(tipDir, i)).getValue(TIP_DIRECTION) == tipDir && (
				!level.getBlockState(pos.relative(tipDir, i + 1)).is(this)
					|| (level.getBlockState(pos.relative(tipDir, i + 1)).hasProperty(TIP_DIRECTION)
					&& level.getBlockState(pos.relative(tipDir, i + 1)).getValue(TIP_DIRECTION) != level.getBlockState(pos.relative(tipDir, i)).getValue(TIP_DIRECTION))
			)) {
				tipPos = pos.relative(tipDir, i);
			}
		}
		return tipPos;
	}

	private BlockPos findBase(BlockState state, LevelReader level, BlockPos pos) {
		Direction tipDir = state.getValue(TIP_DIRECTION);
		BlockPos basePos = null;
		for (int i = 0; i < 4; i++) {
			if (i != 0 && !level.getBlockState(pos.relative(tipDir.getOpposite(), i)).is(this)) {
				break;
			}
			if (i == 0) {
				if ((!level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).is(this)
					|| (level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).hasProperty(TIP_DIRECTION)
					&& level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).getValue(TIP_DIRECTION) != tipDir)
				)) {
					basePos = pos.relative(tipDir.getOpposite(), i);
				}
			} else if (level.getBlockState(pos.relative(tipDir.getOpposite(), i)).is(this) && level.getBlockState(pos.relative(tipDir.getOpposite(), i)).getValue(TIP_DIRECTION) == tipDir && (
				!level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).is(this)
					|| (level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).hasProperty(TIP_DIRECTION)
					&& level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).getValue(TIP_DIRECTION) != level.getBlockState(pos.relative(tipDir.getOpposite(), i)).getValue(TIP_DIRECTION))
			)) {
				basePos = pos.relative(tipDir.getOpposite(), i);
			}
		}
		return basePos;
	}

	private void spawnFallingStalactite(BlockState state, Level level, BlockPos pos) {
		BlockPos.MutableBlockPos mutable = pos.mutable();

		for (BlockState blockState = state; blockState.is(this) && blockState.getValue(TIP_DIRECTION) == Direction.DOWN; blockState = level.getBlockState(mutable)) {
			FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, mutable, blockState);
			if (blockState.getValue(THICKNESS) == IcicleThickness.TIP) {
				fallingblockentity.setHurtsEntities(5, 40);
				break;
			}
			level.destroyBlock(mutable, false);
			mutable.move(Direction.DOWN);
		}
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return blockState.getValue(THICKNESS) == IcicleThickness.TIP ? (blockState.getValue(TIP_DIRECTION) == Direction.UP ? TIP_SHAPE_UP : TIP_SHAPE_DOWN) : SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
	}

	public enum IcicleThickness implements StringRepresentable {
		TIP("tip"),
		TOP("top"),
		MIDDLE("middle"),
		BASE("base");

		private final String name;

		IcicleThickness(final String string2) {
			this.name = string2;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
