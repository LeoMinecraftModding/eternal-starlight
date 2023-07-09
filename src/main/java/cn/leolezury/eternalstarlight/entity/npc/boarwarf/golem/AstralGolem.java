package cn.leolezury.eternalstarlight.entity.npc.boarwarf.golem;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.entity.ai.goal.AstralGolemRandomStrollNearVillageGoal;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AstralGolem extends AbstractGolem implements NeutralMob {
    public AstralGolem(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
        super(p_27508_, p_27509_);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }
    
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    private int attackAnimationTick;
    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }
    public BlockPos homePos = BlockPos.ZERO;
    protected static final EntityDataAccessor<Integer> MATERIAL = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.INT);
    public int getMaterial() {
        return entityData.get(MATERIAL);
    }
    public void setMaterial(int material) {
        entityData.set(MATERIAL, material);
    }
    protected static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.BOOLEAN);
    public boolean isGolemBlocking() {
        return entityData.get(BLOCKING);
    }
    public void setBlocking(boolean blocking) {
        entityData.set(BLOCKING, blocking);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(MATERIAL, 0);
        entityData.define(BLOCKING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setMaterial(compoundTag.getInt("Material"));
        homePos = new BlockPos(compoundTag.getInt("HomeX"), compoundTag.getInt("HomeY"), compoundTag.getInt("HomeZ"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Material", getMaterial());
        compoundTag.putInt("HomeX", homePos.getX());
        compoundTag.putInt("HomeY", homePos.getY());
        compoundTag.putInt("HomeZ", homePos.getZ());
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new AstralGolemMeleeAttackGoal(this));
        goalSelector.addGoal(2, new AstralGolemRandomStrollNearVillageGoal(this));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 32.0F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (entity) -> entity instanceof Enemy && !(entity instanceof Creeper)));
        targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    static class AstralGolemMeleeAttackGoal extends MeleeAttackGoal {
        public AstralGolemMeleeAttackGoal(AstralGolem p_34123_) {
            super(p_34123_, 2.0D, true);
        }

        protected double getAttackReachSqr(LivingEntity entity) {
            return super.getAttackReachSqr(entity) * 3;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FLYING_SPEED, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData data, @org.jetbrains.annotations.Nullable CompoundTag tag) {
        homePos = blockPosition();

        setMaterial(getRandom().nextInt(AstralGolemMaterials.MATERIAL_NUM));

        return super.finalizeSpawn(level, instance, spawnType, data, tag);
    }

    private boolean isValidRepairMaterial(ItemStack stack) {
        switch (getMaterial()) {
            case AstralGolemMaterials.IRON -> {
                return stack.is(Items.IRON_INGOT);
            }
            case AstralGolemMaterials.SWAMP_SILVER -> {
                return stack.is(ItemInit.SWAMP_SILVER_INGOT.get());
            }
            case AstralGolemMaterials.ASTRAL_DIAMOND -> {
                return stack.is(ItemInit.SEEKING_EYE.get());
                // TODO: astral diamond material
            }
        }
        return false;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!isValidRepairMaterial(itemstack)) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(40.0F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEventInit.ASTRAL_GOLEM_REPAIR.get(), 1.0F, f1);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (!player.getAbilities().instabuild) {
                    Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) + 10);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isLeftHanded()) {
            setLeftHanded(false);
        }
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }
        if (!level().isClientSide()) {
            if (getTarget() == null) {
                setBlocking(false);
                setNoGravity(false);
                setDeltaMovement(getDeltaMovement().add(0, -getDeltaMovement().y / 3, 0));
            } else {
                LivingEntity target = getTarget();
                double reachSqr = 3 * (getBbWidth() * 2.0F * getBbWidth() * 2.0F + getTarget().getBbWidth());
                setBlocking(getOffhandItem().canPerformAction(ToolActions.SHIELD_BLOCK) && distanceToSqr(target) >= reachSqr + 2);
            }
            if (isGolemBlocking() && !isBlocking()) {
                startUsingItem(InteractionHand.OFF_HAND);
            }
            if (!isGolemBlocking() && isBlocking()) {
                stopUsingItem();
            }
        } else {
            level().addParticle(ParticleTypes.FLAME, getX() + (random.nextDouble() - 0.5) * 0.2, getY() + 0.5 + (random.nextDouble() - 0.5) * 0.2, getZ() + (random.nextDouble() - 0.5) * 0.2, 0, -0.2, 0);
        }
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }
    
    public boolean doHurtTarget(Entity target) {
        if (isGolemBlocking()) {
            return false;
        }
        this.attackAnimationTick = 10;
        this.level().broadcastEntityEvent(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        float f2 = 1;
        switch (getMaterial()) {
            case AstralGolemMaterials.IRON -> f2 = 1.2F;
            case AstralGolemMaterials.SWAMP_SILVER -> f2 = 1.5F;
            case AstralGolemMaterials.ASTRAL_DIAMOND -> f2 = 2.0F;
        }
        boolean flag = target.hurt(this.damageSources().mobAttack(this), f1 * f2);
        if (flag) {
            double d2;
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                d2 = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d2 = 0.0D;
            }

            double d0 = d2;
            double d1 = Math.max(0.0D, 1.0D - d0);
            target.setDeltaMovement(target.getDeltaMovement().add(0.0D, (double)0.4F * d1, 0.0D));
            this.doEnchantDamageEffects(this, target);
        }

        this.playSound(SoundEventInit.ASTRAL_GOLEM_ATTACK.get(), 1.0F, 1.0F);
        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        float f = 1;
        switch (getMaterial()) {
            case AstralGolemMaterials.IRON -> f = 1.0F;
            case AstralGolemMaterials.SWAMP_SILVER -> f = 1.5F;
            case AstralGolemMaterials.ASTRAL_DIAMOND -> f = 3.0F;
        }
        if (source.getEntity() instanceof Player player && !player.getAbilities().instabuild) {
            if (Boarwarf.getBoarwarfCredit(player) > -10000) {
                Boarwarf.setBoarwarfCredit(player, (int) (Boarwarf.getBoarwarfCredit(player) - amount));
            }
        }
        return super.hurt(source, amount / f);
    }

    @Override
    public void die(DamageSource source) {
        if (source.getEntity() instanceof Player player && !player.getAbilities().instabuild) {
            if (Boarwarf.getBoarwarfCredit(player) > -10000) {
                Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) - 10);
            }
        }
        super.die(source);
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    public void handleEntityEvent(byte event) {
        if (event == 4) {
            this.attackAnimationTick = 10;
            this.playSound(SoundEventInit.ASTRAL_GOLEM_ATTACK.get(), 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(event);
        }
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return isDamageSourceBlocked(source) ? SoundEvents.SHIELD_BLOCK : SoundEventInit.ASTRAL_GOLEM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.ASTRAL_GOLEM_DEATH.get();
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }
}
