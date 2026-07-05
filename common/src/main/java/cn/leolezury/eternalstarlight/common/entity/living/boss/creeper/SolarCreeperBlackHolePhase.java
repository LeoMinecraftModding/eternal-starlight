package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class SolarCreeperBlackHolePhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 12;
	public static final int DURATION = 180;

	public SolarCreeperBlackHolePhase() {
		super(ID, 1, DURATION, 600);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTargetXZ(entity, 10) && entity.getTarget() != null;
	}

	@Override
	public void onStart(SolarCreeper entity) {

	}

	@Override
	public void tick(SolarCreeper entity) {

	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}

	@Override
	public void onStop(SolarCreeper entity) {

	}
}
