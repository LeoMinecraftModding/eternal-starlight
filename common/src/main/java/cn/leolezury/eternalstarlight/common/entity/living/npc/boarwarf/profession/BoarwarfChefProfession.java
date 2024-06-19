package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfChefProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(Items.WHEAT_SEEDS, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
                new BuyItemTrade(Items.BEETROOT_SEEDS, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 8, 20),
                new BuyItemTrade(Items.MELON_SEEDS, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 9, 20),
                new BuyItemTrade(Items.WHEAT, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 3, 10),
                new BuyItemTrade(Items.BEETROOT, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 10),
                new BuyItemTrade(Items.MELON_SLICE, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 10),
                new BuyItemTrade(ESItems.LUNAR_BERRIES.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 50),
                new BuyItemTrade(ESItems.ABYSSAL_FRUIT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 4, 50),
                new BuyItemTrade(ESItems.AURORA_DEER_STEAK.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ESItems.RATLIN_MEAT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ESItems.LUMINOFISH.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ESItems.LUMINARIS.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ESItems.LUMINOFISH_BUCKET.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 30),
                new BuyItemTrade(ESItems.LUMINARIS_BUCKET.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 30),
                new SellItemTrade(ESItems.COOKED_AURORA_DEER_STEAK.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 20),
                new SellItemTrade(ESItems.COOKED_RATLIN_MEAT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 20),
                new SellItemTrade(ESItems.COOKED_LUMINOFISH.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 20),
                new SellItemTrade(ESItems.COOKED_LUMINARIS.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 20)
        };
    }
}
