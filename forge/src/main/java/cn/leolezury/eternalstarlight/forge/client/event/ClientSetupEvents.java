package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ClientSetupHandlers.clientSetup();
    }

    @SubscribeEvent
    public static void clientWoodSetup(FMLClientSetupEvent event) {
        ClientSetupHandlers.clientWoodSetup();
    }

    @SubscribeEvent
    public static void onRegisterDimEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(new ResourceLocation(EternalStarlight.MOD_ID, "special_effect"), ESPlatform.INSTANCE.getDimEffect());
    }

    @SubscribeEvent
    public static void onRegisterBlockColor(RegisterColorHandlersEvent.Block event) {
        ClientSetupHandlers.registerBlockColors(event::register);
    }

    @SubscribeEvent
    public static void onRegisterItemColor(RegisterColorHandlersEvent.Item event) {
        ClientSetupHandlers.registerItemColors(event::register);
    }

    @SubscribeEvent
    public static void onBakingCompleted(ModelEvent.ModifyBakingResult event) {
        ClientSetupHandlers.modifyBakingResult(event.getModels());
    }

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        ClientSetupHandlers.registerParticleProviders(event::registerSpriteSet);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        ClientSetupHandlers.registerEntityRenderers(event::registerEntityRenderer);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ClientSetupHandlers.registerLayers(event::registerLayerDefinition);
    }
}
