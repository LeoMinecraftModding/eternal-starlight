package cn.leolezury.eternalstarlight.common.entity.living.npc.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class BuyItemTrade implements VillagerTrades.ItemListing {
	private final ItemStack sell;
	private final ItemStack currency;
	private final int maxUses;

	public BuyItemTrade(Item sell, Item currency, int currencyCount, int sellCount, int maxUses) {
		this(new ItemStack(sell, sellCount), new ItemStack(currency, currencyCount), maxUses);
	}

	public BuyItemTrade(ItemStack sell, ItemStack currency, int maxUses) {
		this.sell = sell;
		this.currency = currency;
		this.maxUses = maxUses;
	}

	@Nullable
	@Override
	public MerchantOffer getOffer(Entity entity, RandomSource random) {
		return new MerchantOffer(new ItemCost(sell.getItem(), sell.getCount()), currency, this.maxUses, 0, 0);
	}
}
