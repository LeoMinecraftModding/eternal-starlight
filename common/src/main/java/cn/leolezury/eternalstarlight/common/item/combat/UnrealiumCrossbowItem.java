package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class UnrealiumCrossbowItem extends CrossbowItem {
	public UnrealiumCrossbowItem(Properties properties) {
		super(properties);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
		if (projectile instanceof AbstractArrow arrow) {
			arrow.setBaseDamage(arrow.getBaseDamage() + 0.6 + arrow.getPierceLevel() * 0.1);
			arrow.setPierceLevel(Byte.MAX_VALUE);
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
		return repairCandidate.is(ESConventionalTags.Items.INGOTS_UNREALIUM) || super.isValidRepairItem(stack, repairCandidate);
	}
}
