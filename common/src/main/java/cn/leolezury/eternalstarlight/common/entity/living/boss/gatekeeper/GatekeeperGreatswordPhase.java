package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperGreatswordPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 5;

	public GatekeeperGreatswordPhase() {
		super(ID, 1, 47, 60);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 3);
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GOLEM_STEEL_GREATSWORD.get().getDefaultInstance());
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if ((ticks == 15 || ticks == 33) && target != null) {
			boolean success = performMeleeAttack(entity, 3);
			entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
			entity.hurtMarked = true;
			entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(0.6));
			if (success) {
				target.hurtMarked = true;
				target.addDeltaMovement(target.position().subtract(entity.position()).normalize().multiply(0.3, 0.2, 0.3));
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
}
