package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StarfireCrossbowItem extends CrossbowItem {
	public StarfireCrossbowItem(Properties properties) {
		super(properties);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
		ESDataAttachments.ARROW_TYPE.setData(projectile, ESCommonHandler.STARFIRE_ARROW);
		if (projectile instanceof AbstractArrow arrow) {
			arrow.setBaseDamage(arrow.getBaseDamage() + 0.25);
		}
		return projectile;
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESItems.STARFIRE.get()) || super.isValidRepairItem(stack, repairCandidate);
	}
}
