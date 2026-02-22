package cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class ArmorLikeAccessoryLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
	public static final ModelLayerLocation INNER_LOCATION = new ModelLayerLocation(EternalStarlight.id("accessory"), "inner_armor");
	public static final ModelLayerLocation OUTER_LOCATION = new ModelLayerLocation(EternalStarlight.id("accessory"), "outer_armor");

	private final A innerModel;
	private final A outerModel;

	public ArmorLikeAccessoryLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel) {
		super(renderer);
		this.innerModel = innerModel;
		this.outerModel = outerModel;
	}

	@Override
	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		T livingEntity,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		if (shouldRender(livingEntity)) {
			this.renderArmorLikePiece(poseStack, buffer, livingEntity, EquipmentSlot.CHEST, packedLight, this.getAccessoryModel(EquipmentSlot.CHEST));
			this.renderArmorLikePiece(poseStack, buffer, livingEntity, EquipmentSlot.LEGS, packedLight, this.getAccessoryModel(EquipmentSlot.LEGS));
			this.renderArmorLikePiece(poseStack, buffer, livingEntity, EquipmentSlot.FEET, packedLight, this.getAccessoryModel(EquipmentSlot.FEET));
			this.renderArmorLikePiece(poseStack, buffer, livingEntity, EquipmentSlot.HEAD, packedLight, this.getAccessoryModel(EquipmentSlot.HEAD));
		}
	}

	private void renderArmorLikePiece(PoseStack poseStack, MultiBufferSource bufferSource, T livingEntity, EquipmentSlot slot, int packedLight, A armorModel) {
		this.getParentModel().copyPropertiesTo(armorModel);
		this.setPartVisibility(armorModel, slot);
		ResourceLocation texture = getAccessoryTexture(livingEntity, livingEntity.getItemBySlot(slot), slot);
		if (texture != null) {
			this.renderModel(poseStack, bufferSource, packedLight, armorModel, texture);
		}
	}

	protected void setPartVisibility(A model, EquipmentSlot slot) {
		model.setAllVisible(false);
		switch (slot) {
			case HEAD:
				model.head.visible = true;
				model.hat.visible = true;
				break;
			case CHEST:
				model.body.visible = true;
				model.rightArm.visible = true;
				model.leftArm.visible = true;
				break;
			case LEGS:
				model.body.visible = true;
				model.rightLeg.visible = true;
				model.leftLeg.visible = true;
				break;
			case FEET:
				model.rightLeg.visible = true;
				model.leftLeg.visible = true;
		}
	}

	private void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Model model, ResourceLocation texture) {
		VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(texture));
		model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
	}

	private A getAccessoryModel(EquipmentSlot slot) {
		return this.usesInnerModel(slot) ? this.innerModel : this.outerModel;
	}

	protected boolean usesInnerModel(EquipmentSlot slot) {
		return slot == EquipmentSlot.LEGS;
	}

	protected abstract ResourceLocation getAccessoryTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot);

	protected abstract boolean shouldRender(LivingEntity entity);
}
