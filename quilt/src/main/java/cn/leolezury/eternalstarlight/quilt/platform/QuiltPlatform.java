package cn.leolezury.eternalstarlight.quilt.platform;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.quilt.item.armor.QuiltThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.quilt.network.QuiltNetworkHandler;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESTeleporter;
import com.chocohead.mm.api.ClassTinkerers;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import java.util.function.Consumer;
import java.util.function.Predicate;

@AutoService(ESPlatform.class)
public class QuiltPlatform implements ESPlatform {
    private static final Rarity STARLIGHT_RARITY = ClassTinkerers.getEnum(Rarity.class, "STARLIGHT");
    private static final EnchantmentCategory ES_WEAPON_ENCHANTMENT_CATEGORY = ClassTinkerers.getEnum(EnchantmentCategory.class, "ES_WEAPON");

    @Override
    public Loader getLoader() {
        return Loader.QUILT;
    }

    @Override
    public boolean isClientSide() {
        return MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public ThermalSpringStoneArmorItem createThermalSpringStoneArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        return new QuiltThermalSpringStoneArmorItem(material, type, properties);
    }

    @Override
    public Rarity getESRarity() {
        return STARLIGHT_RARITY;
    }

    @Override
    public CreativeModeTab getESTab() {
        return FabricItemGroup.builder().title(Component.translatable("itemGroup.eternal_starlight")).icon(() -> new ItemStack(ItemInit.STARLIGHT_FLOWER.get())).displayItems((displayParameters, output) -> {
            for (ResourceKey<Item> entry : ItemInit.REGISTERED_ITEMS) {
                Item item = ItemInit.ITEMS.getRegistry().get(entry);
                if (item != null) {
                    output.accept(item);
                }
            }
        }).build();
    }

    @Override
    public void teleportEntity(ServerLevel dest, Entity entity) {
        QuiltDimensions.teleport(entity, dest, new ESTeleporter(dest).getPortalInfo(entity, dest));
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
    public void sendToClient(ServerPlayer player, Object packet) {
        QuiltNetworkHandler.sendToClient(player, packet);
    }

    @Override
    public void sendToServer(Object packet) {
        QuiltNetworkHandler.sendToServer(packet);
    }
}
