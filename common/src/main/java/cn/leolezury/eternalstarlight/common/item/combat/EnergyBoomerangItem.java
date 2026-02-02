package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownBoomerang;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownEnergyBoomerang;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EnergyBoomerangItem extends BoomerangItem {
	public EnergyBoomerangItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public ThrownBoomerang createBoomerang(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		return new ThrownEnergyBoomerang(level, owner, x, y, z, pickupItemStack);
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		ThrownEnergyBoomerang boomerang = new ThrownEnergyBoomerang(level, null, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
		boomerang.pickup = AbstractArrow.Pickup.ALLOWED;
		return boomerang;
	}
}
