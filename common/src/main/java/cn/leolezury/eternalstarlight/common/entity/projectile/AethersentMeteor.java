package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import java.util.UUID;

public class AethersentMeteor extends AbstractHurtingProjectile implements TrailOwner {
	public static final String TAG_METEOR_COOLDOWN = "meteor_cooldown";
	private static final String TAG_SIZE = "size";
	private static final String TAG_TARGET = "target";
	private static final String TAG_TARGET_X = "target_x";
	private static final String TAG_TARGET_Y = "target_y";
	private static final String TAG_TARGET_Z = "target_z";
	private static final String TAG_ONLY_HURT_ENEMY = "only_hurt_enemy";
	private static final String TAG_NATURAL = "natural";

	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/concentrated_trail.png");
	protected static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(AethersentMeteor.class, EntityDataSerializers.INT);

	public int getSize() {
		return this.getEntityData().get(SIZE);
	}

	public void setSize(int size) {
		this.getEntityData().set(SIZE, size);
	}

	@Nullable
	private LivingEntity target;
	@Nullable
	private UUID targetId;

	public LivingEntity getTarget() {
		return target;
	}

	public void setTarget(LivingEntity target) {
		this.target = target;
	}

	private Vec3 targetPos = Vec3.ZERO;

	public void setTargetPos(Vec3 targetPos) {
		this.targetPos = targetPos;
	}

	private boolean onlyHurtEnemy = true;

	public void setOnlyHurtEnemy(boolean onlyHurtEnemy) {
		this.onlyHurtEnemy = onlyHurtEnemy;
	}

	private boolean natural = true;

	public AethersentMeteor(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
		super(type, level);
	}

	public AethersentMeteor(Level level, LivingEntity entity, double x, double y, double z) {
		this(ESEntities.AETHERSENT_METEOR.get(), level);
		xo = x;
		yo = y;
		zo = z;
		setPos(x, y, z);
		setOwner(entity);
	}

