package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.StarfireBirdAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.StarfireBird;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class StarfireBirdModel<T extends StarfireBird> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation ADULT_LOCATION = new ModelLayerLocation(EternalStarlight.id("starfire_bird"), "main");
	public static final ModelLayerLocation BABY_LOCATION = new ModelLayerLocation(EternalStarlight.id("starfire_bird"), "baby");
	private final ModelPart root;

	public StarfireBirdModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(4, 14).addBox(-3.5F, -5.0F, 2.5F, 7.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-3.5F, -8.0F, -3.5F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(23, 21).addBox(-1.5F, -3.0F, -4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -0.5F));

		body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(22, 14).addBox(0.0F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -2.5F, -1.5F));

		body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(22, 14).mirror().addBox(-1.0F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, -2.5F, -1.5F));

		body.addOrReplaceChild("feather", CubeListBuilder.create().texOffs(1, 18).mirror().addBox(0.0F, -4.0F, -0.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -8.0F, -1.0F));

		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 22.0F, -0.5F));

		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 22.0F, -0.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public static LayerDefinition createBabyBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 3).addBox(-2.0F, -3.0F, 2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(0, 9).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.5F));

		body.addOrReplaceChild("feather", CubeListBuilder.create().texOffs(1, 10).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -5.0F, -1.0F));

		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(6, 9).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 23.0F, 0.5F));

		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(6, 9).mirror().addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 23.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, StarfireBirdAnimation.IDLE, ageInTicks);
		animate(entity.nestIdleAnimationState, StarfireBirdAnimation.IDLE_NEST, ageInTicks);
		float flapScale = entity.getFlapScale(Mth.frac(ageInTicks));
		animate(entity.flapAnimationState, StarfireBirdAnimation.FLAP, ageInTicks, 4, flapScale);
		animateWalk(StarfireBirdAnimation.WALK, limbSwing, limbSwingAmount, 2.5f, 1 - flapScale);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
