package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ThrownMalariteSpear extends ThrownSpear {
	public ThrownMalariteSpear(EntityType<? extends ThrownMalariteSpear> type, Level level) {
		super(type, level);
	}

	public ThrownMalariteSpear(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		super(ESEntities.MALARITE_SPEAR.get(), level, owner, x, y, z, pickupItemStack);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		living.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.MALARITE_SPEAR.get().getDefaultInstance();
	}
}
