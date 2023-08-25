package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ForgeThermalSpringStoneArmorItem extends ThermalSpringStoneArmorItem {
    public ForgeThermalSpringStoneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return getTexture(stack, entity, slot, type);
    }

    @OnlyIn(Dist.CLIENT)
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
}
