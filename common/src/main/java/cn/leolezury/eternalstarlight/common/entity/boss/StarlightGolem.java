package cn.leolezury.eternalstarlight.common.entity.boss;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.data.DamageTypeInit;
import cn.leolezury.eternalstarlight.common.entity.attack.FireColumn;
import cn.leolezury.eternalstarlight.common.entity.attack.beam.StarlightGolemBeam;
import cn.leolezury.eternalstarlight.common.entity.boss.bossevent.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.interfaces.LaserCaster;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class StarlightGolem extends ESBoss implements LaserCaster {
    public StarlightGolem(EntityType<? extends ESBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.BLUE, true);

    public AnimationState energyBeamAnimationState = new AnimationState();
    public AnimationState flameAnimationState = new AnimationState();
    public AnimationState shakeGroundAnimationState = new AnimationState();
    public AnimationState startPowerAnimationState = new AnimationState();
    public AnimationState powerAnimationState = new AnimationState();
    public AnimationState endPowerAnimationState = new AnimationState();
    public AnimationState deathAnimationState = new AnimationState();
    int energyBeamCoolDown = 0;
    int flameCoolDown = 0;
    int shakeGroundCoolDown = 0;
    int powerCoolDown = 0;
    private Vec3 targetPos = Vec3.ZERO;

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
        goalSelector.addGoal(1, new LookAtTargetGoal());
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this, StarlightGolem.class)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public boolean shouldLaserFollowHead() {
        return false;
    }

    @Override
    public Vec3 getLaserWantedPos() {
        return getTarget() == null ? position() : getTarget().position();
    }

    @Override
    public void lookAtLaserEnd(Vec3 endPos) {
        setXRot(-ESUtil.positionToPitch(getX(), endPos.x, getY(), endPos.y, getZ(), endPos.z));
        setYBodyRot(ESUtil.positionToYaw(getX(), endPos.x, getZ(), endPos.z) - 90);
        setYHeadRot(ESUtil.positionToYaw(getX(), endPos.x, getZ(), endPos.z) - 90);
    }

    class LookAtTargetGoal extends Goal {
        public LookAtTargetGoal() {
            setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            return StarlightGolem.this.getTarget() != null;
        }

        public boolean canContinueToUse() {
            return StarlightGolem.this.getTarget() != null;
        }

        public void tick() {
            if (StarlightGolem.this.getAttackState() == 3 && StarlightGolem.this.getAttackTicks() >= 25) {
                StarlightGolem.this.getLookControl().setLookAt(StarlightGolem.this.targetPos.x, StarlightGolem.this.targetPos.y, StarlightGolem.this.targetPos.z, 100F, 100F);
            } else if (StarlightGolem.this.getTarget() != null && (StarlightGolem.this.getAttackState() != 1 || StarlightGolem.this.getAttackTicks() <= 55)) {
                StarlightGolem.this.getLookControl().setLookAt(StarlightGolem.this.getTarget(), 100F, 100F);
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
                playSound(SoundEventInit.STARLIGHT_GOLEM_BLOCK.get(), getSoundVolume(), getVoicePitch());
            }
            return false;
        }
    }

    @Override
    protected void tickDeath() {
        if (deathTime == 0) {
            stopAllAnimStates();
            deathAnimationState.start(tickCount);
            setAttackState(-1);
        }
        ++deathTime;
        if (deathTime == 100 && !level().isClientSide()) {
            level().broadcastEntityEvent(this, (byte)60);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    public void stopAllAnimStates() {
        energyBeamAnimationState.stop();
        flameAnimationState.stop();
        shakeGroundAnimationState.stop();
        startPowerAnimationState.stop();
        powerAnimationState.stop();
        endPowerAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case 1 -> energyBeamAnimationState.start(tickCount);
                case 2 -> flameAnimationState.start(tickCount);
                case 3 -> shakeGroundAnimationState.start(tickCount);
                case 4 -> startPowerAnimationState.start(tickCount);
                case 5 -> powerAnimationState.start(tickCount);
                case 6 -> endPowerAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public void push(double p_20286_, double p_20287_, double p_20288_) {
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
                    if (state.is(BlockInit.ENERGY_BLOCK.get()) && (!lit || state.getValue(BlockStateProperties.LIT))) {
                        list.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return list;
    }

    private void litAllEnergyBlocks() {
        List<BlockPos> list = new ArrayList<>();
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(14, 0, 14), false));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(14, 0, -14), false));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-14, 0, 14), false));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-14, 0, -14), false));
        for (BlockPos pos : list) {
            BlockState state = level().getBlockState(pos);
            if (state.is(BlockInit.ENERGY_BLOCK.get()) && !state.getValue(BlockStateProperties.LIT)) {
                level().setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, true));
            }
        }
    }

    private List<BlockPos> getLitEnergyBlocks() {
        List<BlockPos> list = new ArrayList<>();
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(14, 0, 14), true));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(14, 0, -14), true));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-14, 0, 14), true));
        list.addAll(getNearbyEnergyBlocks(new BlockPos((int) position().x, (int) position().y, (int) position().z).offset(-14, 0, -14), true));
        return list;
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
            if (!isSilent()) {
                this.level().broadcastEntityEvent(this, (byte) ClientHandlers.BOSS_MUSIC_ID);
            }
            setDeltaMovement(0, getDeltaMovement().y, 0);
            LivingEntity target = getTarget();
            if (energyBeamCoolDown > 0) {
                energyBeamCoolDown--;
            }
            if (flameCoolDown > 0) {
                flameCoolDown--;
            }
            if (shakeGroundCoolDown > 0) {
                shakeGroundCoolDown--;
            }
            if (powerCoolDown > 0) {
                powerCoolDown--;
            }
            if (getAttackState() == 0) {
                setAttackTicks(0);
                if (target != null && target.isAlive()) {
                    if (energyBeamCoolDown == 0) {
                        setAttackState(1);
                        energyBeamCoolDown = 700;
                    } else if (flameCoolDown == 0) {
                        setAttackState(2);
                        flameCoolDown = 700;
                    } else if (shakeGroundCoolDown == 0) {
                        setAttackState(3);
                        shakeGroundCoolDown = 700;
                    } else if (powerCoolDown == 0) {
                        setAttackState(4);
                        powerCoolDown = 700;
                    }
                }
            }
            switch (getAttackState()) {
                case 1 -> {
                    if (getAttackTicks() == 60) {
                        playSound(SoundEventInit.STARLIGHT_GOLEM_PREPARE_BEAM.get(), getSoundVolume(), getVoicePitch());
                        StarlightGolemBeam beam = new StarlightGolemBeam(EntityInit.STARLIGHT_GOLEM_BEAM.get(), level(), this, getX(), getY() + 1, getZ(), (float) ((yHeadRot + 90) * Math.PI / 180.0d), (float) (-getXRot() * Math.PI / 180.0d), 100);
                        beam.setPos(position());
                        level().addFreshEntity(beam);
                    }

                    if (getAttackTicks() >= 60 && getAttackTicks() % 40 == 0) {
                        CameraShake.createCameraShake(level(), position(), 45, 0.02f, 40, 20);
                        for (int x = -1; x <= 1; x += 2) {
                            for (int z = -1; z <= 1; z += 2) {
                                FireColumn fireColumn = EntityInit.FIRE_COLUMN.get().create(level());
                                fireColumn.setPos(position().add(x * 5, 0, z * 5));
                                fireColumn.setOwner(this);
                                level().addFreshEntity(fireColumn);
                            }
                        }
                    }

                    setAttackTicks((getAttackTicks() + 1) % 201);
                }
                case 2 -> {
                    if (getAttackTicks() % 20 == 0 && target != null) {
                        CameraShake.createCameraShake(level(), position(), 45, 0.02f, 40, 20);
                        FireColumn fireColumn = EntityInit.FIRE_COLUMN.get().create(level());
                        fireColumn.setPos(target.position());
                        fireColumn.setOwner(this);
                        level().addFreshEntity(fireColumn);
                    }

                    setAttackTicks((getAttackTicks() + 1) % 201);
                }
                case 3 -> {
                    if (getAttackTicks() == 25 && target != null) {
                        targetPos = new Vec3(target.getX(), getY(), target.getZ());
                    }
                    
                    if (getAttackTicks() > 25) {
                        setYBodyRot(ESUtil.positionToYaw(getX(), targetPos.x, getZ(), targetPos.z));
                    }

                    if (getAttackTicks() == 40 && targetPos.distanceTo(position()) < 25) {
                        List<BlockPos> blockList = new ArrayList<>();
                        CameraShake.createCameraShake(level(), position(), 45, 0.02f, 40, 20);
                        float distance = Mth.sqrt((float) distanceToSqr(targetPos));
                        for (int i = 0; i <= Mth.ceil(distance); i += 4) {
                            AABB aabb = new AABB(getX() - 1.5, getY() - 1.5, getZ() - 1.5, getX() + 1.5, getY() + 1.5, getZ() + 1.5).move(targetPos.add(position().add(0, 1, 0).scale(-1)).scale(((double) i) / ((double) Mth.ceil(distance))));
                            for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, aabb)) {
                                if (!(livingEntity instanceof StarlightGolem)) {
                                    livingEntity.hurt(DamageTypeInit.getEntityDamageSource(level(), DamageTypeInit.GROUND_SHAKE, this), 40);
                                }
                            }
                            for (int x = (int) aabb.minX; x <= aabb.maxX; x++) {
                                for (int y = (int) aabb.minY; y <= aabb.maxY; y++) {
                                    for (int z = (int) aabb.minZ; z <= aabb.maxZ; z++) {
                                        BlockPos pos = new BlockPos(x, y, z);
                                        boolean hasPos = false;
                                        for (BlockPos blockPos : blockList) {
                                            if (blockPos.equals(pos)) {
                                                hasPos = true;
                                                break;
                                            }
                                        }
                                        if (!hasPos) {
                                            blockList.add(pos);
                                        }
                                    }
                                }
                            }
                        }
                        for (BlockPos blockPos : blockList) {
                            ESFallingBlock fallingBlock = new ESFallingBlock(level(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), level().getBlockState(blockPos), 60);
                            fallingBlock.push(0, getRandom().nextDouble() / 5 + 0.5, 0);
                            level().addFreshEntity(fallingBlock);
                        }
                    }

                    setAttackTicks((getAttackTicks() + 1) % 61);
                }
                case 4 -> {
                    if (getAttackTicks() == 1) {
                        playSound(SoundEventInit.STARLIGHT_GOLEM_PREPARE_CHARGE.get(), getSoundVolume(), getVoicePitch());
                        litAllEnergyBlocks();
                    }
                    if (!canHurt()) {
                        heal(0.1f);
                    }

                    setAttackTicks((getAttackTicks() + 1) % 21);
                    if (getAttackTicks() == 0) {
                        setAttackState(5);
                        setAttackTicks(1);
                    }
                }
                case 5 -> {
                    if (!canHurt()) {
                        heal(0.1f);
                    }

                    setAttackTicks((getAttackTicks() + 1) % 601);
                    if (getAttackTicks() == 0) {
                        setAttackState(6);
                        setAttackTicks(1);
                    }
                }
                case 6 -> {
                    if (!canHurt()) {
                        heal(0.1f);
                    }

                    setAttackTicks((getAttackTicks() + 1) % 11);
                }
            }
            if (getAttackTicks() == 0) {
                setAttackState(0);
            }
        } else {
            if (getAttackState() == 5 && !canHurt()) {
                List<BlockPos> list = getLitEnergyBlocks();
                for (BlockPos pos : list) {
                    Vec3 angle = position().add(- pos.getX() - 0.5, - pos.getY() - 1.0, - pos.getZ() - 0.5);
                    double px = pos.getX() + 0.5;
                    double py = pos.getY() + 1.0;
                    double pz = pos.getZ() + 0.5;

                    for (int i = 0; i < 10; i++) {
                        double dx = angle.x();
                        double dy = angle.y();
                        double dz = angle.z();

                        double spread = 5.0D + getRandom().nextFloat() * 2.5D;
                        double velocity = (3.0D + getRandom().nextFloat() * 0.15D) / 45.0D;

                        dx += getRandom().nextGaussian() * 0.0075D * spread;
                        dy += getRandom().nextGaussian() * 0.0075D * spread;
                        dz += getRandom().nextGaussian() * 0.0075D * spread;
                        dx *= velocity;
                        dy *= velocity;
                        dz *= velocity;

                        level().addParticle(ParticleInit.ENERGY.get(), px, py, pz, dx, dy, dz);
                    }
                }
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEventInit.STARLIGHT_GOLEM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.STARLIGHT_GOLEM_DEATH.get();
    }
}
