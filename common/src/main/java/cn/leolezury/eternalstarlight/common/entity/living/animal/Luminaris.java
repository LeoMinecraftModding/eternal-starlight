package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Luminaris extends AbstractSchoolingFish implements Charger {
    public Luminaris(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    protected static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Luminaris.class, EntityDataSerializers.BOOLEAN);

    public boolean isCharging() {
        return entityData.get(CHARGING);
    }

    @Override
    public void setCharging(boolean charging) {
        entityData.set(CHARGING, charging);
    }

    public AnimationState swimAnimationState = new AnimationState();
    public AnimationState chargeAnimationState = new AnimationState();

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGING, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 100.0).add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ChargeAttackGoal(this, true, 3f, 3, 60, 0.5f));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test) {
            @Override
            public boolean canUse() {
                return super.canUse() && mob.getTarget() == null;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && mob.getTarget() == null;
            }
        });
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
        this.goalSelector.addGoal(5, new FollowFlockLeaderGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            swimAnimationState.startIfStopped(tickCount);
            if (isCharging()) {
                chargeAnimationState.startIfStopped(tickCount);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (accessor.equals(CHARGING) && isCharging()) {
            chargeAnimationState.start(tickCount);
        }
    }

    static class FishSwimGoal extends RandomSwimmingGoal {
        private final Luminaris fish;

        public FishSwimGoal(Luminaris abstractFish) {
            super(abstractFish, 1.0, 40);
            this.fish = abstractFish;
        }

        public boolean canUse() {
            return this.fish.canRandomlySwim() && super.canUse();
        }
    }

    protected boolean canRandomlySwim() {
        return !this.isFollower() && this.getTarget() == null;
    }

    @Override
    public boolean isFollower() {
        return super.isFollower() && this.getTarget() == null;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) || damageSource.is(DamageTypes.HOT_FLOOR);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ESSoundEvents.LUMINARIS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ESSoundEvents.LUMINARIS_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return ESSoundEvents.LUMINARIS_FLOP.get();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ESItems.LUMINARIS_BUCKET.get().getDefaultInstance();
    }
}
