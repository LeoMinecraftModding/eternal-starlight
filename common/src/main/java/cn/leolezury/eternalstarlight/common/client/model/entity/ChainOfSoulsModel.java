package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ChainOfSoulsModel<T extends ChainOfSouls> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("chain_of_souls"), "main");
	private final ModelPart base;
	private final ModelPart side1;
	private final ModelPart side2;
	private final ModelPart side3;
	private final ModelPart side4;

	public ChainOfSoulsModel(ModelPart root) {
		this.base = root.getChild("base");
		this.side1 = root.getChild("side1");
		this.side2 = root.getChild("side2");
		this.side3 = root.getChild("side3");
		this.side4 = root.getChild("side4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("side1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, -2.0F, 0.5236F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("side2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 2.0F, 0.5236F, 3.1416F, 0.0F));

		partdefinition.addOrReplaceChild("side3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 24.0F, 0.0F, 0.5236F, -1.5708F, 0.0F));

		partdefinition.addOrReplaceChild("side4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 24.0F, 0.0F, 0.5236F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		side1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		side2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		side3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		side4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}
