package cn.leolezury.eternalstarlight.forge.datagen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.forge.datagen.provider.*;
import cn.leolezury.eternalstarlight.forge.datagen.provider.custom.ESGeyserSmokingProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ESBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new ESItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new ESAtlasProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeClient(), new ESParticleDescriptionProvider(output, helper));
        generator.addProvider(event.includeClient(), new ESSoundProvider(output, helper));

        ESBlockTagsProvider blockTagsProvider = new ESBlockTagsProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ESItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new ESEntityTypeTagsProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new ESFluidTagsProvider(output, lookupProvider, helper));

        DatapackBuiltinEntriesProvider dataProvider = new ESDataProvider(output, lookupProvider);
        CompletableFuture<HolderLookup.Provider> lookup = dataProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), dataProvider);
        generator.addProvider(event.includeServer(), new ESDamageTypeTagsProvider(output, lookup, helper));
        generator.addProvider(event.includeServer(), new ESBiomeTagsProvider(output, lookup, helper));

        generator.addProvider(event.includeServer(), new ESLootProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ESRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ESAdvancementProvider(output, lookupProvider, helper));

        // custom
        generator.addProvider(event.includeServer(), new ESGeyserSmokingProvider(output));
    }
}
