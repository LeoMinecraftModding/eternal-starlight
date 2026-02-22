package cn.leolezury.eternalstarlight.common.client.model.item;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class FlowglazeShieldModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("flowglaze_shield"), "main");
	private final ModelPart handle;
	private final ModelPart shield;

	public FlowglazeShieldModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.handle = root.getChild("handle");
		this.shield = root.getChild("shield");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -3.0F, 0.5F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -0.5F));

		partdefinition.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, -0.5F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 13).addBox(-8.0F, -8.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(0, 31).addBox(-8.0F, 6.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(0, 17).addBox(-8.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(8, 17).addBox(6.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		handle.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		shield.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}