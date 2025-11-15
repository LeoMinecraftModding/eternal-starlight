package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownEnergyBoomerang extends ThrownBoomerang {
	public ThrownEnergyBoomerang(EntityType<? extends ThrownEnergyBoomerang> type, Level level) {
		super(type, level);
	}

	public ThrownEnergyBoomerang(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		super(ESEntities.ENERGY_BOOMERANG.get(), level, owner, x, y, z, pickupItemStack);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		super.doPostHurtEffects(livingEntity);
		Entity owner = getOwner();
		ItemStack weapon = getWeaponItem();
		livingEntity.igniteForSeconds(2);
		if ((owner == null || weapon == null || !SpecialItemCooldown.isOnCooldown(owner, weapon.getItem())) && level() instanceof ServerLevel serverLevel) {
			for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2))) {
				if (ESEntityUtil.shouldHarm(getOwner(), living) && living != livingEntity) {
					living.invulnerableTime = 0;
					if (living.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ELECTRIC_SHOCK, this, getOwner()), 8)) {
						living.igniteForSeconds(2);
						Vec3 speed = living.position().add((random.nextDouble() - 0.5) * living.getBbWidth(), random.nextDouble() * living.getBbHeight(), (random.nextDouble() - 0.5) * living.getBbWidth()).subtract(position());
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.ELECTRIC_SPARK.get(), getX(), getY(), getZ(), speed.x, speed.y, speed.z));
					}
				}
			}
			if (owner != null && weapon != null) {
				SpecialItemCooldown.setCooldown(owner, weapon.getItem(), 40);
			}
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.ENERGY_BOOMERANG.get().getDefaultInstance();
	}
}
