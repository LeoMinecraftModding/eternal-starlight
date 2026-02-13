package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GatekeeperEatPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 13;

	public GatekeeperEatPhase() {
		super(ID, 1, 55, 400);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver
			&& !canReachTarget(entity, 10)
			&& entity.getHealth() < entity.getMaxHealth() * 0.5f
			&& entity.healCount < 16
			&& entity.healInterruptedCount < 4;
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.setItemInHand(InteractionHand.OFF_HAND, Items.GOLDEN_CARROT.getDefaultInstance());
		entity.healInterrupted = false;
		entity.healInterruptedIndirect = false;
		entity.healCount++;
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
			entity.hurtMarked = true;
			entity.addDeltaMovement(entity.position().subtract(target.position()).normalize().scale(0.05));
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return !entity.healInterrupted;
	}

	@Override
	public void onStop(TheGatekeeper entity) {
	}

	@Override
	public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
		if (!(entity.healInterrupted && entity.healInterruptedIndirect)) {
			entity.heal(entity.getMaxHealth() / 5);
		}
		entity.setBehaviorState(0);
		entity.setBehaviorTicks(0);
		int newId = (entity.healInterrupted && entity.healInterruptedIndirect)
			? GatekeeperEatFailPhase.ID
			: (canReachTarget(entity, 3)
			? GatekeeperGreatswordPhase.ID
			: (canReachTarget(entity, 18)
			? GatekeeperBowPhase.ID
			: GatekeeperBowComboPhase.ID));
		if (newId != GatekeeperEatFailPhase.ID) {
			entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
		}
		if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
			manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		}
		onStop(entity);
	}
}
