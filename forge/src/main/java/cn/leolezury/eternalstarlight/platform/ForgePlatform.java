package cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.item.weapon.ForgeHammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ForgeScytheItem;
import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import com.google.auto.service.AutoService;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ForgePlatform implements ESPlatform {
    private static final Rarity STARLIGHT_RARITY = Rarity.create("STARLIGHT", ChatFormatting.DARK_AQUA);
    private static final EnchantmentCategory ES_WEAPON_ENCHANTMENT_CATEGORY = EnchantmentCategory.create("ES_WEAPON", (item -> item instanceof SwordItem || item instanceof AxeItem || item instanceof ScytheItem || item instanceof HammerItem));

    @Override
    public Loader getLoader() {
        return Loader.FORGE;
    }

    @Override
    public ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new ForgeScytheItem(tier, damage, attackSpeed, properties);
    }

    @Override
    public HammerItem createHammer(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new ForgeHammerItem(tier, damage, attackSpeed, properties);
    }

    @Override
    public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(pot, flower, properties);
    }

    @Override
    public boolean postProjectileImpactEvent(Projectile projectile, HitResult hitResult) {
        return ForgeEventFactory.onProjectileImpact(projectile, hitResult);
    }

    @Override
    public int postArrowLooseEvent(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return ForgeEventFactory.onArrowLoose(stack, level, player, charge, hasAmmo);
    }

    @Override
    public boolean postMobGriefingEvent(Level level, Entity entity) {
        return ForgeEventFactory.getMobGriefingEvent(level, entity);
    }

    @Override
    public boolean noFluidAtCamera(Camera camera) {
        return camera.getBlockAtCamera().getFluidState().isEmpty();
    }

    @Override
    public EntityType<?> getEntityType(ResourceLocation location) {
        Optional<Holder<EntityType<?>>> entityTypeHolder = ForgeRegistries.ENTITY_TYPES.getHolder(location);
        return entityTypeHolder.<EntityType<?>>map(Holder::get).orElse(null);
    }

    @Override
    public ResourceLocation getEntityTypeIdentifier(EntityType<?> entityType) {
        return ForgeRegistries.ENTITY_TYPES.getKey(entityType);
    }

    @Override
    public Attribute getEntityReachAttribute() {
        return ForgeMod.ENTITY_REACH.get();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(Entity entity) {
        return NetworkHooks.getEntitySpawningPacket(entity);
    }

    @Override
    public boolean isShears(ItemStack stack) {
        return stack.is(Tags.Items.SHEARS);
    }

    @Override
    public boolean isShield(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    @Override
    public boolean isArrowInfinite(ItemStack arrow, ItemStack bow, Player player) {
        if (arrow.getItem() instanceof ArrowItem arrowItem) {
            return arrowItem.isInfinite(arrow, bow, player);
        }
        return false;
    }

    @Override
    public Rarity getESRarity() {
        return STARLIGHT_RARITY;
    }

    @Override
    public EnchantmentCategory getESWeaponEnchantmentCategory() {
        return ES_WEAPON_ENCHANTMENT_CATEGORY;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ToolActions.HOE_TILL, false);
        return toolModifiedState == null ? null : Pair.of(ctx -> true, ScytheItem.changeIntoState(toolModifiedState));
    }

    @Override
    public void renderBlock(BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed) {
        var model = dispatcher.getBlockModel(state);
        for (var renderType : model.getRenderTypes(state, RandomSource.create(seed), ModelData.EMPTY))
            dispatcher.getModelRenderer().tesselateBlock(level, model, state, pos, stack, multiBufferSource.getBuffer(renderType), false, RandomSource.create(), seed, OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);

    }
}
