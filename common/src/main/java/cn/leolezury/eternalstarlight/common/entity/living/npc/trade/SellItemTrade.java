package cn.leolezury.eternalstarlight.common.entity.living.npc.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class SellItemTrade implements VillagerTrades.ItemListing {
    private final ItemStack sell;
    private final ItemStack currency;
    private final int maxUses;

    public SellItemTrade(Item item, Item currency, int currencyCount, int count, int maxUses) {
        this(new ItemStack(item, count), new ItemStack(currency, currencyCount), maxUses);
    }

    public SellItemTrade(ItemStack stack, ItemStack currency, int maxUses) {
        this.sell = stack;
        this.currency = currency;
        this.maxUses = maxUses;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, RandomSource random) {
        return new MerchantOffer(new ItemCost(currency.getItem(), currency.getCount()), sell, this.maxUses, 0, 0);
    }
}
