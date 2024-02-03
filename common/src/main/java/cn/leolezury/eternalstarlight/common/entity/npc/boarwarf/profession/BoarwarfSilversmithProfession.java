package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.world.entity.npc.VillagerTrades;

public class BoarwarfSilversmithProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(ItemInit.AETHERSENT_INGOT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 10),
                new BuyItemTrade(ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 3, 1, 10),
                new BuyItemTrade(ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 3, 1, 10),
                new BuyItemTrade(ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 15, 1, 10),
                new BuyItemTrade(ItemInit.TENACIOUS_PETAL.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 18, 1, 10),
                new SellItemTrade(ItemInit.SHATTERED_SWORD.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 20, 1, 1),
                new SellItemTrade(ItemInit.ENERGY_SWORD.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 20, 1, 1)
        };
    }
}
