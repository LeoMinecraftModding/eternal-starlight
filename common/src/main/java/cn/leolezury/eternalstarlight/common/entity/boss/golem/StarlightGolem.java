package cn.leolezury.eternalstarlight.common.entity.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.ai.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.boss.AttackManager;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.interfaces.LaserCaster;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class StarlightGolem extends ESBoss implements LaserCaster {
    public StarlightGolem(EntityType<? extends ESBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.BLUE, true);

    private final AttackManager<StarlightGolem> attackManager = new AttackManager<>(this, List.of(
            new StarlightGolemLaserBeamPhase(),
            new StarlightGolemSpitFlamePhase(),
            new StarlightGolemSummonFlamePhase(),
            new StarlightGolemSmashPhase()
    ));

    public AnimationState laserBeamAnimationState = new AnimationState();
    public AnimationState spitFlameAnimationState = new AnimationState();
    public AnimationState summonFlameAnimationState = new AnimationState();
    public AnimationState smashAnimationState = new AnimationState();
    public AnimationState deathAnimationState = new AnimationState();

    public boolean canHurt() {
        return getLitEnergyBlocks().isEmpty();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        bossEvent.setId(getUUID());
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
        goalSelector.addGoal(1, new GolemLookAtTargetGoal());
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this, StarlightGolem.class)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public boolean isLaserFollowingHeadRotation() {
        return false;
    }

    @Override
    public Vec3 getLaserRotationTarget() {
        return getTarget() == null ? position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f)) : getTarget().position().add(getTarget().getBbWidth() * (getRandom().nextFloat() - 0.5f), getTarget().getBbHeight() * getRandom().nextFloat(), getTarget().getBbWidth() * (getRandom().nextFloat() - 0.5f));
    }

    @Override
    public void lookAtLaserEnd(Vec3 endPos) {
        setXRot(-ESMathUtil.positionToPitch(position(), endPos));
        setYBodyRot(ESMathUtil.positionToYaw(position(), endPos) - 90);
        setYHeadRot(ESMathUtil.positionToYaw(position(), endPos) - 90);
    }

    private class GolemLookAtTargetGoal extends LookAtTargetGoal {
        public GolemLookAtTargetGoal() {
            super(StarlightGolem.this);
        }

        @Override
        public void tick() {
            boolean affectsLook =
                    StarlightGolem.this.getAttackState() == StarlightGolemLaserBeamPhase.ID
                            || StarlightGolem.this.getAttackState() == StarlightGolemSpitFlamePhase.ID;
            if (!affectsLook) {
                super.tick();
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 220.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.is(DamageTypes.GENERIC_KILL)) {
            return super.hurt(damageSource, f);
        } else if (canHurt() && getAttackState() == 5 && !damageSource.is(DamageTypes.FALL)) {
            if (getAttackTicks() < 500) {
                setAttackTicks(getAttackTicks() + 40);
            }
            return super.hurt(damageSource, f);
        } else {
            if (damageSource.getDirectEntity() instanceof LivingEntity) {
                playSound(ESSoundEvents.STARLIGHT_GOLEM_BLOCK.get(), getSoundVolume(), getVoicePitch());
            }
            return false;
        }
    }

    @Override
    protected void tickDeath() {
        if (deathTime == 0) {
            stopAllAnimStates();
            deathAnimationState.start(tickCount);
            setAttackState(0);
        }
        ++deathTime;
        if (deathTime == 100 && !level().isClientSide()) {
            level().broadcastEntityEvent(this, (byte)60);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    public void stopAllAnimStates() {
        laserBeamAnimationState.stop();
        spitFlameAnimationState.stop();
        summonFlameAnimationState.stop();
        smashAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case StarlightGolemLaserBeamPhase.ID -> laserBeamAnimationState.start(tickCount);
                case StarlightGolemSpitFlamePhase.ID -> spitFlameAnimationState.start(tickCount);
                case StarlightGolemSummonFlamePhase.ID -> summonFlameAnimationState.start(tickCount);
                case StarlightGolemSmashPhase.ID -> smashAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBossMove() {
        return false;
    }

    private List<BlockPos> getNearbyEnergyBlocks(BlockPos center, boolean lit) {
        List<BlockPos> list = new ArrayList<>();
        for (int x = center.getX() - 1; x <= center.getX() + 1; x++) {
            for (int y = center.getY() - 1; y <= center.getY() + 1; y++) {
                for (int z = center.getZ() - 1; z <= center.getZ() + 1; z++) {
                    BlockState state = level().getBlockState(new BlockPos(x, y, z));
                    if (state.is(ESBlocks.ENERGY_BLOCK.get()) && (!lit || state.getValue(BlockStateProperties.LIT))) {
                        list.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return list;
    }

    private void litAllEnergyBlocks() {
        List<BlockPos> list = new ArrayList<>();
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(12, 0, 12), false));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(12, 0, -12), false));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-12, 0, 12), false));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-12, 0, -12), false));
        for (BlockPos pos : list) {
            BlockState state = level().getBlockState(pos);
            if (state.is(ESBlocks.ENERGY_BLOCK.get()) && !state.getValue(BlockStateProperties.LIT)) {
                level().setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, true));
            }
        }
    }

    private List<BlockPos> getLitEnergyBlocks() {
        List<BlockPos> list = new ArrayList<>();
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(12, 0, 12), true));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(12, 0, -12), true));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-12, 0, 12), true));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-12, 0, -12), true));
        return list;
    }

    public void spawnEnergizedFlame(int maxNum, int scanRadius, boolean trackTarget) {
        int left = maxNum;
        if (trackTarget) {
            EnergizedFlame energizedFlame = ESEntities.ENERGIZED_FLAME.get().create(level());
            energizedFlame.setPos(getTarget() != null ? getTarget().position() : position());
            energizedFlame.setOwner(this);
            level().addFreshEntity(energizedFlame);
            left--;
        }
        List<BlockPos> possiblePositions = new ArrayList<>();
        for (int x = -scanRadius; x <= scanRadius; x += 1) {
            for (int z = -scanRadius; z <= scanRadius; z += 1) {
                for (int y = -5; y <= 5; y++) {
                    BlockPos firePos = blockPosition().offset(x, y, z);
                    if (level().isEmptyBlock(firePos) && level().getBlockState(firePos.below()).isFaceSturdy(level(), firePos.below(), Direction.UP)) {
                        possiblePositions.add(firePos);
                    }
                }
            }
        }
        for (int i = 0; i < left; i++) {
            if (!possiblePositions.isEmpty()) {
                BlockPos firePos = possiblePositions.get(getRandom().nextInt(possiblePositions.size()));
                EnergizedFlame energizedFlame = ESEntities.ENERGIZED_FLAME.get().create(level());
                energizedFlame.setPos(firePos.getCenter().add(0, -0.5, 0));
                energizedFlame.setOwner(this);
                level().addFreshEntity(energizedFlame);
                possiblePositions.remove(firePos);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.update();
        if (!level().isClientSide) {
            attackManager.tick();
        } else {
            if (getRandom().nextInt(15) == 0) {
                Vec3 smokePos = position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f));
                level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, smokePos.x, smokePos.y, smokePos.z, (getRandom().nextFloat() - 0.5f) * 0.15, getRandom().nextFloat() * 0.15, (getRandom().nextFloat() - 0.5f) * 0.15);
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return ESSoundEvents.STARLIGHT_GOLEM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ESSoundEvents.STARLIGHT_GOLEM_DEATH.get();
    }
}
