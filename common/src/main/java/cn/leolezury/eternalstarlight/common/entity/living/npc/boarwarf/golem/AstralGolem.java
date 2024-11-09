package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.living.goal.AstralGolemRandomStrollNearVillageGoal;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class AstralGolem extends AbstractGolem implements NeutralMob {
	private static final String TAG_MATERIAL = "material";
	private static final String TAG_HOME_X = "home_x";
	private static final String TAG_HOME_Y = "home_y";
	private static final String TAG_HOME_Z = "home_z";

	public AstralGolem(EntityType<? extends AbstractGolem> golem, Level level) {
		super(golem, level);
		this.moveControl = new FlyingMoveControl(this, 20, true);
	}

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;
	private int oldAttackAnimationTick;
	private int attackAnimationTick;

	public float getAttackAnimationTick(float partialTicks) {
		return Mth.lerp(partialTicks, this.oldAttackAnimationTick, this.attackAnimationTick);
	}

	public BlockPos homePos = BlockPos.ZERO;
	protected static final EntityDataAccessor<String> MATERIAL = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.STRING);

	public ResourceLocation getMaterialId() {
		return ResourceLocation.parse(this.getEntityData().get(MATERIAL));
	}

	public void setMaterialId(ResourceLocation material) {
		this.getEntityData().set(MATERIAL, material.toString());
	}

	protected static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.BOOLEAN);

	public boolean isGolemBlocking() {
		return this.getEntityData().get(BLOCKING);
	}

	public void setBlocking(boolean blocking) {
		this.getEntityData().set(BLOCKING, blocking);
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
		setMaterialId(ResourceLocation.read(compoundTag.getString(TAG_MATERIAL)).getOrThrow());
		homePos = new BlockPos(compoundTag.getInt(TAG_HOME_X), compoundTag.getInt(TAG_HOME_Y), compoundTag.getInt(TAG_HOME_Z));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString(TAG_MATERIAL, getMaterialId().toString());
		compoundTag.putInt(TAG_HOME_X, homePos.getX());
		compoundTag.putInt(TAG_HOME_Y, homePos.getY());
		compoundTag.putInt(TAG_HOME_Z, homePos.getZ());
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
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (entity, serverLevel) -> entity instanceof Enemy && !(entity instanceof Creeper)));
		targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.astralGolem.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.astralGolem.armor())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.astralGolem.followRange())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.astralGolem.attackDamage())
			.add(Attributes.ATTACK_KNOCKBACK, 3.0D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
			.add(Attributes.FLYING_SPEED, 1.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.3D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, EntitySpawnReason spawnReason, @Nullable SpawnGroupData data) {
		homePos = blockPosition();
		return super.finalizeSpawn(level, instance, spawnReason, data);
	}

	public Optional<Holder.Reference<AstralGolemMaterial>> getMaterial() {
		return level().registryAccess().lookupOrThrow(ESRegistries.ASTRAL_GOLEM_MATERIAL).get(getMaterialId());
	}

	public void setMaterial(AstralGolemMaterial material) {
		ResourceLocation key = level().registryAccess().lookupOrThrow(ESRegistries.ASTRAL_GOLEM_MATERIAL).getKey(material);
		if (key != null) {
			setMaterialId(key);
		}
	}

	private boolean isValidRepairMaterial(ItemStack stack) {
		Item material = Items.IRON_INGOT;
		Optional<Holder.Reference<AstralGolemMaterial>> ref = getMaterial();
		if (ref.isPresent() && ref.get().isBound()) material = ref.get().value().material();
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
				if (!player.hasInfiniteMaterials()) {
					itemstack.shrink(1);
					Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) + 10);
				}
				return InteractionResult.SUCCESS;
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
		this.oldAttackAnimationTick = attackAnimationTick;
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
		Optional<Holder.Reference<AstralGolemMaterial>> ref = getMaterial();
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (ref.isPresent() && ref.get().isBound() ? ref.get().value().attackDamageMultiplier() : 1);
	}

	@Override
	public boolean doHurtTarget(ServerLevel serverLevel, Entity target) {
		if (isGolemBlocking()) {
			return false;
		}
		this.attackAnimationTick = 10;
		serverLevel.broadcastEntityEvent(this, (byte)4);
		float f = this.getAttackDamage();
		float g = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
		DamageSource damageSource = this.damageSources().mobAttack(this);
		boolean bl = target.hurtServer(serverLevel, damageSource, g);
		if (bl) {
			double d = target instanceof LivingEntity livingEntity ? livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 0.0;
			double e = Math.max(0.0, 1.0 - d);
			target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.4F * e, 0.0));
			EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
		}

		this.playSound(ESSoundEvents.ASTRAL_GOLEM_ATTACK.get(), 1.0F, 1.0F);
		return bl;
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		Optional<Holder.Reference<AstralGolemMaterial>> ref = getMaterial();
		float f = ref.isPresent() && ref.get().isBound() ? ref.get().value().defenseMultiplier() : 1;
		if (source.getEntity() instanceof Player player && !player.hasInfiniteMaterials()) {
			if (Boarwarf.getBoarwarfCredit(player) > -10000) {
				Boarwarf.setBoarwarfCredit(player, (int) (Boarwarf.getBoarwarfCredit(player) - amount));
			}
		}
		return super.hurtServer(serverLevel, source, amount / f);
	}

	@Override
	public void die(DamageSource source) {
		if (source.getEntity() instanceof Player player && !player.hasInfiniteMaterials()) {
			if (Boarwarf.getBoarwarfCredit(player) > -10000) {
				Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) - 10);
			}
		}
		super.die(source);
	}

	@Override
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

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.remainingPersistentAngerTime = time;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		this.persistentAngerTarget = target;
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public static boolean checkAstralGolemSpawnRules(EntityType<? extends AstralGolem> type, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		return checkMobSpawnRules(type, level, spawnReason, pos, random) && ESConfig.INSTANCE.mobsConfig.astralGolem.canSpawn();
	}
}
