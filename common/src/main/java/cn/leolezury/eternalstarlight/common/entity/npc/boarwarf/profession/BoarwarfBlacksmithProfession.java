package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfBlacksmithProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(Items.IRON_INGOT, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
                new BuyItemTrade(Items.RAW_IRON, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 3, 20),
                new BuyItemTrade(Items.IRON_ORE, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 4, 20),
                new BuyItemTrade(ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 3, 1, 10),
                new BuyItemTrade(ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 15, 1, 10),
                new BuyItemTrade(ItemInit.TENACIOUS_PETAL.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 18, 1, 10),
                new SellItemTrade(Items.SHEARS, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 1, 10),
                new SellItemTrade(Items.IRON_SWORD, ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(Items.IRON_AXE, ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(Items.IRON_PICKAXE, ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(Items.IRON_SHOVEL, ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(Items.IRON_HOE, ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 10)
        };
    }
}
