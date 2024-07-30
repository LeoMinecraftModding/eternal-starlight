package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.TangledHatred;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.TangledHatredPart;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class LunarSpore extends ThrowableProjectile implements TrailOwner {
	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");

	public LunarSpore(EntityType<? extends ThrowableProjectile> type, Level level) {
		super(type, level);
	}

	public LunarSpore(Level level, LivingEntity entity, double x, double y, double z) {
		super(ESEntities.LUNAR_SPORE.get(), x, y, z, level);
		setOwner(entity);
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	public boolean isOnFire() {
		return false;
	}

	public boolean hurt(DamageSource damageSource, float amount) {
		return false;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	public void tick() {
		super.tick();
		if ((tickCount > 200 || getDeltaMovement().length() < 0.001) && !level().isClientSide) discard();
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (getOwner() instanceof TangledHatred hatred && hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hitResult).getEntity() instanceof TangledHatredPart part) {
			boolean hasPart = false;
			for (TangledHatredPart hatredPart : hatred.parts) {
				if (hatredPart.getUUID().equals(part.getUUID())) {
					hasPart = true;
				}
			}
			if (hasPart) {
				return;
			}
		}
		if (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hitResult).getEntity() instanceof Projectile) {
			return;
		}
		if (!level().isClientSide) {
			playSound(SoundEvents.GENERIC_EXPLODE.value());
			if (level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 4; i++) {
					Vec3 vec3 = new Vec3(this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth());
					for (int m = 0; m < serverLevel.players().size(); ++m) {
						ServerPlayer serverPlayer = serverLevel.players().get(m);
						serverLevel.sendParticles(serverPlayer, ESExplosionParticleOptions.LUNAR, true, vec3.x, vec3.y, vec3.z, 3, 0, 0, 0, 0);
						serverLevel.sendParticles(serverPlayer, ESSmokeParticleOptions.LUNAR_SHORT, true, vec3.x, vec3.y, vec3.z, 3, 0, 0, 0, 0);
					}
				}
				CameraShake.createCameraShake(level(), position(), 45, 0.001f, 80, 20);
			}
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
				if (getOwner() instanceof LivingEntity owner && !owner.getUUID().equals(entity.getUUID())) {
					entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, owner), 5);
				}
			}
			discard();
		}
	}

	@Override
	public TrailEffect newTrail() {
		return new TrailEffect(0.4f, getOwner() instanceof LunarMonstrosity ? 10 : 40);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0), position().subtract(oldPos));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 0.5f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(32 / 255f, 32 / 255f, 64 / 255f, 0.75f);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public RenderType getTrailRenderType() {
		return ESRenderType.translucentGlow(TRAIL_TEXTURE);
	}
}
