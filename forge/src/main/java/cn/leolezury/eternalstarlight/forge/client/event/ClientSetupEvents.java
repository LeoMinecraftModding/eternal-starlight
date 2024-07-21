package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.client.model.armor.AlchemistArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.forge.client.renderer.ForgeItemStackRenderer;
import cn.leolezury.eternalstarlight.forge.registry.ESFluidTypes;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = EternalStarlight.ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
	@SubscribeEvent
	private static void clientSetup(FMLClientSetupEvent event) {
		ClientSetupHandlers.clientSetup();
	}

	@SubscribeEvent
	private static void onRegisterDimEffects(RegisterDimensionSpecialEffectsEvent event) {
		event.register(EternalStarlight.id("special_effect"), ESPlatform.INSTANCE.getDimEffect());
	}

	@SubscribeEvent
	private static void onRegisterBlockColor(RegisterColorHandlersEvent.Block event) {
		ClientSetupHandlers.registerBlockColors(event::register);
	}

	@SubscribeEvent
	private static void onRegisterItemColor(RegisterColorHandlersEvent.Item event) {
		ClientSetupHandlers.registerItemColors(event::register);
	}

	@SubscribeEvent
	private static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
		event.registerItem(ForgeItemStackRenderer.CLIENT_ITEM_EXTENSION, ESItems.GLACITE_SHIELD.get());
		IClientItemExtensions alchemistArmor = new IClientItemExtensions() {
			private AlchemistArmorModel<LivingEntity> model;

			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				if (model == null) {
					model = new AlchemistArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlchemistArmorModel.LAYER_LOCATION));
				}

				return model;
			}
		};
		event.registerItem(alchemistArmor, ESItems.ALCHEMIST_MASK.get());
		event.registerItem(alchemistArmor, ESItems.ALCHEMIST_ROBE.get());
		IClientItemExtensions thermalSpringstoneArmor = new IClientItemExtensions() {
			private ThermalSpringStoneArmorModel<LivingEntity> innerModel;
			private ThermalSpringStoneArmorModel<LivingEntity> outerModel;

			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				if (innerModel == null || outerModel == null) {
					innerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.INNER_LOCATION));
					outerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.OUTER_LOCATION));
				}

				if (itemStack.is(ESItems.THERMAL_SPRINGSTONE_HELMET.get()) || itemStack.is(ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get()) || itemStack.is(ESItems.THERMAL_SPRINGSTONE_BOOTS.get())) {
					return outerModel;
				} else if (itemStack.is(ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get())) {
					return innerModel;
				}

				return IClientItemExtensions.super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
			}
		};
		event.registerItem(thermalSpringstoneArmor, ESItems.THERMAL_SPRINGSTONE_HELMET.get());
		event.registerItem(thermalSpringstoneArmor, ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get());
		event.registerItem(thermalSpringstoneArmor, ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get());
		event.registerItem(thermalSpringstoneArmor, ESItems.THERMAL_SPRINGSTONE_BOOTS.get());
		event.registerItem(ForgeItemStackRenderer.CLIENT_ITEM_EXTENSION, ESItems.CRESCENT_SPEAR.get());

		event.registerFluidType(new IClientFluidTypeExtensions() {
			@Override
			public ResourceLocation getStillTexture() {
				return EternalStarlight.id("block/ether");
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return EternalStarlight.id("block/ether_flow");
			}

			@Override
			public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
				return new Vector3f(232 / 255F, 255 / 255F, 222 / 255F);
			}

			@Override
			public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
				RenderSystem.setShaderFogStart(0.0F);
				RenderSystem.setShaderFogEnd(3.0F);
			}
		}, ESFluidTypes.ETHER.get());
	}

	@SubscribeEvent
	private static void onBakingCompleted(ModelEvent.ModifyBakingResult event) {
		Map<ModelResourceLocation, BakedModel> models = event.getModels();
		ClientSetupHandlers.modifyBakingResult(models);
	}

	@SubscribeEvent
	private static void onRegisterExtraModels(ModelEvent.RegisterAdditional event) {
		ClientSetupHandlers.registerExtraBakedModels(l -> {
			ModelResourceLocation forged = ModelResourceLocation.standalone(l.id().withPrefix("item/"));
			event.register(forged);
		});
	}

	@SubscribeEvent
	private static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
		ClientSetupHandlers.registerParticleProviders(event::registerSpriteSet);
	}

	@SubscribeEvent
	private static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		ClientSetupHandlers.registerEntityRenderers(event::registerEntityRenderer);
	}

	@SubscribeEvent
	private static void onRegisterSkullModels(EntityRenderersEvent.CreateSkullModels event) {
		ClientSetupHandlers.registerSkullModels(event::registerSkullModel, event.getEntityModelSet());
	}

	@SubscribeEvent
	private static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		ClientSetupHandlers.registerLayers(event::registerLayerDefinition);
	}

	@SubscribeEvent
	private static void onRegisterShader(RegisterShadersEvent event) {
		ClientSetupHandlers.registerShaders((location, format, loaded) -> {
			try {
				event.registerShader(new ShaderInstance(event.getResourceProvider(), location, format), loaded);
			} catch (IOException e) {
				EternalStarlight.LOGGER.error("Cannot register shader: {}", location);
			}
		});
	}

	@SubscribeEvent
	private static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("spell_crosshair"), (graphics, partialTicks) -> ClientHandlers.renderSpellCrosshair(graphics, graphics.guiWidth(), graphics.guiHeight()));
		event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("ether_erosion"), (graphics, partialTicks) -> ClientHandlers.renderEtherErosion(graphics));
		event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, EternalStarlight.id("ether_armor"), (graphics, partialTicks) -> {
			if (Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
				ClientHandlers.renderEtherArmor(graphics, graphics.guiWidth(), graphics.guiHeight());
			}
		});
		event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("orb_of_prophecy_use"), (graphics, partialTicks) -> ClientHandlers.renderOrbOfProphecyUse(graphics));
		event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("dream_catcher"), (graphics, partialTicks) -> ClientHandlers.renderDreamCatcher(graphics));
		event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("current_crest"), (graphics, partialTicks) -> ClientHandlers.renderCurrentCrest(graphics));
	}

	@SubscribeEvent
	private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		for (Map.Entry<ResourceLocation, KeyMapping> mapping : ClientSetupHandlers.KEY_MAPPINGS.entrySet()) {
			event.register(mapping.getValue());
		}
	}
}
