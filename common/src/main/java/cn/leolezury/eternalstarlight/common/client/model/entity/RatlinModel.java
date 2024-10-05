package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.RatlinAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ratlin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class RatlinModel<T extends Ratlin> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("ratlin"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public RatlinModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = root.getChild("root").getChild("body").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -11.0F, -8.0F, 13.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(26, 27).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 5.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -4.0F, -6.0F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -7.0F, -8.0F));

		head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -6.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, -3.0F));

		head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -6.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, -3.0F));

		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(39, 41).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -4.5F));

		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(39, 41).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -3.0F, -4.5F));

		root.addOrReplaceChild("left_rear_leg", CubeListBuilder.create().texOffs(26, 38).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, 4.5F));

		root.addOrReplaceChild("right_rear_leg", CubeListBuilder.create().texOffs(39, 41).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -3.0F, 4.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		if (young) {
			root.xScale = 0.6f;
			root.yScale = 0.6f;
			root.zScale = 0.6f;
			head.xScale = 1.67f;
			head.yScale = 1.67f;
			head.zScale = 1.67f;
		}
		this.animate(entity.idleAnimationState, RatlinAnimation.IDLE, ageInTicks);
		this.animateWalk(RatlinAnimation.WALK, limbSwing, limbSwingAmount, young ? 3.0f : 5.0f, 1f);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
