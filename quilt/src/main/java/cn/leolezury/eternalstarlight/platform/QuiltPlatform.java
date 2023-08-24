package cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.item.weapon.CommonHammerItem;
import cn.leolezury.eternalstarlight.item.weapon.CommonScytheItem;
import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.minecraft.client.Camera;
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
import net.minecraft.world.phys.HitResult;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class QuiltPlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.QUILT;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        return HoeItemAccessor.getTillingActions().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
    }
}
