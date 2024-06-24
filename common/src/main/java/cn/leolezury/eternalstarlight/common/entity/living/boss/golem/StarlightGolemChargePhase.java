package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;

public class StarlightGolemChargePhase extends BehaviourPhase<StarlightGolem> {
	public static final int ID = 5;

	public StarlightGolemChargePhase() {
		super(ID, 1, 600, 0, StarlightGolemChargeEndPhase.ID);
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
		return entity.getHurtCount() < 5;
	}

	@Override
	public void onStop(StarlightGolem entity) {

	}
}
