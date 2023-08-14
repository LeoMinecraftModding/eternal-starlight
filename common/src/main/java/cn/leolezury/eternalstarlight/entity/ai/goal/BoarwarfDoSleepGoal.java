package cn.leolezury.eternalstarlight.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BoarwarfDoSleepGoal extends Goal {
    private final PathfinderMob creature;

    public BoarwarfDoSleepGoal(PathfinderMob creature) {
        this.creature = creature;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return this.creature.isSleeping();
    }
}
