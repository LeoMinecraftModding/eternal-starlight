package cn.leolezury.eternalstarlight.fabric.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.fabric.network.FabricNetworkHandler;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;

// TODO: Register Block Render Types (cutout_mipped)
@Environment(EnvType.CLIENT)
public class ESFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricNetworkHandler.init(true);
        ClientSetupHandlers.clientSetup();
        ClientSetupHandlers.clientWoodSetup();
        ClientSetupHandlers.registerBlockColors(ColorProviderRegistry.BLOCK::register);
        ClientSetupHandlers.registerItemColors(ColorProviderRegistry.ITEM::register);

        ClientSetupHandlers.ParticleProviderRegisterStrategy particleProviderRegisterStrategy = new ClientSetupHandlers.ParticleProviderRegisterStrategy() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> particle, ParticleEngine.SpriteParticleRegistration<T> provider) {
                ParticleFactoryRegistry.getInstance().register(particle, provider::create);
            }
        };
        ClientSetupHandlers.registerParticleProviders(particleProviderRegisterStrategy);

        ClientSetupHandlers.registerEntityRenderers(EntityRendererRegistry::register);
        ClientSetupHandlers.registerLayers((layerLocation, supplier) -> EntityModelLayerRegistry.registerModelLayer(layerLocation, supplier::get));

        DimensionRenderingRegistry.registerDimensionEffects(new ResourceLocation(EternalStarlight.MOD_ID, "special_effect"), ESPlatform.INSTANCE.getDimEffect());
    }
}
