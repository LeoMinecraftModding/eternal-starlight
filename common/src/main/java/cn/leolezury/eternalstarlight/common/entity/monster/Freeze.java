package cn.leolezury.eternalstarlight.common.entity.monster;

import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Freeze extends Monster implements RangedAttackMob {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState throwAnimationState = new AnimationState();
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Freeze.class, EntityDataSerializers.BOOLEAN);
    public boolean isAttacking() {
        return entityData.get(ATTACKING);
    }
    public void setAttacking(boolean attacking) {
        entityData.set(ATTACKING, attacking);
    }
    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(Freeze.class, EntityDataSerializers.INT);
    public int getAttackTicks() {
        return entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        entityData.set(ATTACK_TICKS, attackTicks);
    }

    public Freeze(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACKING, false);
        entityData.define(ATTACK_TICKS, 0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return super.isStableDestination(blockPos) && level.getBlockState(blockPos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(true);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new FreezeAttackGoal(this, 0.3, 40, 25));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 32.0F));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.3));
        
        targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers(Freeze.class));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        setAttacking(true);
        setAttackTicks(0);
    }

    private static class FreezeAttackGoal extends RangedAttackGoal {
        private final Freeze freeze;

        public FreezeAttackGoal(Freeze freeze, double speed, int interval, float radius) {
            super(freeze, speed, interval, radius);
            this.freeze = freeze;
        }

        public void start() {
            super.start();
            this.freeze.setAggressive(true);
        }

        public void stop() {
            super.stop();
            this.freeze.setAggressive(false);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.FLYING_SPEED, 0.3).add(Attributes.FOLLOW_RANGE, 80);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACKING)) {
            throwAnimationState.stop();
            if (isAttacking()) {
                throwAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        return super.isAlliedTo(entity) || entity.getType().is(ESTags.EnityTypes.ROBOTIC);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (isAttacking()) {
                if (getAttackTicks() == 12 && getTarget() != null) {
                    LivingEntity livingEntity = getTarget();
                    Vec3 targetPos = livingEntity.position().add(0, livingEntity.getBbHeight() / 2f, 0);
                    Vec3 launchPos = position().add(0, getBbHeight() / 2f, 0);
                    Vec3 delta = targetPos.subtract(launchPos).normalize();
                    FrozenTube tube = new FrozenTube(level(), this, delta.x, delta.y, delta.z);
                    tube.setPos(launchPos);
                    tube.setOwner(this);
                    level().addFreshEntity(tube);
                }
                setAttackTicks(getAttackTicks() + 1);
                if (getAttackTicks() > 20) {
                    setAttacking(false);
                }
            } else {
                setAttackTicks(0);
            }
            for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2))) {
                if (livingEntity.canFreeze()) {
                    livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + 3);
                }
            }
        } else {
            if (!onGround()) {
                Vec3 pos = position().add(0, getBbHeight() / 20f * 12f, 0);
                for (int i = 0; i < 5; i++) {
                    level().addParticle(ESParticles.STARLIGHT.get(), pos.x + (getBbWidth() / 2f) * (getRandom().nextFloat() - 0.5f), pos.y, pos.z + (getBbWidth() / 2f) * (getRandom().nextFloat() - 0.5f), 0, -0.15, 0);
                }
            }
            idleAnimationState.startIfStopped(tickCount);
        }
    }
}
