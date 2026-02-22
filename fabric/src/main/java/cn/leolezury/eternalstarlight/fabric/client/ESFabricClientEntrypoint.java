package cn.leolezury.eternalstarlight.fabric.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.tooltip.ClientGalacticQuiverTooltip;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientSetupHandler;
import cn.leolezury.eternalstarlight.common.client.renderer.world.ESSkyRenderer;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.item.component.LargeItemStackList;
import cn.leolezury.eternalstarlight.common.item.tooltip.GalacticQuiverTooltipComponent;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.fabric.client.renderer.ESFabricItemStackRenderer;
import cn.leolezury.eternalstarlight.fabric.client.renderer.armor.AlchemistArmorRenderer;
import cn.leolezury.eternalstarlight.fabric.client.renderer.armor.StarlitDiamondArmorRenderer;
import cn.leolezury.eternalstarlight.fabric.client.renderer.armor.ThermalSpringstoneArmorRenderer;
import cn.leolezury.eternalstarlight.fabric.client.renderer.armor.UnrealiumArmorRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class ESFabricClientEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ESClientSetupHandler.clientSetup();
		ESCommonSetupHandler.registerPackets(new ESCommonSetupHandler.NetworkRegisterStrategy() {
			@Override
			public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
				ClientPlayNetworking.registerGlobalReceiver(packetInfo.type(), (payload, context) -> packetInfo.handler().handle(payload, context.player()));
			}
		});
		ESClientSetupHandler.registerBlockColors(ColorProviderRegistry.BLOCK::register);
		ESClientSetupHandler.registerExtraBakedModels(ESModelLoadingPlugin.MODELS::add);
		ModelLoadingPlugin.register(new ESModelLoadingPlugin());
		ESClientSetupHandler.registerItemColors(ColorProviderRegistry.ITEM::register);
		ESClientSetupHandler.registerShaders((location, format, loaded) -> CoreShaderRegistrationCallback.EVENT.register(context -> context.register(location, format, loaded)));
		ESClientSetupHandler.ParticleProviderRegisterStrategy particleProviderRegisterStrategy = new ESClientSetupHandler.ParticleProviderRegisterStrategy() {
			@Override
			public <T extends ParticleOptions> void register(ParticleType<T> particle, ParticleEngine.SpriteParticleRegistration<T> provider) {
				ParticleFactoryRegistry.getInstance().register(particle, provider::create);
			}
		};
		ESClientSetupHandler.registerParticleProviders(particleProviderRegisterStrategy);

		ESClientSetupHandler.registerEntityRenderers(EntityRendererRegistry::register);
		ESClientSetupHandler.registerBlockEntityRenderers(BlockEntityRenderers::register);
		ESClientSetupHandler.registerLayers((layerLocation, supplier) -> EntityModelLayerRegistry.registerModelLayer(layerLocation, supplier::get));
		ESClientSetupHandler.registerMenuScreens(MenuScreens::register);
		ESClientSetupHandler.addClientReloadListeners(listener -> ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener((IdentifiableResourceReloadListener) listener));
		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, livingEntityRenderer, registrationHelper, context) -> ESClientSetupHandler.onRenderLayerAttachment(entityType, livingEntityRenderer, context));
		WorldRenderEvents.AFTER_ENTITIES.register(context -> ESClientHandler.onAfterRenderEntities(context.consumers(), context.matrixStack(), context.tickCounter().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally())));
		TooltipComponentCallback.EVENT.register(tooltipComponent -> {
			if (tooltipComponent instanceof GalacticQuiverTooltipComponent(LargeItemStackList contents)) {
				return new ClientGalacticQuiverTooltip(contents);
			}
			return null;
		});

		for (Supplier<? extends Block> blockSupplier : ESClientSetupHandler.BLOCKS_CUTOUT_MIPPED) {
			BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), RenderType.cutoutMipped());
		}
		for (Supplier<? extends Block> blockSupplier : ESClientSetupHandler.BLOCKS_CUTOUT) {
			BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), RenderType.cutout());
		}
		for (Supplier<? extends Block> blockSupplier : ESClientSetupHandler.BLOCKS_TRANSLUCENT) {
			BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), RenderType.translucent());
		}

		for (Map.Entry<ResourceLocation, KeyMapping> mapping : ESClientSetupHandler.KEY_MAPPINGS.entrySet()) {
			KeyBindingHelper.registerKeyBinding(mapping.getValue());
		}

		DimensionRenderingRegistry.registerDimensionEffects(EternalStarlight.id("special_effect"), ESClientPlatform.INSTANCE.getDimEffect());
		DimensionRenderingRegistry.registerSkyRenderer(ESDimensions.STARLIGHT_KEY, context -> ESSkyRenderer.renderSky(context.world(), context.positionMatrix(), context.projectionMatrix(), context.tickCounter().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()), context.camera(), () -> {
		}));

		FluidRenderHandlerRegistry.INSTANCE.register(ESFluids.ETHER_STILL.get(), ESFluids.ETHER_FLOWING.get(), new SimpleFluidRenderHandler(
			EternalStarlight.id("block/ether"),
			EternalStarlight.id("block/ether_flow")
		) {
			@Override
			public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return ESClientHandler.getEtherTint(view, pos);
			}
		});

		BuiltinItemRendererRegistry.INSTANCE.register(ESItems.GLACITE_SHIELD.get(), new ESFabricItemStackRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(ESItems.FLOWGLAZE_SHIELD.get(), new ESFabricItemStackRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(ESItems.MALARITE_SPEAR.get(), new ESFabricItemStackRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(ESItems.PUNGENCY_FRUIT_SPEAR.get(), new ESFabricItemStackRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(ESItems.CRESCENT_SPEAR.get(), new ESFabricItemStackRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(ESItems.LOOT_CHEST.get(), new ESFabricItemStackRenderer());

		ArmorRenderer.register(ThermalSpringstoneArmorRenderer.INSTANCE, ESItems.THERMAL_SPRINGSTONE_HELMET.get());
		ArmorRenderer.register(ThermalSpringstoneArmorRenderer.INSTANCE, ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get());
		ArmorRenderer.register(ThermalSpringstoneArmorRenderer.INSTANCE, ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get());
		ArmorRenderer.register(ThermalSpringstoneArmorRenderer.INSTANCE, ESItems.THERMAL_SPRINGSTONE_BOOTS.get());
		ArmorRenderer.register(AlchemistArmorRenderer.INSTANCE, ESItems.ALCHEMIST_MASK.get());
		ArmorRenderer.register(AlchemistArmorRenderer.INSTANCE, ESItems.ALCHEMIST_ROBE.get());
		ArmorRenderer.register(StarlitDiamondArmorRenderer.INSTANCE, ESItems.STARLIT_DIAMOND_HELMET.get());
		ArmorRenderer.register(StarlitDiamondArmorRenderer.INSTANCE, ESItems.STARLIT_DIAMOND_CHESTPLATE.get());
		ArmorRenderer.register(StarlitDiamondArmorRenderer.INSTANCE, ESItems.STARLIT_DIAMOND_LEGGINGS.get());
		ArmorRenderer.register(StarlitDiamondArmorRenderer.INSTANCE, ESItems.STARLIT_DIAMOND_BOOTS.get());
		ArmorRenderer.register(UnrealiumArmorRenderer.INSTANCE, ESItems.UNREALIUM_HELMET.get());
		ArmorRenderer.register(UnrealiumArmorRenderer.INSTANCE, ESItems.UNREALIUM_CHESTPLATE.get());
		ArmorRenderer.register(UnrealiumArmorRenderer.INSTANCE, ESItems.UNREALIUM_LEGGINGS.get());
		ArmorRenderer.register(UnrealiumArmorRenderer.INSTANCE, ESItems.UNREALIUM_BOOTS.get());

		ClientTickEvents.END_CLIENT_TICK.register(client -> ESClientHandler.onClientTick());
	}
}
