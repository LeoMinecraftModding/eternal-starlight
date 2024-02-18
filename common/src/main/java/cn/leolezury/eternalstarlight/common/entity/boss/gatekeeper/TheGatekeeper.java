package cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.ai.goal.GatekeeperTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.ai.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.boss.AttackManager;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import java.util.Optional;

public class TheGatekeeper extends ESBoss {
    public TheGatekeeper(EntityType<? extends ESBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.PURPLE, false);

    private final AttackManager<TheGatekeeper> attackManager = new AttackManager<>(this, List.of(
            new GatekeeperMeleePhase(),
            new GatekeeperDodgePhase(),
            new GatekeeperDashPhase(),
            new GatekeeperCastFireballPhase()
    ));

    public AnimationState meleeAnimationStateA = new AnimationState();
    public AnimationState meleeAnimationStateB = new AnimationState();
    public AnimationState meleeAnimationStateC = new AnimationState();
    public AnimationState dodgeAnimationState = new AnimationState();
    public AnimationState dashAnimationState = new AnimationState();
    public AnimationState castFireballAnimationState = new AnimationState();
    private String gatekeeperName = "TheGatekeeper";
    private ServerPlayer conversationTarget;
    private String fightTarget;
    private boolean fightPlayerOnly = true;

    public void setFightTargetName(String fightTarget) {
        this.fightTarget = fightTarget;
    }

    public String getFightTargetName() {
        return fightTarget;
    }

    public Optional<? extends Player> getFightTarget() {
        return level().players().stream().filter(p -> p.getName().getString().equals(fightTarget)).findFirst();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        gatekeeperName = compoundTag.getString("GatekeeperName");
        fightTarget = compoundTag.getString("FightTarget");
        fightPlayerOnly = compoundTag.getBoolean("FightPlayerOnly");
        bossEvent.setId(getUUID());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("GatekeeperName", gatekeeperName);
        compoundTag.putString("FightTarget", fightTarget);
        compoundTag.putBoolean("FightPlayerOnly", fightPlayerOnly);
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
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.5D, false) {
            @Override
            protected void checkAndPerformAttack(LivingEntity livingEntity) {

            }
        });
        goalSelector.addGoal(2, new LookAtTargetGoal(this));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, new GatekeeperTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && fightPlayerOnly;
            }
        });
        targetSelector.addGoal(1, new HurtByTargetGoal(this, TheGatekeeper.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && !fightPlayerOnly;
            }
        }.setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && !fightPlayerOnly;
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 400.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 15.0D)
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
        meleeAnimationStateC.stop();
        dodgeAnimationState.stop();
        dashAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case GatekeeperMeleePhase.ID -> {
                    switch (getRandom().nextInt(3)) {
                        case 0 -> meleeAnimationStateA.start(tickCount);
                        case 1 -> meleeAnimationStateB.start(tickCount);
                        case 2 -> meleeAnimationStateC.start(tickCount);
                    }
                }
                case GatekeeperDodgePhase.ID -> dodgeAnimationState.start(tickCount);
                case GatekeeperDashPhase.ID -> dashAnimationState.start(tickCount);
                case GatekeeperCastFireballPhase.ID -> castFireballAnimationState.start(tickCount);
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

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (!level().isClientSide && player instanceof ServerPlayer serverPlayer && conversationTarget == null && getTarget() == null && getFightTarget().isEmpty()) {
            conversationTarget = serverPlayer;
            ESPlatform.INSTANCE.sendToClient(serverPlayer, new OpenGatekeeperGuiPacket(getId()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void handleDialogueClose(int operation) {
        conversationTarget = null;
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
    public boolean hurt(DamageSource source, float amount) {
        if (!source.is(DamageTypes.GENERIC_KILL) && (source.getEntity() == null || getTarget() == null || source.getEntity().getUUID() != getTarget().getUUID())) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.update();
        if (!level().isClientSide) {
            setCustomName(Component.literal(gatekeeperName));
            if (isLeftHanded()) {
                setLeftHanded(false);
            }
            if (isActivated()) {
                attackManager.tick();
                if (!isSilent()) {
                    this.level().broadcastEntityEvent(this, (byte) ClientHandlers.BOSS_MUSIC_ID);
                }
            }
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
