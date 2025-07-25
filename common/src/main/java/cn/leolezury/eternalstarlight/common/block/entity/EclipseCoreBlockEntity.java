package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EclipseCoreBlockEntity extends AbstractDuskLightBlockEntity {
	private int ticksLeftNorth = 0;
	private int ticksLeftSouth = 0;
	private int ticksLeftWest = 0;
	private int ticksLeftEast = 0;

	public EclipseCoreBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.ECLIPSE_CORE.get(), blockPos, blockState);
	}

	@Override
	protected void lightUp(Direction direction) {
		switch (direction) {
			case NORTH -> ticksLeftNorth = 5;
			case SOUTH -> ticksLeftSouth = 5;
			case WEST -> ticksLeftWest = 5;
			case EAST -> ticksLeftEast = 5;
		}
	}

	public static void tick(Level level, BlockPos pos, BlockState state, EclipseCoreBlockEntity entity) {
		entity.ticksLeftNorth--;
		if (entity.ticksLeftNorth < 0) {
			entity.ticksLeftNorth = 0;
		}
		entity.ticksLeftSouth--;
		if (entity.ticksLeftSouth < 0) {
			entity.ticksLeftSouth = 0;
		}
		entity.ticksLeftWest--;
		if (entity.ticksLeftWest < 0) {
			entity.ticksLeftWest = 0;
		}
		entity.ticksLeftEast--;
		if (entity.ticksLeftEast < 0) {
			entity.ticksLeftEast = 0;
		}
		if (entity.ticksLeftNorth > 0 && entity.ticksLeftSouth > 0 && entity.ticksLeftWest > 0 && entity.ticksLeftEast > 0) {
			entity.ticksLeft = 5;
		}
		AbstractDuskLightBlockEntity.tick(level, pos, state, entity);
	}

	@Override
	protected boolean isFaceActivated(BlockState state, Direction direction) {
		return direction == Direction.UP;
	}
}
