package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.world.entity.LivingEntity;

public class EctostoneWakeUpPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 7;
	public static final int DURATION = 45;
	private static final double WAKE_RANGE = 10;

	public EctostoneWakeUpPhase() {
		super(ID, 0, DURATION, 0);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		LivingEntity target = entity.getTarget();
		return entity.isDormant() && target != null && entity.distanceTo(target) <= WAKE_RANGE;
	}

	@Override
	public void onStart(Ectostone entity) {
		entity.setDormant(false);
	}

	@Override
	public void tick(Ectostone entity) {

	}
}
