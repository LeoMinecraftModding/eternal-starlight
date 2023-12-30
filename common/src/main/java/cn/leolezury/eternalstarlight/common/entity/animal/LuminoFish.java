package cn.leolezury.eternalstarlight.common.entity.animal;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class LuminoFish extends AbstractSchoolingFish {
    public LuminoFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    protected static final EntityDataAccessor<Integer> SWELL_TICKS = SynchedEntityData.defineId(LuminoFish.class, EntityDataSerializers.INT);

    public int getSwellTicks() {
        return entityData.get(SWELL_TICKS);
    }

    public void setSwellTicks(int swellTicks) {
        entityData.set(SWELL_TICKS, swellTicks);
    }

    public AnimationState swimAnimationState = new AnimationState();
    public AnimationState swellAnimationState = new AnimationState();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SWELL_TICKS, 0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide) {
            int swellTicks = getSwellTicks();
            if (swellTicks > 0) {
                setSwellTicks(swellTicks - 1);
                LivingEntity target = getTarget();
                if (swellTicks == 10 && target != null) {
                    if (target.position().distanceTo(position()) < 3) {
                        target.hurt(level().damageSources().mobAttack(this), 3);
                        target.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
                    }
                }
            }
        } else {
            swimAnimationState.startIfStopped(tickCount);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (accessor.equals(SWELL_TICKS) && getSwellTicks() == 20) {
            swellAnimationState.start(tickCount);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) || damageSource.is(DamageTypes.HOT_FLOOR);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        boolean flag = super.hurt(damageSource, amount);
        Entity entity = damageSource.getDirectEntity();
        if (flag && entity instanceof LivingEntity living && getSwellTicks() <= 0 && !level().isClientSide) {
            setSwellTicks(20);
            this.setTarget(living);
        }
        return flag;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEventInit.LUMINOFISH_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.LUMINOFISH_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEventInit.LUMINOFISH_FLOP.get();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ItemInit.LUMINOFISH_BUCKET.get().getDefaultInstance();
    }

    public static boolean checkAbyssalWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        int seaLevel = levelAccessor.getSeaLevel();
        return blockPos.getY() <= seaLevel - 40 && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER);
    }
}
