package cn.leolezury.eternalstarlight.entity.attack;

import cn.leolezury.eternalstarlight.datagen.DamageTypeInit;
import cn.leolezury.eternalstarlight.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.init.ParticleInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Vine extends AbstractOwnedEntity {
    public Vine(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean shouldContinueToTick() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (getSpawnedTicks() >= 200) {
                discard();
            }
            if (getSpawnedTicks() == 10) {
                CameraShake.createCameraShake(level(), position(), 30, 0.001f, 20, 10);
            }
            if (getSpawnedTicks() > 40 && getOwner() != null) {
                if (getAttackMode() == 0) {
                    for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5))) {
                        if (!livingEntity.getUUID().equals(getOwner().getUUID())) {
                            livingEntity.hurt(DamageTypeInit.getIndirectEntityDamageSource(level(), DamageTypeInit.POISON, this, getOwner()), 4);
                        }
                    }
                }
                if (getAttackMode() == 1) {
                    for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5))) {
                        if (!livingEntity.getUUID().equals(getOwner().getUUID())) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
                            livingEntity.hurt(DamageTypeInit.getIndirectEntityDamageSource(level(), DamageTypeInit.POISON, this, getOwner()), 1);
                        }
                    }
                }
            }
        } else {
            Random random = new Random();
            level().addParticle(ParticleInit.POISON.get(), getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 0, 0);
        }
    }
}
