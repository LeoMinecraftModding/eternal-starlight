package cn.leolezury.eternalstarlight.forge.datagen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.forge.datagen.provider.ESAdvancementProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.ESDataProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.ESLootProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.ESRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new ESLootProvider(output));
        generator.addProvider(event.includeServer(), new ESRecipeProvider(output));
        generator.addProvider(event.includeServer(), new ESAdvancementProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new ESDataProvider(output, lookupProvider));
    }
}
