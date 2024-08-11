package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;

public class LunarMonstrositySoulPhase extends BehaviorPhase<LunarMonstrosity> {
	public static final int ID = 8;

	public LunarMonstrositySoulPhase() {
		super(ID, 1, 100, 0);
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
		if (entity.getBehaviorTicks() == 70) {
			entity.playSound(ESSoundEvents.LUNAR_MONSTROSITY_ROAR.get(), 0.5f, 1);
			entity.knockbackNearbyEntities(5, 2.5f, false);
			if (entity.level() instanceof ServerLevel serverLevel) {
				ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 30, 30, 0.15f, 0.24f, 4, 5).send(serverLevel);
			}
		}
	}

	@Override
	public boolean canContinue(LunarMonstrosity entity) {
		return true;
	}

	@Override
	public void onStop(LunarMonstrosity entity) {

	}
}
