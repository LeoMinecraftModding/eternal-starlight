package cn.leolezury.eternalstarlight.fabric.platform;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.resource.book.BookManager;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.world.ESTeleporter;
import cn.leolezury.eternalstarlight.fabric.client.model.item.FabricGlowingBakedModel;
import cn.leolezury.eternalstarlight.fabric.item.armor.FabricThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.fabric.manager.book.FabricBookManager;
import cn.leolezury.eternalstarlight.fabric.manager.book.chapter.FabricChapterManager;
import cn.leolezury.eternalstarlight.fabric.manager.gatekeeper.FabricGatekeeperNameManager;
import cn.leolezury.eternalstarlight.fabric.network.FabricNetworkHandler;
import com.chocohead.mm.api.ClassTinkerers;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class FabricPlatform implements ESPlatform {
    private static final Rarity STARLIGHT_RARITY = ClassTinkerers.getEnum(Rarity.class, "ES_STARLIGHT");
    private static final Rarity DEMON_RARITY = ClassTinkerers.getEnum(Rarity.class, "ES_DEMON");
    private static final EnchantmentCategory ES_WEAPON_ENCHANTMENT_CATEGORY = ClassTinkerers.getEnum(EnchantmentCategory.class, "ES_WEAPON");

    @Override
    public Loader getLoader() {
        return Loader.FABRIC;
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
        return new RegistrationProvider<T>() {
            private final Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());

            @Override
            public Registry<T> registry() {
                return registry;
            }

            @Override
            public <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier) {
                ResourceLocation location = new ResourceLocation(namespace, id);
                ResourceKey<I> resourceKey = (ResourceKey<I>) ResourceKey.create(registry.key(), location);
                I object = Registry.register(registry, location, supplier.get());
                return new RegistryObject<T, I>() {
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
    public ThermalSpringStoneArmorItem createThermalSpringStoneArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        return new FabricThermalSpringStoneArmorItem(material, type, properties);
    }

    @Override
    public Rarity getStarlightRarity() {
        return STARLIGHT_RARITY;
    }

    @Override
    public Rarity getDemonRarity() {
        return DEMON_RARITY;
    }

    @Override
    public CreativeModeTab getESTab() {
        return FabricItemGroup.builder().title(Component.translatable("itemGroup.eternal_starlight")).icon(() -> new ItemStack(ItemInit.STARLIGHT_FLOWER.get())).displayItems((displayParameters, output) -> {
            for (ResourceKey<Item> entry : ItemInit.REGISTERED_ITEMS) {
                Item item = BuiltInRegistries.ITEM.get(entry);
                if (item != null) {
                    output.accept(item);
                }
            }
        }).build();
    }

    @Override
    public BookManager createBookManager() {
        return new FabricBookManager();
    }

    @Override
    public ChapterManager createChapterManager() {
        return new FabricChapterManager();
    }

    @Override
    public TheGatekeeperNameManager createGatekeeperNameManager() {
        return new FabricGatekeeperNameManager();
    }

    @Override
    public void teleportEntity(ServerLevel dest, Entity entity) {
        FabricDimensions.teleport(entity, dest, new ESTeleporter(dest).getPortalInfo(entity, dest));
    }

    @Override
    public EnchantmentCategory getESWeaponEnchantmentCategory() {
        return ES_WEAPON_ENCHANTMENT_CATEGORY;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        return HoeItemAccessor.getTillingActions().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
    }

    @Override
    public BakedModel getGlowingBakedModel(BakedModel origin) {
        return new FabricGlowingBakedModel(origin);
    }

    @Override
    public void sendToClient(ServerPlayer player, Object packet) {
        FabricNetworkHandler.sendToClient(player, packet);
    }

    @Override
    public void sendToServer(Object packet) {
        FabricNetworkHandler.sendToServer(packet);
    }
}
