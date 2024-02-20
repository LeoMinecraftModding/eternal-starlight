package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FrozenTube extends AbstractHurtingProjectile {
    public FrozenTube(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public FrozenTube(Level level, LivingEntity entity, double x, double y, double z) {
        super(ESEntities.FROZEN_TUBE.get(), entity, x, y, z, level);
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    protected ParticleOptions getTrailParticle() {
        return ESParticles.STARLIGHT.get();
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

    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        playSound(SoundEvents.GLASS_BREAK, getSoundVolume(), getVoicePitch());
        for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
            if (entity.canFreeze()) {
                if (!level().isClientSide) {
                    if (getOwner() instanceof LivingEntity owner) {
                        entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), DamageTypes.FREEZE, this, owner), 5);
                    }
                    entity.setTicksFrozen(entity.getTicksFrozen() + 100);
                } else {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 0.7, 0.7, 0.9);
                }
            }
        }
        discard();
    }

    @Override
    public void tick() {
        Vec3 vec3 = this.getDeltaMovement();
        double e = vec3.x;
        double f = vec3.y;
        double g = vec3.z;
        double l = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(e, g) * Mth.RAD_TO_DEG));
        this.setXRot((float)(Mth.atan2(f, l) * Mth.RAD_TO_DEG));
        float actualXRot0 = xRotO;
        float actualYRot0 = yRotO;
        float actualXRot = getXRot();
        float actualYRot = getYRot();
        super.tick();
        xRotO = actualXRot0;
        yRotO = actualYRot0;
        setXRot(actualXRot);
        setYRot(actualYRot);
    }
}
