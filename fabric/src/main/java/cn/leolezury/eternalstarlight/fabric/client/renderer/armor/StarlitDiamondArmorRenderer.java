package cn.leolezury.eternalstarlight.fabric.client.renderer.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.armor.StarlitDiamondArmorModel;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class StarlitDiamondArmorRenderer implements ArmorRenderer {
	public static final StarlitDiamondArmorRenderer INSTANCE = new StarlitDiamondArmorRenderer();

	private StarlitDiamondArmorModel<LivingEntity> innerModel;
	private StarlitDiamondArmorModel<LivingEntity> outerModel;

	public static void setPartVisibility(StarlitDiamondArmorModel<LivingEntity> armorModel, EquipmentSlot slot) {
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
			innerModel = new StarlitDiamondArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(StarlitDiamondArmorModel.INNER_LOCATION));
			outerModel = new StarlitDiamondArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(StarlitDiamondArmorModel.OUTER_LOCATION));
		}

		StarlitDiamondArmorModel<LivingEntity> armorModel;

		if (itemStack.is(ESItems.STARLIT_DIAMOND_HELMET.get()) || itemStack.is(ESItems.STARLIT_DIAMOND_CHESTPLATE.get()) || itemStack.is(ESItems.STARLIT_DIAMOND_BOOTS.get())) {
			armorModel = outerModel;
		} else if (itemStack.is(ESItems.STARLIT_DIAMOND_LEGGINGS.get())) {
			armorModel = innerModel;
		} else return;

		parentModel.copyPropertiesTo(armorModel);
		setPartVisibility(armorModel, armorSlot);
		ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, armorModel, EternalStarlight.id("textures/armor/starlit_diamond_layer_" + ((armorSlot == EquipmentSlot.LEGS) ? "2.png" : "1.png")));
	}
}