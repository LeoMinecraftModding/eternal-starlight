package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.DyedArmorTrade;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfDyerProfession extends AbstractBoarwarfProfession {
	@Override
	public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
		return new VillagerTrades.ItemListing[]{
			ESEntityUtil.simpleTrade(Items.LEATHER, 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.RABBIT_HIDE, 10, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 40),
			ESEntityUtil.simpleTrade(Items.WHITE_WOOL, 6, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.WHITE_YETI_FUR.get(), 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, ESItems.WHITE_YETI_FUR.get(), 4, 50),
			new DyedArmorTrade(Items.LEATHER_HELMET, ESItems.STARLIGHT_SILVER_COIN.get(), 3),
			new DyedArmorTrade(Items.LEATHER_CHESTPLATE, ESItems.STARLIGHT_SILVER_COIN.get(), 4),
			new DyedArmorTrade(Items.LEATHER_LEGGINGS, ESItems.STARLIGHT_SILVER_COIN.get(), 3),
			new DyedArmorTrade(Items.LEATHER_BOOTS, ESItems.STARLIGHT_SILVER_COIN.get(), 3)
		};
	}
}
