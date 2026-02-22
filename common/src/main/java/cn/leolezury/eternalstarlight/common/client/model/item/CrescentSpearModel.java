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
import net.minecraft.resources.ResourceLocation;

public class CrescentSpearModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("crescent_spear"), "main");
	public static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/crescent_spear.png");
	private final ModelPart root;

	public CrescentSpearModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(3.0F, -14.0F, -0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(0, 3).mirror().addBox(1.0F, -13.0F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(6, 3).mirror().addBox(3.0F, -12.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(12, 0).mirror().addBox(0.0F, -12.0F, -0.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(6, 5).mirror().addBox(3.0F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(6, 7).mirror().addBox(-1.0F, -11.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(20, 0).mirror().addBox(-2.0F, -10.0F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(24, 0).mirror().addBox(12.0F, -8.0F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(20, 10).mirror().addBox(11.0F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(24, 6).mirror().addBox(3.0F, -5.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(30, 0).mirror().addBox(10.0F, -4.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(28, 6).mirror().addBox(4.0F, -4.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(37, 0).mirror().addBox(12.0F, -3.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(37, 3).mirror().addBox(5.0F, -3.0F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(37, 9).mirror().addBox(5.0F, 2.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(37, 11).mirror().addBox(1.0F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(45, 9).mirror().addBox(-3.0F, -8.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(41, 11).mirror().addBox(2.0F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -25.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition handle = root.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, -25.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(4, 27).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		handle.addOrReplaceChild("leaf1", CubeListBuilder.create().texOffs(5, 32).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -23.0F, -0.5F, 0.0F, -0.3927F, 0.3927F));

		handle.addOrReplaceChild("leaf2", CubeListBuilder.create().texOffs(5, 36).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -18.0F, -0.5F, 0.0F, 0.3927F, -0.3927F));

		handle.addOrReplaceChild("leaf3", CubeListBuilder.create().texOffs(5, 40).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -21.0F, 0.5F, 0.0F, -0.7854F, -0.7854F));

		handle.addOrReplaceChild("leaf4", CubeListBuilder.create().texOffs(5, 40).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -16.0F, 0.5F, 0.0F, 0.3927F, 0.7854F));

		handle.addOrReplaceChild("leaf5", CubeListBuilder.create().texOffs(5, 32).mirror().addBox(-1.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -13.0F, 0.5F, 0.0F, -0.3927F, -0.3927F));

		handle.addOrReplaceChild("leaf6", CubeListBuilder.create().texOffs(5, 36).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.0F, -0.5F, 0.0F, -0.3927F, 0.3927F));

		handle.addOrReplaceChild("leaf7", CubeListBuilder.create().texOffs(5, 40).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -12.0F, -0.5F, 0.0F, 0.7854F, -0.3927F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}