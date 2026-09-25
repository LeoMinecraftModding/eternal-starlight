package cn.leolezury.eternalstarlight.neoforge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.neoforge.platform.ESNeoPlatform;
import cn.leolezury.eternalstarlight.neoforge.registry.ESCommandArgumentTypes;
import cn.leolezury.eternalstarlight.neoforge.registry.ESFluidTypes;
import cn.leolezury.eternalstarlight.neoforge.registry.ESRegistryRemapper;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(EternalStarlight.ID)
public class ESNeoEntrypoint {
	public ESNeoEntrypoint(IEventBus modBus) {
		ESFluidTypes.loadClass();
		ESCommandArgumentTypes.loadClass();
		EternalStarlight.init();
		modBus.addListener(this::onNewRegistry);
		for (DeferredRegister<?> register : ESNeoPlatform.REGISTERS) {
			register.register(modBus);
		}
		ESNeoPlatform.ATTACHMENT_TYPE_REGISTER.register(modBus);
		ESRegistryRemapper.addAliases();
	}

	private void onNewRegistry(NewRegistryEvent event) {
		for (Registry<?> registry : ESNeoPlatform.NEW_REGISTRIES) {
			event.register(registry);
		}
	}
}
