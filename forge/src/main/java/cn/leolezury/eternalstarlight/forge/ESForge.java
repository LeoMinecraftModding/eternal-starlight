package cn.leolezury.eternalstarlight.forge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.forge.init.FluidTypeInit;
import cn.leolezury.eternalstarlight.forge.platform.ForgePlatform;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.io.IOException;

@Mod(EternalStarlight.MOD_ID)
public class ESForge {
    public ESForge(IEventBus modEventBus, Dist dist) {
        FluidTypeInit.loadClass();
        EternalStarlight.init();
        modEventBus.addListener(this::onRegister);
        for (DeferredRegister<?> register : ForgePlatform.registers) {
            register.register(modEventBus);
        }
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
