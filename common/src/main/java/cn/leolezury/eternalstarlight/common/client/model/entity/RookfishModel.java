package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Rookfish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import java.util.Arrays;

public class RookfishModel<T extends Rookfish> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("rookfish"), "main");
	private final ModelPart root;
	private final ModelPart[] legs = new ModelPart[8];

	public RookfishModel(ModelPart root) {
		this.root = root.getChild("root");
		Arrays.setAll(this.legs, (i) -> root.getChild("root").getChild("leg" + (i + 1)));
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		root.addOrReplaceChild("upper1", CubeListBuilder.create().texOffs(1, 26).addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -4.0F, 0.2138F, 0.0F, 0.0F));

		root.addOrReplaceChild("upper2", CubeListBuilder.create().texOffs(1, 26).addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 4.0F, -0.2138F, 0.0F, 0.0F));

		root.addOrReplaceChild("upper3", CubeListBuilder.create().texOffs(1, 18).addBox(0.0F, -1.0F, -4.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.0F, 0.0F, 0.0F, 0.0F, -0.2138F));

		root.addOrReplaceChild("upper4", CubeListBuilder.create().texOffs(1, 18).addBox(0.0F, -1.0F, -4.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.2138F));

		root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -2.0F));

		root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 2.0F, 0.0F, 3.1416F, 0.0F));

		root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		root.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.6464F, 3.0F, -1.6464F, 0.0F, -0.7854F, 0.0F));

		root.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6464F, 3.0F, -1.6464F, 0.0F, 0.7854F, 0.0F));

		root.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6464F, 3.0F, 1.6464F, 0.0F, 2.3562F, 0.0F));

		root.addOrReplaceChild("leg8", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.6464F, 3.0F, 1.6464F, 0.0F, -2.3562F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialTick = Mth.frac(ageInTicks);
		float xBodyRot = Mth.lerp(partialTick, entity.xBodyRotO, entity.xBodyRot);
		float zBodyRot = Mth.lerp(partialTick, entity.zBodyRotO, entity.zBodyRot);
		root.xRot = -xBodyRot * Mth.DEG_TO_RAD;
		root.zRot = -zBodyRot * Mth.DEG_TO_RAD;
		for (ModelPart modelPart : this.legs) {
			modelPart.xRot = -Mth.lerp(partialTick, entity.oldTentacleAngle, entity.tentacleAngle);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}
