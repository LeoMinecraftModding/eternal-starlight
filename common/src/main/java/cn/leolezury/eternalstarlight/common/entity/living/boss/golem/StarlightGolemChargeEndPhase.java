package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class StarlightGolemChargeEndPhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 6;

	public StarlightGolemChargeEndPhase() {
		super(ID, 1, 30, 0);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(StarlightGolem entity) {

	}

	@Override
	public void tick(StarlightGolem entity) {
		if (!entity.canHurt()) {
			entity.heal(0.02f);
		}
	}

	@Override
	public boolean canContinue(StarlightGolem entity) {
		return true;
	}

	@Override
	public void onStop(StarlightGolem entity) {

	}
}
