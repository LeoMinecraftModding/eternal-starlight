package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.world.entity.LivingEntity;

public class SolarCreeperMeleePhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 2;

	public SolarCreeperMeleePhase() {
		super(ID, 1, 30, 40);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 3);
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if (target != null) {
			if (ticks == 12) {
				performDefaultMeleeAttack(entity, 3, true, 45, e -> {
					e.hurtMarked = true;
					e.addDeltaMovement(e.position().subtract(entity.position()).normalize().scale(0.75));
				});
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
