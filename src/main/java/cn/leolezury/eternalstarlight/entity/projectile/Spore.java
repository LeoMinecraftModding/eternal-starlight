package cn.leolezury.eternalstarlight.entity.projectile;

import cn.leolezury.eternalstarlight.entity.attack.Vine;
import cn.leolezury.eternalstarlight.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.datagen.generator.DamageTypeGenerator;
import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ParticleInit;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Random;

public class Spore extends AbstractHurtingProjectile {
    public Spore(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public Spore(Level p_37609_, LivingEntity p_37610_, double p_37611_, double p_37612_, double p_37613_) {
        super(EntityInit.SPORE.get(), p_37610_, p_37611_, p_37612_, p_37613_, p_37609_);
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleInit.POISON.get();
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    public boolean isOnFire() {
        return false;
    }

    public boolean hurt(DamageSource p_37616_, float p_37617_) {
        return false;
    }

    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);

        playSound(SoundEvents.GENERIC_EXPLODE, getSoundVolume(), getVoicePitch());
        AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());

        if (getOwner() instanceof LivingEntity entity) {
            cloud.setOwner(entity);
        }
        cloud.setParticle(ParticleInit.POISON.get());
        cloud.setRadius(1.5F);
        cloud.setDuration(200);
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        this.level().addFreshEntity(cloud);

        if (p_37258_.getDirection().equals(Direction.UP)) {
            for (int i = 0; i < 5; i++) {
                Vine vine = EntityInit.VINE.get().create(level());
                Random random = new Random();
                vine.setPos(position().add(random.nextDouble(), 0.2, random.nextDouble()));
                vine.setAttackMode(0);
                if (getOwner() instanceof LivingEntity entity) {
                    vine.setOwner(entity);
                }
                level().addFreshEntity(vine);
            }
        }
        if (!level().isClientSide) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
        }

        CameraShake.cameraShake(level(), position(), 45, 0.002f, 20, 10);
        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        super.onHitEntity(p_37259_);

        if (getOwner() != null && !p_37259_.getEntity().getUUID().equals(getOwner().getUUID())) {
            p_37259_.getEntity().hurt(DamageTypeGenerator.getIndirectEntityDamageSource(level(), DamageTypeGenerator.POISON, this, getOwner()), 5);
        }

        playSound(SoundEvents.GENERIC_EXPLODE, getSoundVolume(), getVoicePitch());
        AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());

        if (getOwner() instanceof LivingEntity entity) {
            cloud.setOwner(entity);
        }
        cloud.setParticle(ParticleInit.POISON.get());
        cloud.setRadius(1.5F);
        cloud.setDuration(200);
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        this.level().addFreshEntity(cloud);

        if (!level().isClientSide) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
        }

        CameraShake.cameraShake(level(), position(), 45, 0.002f, 20, 10);
        discard();
    }
}
