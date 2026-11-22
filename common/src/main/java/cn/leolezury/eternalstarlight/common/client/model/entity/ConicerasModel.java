package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.ConicerasAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Coniceras;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ConicerasModel<T extends Coniceras> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("coniceras"), "main");
	private final ModelPart root;

	public ConicerasModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		root.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(24, 25).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(24, 15).addBox(-3.5F, -3.0F, -4.0F, 7.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(0, 15).addBox(-3.0F, -3.0F, -10.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(0, 26).addBox(-2.0F, -3.0F, -15.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(32, 34).addBox(-1.5F, -3.0F, -19.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(0, 35).addBox(-1.0F, -3.0F, -22.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(24, 25).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.01F, 0.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

		body.addOrReplaceChild("inner_mouth", CubeListBuilder.create().texOffs(18, 34).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 10.0F));

		body.addOrReplaceChild("mouth_top", CubeListBuilder.create().texOffs(30, 0).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offset(0.0F, 1.0F, 10.0F));

		body.addOrReplaceChild("mouth_bottom", CubeListBuilder.create().texOffs(30, 7).addBox(-2.5F, -0.01F, -0.1F, 5.0F, 2.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offset(0.0F, 1.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity.isCharging()) {
			animate(entity.chargeAnimationState, ConicerasAnimation.CHARGE, ageInTicks);
		}
	}
}