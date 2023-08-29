package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.client.model.item.GlowingBakedModel;
import cn.leolezury.eternalstarlight.common.client.model.item.wrapper.ESBakedModelWrapper;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;


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
        Map<ResourceLocation, BakedModel> models = event.getModels();
        ClientSetupHandlers.modifyBakingResult(models);

        // Modify Inventory Icon
        for (ResourceLocation id : models.keySet()) {
            if (ClientSetupHandlers.itemModelsInInventoryMap.containsKey(id)) {
                BakedModel bakedModel = models.get(id);
                BakedModelWrapper<?> wrapper = new BakedModelWrapper<>(bakedModel) {
                    @Override
                    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
                        if (transformType == ItemDisplayContext.GUI) {
                            return models.get(ClientSetupHandlers.itemModelsInInventoryMap.get(id));
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
}
