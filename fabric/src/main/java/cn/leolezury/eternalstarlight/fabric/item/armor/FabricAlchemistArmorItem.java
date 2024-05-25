package cn.leolezury.eternalstarlight.fabric.item.armor;

import cn.leolezury.eternalstarlight.common.client.model.armor.AlchemistArmorModel;
import cn.leolezury.eternalstarlight.common.item.armor.AlchemistArmorItem;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class FabricAlchemistArmorItem extends AlchemistArmorItem {
    public FabricAlchemistArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
        ESMiscUtil.runWhenOnClient(() -> this::initializeClient);
    }

    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        ArmorRenderer.register(ArmorRender.INSTANCE, this);
    }

    @Environment(EnvType.CLIENT)
    private static final class ArmorRender implements ArmorRenderer {
        private static final ArmorRender INSTANCE = new ArmorRender();

        private AlchemistArmorModel<LivingEntity> model;

        public static void setPartVisibility(AlchemistArmorModel<LivingEntity> armorModel, EquipmentSlot slot) {
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
            if (model == null) {
                model = new AlchemistArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlchemistArmorModel.LAYER_LOCATION));
            }

            parentModel.copyPropertiesTo(model);
            setPartVisibility(model, armorSlot);
            ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, model, TEXTURE);
        }
    }
}
