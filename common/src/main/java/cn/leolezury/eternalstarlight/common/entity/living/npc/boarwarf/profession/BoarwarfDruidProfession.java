package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public class BoarwarfDruidProfession extends AbstractBoarwarfProfession {
	@Override
	public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
		return new VillagerTrades.ItemListing[]{
			ESEntityUtil.simpleTrade(Items.GLISTERING_MELON_SLICE, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.FERMENTED_SPIDER_EYE, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.BLAZE_POWDER, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.PHANTOM_MEMBRANE, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.RABBIT_FOOT, 2, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.LUNAR_BERRIES.get(), 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 50),
			ESEntityUtil.simpleTrade(ESItems.ABYSSAL_FRUIT.get(), 4, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 50),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, Items.CAULDRON, 1, 5),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 10), potion(Potions.LONG_REGENERATION), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 8), potion(Potions.LONG_FIRE_RESISTANCE), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 8), potion(Potions.LONG_SLOW_FALLING), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 10), potion(Potions.LONG_REGENERATION), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 12), splashPotion(Potions.HEALING), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 12), splashPotion(Potions.HARMING), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 16), lingeringPotion(Potions.LONG_REGENERATION), 20),
			ESEntityUtil.simpleTrade(new ItemStack(ESItems.STARLIGHT_SILVER_COIN.get(), 30), lingeringPotion(Potions.STRONG_HEALING), 1)
		};
	}

	private static ItemStack potion(Holder<Potion> potion) {
		return PotionContents.createItemStack(Items.POTION, potion);
	}

	private static ItemStack splashPotion(Holder<Potion> potion) {
		return PotionContents.createItemStack(Items.SPLASH_POTION, potion);
	}

	private static ItemStack lingeringPotion(Holder<Potion> potion) {
		return PotionContents.createItemStack(Items.LINGERING_POTION, potion);
	}
}
