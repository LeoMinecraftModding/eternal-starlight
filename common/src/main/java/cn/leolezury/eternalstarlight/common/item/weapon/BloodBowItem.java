package cn.leolezury.eternalstarlight.common.item.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloodBowItem extends BowItem {
	public BloodBowItem(Properties properties) {
		super(properties);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		super.releaseUsing(itemStack, level, livingEntity, n);
	}
}
