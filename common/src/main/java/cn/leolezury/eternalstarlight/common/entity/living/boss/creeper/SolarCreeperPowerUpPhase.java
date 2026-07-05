package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

import java.util.List;

public class SolarCreeperPowerUpPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 14;
	public static final int DURATION = 150;

	private static final List<String> PART_NAMES = List.of("root", "body", "head", "leg1", "leg2", "leg3", "leg4");

	public SolarCreeperPowerUpPhase() {
		super(ID, 1, DURATION, 0);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(SolarCreeper entity) {

	}

	@Override
	public void tick(SolarCreeper entity) {
		int ticks = entity.getBehaviorTicks();

	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
