package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.EclipseCoreBlock;
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
	public void lightUp(Level level, BlockPos pos, Direction sourceDir) {
		switch (sourceDir) {
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
		if (level.isClientSide) {
			entity.oldEclipseProgress = entity.eclipseProgress;
			if (state.getValue(EclipseCoreBlock.LIT)) {
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
			entity.ticksLeft = Math.min(Math.min(entity.ticksLeftNorth, entity.ticksLeftSouth), Math.min(entity.ticksLeftWest, entity.ticksLeftEast));
		}
		boolean oldLit = entity.isLit();
		AbstractDuskLightBlockEntity.tick(level, pos, state, entity);
		if (level instanceof ServerLevel serverLevel && !oldLit && entity.isLit()) {
			if (entity.ticks > 5) {
				ScreenShakeVfx.createInstance(serverLevel.dimension(), entity.getBlockPos().getCenter(), 45, 50, 0.24f, 0.24f, 4.5f, 5).send(serverLevel);
			}
			if (!state.getValue(EclipseCoreBlock.LIT)) {
				level.setBlockAndUpdate(pos, state.setValue(EclipseCoreBlock.LIT, true));
			}
		}
	}

	@Override
	protected boolean isFaceActivated(BlockState state, Direction direction) {
		return direction == Direction.UP;
	}
}
