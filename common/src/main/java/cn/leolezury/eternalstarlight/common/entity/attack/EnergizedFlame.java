package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

public class EnergizedFlame extends AttackEffect {
    public EnergizedFlame(EntityType<? extends Entity> type, Level level) {
        super(type, level);
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public boolean shouldContinueToTick() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (getSpawnedTicks() > 100) {
            discard();
        }
        if (!level().isClientSide) {
            if (getSpawnedTicks() == 20) {
                playSound(SoundEvents.FIRECHARGE_USE, getSoundVolume(), getVoicePitch());
            }
            if (getSpawnedTicks() > 20 && getOwner() != null) {
                AABB box = getBoundingBox().inflate(0.5, 1, 0.5);
                for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, box)) {
                    livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ENERGIZED_FLAME, this, getOwner()), 2);
                    livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + 60);
                }
            }
        } else {
            level().addParticle(ESSmokeParticleOptions.ENERGIZED_FLAME, getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 1, 0);
            level().addParticle(ParticleTypes.LARGE_SMOKE, getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 0.2, 0);
        }
    }
}
