package cn.leolezury.eternalstarlight.common.item.tooltip;

import cn.leolezury.eternalstarlight.common.item.component.LargeItemStackList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record GalacticQuiverTooltipComponent(LargeItemStackList contents) implements TooltipComponent {
}
