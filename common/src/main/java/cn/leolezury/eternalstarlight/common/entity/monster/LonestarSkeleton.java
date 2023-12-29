package cn.leolezury.eternalstarlight.common.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class LonestarSkeleton extends Skeleton {
    public LonestarSkeleton(EntityType<? extends LonestarSkeleton> type, Level level) {
        super(type, level);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    private int meleeTicks = 0;

    @Override
    public void aiStep() {
        super.aiStep();
        if (getTarget() != null && !level().isClientSide) {
            LivingEntity target = getTarget();
            boolean isTargetNear = false;
            for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(3))) {
                if (livingEntity.getUUID().equals(target.getUUID())) {
                    isTargetNear = true;
                }
            }
            if (isTargetNear) {
                if (!getItemInHand(InteractionHand.MAIN_HAND).is(Items.STONE_SWORD)) {
                    setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
                }
                meleeTicks++;
            } else {
                if (!getItemInHand(InteractionHand.MAIN_HAND).is(Items.BOW)) {
                    setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                }
                meleeTicks = 0;
            }
            if (meleeTicks >= 60 && meleeTicks % 20 == 0) {
                double x = getX() - target.getX();
                double y = getY() - target.getY();
                double z = getZ() - target.getZ();
                double d = Mth.sqrt((float) (x * x + y * y + z * z));
                setDeltaMovement(x / d, 0.05, z / d);
                if (!getItemInHand(InteractionHand.MAIN_HAND).is(Items.BOW)) {
                    setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                }
                meleeTicks = 0;
            }
        }
    }

    protected SoundEvent getHurtSound(DamageSource p_33850_) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected AbstractArrow getArrow(ItemStack p_33846_, float p_33847_) {
        AbstractArrow abstractarrow = super.getArrow(p_33846_, p_33847_);
        if (abstractarrow instanceof Arrow) {
            ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.POISON, 60));
        }
        return abstractarrow;
    }
}
