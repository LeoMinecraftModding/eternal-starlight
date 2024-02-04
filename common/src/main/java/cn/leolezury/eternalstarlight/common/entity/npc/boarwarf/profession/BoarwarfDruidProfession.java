package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.BuyItemTrade;
import cn.leolezury.eternalstarlight.common.entity.npc.trade.SellItemTrade;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class BoarwarfDruidProfession extends AbstractBoarwarfProfession {
    @Override
    public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
        return new VillagerTrades.ItemListing[] {
                new BuyItemTrade(Items.GLISTERING_MELON_SLICE, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
                new BuyItemTrade(Items.FERMENTED_SPIDER_EYE, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
                new BuyItemTrade(Items.BLAZE_POWDER, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
                new BuyItemTrade(Items.PHANTOM_MEMBRANE, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
                new BuyItemTrade(Items.RABBIT_FOOT, ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 2, 20),
                new BuyItemTrade(ItemInit.LUNAR_BERRIES.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 5, 50),
                new BuyItemTrade(ItemInit.ABYSSAL_FRUIT.get(), ItemInit.STARLIGHT_SILVER_COIN.get(), 1, 4, 50),
                new SellItemTrade(Items.CAULDRON, ItemInit.STARLIGHT_SILVER_COIN.get(), 2, 1, 5),
                new SellItemTrade(potion(Potions.LONG_REGENERATION), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 10), 20),
                new SellItemTrade(potion(Potions.LONG_FIRE_RESISTANCE), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 8), 20),
                new SellItemTrade(potion(Potions.LONG_SLOW_FALLING), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 8), 20),
                new SellItemTrade(potion(Potions.LONG_REGENERATION), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 10), 20),
                new SellItemTrade(splashPotion(Potions.HEALING), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 12), 20),
                new SellItemTrade(splashPotion(Potions.HARMING), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 12), 20),
                new SellItemTrade(lingeringPotion(Potions.LONG_REGENERATION), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 16), 20),
                new SellItemTrade(lingeringPotion(Potions.STRONG_HEALING), new ItemStack(ItemInit.STARLIGHT_SILVER_COIN.get(), 30), 1)
        };
    }

    private static ItemStack potion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }

    private static ItemStack splashPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion);
    }

    private static ItemStack lingeringPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
    }
}
