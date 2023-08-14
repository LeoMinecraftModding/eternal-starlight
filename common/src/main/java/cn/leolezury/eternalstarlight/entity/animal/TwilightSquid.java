package cn.leolezury.eternalstarlight.entity.animal;

import cn.leolezury.eternalstarlight.init.SoundEventInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class TwilightSquid extends PathfinderMob {
    public TwilightSquid(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Squid.createAttributes();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new RandomLookAroundGoal(this));
    }

    public static boolean checkTwilightSquidSpawnRules(EntityType<? extends TwilightSquid> squid, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        double x = getRandom().nextDouble() - 0.5;
        double y = getRandom().nextDouble();
        double z = getRandom().nextDouble() - 0.5;
        for (int i = 0; i < 10; i++) {
            level().addParticle(ParticleTypes.GLOW_SQUID_INK, getX(), getY(), getZ(), x, y, z);
        }
        if (!level().isClientSide) {
            if (!this.isSilent()) {
                this.playSound(SoundEventInit.TWILIGHT_SQUID_SQUIRT.get(), getSoundVolume(), 1.0F);
            }
            if (source.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEventInit.TWILIGHT_SQUID_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEventInit.TWILIGHT_SQUID_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.TWILIGHT_SQUID_DEATH.get();
    }
}
