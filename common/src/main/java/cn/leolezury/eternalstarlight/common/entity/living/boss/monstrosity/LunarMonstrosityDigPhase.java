package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class LunarMonstrosityDigPhase extends BehaviorPhase<LunarMonstrosity> {
	public static final int ID = 5;

	public LunarMonstrosityDigPhase() {
		super(ID, 1, 31, 300, LunarMonstrositySneakPhase.ID);
	}

	@Override
	public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
		return (cooldownOver && entity.getTarget() != null && !canReachTarget(entity, 10)) || (entity.fleeFromLavaCooldown <= 0 && entity.isInLava());
	}

	@Override
	public void onStart(LunarMonstrosity entity) {
		if (entity.isInLava()) {
			entity.fleeFromLavaCooldown = 100;
		}
	}

	@Override
	public void tick(LunarMonstrosity entity) {

	}

	@Override
	public boolean canContinue(LunarMonstrosity entity) {
		return true;
	}
}
