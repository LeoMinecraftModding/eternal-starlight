package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;

public class GatekeeperDashPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 7;

	public GatekeeperDashPhase() {
		super(ID, 2, 40, 150);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTargetXZ(entity, 9) && !canReachTargetXZ(entity, 5);
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GLISTERING_SWORD.get().getDefaultInstance());
		entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
			if (entity.getBehaviorTicks() == 16) {
				entity.hurtMarked = true;
				entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(3));
			}
			if (entity.getBehaviorTicks() >= 16 && entity.getBehaviorTicks() <= 35) {
				for (LivingEntity livingEntity : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(1.5))) {
					if (livingEntity == target || (livingEntity instanceof Targeting targeting && targeting.getTarget() == entity)) {
						entity.doHurtTarget(livingEntity);
						livingEntity.hurtMarked = true;
						livingEntity.addDeltaMovement(livingEntity.position().subtract(entity.position()).normalize().multiply(0.2, 0.1, 0.2));
					}
				}
			}
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return true;
	}

	@Override
	public void onStop(TheGatekeeper entity) {
	}

	@Override
	public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
		entity.setBehaviorState(0);
		entity.setBehaviorTicks(0);
		int newId = canReachTarget(entity, 5) ? GatekeeperStepBackPhase.ID : 0;
		if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
			manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		}
		onStop(entity);
	}
}
