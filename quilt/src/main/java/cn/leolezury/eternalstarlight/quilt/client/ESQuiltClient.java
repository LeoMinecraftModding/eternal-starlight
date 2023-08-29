package cn.leolezury.eternalstarlight.quilt.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.quilt.network.QuiltNetworkHandler;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import java.util.function.Supplier;

@ClientOnly
public class ESQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer container) {
        QuiltNetworkHandler.init(true);
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

        for (Supplier<Block> blockSupplier : ClientSetupHandlers.cutoutMippedBlocks) {
            BlockRenderLayerMap.put(RenderType.cutoutMipped(), blockSupplier.get());
        }
        for (Supplier<Block> blockSupplier : ClientSetupHandlers.cutoutBlocks) {
            BlockRenderLayerMap.put(RenderType.cutout(), blockSupplier.get());
        }
        for (Supplier<Block> blockSupplier : ClientSetupHandlers.translucentBlocks) {
            BlockRenderLayerMap.put(RenderType.translucent(), blockSupplier.get());
        }

        DimensionRenderingRegistry.registerDimensionEffects(new ResourceLocation(EternalStarlight.MOD_ID, "special_effect"), ESPlatform.INSTANCE.getDimEffect());
    }
}
