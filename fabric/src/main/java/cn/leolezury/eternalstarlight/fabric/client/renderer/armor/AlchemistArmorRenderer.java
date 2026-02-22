package cn.leolezury.eternalstarlight.fabric.client.renderer.armor;

import cn.leolezury.eternalstarlight.common.client.model.armor.AlchemistArmorModel;
import cn.leolezury.eternalstarlight.common.item.armor.AlchemistArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AlchemistArmorRenderer implements ArmorRenderer {
	public static final AlchemistArmorRenderer INSTANCE = new AlchemistArmorRenderer();

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
		ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, model, AlchemistArmorItem.TEXTURE);
	}
}