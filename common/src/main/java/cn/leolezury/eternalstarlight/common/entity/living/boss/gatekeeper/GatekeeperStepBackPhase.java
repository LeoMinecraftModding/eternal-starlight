package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GatekeeperStepBackPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 1;

	public GatekeeperStepBackPhase() {
		super(ID, 1, 17, 250);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 6);
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
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
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
		int newId = canReachTarget(entity, 8) ? GatekeeperJumpStartPhase.ID : GatekeeperBowPhase.ID;
		if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
			manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity));
		}
		onStop(entity);
	}
}
