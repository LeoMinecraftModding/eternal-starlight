package cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.ai.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.boss.AttackManager;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheGatekeeper extends ESBoss {
    public TheGatekeeper(EntityType<? extends ESBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.PURPLE, false);

    private final AttackManager<TheGatekeeper> attackManager = new AttackManager<>(this, List.of(
            new GatekeeperMeleePhase(),
            new GatekeeperDodgePhase(),
            new GatekeeperDashPhase()
    ));

    public AnimationState meleeAnimationStateA = new AnimationState();
    public AnimationState meleeAnimationStateB = new AnimationState();
    public AnimationState dodgeAnimationState = new AnimationState();
    public AnimationState dashAnimationState = new AnimationState();
    private String gatekeeperName = "TheGatekeeper";

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        gatekeeperName = compoundTag.getString("GatekeeperName");
        bossEvent.setId(getUUID());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
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
        goalSelector.addGoal(2, new LookAtTargetGoal(this));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, new HurtByTargetGoal(this, TheGatekeeper.class).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static class TheGatekeeperMeleeAttackGoal extends MeleeAttackGoal {
        public TheGatekeeperMeleeAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
            super(mob, speed, followingTargetEvenIfNotSeen);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        RandomSource randomSource = serverLevelAccessor.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        return spawnGroupData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
    }

    @Override
    protected void tickDeath() {
        //TODO: change to trading mode
        super.tickDeath();
    }

    public void stopAllAnimStates() {
        meleeAnimationStateA.stop();
        meleeAnimationStateB.stop();
        dodgeAnimationState.stop();
        dashAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case GatekeeperMeleePhase.ID -> (getRandom().nextBoolean() ? meleeAnimationStateA : meleeAnimationStateB).start(tickCount);
                case GatekeeperDodgePhase.ID -> dodgeAnimationState.start(tickCount);
                case GatekeeperDashPhase.ID -> dashAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
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
    }

    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == ClientHandlers.BOSS_MUSIC_ID) {
            ClientHandlers.handleEntityEvent(this, id);
        } else {
            super.handleEntityEvent(id);
        }
    }


    public boolean canReachTarget(double range) {
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

    public void performMeleeAttack(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                livingEntity.invulnerableTime = 0;
                doHurtTarget(livingEntity);
                spawnMeleeAttackParticles();
            }
        }
    }

    public void spawnMeleeAttackParticles() {
        if (level() instanceof ServerLevel serverLevel) {
            float lookYaw = getYHeadRot() + 90.0f;
            float lookPitch = -getXRot();
            Vec3 initialEndPos = ESUtil.rotationToPosition(getEyePosition(), 1f, lookPitch, lookYaw);
            for (int i = 0; i < 15; i++) {
                Vec3 endPos = initialEndPos.offsetRandom(getRandom(), 0.8f);
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ESParticlePacket(ParticleInit.BLADE_SHOCKWAVE.get(), getEyePosition().x, getEyePosition().y, getEyePosition().z, endPos.x - getEyePosition().x, endPos.y - getEyePosition().y, endPos.z - getEyePosition().z));
            }
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
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }
}
