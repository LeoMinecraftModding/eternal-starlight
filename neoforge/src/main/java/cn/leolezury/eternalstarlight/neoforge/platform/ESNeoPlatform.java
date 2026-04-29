package cn.leolezury.eternalstarlight.neoforge.platform;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.TearBombBlock;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.item.armor.AlchemistArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.UnrealiumArmorItem;
import cn.leolezury.eternalstarlight.common.item.combat.CrescentSpearItem;
import cn.leolezury.eternalstarlight.common.item.combat.HammerItem;
import cn.leolezury.eternalstarlight.common.item.combat.ScytheItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.EntityDataAttachment;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.registry.ESCreativeModeTabs;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.neoforge.block.NeoTearBombBlock;
import cn.leolezury.eternalstarlight.neoforge.block.fluid.NeoEtherFluid;
import cn.leolezury.eternalstarlight.neoforge.item.armor.NeoAlchemistArmorItem;
import cn.leolezury.eternalstarlight.neoforge.item.armor.NeoStarlitDiamondArmorItem;
import cn.leolezury.eternalstarlight.neoforge.item.armor.NeoThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.neoforge.item.armor.NeoUnrealiumArmorItem;
import cn.leolezury.eternalstarlight.neoforge.item.combat.NeoCrescentSpearItem;
import cn.leolezury.eternalstarlight.neoforge.item.combat.NeoHammerItem;
import cn.leolezury.eternalstarlight.neoforge.item.combat.NeoPetalScytheItem;
import cn.leolezury.eternalstarlight.neoforge.item.combat.NeoScytheItem;
import cn.leolezury.eternalstarlight.neoforge.network.ESNeoNetworkHandler;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ESNeoPlatform implements ESPlatform {
	public static final List<DeferredRegister<?>> REGISTERS = new ArrayList<>();
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EternalStarlight.ID);
	public static final List<Registry<?>> NEW_REGISTRIES = new ArrayList<>();

	@Override
	public Loader getLoader() {
		return Loader.NEOFORGE;
	}

	@Override
	public boolean isPhysicalClient() {
		return FMLLoader.getDist() == Dist.CLIENT;
	}

	@Override
	public Path getConfigDir() {
		return FMLPaths.CONFIGDIR.get();
	}

	@Override
	public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
		NeoForgeRegistrationProvider<T> provider = new NeoForgeRegistrationProvider<>(key, null, namespace);
		if (!REGISTERS.contains(provider.deferredRegister)) {
			REGISTERS.add(provider.deferredRegister);
		}
		return provider;
	}

	@Override
	public <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
		RegistryBuilder<T> builder = new RegistryBuilder<>(key);
		builder.sync(true);
		Registry<T> registry = builder.create();
		NeoForgeRegistrationProvider<T> provider = new NeoForgeRegistrationProvider<>(key, registry, namespace);
		if (!REGISTERS.contains(provider.deferredRegister)) {
			REGISTERS.add(provider.deferredRegister);
		}
		NEW_REGISTRIES.add(registry);
		return provider;
	}

	static class NeoForgeRegistrationProvider<T> implements RegistrationProvider<T> {
		private final ResourceKey<? extends Registry<T>> key;
		private final Registry<T> registry;
		private final DeferredRegister<T> deferredRegister;
		private final String namespace;

		NeoForgeRegistrationProvider(ResourceKey<? extends Registry<T>> key, Registry<T> registry, String namespace) {
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
			return new RegistryObject<>() {
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
		Optional<? extends ModContainer> optional = ModList.get().getModContainerById(key.location().getNamespace());
		if (optional.isPresent()) {
			IEventBus bus = optional.get().getEventBus();
			if (bus != null) {
				if (networkCodec != null) {
					bus.addListener(DataPackRegistryEvent.NewRegistry.class, (event) -> event.dataPackRegistry(key, codec, networkCodec));
				} else {
					bus.addListener(DataPackRegistryEvent.NewRegistry.class, (event) -> event.dataPackRegistry(key, codec));
				}
			}
		}
	}

	@Override
	public SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends Mob>> defaultType, int backgroundColor, int highlightColor, Item.Properties properties) {
		return new DeferredSpawnEggItem(defaultType, backgroundColor, highlightColor, properties);
	}

	@Override
	public ScytheItem createScythe(Tier tier, boolean canTill, Item.Properties properties) {
		return new NeoScytheItem(tier, canTill, properties);
	}

	@Override
	public ScytheItem createPetalScythe(Tier tier, boolean canTill, Item.Properties properties) {
		return new NeoPetalScytheItem(tier, canTill, properties);
	}

	@Override
	public HammerItem createHammer(Tier tier, Supplier<ParticleOptions> smashParticle, Holder<SoundEvent> smashSound, Item.Properties properties) {
		return new NeoHammerItem(tier, smashParticle, smashSound, properties);
	}

	@Override
	public CrescentSpearItem createCrescentSpear(Item.Properties properties) {
		return new NeoCrescentSpearItem(properties);
	}

	@Override
	public ThermalSpringstoneArmorItem createThermalSpringstoneArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new NeoThermalSpringstoneArmorItem(material, type, properties);
	}

	@Override
	public AlchemistArmorItem createAlchemistArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new NeoAlchemistArmorItem(material, type, properties);
	}

	@Override
	public ArmorItem createStarlitDiamondArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new NeoStarlitDiamondArmorItem(material, type, properties);
	}

	@Override
	public UnrealiumArmorItem createUnrealiumArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new NeoUnrealiumArmorItem(material, type, properties);
	}

	@Override
	public CreativeModeTab getESTab() {
		return CreativeModeTab.builder().icon(() -> new ItemStack(ESItems.STARLIGHT_FLOWER.get())).title(Component.translatable("name.eternal_starlight")).displayItems((displayParameters, output) -> {
			for (ResourceKey<Item> entry : ESItems.REGISTERED_ITEMS) {
				Item item = BuiltInRegistries.ITEM.get(entry);
				if (item != null) {
					output.accept(item);
					if (item == ESItems.STARLIT_PAINTING.get()) {
						displayParameters.holders().lookup(Registries.PAINTING_VARIANT).ifPresent((registryLookup) -> ESCreativeModeTabs.generatePresetPaintings(output, displayParameters.holders(), registryLookup, holder -> holder.is(ESTags.PaintingVariants.PLACEABLE)));
					}
				}
			}
		}).build();
	}

	@Override
	public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
		FlowerPotBlock block = new FlowerPotBlock(pot, flower, properties);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BuiltInRegistries.BLOCK.getKey(flower.get()), () -> block);
		return block;
	}

	@Override
	public EtherFluid.Still createEtherFluid() {
		return new NeoEtherFluid.Still();
	}

	@Override
	public EtherFluid.Flowing createFlowingEtherFluid() {
		return new NeoEtherFluid.Flowing();
	}

	@Override
	public TearBombBlock createTearBombBlock(BlockBehaviour.Properties properties) {
		return new NeoTearBombBlock(properties);
	}

	@Override
	public <T> EntityDataAttachment<T> registerDataAttachment(String id, Supplier<T> defaultValue, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, BiPredicate<T, T> shouldSync, boolean copyOnDeath) {
		AttachmentType.Builder<T> builder = AttachmentType.builder(h -> defaultValue.get());
		if (codec != null) {
			builder = builder.serialize(codec);
		}
		// "Received synced attachments from unknown entity"
		/*if (streamCodec != null) {
			builder = builder.sync((target, player) -> target instanceof Entity entity ? entity.isAlive() : target != null, streamCodec);
		}*/
		if (copyOnDeath) {
			builder = builder.copyOnDeath();
		}
		DeferredHolder<AttachmentType<?>, AttachmentType<T>> holder = ATTACHMENT_TYPE_REGISTER.register(id, builder::build);
		return new EntityDataAttachment<>() {
			@Override
			public ResourceLocation id() {
				return EternalStarlight.id(id);
			}

			@Override
			public boolean hasData(Entity entity) {
				return entity.hasData(holder);
			}

			@Override
			public T getData(Entity entity) {
				return entity.getData(holder);
			}

			@Override
			public Optional<T> getExistingData(Entity entity) {
				return entity.getExistingData(holder);
			}

			@Override
			public @Nullable T setDataUnsynced(Entity entity, T data) {
				return entity.setData(holder, data);
			}

			@Override
			public @Nullable T removeDataUnsynced(Entity entity) {
				return entity.removeData(holder);
			}

			@Override
			public @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return streamCodec;
			}

			@Override
			public boolean shouldSync(T v1, T v2) {
				return shouldSync.test(v1, v2);
			}
		};
	}

	@Override
	public boolean canEntityGrief(Level level, Entity entity) {
		return EventHooks.canEntityGrief(level, entity);
	}

	@Override
	public boolean postEntityDestroyBlockEvent(Level level, BlockPos pos, Entity entity) {
		return entity instanceof LivingEntity living ? CommonHooks.canEntityDestroy(level, pos, living) : canEntityGrief(level, entity);
	}

	@Override
	public boolean postTravelToDimensionEvent(Entity entity, ResourceKey<Level> dimension) {
		return CommonHooks.onTravelToDimension(entity, dimension);
	}

	@Override
	public boolean postTeleportEvent(Entity entity, Vec3 destPos) {
		EntityTeleportEvent event = new EntityTeleportEvent(entity, destPos.x, destPos.y, destPos.z);
		NeoForge.EVENT_BUS.post(event);
		return !event.isCanceled();
	}

	@Override
	public boolean isShears(ItemStack stack) {
		return stack.is(Tags.Items.TOOLS_SHEAR);
	}

	@Override
	public boolean isShield(ItemStack stack) {
		return stack.canPerformAction(ItemAbilities.SHIELD_BLOCK);
	}

	@Override
	public boolean canScrape(ItemStack stack) {
		return stack.canPerformAction(ItemAbilities.AXE_SCRAPE);
	}

	@Override
	public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
		return toolModifiedState == null ? null : Pair.of(ctx -> true, ScytheItem.changeIntoState(toolModifiedState));
	}

	@Override
	public int getBurnTime(ItemStack stack, RecipeType<?> type) {
		return stack.getBurnTime(type);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return stack.hasCraftingRemainingItem();
	}

	@Override
	public Optional<ItemStack> getCraftingRemainingItem(ItemStack stack) {
		return Optional.of(stack.getCraftingRemainingItem());
	}

	@Override
	public boolean canBoatInFluid(Boat boat, FluidState state) {
		return boat.canBoatInFluid(state);
	}

	@Override
	public Entity getPartEntityParent(Entity entity) {
		if (entity instanceof PartEntity<?> part) {
			return part.getParent();
		}
		return null;
	}

	@Override
	public void sendToClient(ServerPlayer player, CustomPacketPayload packet) {
		ESNeoNetworkHandler.sendToClient(player, packet);
	}
}
