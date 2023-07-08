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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class TwilightSquid extends Mob {
    public TwilightSquid(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Squid.createAttributes();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new RandomLookAroundGoal(this));
    }

    public static boolean checkTwilightSquidSpawnRules(EntityType<? extends TwilightSquid> p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
        return p_218106_.getBlockState(p_218108_.below()).is(BlockTags.DIRT);
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        double x = getRandom().nextDouble() - 0.5;
        double y = getRandom().nextDouble();
        double z = getRandom().nextDouble() - 0.5;
        for (int i = 0; i < 10; i++) {
            level().addParticle(ParticleTypes.GLOW_SQUID_INK, getX(), getY(), getZ(), x, y, z);
            playSound(SoundEventInit.TWILIGHT_SQUID_SQUIRT.get());
        }
        if (p_21016_.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
        }
        return super.hurt(p_21016_, p_21017_);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEventInit.TWILIGHT_SQUID_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.TWILIGHT_SQUID_DEATH.get();
    }
}
