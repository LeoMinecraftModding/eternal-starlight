package cn.leolezury.eternalstarlight.common.item.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlisteringBowItem extends BowItem {
	public GlisteringBowItem(Properties properties) {
		super(properties);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
		if (projectile instanceof AbstractArrow arrow) {
			arrow.setBaseDamage(arrow.getBaseDamage() + 0.3);
		}
		return projectile;
	}
}
