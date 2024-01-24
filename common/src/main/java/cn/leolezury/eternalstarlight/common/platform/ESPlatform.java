package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.client.ESDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.common.client.model.item.GlowingBakedModel;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.weapon.CommonHammerItem;
import cn.leolezury.eternalstarlight.common.item.weapon.CommonScytheItem;
import cn.leolezury.eternalstarlight.common.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.common.item.weapon.ScytheItem;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.resource.book.BookManager;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ESPlatform {
    ESPlatform INSTANCE = Util.make(() -> {
        final ServiceLoader<ESPlatform> loader = ServiceLoader.load(ESPlatform.class);
        final Iterator<ESPlatform> iterator = loader.iterator();
        if (!iterator.hasNext()) {
            throw new RuntimeException("Platform instance not found!");
        } else {
            final ESPlatform platform = iterator.next();
            if (iterator.hasNext()) {
                throw new RuntimeException("More than one platform instance was found!");
            }
            return platform;
        }
    });

    enum Loader {
        FORGE,
        FABRIC
    }

    // some loader-related stuff
    Loader getLoader();
    boolean isPhysicalClient();

    // registry utils
    <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace);
    <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace);
    <T> void registerDatapackRegistry(ResourceKey<Registry<T>> key, Codec<T> codec, Codec<T> networkCodec);

    // for initialization
    // ---Items
    default ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonScytheItem(tier, damage, attackSpeed, properties);
    }
    default HammerItem createHammer(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonHammerItem(tier, damage, attackSpeed, properties);
    }
    default BucketItem createBucket(Supplier<? extends Fluid> fluid, Item.Properties properties) {
        return new BucketItem(fluid.get(), properties);
    }
    default MobBucketItem createMobBucket(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Supplier<? extends SoundEvent> soundEvent, Item.Properties properties) {
        return new MobBucketItem(entityType.get(), fluid.get(), soundEvent.get(), properties);
    }
    ThermalSpringStoneArmorItem createThermalSpringStoneArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties);
    Rarity getStarlightRarity();
    Rarity getDemonRarity();
    CreativeModeTab getESTab();
    // ---Blocks
    default FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(flower.get(), properties);
    }
    default LiquidBlock createLiquidBlock(Supplier<? extends FlowingFluid> fluid, BlockBehaviour.Properties properties) {
        return new LiquidBlock(fluid.get(), properties);
    }
    default EtherFluid.Still createEtherFluid() {
        return new EtherFluid.Still();
    }
    default EtherFluid.Flowing createFlowingEtherFluid() {
        return new EtherFluid.Flowing();
    }
    // ---Reload listeners
    default BookManager createBookManager() {
        return new BookManager();
    }
    default ChapterManager createChapterManager() {
        return new ChapterManager();
    }
    default TheGatekeeperNameManager createGatekeeperNameManager() {
        return new TheGatekeeperNameManager();
    }

    // event-related
    default int postArrowLooseEvent(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return charge;
    }
    default boolean postMobGriefingEvent(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }
    default boolean postTravelToDimensionEvent(Entity entity, ResourceKey<Level> dimension) {
        return true;
    }

    // world
    void teleportEntity(ServerLevel dest, Entity entity);

    // entity stuff
    default Attribute getEntityReachAttribute() {
        return null;
    }

    // item stuff
    default boolean isShears(ItemStack stack) {
        return stack.is(Items.SHEARS);
    }
    default boolean isShield(ItemStack stack) {
        return stack.is(Items.SHIELD);
    }
    default boolean canStrip(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }
    default boolean isArrowInfinite(ItemStack arrow, ItemStack bow, Player player) {
        int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
        return enchant > 0 && arrow.getItem().getClass() == ArrowItem.class;
    }
    EnchantmentCategory getESWeaponEnchantmentCategory();
    Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context);

    // client-side
    @Environment(EnvType.CLIENT)
    default DimensionSpecialEffects getDimEffect() {
        return new ESDimensionSpecialEffects(160.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false);
    }
    @Environment(EnvType.CLIENT)
    default void renderBlock(BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed) {
        dispatcher.getModelRenderer().tesselateBlock(level, dispatcher.getBlockModel(state), state, pos, stack, multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, RandomSource.create(), seed, OverlayTexture.NO_OVERLAY);
    }
    @Environment(EnvType.CLIENT)
    default BakedModel getGlowingBakedModel(BakedModel origin) {
        return new GlowingBakedModel(origin);
    }

    // networking
    void sendToClient(ServerPlayer player, Object packet);
    default void sendToAllClients(ServerLevel level, Object packet) {
        for (ServerPlayer player : level.players()) {
            sendToClient(player, packet);
        }
    }
    void sendToServer(Object packet);
}
