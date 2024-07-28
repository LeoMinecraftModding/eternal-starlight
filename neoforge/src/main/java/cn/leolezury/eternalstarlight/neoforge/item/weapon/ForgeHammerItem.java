package cn.leolezury.eternalstarlight.neoforge.item.weapon;

import cn.leolezury.eternalstarlight.common.item.weapon.HammerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class ForgeHammerItem extends HammerItem {
	public ForgeHammerItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability) || ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(ability);
	}
}
