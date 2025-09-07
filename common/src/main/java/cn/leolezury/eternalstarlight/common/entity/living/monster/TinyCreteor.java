package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.goal.MoveToTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class TinyCreteor extends Monster implements PowerableMob {
	private static final String TAG_IGNITED = "ignited";
	private static final String TAG_POWERED = "powered";
	private static final String TAG_SWELL = "swell";

	protected static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(TinyCreteor.class, EntityDataSerializers.BOOLEAN);

	public boolean isIgnited() {
		return this.getEntityData().get(IGNITED);
	}

	public void setIgnited(boolean ignited) {
		this.getEntityData().set(IGNITED, ignited);
	}

	protected static final EntityDataAccessor<Boolean> POWERED = SynchedEntityData.defineId(TinyCreteor.class, EntityDataSerializers.BOOLEAN);

	@Override
	public boolean isPowered() {
		return this.getEntityData().get(POWERED);
	}

	public void setPowered(boolean powered) {
		this.getEntityData().set(POWERED, powered);
	}

	protected static final EntityDataAccessor<Integer> SWELL = SynchedEntityData.defineId(TinyCreteor.class, EntityDataSerializers.INT);

	public int getSwell() {
		return this.getEntityData().get(SWELL);
	}

	public void setSwell(int swell) {
		this.getEntityData().set(SWELL, swell);
	}

	public int oldSwell;
	public float rollAngle, prevRollAngle;

	public TinyCreteor(EntityType<? extends TinyCreteor> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(IGNITED, false)
			.define(POWERED, false)
			.define(SWELL, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MoveToTargetGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new RandomFlyGoal(this));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.tinyCreteor.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.tinyCreteor.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.tinyCreteor.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.tinyCreteor.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0D)
			.add(Attributes.FLYING_SPEED, 1D);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
			@Override
			public boolean isStableDestination(BlockPos blockPos) {
				return this.level.getBlockState(blockPos).isAir();
			}
		};
		navigation.setCanOpenDoors(true);
		navigation.setCanFloat(true);
		navigation.setCanPassDoors(true);
		return navigation;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		oldSwell = getSwell();
		if (!level().isClientSide) {
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
				explode();
			}
			BlockHitResult result = level().clip(new ClipContext(position(), position().subtract(0, 2, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (onGround() || (result.getType() != HitResult.Type.MISS && result.getLocation().distanceTo(position()) < 0.5)) {
				addDeltaMovement(new Vec3(0, 0.01, 0));
			}
		} else {
			prevRollAngle = rollAngle;
			rollAngle += (getSwell() / 80f) * 30;
			if (getSwell() == 0) {
				rollAngle = Mth.approachDegrees(rollAngle, 0, 15);
			}
		}
	}

	public float getSwellProgress(float partialTick) {
		return Mth.lerp(partialTick, oldSwell, getSwell()) / 80f;
	}

	@Override
	public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
		super.thunderHit(serverLevel, lightningBolt);
		setPowered(true);
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
			this.dead = true;
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.isPowered() ? 3.0F : 2.0F, Level.ExplosionInteraction.NONE);
			if (level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 12; i++) {
					Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.AETHERSENT, getEyePosition().x + speed.x * 1.2, getEyePosition().y + speed.y * 1.2, getEyePosition().z + speed.z * 1.2, speed.x, speed.y, speed.z));
				}
			}
			playSound(ESSoundEvents.CRETEOR_EXPLODE.get());
			this.spawnLingeringCloud();
			this.triggerOnDeathMobEffects(RemovalReason.KILLED);
			this.discard();
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
		compoundTag.putBoolean(TAG_IGNITED, isIgnited());
		compoundTag.putBoolean(TAG_POWERED, isPowered());
		compoundTag.putInt(TAG_SWELL, getSwell());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setIgnited(compoundTag.getBoolean(TAG_IGNITED));
		setPowered(compoundTag.getBoolean(TAG_POWERED));
		setSwell(compoundTag.getInt(TAG_SWELL));
	}
}
