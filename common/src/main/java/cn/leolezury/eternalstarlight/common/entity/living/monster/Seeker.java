package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESSeekerVariants;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Seeker extends Monster implements VariantHolder<Holder<SeekerVariant>> {
	private static final String TAG_VARIANT = "variant";
	public static final float TENTACLE_LENGTH = 5;
	private static final byte EVENT_MOVE = 100;
	private static final byte EVENT_ATTACK = 101;

	protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.STRING);

	public ResourceLocation getVariantId() {
		return ResourceLocation.parse(this.getEntityData().get(VARIANT));
	}

	public void setVariantId(ResourceLocation variant) {
		this.getEntityData().set(VARIANT, variant.toString());
	}

	@Override
	public void setVariant(Holder<SeekerVariant> variant) {
		if (variant.isBound()) {
			ResourceLocation key = level().registryAccess().registryOrThrow(ESRegistries.SEEKER_VARIANT).getKey(variant.value());
			if (key != null) {
				setVariantId(key);
			}
		}
	}

	@Override
	public Holder<SeekerVariant> getVariant() {
		ResourceLocation key = getVariantId();
		Registry<SeekerVariant> variants = level().registryAccess().registryOrThrow(ESRegistries.SEEKER_VARIANT);
		Optional<Holder.Reference<SeekerVariant>> optional = variants.getHolder(key);
		return optional.orElse(variants.getHolder(ESSeekerVariants.LUNAR).orElseThrow());
	}

	protected static final EntityDataAccessor<Float> SEEKER_Y_ROT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.FLOAT);

	public float getSeekerYRot() {
		return this.getEntityData().get(SEEKER_Y_ROT);
	}

	public void setSeekerYRot(float yRot) {
		this.getEntityData().set(SEEKER_Y_ROT, yRot);
	}

	protected static final EntityDataAccessor<Float> SEEKER_X_ROT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.FLOAT);

	public float getSeekerXRot() {
		return this.getEntityData().get(SEEKER_X_ROT);
	}

	public void setSeekerXRot(float xRot) {
		this.getEntityData().set(SEEKER_X_ROT, xRot);
	}

	private float oldSeekerXRot, seekerXRot, oldSeekerYRot, seekerYRot;
	public AnimationState moveAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();

	private Vec3 nextMovement = Vec3.ZERO;
	private int moveTicks, moveCooldown, attackTicks, attackCooldown;
	private float attackXRot, attackYRot;

	public Seeker(EntityType<? extends Seeker> entityType, Level level) {
		super(entityType, level);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, ESSeekerVariants.LUNAR.location().toString())
			.define(SEEKER_X_ROT, 0f)
			.define(SEEKER_Y_ROT, 0f);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.seeker.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.seeker.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.seeker.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.seeker.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0)
			.add(Attributes.FLYING_SPEED, 1);
	}

	public float getSeekerXRot(float f) {
		return Mth.rotLerp(f, oldSeekerXRot, seekerXRot);
	}

	public float getSeekerYRot(float f) {
		return Mth.rotLerp(f, oldSeekerYRot, seekerYRot);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
		if (!level().isClientSide) {
			if (isAlive() && !isNoAi()) {
				LivingEntity target = getTarget();
				if (target != null && !target.isAlive()) {
					setTarget(null);
					target = null;
				}
				if (moveTicks > 0) {
					moveTicks--;
				}
				if (moveCooldown > 0) {
					moveCooldown--;
				}
				if (attackTicks > 0) {
					attackTicks--;
				}
				if (attackCooldown > 0) {
					attackCooldown--;
				}
				if (moveTicks <= 0 && attackTicks <= 0 && moveCooldown <= 0) {
					level().broadcastEntityEvent(this, EVENT_MOVE);
					moveTicks = 20;
					moveCooldown = target == null ? 50 : 30;
					BlockHitResult heightResult = level().clip(new ClipContext(position(), position().subtract(0, 10, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, this));
					boolean tooHigh = heightResult.getType() == HitResult.Type.MISS;
					for (int i = 0; i < 32; i++) {
						if (target != null && i == 0) {
							Vec3 targetPos = target.position().add(0, target.getBbHeight() + 1, 0);
							Vec3 selfPos = position().add(0, getBbHeight() / 2f, 0);
							Vec3 wanted = ESMathUtil.rotationToPosition(targetPos, TENTACLE_LENGTH * 0.6f, (getRandom().nextFloat() - 0.5F) * 30, ESMathUtil.positionToYaw(targetPos, selfPos) + 15);
							nextMovement = wanted.subtract(position()).normalize();
						} else {
							nextMovement = new Vec3(getRandom().nextFloat() - 0.5F, tooHigh ? -Math.abs(getRandom().nextFloat() - 0.5F) : getRandom().nextFloat() - 0.5F, getRandom().nextFloat() - 0.5F).normalize();
						}
						BlockHitResult result = level().clip(new ClipContext(position(), position().add(nextMovement.scale(getBoundingBox().getSize() + 3)), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, this));
						if (result.getType() == HitResult.Type.MISS
							&& ((target != null && i == 0) || (Mth.degreesDifferenceAbs(getSeekerXRot(), ESMathUtil.positionToPitch(nextMovement)) <= 90
							&& Mth.degreesDifferenceAbs(getSeekerYRot(), ESMathUtil.positionToYaw(nextMovement)) <= 90))) {
							break;
						}
					}
					setSeekerXRot(ESMathUtil.positionToPitch(nextMovement));
					setSeekerYRot(ESMathUtil.positionToYaw(nextMovement));
					setYRot(getSeekerYRot() - 90);
				}
				if (moveTicks == 15 && level() instanceof ServerLevel serverLevel) {
					this.hurtMarked = true;
					setDeltaMovement(nextMovement.scale(getAttributeValue(Attributes.FLYING_SPEED) * (target == null ? 0.2 : 0.4)));
					for (int i = 0; i < 64; i++) {
						Vec3 particleMovement = nextMovement.add((getRandom().nextDouble() - 0.5) * 0.5, (getRandom().nextDouble() - 0.5) * 0.5, (getRandom().nextDouble() - 0.5) * 0.5).normalize();
						double particleSpeed = 0.1 + getRandom().nextDouble() * 0.4;
						ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, this, new ParticlePacket(ColorParticleOption.create(ESParticles.COLORED_INK.get(), getVariant().value().particleColor()), getX() - nextMovement.x() * 0.5, getY() + getBbHeight() / 2 - nextMovement.y() * 0.5, getZ() - nextMovement.z() * 0.5, -particleMovement.x() * particleSpeed, -particleMovement.y() * particleSpeed, -particleMovement.z() * particleSpeed));
					}
				}
				if (target != null) {
					Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2f, 0);
					Vec3 selfPos = position().add(0, getBbHeight() / 2f, 0);
					if (attackTicks <= 0) {
						attackXRot = ESMathUtil.positionToPitch(selfPos, targetPos);
						attackYRot = ESMathUtil.positionToYaw(selfPos, targetPos);
					}
					AABB aabb = target.getBoundingBox().inflate(target.getPickRadius() + 0.5f);
					Vec3 endPos = ESMathUtil.rotationToPosition(selfPos, TENTACLE_LENGTH, attackXRot, attackYRot);
					boolean canReachTarget = (aabb.contains(selfPos) || aabb.clip(selfPos, endPos).isPresent()) && hasLineOfSight(target);
					if (moveTicks <= 0 && attackTicks <= 0 && attackCooldown <= 0 && canReachTarget) {
						attackTicks = 30;
						attackCooldown = 50;
						setDeltaMovement(Vec3.ZERO);
						level().broadcastEntityEvent(this, EVENT_ATTACK);
					}
					if (attackTicks > 10) {
						setSeekerXRot(Mth.rotLerp(0.2f, getSeekerXRot(), -attackXRot));
						setSeekerYRot(Mth.rotLerp(0.2f, getSeekerYRot(), attackYRot + 180));
						setYRot(getSeekerYRot() - 90);
					}
					if (attackTicks == 10) {
						setSeekerXRot(-attackXRot);
						setSeekerYRot(attackYRot + 180);
						setYRot(getSeekerYRot() - 90);
						if (canReachTarget) {
							doHurtTarget(target);
						}
					}
				}
			}
		} else {
			oldSeekerXRot = seekerXRot;
			oldSeekerYRot = seekerYRot;
			seekerXRot = getSeekerXRot();
			seekerYRot = getSeekerYRot();
		}
	}

	@Override
	public void handleEntityEvent(byte b) {
		if (b == EVENT_MOVE) {
			moveAnimationState.start(tickCount);
		} else if (b == EVENT_ATTACK) {
			attackAnimationState.start(tickCount);
		} else {
			super.handleEntityEvent(b);
		}
	}

	@Override
	public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
		setVariant(SeekerVariant.getSpawnVariant(level.registryAccess(), level.getBiome(blockPosition())));
		return super.finalizeSpawn(level, instance, spawnType, data);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ESSoundEvents.SEEKER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.SEEKER_DEATH.get();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setVariantId(ResourceLocation.read(compoundTag.getString(TAG_VARIANT)).getOrThrow());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString(TAG_VARIANT, getVariantId().toString());
	}

	public static boolean checkSeekerSpawnRules(EntityType<? extends Seeker> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.seeker.canSpawn();
	}
}
