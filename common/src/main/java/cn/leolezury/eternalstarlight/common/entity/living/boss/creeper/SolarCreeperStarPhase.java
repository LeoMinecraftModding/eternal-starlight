package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.SolarStarProjectile;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SolarCreeperStarPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 7;

	public SolarCreeperStarPhase() {
		super(ID, 1, 50, 200);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 5) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		RandomSource random = entity.getRandom();
		int ticks = entity.getBehaviorTicks();
		if (target == null) {
			return;
		}
		if (ticks == 5 || ticks == 15 || ticks == 25) {
			SolarStarProjectile projectile = new SolarStarProjectile(ESEntities.SOLAR_STAR_PROJECTILE.get(), entity.level());
			projectile.setPos(entity.getEyePosition());
			projectile.setOwner(entity);
			projectile.setTarget(target);
			Vec3 toTarget = target.position().add(0, target.getBbHeight() / 2, 0).subtract(entity.getEyePosition());
			float pitch = ESMathUtil.positionToPitch(toTarget);
			float yaw = ESMathUtil.positionToYaw(toTarget);
			toTarget = ESMathUtil.rotationToPosition(1, pitch + 45 * (random.nextFloat() - 0.5F), yaw + 45 * (random.nextFloat() - 0.5F));
			projectile.shoot(toTarget.x, toTarget.y, toTarget.z, 0.6f, 0.6f);
			entity.level().addFreshEntity(projectile);
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
