package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BouldershroomBlock extends DirectionalBudBlock implements BonemealableBlock {
	public static final MapCodec<BouldershroomBlock> CODEC = simpleCodec(BouldershroomBlock::new);

	public BouldershroomBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected double getShapeWidth() {
		return 6;
	}

	@Override
	protected double getShapeHeight() {
		return 6;
	}

	@Override
	protected MapCodec<BouldershroomBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos attachPos = pos.relative(direction.getOpposite());
		BlockState attachState = level.getBlockState(attachPos);
		return attachState.isFaceSturdy(level, attachPos, direction) && !(attachState.getBlock() instanceof HugeMushroomBlock);
	}

	private static boolean canReplace(BlockState state) {
		return (state.isAir() || state.canBeReplaced()) && !(state.getBlock() instanceof LiquidBlock);
	}

	public static boolean growMushroom(ServerLevelAccessor level, RandomSource random, BlockPos origin, Direction growDirection) {
		BlockPos attachPos = origin.relative(growDirection.getOpposite());
		BlockState attachState = level.getBlockState(attachPos);
		if (!attachState.isFaceSturdy(level, attachPos, growDirection)) {
			return false;
		}
		List<BlockPos> stemPositions = new ArrayList<>();
		List<BlockPos> capPositions = new ArrayList<>();
		List<Direction> randomOffsetDirs = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != growDirection.getAxis()).toList();
		int height = random.nextInt(6, 12);
		int distToNextTurn = random.nextInt(height / 3) + 2;
		BlockPos.MutableBlockPos pos = origin.mutable();
		for (int i = 0; i < height; i++) {
			stemPositions.add(pos.immutable());
			if (!canReplace(level.getBlockState(pos))) {
				return false;
			}
			if (distToNextTurn <= 0) {
				Direction offsetDir = randomOffsetDirs.get(random.nextInt(randomOffsetDirs.size()));
				int turnLength = random.nextInt(height / 3) + 2;
				for (int j = 0; j < turnLength; j++) {
					pos.move(offsetDir);
					stemPositions.add(pos.immutable());
					if (!canReplace(level.getBlockState(pos))) {
						return false;
					}
				}
				distToNextTurn = random.nextInt(height / 3) + 2;
			}
			pos.move(growDirection);
			distToNextTurn--;
		}
		pos.move(growDirection.getOpposite());
		int capRadius = random.nextInt(4, 7);
		Direction capDirection = growDirection != Direction.DOWN ? Direction.UP : Direction.DOWN;
		if (capDirection == Direction.UP && growDirection != Direction.UP) {
			boolean cap = false;
			for (int h = 0; h < height; h++) {
				pos.move(Direction.UP);
				boolean canPlaceCap = true;
				for (int i = -capRadius; i <= capRadius; i++) {
					for (int j = -capRadius; j <= capRadius; j++) {
						if (i * i + j * j <= capRadius * capRadius) {
							BlockPos capPos = pos.offset(i, 0, j);
							if (stemPositions.contains(capPos)) {
								canPlaceCap = false;
							}
						}
					}
				}
				stemPositions.add(pos.immutable());
				if (!canReplace(level.getBlockState(pos))) {
					return false;
				}
				if (canPlaceCap) {
					cap = true;
					break;
				}
			}
			if (!cap) {
				return false;
			}
		}
		List<BlockPos> fullCapPositions = new ArrayList<>();
		for (int layerRadius = capRadius; layerRadius > 0; layerRadius -= 2) {
			for (int i = -layerRadius; i <= layerRadius; i++) {
				for (int j = -layerRadius; j <= layerRadius; j++) {
					if (i * i + j * j <= layerRadius * layerRadius) {
						BlockPos capPos = pos.offset(i, 0, j);
						boolean hollow = layerRadius == capRadius && i * i + j * j <= (layerRadius - 2) * (layerRadius - 2);
						if (!hollow) {
							capPositions.add(capPos);
							if (!canReplace(level.getBlockState(capPos))) {
								return false;
							}
							if (layerRadius != capRadius) {
								for (Direction dir : Direction.values()) {
									if (random.nextInt(8) == 0 && dir != capDirection.getOpposite()) {
										BlockPos extraPos = capPos.relative(dir);
										if (canReplace(level.getBlockState(extraPos)) && !stemPositions.contains(extraPos)) {
											capPositions.add(extraPos);
										}
									}
								}
							}
						}
						fullCapPositions.add(capPos);
					}
				}
			}
			pos.move(capDirection);
		}
		for (BlockPos blockPos : stemPositions) {
			BlockState state = ESBlocks.BOULDERSHROOM_STEM.get().defaultBlockState();
			for (Direction dir : Direction.values()) {
				if (stemPositions.contains(blockPos.relative(dir)) || capPositions.contains(blockPos.relative(dir))) {
					state = state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir), false);
				}
			}
			level.setBlock(blockPos, state, Block.UPDATE_ALL);
			if (random.nextInt(8) == 0) {
				int maxLength = 0;
				int randLength = random.nextInt(1, 4);
				for (int i = 1; i < randLength + 1; i++) {
					BlockPos belowPos = blockPos.below(i);
					if (stemPositions.contains(belowPos) || capPositions.contains(belowPos) || !canReplace(level.getBlockState(belowPos))) {
						break;
					}
					maxLength++;
				}
				for (int i = 1; i <= maxLength; i++) {
					level.setBlock(blockPos.below(i), i == maxLength ? ESBlocks.BOULDERSHROOM_ROOTS.get().defaultBlockState() : ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get().defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}
		for (BlockPos blockPos : capPositions) {
			BlockState state = ESBlocks.BOULDERSHROOM_BLOCK.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(capDirection.getOpposite()), false);
			for (Direction dir : Direction.values()) {
				if (stemPositions.contains(blockPos.relative(dir)) || capPositions.contains(blockPos.relative(dir)) || fullCapPositions.contains(blockPos.relative(dir))) {
					state = state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir), false);
				}
			}
			level.setBlock(blockPos, state, Block.UPDATE_ALL);
			if (random.nextInt(5) == 0) {
				int maxLength = 0;
				int randLength = random.nextInt(2, 7);
				for (int i = 1; i < randLength + 1; i++) {
					BlockPos belowPos = blockPos.below(i);
					if (stemPositions.contains(belowPos) || capPositions.contains(belowPos) || !canReplace(level.getBlockState(belowPos))) {
						break;
					}
					maxLength++;
				}
				for (int i = 1; i <= maxLength; i++) {
					level.setBlock(blockPos.below(i), i == maxLength ? ESBlocks.BOULDERSHROOM_ROOTS.get().defaultBlockState() : ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get().defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}
		return true;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return random.nextFloat() < 0.4;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource random, BlockPos pos, BlockState state) {
		serverLevel.removeBlock(pos, false);
		if (!growMushroom(serverLevel, random, pos, state.getValue(FACING))) {
			serverLevel.setBlock(pos, state, Block.UPDATE_ALL);
		}
	}
}
