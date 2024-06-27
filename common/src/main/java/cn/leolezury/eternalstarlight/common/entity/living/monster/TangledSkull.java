package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.EnumSet;

public class TangledSkull extends Monster {
	public int skullDeathTime;

	protected static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);

	public boolean isCharging() {
		return entityData.get(CHARGING);
	}

	public void setCharging(boolean charging) {
		entityData.set(CHARGING, charging);
	}

	protected static final EntityDataAccessor<Boolean> SHOT = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);

	public boolean isShot() {
		return entityData.get(SHOT);
	}

	public void setShot(boolean shot) {
		entityData.set(SHOT, shot);
	}

	protected static final EntityDataAccessor<Boolean> SHOT_FROM_MONSTROSITY = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);

	public boolean isShotFromMonstrosity() {
		return entityData.get(SHOT_FROM_MONSTROSITY);
	}

	public void setShotFromMonstrosity(boolean shotFromMonstrosity) {
		entityData.set(SHOT_FROM_MONSTROSITY, shotFromMonstrosity);
	}

	protected static final EntityDataAccessor<Vector3f> SHOT_MOVEMENT = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.VECTOR3);

	public Vec3 getShotMovement() {
		return new Vec3(entityData.get(SHOT_MOVEMENT));
	}

	public void setShotMovement(Vec3 shotMovement) {
		entityData.set(SHOT_MOVEMENT, shotMovement.toVector3f());
	}

	public TangledSkull(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new TangledSkullMoveControl(this);
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new TangledSkullRotationControl(this);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new TangledSkullChargeAttackGoal());
		this.goalSelector.addGoal(2, new TangledSkullRandomMoveGoal());
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	private class TangledSkullChargeAttackGoal extends Goal {
		public TangledSkullChargeAttackGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean canUse() {
			LivingEntity livingEntity = TangledSkull.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive() && !TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.random.nextInt(reducedTickDelay(7)) == 0) {
				return TangledSkull.this.distanceToSqr(livingEntity) > 4.0;
			} else {
				return false;
			}
		}

		public boolean canContinueToUse() {
			return TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.isCharging() && TangledSkull.this.getTarget() != null && TangledSkull.this.getTarget().isAlive();
		}

		public void start() {
			LivingEntity livingEntity = TangledSkull.this.getTarget();
			if (livingEntity != null) {
				Vec3 vec3 = livingEntity.getEyePosition();
				TangledSkull.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
			}

			TangledSkull.this.setCharging(true);
		}

		public void stop() {
			TangledSkull.this.setCharging(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingEntity = TangledSkull.this.getTarget();
			if (livingEntity != null) {
				if (TangledSkull.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
					TangledSkull.this.doHurtTarget(livingEntity);
					TangledSkull.this.setCharging(false);
				} else {
					double d = TangledSkull.this.distanceToSqr(livingEntity);
					if (d < 9.0) {
						Vec3 vec3 = livingEntity.getEyePosition();
						TangledSkull.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
					}
				}

			}
		}
	}

	private class TangledSkullRandomMoveGoal extends Goal {
		public TangledSkullRandomMoveGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean canUse() {
			return !TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.random.nextInt(reducedTickDelay(7)) == 0;
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void tick() {
			BlockPos blockPos = TangledSkull.this.blockPosition();

			for (int i = 0; i < 3; ++i) {
				BlockPos blockPos2 = blockPos.offset(TangledSkull.this.random.nextInt(15) - 7, TangledSkull.this.random.nextInt(11) - 5, TangledSkull.this.random.nextInt(15) - 7);
				if (TangledSkull.this.level().isEmptyBlock(blockPos2)) {
					TangledSkull.this.moveControl.setWantedPosition((double) blockPos2.getX() + 0.5, (double) blockPos2.getY() + 0.5, (double) blockPos2.getZ() + 0.5, 0.25);
					if (TangledSkull.this.getTarget() == null) {
						TangledSkull.this.getLookControl().setLookAt((double) blockPos2.getX() + 0.5, (double) blockPos2.getY() + 0.5, (double) blockPos2.getZ() + 0.5, 180.0F, 20.0F);
					}
					break;
				}
			}

		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, 10.0)
			.add(Attributes.ATTACK_DAMAGE, 3.0)
			.add(Attributes.FOLLOW_RANGE, 64.0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(CHARGING, false)
			.define(SHOT, false)
			.define(SHOT_FROM_MONSTROSITY, false)
			.define(SHOT_MOVEMENT, Vec3.ZERO.toVector3f());
	}

	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if (!level().isClientSide) {
			if (isShotFromMonstrosity() && tickCount == 30) {
				playSound(ESSoundEvents.TANGLED_SKULL_MOAN.get(), 5, 1);
			}
			if (isShot()) {
				setDeltaMovement(getShotMovement());
				HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, entity -> true);
				if (isShotFromMonstrosity() && result.getType() == HitResult.Type.ENTITY && ((EntityHitResult) result).getEntity() instanceof LivingEntity living && !living.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS)) {
					living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60));
					living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
					living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
					living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60));
				}
				boolean ally = isShotFromMonstrosity() && result.getType() == HitResult.Type.ENTITY && ((EntityHitResult) result).getEntity().getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS);
				if (result.getType() != HitResult.Type.MISS && !ally) {
					this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, Level.ExplosionInteraction.NONE);
					if (isShotFromMonstrosity() && level() instanceof ServerLevel serverLevel) {
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.SOUL, getX(), getY(), getZ(), 0, 0.2, 0));
					}
					discard();
				}
			}
		} else {
			level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(0.5), getZ(), 0, 0, 0);
		}
	}

	@Override
	protected void tickDeath() {
		setDeltaMovement(Vec3.ZERO);
		++this.skullDeathTime;
		if (this.skullDeathTime >= 80 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, Level.ExplosionInteraction.NONE);
			this.level().broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		if (!this.level().isClientSide() && source.getEntity() instanceof TangledSkull killer && killer.getKillCredit() instanceof ServerPlayer player) {
			ESCriteriaTriggers.CHAIN_TANGLED_SKULL_EXPLOSION.get().trigger(player);
		}
	}

	@Nullable
	@Override
	public LivingEntity getTarget() {
		return isShot() ? null : super.getTarget();
	}

	@Override
	public void setDeltaMovement(Vec3 vec3) {
		if (isShot()) {
			super.setDeltaMovement(isShotFromMonstrosity() && tickCount < 30 ? Vec3.ZERO : getShotMovement());
		} else {
			super.setDeltaMovement(vec3);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (isShotFromMonstrosity() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return false;
		}
		return super.hurt(source, amount);
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("Shot", isShot());
		compoundTag.putBoolean("ShotFromMonstrosity", isShotFromMonstrosity());
		if (isShot()) {
			compoundTag.putDouble("ShotMovementX", getShotMovement().x);
			compoundTag.putDouble("ShotMovementY", getShotMovement().y);
			compoundTag.putDouble("ShotMovementZ", getShotMovement().z);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("Shot", CompoundTag.TAG_BYTE)) {
			setShot(compoundTag.getBoolean("Shot"));
		}
		if (compoundTag.contains("ShotFromMonstrosity", CompoundTag.TAG_BYTE)) {
			setShotFromMonstrosity(compoundTag.getBoolean("ShotFromMonstrosity"));
		}
		if (compoundTag.contains("ShotMovementX", CompoundTag.TAG_DOUBLE) && compoundTag.contains("ShotMovementY", CompoundTag.TAG_DOUBLE) && compoundTag.contains("ShotMovementZ", CompoundTag.TAG_DOUBLE)) {
			setShotMovement(new Vec3(compoundTag.getDouble("ShotMovementX"), compoundTag.getDouble("ShotMovementY"), compoundTag.getDouble("ShotMovementZ")));
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}

	private class TangledSkullMoveControl extends MoveControl {
		public TangledSkullMoveControl(final TangledSkull skull) {
			super(skull);
		}

		public void tick() {
			if (this.operation == Operation.MOVE_TO) {
				Vec3 vec3 = new Vec3(this.wantedX - TangledSkull.this.getX(), this.wantedY - TangledSkull.this.getY(), this.wantedZ - TangledSkull.this.getZ());
				double d = vec3.length();
				if (d < TangledSkull.this.getBoundingBox().getSize()) {
					this.operation = Operation.WAIT;
					TangledSkull.this.setDeltaMovement(TangledSkull.this.getDeltaMovement().scale(0.5));
				} else {
					TangledSkull.this.setDeltaMovement(TangledSkull.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d)));
					if (TangledSkull.this.getTarget() == null) {
						Vec3 vec32 = TangledSkull.this.getDeltaMovement();
						TangledSkull.this.setYRot(-((float) Mth.atan2(vec32.x, vec32.z)) * 57.295776F);
					} else {
						double e = TangledSkull.this.getTarget().getX() - TangledSkull.this.getX();
						double f = TangledSkull.this.getTarget().getZ() - TangledSkull.this.getZ();
						TangledSkull.this.setYRot(-((float) Mth.atan2(e, f)) * 57.295776F);
					}
					TangledSkull.this.yBodyRot = TangledSkull.this.getYRot();
				}
			}
		}
	}

	private class TangledSkullRotationControl extends BodyRotationControl {
		public TangledSkullRotationControl(final TangledSkull skull) {
			super(skull);
		}

		@Override
		public void clientTick() {
			if (TangledSkull.this.isShot()) {
				Vec3 movement = TangledSkull.this.getShotMovement();
				float pitch = ESMathUtil.positionToPitch(movement);
				float yaw = ESMathUtil.positionToYaw(movement);
				TangledSkull.this.setYRot(yaw - 90);
				TangledSkull.this.yHeadRot = yaw - 90;
				TangledSkull.this.yBodyRot = yaw - 90;
				TangledSkull.this.setXRot(-pitch);
			} else {
				super.clientTick();
			}
		}
	}
}
