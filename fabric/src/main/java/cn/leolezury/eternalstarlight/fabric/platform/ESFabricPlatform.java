package cn.leolezury.eternalstarlight.fabric.platform;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.EntityDataAttachment;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.registry.ESCreativeModeTabs;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.fabric.resource.gatekeeper.FabricGatekeeperNameManager;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.impl.attachment.AttachmentRegistryImpl;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ESFabricPlatform implements ESPlatform {
	@Override
	public Loader getLoader() {
		return Loader.FABRIC;
	}

	@Override
	public boolean isPhysicalClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}

	@Override
	public Path getConfigDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
		return new RegistrationProvider<>() {
			private final Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());

			@Override
			public Registry<T> registry() {
				return registry;
			}

			@Override
			public <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier) {
				ResourceLocation location = ResourceLocation.fromNamespaceAndPath(namespace, id);
				ResourceKey<I> resourceKey = (ResourceKey<I>) ResourceKey.create(registry.key(), location);
				I object = Registry.register(registry, location, supplier.get());
				return new RegistryObject<>() {
					@Override
					public Holder<T> asHolder() {
						return registry.getHolderOrThrow((ResourceKey<T>) this.getResourceKey());
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
						return object;
					}
				};
			}
		};
	}

	@Override
	public <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
		MappedRegistry<T> mappedRegistry = new MappedRegistry<>(key, Lifecycle.stable(), false);
		FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.from(mappedRegistry);
		builder.attribute(RegistryAttribute.SYNCED);
		builder.buildAndRegister();
		return createRegistrationProvider(key, namespace);
	}

	@Override
	public <T> void registerDatapackRegistry(ResourceKey<Registry<T>> key, Codec<T> codec, Codec<T> networkCodec) {
		if (networkCodec == null) {
			DynamicRegistries.register(key, codec);
		} else {
			DynamicRegistries.registerSynced(key, codec, networkCodec);
		}
	}

	@Override
	public CreativeModeTab getESTab() {
		return FabricItemGroup.builder().title(Component.translatable("name.eternal_starlight")).icon(() -> new ItemStack(ESItems.STARLIGHT_FLOWER.get())).displayItems((displayParameters, output) -> {
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
	public <T> EntityDataAttachment<T> registerDataAttachment(String id, Supplier<T> defaultValue, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, BiPredicate<T, T> shouldSync, boolean copyOnDeath) {
		AttachmentRegistry.Builder<T> builder = AttachmentRegistryImpl.<T>builder().initializer(defaultValue);
		if (codec != null) {
			builder = builder.persistent(codec);
		}
		// "Received attachment change for unknown target!"
		/*if (streamCodec != null) {
			builder = builder.syncWith(streamCodec, (target, player) -> target instanceof Entity entity ? entity.isAlive() : target != null);
		}*/
		if (copyOnDeath) {
			builder = builder.copyOnDeath();
		}
		AttachmentType<T> type = builder.buildAndRegister(EternalStarlight.id(id));
		return new EntityDataAttachment<>() {
			@Override
			public ResourceLocation id() {
				return EternalStarlight.id(id);
			}

			@Override
			public boolean hasData(Entity entity) {
				return entity.hasAttached(type);
			}

			@Override
			public T getData(Entity entity) {
				// "initializer result cannot be null"
				if (!entity.hasAttached(type)) {
					return defaultValue.get();
				} else {
					return entity.getAttached(type);
				}
			}

			@Override
			public Optional<T> getExistingData(Entity entity) {
				return Optional.ofNullable(entity.getAttached(type));
			}

			@Override
			public @Nullable T setDataUnsynced(Entity entity, T data) {
				return entity.setAttached(type, data);
			}

			@Override
			public @Nullable T removeDataUnsynced(Entity entity) {
				return entity.removeAttached(type);
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
	public TheGatekeeperNameManager createGatekeeperNameManager() {
		return new FabricGatekeeperNameManager();
	}

	@Override
	public boolean isShears(ItemStack stack) {
		return stack.is(ConventionalItemTags.SHEARS_TOOLS);
	}

	@Override
	public boolean isShield(ItemStack stack) {
		return stack.is(ConventionalItemTags.SHIELDS_TOOLS);
	}

	@Override
	public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
		return HoeItemAccessor.getTillingActions().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
	}

	@Override
	public void sendToClient(ServerPlayer player, CustomPacketPayload packet) {
		ServerPlayNetworking.send(player, packet);
	}
}
