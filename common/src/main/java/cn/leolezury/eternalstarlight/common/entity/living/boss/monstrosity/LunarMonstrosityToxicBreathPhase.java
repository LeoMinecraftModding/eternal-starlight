package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.attack.ray.LunarMonstrosityBreath;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;

public class LunarMonstrosityToxicBreathPhase extends BehaviorPhase<LunarMonstrosity> {
	public static final int ID = 1;

	public LunarMonstrosityToxicBreathPhase() {
		super(ID, 1, 100, 200);
	}

	@Override
	public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null && entity.getPhase() == 1;
	}

	@Override
	public void onStart(LunarMonstrosity entity) {

	}

	@Override
	public void tick(LunarMonstrosity entity) {
		if (entity.getBehaviorTicks() == 20) {
			LunarMonstrosityBreath breath = new LunarMonstrosityBreath(ESEntities.LUNAR_MONSTROSITY_BREATH.get(), entity.level(), entity, entity.getX(), entity.getY() + entity.getBbHeight() / 2.5f, entity.getZ(), entity.yHeadRot + 90, -entity.getXRot());
			entity.level().addFreshEntity(breath);
		}
		if (entity.getBehaviorTicks() >= 20 && entity.getBehaviorTicks() % 20 == 0 && entity.level() instanceof ServerLevel serverLevel) {
			ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 45, 25, 0.24f, 0.24f, 4.5f, 5).send(serverLevel);
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
