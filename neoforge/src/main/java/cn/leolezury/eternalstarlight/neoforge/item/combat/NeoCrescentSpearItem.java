package cn.leolezury.eternalstarlight.neoforge.item.combat;

import cn.leolezury.eternalstarlight.common.item.combat.CrescentSpearItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class NeoCrescentSpearItem extends CrescentSpearItem {
	public NeoCrescentSpearItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ability == ItemAbilities.SWORD_SWEEP;
	}
}
