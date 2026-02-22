package cn.leolezury.eternalstarlight.fabric.client.renderer.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.armor.UnrealiumArmorModel;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class UnrealiumArmorRenderer implements ArmorRenderer {
	public static final UnrealiumArmorRenderer INSTANCE = new UnrealiumArmorRenderer();
	// private TextureAtlas armorTrimAtlas;

	private UnrealiumArmorModel<LivingEntity> innerModel;
	private UnrealiumArmorModel<LivingEntity> outerModel;

	public static void setPartVisibility(UnrealiumArmorModel<LivingEntity> armorModel, EquipmentSlot slot) {
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
			innerModel = new UnrealiumArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(UnrealiumArmorModel.INNER_LOCATION));
			outerModel = new UnrealiumArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(UnrealiumArmorModel.OUTER_LOCATION));
		}

		UnrealiumArmorModel<LivingEntity> armorModel;

		if (itemStack.is(ESItems.UNREALIUM_HELMET.get()) || itemStack.is(ESItems.UNREALIUM_CHESTPLATE.get()) || itemStack.is(ESItems.UNREALIUM_BOOTS.get())) {
			armorModel = outerModel;
		} else if (itemStack.is(ESItems.UNREALIUM_LEGGINGS.get())) {
			armorModel = innerModel;
		} else return;

		parentModel.copyPropertiesTo(armorModel);
		setPartVisibility(armorModel, armorSlot);
		ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, armorModel, EternalStarlight.id("textures/armor/unrealium_layer_" + ((armorSlot == EquipmentSlot.LEGS) ? "2.png" : "1.png")));
	}
}