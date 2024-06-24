package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.CrystallizedMothAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.CrystallizedMoth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CrystallizedMothModel<T extends CrystallizedMoth> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("crystallized_moth"), "main");
	private final ModelPart root;
	private final ModelPart body;

	public CrystallizedMothModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(34, 26).addBox(-4.5F, -4.5F, -6.25F, 9.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(0, 26).addBox(-3.5F, -3.5F, -1.25F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, 2.25F));

		body.addOrReplaceChild("left_legs", CubeListBuilder.create().texOffs(36, 1).mirror().addBox(0.0F, 0.0F, -4.5F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 3.5F, 2.25F, 0.0F, 0.0F, 1.1781F));

		body.addOrReplaceChild("right_legs", CubeListBuilder.create().texOffs(36, 1).addBox(-7.0F, 0.0F, -4.5F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 3.5F, 2.25F, 0.0F, 0.0F, -1.1781F));

		body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 43).addBox(-3.5F, -4.5F, -5.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(1.5F, -2.5F, -6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-4.5F, -2.5F, -6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(19, 46).addBox(-3.5F, 2.5F, -5.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -6.25F));

		body.addOrReplaceChild("left_upper_wing", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, 0.0F, -3.5F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.5F, -2.75F, 0.0F, 0.0F, -0.48F));

		body.addOrReplaceChild("left_lower_wing", CubeListBuilder.create().texOffs(27, 40).addBox(-1.0F, 0.0F, -0.5F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.5F, -5.75F, 0.0F, 0.0F, 0.4363F));

		body.addOrReplaceChild("right_upper_wing", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-16.0F, 0.0F, -3.5F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -3.5F, -2.75F, 0.0F, 0.0F, 0.48F));

		body.addOrReplaceChild("right_lower_wing", CubeListBuilder.create().texOffs(27, 40).mirror().addBox(-8.0F, 0.0F, -0.5F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -3.5F, -5.75F, 0.0F, 0.0F, -0.4363F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		body.xRot += headPitch * Mth.DEG_TO_RAD;
		body.yRot += netHeadYaw * Mth.DEG_TO_RAD;
		animate(entity.idleAnimationState, CrystallizedMothAnimation.IDLE, ageInTicks, 1, entity.getAttackTicks() > 0 ? 1.2f : 1f);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
