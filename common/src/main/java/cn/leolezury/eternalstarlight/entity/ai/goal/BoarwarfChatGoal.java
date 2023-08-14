package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class BoarwarfChatGoal extends Goal {
    protected final Boarwarf mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private long lastCanUseCheck;

    public BoarwarfChatGoal(Boarwarf villager, double speed, boolean ignoreNotSeen) {
        this.mob = villager;
        this.speedModifier = speed;
        this.followingTargetEvenIfNotSeen = ignoreNotSeen;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (mob.chatTicks <= 0 || mob.chatTarget == null) {
            return false;
        }
        this.mob.getLookControl().setLookAt(mob.chatTarget, 30.0F, 30.0F);
        if (mob.distanceTo(mob.chatTarget) <= 3) {
            return false;
        }
        long i = this.mob.level().getGameTime();
        if (i - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = i;
            LivingEntity livingentity = this.mob.chatTarget;
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                this.path = this.mob.getNavigation().createPath(livingentity, 0);
                return true;
            }
        }
    }

    public boolean canContinueToUse() {
        if (mob.chatTicks <= 0 || mob.chatTarget == null) {
            return false;
        }
        this.mob.getLookControl().setLookAt(mob.chatTarget, 30.0F, 30.0F);
        if (mob.distanceTo(mob.chatTarget) <= 3) {
            return false;
        }
        LivingEntity livingentity = this.mob.chatTarget;
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        } else return this.mob.isWithinRestriction(livingentity.blockPosition());
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.ticksUntilNextPathRecalculation = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.mob.chatTarget;
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.chatTarget = null;
        }
        this.mob.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingentity = this.mob.chatTarget;
        if (livingentity != null) {
            this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);

                if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
        }
    }
}
