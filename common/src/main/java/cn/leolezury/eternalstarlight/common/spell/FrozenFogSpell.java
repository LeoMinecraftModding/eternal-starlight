package cn.leolezury.eternalstarlight.common.spell;

import net.minecraft.world.entity.LivingEntity;

public class FrozenFogSpell extends AbstractSpell {
	public FrozenFogSpell(Properties properties) {
		super(properties);
	}

	@Override
	public boolean checkExtraConditions(LivingEntity entity) {
		return true;
	}

	@Override
	public boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks) {
		return true;
	}

	@Override
	public void onPreparationTick(LivingEntity entity, int ticks) {

	}

	@Override
	public void onSpellTick(LivingEntity entity, int ticks) {

	}

	@Override
	public void onStart(LivingEntity entity) {

	}

	@Override
	public void onStop(LivingEntity entity, int ticks) {

	}
}
