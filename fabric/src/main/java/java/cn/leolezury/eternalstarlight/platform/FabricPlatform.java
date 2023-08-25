package java.cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.platform.ESPlatform;
import com.chocohead.mm.api.ClassTinkerers;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.cn.leolezury.eternalstarlight.item.armor.FabricThermalSpringStoneArmorItem;
import java.cn.leolezury.eternalstarlight.network.FabricNetworkHandler;
import java.util.function.Consumer;
import java.util.function.Predicate;

@AutoService(ESPlatform.class)
public class FabricPlatform implements ESPlatform {
    private static final Rarity STARLIGHT_RARITY = ClassTinkerers.getEnum(Rarity.class, "STARLIGHT");
    private static final EnchantmentCategory ES_WEAPON_ENCHANTMENT_CATEGORY = ClassTinkerers.getEnum(EnchantmentCategory.class, "ES_WEAPON");

    @Override
    public Loader getLoader() {
        return Loader.FABRIC;
    }

    @Override
    public boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public ThermalSpringStoneArmorItem createThermalSpringStoneArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        return new FabricThermalSpringStoneArmorItem(material, type, properties);
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
    public EnchantmentCategory getESWeaponEnchantmentCategory() {
        return ES_WEAPON_ENCHANTMENT_CATEGORY;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        return HoeItemAccessor.getTillingActions().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
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
