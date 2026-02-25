package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class GatekeeperStepBackPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 1;

	public GatekeeperStepBackPhase() {
		super(ID, 1, 17, 200);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 5);
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			entity.hurtMarked = true;
			Vec3 delta = entity.position().subtract(target.position());
			entity.addDeltaMovement(new Vec3(delta.x, 0, delta.z).normalize()
				.add(0, delta.normalize().y, 0).normalize().scale(1.8));
		}
		entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
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
		int newId = canReachTarget(entity, 8) ? GatekeeperJumpStartPhase.ID : (canReachTarget(entity, 18) ? GatekeeperBowPhase.ID : GatekeeperBowComboPhase.ID);
		// ignore cooldown
		manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		onStop(entity);
	}
}
