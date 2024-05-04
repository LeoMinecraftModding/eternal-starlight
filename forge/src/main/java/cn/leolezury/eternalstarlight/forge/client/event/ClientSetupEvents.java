package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.io.IOException;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ClientSetupHandlers.clientSetup();
    }

    @SubscribeEvent
    public static void onRegisterDimEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(EternalStarlight.id("special_effect"), ESPlatform.INSTANCE.getDimEffect());
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
        Map<ResourceLocation, BakedModel> models = event.getModels();
        ClientSetupHandlers.modifyBakingResult(models);
    }

    @SubscribeEvent
    public static void onRegisterExtraModels(ModelEvent.RegisterAdditional event) {
        ClientSetupHandlers.registerExtraBakedModels(event::register);
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

    @SubscribeEvent
    public static void onRegisterShader(RegisterShadersEvent event) {
        ClientSetupHandlers.registerShaders((location, format, loaded) -> {
            try {
                event.registerShader(new ShaderInstance(event.getResourceProvider(), location, format), loaded);
            } catch (IOException e) {
                EternalStarlight.LOGGER.error("Cannot register shader: {}", location);
            }
        });
    }

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("spell_crosshair"), (graphics, partialTicks) -> ClientHandlers.renderSpellCrosshair(graphics, graphics.guiWidth(), graphics.guiHeight()));
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("ether_erosion"), (graphics, partialTicks) -> ClientHandlers.renderEtherErosion(graphics));
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, EternalStarlight.id("ether_armor"), (graphics, partialTicks) -> {
            if (Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
                ClientHandlers.renderEtherArmor(graphics, graphics.guiWidth(), graphics.guiHeight());
            }
        });
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("orb_of_prophecy_use"), (graphics, partialTicks) -> ClientHandlers.renderOrbOfProphecyUse(graphics));
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("dream_catcher"), (graphics, partialTicks) -> ClientHandlers.renderDreamCatcher(graphics));
    }
}
