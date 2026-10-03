package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class EctostoneFallAsleepPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 6;
	public static final int DURATION = 25;

	public EctostoneFallAsleepPhase() {
		super(ID, 2, DURATION, 400);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		return cooldownOver && !entity.isDormant() && entity.getTarget() == null
			&& !entity.isInWater() && !entity.isInLava()
			&& entity.hasGroundBelow(Ectostone.SLEEP_GROUND_DISTANCE);
	}

	@Override
	public void onStart(Ectostone entity) {
		entity.setDormant(true);
	}

	@Override
	public void tick(Ectostone entity) {

	}
}
