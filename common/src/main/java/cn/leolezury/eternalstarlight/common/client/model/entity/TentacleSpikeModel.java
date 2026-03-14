package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.TentacleSpike;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TentacleSpikeModel<T extends TentacleSpike> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("tentacle_spike"), "main");
	private static final int NUM_SEGMENTS = 15;
	private final ModelPart root;
	private final ModelPart[] segments;

	public TentacleSpikeModel(ModelPart root) {
		this.root = root.getChild("root");
		ModelPart[] parts = new ModelPart[NUM_SEGMENTS];
		ModelPart last = this.root;
		for (int i = 0; i < NUM_SEGMENTS; i++) {
			last = last.getChild("segment" + (i + 1));
			parts[i] = last;
		}
		this.segments = parts;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition last = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		for (int i = 0; i < NUM_SEGMENTS; i++) {
			if (i == 0) {
				last = last.addOrReplaceChild("segment1", CubeListBuilder.create().texOffs(18, 15).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
					.texOffs(0, 21).addBox(-1.5F, -9.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(0, 21).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
				last.addOrReplaceChild("cross1", CubeListBuilder.create().texOffs(12, 21).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.7854F, 0.0F));
				last.addOrReplaceChild("cross2", CubeListBuilder.create().texOffs(12, 21).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
			} else if (i == NUM_SEGMENTS - 1) {
				last = last.addOrReplaceChild("segment" + (i + 1), CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
				last.addOrReplaceChild("cross" + (i * 2 + 1), CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -6.0F, -4.5F, 0.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
				last.addOrReplaceChild("cross" + (i * 2 + 2), CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -6.0F, -4.5F, 0.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
			} else {
				last = last.addOrReplaceChild("segment" + (i + 1), CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
				last.addOrReplaceChild("cross" + (i * 2 + 1), CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, -6.0F, -1.5F, 0.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
				last.addOrReplaceChild("cross" + (i * 2 + 2), CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, -6.0F, -1.5F, 0.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
			}
		}

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root.getAllParts().forEach(ModelPart::resetPose);
		float progress = Mth.clamp(entity.getAnimationTicks(Mth.frac(ageInTicks)) / entity.getLifespan(), 0, 1);
		for (int i = 0; i < NUM_SEGMENTS; i++) {
			ModelPart segment = segments[i];
			segment.xRot = Mth.lerp(progress, 120f / (i + 1), -120f / (i + 1)) * Mth.DEG_TO_RAD;
			if (i == 0) {
				float scale = Easing.IN_OUT_SINE.calculate(1 - Math.abs(progress - 0.5f) * 2);
				segment.xScale = segment.yScale = segment.zScale = scale;
			}
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}