package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class BuyForCurrencyTrade implements VillagerTrades.ItemListing {

    private final ItemStack sell;
    private final int currencyCount;
    private final int count;
    private final int maxUses;

    public BuyForCurrencyTrade(Item item, int currencyCount, int count, int maxUses) {
        this(new ItemStack(item), currencyCount, count, maxUses);
    }

    public BuyForCurrencyTrade(ItemStack stack, int currencyCount, int count, int maxUses) {
        this.sell = stack;
        this.currencyCount = currencyCount;
        this.count = count;
        this.maxUses = maxUses;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, RandomSource random) {
        return new MerchantOffer(new ItemStack(Items.EMERALD, this.currencyCount), new ItemStack(this.sell.getItem(), this.count), this.maxUses, 0, 0);
    }
}
