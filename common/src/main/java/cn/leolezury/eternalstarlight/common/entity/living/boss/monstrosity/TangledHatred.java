package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.util.Chain;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
    private Vec3 targetPos = null;
    private Vec3 currentTargetPos = null;
    private float speed = 0.1f;
    private int ticksToNextAttack;

    public TangledHatred(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.chain = createChain();
        this.oldChain = createChain();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 100);
    }

    private Chain createChain() {
        return new Chain(position(), 30, 25f / 16f);
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
        if (flag) {
            ticksToNextAttack = 60;
        }
        return flag;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide) {
            LivingEntity target = getTarget();
            if (ticksToNextAttack > 0) ticksToNextAttack--;
            if (targetPos != null) {
                if (currentTargetPos == null) {
                    currentTargetPos = this.chain.getEndPos().orElse(position().add(0, getBbHeight(), 0));
                }
                currentTargetPos = ESMathUtil.approachVec(currentTargetPos, targetPos, speed);
            }
            if (target != null && ticksToNextAttack == 0) {
                targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
            }
            if ((target == null || ticksToNextAttack > 0) && tickCount % 150 == 0) {
                targetPos = this.chain.getEndPos().orElse(position().add(0, getBbHeight(), 0)).offsetRandom(getRandom(), 10);
                if (targetPos.y < position().y) {
                    targetPos = new Vec3(targetPos.x, position().y() + getRandom().nextInt(10, 20), targetPos.z);
                }
            }
            this.speed = target != null && ticksToNextAttack == 0 ? 0.3f : 0.1f;
            if (target != null && ticksToNextAttack == 0 && target.getBoundingBox().contains(this.chain.getEndPos().orElse(position().add(0, getBbHeight(), 0)))) {
                doHurtTarget(target);
            }
            if (currentTargetPos != null) {
                this.chain.update(currentTargetPos, targetPos, position(), 3, 10);
            }
            setSyncedChain(this.chain);
        } else {
            this.oldChain = chain;
            if (getSyncedChain().segments().size() == this.chain.segments().size()) {
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
