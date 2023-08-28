package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.trade;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class SellForCurrencyTrade implements VillagerTrades.ItemListing {

    private final ItemStack sell;
    private final int currencyCount;
    private final int sellCount;
    private final int maxUses;

    public SellForCurrencyTrade(Item sell, int currencyCount, int sellCount, int maxUses) {
        this(new ItemStack(sell), currencyCount, sellCount, maxUses);
    }

    public SellForCurrencyTrade(ItemStack sell, int currencyCount, int sellCount, int maxUses) {
        this.sell = sell;
        this.currencyCount = currencyCount;
        this.sellCount = sellCount;
        this.maxUses = maxUses;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, RandomSource random) {
        return new MerchantOffer(new ItemStack(this.sell.getItem(), this.sellCount), new ItemStack(Items.EMERALD, this.currencyCount), this.maxUses, 0, 0);
    }
}
