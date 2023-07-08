package cn.leolezury.eternalstarlight.entity.boss;

import cn.leolezury.eternalstarlight.entity.ai.goal.TheGatekeeperBowAttackGoal;
import cn.leolezury.eternalstarlight.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.entity.misc.SLFallingBlock;
import cn.leolezury.eternalstarlight.event.server.ServerEvents;
import cn.leolezury.eternalstarlight.init.DamageTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;

import java.util.List;

public class TheGatekeeper extends AbstractSLBoss implements RangedAttackMob {
    public TheGatekeeper(EntityType<? extends AbstractSLBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    protected static final EntityDataAccessor<Boolean> SLIM = SynchedEntityData.defineId(TheGatekeeper.class, EntityDataSerializers.BOOLEAN);
    public boolean isSlim() {
        return entityData.get(SLIM);
    }
    public void setSlim(boolean slim) {
        entityData.set(SLIM, slim);
    }
    protected static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(TheGatekeeper.class, EntityDataSerializers.BOOLEAN);
    public boolean isGatekeeperBlocking() {
        return entityData.get(BLOCKING);
    }
    public void setBlocking(boolean blocking) {
        entityData.set(BLOCKING, blocking);
    }
    
    public AnimationState meleeAttackAnimationState = new AnimationState();
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState inAirAnimationState = new AnimationState();
    public AnimationState landAnimationState = new AnimationState();
    public AnimationState switchPhaseAnimationState = new AnimationState();
    public boolean performingRemoteAttack = false;
    private int noSuccessMeleeAttackTicks = 0;
    private int usingShieldTicks = 0;
    private int shieldCoolDown = 0;
    private int jumpCoolDown = 0;
    private String gatekeeperName = "steve";

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SLIM, false);
        entityData.define(BLOCKING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setSlim(compoundTag.getBoolean("Slim"));
        gatekeeperName = compoundTag.getString("GatekeeperName");
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
        goalSelector.addGoal(1, new TheGatekeeperBowAttackGoal(this, 1.0D, 20, 15.0F));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this, TheGatekeeper.class)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float f) {
        if (isBlocking() || isGatekeeperBlocking()) {
            return;
        }
        ItemStack itemStack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
        AbstractArrow abstractArrow = ProjectileUtil.getMobArrow(this, itemStack, f);
        if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
            abstractArrow = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractArrow);
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - abstractArrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractArrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(abstractArrow);
    }

    public static class TheGatekeeperMeleeAttackGoal extends MeleeAttackGoal {
        public TheGatekeeperMeleeAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
            super(mob, speed, followingTargetEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            boolean canUse = true;
            if (mob instanceof TheGatekeeper gatekeeper) {
                canUse = !gatekeeper.performingRemoteAttack && gatekeeper.isActivated() && (gatekeeper.getAttackState() == 0 || gatekeeper.getAttackState() == 1);
            }
            return super.canUse() && canUse;
        }

        @Override
        public boolean canContinueToUse() {
            boolean canUse = true;
            if (mob instanceof TheGatekeeper gatekeeper) {
                canUse = !gatekeeper.performingRemoteAttack && gatekeeper.isActivated() && (gatekeeper.getAttackState() == 0 || gatekeeper.getAttackState() == 1);
            }
            return super.canContinueToUse() && canUse;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target, double dist) {

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public boolean removeWhenFarAway(double p_37894_) {
        return false;
    }

    @Override
    protected void tickDeath() {
        //TODO: change to trading mode
        super.tickDeath();
    }

    public void stopAllAnimStates() {
        switchPhaseAnimationState.stop();
        meleeAttackAnimationState.stop();
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
                case 2 -> jumpAnimationState.start(tickCount);
                case 3 -> inAirAnimationState.start(tickCount);
                case 4 -> landAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    private boolean targetInRange(double range) {
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

    private void performMeleeAttack() {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(3))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                livingEntity.hurt(damageSources().mobAttack(this), 15);
                noSuccessMeleeAttackTicks = 0;
            }
        }
    }

    private void startUsingShield(int ticks) {
        usingShieldTicks = ticks;
        setBlocking(true);
        startUsingItem(InteractionHand.OFF_HAND);
    }

    private void stopUsingShield() {
        usingShieldTicks = 0;
        setBlocking(false);
        if (getUseItem().getItem() instanceof ShieldItem) {
            stopUsingItem();
        }
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
        gatekeeperName = ServerEvents.getGatekeeperName();
        setSlim(getRandom().nextBoolean());
        //TODO: remove debug thing
        activate();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide) {
            setCustomName(Component.literal(gatekeeperName));
            if (!getOffhandItem().is(Items.SHIELD)) {
                setItemInHand(InteractionHand.OFF_HAND, Items.SHIELD.getDefaultInstance());
            }
            if (performingRemoteAttack) {
                noSuccessMeleeAttackTicks = 0;
                if (!getMainHandItem().is(Items.BOW)) {
                    setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
                }
            } else {
                if (noSuccessMeleeAttackTicks < 120) {
                    noSuccessMeleeAttackTicks++;
                }
                if (!isBlocking() && isUsingItem()) {
                    stopUsingItem();
                }
                if (!getMainHandItem().is(Items.DIAMOND_SWORD)) {
                    setItemInHand(InteractionHand.MAIN_HAND, Items.DIAMOND_SWORD.getDefaultInstance());
                }
            }
            setItemSlot(EquipmentSlot.HEAD, Items.DIAMOND_HELMET.getDefaultInstance());
            setItemSlot(EquipmentSlot.CHEST, Items.DIAMOND_CHESTPLATE.getDefaultInstance());
            setItemSlot(EquipmentSlot.LEGS, Items.DIAMOND_LEGGINGS.getDefaultInstance());
            setItemSlot(EquipmentSlot.FEET, Items.DIAMOND_BOOTS.getDefaultInstance());
            bossEvent.setProgress(getHealth() / getMaxHealth());
            LivingEntity target = getTarget();
            if (target == null || !target.isAlive()) {
                stopUsingShield();
            }
            if (isGatekeeperBlocking() && !isBlocking()) {
                startUsingItem(InteractionHand.OFF_HAND);
            }
            if (!isGatekeeperBlocking() && isBlocking()) {
                stopUsingItem();
            }
            if (isBlocking()) {
                usingShieldTicks--;
                if (usingShieldTicks <= 0) {
                    stopUsingShield();
                }
            } else {
                if (usingShieldTicks > 0) {
                    setBlocking(true);
                }
                if (performingRemoteAttack && !targetInRange(2)) {
                    startUsingShield(10);
                }
                if (shieldCoolDown > 0) {
                    shieldCoolDown--;
                } else {
                    List<Projectile> projectiles = getEntitiesNearby(Projectile.class, 20);
                    boolean shouldBlock = false;
                    for (Projectile projectile : projectiles) {
                        if (((projectile.getOwner() instanceof Targeting targeting && targeting.getTarget() != null && targeting.getTarget().getUUID().equals(getUUID())) || (projectile.getOwner() instanceof LivingEntity livingEntity && target != null && livingEntity.getUUID().equals(target.getUUID()))) && projectile.getDeltaMovement().length() > 0.01) {
                            shouldBlock = true;
                        }
                    }
                    if (shouldBlock && getAttackState() == 0) {
                        startUsingShield(40);
                        shieldCoolDown = 120;
                    }
                }
            }
            if (getAttackState() == 0 && isActivated()) {
                setAttackTicks(0);
                if (jumpCoolDown > 0) {
                    jumpCoolDown--;
                }
                if (target != null && target.isAlive()) {
                    if (targetInRange(2) && !isBlocking()) {
                        setAttackState(1);
                        stopUsingItem();
                        performingRemoteAttack = false;
                    } else if (jumpCoolDown <= 0 && !targetInRange(6) && !isBlocking()) {
                        setAttackState(2);
                        jumpCoolDown = 200;
                        performingRemoteAttack = false;
                    } else if (noSuccessMeleeAttackTicks >= 120) {
                        performingRemoteAttack = true;
                    }
                }
            }
            switch (getAttackState()) {
                case -1 -> {
                    if (getAttackState() != 0) {
                        setAttackTicks((getAttackTicks() + 1) % 41);
                        if (getAttackTicks() == 0) {
                            //lock portal
                            setActivated(true);
                        }
                    }
                }
                case 1 -> {
                    if (getAttackTicks() == 6) {
                        performMeleeAttack();
                    }
                    if (getAttackState() != 0) {
                        setAttackTicks((getAttackTicks() + 1) % 9);
                    }
                }
                case 2 -> {
                    if (getAttackTicks() == 20) {
                        hurtMarked = true;
                        hasImpulse = true;
                        setDeltaMovement(this.getDeltaMovement().add(0, 4, 0));
                    }
                    if (getAttackState() != 0) {
                        setAttackTicks((getAttackTicks() + 1) % 21);
                        if (getAttackTicks() == 0) {
                            setAttackState(3);
                            setAttackTicks(1);
                        }
                    }
                }
                case 3 -> {
                    if (getAttackTicks() == 10 && target != null) {
                        hurtMarked = true;
                        setDeltaMovement(this.getDeltaMovement().add((target.getX() - getX()) / 5d, (target.getY() - getY()) / 5d, (target.getZ() - getZ()) / 5d));
                    }
                    if (onGround() && getAttackTicks() > 20) {
                        setAttackTicks(200);
                        CameraShake.cameraShake(level(), position(), 45, 0.03f, 40, 20);
                        playSound(SoundEvents.GENERIC_EXPLODE, getSoundVolume(), getVoicePitch());
                        ((ServerLevel)level()).sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
                        for (int x = -5; x <= 5; x++) {
                            for (int y = -1; y <= 3; y++) {
                                for (int z = -5; z <= 5; z++) {
                                    BlockPos blockPos = blockPosition().offset(x, y, z);
                                    SLFallingBlock fallingBlock = new SLFallingBlock(level(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), level().getBlockState(blockPos), 60);
                                    fallingBlock.push(0, getRandom().nextDouble() / 5 + 0.5, 0);
                                    level().addFreshEntity(fallingBlock);
                                }
                            }
                        }
                        for (LivingEntity livingEntity : getEntitiesNearby(LivingEntity.class, 5)) {
                            livingEntity.hurt(DamageTypeInit.getEntityDamageSource(level(), DamageTypeInit.GROUND_SHAKE, this), 20);
                        }
                    }
                    if (getAttackState() != 0) {
                        setAttackTicks((getAttackTicks() + 1) % 201);
                        if (getAttackTicks() == 0) {
                            setAttackState(4);
                            setAttackTicks(1);
                        }
                    }
                }
                case 4 -> {
                    if (getAttackState() != 0) {
                        setAttackTicks((getAttackTicks() + 1) % 21);
                    }
                }
            }
            if (getAttackTicks() == 0) {
                setAttackState(0);
            }
        } else {
            //clientside
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }
}