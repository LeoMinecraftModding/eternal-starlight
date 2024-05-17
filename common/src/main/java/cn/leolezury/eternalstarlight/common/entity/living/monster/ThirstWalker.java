package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.entity.living.phase.AttackManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MeleeAttackPhase;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiPhaseAttacker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ThirstWalker extends Monster implements MultiPhaseAttacker, NeutralMob {
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    private float hungerLevel = 1;
    public static final int MELEE_ID = 1;
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState meleeAnimationState = new AnimationState();

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.INT);
    public int getAttackState() {
        return entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attackState) {
        entityData.set(ATTACK_STATE, attackState);
    }

    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.INT);
    public int getAttackTicks() {
        return entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        entityData.set(ATTACK_TICKS, attackTicks);
    }

    protected static final EntityDataAccessor<Boolean> INTENTIONAL_ATTACK = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.BOOLEAN);
    public boolean isIntentionalAttack() {
        return entityData.get(INTENTIONAL_ATTACK);
    }
    public void setIntentionalAttack(boolean intentionalAttack) {
        entityData.set(INTENTIONAL_ATTACK, intentionalAttack);
    }

    private final AttackManager<ThirstWalker> attackManager = new AttackManager<>(this, List.of(
            new MeleeAttackPhase<ThirstWalker>(MELEE_ID, 1, 20, 10).with(2, 7)
    ));

    public ThirstWalker(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_STATE, 0)
                .define(ATTACK_TICKS, 0)
                .define(INTENTIONAL_ATTACK, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {
            @Override
            protected void checkAndPerformAttack(LivingEntity livingEntity) {

            }
        });
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    public void setTarget(@Nullable LivingEntity livingEntity) {
        super.setTarget(livingEntity);
        if (livingEntity != null) {
            setIntentionalAttack(false);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int) f), this);
            hungerLevel = Math.min(hungerLevel + (isIntentionalAttack() ? 0.3f : 0.1f), 1);
            if (isIntentionalAttack()) {
                stopBeingAngry();
            }
        }
        return flag;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.attackManager.tick();
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
            this.updatePersistentAnger(serverLevel, true);
            if (hungerLevel < 0.3 && getTarget() == null) {
                List<Player> players = level().getNearbyEntities(Player.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(20));
                if (!players.isEmpty()) {
                    Player target = players.get(getRandom().nextInt(players.size()));
                    setTarget(target);
                    setIntentionalAttack(true);
                }
            }
            hungerLevel = Math.max(hungerLevel - 0.001f, 0);
        }
        if (level().isClientSide) {
            idleAnimationState.startIfStopped(tickCount);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            if (getAttackState() == MELEE_ID) {
                meleeAnimationState.start(tickCount);
            } else {
                meleeAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.addPersistentAngerSaveData(compoundTag);
        compoundTag.putFloat("HungerLevel", hungerLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.readPersistentAngerSaveData(this.level(), compoundTag);
        if (compoundTag.contains("HungerLevel", CompoundTag.TAG_FLOAT)) {
            hungerLevel = compoundTag.getFloat("HungerLevel");
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}
