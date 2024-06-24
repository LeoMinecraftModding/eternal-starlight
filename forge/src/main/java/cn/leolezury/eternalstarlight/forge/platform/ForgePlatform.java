package cn.leolezury.eternalstarlight.forge.platform;

import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.item.armor.AlchemistArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.common.item.weapon.ScytheItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.forge.block.ForgeWaterlilyBlock;
import cn.leolezury.eternalstarlight.forge.block.fluid.ForgeEtherFluid;
import cn.leolezury.eternalstarlight.forge.client.ForgeDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.forge.item.armor.ForgeAlchemistArmorItem;
import cn.leolezury.eternalstarlight.forge.item.armor.ForgeThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.forge.item.weapon.ForgeHammerItem;
import cn.leolezury.eternalstarlight.forge.item.weapon.ForgeScytheItem;
import cn.leolezury.eternalstarlight.forge.item.weapon.ForgeShieldItem;
import cn.leolezury.eternalstarlight.forge.network.ForgeNetworkHandler;
import com.google.auto.service.AutoService;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ForgePlatform implements ESPlatform {
	public static final List<DeferredRegister<?>> registers = new ArrayList<>();
	public static final List<Registry<?>> newRegistries = new ArrayList<>();

	@Override
	public Loader getLoader() {
		return Loader.FORGE;
	}

	@Override
	public boolean isPhysicalClient() {
		return FMLLoader.getDist() == Dist.CLIENT;
	}

	@Override
	public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
		ForgeRegistrationProvider<T> provider = new ForgeRegistrationProvider<>(key, null, namespace);
		if (!registers.contains(provider.deferredRegister)) {
			registers.add(provider.deferredRegister);
		}
		return provider;
	}

	@Override
	public <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
		RegistryBuilder<T> builder = new RegistryBuilder<>(key);
		builder.sync(true);
		Registry<T> registry = builder.create();
		ForgeRegistrationProvider<T> provider = new ForgeRegistrationProvider<>(key, registry, namespace);
		if (!registers.contains(provider.deferredRegister)) {
			registers.add(provider.deferredRegister);
		}
		newRegistries.add(registry);
		return provider;
	}

	class ForgeRegistrationProvider<T> implements RegistrationProvider<T> {
		private final ResourceKey<? extends Registry<T>> key;
		private final Registry<T> registry;
		private final DeferredRegister<T> deferredRegister;
		private final String namespace;

		ForgeRegistrationProvider(ResourceKey<? extends Registry<T>> key, Registry<T> registry, String namespace) {
			this.key = key;
			this.registry = registry;
			this.deferredRegister = DeferredRegister.create(key, namespace);
			this.namespace = namespace;
		}

		@Override
		public Registry<T> registry() {
			return registry == null ? (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location()) : registry;
		}

		@Override
		public <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier) {
			ResourceLocation location = ResourceLocation.fromNamespaceAndPath(namespace, id);
			ResourceKey<I> resourceKey = (ResourceKey<I>) ResourceKey.create(key, location);
			DeferredHolder<T, I> holder = deferredRegister.register(id, supplier);
			return new RegistryObject<T, I>() {
				@Override
				public Holder<T> asHolder() {
					return holder;
				}

				@Override
				public ResourceKey<I> getResourceKey() {
					return resourceKey;
				}

				@Override
				public ResourceLocation getId() {
					return location;
				}

				@Override
				public I get() {
					return holder.value();
				}
			};
		}
	}

	@Override
	public <T> void registerDatapackRegistry(ResourceKey<Registry<T>> key, Codec<T> codec, Codec<T> networkCodec) {
		IEventBus bus = ModList.get().getModContainerById(key.location().getNamespace()).get().getEventBus();
		if (networkCodec != null) {
			bus.addListener(DataPackRegistryEvent.NewRegistry.class, (event) -> event.dataPackRegistry(key, codec, networkCodec));
		} else {
			bus.addListener(DataPackRegistryEvent.NewRegistry.class, (event) -> event.dataPackRegistry(key, codec));
		}
	}

	@Override
	public ScytheItem createScythe(Tier tier, Item.Properties properties) {
		return new ForgeScytheItem(tier, properties);
	}

	@Override
	public HammerItem createHammer(Tier tier, Item.Properties properties) {
		return new ForgeHammerItem(tier, properties);
	}

	@Override
	public ShieldItem createShield(Item.Properties properties) {
		return new ForgeShieldItem(properties);
	}

	@Override
	public ThermalSpringStoneArmorItem createThermalSpringStoneArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new ForgeThermalSpringStoneArmorItem(material, type, properties);
	}

	@Override
	public AlchemistArmorItem createAlchemistArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new ForgeAlchemistArmorItem(material, type, properties);
	}

	@Override
	public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
		FlowerPotBlock block = new FlowerPotBlock(pot, flower, properties);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BuiltInRegistries.BLOCK.getKey(flower.get()), () -> block);
		return block;
	}

	@Override
	public BushBlock createWaterlily(BlockBehaviour.Properties properties) {
		return new ForgeWaterlilyBlock(properties);
	}

	@Override
	public EtherFluid.Still createEtherFluid() {
		return new ForgeEtherFluid.Still();
	}

	@Override
	public EtherFluid.Flowing createFlowingEtherFluid() {
		return new ForgeEtherFluid.Flowing();
	}

	@Override
	public boolean postMobGriefingEvent(Level level, Entity entity) {
		return EventHooks.canEntityGrief(level, entity);
	}

	@Override
	public boolean postTravelToDimensionEvent(Entity entity, ResourceKey<Level> dimension) {
		return CommonHooks.onTravelToDimension(entity, dimension);
	}

	@Override
	public boolean isShears(ItemStack stack) {
		return stack.is(Tags.Items.TOOLS_SHEARS);
	}

	@Override
	public boolean isShield(ItemStack stack) {
		return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
	}

	@Override
	public boolean canStrip(ItemStack stack) {
		return stack.canPerformAction(ToolActions.AXE_STRIP);
	}

	@Override
	public CreativeModeTab getESTab() {
		return CreativeModeTab.builder().icon(() -> new ItemStack(ESItems.STARLIGHT_FLOWER.get())).title(Component.translatable("name.eternal_starlight")).displayItems((displayParameters, output) -> {
			for (ResourceKey<Item> entry : ESItems.REGISTERED_ITEMS) {
				Item item = BuiltInRegistries.ITEM.get(entry);
				if (item != null) {
					output.accept(item);
				}
			}
		}).build();
	}

	@Override
	public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ToolActions.HOE_TILL, false);
		return toolModifiedState == null ? null : Pair.of(ctx -> true, ScytheItem.changeIntoState(toolModifiedState));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public DimensionSpecialEffects getDimEffect() {
		ESPlatform.super.getDimEffect();
		return new ForgeDimensionSpecialEffects(160.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderBlock(BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed) {
		var model = dispatcher.getBlockModel(state);
		for (var renderType : model.getRenderTypes(state, RandomSource.create(seed), ModelData.EMPTY))
			dispatcher.getModelRenderer().tesselateBlock(level, model, state, pos, stack, multiBufferSource.getBuffer(renderType), false, RandomSource.create(), seed, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);

	}

	@Override
	public void sendToClient(ServerPlayer player, CustomPacketPayload packet) {
		ForgeNetworkHandler.sendToClient(player, packet);
	}

	@Override
	public void sendToServer(CustomPacketPayload packet) {
		ForgeNetworkHandler.sendToServer(packet);
	}
}
