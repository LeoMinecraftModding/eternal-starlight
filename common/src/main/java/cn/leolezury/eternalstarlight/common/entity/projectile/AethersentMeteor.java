package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AethersentMeteor extends AbstractHurtingProjectile implements TrailOwner {
	private static final String TAG_SIZE = "size";
	private static final String TAG_TARGET = "target";
	private static final String TAG_TARGET_X = "target_x";
	private static final String TAG_TARGET_Y = "target_y";
	private static final String TAG_TARGET_Z = "target_z";
	private static final String TAG_NATURAL = "natural";

	protected static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(AethersentMeteor.class, EntityDataSerializers.INT);

	public int getSize() {
		return this.getEntityData().get(SIZE);
	}

	public void setSize(int size) {
		this.getEntityData().set(SIZE, size);
	}

	public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>();
	public float lastTrailTick = 0;
	public float oXSpin, xSpin, oYSpin, ySpin;

	@Nullable
	private LivingEntity target;
	@Nullable
	private UUID targetId;

	public LivingEntity getTarget() {
		return target;
	}

	public void setTarget(LivingEntity target) {
		this.target = target;
		if (target != null) {
			this.targetId = target.getUUID();
		}
	}

	private Vec3 targetPos = null;

	public void setTargetPos(Vec3 targetPos) {
		this.targetPos = targetPos;
	}

	private boolean natural = true;

	public boolean isNatural() {
		return natural;
	}

	public AethersentMeteor(EntityType<? extends AethersentMeteor> type, Level level) {
		super(type, level);
		this.noCulling = true;
	}

	public AethersentMeteor(Level level, LivingEntity entity, double x, double y, double z) {
		this(ESEntities.AETHERSENT_METEOR.get(), level);
		xo = x;
		yo = y;
		zo = z;
		setPos(x, y, z);
		setOwner(entity);
	}

	public static void createMeteorShower(Level level, LivingEntity entity, LivingEntity target, double targetX, double targetY, double targetZ, double height, int cooldown) {
		if (!level.isClientSide) {
			if (ESDataAttachments.METEOR_COOLDOWN.getData(entity) > 0) {
				return;
			}
			ESDataAttachments.METEOR_COOLDOWN.setData(entity, cooldown);
			level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ESSoundEvents.AETHERSENT_METEOR_WHOOSH.get(), entity.getSoundSource(), 1, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1.0F);
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					RandomSource random = entity.getRandom();
					AethersentMeteor meteor = new AethersentMeteor(level, entity, targetX + x + (random.nextFloat() - 0.5) * 3, targetY + height + (random.nextFloat() - 0.5) * 5, targetZ + z + (random.nextFloat() - 0.5) * 3);
					meteor.setSize(random.nextInt(2, 5));
					meteor.setTarget(target);
					meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
					meteor.natural = false;
					level.addFreshEntity(meteor);
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ESExplosionParticleOptions.AETHERSENT, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
					}
				}
			}
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SIZE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		setSize(compoundTag.getInt(TAG_SIZE));
		if (compoundTag.hasUUID(TAG_TARGET)) {
			targetId = compoundTag.getUUID(TAG_TARGET);
		}
		targetPos = new Vec3(compoundTag.getDouble(TAG_TARGET_X), compoundTag.getDouble(TAG_TARGET_Y), compoundTag.getDouble(TAG_TARGET_Z));
		if (compoundTag.contains(TAG_NATURAL, CompoundTag.TAG_BYTE)) {
			natural = compoundTag.getBoolean(TAG_NATURAL);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putInt(TAG_SIZE, getSize());
		if (target != null) {
			compoundTag.putUUID(TAG_TARGET, target.getUUID());
		}
		if (targetPos != null) {
			compoundTag.putDouble(TAG_TARGET_X, targetPos.x);
			compoundTag.putDouble(TAG_TARGET_Y, targetPos.y);
			compoundTag.putDouble(TAG_TARGET_Z, targetPos.z);
		}
		compoundTag.putBoolean(TAG_NATURAL, natural);
	}

	public void dropAndDiscard(boolean clean) {
		if (!isRemoved() && !level().isClientSide) {
			if (natural && getSize() >= 10) {
				ItemEntity entity = spawnAtLocation(new ItemStack(ESItems.RAW_AETHERSENT.get(), random.nextInt(20, 30)));
				if (entity != null) {
					entity.setGlowingTag(true);
				}
				if (!clean) {
					if (ESConfig.INSTANCE.aethersentMeteorReplaceBlocks) {
						for (int x = -3; x <= 3; x++) {
							for (int y = -3; y <= 3; y++) {
								for (int z = -3; z <= 3; z++) {
									BlockPos pos = blockPosition().offset(x, y, z);
									boolean canDestroy = ESPlatform.INSTANCE.postEntityDestroyBlockEvent(this.level(), pos, this);
									if (canDestroy && pos.distToCenterSqr(blockPosition().getCenter()) <= 3.5 && level().getBlockState(pos).is(ESTags.Blocks.AETHERSENT_METEOR_REPLACEABLES)) {
										level().setBlockAndUpdate(pos, random.nextBoolean() ? ESBlocks.RAW_AETHERSENT_BLOCK.get().defaultBlockState() : ESBlocks.NEBULAITE.get().defaultBlockState());
									}
								}
							}
						}
					}
					if (ESConfig.INSTANCE.mobsConfig.creteor.canSpawn() && random.nextFloat() < ESConfig.INSTANCE.mobsConfig.creteor.spawnChance() && level().getEntitiesOfClass(Creteor.class, getBoundingBox().inflate(32)).isEmpty()) {
						Creteor creteor = new Creteor(ESEntities.CRETEOR.get(), level());
						creteor.setPos(position());
						creteor.setPersistenceRequired();
						level().addFreshEntity(creteor);
					}
					for (int i = 0; i < ((ServerLevel) level()).players().size(); ++i) {
						ServerPlayer serverPlayer = ((ServerLevel) level()).players().get(i);
						((ServerLevel) level()).sendParticles(serverPlayer, ESParticles.AETHERSENT_EXPLOSION.get(), true, getX(), getY(), getZ(), 1, 0, 0, 0, 0);
					}
				} else if (level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 25; i++) {
						Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.AETHERSENT, position().x + speed.x * 1.2, position().y + speed.y * 1.2, position().z + speed.z * 1.2, speed.x, speed.y, speed.z));
					}
				}
			}
			discard();
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS) {
			if (natural && level() instanceof ServerLevel serverLevel) {
				ScreenShakeVfx.createInstance(level().dimension(), position(), 45, 30, 0.15f, 0.24f, 4, 5).send(serverLevel);
			}
			if (natural || (getTarget() == null && targetPos == null) || (getTarget() != null && getY() <= (getTarget().getY() + getTarget().getBbHeight())) || (targetPos != null && getY() <= targetPos.y + 1)) {
				if (natural) {
					playSound(SoundEvents.GENERIC_EXPLODE.value(), getSoundVolume(), getVoicePitch());
				}
				if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
					for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(getSize(), 0, getSize()))) {
						if (ESEntityUtil.shouldHarm(getOwner(), livingEntity)) {
							livingEntity.invulnerableTime = 0;
							livingEntity.hurt(ESDamageTypes.getEntityDamageSource(level(), ESDamageTypes.METEOR, getOwner()), getSize() * 5 * (getOwner() instanceof LivingEntity ? 0.08f : 1f) * (getOwner() instanceof Player ? (float) ESConfig.INSTANCE.itemsConfig.playerAethersentMeteorDamageScale : 1));
						}
					}
					serverLevel.sendParticles(getSize() >= 10 ? ParticleTypes.EXPLOSION_EMITTER : ESExplosionParticleOptions.AETHERSENT, getX(), getY() + 0.05 * getSize(), getZ(), 1, 0, 0, 0, 0);
					dropAndDiscard(false);
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		setDeltaMovement(0, natural ? -2 : -4, 0);
		if (tickCount % 3 == 0) {
			refreshDimensions();
		}
		if (!level().isClientSide) {
			if (target == null && targetId != null && level() instanceof ServerLevel serverLevel) {
				if (serverLevel.getEntity(targetId) instanceof LivingEntity livingEntity) {
					target = livingEntity;
				}
				if (target == null) {
					targetId = null;
				}
			}
		} else {
			oXSpin = xSpin;
			xSpin += Mth.PI * (0.03f + getRandom().nextFloat() * 0.02f);
			oYSpin = ySpin;
			ySpin += Mth.PI * (0.03f + getRandom().nextFloat() * 0.02f);
		}
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(getSize() / 10f);
	}

	@Nullable
	@Override
	protected ParticleOptions getTrailParticle() {
		return null;
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	public float getVoicePitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		return false;
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(Math.max(getSize() / 10f, 0.4f), 15);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 1.2f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(144 / 255f, 94 / 255f, 168 / 255f, 1f);
	}

	@Override
	public boolean isTrailFullBright() {
		return true;
	}
}
