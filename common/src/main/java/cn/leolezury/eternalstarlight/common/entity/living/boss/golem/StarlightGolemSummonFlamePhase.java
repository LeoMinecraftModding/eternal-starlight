package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class StarlightGolemSummonFlamePhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 2;

	public StarlightGolemSummonFlamePhase() {
		super(ID, 2, 200, 250);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null && entity.getAttackEnergy() >= 0 && entity.getPhase() == 0;
	}

	@Override
	public void onStart(StarlightGolem entity) {
		entity.setAttackEnergy(Math.max(entity.getAttackEnergy() - 20, 0));
	}

	@Override
	public void tick(StarlightGolem entity) {
		if (entity.getBehaviorTicks() % 40 == 0) {
			entity.spawnEnergizedFlame(2, 15, true);
		}
	}

	@Override
	public boolean canContinue(StarlightGolem entity) {
		return true;
	}
}
