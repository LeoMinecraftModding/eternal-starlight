package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ESItems;
import net.minecraft.world.entity.npc.VillagerTrades;

public class BoarwarfSilversmithProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(ESItems.AETHERSENT_INGOT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new BuyItemTrade(ESItems.SWAMP_SILVER_INGOT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 3, 1, 10),
                new BuyItemTrade(ESItems.THERMAL_SPRINGSTONE_INGOT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 3, 1, 10),
                new BuyItemTrade(ESItems.GOLEM_STEEL_INGOT.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 15, 1, 10),
                new BuyItemTrade(ESItems.TENACIOUS_PETAL.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 18, 1, 10),
                new SellItemTrade(ESItems.SWAMP_SILVER_SWORD.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(ESItems.SWAMP_SILVER_AXE.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(ESItems.SWAMP_SILVER_PICKAXE.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(ESItems.SWAMP_SILVER_SICKLE.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 2, 1, 10),
                new SellItemTrade(ESItems.SHATTERED_SWORD.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 20, 1, 1),
                new SellItemTrade(ESItems.ENERGY_SWORD.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 20, 1, 1)
        };
    }
}
