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

public class MalariteSpearModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("malarite_spear"), "main");
	public static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/malarite_spear.png");
	private final ModelPart root;

	public MalariteSpearModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -31.0F, -0.5F, 1.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(4, 0).addBox(-0.5F, -31.0F, -0.5F, 1.0F, 31.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tip1 = root.addOrReplaceChild("tip1", CubeListBuilder.create().texOffs(9, 1).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -28.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tip2 = root.addOrReplaceChild("tip2", CubeListBuilder.create().texOffs(9, 1).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -28.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}