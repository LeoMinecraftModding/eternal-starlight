package cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.trade;

import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.BoarwarfVariants;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class BoarwarfTrades {
    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> BOARWARF_TRADES = new Int2ObjectOpenHashMap<>(ImmutableMap.of(
            BoarwarfVariants.BoarwarfProfessionVariants.TAILOR,
            new VillagerTrades.ItemListing[] {
                    new BuyForCurrencyTrade(ItemInit.SEEKING_EYE.get(), 10, 7, 10),
                    new SellForCurrencyTrade(Items.GOLD_INGOT, 8, 7, 5),
            },
            BoarwarfVariants.BoarwarfProfessionVariants.BLACKSMITH,
            new VillagerTrades.ItemListing[] {
                    new BuyForCurrencyTrade(ItemInit.SEEKING_EYE.get(), 10, 7, 10),
                    new SellForCurrencyTrade(Items.GOLD_INGOT, 8, 7, 5),
            },
            BoarwarfVariants.BoarwarfProfessionVariants.SILVERSMITH,
            new VillagerTrades.ItemListing[] {
                    new BuyForCurrencyTrade(ItemInit.SEEKING_EYE.get(), 10, 7, 10),
                    new SellForCurrencyTrade(Items.GOLD_INGOT, 8, 7, 5),
            },
            BoarwarfVariants.BoarwarfProfessionVariants.PRIEST,
            new VillagerTrades.ItemListing[] {
                    new BuyForCurrencyTrade(ItemInit.SEEKING_EYE.get(), 10, 7, 10),
                    new SellForCurrencyTrade(Items.GOLD_INGOT, 8, 7, 5),
            },
            BoarwarfVariants.BoarwarfProfessionVariants.DRUID,
            new VillagerTrades.ItemListing[] {
                    new BuyForCurrencyTrade(ItemInit.SEEKING_EYE.get(), 10, 7, 10),
                    new SellForCurrencyTrade(Items.GOLD_INGOT, 8, 7, 5),
            }
    ));
}
