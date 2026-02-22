package cn.leolezury.eternalstarlight.neoforge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.neoforge.platform.ESNeoPlatform;
import cn.leolezury.eternalstarlight.neoforge.registry.ESFluidTypes;
import cn.leolezury.eternalstarlight.neoforge.registry.ESRegistryRemapper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(EternalStarlight.ID)
public class ESNeoEntrypoint {
	public ESNeoEntrypoint(IEventBus modBus) {
		ESFluidTypes.loadClass();
		EternalStarlight.init();
		modBus.addListener(this::onRegister);
		modBus.addListener(this::onNewRegistry);
		for (DeferredRegister<?> register : ESNeoPlatform.REGISTERS) {
			register.register(modBus);
		}
		ESNeoPlatform.ATTACHMENT_TYPE_REGISTER.register(modBus);
		ESRegistryRemapper.addAliases();
	}

	private void onRegister(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.CHUNK_GENERATOR)) {
			ESCommonSetupHandler.registerChunkGenerator();
		} else if (event.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
			ESCommonSetupHandler.registerBiomeSource();
		}
	}

	private void onNewRegistry(NewRegistryEvent event) {
		for (Registry<?> registry : ESNeoPlatform.NEW_REGISTRIES) {
			event.register(registry);
		}
	}
}
