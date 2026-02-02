package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownMalariteSpear;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownSpear;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MalariteSpearItem extends SpearItem {
	public MalariteSpearItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public ThrownSpear createSpear(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		return new ThrownMalariteSpear(level, owner, x, y, z, pickupItemStack);
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		ThrownMalariteSpear spear = new ThrownMalariteSpear(level, null, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
		spear.pickup = AbstractArrow.Pickup.ALLOWED;
		return spear;
	}
}
