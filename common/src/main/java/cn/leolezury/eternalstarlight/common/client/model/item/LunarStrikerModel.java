package cn.leolezury.eternalstarlight.common.client.model.item;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LunarStrikerModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("lunar_striker"), "main");
	public static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/lunar_striker.png");
	private final ModelPart root;

	public LunarStrikerModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition weapon = root.addOrReplaceChild("weapon", CubeListBuilder.create().texOffs(4, 23).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 17).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, 0.0F));

		weapon.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -18.0F, 0.0F, 17.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leaves1 = root.addOrReplaceChild("leaves1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -22.5F, 0.0F, -0.4363F, -0.5236F, -0.2182F));

		leaves1.addOrReplaceChild("leaf1", CubeListBuilder.create().texOffs(4, 17).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

		leaves1.addOrReplaceChild("leaf2", CubeListBuilder.create().texOffs(4, 17).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.9199F, 0.0F));

		PartDefinition leaves2 = root.addOrReplaceChild("leaves2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -16.5F, 0.0F, -1.4835F, 1.4835F, -1.0472F));

		leaves2.addOrReplaceChild("leaf3", CubeListBuilder.create().texOffs(4, 17).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

		leaves2.addOrReplaceChild("leaf4", CubeListBuilder.create().texOffs(4, 17).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.9199F, 0.0F));

		PartDefinition leaves3 = root.addOrReplaceChild("leaves3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -16.5F, 0.0F, 2.9671F, 0.6545F, 3.1416F));

		leaves3.addOrReplaceChild("leaf5", CubeListBuilder.create().texOffs(14, 20).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		leaves3.addOrReplaceChild("leaf6", CubeListBuilder.create().texOffs(14, 20).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition leaves4 = root.addOrReplaceChild("leaves4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.5F, 0.0F, -0.3491F, -1.1781F, 0.0F));

		leaves4.addOrReplaceChild("leaf7", CubeListBuilder.create().texOffs(14, 20).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		leaves4.addOrReplaceChild("leaf8", CubeListBuilder.create().texOffs(14, 20).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition leaves5 = root.addOrReplaceChild("leaves5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, -0.3054F, 1.2217F, 0.0F));

		leaves5.addOrReplaceChild("leaf9", CubeListBuilder.create().texOffs(4, 20).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.309F, 0.0F));

		leaves5.addOrReplaceChild("leaf10", CubeListBuilder.create().texOffs(4, 20).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.8326F, 0.0F));

		PartDefinition leaves6 = root.addOrReplaceChild("leaves6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, 2.8798F, -0.2182F, 3.1416F));

		leaves6.addOrReplaceChild("leaf11", CubeListBuilder.create().texOffs(14, 17).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		leaves6.addOrReplaceChild("leaf12", CubeListBuilder.create().texOffs(14, 17).addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}