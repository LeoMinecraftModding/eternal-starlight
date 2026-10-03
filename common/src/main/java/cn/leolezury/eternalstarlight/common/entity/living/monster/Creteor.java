package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.EnumSet;

public class Creteor extends Monster implements PowerableMob {
	private static final String TAG_ACTIVATED = "activated";
	private static final String TAG_IGNITED = "ignited";
	private static final String TAG_POWERED = "powered";
	private static final String TAG_SWELL = "swell";
	private static final String TAG_SPIN_TICKS = "spin_ticks";

	public AnimationState idleAnimationState = new AnimationState();

	protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.BOOLEAN);

	public boolean isActivated() {
		return this.getEntityData().get(ACTIVATED);
	}

	public void setActivated(boolean activated) {
		this.getEntityData().set(ACTIVATED, activated);
	}

	protected static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.BOOLEAN);

	public boolean isIgnited() {
		return this.getEntityData().get(IGNITED);
	}

	public void setIgnited(boolean ignited) {
		this.getEntityData().set(IGNITED, ignited);
	}

	protected static final EntityDataAccessor<Boolean> POWERED = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.BOOLEAN);

	@Override
	public boolean isPowered() {
		return this.getEntityData().get(POWERED);
	}

	public void setPowered(boolean powered) {
		this.getEntityData().set(POWERED, powered);
	}

	protected static final EntityDataAccessor<Integer> SWELL = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.INT);

	public int getSwell() {
		return this.getEntityData().get(SWELL);
	}

	public void setSwell(int swell) {
		this.getEntityData().set(SWELL, swell);
	}

	public int oldSwell;
	public int spinTicks;
	public float rollAngle, prevRollAngle;
	public float spin, prevSpin;

	public Creteor(EntityType<? extends Creteor> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ACTIVATED, false)
			.define(IGNITED, false)
			.define(POWERED, false)
			.define(SWELL, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new CreteorDoNothingGoal());
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true) {
			@Override
			public boolean canUse() {
				return super.canUse() && Creteor.this.isActivated();
			}
		});
	}

	class CreteorDoNothingGoal extends Goal {
		public CreteorDoNothingGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return !Creteor.this.isActivated();
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			Creteor.this.stopInPlace();
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.creteor.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.creteor.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.creteor.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.creteor.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.3);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		oldSwell = getSwell();
		if (getSwell() < 80) {
			spinTicks = 0;
		} else {
			spinTicks++;
		}
		if (!level().isClientSide) {
			if ((getTarget() != null || isIgnited()) && !isActivated()) {
				setActivated(true);
			}
			if (getSwell() < 80) {
				LivingEntity target = getTarget();
				if (isIgnited()) {
					setSwell(getSwell() + 1);
				} else if (target == null) {
					setSwell(getSwell() - 1);
				} else if (distanceToSqr(target) > (double) 25.0F) {
					setSwell(getSwell() - 1);
				} else if (!getSensing().hasLineOfSight(target)) {
					setSwell(getSwell() - 1);
				} else {
					setSwell(getSwell() + 1);
				}
				setSwell(Mth.clamp(getSwell(), 0, 80));
			} else {
				setNoGravity(true);
				hurtMarked = true;
				if (spinTicks <= 40) {
					setDeltaMovement(new Vec3(0, 0.1, 0));
				} else {
					setDeltaMovement(Vec3.ZERO);
				}
				if (spinTicks == 1) {
					playSound(ESSoundEvents.CRETEOR_CHARGE.get());
				}
				if (spinTicks > 80) {
					explode();
				}
			}
		} else {
			idleAnimationState.startIfStopped(tickCount);
			prevRollAngle = rollAngle;
			rollAngle += (float) (position().subtract(new Vec3(xOld, yOld, zOld)).length() / (3f / 260f));
			rollAngle = Mth.wrapDegrees(rollAngle);
			if (!isActivated()) {
				rollAngle = 0;
			}
			prevSpin = spin;
			if (getSwell() >= 80) {
				spin += 90;
			}
		}
	}

	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(isActivated() ? vec3 : new Vec3(0, vec3.y, 0));
	}

	public float getSwellProgress(float partialTick) {
		if (getSwell() < 80) {
			return Mth.lerp(partialTick, oldSwell, getSwell()) / 160f;
		} else {
			return Math.min(spinTicks + 80 + partialTick, 160) / 160f;
		}
	}

	@Override
	public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
		super.thunderHit(serverLevel, lightningBolt);
		setPowered(true);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!isActivated()) {
			setActivated(true);
		}
		return super.hurt(source, amount);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(ItemTags.CREEPER_IGNITERS)) {
			SoundEvent soundEvent = itemStack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
			this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
			if (!this.level().isClientSide) {
				setIgnited(true);
				if (!itemStack.isDamageableItem()) {
					itemStack.shrink(1);
				} else {
					itemStack.hurtAndBreak(1, player, getSlotForHand(interactionHand));
				}
			}

			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else {
			return super.mobInteract(player, interactionHand);
		}
	}

	private void explode() {
		if (!this.level().isClientSide) {
			float f = this.isPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3 * f, Level.ExplosionInteraction.MOB);
			if (level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 25; i++) {
					Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.AETHERSENT, getEyePosition().x + speed.x * 1.2, getEyePosition().y + speed.y * 1.2, getEyePosition().z + speed.z * 1.2, speed.x, speed.y, speed.z));
				}
			}
			playSound(ESSoundEvents.CRETEOR_EXPLODE.get());
			this.spawnLingeringCloud();
			this.triggerOnDeathMobEffects(RemovalReason.KILLED);
			this.discard();
			if (ESConfig.INSTANCE.mobsConfig.tinyCreteor.canSpawn()) {
				int splitCount = getRandom().nextInt(2, 5);
				for (int i = 0; i < splitCount; i++) {
					TinyCreteor tinyCreteor = new TinyCreteor(ESEntities.TINY_CRETEOR.get(), level());
					tinyCreteor.setPos(position().add(0, getBbHeight() / 2, 0));
					tinyCreteor.setPowered(isPowered());
					tinyCreteor.setTarget(getTarget());
					level().addFreshEntity(tinyCreteor);
					tinyCreteor.hurtMarked = true;
					tinyCreteor.setDeltaMovement(new Vec3((getRandom().nextFloat() - getRandom().nextFloat()) * 0.1F, getRandom().nextFloat() * 0.05F, (getRandom().nextFloat() - getRandom().nextFloat()) * 0.1F));
				}
			}
		}
	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
			areaEffectCloud.setRadius(2.5F);
			areaEffectCloud.setRadiusOnUse(-0.5F);
			areaEffectCloud.setWaitTime(10);
			areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
			areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());

			for (MobEffectInstance mobEffectInstance : collection) {
				areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));
			}

			this.level().addFreshEntity(areaEffectCloud);
		}
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		super.playStepSound(blockPos, blockState);
		playSound(ESSoundEvents.CRETEOR_ROLL.get());
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return ESSoundEvents.CRETEOR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.CRETEOR_DEATH.get();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_ACTIVATED, isActivated());
		compoundTag.putBoolean(TAG_IGNITED, isIgnited());
		compoundTag.putBoolean(TAG_POWERED, isPowered());
		compoundTag.putInt(TAG_SWELL, getSwell());
		compoundTag.putInt(TAG_SPIN_TICKS, spinTicks);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setActivated(compoundTag.getBoolean(TAG_ACTIVATED));
		setIgnited(compoundTag.getBoolean(TAG_IGNITED));
		setPowered(compoundTag.getBoolean(TAG_POWERED));
		setSwell(compoundTag.getInt(TAG_SWELL));
		spinTicks = compoundTag.getInt(TAG_SPIN_TICKS);
	}

	public static boolean checkCreteorSpawnRules(EntityType<? extends Creteor> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.creteor.canSpawn();
	}
}
