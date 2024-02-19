package cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ESItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class GatekeeperTrades {
    public static final VillagerTrades.ItemListing[] TRADES = new VillagerTrades.ItemListing[] {
            new BuyItemTrade(Items.IRON_INGOT, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
            new BuyItemTrade(Items.EMERALD, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 20),
            new SellItemTrade(ESItems.SEEKING_EYE.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
            new SellItemTrade(ESItems.ORB_OF_PROPHECY.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 20)
    };
}
