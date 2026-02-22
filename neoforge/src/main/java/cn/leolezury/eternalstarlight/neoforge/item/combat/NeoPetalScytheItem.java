package cn.leolezury.eternalstarlight.neoforge.item.combat;

import cn.leolezury.eternalstarlight.common.item.combat.PetalScytheItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class NeoPetalScytheItem extends PetalScytheItem {
	public NeoPetalScytheItem(Tier tier, boolean canTill, Properties properties) {
		super(tier, canTill, properties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || (canTill && ItemAbilities.DEFAULT_HOE_ACTIONS.contains(ability)) || ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability);
	}
}
