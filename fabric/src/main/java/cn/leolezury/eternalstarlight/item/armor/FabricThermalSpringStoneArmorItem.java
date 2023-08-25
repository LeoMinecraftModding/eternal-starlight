package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.util.ESUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class FabricThermalSpringStoneArmorItem extends ThermalSpringStoneArmorItem {
    public FabricThermalSpringStoneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        ESUtil.runWhenOnClient(() -> this::initializeClient);
    }

    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        ArmorRenderer.register(ArmorRender.INSTANCE, this);
    }

    @Environment(EnvType.CLIENT)
    private static final class ArmorRender implements ArmorRenderer {
        private static final ArmorRender INSTANCE = new ArmorRender();

        private ThermalSpringStoneArmorModel<LivingEntity> innerModel;
        private ThermalSpringStoneArmorModel<LivingEntity> outerModel;

        public static void setPartVisibility(ThermalSpringStoneArmorModel<LivingEntity> armorModel, EquipmentSlot slot) {
            armorModel.setAllVisible(false);
            switch (slot) {
                case HEAD -> {
                    armorModel.head.visible = true;
                    armorModel.hat.visible = true;
                }
                case CHEST -> {
                    armorModel.body.visible = true;
                    armorModel.rightArm.visible = true;
                    armorModel.leftArm.visible = true;
                }
                case LEGS -> {
                    armorModel.body.visible = true;
                    armorModel.rightLeg.visible = true;
                    armorModel.leftLeg.visible = true;
                }
                case FEET -> {
                    armorModel.rightLeg.visible = true;
                    armorModel.leftLeg.visible = true;
                }
            }
        }

        @Override
        public void render(PoseStack stack, MultiBufferSource multiBufferSource, ItemStack itemStack, LivingEntity livingEntity, EquipmentSlot armorSlot, int light, HumanoidModel<LivingEntity> parentModel) {
            if (innerModel == null || outerModel == null) {
                innerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.INNER_LOCATION));
                outerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.OUTER_LOCATION));
            }

            ThermalSpringStoneArmorModel<LivingEntity> armorModel;

            if (itemStack.is(ItemInit.THERMAL_SPRINGSTONE_HELMET.get()) || itemStack.is(ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get()) || itemStack.is(ItemInit.THERMAL_SPRINGSTONE_BOOTS.get())) {
                armorModel = outerModel;
            } else if (itemStack.is(ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get())) {
                armorModel = innerModel;
            } else return;

            parentModel.copyPropertiesTo(armorModel);
            setPartVisibility(armorModel, armorSlot);
            ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, armorModel, new ResourceLocation(getTexture(itemStack, livingEntity, armorSlot, "overlay")));
        }
    }
}
