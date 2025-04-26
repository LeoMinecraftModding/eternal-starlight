package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TearBomb extends PrimedTnt {
	public TearBomb(EntityType<? extends TearBomb> type, Level level) {
		super(type, level);
	}

	public TearBomb(Level level, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(ESEntities.TEAR_BOMB.get(), level);
		this.setPos(x, y, z);
		double d0 = level.getRandom().nextDouble() * (Math.PI * 2F);
		this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
		// faster
		this.setFuse(60);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.owner = igniter;
		this.setBlockState(ESBlocks.TEAR_BOMB.get().defaultBlockState());
	}

	@Override
	protected void explode() {
		this.level().explode(this, Explosion.getDefaultDamageSource(this.level(), this), this.usedPortal ? PrimedTnt.USED_PORTAL_DAMAGE_CALCULATOR : null, this.getX(), this.getY(0.0625F), this.getZ(), 3.0F, false, Level.ExplosionInteraction.TNT);
		AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
		cloud.setRadius(5F);
		cloud.setRadiusOnUse(-0.5F);
		cloud.setWaitTime(10);
		cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
		cloud.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.POISON, 120)));
		cloud.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.CONFUSION, 120)));
		cloud.addEffect(new MobEffectInstance(new MobEffectInstance(ESMobEffects.TEARY.asHolder(), 120)));
		this.level().addFreshEntity(cloud);
	}
}
