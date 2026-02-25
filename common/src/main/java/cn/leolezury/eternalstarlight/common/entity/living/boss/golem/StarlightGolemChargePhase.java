package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class StarlightGolemChargePhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 5;

	public StarlightGolemChargePhase() {
		super(ID, 1, 1200, 0, StarlightGolemChargeEndPhase.ID);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(StarlightGolem entity) {
		if (entity.hasProtection()) {
			entity.heal(0.02f);
		}
	}

	@Override
	public boolean canContinue(StarlightGolem entity) {
		return entity.getPhase() == 0 && entity.getChargeHurtCount() < 7 && entity.getChargeHurtAmount() < entity.getMaxHealth() / 1.5;
	}

	@Override
	public void onStop(StarlightGolem entity) {

	}
}
