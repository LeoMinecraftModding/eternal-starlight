package cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.boss.AttackManager;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.List;

public class TheGatekeeper extends ESBoss {
    public TheGatekeeper(EntityType<? extends ESBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.PURPLE, false);

    private final AttackManager<TheGatekeeper> attackManager = new AttackManager<>(this, List.of(
        new GatekeeperMeleePhase()
    ));

    protected static final EntityDataAccessor<Boolean> SLIM = SynchedEntityData.defineId(TheGatekeeper.class, EntityDataSerializers.BOOLEAN);
    public boolean isSlim() {
        return entityData.get(SLIM);
    }
    public void setSlim(boolean slim) {
        entityData.set(SLIM, slim);
    }

    public AnimationState switchPhaseAnimationState = new AnimationState();
    public AnimationState meleeAttackAnimationState = new AnimationState();
    public AnimationState throwTridentAnimationState = new AnimationState();
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState inAirAnimationState = new AnimationState();
    public AnimationState landAnimationState = new AnimationState();
    public boolean performingRemoteAttack = false;
    private int noSuccessMeleeAttackTicks = 0;
    private int shieldCoolDown = 0;
    private int jumpCoolDown = 0;
    private String gatekeeperName = "steve";

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SLIM, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setSlim(compoundTag.getBoolean("Slim"));
        gatekeeperName = compoundTag.getString("GatekeeperName");
        bossEvent.setId(getUUID());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Slim", isSlim());
        compoundTag.putString("GatekeeperName", gatekeeperName);
    }

    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        bossEvent.addPlayer(serverPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        bossEvent.removePlayer(serverPlayer);
    }

    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new TheGatekeeperMeleeAttackGoal(this, 0.5D, false));
        //goalSelector.addGoal(1, new TheGatekeeperTridentAttackGoal(this, 1.0D, 40, 15.0F));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this, TheGatekeeper.class)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static class TheGatekeeperMeleeAttackGoal extends MeleeAttackGoal {
        public TheGatekeeperMeleeAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
            super(mob, speed, followingTargetEvenIfNotSeen);
        }

        /*@Override
        public boolean canUse() {
            boolean canUse = true;
            if (mob instanceof TheGatekeeper gatekeeper) {
                canUse = !gatekeeper.performingRemoteAttack && gatekeeper.isActivated() && (gatekeeper.getAttackState() == -2 || gatekeeper.getAttackState() == 0 || gatekeeper.getAttackState() == 1);
            }
            return super.canUse() && canUse;
        }

        @Override
        public boolean canContinueToUse() {
            boolean canUse = true;
            if (mob instanceof TheGatekeeper gatekeeper) {
                canUse = !gatekeeper.performingRemoteAttack && gatekeeper.isActivated() && (gatekeeper.getAttackState() == -2 || gatekeeper.getAttackState() == 0 || gatekeeper.getAttackState() == 1);
            }
            return super.canContinueToUse() && canUse;
        }*/

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D);
    }

    @Override
    protected void tickDeath() {
        //TODO: change to trading mode
        super.tickDeath();
    }

    public void stopAllAnimStates() {
        switchPhaseAnimationState.stop();
        meleeAttackAnimationState.stop();
        throwTridentAnimationState.stop();
        jumpAnimationState.stop();
        inAirAnimationState.stop();
        landAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case -1 -> switchPhaseAnimationState.start(tickCount);
                case 1 -> meleeAttackAnimationState.start(tickCount);
                case 2 -> throwTridentAnimationState.start(tickCount);
                case 3 -> jumpAnimationState.start(tickCount);
                case 4 -> inAirAnimationState.start(tickCount);
                case 5 -> landAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public boolean targetInRange(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return false;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }
        return false;
    }

    public void performMeleeAttack() {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(3))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                livingEntity.invulnerableTime = 0;
                livingEntity.hurt(damageSources().mobAttack(this), 7);
                noSuccessMeleeAttackTicks = 0;
            }
        }
    }

    public void performRemoteAttack() {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        ThrownTrident throwntrident = new ThrownTrident(this.level(), this, new ItemStack(Items.TRIDENT));
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - throwntrident.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        throwntrident.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(throwntrident);
    }

    public void activate() {
        setAttackState(-1);
        setAttackTicks(1);
    }

    @Override
    public boolean canBossMove() {
        return isActivated();
    }

    @Override
    public void initializeBoss() {
        super.initializeBoss();
        setActivated(false);
        gatekeeperName = CommonHandlers.getGatekeeperName();
        setSlim(getRandom().nextBoolean());
        //TODO: remove debug thing
        //activate();
    }

    @Override
    public boolean isBlocking() {
        // a really awful way to fix the shield bug...
        return this.isUsingItem() && getUsedItemHand() == InteractionHand.OFF_HAND && getAttackState() == -2;
    }

    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == ClientHandlers.BOSS_MUSIC_ID) {
            ClientHandlers.handleEntityEvent(this, id);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.update();
        if (!level().isClientSide) {
            if (!isActivated()) {
                setActivated(true);
            }
            if (!isSilent()) {
                this.level().broadcastEntityEvent(this, (byte) ClientHandlers.BOSS_MUSIC_ID);
            }

            setCustomName(Component.literal(gatekeeperName));

            if (isLeftHanded()) {
                setLeftHanded(false);
            }

            attackManager.tick();
        } else {
            level().addParticle(ParticleInit.STARLIGHT.get(), getX() + (getRandom().nextDouble() - 0.5) * 2, getY() + 1 + (getRandom().nextDouble() - 0.5) * 2, getZ() + (getRandom().nextDouble() - 0.5) * 2, 0, 0, 0);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return isDamageSourceBlocked(damageSource) ? SoundEvents.SHIELD_BLOCK : SoundEvents.PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }
}
