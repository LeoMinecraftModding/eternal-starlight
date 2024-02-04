package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfChefProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(Items.WHEAT_SEEDS, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
                new BuyItemTrade(Items.BEETROOT_SEEDS, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 8, 20),
                new BuyItemTrade(Items.MELON_SEEDS, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 9, 20),
                new BuyItemTrade(Items.WHEAT, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 3, 10),
                new BuyItemTrade(Items.BEETROOT, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 10),
                new BuyItemTrade(Items.MELON_SLICE, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 10),
                new BuyItemTrade(ItemInit.LUNAR_BERRIES.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 50),
                new BuyItemTrade(ItemInit.ABYSSAL_FRUIT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 4, 50),
                new BuyItemTrade(ItemInit.AURORA_DEER_STEAK.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ItemInit.LUMINOFISH.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ItemInit.LUMINARIS.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 1, 30),
                new BuyItemTrade(ItemInit.LUMINOFISH_BUCKET.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 30),
                new BuyItemTrade(ItemInit.LUMINARIS_BUCKET.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 30),
                new SellItemTrade(ItemInit.COOKED_AURORA_DEER_STEAK.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 20),
                new SellItemTrade(ItemInit.COOKED_LUMINOFISH.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 20),
                new SellItemTrade(ItemInit.COOKED_LUMINARIS.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 20)
        };
    }
}
