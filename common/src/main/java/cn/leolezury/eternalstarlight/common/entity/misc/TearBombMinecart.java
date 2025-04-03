package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TearBombMinecart extends MinecartTNT {
	public TearBombMinecart(EntityType<? extends TearBombMinecart> type, Level level) {
		super(type, level);
	}

	public TearBombMinecart(Level level, double x, double y, double z) {
		super(ESEntities.TEAR_BOMB_MINECART.get(), level);
		setPos(x, y, z);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	public BlockState getDefaultDisplayBlockState() {
		return ESBlocks.TEAR_BOMB.get().defaultBlockState();
	}

	@Override
	public ItemStack getPickResult() {
		return new ItemStack(ESItems.TEAR_BOMB_MINECART.get());
	}

	@Override
	protected void explode(@Nullable DamageSource source, double movement) {
		if (!this.level().isClientSide) {
			double e = Math.sqrt(movement);
			if (e > 5.0) {
				e = 5.0;
			}
			this.level().explode(this, source, null, this.getX(), this.getY(), this.getZ(), (float) (3.0F + this.random.nextDouble() * 1.5F * e), false, Level.ExplosionInteraction.TNT);
			AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
			cloud.setRadius((float) (5F + this.random.nextDouble() * 1.5F * e));
			cloud.setRadiusOnUse(-0.5F);
			cloud.setWaitTime(10);
			cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
			cloud.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.POISON, 120)));
			cloud.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.CONFUSION, 120)));
			cloud.addEffect(new MobEffectInstance(new MobEffectInstance(ESMobEffects.TEARY.asHolder(), 120)));
			this.level().addFreshEntity(cloud);
			this.discard();
		}
	}
}
