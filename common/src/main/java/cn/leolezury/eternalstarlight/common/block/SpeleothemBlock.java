package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class SpeleothemBlock extends Block implements SimpleWaterloggedBlock, Fallable {
	public static final EnumProperty<Direction> TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	public static final EnumProperty<SpeleothemThickness> THICKNESS = EnumProperty.create("thickness", SpeleothemThickness.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final int DELAY_BEFORE_FALLING = 2;
	private static final double MIN_TRIDENT_VELOCITY_TO_BREAK = 0.6;
	private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
	private static final int STALACTITE_MAX_DAMAGE = 40;
	private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
	private static final VoxelShape SHAPE_TIP_MERGE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
	private static final VoxelShape SHAPE_TIP_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
	protected static final VoxelShape SHAPE_TIP_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
	private static final VoxelShape SHAPE_FRUSTUM = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
	private static final VoxelShape SHAPE_MIDDLE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
	private static final VoxelShape SHAPE_BASE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
	private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
	private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
	private static final int MAX_GROWTH_LENGTH = 7;
	private static final int MAX_STALAGMITE_SEARCH_RANGE = 10;
	protected final BlockState blockToGrowOn;

	@Override
	public abstract MapCodec<? extends SpeleothemBlock> codec();

	public SpeleothemBlock(BlockState blockToGrowOn, Properties properties) {
		super(properties);
		this.blockToGrowOn = blockToGrowOn;
		this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, SpeleothemThickness.TIP).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return isValidSpeleothemPlacement(level, pos, state.getValue(TIP_DIRECTION));
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction directionToNeighbour, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		if (directionToNeighbour != Direction.UP && directionToNeighbour != Direction.DOWN) {
			return state;
		}
		Direction tipDirection = state.getValue(TIP_DIRECTION);
		if (tipDirection == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this)) {
			return state;
		}
		if (directionToNeighbour == tipDirection.getOpposite() && !this.canSurvive(state, level, pos)) {
			level.scheduleTick(pos, this, tipDirection == Direction.DOWN ? DELAY_BEFORE_FALLING : 1);
			return state;
		}
		boolean mergeOpposingTips = state.getValue(THICKNESS) == SpeleothemThickness.TIP_MERGE;
		SpeleothemThickness newThickness = calculateSpeleothemThickness(level, pos, tipDirection, mergeOpposingTips);
		return state.setValue(THICKNESS, newThickness);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction defaultTipDirection = context.getNearestLookingVerticalDirection().getOpposite();
		Direction tipDirection = calculateTipDirection(level, pos, defaultTipDirection);
		if (tipDirection == null) {
			return null;
		}
		boolean mergeOpposingTips = !context.isSecondaryUseActive();
		SpeleothemThickness thickness = calculateSpeleothemThickness(level, pos, tipDirection, mergeOpposingTips);
		return this.defaultBlockState().setValue(TIP_DIRECTION, tipDirection).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
	}

	@Nullable
	private Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction defaultTipDirection) {
		if (isValidSpeleothemPlacement(level, pos, defaultTipDirection)) {
			return defaultTipDirection;
		}
		if (!isValidSpeleothemPlacement(level, pos, defaultTipDirection.getOpposite())) {
			return null;
		}
		return defaultTipDirection.getOpposite();
	}

	private SpeleothemThickness calculateSpeleothemThickness(LevelReader level, BlockPos pos, Direction tipDirection, boolean mergeOpposingTips) {
		Direction baseDirection = tipDirection.getOpposite();
		BlockState inFrontState = level.getBlockState(pos.relative(tipDirection));
		if (isSpeleothemWithDirection(inFrontState, baseDirection) && inFrontState.is(this)) {
			return !mergeOpposingTips && inFrontState.getValue(THICKNESS) != SpeleothemThickness.TIP_MERGE ? SpeleothemThickness.TIP : SpeleothemThickness.TIP_MERGE;
		}
		if (!isSpeleothemWithDirection(inFrontState, tipDirection)) {
			return SpeleothemThickness.TIP;
		}
		SpeleothemThickness inFrontThickness = inFrontState.getValue(THICKNESS);
		if (inFrontThickness != SpeleothemThickness.TIP && inFrontThickness != SpeleothemThickness.TIP_MERGE) {
			BlockState behindState = level.getBlockState(pos.relative(baseDirection));
			return !isSpeleothemWithDirection(behindState, tipDirection) ? SpeleothemThickness.BASE : SpeleothemThickness.MIDDLE;
		}
		return SpeleothemThickness.FRUSTUM;
	}

	private boolean isValidSpeleothemPlacement(LevelReader level, BlockPos pos, Direction tipDirection) {
		BlockPos behindPos = pos.relative(tipDirection.getOpposite());
		BlockState behindState = level.getBlockState(behindPos);
		return behindState.isFaceSturdy(level, behindPos, tipDirection) || isSpeleothemWithDirection(behindState, tipDirection) && behindState.is(this);
	}

	public static boolean isSpeleothemWithDirection(BlockState blockState, Direction tipDirection) {
		return blockState.is(ESTags.Blocks.SPELEOTHEMS) && blockState.getValue(TIP_DIRECTION) == tipDirection;
	}

	@Override
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult blockHit, Projectile projectile) {
		if (!level.isClientSide) {
			BlockPos pos = blockHit.getBlockPos();
			if (projectile.mayInteract(level, pos) && projectile.mayBreak(level) && projectile.getDeltaMovement().length() > MIN_TRIDENT_VELOCITY_TO_BREAK) {
				level.destroyBlock(pos, true);
			}
		}
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, true);
		} else {
			spawnFallingStalactite(state, level, pos);
		}
	}

	private static void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
		BlockPos.MutableBlockPos fallPos = pos.mutable();
		BlockState fallState = state;

		while (isStalactite(fallState)) {
			FallingBlockEntity entity = FallingBlockEntity.fall(level, fallPos, fallState);
			if (isTip(fallState, true)) {
				int size = Math.max(1 + pos.getY() - fallPos.getY(), MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION);
				float damage = STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE * (float) size;
				entity.setHurtsEntities(damage, STALACTITE_MAX_DAMAGE);
				break;
			}
			fallPos.move(Direction.DOWN);
			fallState = level.getBlockState(fallPos);
		}
	}

	public static boolean isStalagmite(BlockState state) {
		return isSpeleothemWithDirection(state, Direction.UP);
	}

	public static boolean isStalactite(BlockState state) {
		return isSpeleothemWithDirection(state, Direction.DOWN);
	}

	public static boolean isTip(BlockState state, boolean includeMergedTip) {
		if (!state.is(ESTags.Blocks.SPELEOTHEMS)) {
			return false;
		}
		SpeleothemThickness thickness = state.getValue(THICKNESS);
		return thickness == SpeleothemThickness.TIP || (includeMergedTip && thickness == SpeleothemThickness.TIP_MERGE);
	}

	@Override
	public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity) {
		if (!entity.isSilent()) {
			level.levelEvent(getStalactiteLandingSound(), pos, 0);
		}
	}

	protected abstract int getStalactiteLandingSound();

	@Override
	public DamageSource getFallDamageSource(Entity entity) {
		return entity.damageSources().fallingStalactite(entity);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = switch (state.getValue(THICKNESS)) {
			case TIP_MERGE -> SHAPE_TIP_MERGE;
			case TIP -> state.getValue(TIP_DIRECTION) == Direction.DOWN ? SHAPE_TIP_DOWN : SHAPE_TIP_UP;
			case FRUSTUM -> SHAPE_FRUSTUM;
			case MIDDLE -> SHAPE_MIDDLE;
			case BASE -> SHAPE_BASE;
		};
		Vec3 vec3 = state.getOffset(level, pos);
		return shape.move(vec3.x, 0.0, vec3.z);
	}

	@Override
	protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return false;
	}

	@Override
	protected float getMaxHorizontalOffset() {
		return MAX_HORIZONTAL_OFFSET;
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextFloat() < GROWTH_PROBABILITY_PER_RANDOM_TICK && isStalactiteStartPos(state, level, pos)) {
			growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
		}
	}

	public static boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
		return isStalactite(state) && !level.getBlockState(pos.above()).is(state.getBlock());
	}

	@VisibleForTesting
	public void growStalactiteOrStalagmiteIfPossible(BlockState stalactiteStartState, ServerLevel level, BlockPos stalactiteStartPos, RandomSource random) {
		if (canGrow(level, stalactiteStartPos)) {
			BlockPos stalactiteTipPos = findTip(stalactiteStartState, level, stalactiteStartPos, MAX_GROWTH_LENGTH, false);
			if (stalactiteTipPos != null) {
				BlockState stalactiteTipState = level.getBlockState(stalactiteTipPos);
				if (isFreeHangingStalactite(stalactiteTipState) && canTipGrow(stalactiteTipState, level, stalactiteTipPos)) {
					if (random.nextBoolean()) {
						grow(level, stalactiteTipPos, Direction.DOWN);
					} else {
						growStalagmiteBelow(level, stalactiteTipPos);
					}
				}
			}
		}
	}

	protected boolean canGrow(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.above()).is(blockToGrowOn.getBlock());
	}

	@Nullable
	public static BlockPos findTip(BlockState speleothemState, LevelAccessor level, BlockPos speleothemPos, int maxSearchLength, boolean includeMergedTip) {
		if (isTip(speleothemState, includeMergedTip)) {
			return speleothemPos;
		}
		Direction searchDirection = speleothemState.getValue(TIP_DIRECTION);
		BiPredicate<BlockPos, BlockState> pathPredicate = (pos, state) -> state.is(speleothemState.getBlock()) && state.getValue(TIP_DIRECTION) == searchDirection;
		return findBlockVertical(level, speleothemPos, searchDirection.getAxisDirection(), pathPredicate, st -> isTip(st, includeMergedTip), maxSearchLength).orElse(null);
	}

	public static Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection axisDirection, BiPredicate<BlockPos, BlockState> pathPredicate, Predicate<BlockState> targetPredicate, int maxSteps) {
		Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 1; i < maxSteps; i++) {
			mutablePos.move(direction);
			BlockState state = level.getBlockState(mutablePos);
			if (targetPredicate.test(state)) {
				return Optional.of(mutablePos.immutable());
			}
			if (level.isOutsideBuildHeight(mutablePos.getY()) || !pathPredicate.test(mutablePos, state)) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	private boolean canTipGrow(BlockState tipState, ServerLevel level, BlockPos tipPos) {
		Direction growDirection = tipState.getValue(TIP_DIRECTION);
		BlockPos growPos = tipPos.relative(growDirection);
		BlockState stateAtGrowPos = level.getBlockState(growPos);
		if (!stateAtGrowPos.getFluidState().isEmpty()) {
			return false;
		}
		return stateAtGrowPos.isAir() || isUnmergedTipWithDirection(stateAtGrowPos, growDirection.getOpposite());
	}

	private boolean isUnmergedTipWithDirection(BlockState state, Direction tipDirection) {
		return isTip(state, false) && state.getValue(TIP_DIRECTION) == tipDirection && state.is(this);
	}

	private void grow(ServerLevel level, BlockPos growFromPos, Direction growToDirection) {
		BlockPos targetPos = growFromPos.relative(growToDirection);
		BlockState existingStateAtTargetPos = level.getBlockState(targetPos);
		if (isUnmergedTipWithDirection(existingStateAtTargetPos, growToDirection.getOpposite())) {
			createMergedTips(existingStateAtTargetPos, level, targetPos);
		} else if (existingStateAtTargetPos.isAir() || existingStateAtTargetPos.is(Blocks.WATER)) {
			createSpeleothem(level, targetPos, growToDirection, SpeleothemThickness.TIP);
		}
	}

	private void createSpeleothem(LevelAccessor level, BlockPos pos, Direction direction, SpeleothemThickness thickness) {
		BlockState state = this.defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
		level.setBlock(pos, state, Block.UPDATE_ALL);
	}

	private void createMergedTips(BlockState tipState, LevelAccessor level, BlockPos tipPos) {
		BlockPos stalactitePos;
		BlockPos stalagmitePos;
		if (tipState.getValue(TIP_DIRECTION) == Direction.UP) {
			stalagmitePos = tipPos;
			stalactitePos = tipPos.above();
		} else {
			stalactitePos = tipPos;
			stalagmitePos = tipPos.below();
		}
		createSpeleothem(level, stalactitePos, Direction.DOWN, SpeleothemThickness.TIP_MERGE);
		createSpeleothem(level, stalagmitePos, Direction.UP, SpeleothemThickness.TIP_MERGE);
	}

	private void growStalagmiteBelow(ServerLevel level, BlockPos posAboveStalagmite) {
		BlockPos.MutableBlockPos pos = posAboveStalagmite.mutable();
		for (int i = 0; i < MAX_STALAGMITE_SEARCH_RANGE; i++) {
			pos.move(Direction.DOWN);
			BlockState state = level.getBlockState(pos);
			if (!state.getFluidState().isEmpty()) {
				return;
			}
			if (isUnmergedTipWithDirection(state, Direction.UP) && canTipGrow(state, level, pos)) {
				grow(level, pos, Direction.UP);
				return;
			}
			if (isValidSpeleothemPlacement(level, pos, Direction.UP) && !level.isWaterAt(pos.below())) {
				grow(level, pos.below(), Direction.UP);
				return;
			}
			if (blocksStalagmiteScan(level, pos, state)) {
				return;
			}
		}
	}

	protected boolean blocksStalagmiteScan(LevelReader level, BlockPos pos, BlockState state) {
		return false;
	}

	public static boolean isFreeHangingStalactite(BlockState state) {
		return isStalactite(state) && state.getValue(THICKNESS) == SpeleothemThickness.TIP && !state.getValue(WATERLOGGED);
	}
}
