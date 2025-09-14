package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ThrownPungencyFruitSpear extends ThrownSpear {
	public ThrownPungencyFruitSpear(EntityType<? extends ThrownPungencyFruitSpear> type, Level level) {
		super(type, level);
	}

	public ThrownPungencyFruitSpear(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		super(ESEntities.PUNGENCY_FRUIT_SPEAR.get(), level, owner, x, y, z, pickupItemStack);
	}

	@Override
	protected float getDamageScale(Entity target) {
		return 2.5F;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		living.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
		living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120));
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.PUNGENCY_FRUIT_SPEAR.get().getDefaultInstance();
	}
}
