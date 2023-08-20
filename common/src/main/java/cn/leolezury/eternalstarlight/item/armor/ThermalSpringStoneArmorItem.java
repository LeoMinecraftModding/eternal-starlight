package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ThermalSpringStoneArmorItem extends ArmorItem {
    public ThermalSpringStoneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    
    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return EternalStarlight.MOD_ID + ":textures/armor/thermal_springstone_layer_" + ((slot == EquipmentSlot.LEGS) ? "2.png" : "1.png");
    }

    
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ThermalSpringStoneArmorModel<LivingEntity> innerModel;
            private ThermalSpringStoneArmorModel<LivingEntity> outerModel;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (innerModel == null || outerModel == null) {
                    innerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.INNER_LOCATION));
                    outerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.OUTER_LOCATION));
                }

                if (itemStack.is(ItemInit.THERMAL_SPRINGSTONE_HELMET.get()) || itemStack.is(ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get()) || itemStack.is(ItemInit.THERMAL_SPRINGSTONE_BOOTS.get())) {
                    return outerModel;
                } else if (itemStack.is(ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get())) {
                    return innerModel;
                }

                return IClientItemExtensions.super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        component.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".thermal_springstone_armor").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, component, flag);
    }
}
