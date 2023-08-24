package java.cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.item.weapon.CommonHammerItem;
import cn.leolezury.eternalstarlight.item.weapon.CommonScytheItem;
import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import cn.leolezury.eternalstarlight.platform.ESPlatform;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class FabricPlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FABRIC;
    }

    @Override
    public ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonScytheItem(tier, damage, attackSpeed, properties);
    }

    @Override
    public HammerItem createHammer(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        return new CommonHammerItem(tier, damage, attackSpeed, properties);
    }

    @Override
    public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(flower.get(), properties);
    }

    @Override
    public EntityType<?> getEntityType(ResourceLocation location) {
        return BuiltInRegistries.ENTITY_TYPE.get(location);
    }

    @Override
    public ResourceLocation getEntityTypeIdentifier(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    @Override
    public Attribute getEntityReachAttribute() {
        return null;
    }

    @Override
    public boolean postProjectileImpactEvent(Projectile projectile, HitResult hitResult) {
        return false;
    }

    @Override
    public int postArrowLooseEvent(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return charge;
    }

    @Override
    public boolean postMobGriefingEvent(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    @Override
    public boolean isShears(ItemStack stack) {
        return stack.is(Items.SHEARS);
    }

    @Override
    public boolean isArrowInfinite(ItemStack arrow, ItemStack bow, Player player) {
        int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
        return enchant <= 0 ? false : arrow.getItem().getClass() == ArrowItem.class;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        return HoeItemAccessor.getTillingActions().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
    }
}
