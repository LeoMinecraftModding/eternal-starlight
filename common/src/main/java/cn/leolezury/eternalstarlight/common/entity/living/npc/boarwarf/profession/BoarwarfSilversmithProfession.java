package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.npc.VillagerTrades;

public class BoarwarfSilversmithProfession extends AbstractBoarwarfProfession {
	@Override
	public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
		return new VillagerTrades.ItemListing[]{
			ESEntityUtil.simpleTrade(ESItems.AETHERSENT_INGOT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 10),
			ESEntityUtil.simpleTrade(ESItems.DEEPSILVER_INGOT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 3, 10),
			ESEntityUtil.simpleTrade(ESItems.THERMAL_SPRINGSTONE_INGOT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 3, 10),
			ESEntityUtil.simpleTrade(ESItems.GOLEM_STEEL_INGOT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 15, 10),
			ESEntityUtil.simpleTrade(ESItems.TENACIOUS_PETAL.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 18, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.DEEPSILVER_SWORD.get(), 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.DEEPSILVER_AXE.get(), 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.DEEPSILVER_PICKAXE.get(), 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.DEEPSILVER_SICKLE.get(), 1, 10),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 20, ESItems.SHATTERED_SWORD.get(), 1, 1),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 20, ESItems.ENERGY_SWORD.get(), 1, 1)
		};
	}
}
