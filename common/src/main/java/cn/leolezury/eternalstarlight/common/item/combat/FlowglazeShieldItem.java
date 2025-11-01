package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class FlowglazeShieldItem extends ShieldItem {
	public FlowglazeShieldItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESItems.FLOWGLAZE.get()) || super.isValidRepairItem(stack, repairCandidate);
	}
}
