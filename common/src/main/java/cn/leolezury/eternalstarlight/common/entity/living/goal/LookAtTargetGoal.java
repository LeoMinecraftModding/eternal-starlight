package cn.leolezury.eternalstarlight.common.entity.living.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {
	private final Mob mob;

	public LookAtTargetGoal(Mob mob) {
		setFlags(EnumSet.of(Goal.Flag.LOOK));
		this.mob = mob;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		return mob.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return mob.getTarget() != null;
	}

	@Override
	public void tick() {
		if (mob.getTarget() != null) {
			mob.getLookControl().setLookAt(mob.getTarget(), 360, 360);
		}
	}
}
