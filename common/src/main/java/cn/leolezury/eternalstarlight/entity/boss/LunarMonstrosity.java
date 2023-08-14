package cn.leolezury.eternalstarlight.entity.boss;

import cn.leolezury.eternalstarlight.datagen.generator.DamageTypeGenerator;
import cn.leolezury.eternalstarlight.entity.attack.Vine;
import cn.leolezury.eternalstarlight.entity.boss.bossevent.SLServerBossEvent;
import cn.leolezury.eternalstarlight.entity.projectile.Spore;
import cn.leolezury.eternalstarlight.event.client.ClientEvents;
import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ParticleInit;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;
import java.util.Random;

public class LunarMonstrosity extends AbstractSLBoss {
    public LunarMonstrosity(EntityType<? extends AbstractSLBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final SLServerBossEvent bossEvent = new SLServerBossEvent(this, getUUID(), BossEvent.BossBarColor.PURPLE, true);

    public AnimationState toxicBreathAnimationState = new AnimationState();
    public AnimationState sporeAnimationState = new AnimationState();
    public AnimationState vineAnimationState = new AnimationState();
    public AnimationState biteAnimationState = new AnimationState();
    public AnimationState disappearAnimationState = new AnimationState();
    public AnimationState sneakAnimationState = new AnimationState();
    public AnimationState appearAnimationState = new AnimationState();
    public AnimationState switchPhaseAnimationState = new AnimationState();
    public AnimationState deathAnimationState = new AnimationState();
    int toxicBreathCoolDown = 0;
    int sporeCoolDown = 0;
    int vineCoolDown = 0;
    int biteCoolDown = 0;
    int sneakCoolDown = 0;
    private Vec3 targetPos = Vec3.ZERO;

    @OnlyIn(Dist.CLIENT)
    public Vec3 headPos = Vec3.ZERO;

    @Override
    public void setAttackState(int attackState) {
        super.setAttackState(attackState);
    }

    protected static final EntityDataAccessor<Float> PARTICLE_ANGLE_X = SynchedEntityData.defineId(LunarMonstrosity.class, EntityDataSerializers.FLOAT);
    public double getParticleAngleX() {
        return entityData.get(PARTICLE_ANGLE_X);
    }
    protected static final EntityDataAccessor<Float> PARTICLE_ANGLE_Y = SynchedEntityData.defineId(LunarMonstrosity.class, EntityDataSerializers.FLOAT);
    public double getParticleAngleY() {
        return entityData.get(PARTICLE_ANGLE_Y);
    }
    protected static final EntityDataAccessor<Float> PARTICLE_ANGLE_Z = SynchedEntityData.defineId(LunarMonstrosity.class, EntityDataSerializers.FLOAT);
    public double getParticleAngleZ() {
        return entityData.get(PARTICLE_ANGLE_Z);
    }

    public void setParticleAngle(double x, double y, double z) {
        entityData.set(PARTICLE_ANGLE_X, (float) x);
        entityData.set(PARTICLE_ANGLE_Y, (float) y);
        entityData.set(PARTICLE_ANGLE_Z, (float) z);
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(PARTICLE_ANGLE_X, (float) 0);
        entityData.define(PARTICLE_ANGLE_Y, (float) 0);
        entityData.define(PARTICLE_ANGLE_Z, (float) 0);
    }

    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new LunarMonstrosityMeleeAttackGoal(this, 1.0D, false));
        goalSelector.addGoal(2, new LookAtTargetGoal());
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this, LunarMonstrosity.class)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
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
            return LunarMonstrosity.this.getTarget() != null;
        }

        public boolean canContinueToUse() {
            return LunarMonstrosity.this.getTarget() != null;
        }

        public void tick() {
            if (LunarMonstrosity.this.getAttackState() == 1) {
                LunarMonstrosity.this.getLookControl().setLookAt(LunarMonstrosity.this.targetPos.x, LunarMonstrosity.this.targetPos.y, LunarMonstrosity.this.targetPos.z);
            } else if (LunarMonstrosity.this.getTarget() != null) {
                LunarMonstrosity.this.getLookControl().setLookAt(LunarMonstrosity.this.getTarget(), 100F, 100F);
            }
        }
    }

    static class LunarMonstrosityMeleeAttackGoal extends MeleeAttackGoal {
        public LunarMonstrosityMeleeAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
            super(mob, speed, followingTargetEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            boolean canUse = true;
            if (mob instanceof LunarMonstrosity boss) {
                canUse = boss.getAttackState() == 6;
            }
            return super.canUse() && canUse;
        }

        @Override
        public boolean canContinueToUse() {
            boolean canUse = true;
            if (mob instanceof LunarMonstrosity boss) {
                canUse = boss.getAttackState() == 6;
            }
            return super.canContinueToUse() && canUse;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void tickDeath() {
        if (deathTime == 0) {
            stopAllAnimStates();
            deathAnimationState.start(tickCount);
            setAttackState(-3);
        }
        ++deathTime;
        if (deathTime == 100 && !level().isClientSide()) {
            level().broadcastEntityEvent(this, (byte)60);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    public void stopAllAnimStates() {
        toxicBreathAnimationState.stop();
        sporeAnimationState.stop();
        vineAnimationState.stop();
        switchPhaseAnimationState.stop();
        biteAnimationState.stop();
        disappearAnimationState.stop();
        sneakAnimationState.stop();
        appearAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case -1 -> switchPhaseAnimationState.start(tickCount);
                case 1 -> toxicBreathAnimationState.start(tickCount);
                case 2 -> sporeAnimationState.start(tickCount);
                case 3 -> vineAnimationState.start(tickCount);
                case 4 -> biteAnimationState.start(tickCount);
                case 5 -> disappearAnimationState.start(tickCount);
                case 6 -> sneakAnimationState.start(tickCount);
                case 7 -> appearAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void blockedByShield(LivingEntity blocker) {
        if (getAttackState() == 4 && blocker.getUUID().equals(getTarget().getUUID())) {
            setAttackState(-2);
        }
        super.blockedByShield(blocker);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        setAttackState(7);
        setAttackTicks(1);
        return super.doHurtTarget(target);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.GENERIC_KILL)) {
            return super.hurt(damageSource, amount);
        }
        if (getAttackState() == 6) {
            return false;
        }
        if (getPhase() == 0 && getHealth() / getMaxHealth() < 0.5) {
            setPhase(1);
            setAttackState(-1);
            setAttackTicks(1);
            return super.hurt(damageSource, amount / 2f);
        }
        if (damageSource.getEntity() != null && getTarget() != null) {
            if (getAttackState() == 4 && damageSource.getEntity().getUUID().equals(getTarget().getUUID()) && amount >= 6) {
                setAttackState(-2);
            }
        }
        if (isOnFire() || getAttackState() == -2) {
            return super.hurt(damageSource, amount);
        } else {
            return super.hurt(damageSource, Math.min(1, amount));
        }
    }

    protected InteractionResult mobInteract(Player p_32301_, InteractionHand p_32302_) {
        ItemStack itemstack = p_32301_.getItemInHand(p_32302_);
        if (itemstack.is(Items.FLINT_AND_STEEL) && getPhase() == 0) {
            this.level().playSound(p_32301_, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            setSecondsOnFire(5);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(p_32301_, p_32302_);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return getAttackState() == 6 ? super.getDimensions(pose).scale(0.1f) : super.getDimensions(pose);
    }

    private void doBiteDamage(float damage) {
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(3))) {
            Vec3 vec3 = livingEntity.position().vectorTo(this.position()).normalize();
            vec3 = new Vec3(vec3.x, 0.0D, vec3.z);
            if (vec3.dot(this.getViewVector(1.0F)) < 0.0D) {
                livingEntity.hurt(DamageTypeGenerator.getEntityDamageSource(level(), DamageTypeGenerator.BITE, this), damage);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == ClientEvents.BOSS_MUSIC_ID) {
            ClientEvents.handleEntityEvent(this, id);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.update();
        refreshDimensions();
        if (!level().isClientSide) {
            if (!isSilent()) {
                this.level().broadcastEntityEvent(this, (byte)ClientEvents.BOSS_MUSIC_ID);
            }
            setParticleAngle((targetPos.x - getX()) / 10D, (targetPos.y - getY() - 2) / 10D, (targetPos.z - getZ()) / 10D);
            LivingEntity target = getTarget();
            if (toxicBreathCoolDown > 0) {
                toxicBreathCoolDown--;
            }
            if (sporeCoolDown > 0) {
                sporeCoolDown--;
            }
            if (vineCoolDown > 0) {
                vineCoolDown--;
            }
            if (biteCoolDown > 0) {
                biteCoolDown--;
            }
            if (sneakCoolDown > 0) {
                sneakCoolDown--;
            }
            if (getAttackState() == 0) {
                setAttackTicks(0);
                if (target != null && target.isAlive()) {
                    if (toxicBreathCoolDown == 0 && getPhase() == 1) {
                        setAttackState(1);
                        toxicBreathCoolDown = 400;
                    } else if (sporeCoolDown == 0) {
                        setAttackState(2);
                        sporeCoolDown = 400;
                    } else if (vineCoolDown == 0 && getPhase() == 0) {
                        setAttackState(3);
                        vineCoolDown = 400;
                    } else if (biteCoolDown == 0 && getPhase() == 1) {
                        setAttackState(4);
                        biteCoolDown = 400;
                    } else if (sneakCoolDown == 0) {
                        setAttackState(5);
                        sneakCoolDown = 400;
                    }
                }
            }
            switch (getAttackState()) {
                case -2 -> {
                    setAttackTicks((getAttackTicks() + 1) % 101);
                }
                case -1 -> {
                    if (getAttackTicks() == 1) {
                        playSound(SoundEventInit.LUNAR_MONSTROSITY_ROAR.get(), getSoundVolume() / 2, getVoicePitch());
                    }
                    if (getAttackTicks() == 75) {
                        playSound(SoundEventInit.LUNAR_MONSTROSITY_BITE.get(), getSoundVolume(), getVoicePitch());
                    }
                    if (getAttackTicks() == 90) {
                        doBiteDamage(40);
                    }
                    setAttackTicks((getAttackTicks() + 1) % 101);
                }
                case 1 -> {
                    if (getAttackTicks() == 1 && target != null) {
                        targetPos = target.position();
                    }

                    if (distanceToSqr(targetPos) < 900 && getAttackTicks() >= 60) {
                        float distance = Mth.sqrt((float) distanceToSqr(targetPos));
                        for (double i = 0; i <= Mth.ceil(distance); i += 0.5) {
                            AABB aabb = new AABB(getX() - 1, getY() + 1, getZ() - 1, getX() + 1, getY() + 3, getZ() + 1).move(targetPos.add(position().add(0, 2, 0).scale(-1)).scale(((double) i) / ((double) Mth.ceil(distance))));
                            for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, aabb)) {
                                if (!(livingEntity instanceof LunarMonstrosity)) {
                                    livingEntity.hurt(DamageTypeGenerator.getEntityDamageSource(level(), DamageTypeGenerator.POISON, this), 2);
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
                                }
                            }
                        }
                    }

                    if (target != null) {
                        targetPos =
                                targetPos.add(target.position().add(0, target.getBoundingBox().getYsize() / 2.0, 0)
                                        .add(targetPos.scale(-1)).scale(0.1));
                    }

                    setAttackTicks((getAttackTicks() + 1) % 201);
                }
                case 2 -> {
                    if (getAttackTicks() % 10 == 0 && target != null) {
                        double x = target.getX() - getX();
                        double y = target.getY() - getY() - 2;
                        double z = target.getZ() - getZ();
                        Spore spore = new Spore(level(), this, x, y, z);
                        spore.setPos(position().add(0, 2, 0));
                        spore.setOwner(this);
                        level().addFreshEntity(spore);
                    }

                    setAttackTicks((getAttackTicks() + 1) % 101);
                }
                case 3 -> {
                    if (getAttackTicks() % 15 == 0 && target != null) {
                        for (int i = 0; i < 5; i++) {
                            Vine vine = EntityInit.VINE.get().create(level());
                            Random random = new Random();
                            vine.setPos(target.position().add(random.nextDouble(), 0.2, random.nextDouble()));
                            vine.setAttackMode(1);
                            vine.setOwner(this);
                            level().addFreshEntity(vine);
                        }
                    }

                    setAttackTicks((getAttackTicks() + 1) % 91);
                }
                case 4 -> {
                    if (getAttackTicks() == 0) {
                        playSound(SoundEventInit.LUNAR_MONSTROSITY_BITE.get(), getSoundVolume(), getVoicePitch());
                    }
                    if (getAttackTicks() == 10) {
                        doBiteDamage(20);
                    }

                    setAttackTicks((getAttackTicks() + 1) % 21);
                }
                case 5 -> {
                    setAttackTicks((getAttackTicks() + 1) % 31);
                    if (getAttackTicks() == 0) {
                        setAttackState(6);
                        setAttackTicks(1);
                    }
                }
                case 6 -> {
                    setAttackTicks((getAttackTicks() + 1) % 301);
                    if (getAttackTicks() == 0) {
                        setAttackState(7);
                        setAttackTicks(1);
                    }
                }
                case 7 -> {
                    setAttackTicks((getAttackTicks() + 1) % 31);
                }
            }
            if (getAttackTicks() == 0) {
                setAttackState(0);
            }
        } else {
            level().addParticle(ParticleInit.POISON.get(), getX() + (getRandom().nextDouble() - 0.5) * 3, getY() + 1 + (getRandom().nextDouble() - 0.5) * 3, getZ() + (getRandom().nextDouble() - 0.5) * 3, 0, 0, 0);
            if (deathTime <= 0) {
                switch (getAttackState()) {
                    case -2 -> {
                        level().addParticle(ParticleTypes.CRIT, headPos.x + getRandom().nextDouble() - 0.5, headPos.y, headPos.z + getRandom().nextDouble() - 0.5, getRandom().nextDouble() / 10, 0.8, getRandom().nextDouble() / 10);
                    }
                    case 1 -> {
                        Vec3 angle = new Vec3(getParticleAngleX(), getParticleAngleY(), getParticleAngleZ());
                        double px = headPos.x + angle.x() * 0.5D;
                        double py = headPos.y;
                        double pz = headPos.z + angle.z() * 0.5D;

                        for (int i = 0; i < 10; i++) {
                            double dx = angle.x();
                            double dy = angle.y();
                            double dz = angle.z();

                            double spread = 5.0D + getRandom().nextFloat() * 2.5D;
                            double velocity = 3.0D + getRandom().nextFloat() * 0.15D;

                            dx += getRandom().nextGaussian() * 0.0075D * spread;
                            dy += getRandom().nextGaussian() * 0.0075D * spread;
                            dz += getRandom().nextGaussian() * 0.0075D * spread;
                            dx *= velocity;
                            dy *= velocity;
                            dz *= velocity;

                            level().addParticle(ParticleInit.POISON.get(), px, py, pz, dx / 5, dy / 5, dz / 5);
                        }
                    }
                    case 6 -> {
                        RandomSource randomsource = this.getRandom();
                        BlockState blockstate = this.getBlockStateOn();
                        if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                            for(int i = 0; i < 30; ++i) {
                                double d0 = this.getX() + (double)Mth.randomBetween(randomsource, -0.7F, 0.7F);
                                double d1 = this.getY();
                                double d2 = this.getZ() + (double)Mth.randomBetween(randomsource, -0.7F, 0.7F);
                                level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEventInit.LUNAR_MONSTROSITY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.LUNAR_MONSTROSITY_DEATH.get();
    }
}
