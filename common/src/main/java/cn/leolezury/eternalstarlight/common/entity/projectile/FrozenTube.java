package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FrozenTube extends AbstractArrow {
    private boolean dealtDamage;

    public FrozenTube(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level, new ItemStack(ESItems.FROZEN_TUBE.get()));
    }

    public FrozenTube(Level level, LivingEntity livingEntity) {
        super(ESEntities.FROZEN_TUBE.get(), livingEntity, level, new ItemStack(ESItems.FROZEN_TUBE.get()));
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        super.tick();
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 vec3, Vec3 vec32) {
        return this.dealtDamage ? null : super.findHitEntity(vec3, vec32);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        playSound(SoundEvents.GLASS_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        if (level() instanceof ServerLevel serverLevel) {
            ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ESParticlePacket(new ItemParticleOption(ParticleTypes.ITEM, ESItems.FROZEN_TUBE.get().getDefaultInstance()), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 0, 0, 0));
        }
        for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
            if (entity.canFreeze()) {
                if (!level().isClientSide) {
                    if (getOwner() instanceof LivingEntity owner) {
                        entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), DamageTypes.FREEZE, this, owner), 5);
                    }
                    entity.setTicksFrozen(entity.getTicksFrozen() + 100);
                }
            }
        }
        discard();
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.dealtDamage = compoundTag.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("DealtDamage", this.dealtDamage);
    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    public boolean shouldRender(double d, double e, double f) {
        return true;
    }
}