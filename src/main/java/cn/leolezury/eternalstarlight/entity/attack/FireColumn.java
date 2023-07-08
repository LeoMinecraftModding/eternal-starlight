package cn.leolezury.eternalstarlight.entity.attack;

import cn.leolezury.eternalstarlight.init.DamageTypeInit;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

public class FireColumn extends AbstractOwnedEntity {
    public FireColumn(EntityType<? extends Entity> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
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
        setDeltaMovement(0, 0, 0);
        if (getSpawnedTicks() > 100) {
            discard();
        }
        if (!level().isClientSide) {
            double y = 0;
            for (y = getY(); level().getBlockState(new BlockPos((int) getX(), (int) y, (int) getZ())).isAir(); y++) {
                if (y > getY() + 20) {
                    break;
                }
            }
            if (getSpawnedTicks() == 20) {
                playSound(SoundEventInit.FIRE_COLUMN_APPEAR.get(), getSoundVolume(), getVoicePitch());
            }
            if (getSpawnedTicks() > 20 && getOwner() != null) {
                AABB box = new AABB(getX() + 0.8, y, getZ() + 0.8, getX() - 0.8, getY(), getZ() - 0.8);
                for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, box)) {
                    livingEntity.hurt(DamageTypeInit.getIndirectEntityDamageSource(level(), DamageTypeInit.FIRE_COLUMN, this, getOwner()), 10);
                    livingEntity.setSecondsOnFire(5);
                }
            }
        } else {
            if (getSpawnedTicks() > 20) {
                level().addParticle(ParticleTypes.FLAME, getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 0.2, 0);
            }
        }
    }
}
