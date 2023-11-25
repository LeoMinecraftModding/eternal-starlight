package cn.leolezury.eternalstarlight.forge.datagen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.forge.datagen.provider.*;
import cn.leolezury.eternalstarlight.forge.datagen.provider.tags.ESBlockTagsProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.tags.ESItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ESBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new ESItemModelProvider(output, helper));

        ESBlockTagsProvider blockTagsProvider = new ESBlockTagsProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ESItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new ESLootProvider(output));
        generator.addProvider(event.includeServer(), new ESRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ESAdvancementProvider(output, lookupProvider, helper));

        generator.addProvider(event.includeServer(), new ESDataProvider(output, lookupProvider));
    }
}
