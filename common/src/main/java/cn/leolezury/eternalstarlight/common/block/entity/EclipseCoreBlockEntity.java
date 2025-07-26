package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EclipseCoreBlockEntity extends AbstractDuskLightBlockEntity {
	private int ticksLeftNorth = 0;
	private int ticksLeftSouth = 0;
	private int ticksLeftWest = 0;
	private int ticksLeftEast = 0;
	private float oldEclipseProgress;
	private float eclipseProgress;
	private int ticks;

	public EclipseCoreBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.ECLIPSE_CORE.get(), blockPos, blockState);
	}

	@Override
	public void lightUp(Level level, BlockPos pos, Direction direction) {
		switch (direction) {
			case NORTH -> ticksLeftNorth = 5;
			case SOUTH -> ticksLeftSouth = 5;
			case WEST -> ticksLeftWest = 5;
			case EAST -> ticksLeftEast = 5;
		}
	}

	public float getEclipseProgress(float partialTick) {
		return Mth.lerp(partialTick, oldEclipseProgress, eclipseProgress);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, EclipseCoreBlockEntity entity) {
		entity.ticks++;
		boolean oldLit = entity.isLit();
		if (level.isClientSide) {
			entity.oldEclipseProgress = entity.eclipseProgress;
			if (entity.isLit()) {
				entity.eclipseProgress = Mth.clamp(entity.eclipseProgress + 0.08f, 0, 1);
			} else {
				entity.eclipseProgress = Mth.clamp(entity.eclipseProgress - 0.2f, 0, 1);
			}
		} else {
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
		}
		AbstractDuskLightBlockEntity.tick(level, pos, state, entity);
		if (level instanceof ServerLevel serverLevel && !oldLit && entity.isLit() && entity.ticks > 5) {
			ScreenShakeVfx.createInstance(serverLevel.dimension(), entity.getBlockPos().getCenter(), 45, 50, 0.24f, 0.24f, 4.5f, 5).send(serverLevel);
		}
	}

	@Override
	protected boolean isFaceActivated(BlockState state, Direction direction) {
		return direction == Direction.UP;
	}
}
