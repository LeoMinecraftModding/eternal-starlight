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
import org.jetbrains.annotations.Nullable;

public class WiltedCrossbowItem extends CrossbowItem {
	public WiltedCrossbowItem(Properties properties) {
		super(properties);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
		ESDataAttachments.ARROW_TYPE.setData(projectile, ESCommonHandler.WILTED_ARROW);
		if (projectile instanceof AbstractArrow arrow) {
			arrow.setBaseDamage(arrow.getBaseDamage() + 1.5);
		}
		return projectile;
	}

	@Override
	protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
		super.shootProjectile(shooter, projectile, index, velocity, inaccuracy, angle, target);
		projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.5));
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESItems.TENACIOUS_PETAL.get()) || repairCandidate.is(ESItems.TENACIOUS_VINE.get()) || super.isValidRepairItem(stack, repairCandidate);
	}
}
