package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class LunarVine extends AttackEffect {
    public LunarVine(EntityType<? extends AttackEffect> type, Level level) {
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
                            livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, getOwner()), 4);
                        }
                    }
                }
                if (getAttackMode() == 1) {
                    for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5))) {
                        if (!livingEntity.getUUID().equals(getOwner().getUUID())) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
                            livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, getOwner()), 1);
                        }
                    }
                }
            }
        } else {
            level().addParticle(ESParticles.POISON.get(), getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 0, 0);
        }
    }
}
