package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.LuminarisAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Luminaris;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LuminarisModel<T extends Luminaris> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("luminaris"), "main");
	private final ModelPart head;

	public LuminarisModel(ModelPart root) {
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(13, 0).addBox(-3.0F, -2.9F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(13, 0).addBox(1.0F, -2.9F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(6, 10).addBox(-1.5F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(6, 10).addBox(1.5F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(10, 8).addBox(-1.5F, -0.2929F, -4.7071F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, -1.0F, -0.7854F, 0.0F, 0.0F));

		head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition body = head.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 2.0F));

		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		head.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(10, 13).addBox(0.0F, 0.5F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 1.0F));

		head.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(10, 13).addBox(-2.0F, 0.5F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.swimAnimationState, LuminarisAnimation.SWIM, ageInTicks, 1.0f, entity.isInWater() ? 1.0f : 1.5f);
		if (entity.isCharging()) {
			animate(entity.chargeAnimationState, LuminarisAnimation.CHARGE, ageInTicks);
		}
	}

	@Override
	public ModelPart root() {
		return head;
	}
}
