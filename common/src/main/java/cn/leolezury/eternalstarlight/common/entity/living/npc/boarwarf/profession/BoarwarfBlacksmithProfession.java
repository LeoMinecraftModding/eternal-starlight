package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfBlacksmithProfession extends AbstractBoarwarfProfession {
	@Override
	public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
		return new VillagerTrades.ItemListing[]{
			new BuyItemTrade(Items.IRON_INGOT, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
			new BuyItemTrade(Items.RAW_IRON, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 3, 20),
			new BuyItemTrade(Items.IRON_ORE, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 4, 20),
			new BuyItemTrade(ESItems.THERMAL_SPRINGSTONE_INGOT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 3, 1, 10),
			new BuyItemTrade(ESItems.GOLEM_STEEL_INGOT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 15, 1, 10),
			new BuyItemTrade(ESItems.TENACIOUS_PETAL.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 18, 1, 10),
			new SellItemTrade(Items.SHEARS, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 10),
			new SellItemTrade(Items.IRON_SWORD, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
			new SellItemTrade(Items.IRON_AXE, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
			new SellItemTrade(Items.IRON_PICKAXE, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
			new SellItemTrade(Items.IRON_SHOVEL, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
			new SellItemTrade(Items.IRON_HOE, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10)
		};
	}
}
