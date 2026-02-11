package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

public class GatekeeperJumpStartPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 2;

	public GatekeeperJumpStartPhase() {
		super(ID, 1, 40, 0, GatekeeperJumpTransitionPhase.ID);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.setItemInHand(InteractionHand.MAIN_HAND, entity.getRandom().nextBoolean() ? ESItems.GOLEM_STEEL_GREATSWORD.get().getDefaultInstance() : ESItems.STARFIRE_HAMMER.get().getDefaultInstance());
	}

	@Override
	public void tick(TheGatekeeper entity) {
		if (entity.getBehaviorTicks() == 15) {
			entity.hurtMarked = true;
			entity.addDeltaMovement(new Vec3(0, 1.5, 0));
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
