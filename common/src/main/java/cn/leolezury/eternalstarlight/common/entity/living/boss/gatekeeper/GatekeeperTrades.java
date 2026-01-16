package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class GatekeeperTrades {
	public static final VillagerTrades.ItemListing[] TRADES = new VillagerTrades.ItemListing[]{
		ESEntityUtil.simpleTrade(Items.IRON_INGOT, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
		ESEntityUtil.simpleTrade(Items.EMERALD, 1, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
		ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, ESItems.SEEKING_EYE.get(), 5, 20),
		ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, ESItems.CHISELED_VOIDSTONE.get(), 8, 20),
		ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 5, ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1, 10),
		ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, ESItems.ORB_OF_PROPHECY.get(), 1, 20),
		ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 5, ESItems.BOOK.get(), 1, 20)
	};
}
