package cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
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
    ScytheItem createScythe(Tier tier, float damage, float attackSpeed, Item.Properties properties);
    HammerItem createHammer(Tier tier, float damage, float attackSpeed, Item.Properties properties);
    FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties);

    // event-related
    boolean postProjectileImpactEvent(Projectile projectile, HitResult hitResult);
    int postArrowLooseEvent(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo);
    boolean postMobGriefingEvent(Level level, Entity entity);

    // entity stuff
    EntityType<?> getEntityType(ResourceLocation location);
    ResourceLocation getEntityTypeIdentifier(EntityType<?> entityType);
    Attribute getEntityReachAttribute();

    // item stuff
    boolean isShears(ItemStack stack);
    boolean isArrowInfinite(ItemStack arrow, ItemStack bow, Player player);
    Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context);
}
