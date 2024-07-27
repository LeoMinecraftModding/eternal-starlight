package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class LunarMonstrositySneakPhase extends BehaviorPhase<LunarMonstrosity> {
	public static final int ID = 6;

	public LunarMonstrositySneakPhase() {
		super(ID, 1, 200, 0, LunarMonstrosityEmergePhase.ID);
	}

	@Override
	public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(LunarMonstrosity entity) {

	}

	@Override
	public void tick(LunarMonstrosity entity) {

	}

	@Override
	public boolean canContinue(LunarMonstrosity entity) {
		return true;
	}

	@Override
	public void onStop(LunarMonstrosity entity) {

	}
}
