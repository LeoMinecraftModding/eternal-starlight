package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SolarCreeperDashPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 9;
	private final List<Entity> hitEntities = new ArrayList<>();
	private float fixedYRot;

	public SolarCreeperDashPhase() {
		super(ID, 2, 100, 300);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 5) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if (target != null) {
			if (ticks < 16 || (ticks > 35 && ticks < 50)) {
				ESEntityUtil.instantLook(entity, target.getEyePosition());
				fixedYRot = entity.yBodyRot;
				hitEntities.clear();
			}
			if (ticks == 16 || ticks == 50) {
				entity.hurtMarked = true;
				entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(3));
			}
			if ((ticks >= 16 && ticks <= 35) || (ticks >= 50 && ticks <= 69)) {
				performMeleeAttack(entity, 1.5, true, 180, e -> {
					if (!hitEntities.contains(e) && entity.doHurtTarget(e)) {
						e.hurtMarked = true;
						e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.2, 0.1, 0.2));
						hitEntities.add(e);
					}
				});
				ESEntityUtil.instantLook(entity, ESMathUtil.rotationToPosition(new Vec3(entity.getX(), target.getEyeY(), entity.getZ()), 1, 0, fixedYRot + 90));
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
