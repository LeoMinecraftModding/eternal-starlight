package cn.leolezury.eternalstarlight.neoforge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.neoforge.platform.NeoForgePlatform;
import cn.leolezury.eternalstarlight.neoforge.registry.ESFluidTypes;
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
		for (DeferredRegister<?> register : NeoForgePlatform.REGISTERS) {
			register.register(modBus);
		}
	}

	private void onRegister(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.CHUNK_GENERATOR)) {
			CommonSetupHandlers.registerChunkGenerator();
		} else if (event.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
			CommonSetupHandlers.registerBiomeSource();
		}
	}

	private void onNewRegistry(NewRegistryEvent event) {
		for (Registry<?> registry : NeoForgePlatform.NEW_REGISTRIES) {
			event.register(registry);
		}
	}
}
