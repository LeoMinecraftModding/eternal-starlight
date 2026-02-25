package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.item.ItemStack;

public class GatekeeperEatFailPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 14;

	public GatekeeperEatFailPhase() {
		super(ID, 1, 22, 0);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.healInterruptedCount++;
	}

	@Override
	public void tick(TheGatekeeper entity) {
		if (entity.getBehaviorTicks() == 2) {
			BehaviorUtils.throwItem(entity, entity.getOffhandItem().copy(), entity.getEyePosition().add(entity.getRandom().nextDouble() - 0.5, entity.getRandom().nextDouble(), entity.getRandom().nextDouble() - 0.5));
			entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return true;
	}

	@Override
	public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
		entity.setBehaviorState(0);
		entity.setBehaviorTicks(0);
		int newId = canReachTarget(entity, 3) ? GatekeeperGreatswordPhase.ID : (canReachTarget(entity, 18) ? GatekeeperBowPhase.ID : GatekeeperBowComboPhase.ID);
		if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
			manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		}
		onStop(entity);
	}
}
