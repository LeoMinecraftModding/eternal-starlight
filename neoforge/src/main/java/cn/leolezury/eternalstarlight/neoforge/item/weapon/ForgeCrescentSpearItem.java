package cn.leolezury.eternalstarlight.neoforge.item.weapon;

import cn.leolezury.eternalstarlight.common.item.weapon.CrescentSpearItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class ForgeCrescentSpearItem extends CrescentSpearItem {
	public ForgeCrescentSpearItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ability == ItemAbilities.SWORD_SWEEP;
	}
}
