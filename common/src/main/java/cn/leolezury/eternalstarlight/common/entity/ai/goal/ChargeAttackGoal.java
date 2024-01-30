package cn.leolezury.eternalstarlight.common.entity.ai.goal;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ChargeAttackGoal extends Goal {
    private final PathfinderMob charger;
    private LivingEntity target;
    private Vec3 targetPos = Vec3.ZERO;
    private final boolean extendTarget;
    private final float speed;
    private final int randomFrequency;
    private final int chargeTime;
    private final float attackRange;
    private int chargeTicks;
    private boolean ended;

    public ChargeAttackGoal(PathfinderMob mob, boolean extendTarget, float speed, int randomFrequency, int chargeTime, float attackRange) {
        this.charger = mob;
        this.extendTarget = extendTarget;
        this.speed = speed;
        this.randomFrequency = randomFrequency;
        this.chargeTime = chargeTime;
        this.attackRange = attackRange;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        target = charger.getTarget();
        if (target == null) {
            return false;
        } else {
            Vec3 chargerPos = charger.position();
            Vec3 chargeTargetPos = target.position();
            if (target.distanceTo(charger) > 20) {
                return false;
            } else {
                if (charger.getSensing().hasLineOfSight(target)) {
                    if (extendTarget) {
                        targetPos = chargerPos.add(chargeTargetPos.subtract(chargerPos).scale(2));
                    } else {
                        targetPos = chargeTargetPos;
                    }
                    return charger.getRandom().nextInt(randomFrequency) == 0;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public void start() {
        super.start();
        chargeTicks = chargeTime;
    }

    @Override
    public boolean canContinueToUse() {
        return chargeTicks > 0 || !charger.getNavigation().isDone();
    }

    @Override
    public void tick() {
        super.tick();

        charger.getLookControl().setLookAt(this.targetPos.x, this.targetPos.y, this.targetPos.z, 10.0F, this.charger.getMaxHeadXRot());

        if (chargeTicks > 0) {
            chargeTicks--;
            if (chargeTicks <= 0) {
                charger.getNavigation().moveTo(this.targetPos.x, this.targetPos.y, this.targetPos.z, speed);
            } else {
                charger.walkAnimation.setSpeed(this.charger.walkAnimation.speed() + 0.5F);
                if (charger instanceof Charger chargingMob) {
                    chargingMob.setCharging(true);
                }
            }
        }

        for (LivingEntity living : charger.level().getEntitiesOfClass(LivingEntity.class, charger.getBoundingBox().inflate(attackRange))) {
            if (!ended && target != null && living.getUUID().equals(target.getUUID())) {
                charger.doHurtTarget(target);
                this.ended = true;
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        chargeTicks = 0;
        target = null;
        ended = false;
        if (charger instanceof Charger chargingMob) {
            chargingMob.setCharging(false);
        }
    }
}
