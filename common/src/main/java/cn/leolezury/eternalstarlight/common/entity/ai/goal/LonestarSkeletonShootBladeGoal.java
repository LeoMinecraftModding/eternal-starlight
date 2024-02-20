package cn.leolezury.eternalstarlight.common.entity.ai.goal;

import cn.leolezury.eternalstarlight.common.entity.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownShatteredBlade;
import cn.leolezury.eternalstarlight.common.item.weapon.ShatteredSwordItem;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class LonestarSkeletonShootBladeGoal extends Goal {
    private final LonestarSkeleton mob;
    private final double speedModifier;
    private final int attackIntervalMin;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    private boolean usingSword;
    private int usingSwordTicks;

    public LonestarSkeletonShootBladeGoal(LonestarSkeleton monster, double d, int i, float f) {
        this.mob = monster;
        this.speedModifier = d;
        this.attackIntervalMin = i;
        this.attackRadiusSqr = f * f;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.mob.getTarget() != null && this.isHoldingRemoteAttackWeapon();
    }

    protected boolean isHoldingRemoteAttackWeapon() {
        return this.mob.getMainHandItem().is(ESItems.SHATTERED_SWORD.get());
    }

    public boolean canContinueToUse() {
        return (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingRemoteAttackWeapon();
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
    }

    public void stop() {
        super.stop();
        this.mob.setAggressive(false);
        this.seeTime = 0;
        this.attackTime = -1;
        this.usingSword = false;
        this.usingSwordTicks = 0;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            double d = this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = this.mob.getSensing().hasLineOfSight(livingEntity);
            boolean bl2 = this.seeTime > 0;
            if (bl != bl2) {
                this.seeTime = 0;
            }

            if (bl) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(d > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
                this.mob.getNavigation().stop();
                ++this.strafingTime;
            } else {
                this.mob.getNavigation().moveTo(livingEntity, this.speedModifier);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double)this.mob.getRandom().nextFloat() < 0.3) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double)this.mob.getRandom().nextFloat() < 0.3) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (d > (double)(this.attackRadiusSqr * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (d < (double)(this.attackRadiusSqr * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                Entity var7 = this.mob.getControlledVehicle();
                if (var7 instanceof Mob) {
                    Mob mob = (Mob)var7;
                    mob.lookAt(livingEntity, 30.0F, 30.0F);
                }

                this.mob.lookAt(livingEntity, 30.0F, 30.0F);
            } else {
                this.mob.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
            }

            if (this.usingSword) {
                this.usingSwordTicks++;
                if (!bl && this.seeTime < -60) {
                    this.usingSword = false;
                    this.usingSwordTicks = 0;
                } else if (bl) {
                    if (this.usingSwordTicks >= 20) {
                        this.usingSword = false;
                        this.usingSwordTicks = 0;
                        this.mob.swing(InteractionHand.MAIN_HAND);
                        ThrownShatteredBlade blade = new ThrownShatteredBlade(mob.level(), mob);
                        blade.shootFromRotation(mob, mob.getXRot(), mob.getYRot(), 0.0F, 2.5F, 1.0F);
                        this.mob.level().addFreshEntity(blade);
                        this.mob.level().playSound(null, blade, SoundEvents.TRIDENT_THROW, SoundSource.HOSTILE, 1.0F, 1.0F);
                        ItemStack stack = this.mob.getMainHandItem();
                        ShatteredSwordItem.setHasBlade(stack, false);
                        this.attackTime = this.attackIntervalMin;
                    }
                }
            } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                this.usingSword = true;
                this.usingSwordTicks = 0;
                ItemStack stack = this.mob.getMainHandItem();
                ShatteredSwordItem.setHasBlade(stack, true);
            }
        }
    }
}
