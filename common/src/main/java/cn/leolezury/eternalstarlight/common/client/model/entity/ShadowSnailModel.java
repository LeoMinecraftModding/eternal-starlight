package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.ShadowSnailAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShadowSnail;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ShadowSnailModel<T extends ShadowSnail> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("shadow_snail"), "main");
	private final ModelPart root;

	public ShadowSnailModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(22, 7).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0125F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		body.addOrReplaceChild("left_feeler", CubeListBuilder.create().texOffs(1, 12).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0125F, -4.0F));

		body.addOrReplaceChild("right_feeler", CubeListBuilder.create().texOffs(1, 12).mirror().addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -3.0125F, -4.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(ShadowSnailAnimation.WALK, limbSwing, limbSwingAmount, 3.0f, 1.25f);
		this.root.xRot = 0;
		if (young) {
			root.xScale = 0.5f;
			root.yScale = 0.5f;
			root.zScale = 0.5f;
		}
		switch (entity.getHideState()) {
			case 1 -> this.animate(entity.hideStartAnimationState, ShadowSnailAnimation.HIDE_START, ageInTicks);
			case 2 -> this.animate(entity.hideAnimationState, ShadowSnailAnimation.HIDE, ageInTicks);
			case 3 -> this.animate(entity.hideEndAnimationState, ShadowSnailAnimation.HIDE_END, ageInTicks);
		}
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
