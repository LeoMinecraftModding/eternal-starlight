package cn.leolezury.eternalstarlight.forge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(EternalStarlight.MOD_ID)
public class ESForge {
    public ESForge() {
        EternalStarlight.init();
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(this::onRegister);
    }

    // I guess we'll have to put it here
    // Using @SubscribeEvent in another class would make it stop working in the data generation environment
    public void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.CHUNK_GENERATOR)) {
            CommonSetupHandlers.registerChunkGenerator();
        } else if (event.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
            CommonSetupHandlers.registerBiomeSource();
        }
    }
}
