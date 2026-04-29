package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PermafrostRangedPhase extends BehaviorPhase<Permafrost> {
	public static final int ID = 4;

	public PermafrostRangedPhase() {
		super(ID, 1, 65, 200);
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
			Vec3 launchPos = entity.position().add(0, entity.getBbHeight() / 2f, 0);
			if (entity.getBehaviorTicks() >= 21 && entity.getBehaviorTicks() <= 45) {
				performDefaultMeleeAttack(entity, 1.5, true, 360, e -> {
					e.hurtMarked = true;
					e.addDeltaMovement(e.position().subtract(entity.position()).normalize().scale(2));
				});
				if (entity.getBehaviorTicks() % 3 == 0) {
					for (int i = 0; i <= 5; i++) {
						Vec3 targetPos = i == 0 ? target.position().add(0, target.getBbHeight() / 2f, 0) : ESMathUtil.rotationToPosition(launchPos, 1, 15, 360f * i / 5f + entity.getRandom().nextInt(72));
						Vec3 delta = targetPos.subtract(launchPos).normalize();
						FrozenTube tube = new FrozenTube(level, entity);
						tube.shoot(delta.x, delta.y, delta.z, i == 0 ? 1.25f : 0.5f, i == 0 ? 0.1f : 0.3f);
						tube.setPos(launchPos);
						tube.playSound(ESSoundEvents.FROZEN_TUBE_THROW.get());
						level.addFreshEntity(tube);
					}
				}
			}
		}
	}

	@Override
	public boolean canContinue(Permafrost entity) {
		return true;
	}
}
