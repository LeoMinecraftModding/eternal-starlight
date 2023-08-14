package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class BoarwarfWakeUpGoal extends Goal {
    private final PathfinderMob creature;

    public BoarwarfWakeUpGoal(PathfinderMob creature) {
        this.creature = creature;
    }

    public boolean canUse() {
        if (creature instanceof Boarwarf boarwarf) {
            return ((boarwarf.wantsToWake() && this.creature.isSleeping()) || (this.creature.getSleepingPos().isPresent() && (this.creature.getY() > (double)this.creature.getSleepingPos().get().getY() + 0.4D || !this.creature.getSleepingPos().get().closerToCenterThan(this.creature.position(), 1.14D)) && this.creature.isSleeping()));
        } else return false;
    }

    public void start() {
        super.start();
        this.creature.stopSleeping();
    }
}
