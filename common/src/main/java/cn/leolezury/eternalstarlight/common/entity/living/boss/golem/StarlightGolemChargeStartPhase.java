package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class StarlightGolemChargeStartPhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 4;

	public StarlightGolemChargeStartPhase() {
		super(ID, 1, 35, 0, StarlightGolemChargePhase.ID);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null && entity.getAttackEnergy() <= 0 && entity.getPhase() == 0;
	}

	@Override
	public void onStart(StarlightGolem entity) {
		entity.setAttackEnergy(100);
		entity.turnOnEnergyBlocks();
		entity.clearChargeHurtCountAndAmount();
	}

	@Override
	public void tick(StarlightGolem entity) {
		if (entity.hasProtection()) {
			entity.heal(0.04f);
		}
	}

	@Override
	public boolean canContinue(StarlightGolem entity) {
		return true;
	}
}
