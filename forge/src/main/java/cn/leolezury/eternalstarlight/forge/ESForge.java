package cn.leolezury.eternalstarlight.forge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

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
