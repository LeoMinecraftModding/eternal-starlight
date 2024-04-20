package cn.leolezury.eternalstarlight.forge.item.weapon;

import cn.leolezury.eternalstarlight.common.item.weapon.ScytheItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

public class ForgeScytheItem extends ScytheItem {
    public ForgeScytheItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return super.canPerformAction(stack, toolAction) || ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }
}
