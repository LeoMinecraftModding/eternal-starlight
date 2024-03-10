package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.DyedArmorTrade;
import cn.leolezury.eternalstarlight.common.entity.living.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfDyerProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(Items.LEATHER, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
                new BuyItemTrade(Items.RABBIT_HIDE, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 10, 40),
                new BuyItemTrade(Items.WHITE_WOOL, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 6, 20),
                new BuyItemTrade(ESItems.WHITE_YETI_FUR.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
                new SellItemTrade(ESItems.WHITE_YETI_FUR.get(), ESItems.STARLIGHT_SILVER_COIN.get(), 1, 4, 50),
                new DyedArmorTrade(Items.LEATHER_HELMET, ESItems.STARLIGHT_SILVER_COIN.get(), 3),
                new DyedArmorTrade(Items.LEATHER_CHESTPLATE, ESItems.STARLIGHT_SILVER_COIN.get(), 4),
                new DyedArmorTrade(Items.LEATHER_LEGGINGS, ESItems.STARLIGHT_SILVER_COIN.get(), 3),
                new DyedArmorTrade(Items.LEATHER_BOOTS, ESItems.STARLIGHT_SILVER_COIN.get(), 3)
        };
    }
}
