package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourManager;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.Chain;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TangledHatred extends ESBoss {
    protected static final EntityDataAccessor<CompoundTag> CHAIN = SynchedEntityData.defineId(TangledHatred.class, EntityDataSerializers.COMPOUND_TAG);

    public Chain getSyncedChain() {
        return Chain.load(this.entityData.get(CHAIN));
    }

    public void setSyncedChain(Chain chain) {
        CompoundTag tag = new CompoundTag();
        chain.save(tag);
        this.entityData.set(CHAIN, tag);
    }

    public Chain chain;
    public Chain oldChain;
    public final List<TangledHatredPart> parts = new ArrayList<>();
    private Vec3 targetPos = null;
    private Vec3 currentTargetPos = null;
    private float speed = 0.1f;
    private int ticksToNextMeleeAttack;

    private final BehaviourManager<TangledHatred> behaviourManager = new BehaviourManager<>(this, List.of(
            new TangledHatredSmokePhase(),
            new TangledHatredSporePhase()
    ));

    public TangledHatred(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.chain = createChain();
        this.oldChain = createChain();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 100);
    }

    private Chain createChain() {
        Chain c = new Chain(position(), 18, 25f / 16f);
        // bend it in advance
        for (int i = 0; i < 18; i++) {
            c.update(position().add(10, 15, 10), position().add(10, 15, 10), position(), 3, 3);
        }
        return c;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        if (this.chain == null) {
            this.chain = createChain();
        }
        CompoundTag tag = new CompoundTag();
        this.chain.save(tag);
        builder.define(CHAIN, tag);
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(0, new HurtByTargetGoal(this, TangledHatred.class).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void setDeltaMovement(Vec3 vec3) {
        super.setDeltaMovement(new Vec3(0, vec3.y < 0 ? vec3.y : 0, 0));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && !level().isClientSide) {
            ticksToNextMeleeAttack = 40;
            CameraShake.createCameraShake(entity.level(), entity.position(), 45, 0.005f, 40, 20);
            this.chain.getEndPos().ifPresent(vec3 -> ((ServerLevel) entity.level()).sendParticles(ESExplosionParticleOptions.LUNAR, vec3.x, vec3.y, vec3.z, 3, 0.1, 0.1, 0.1, 0));
        }
        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (flag && getBehaviourState() == TangledHatredSmokePhase.ID && source.getDirectEntity() instanceof LivingEntity entity) {
            entity.hurtMarked = true;
            entity.setDeltaMovement(entity.position().subtract(position()).normalize().scale(1.5));
        }
        return flag;
    }

    public Optional<Vec3> calculateAttackTargetPos() {
        LivingEntity target = getTarget();
        if (target != null && getBehaviourState() == TangledHatredSporePhase.ID) {
            return Optional.of(target.position().add(0, target.getBbHeight() * 6, 0));
        }
        if (target != null && ticksToNextMeleeAttack == 0) {
            return Optional.of(target.position().add(0, target.getBbHeight() / 2, 0));
        }
        return Optional.empty();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide) {
            if (!isNoAi()) {
                behaviourManager.tick();
            }
            LivingEntity target = getTarget();
            if (ticksToNextMeleeAttack > 0) ticksToNextMeleeAttack--;
            if (targetPos != null) {
                if (currentTargetPos == null) {
                    currentTargetPos = this.chain.getEndPos().orElse(position().add(0, getBbHeight(), 0));
                }
                currentTargetPos = ESMathUtil.approachVec(currentTargetPos, targetPos, speed);
            } else {
                targetPos = position().offsetRandom(getRandom(), 60);
                currentTargetPos = ESMathUtil.approachVec(this.chain.getEndPos().orElse(position().add(0, getBbHeight(), 0)), targetPos, speed);
            }
            if (target != null) {
                calculateAttackTargetPos().ifPresent(vec3 -> targetPos = vec3);
            }
            if (target == null && tickCount % 60 == 0) {
                targetPos = position().offsetRandom(getRandom(), 60);
                if (targetPos.y < position().y) {
                    targetPos = new Vec3(targetPos.x, position().y() + getRandom().nextInt(10, 20), targetPos.z);
                }
            }
            this.speed = target != null && ticksToNextMeleeAttack == 0 ? 0.2f : 0.15f;
            if (target != null && ticksToNextMeleeAttack == 0 && target.getBoundingBox().contains(this.chain.getEndPos().orElse(position().add(0, getBbHeight(), 0)))) {
                doHurtTarget(target);
            }
            if (currentTargetPos != null) {
                this.chain.update(currentTargetPos, targetPos, position(), 3, 5);
            }
            if (level() instanceof ServerLevel serverLevel) {
                for (Chain.Segment segment : chain.segments()) {
                    if (!level().getBlockState(BlockPos.containing(segment.getLowerPosition())).isAir() || !level().getBlockState(BlockPos.containing(segment.getMiddlePosition())).isAir() || !level().getBlockState(BlockPos.containing(segment.getUpperPosition())).isAir()) {
                        Vec3 base = segment.getMiddlePosition();
                        for (int j = 0; j < 2; j++) {
                            Vec3 vec3 = base.offsetRandom(getRandom(), 2);
                            for (int m = 0; m < serverLevel.players().size(); ++m) {
                                ServerPlayer serverPlayer = serverLevel.players().get(m);
                                serverLevel.sendParticles(serverPlayer, ESExplosionParticleOptions.LUNAR, true, vec3.x, vec3.y, vec3.z, 3, 0, 0, 0, 0);
                            }
                        }
                    }
                }
            }

            int numSegments = this.chain.segments().size();
            List<TangledHatredPart> partsToRemove = new ArrayList<>();
            for (TangledHatredPart part : parts) {
                if (part.isRemoved() || part.isDeadOrDying() || part.getParent() == null || !part.getParent().getUUID().equals(getUUID())) {
                    partsToRemove.add(part);
                }
            }
            for (TangledHatredPart part : partsToRemove) {
                parts.remove(part);
            }
            if (parts.size() != numSegments) {
                for (TangledHatredPart part : parts) {
                    part.discard();
                }
                parts.clear();
                for (int i = 0; i < numSegments; i++) {
                    TangledHatredPart part = new TangledHatredPart(ESEntities.TANGLED_HATRED_PART.get(), level());
                    part.setPos(this.chain.segments().get(i).getMiddlePosition());
                    part.setParent(this);
                    level().addFreshEntity(part);
                    parts.add(part);
                }
            }
            if (parts.size() == numSegments) {
                for (int i = 0; i < numSegments; i++) {
                    TangledHatredPart part = parts.get(i);
                    part.setPos(this.chain.segments().get(i).getMiddlePosition());
                }
            }

            setSyncedChain(this.chain);
        } else {
            this.oldChain = chain;
            if (!getSyncedChain().segments().isEmpty()) {
                this.chain = getSyncedChain();
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("Chain", CompoundTag.TAG_COMPOUND)) {
            this.chain = Chain.load(compoundTag.getCompound("Chain"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        CompoundTag chainTag = new CompoundTag();
        this.chain.save(chainTag);
        compoundTag.put("Chain", chainTag);
    }
}
