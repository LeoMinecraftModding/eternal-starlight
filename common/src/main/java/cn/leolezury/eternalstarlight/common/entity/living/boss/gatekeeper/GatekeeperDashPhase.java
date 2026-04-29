package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GatekeeperDashPhase extends BehaviorPhase<TheGatekeeper> {
	private final List<Entity> hitEntities = new ArrayList<>();

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
		hitEntities.clear();
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
				performMeleeAttack(entity, 1.5, true, 360, e -> {
					if (!hitEntities.contains(e) && entity.doHurtTarget(e)) {
						e.hurtMarked = true;
						e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.2, 0.1, 0.2));
						hitEntities.add(e);
					}
				});
			}
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
		int newId = canReachTarget(entity, 5) ? GatekeeperStepBackPhase.ID : 0;
		if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
			manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		}
		onStop(entity);
	}
}
