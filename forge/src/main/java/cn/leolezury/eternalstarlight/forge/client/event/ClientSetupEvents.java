package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;
import net.neoforged.neoforge.client.model.BakedModelWrapper;

import java.util.Map;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ClientSetupHandlers.clientSetup();
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
        Map<ResourceLocation, BakedModel> models = event.getModels();
        ClientSetupHandlers.modifyBakingResult(models);

        // Modify Inventory Icon
        for (ResourceLocation id : models.keySet()) {
            if (ClientSetupHandlers.ITEMS_WITH_INV_ICON.containsKey(id)) {
                BakedModel bakedModel = models.get(id);
                BakedModelWrapper<?> wrapper = new BakedModelWrapper<>(bakedModel) {
                    @Override
                    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
                        if (transformType == ItemDisplayContext.GUI) {
                            return models.get(ClientSetupHandlers.ITEMS_WITH_INV_ICON.get(id));
                        }
                        return super.applyTransform(transformType, poseStack, applyLeftHandTransform);
                    }
                };
                models.put(id, wrapper);
            }
        }
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
    public static void onRegisterOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), new ResourceLocation(EternalStarlight.MOD_ID, "ether_erosion"), (gui, guiGraphics, partialTicks, width, height) -> {
            ClientHandlers.renderEtherErosion(gui, guiGraphics);
        });
        event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(), new ResourceLocation(EternalStarlight.MOD_ID, "ether_armor"), (gui, guiGraphics, partialTicks, width, height) -> {
            if (gui.shouldDrawSurvivalElements()) {
                ClientHandlers.renderEtherArmor(guiGraphics, width, height);
            }
        });
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), new ResourceLocation(EternalStarlight.MOD_ID, "orb_of_prophecy_use"), (gui, guiGraphics, partialTicks, width, height) -> {
            ClientHandlers.renderOrbOfProphecyUse(gui, guiGraphics);
        });
    }
}
