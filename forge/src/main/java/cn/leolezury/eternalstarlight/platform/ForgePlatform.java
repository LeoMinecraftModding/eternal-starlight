package cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.item.weapon.CommonScytheItem;
import cn.leolezury.eternalstarlight.item.weapon.ForgeScytheItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ForgePlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FORGE;
    }

    @Override
    public ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new ForgeScytheItem(tier, damage, attackSpeed, properties);
    }

    @Override
    public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(pot, flower, properties);
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
    public boolean postProjectileImpactEvent(Projectile projectile, HitResult hitResult) {
        return ForgeEventFactory.onProjectileImpact(projectile, hitResult);
    }

    @Override
    public int postArrowLooseEvent(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return ForgeEventFactory.onArrowLoose(stack, level, player, charge, hasAmmo);
    }

    @Override
    public boolean isShears(ItemStack stack) {
        return stack.is(Tags.Items.SHEARS);
    }

    @Override
    public boolean isArrowInfinite(ItemStack arrow, ItemStack bow, Player player) {
        if (arrow.getItem() instanceof ArrowItem arrowItem) {
            return arrowItem.isInfinite(arrow, bow, player);
        }
        return false;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ToolActions.HOE_TILL, false);
        return toolModifiedState == null ? null : Pair.of(ctx -> true, ScytheItem.changeIntoState(toolModifiedState));
    }
}
