package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.BouncyStar;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class SolarCreeperStompShootPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 3;

	public SolarCreeperStompShootPhase() {
		super(ID, 1, 60, 200);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 3) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if (target == null) {
			return;
		}
		if (ticks == 10 && entity.level() instanceof ServerLevel serverLevel) {
			ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 20, 0.3f, 0.4f, 3, 5.5f).send(serverLevel);
			performDefaultMeleeAttack(entity, 4, true, 180, e -> {
				e.hurtMarked = true;
				e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(1.25, 0.5, 1.25));
			});
			RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, entity.getX(), entity.getY(), entity.getZ(), entity.getRandom());
			for (int i = -1; i <= 1; i++) {
				BouncyStar projectile = new BouncyStar(ESEntities.BOUNCY_STAR.get(), entity.level());
				projectile.setPos(entity.position());
				projectile.setOwner(entity);
				projectile.setTarget(target);
				projectile.shootFromRotation(entity, -20, entity.yBodyRot + i * 15, 0.0F, 0.8F, 2.0F);
				entity.level().addFreshEntity(projectile);
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
