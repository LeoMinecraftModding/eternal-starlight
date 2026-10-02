package cn.leolezury.eternalstarlight.neoforge.item.combat;

import cn.leolezury.eternalstarlight.common.item.combat.SickleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class NeoSickleItem extends SickleItem {
	public NeoSickleItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	// NeoForge maps the vanilla shears loot conditions and interactions to this ability
	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ItemAbilities.DEFAULT_SHEARS_ACTIONS.contains(ability);
	}
}
