package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfBlacksmithProfession extends AbstractBoarwarfProfession {
	@Override
	public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
		return new VillagerTrades.ItemListing[]{
			ESEntityUtil.simpleTrade(Items.IRON_INGOT, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.RAW_IRON, 3, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.IRON_ORE, 4, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.THERMAL_SPRINGSTONE_INGOT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 3, 10),
			ESEntityUtil.simpleTrade(ESItems.GOLEM_STEEL_INGOT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 15, 10),
			ESEntityUtil.simpleTrade(ESItems.TENACIOUS_PETAL.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 18, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, Items.SHEARS, 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, Items.IRON_SWORD, 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, Items.IRON_AXE, 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, Items.IRON_PICKAXE, 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, Items.IRON_SHOVEL, 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, Items.IRON_HOE, 1, 10)
		};
	}
}
