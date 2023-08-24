package cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.item.weapon.CommonHammerItem;
import cn.leolezury.eternalstarlight.item.weapon.CommonScytheItem;
import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

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
        FABRIC,
        QUILT
    }

    // some loader-related stuff
    Loader getLoader();

    // for initialization
    default ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonScytheItem(tier, damage, attackSpeed, properties);
    }
    default HammerItem createHammer(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonHammerItem(tier, damage, attackSpeed, properties);
    }
    default FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(flower.get(), properties);
    }

    // event-related
    default boolean postProjectileImpactEvent(Projectile projectile, HitResult hitResult) {
        return false;
    }
    default int postArrowLooseEvent(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return charge;
    }
    default boolean postMobGriefingEvent(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }
    default boolean noFluidAtCamera(Camera camera) {
        return true;
    }

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
    default boolean isArrowInfinite(ItemStack arrow, ItemStack bow, Player player) {
        int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
        return enchant <= 0 ? false : arrow.getItem().getClass() == ArrowItem.class;
    }
    Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context);

    // client-side
    @Environment(EnvType.CLIENT)
    default void renderBlock(BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed) {
        dispatcher.getModelRenderer().tesselateBlock(level, dispatcher.getBlockModel(state), state, pos, stack, multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, RandomSource.create(), seed, OverlayTexture.NO_OVERLAY);
    }
}
