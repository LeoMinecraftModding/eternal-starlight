package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.living.goal.AstralGolemRandomStrollNearVillageGoal;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AstralGolem extends AbstractGolem implements NeutralMob {
    public AstralGolem(EntityType<? extends AbstractGolem> golem, Level level) {
        super(golem, level);
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
    protected static final EntityDataAccessor<String> MATERIAL = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.STRING);
    public ResourceLocation getMaterialId() {
        return ResourceLocation.parse(entityData.get(MATERIAL));
    }
    public void setMaterialId(ResourceLocation material) {
        entityData.set(MATERIAL, material.toString());
    }
    protected static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.BOOLEAN);
    public boolean isGolemBlocking() {
        return entityData.get(BLOCKING);
    }
    public void setBlocking(boolean blocking) {
        entityData.set(BLOCKING, blocking);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MATERIAL, "null")
                .define(BLOCKING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setMaterialId(ResourceLocation.read(compoundTag.getString("Material")).getOrThrow());
        homePos = new BlockPos(compoundTag.getInt("HomeX"), compoundTag.getInt("HomeY"), compoundTag.getInt("HomeZ"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("Material", getMaterialId().toString());
        compoundTag.putInt("HomeX", homePos.getX());
        compoundTag.putInt("HomeY", homePos.getY());
        compoundTag.putInt("HomeZ", homePos.getZ());
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0, true));
        goalSelector.addGoal(2, new AstralGolemRandomStrollNearVillageGoal(this));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 32.0F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (entity) -> entity instanceof Enemy && !(entity instanceof Creeper)));
        targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData data) {
        homePos = blockPosition();
        return super.finalizeSpawn(level, instance, spawnType, data);
    }

    public AstralGolemMaterial getMaterial() {
        return level().registryAccess().registryOrThrow(ESRegistries.ASTRAL_GOLEM_MATERIAL).get(getMaterialId());
    }

    public void setMaterial(AstralGolemMaterial material) {
        ResourceLocation key = level().registryAccess().registryOrThrow(ESRegistries.ASTRAL_GOLEM_MATERIAL).getKey(material);
        if (key != null) {
            setMaterialId(key);
        }
    }

    private boolean isValidRepairMaterial(ItemStack stack) {
        Item material = Items.IRON_INGOT;
        if (getMaterial() != null) material = getMaterial().material();
        return stack.is(material);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!isValidRepairMaterial(itemstack)) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(25.0F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(ESSoundEvents.ASTRAL_GOLEM_REPAIR.get(), 1.0F, f1);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) + 10);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

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
                setBlocking(ESPlatform.INSTANCE.isShield(getOffhandItem()) && distanceToSqr(target) >= reachSqr + 2);
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
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (getMaterial() == null ? 1 : getMaterial().attackDamageMultiplier());
    }
    
    public boolean doHurtTarget(Entity target) {
        if (isGolemBlocking()) {
            return false;
        }
        this.attackAnimationTick = 10;
        this.level().broadcastEntityEvent(this, (byte)4);
        float f = getAttackDamage();
        DamageSource damageSource = this.damageSources().mobAttack(this);
        Level var5 = this.level();
        if (var5 instanceof ServerLevel serverLevel) {
            f = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, damageSource, f);
        }

        boolean bl = target.hurt(damageSource, f);
        if (bl) {
            float g = this.getKnockback(target, damageSource);
            if (g > 0.0F && target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                livingEntity.knockback((double)(g * 0.5F), (double) Mth.sin(this.getYRot() * 0.017453292F), (double)(-Mth.cos(this.getYRot() * 0.017453292F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            Level var7 = this.level();
            if (var7 instanceof ServerLevel) {
                ServerLevel serverLevel2 = (ServerLevel)var7;
                EnchantmentHelper.doPostAttackEffects(serverLevel2, target, damageSource);
            }

            this.setLastHurtMob(target);
            this.playAttackSound();
        }

        return bl;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        float f = getMaterial() == null ? 1 : getMaterial().defenseMultiplier();
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

    public void handleEntityEvent(byte event) {
        if (event == 4) {
            this.attackAnimationTick = 10;
            this.playSound(ESSoundEvents.ASTRAL_GOLEM_ATTACK.get(), 1.0F, 1.0F);
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
        return isDamageSourceBlocked(source) ? SoundEvents.SHIELD_BLOCK : ESSoundEvents.ASTRAL_GOLEM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ESSoundEvents.ASTRAL_GOLEM_DEATH.get();
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
