package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfChefProfession extends AbstractBoarwarfProfession {
	@Override
	public VillagerTrades.ItemListing[] getTrades(Boarwarf boarwarf) {
		return new VillagerTrades.ItemListing[]{
			ESEntityUtil.simpleTrade(Items.WHEAT_SEEDS, 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.BEETROOT_SEEDS, 8, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.MELON_SEEDS, 9, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 20),
			ESEntityUtil.simpleTrade(Items.WHEAT, 3, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 10),
			ESEntityUtil.simpleTrade(Items.BEETROOT, 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 10),
			ESEntityUtil.simpleTrade(Items.MELON_SLICE, 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 10),
			ESEntityUtil.simpleTrade(ESItems.LUNAR_BERRIES.get(), 5, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 50),
			ESEntityUtil.simpleTrade(ESItems.ABYSSAL_FRUIT.get(), 4, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 50),
			ESEntityUtil.simpleTrade(ESItems.AURORA_DEER_STEAK.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 30),
			ESEntityUtil.simpleTrade(ESItems.RATLIN_MEAT.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 30),
			ESEntityUtil.simpleTrade(ESItems.SHADOW_SNAIL_MEAT.get(), 3, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 30),
			ESEntityUtil.simpleTrade(ESItems.ROOKFISH.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 30),
			ESEntityUtil.simpleTrade(ESItems.LUMINOFISH.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 30),
			ESEntityUtil.simpleTrade(ESItems.LUMINARIS.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 1, 30),
			ESEntityUtil.simpleTrade(ESItems.LUMINOFISH_BUCKET.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 30),
			ESEntityUtil.simpleTrade(ESItems.LUMINARIS_BUCKET.get(), 1, ESItems.STARLIGHT_SILVER_COIN.get(), 2, 30),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.COOKED_AURORA_DEER_STEAK.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.COOKED_RATLIN_MEAT.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, ESItems.COOKED_SHADOW_SNAIL_MEAT.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.COOKED_ROOKFISH.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 1, ESItems.ROOKFISH_SKEWER.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.COOKED_LUMINOFISH.get(), 1, 20),
			ESEntityUtil.simpleTrade(ESItems.STARLIGHT_SILVER_COIN.get(), 2, ESItems.COOKED_LUMINARIS.get(), 1, 20)
		};
	}
}
