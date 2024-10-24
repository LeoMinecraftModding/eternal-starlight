package cn.leolezury.eternalstarlight.common.entity.living.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BoarwarfDoSleepGoal extends Goal {
	private final PathfinderMob creature;

	public BoarwarfDoSleepGoal(PathfinderMob creature) {
		this.creature = creature;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.creature.isSleeping();
	}
}
