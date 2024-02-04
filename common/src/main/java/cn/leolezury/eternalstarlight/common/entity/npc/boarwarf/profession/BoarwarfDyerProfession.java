package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.DyedArmorTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfDyerProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(Items.LEATHER, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
                new BuyItemTrade(Items.RABBIT_HIDE, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 10, 40),
                new BuyItemTrade(Items.WHITE_WOOL, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 6, 20),
                new BuyItemTrade(ItemInit.WHITE_YETI_FUR.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 20),
                new SellItemTrade(ItemInit.WHITE_YETI_FUR.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 4, 50),
                new DyedArmorTrade(Items.LEATHER_HELMET, ItemInit.STARLIGHT_SILVER_COIN.get(), 3),
                new DyedArmorTrade(Items.LEATHER_CHESTPLATE, ItemInit.STARLIGHT_SILVER_COIN.get(), 4),
                new DyedArmorTrade(Items.LEATHER_LEGGINGS, ItemInit.STARLIGHT_SILVER_COIN.get(), 3),
                new DyedArmorTrade(Items.LEATHER_BOOTS, ItemInit.STARLIGHT_SILVER_COIN.get(), 3)
        };
    }
}
