package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class GlaciteShieldItem extends ShieldItem {
	public GlaciteShieldItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESConventionalTags.Items.GEMS_GLACITE) || super.isValidRepairItem(stack, repairCandidate);
	}
}