	public static void createMeteorShower(Level level, LivingEntity entity, LivingEntity target, double targetX, double targetY, double targetZ, double height, boolean onlyHurtEnemy) {
		if (!level.isClientSide) {
			CompoundTag tag = ESEntityUtil.getPersistentData(entity);
			if (tag.getInt(TAG_METEOR_COOLDOWN) > 0) {
				return;
			}
			tag.putInt(TAG_METEOR_COOLDOWN, 1);
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					RandomSource random = entity.getRandom();
					AethersentMeteor meteor = new AethersentMeteor(level, entity, targetX + x + (random.nextFloat() - 0.5) * 3, targetY + height + (random.nextFloat() - 0.5) * 5, targetZ + z + (random.nextFloat() - 0.5) * 3);
					meteor.setSize(random.nextInt(2, 5));
					meteor.setTarget(target);
					meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
					meteor.onlyHurtEnemy = onlyHurtEnemy;
					meteor.natural = false;
					level.addFreshEntity(meteor);
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ParticleTypes.EXPLOSION, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
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
		if (compoundTag.contains(TAG_ONLY_HURT_ENEMY, CompoundTag.TAG_BYTE)) {
			onlyHurtEnemy = compoundTag.getBoolean(TAG_ONLY_HURT_ENEMY);
		}
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
		compoundTag.putBoolean(TAG_ONLY_HURT_ENEMY, onlyHurtEnemy);
		compoundTag.putBoolean(TAG_NATURAL, natural);
	}

	private void handleHit() {
		if (level() instanceof ServerLevel serverLevel) {
			ScreenShakeVfx.createInstance(level().dimension(), position(), 45, 40, 0.02f, 0.03f, 4.5f, 5).send(serverLevel);
		}
		if (natural) {
			dropAndDiscard();
		}
		for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(getSize()))) {
			if ((!(getOwner() instanceof Player) || livingEntity instanceof Enemy || !onlyHurtEnemy) && (getOwner() == null || !getOwner().getUUID().equals(livingEntity.getUUID()))) {
				livingEntity.invulnerableTime = 0;
				livingEntity.hurt(ESDamageTypes.getEntityDamageSource(level(), ESDamageTypes.METEOR, getOwner()), getSize() * (float) 5 * (getOwner() instanceof LivingEntity ? 0.01f : 1f));
			}
		}
		if (getTarget() != null && getY() < getTarget().getY()) {
			dropAndDiscard();
		} else if (getY() < targetPos.y) {
			dropAndDiscard();
		}
	}

	private void dropAndDiscard() {
		if (!isRemoved()) {
			playSound(SoundEvents.GENERIC_EXPLODE.value(), getSoundVolume(), getVoicePitch());
			if (!level().isClientSide) {
				((ServerLevel) level()).sendParticles(getSize() >= 8 ? ParticleTypes.EXPLOSION_EMITTER : ParticleTypes.EXPLOSION, getX(), getY() + 0.05 * getSize(), getZ(), 1, 0, 0, 0, 0);
				if (natural && getSize() >= 10) {
					ItemEntity entity = spawnAtLocation(new ItemStack(ESItems.RAW_AETHERSENT.get(), random.nextInt(5, 9)));
					for (int x = -3; x <= 3; x++) {
						for (int y = -3; y <= 3; y++) {
							for (int z = -3; z <= 3; z++) {
								BlockPos pos = blockPosition().offset(x, y, z);
								if (pos.distToCenterSqr(blockPosition().getCenter()) <= 3.5 && level().getBlockState(pos).is(ESTags.Blocks.AETHERSENT_METEOR_REPLACEABLES)) {
									level().setBlockAndUpdate(pos, random.nextBoolean() ? ESBlocks.RAW_AETHERSENT_BLOCK.get().defaultBlockState() : ESBlocks.NEBULAITE.get().defaultBlockState());
								}
							}
						}
					}
					if (entity != null) {
						entity.setGlowingTag(true);
					}
					for (int m = 0; m < ((ServerLevel) level()).players().size(); ++m) {
						ServerPlayer serverPlayer = ((ServerLevel) level()).players().get(m);
						((ServerLevel) level()).sendParticles(serverPlayer, ESParticles.AETHERSENT_EXPLOSION.get(), true, getX(), getY(), getZ(), 1, 0, 0, 0, 0);
					}
				}
				discard();
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		handleHit();
		if (!level().isClientSide && target == null && targetPos == null) {
			dropAndDiscard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		handleHit();
	}

	@Override
	public void tick() {
		super.tick();
		setDeltaMovement(0, -2, 0);
		if (tickCount % 10 == 0) {
			refreshDimensions();
		}
		if (!level().isClientSide) {
			if (target == null && targetId != null) {
				if (((ServerLevel) this.level()).getEntity(targetId) instanceof LivingEntity livingEntity) {
					target = livingEntity;
				}
				if (target == null) {
					targetId = null;
				}
			}
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
	public TrailEffect newTrail() {
		return new TrailEffect(Math.max(getSize() / 10f, 0.4f), 15);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0), position().subtract(oldPos));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 1.2f, 0));
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public TrailEffect.TrailPoint adjustPoint(TrailEffect.TrailPoint point, boolean vertical, float partialTicks) {
		Vec3 center = point.center();
		float width = point.width() / 2;
		if (Minecraft.getInstance().getCameraEntity() != null) {
			float yRot = Minecraft.getInstance().getCameraEntity().getYHeadRot() + 90;
			Vec3 upper = ESMathUtil.rotationToPosition(center, width, 0, yRot + 90);
			Vec3 lower = ESMathUtil.rotationToPosition(center, width, 0, yRot - 90);
			return new TrailEffect.TrailPoint(upper, lower);
		}
		return point;
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(144 / 255f, 94 / 255f, 168 / 255f, 1f);
	}

	@Override
	public boolean isTrailFullBright() {
		return true;
	}

	@Override
	public boolean shouldRenderHorizontal() {
		return false;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public RenderType getTrailRenderType() {
		return ESRenderType.entityTranslucentGlow(TRAIL_TEXTURE);
	}
}
