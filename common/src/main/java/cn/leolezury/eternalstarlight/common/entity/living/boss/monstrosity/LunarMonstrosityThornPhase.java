package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TangledSkull;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LunarMonstrosityThornPhase extends BehaviorPhase<LunarMonstrosity> {
	public static final int ID = 3;

	public LunarMonstrosityThornPhase() {
		super(ID, 1, 90, 200);
	}

	@Override
	public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null;
	}

	@Override
	public void onStart(LunarMonstrosity entity) {

	}

	@Override
	public void tick(LunarMonstrosity entity) {
		LivingEntity target = entity.getTarget();
		if (entity.getBehaviorTicks() % 15 == 0 && target != null) {
			double d0 = Math.min(target.getY(), entity.getY());
			double d1 = Math.max(target.getY(), entity.getY()) + 1.0;
			float f = (float) Mth.atan2(target.getZ() - entity.getZ(), target.getX() - entity.getX());
			if (entity.level() instanceof ServerLevel serverLevel) {
				ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 30, 30, 0.15f, 0.24f, 4, 5).send(serverLevel);
			}
			for (int i = 0; i < (entity.getBehaviorTicks() / 15 + 1) * 6; i++) {
				double d2 = 1.25 * (double) (i + 1);
				this.createThorn(entity, entity.getX() + (double) Mth.cos(f) * d2, entity.getZ() + (double) Mth.sin(f) * d2, d0, d1, i);
			}
			entity.knockbackNearbyEntities(1.5f, entity.getBehaviorTicks() >= 60);
			if (entity.getPhase() == 1) {
				TangledSkull skull = new TangledSkull(ESEntities.TANGLED_SKULL.get(), entity.level());
				skull.setPos(entity.position().add((entity.getRandom().nextFloat() - 0.5) * entity.getBbWidth() * 8, entity.getBbHeight() * entity.getRandom().nextFloat() + 1.5, (entity.getRandom().nextFloat() - 0.5) * entity.getBbWidth() * 8));
				skull.setShot(true);
				skull.setShotFromMonstrosity(true);
				skull.setShotMovement(entity.getRayRotationTarget().subtract(skull.position()).normalize().scale(1.5));
				entity.level().addFreshEntity(skull);
				if (entity.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 7; i++) {
						serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS, skull.getRandomX(1), skull.getRandomY(), skull.getRandomZ(1), 5, 0, 0, 0, 0);
					}
				}
			}
		}
	}

	private void createThorn(LunarMonstrosity entity, double x, double z, double minY, double maxY, int delay) {
		BlockPos blockpos = BlockPos.containing(x, maxY, z);
		boolean flag = false;
		double d0 = 0.0;

		do {
			BlockPos blockpos1 = blockpos.below();
			BlockState blockstate = entity.level().getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(entity.level(), blockpos1, Direction.UP)) {
				if (!entity.level().isEmptyBlock(blockpos)) {
					BlockState blockstate1 = entity.level().getBlockState(blockpos);
					VoxelShape voxelshape = blockstate1.getCollisionShape(entity.level(), blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}

				flag = true;
				break;
			}

			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(minY) - 1);

		if (flag) {
			LunarThorn thorn = new LunarThorn(ESEntities.LUNAR_THORN.get(), entity.level());
			thorn.setPos(x, (double) blockpos.getY() + d0, z);
			thorn.setAttackMode(1);
			thorn.setSpawnedTicks(-delay);
			thorn.setOwner(entity);
			entity.level().addFreshEntity(thorn);
		}
	}

	@Override
	public boolean canContinue(LunarMonstrosity entity) {
		return true;
	}

	@Override
	public void onStop(LunarMonstrosity entity) {

	}
}
