package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.PermafrostSpit;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PermafrostSneezePhase extends BehaviorPhase<Permafrost> {
	public static final int ID = 5;

	public PermafrostSneezePhase() {
		super(ID, 1, 99, 300);
	}

	@Override
	public boolean canStart(Permafrost entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 20);
	}

	@Override
	public void tick(Permafrost entity) {
		Level level = entity.level();
		if (entity.getTarget() != null) {
			LivingEntity target = entity.getTarget();
			ESEntityUtil.instantLook(entity, target.getEyePosition());
			if (entity.getBehaviorTicks() == 75) {
				entity.playSound(ESSoundEvents.PERMAFROST_SNEEZE.get());
				Vec3 launchPos = entity.getEyePosition();
				Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2f, 0);
				Vec3 delta = targetPos.subtract(launchPos).normalize();
				PermafrostSpit spit = new PermafrostSpit(level, entity);
				spit.shoot(delta.x, delta.y, delta.z, 1.5f, 0.1f);
				spit.setPos(launchPos);
				level.addFreshEntity(spit);
			}
		}
	}

	@Override
	public boolean canContinue(Permafrost entity) {
		return true;
	}
}
