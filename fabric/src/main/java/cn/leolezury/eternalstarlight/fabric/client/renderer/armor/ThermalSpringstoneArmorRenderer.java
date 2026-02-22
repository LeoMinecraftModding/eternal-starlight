package cn.leolezury.eternalstarlight.fabric.client.renderer.armor;

import cn.leolezury.eternalstarlight.common.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ThermalSpringstoneArmorRenderer implements ArmorRenderer {
	public static final ThermalSpringstoneArmorRenderer INSTANCE = new ThermalSpringstoneArmorRenderer();

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

		if (itemStack.is(ESItems.THERMAL_SPRINGSTONE_HELMET.get()) || itemStack.is(ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get()) || itemStack.is(ESItems.THERMAL_SPRINGSTONE_BOOTS.get())) {
			armorModel = outerModel;
		} else if (itemStack.is(ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get())) {
			armorModel = innerModel;
		} else return;

		parentModel.copyPropertiesTo(armorModel);
		setPartVisibility(armorModel, armorSlot);
		ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, armorModel, ThermalSpringstoneArmorItem.getTexture(armorSlot));
	}
}