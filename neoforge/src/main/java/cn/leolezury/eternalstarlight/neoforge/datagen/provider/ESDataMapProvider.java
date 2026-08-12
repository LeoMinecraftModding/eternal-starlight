package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ESDataMapProvider extends DataMapProvider {
	public ESDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		ESCommonSetupHandler.COMPOSTABLES.get().object2FloatEntrySet().forEach(entry -> builder(NeoForgeDataMaps.COMPOSTABLES).add(entry.getKey().asHolder(), new Compostable(entry.getFloatValue()), false));
	}
}
