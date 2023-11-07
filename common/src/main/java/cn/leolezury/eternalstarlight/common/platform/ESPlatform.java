package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.client.ESDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.common.client.model.item.GlowingBakedModel;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.weapon.CommonHammerItem;
import cn.leolezury.eternalstarlight.common.item.weapon.CommonScytheItem;
import cn.leolezury.eternalstarlight.common.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.common.item.weapon.ScytheItem;
import cn.leolezury.eternalstarlight.common.resource.book.BookManager;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ESPlatform {
    ESPlatform INSTANCE = Util.make(() -> {
        final ServiceLoader<ESPlatform> loader = ServiceLoader.load(ESPlatform.class);
        final Iterator<ESPlatform> it = loader.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Platform instance not found!");
        } else {
            final ESPlatform platform = it.next();
            if (it.hasNext()) {
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

    // for initialization
    // ---Items
    default ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonScytheItem(tier, damage, attackSpeed, properties);
    }
    default HammerItem createHammer(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonHammerItem(tier, damage, attackSpeed, properties);
    }
    ThermalSpringStoneArmorItem createThermalSpringStoneArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties);
    Rarity getStarlightRarity();
    Rarity getDemonRarity();
    CreativeModeTab getESTab();
    // ---Blocks
    default FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(flower.get(), properties);
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
    default boolean noFluidAtCamera(Camera camera) {
        return true;
    }

    // world
    void teleportEntity(ServerLevel dest, Entity entity);

    // entity stuff
    default EntityType<?> getEntityType(ResourceLocation location) {
        return BuiltInRegistries.ENTITY_TYPE.get(location);
    }
    default ResourceLocation getEntityTypeIdentifier(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }
    default Attribute getEntityReachAttribute() {
        return null;
    }
    default Packet<ClientGamePacketListener> getAddEntityPacket(Entity entity) {
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
    void sendToServer(Object packet);
}
