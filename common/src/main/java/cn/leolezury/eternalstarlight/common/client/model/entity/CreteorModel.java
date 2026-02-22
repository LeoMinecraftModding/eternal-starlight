package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.CreteorAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CreteorModel<T extends Creteor> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("creteor"), "main");
	public static final ModelLayerLocation ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("creteor"), "armor");
	private final ModelPart root;
	private final ModelPart rotator;

	public CreteorModel(ModelPart root) {
		this.root = root.getChild("root");
		this.rotator = root.getChild("root").getChild("rotator");
	}

	public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition rotator = root.addOrReplaceChild("rotator", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 16.0F, 6.0F, deformation), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition crystal1 = rotator.addOrReplaceChild("crystal1", CubeListBuilder.create().texOffs(29, 1).addBox(-6.0F, -6.0F, 0.0F, 10.0F, 10.0F, 0.0F, deformation), PartPose.offset(-4.0F, -12.0F, 0.0F));

		crystal1.addOrReplaceChild("crystal2", CubeListBuilder.create().texOffs(29, 12).addBox(-4.5F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, deformation), PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

		PartDefinition crystal3 = rotator.addOrReplaceChild("crystal3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -6.0F, 1.0F, 0.0F, 0.0F, -0.2618F));

		crystal3.addOrReplaceChild("crystal4", CubeListBuilder.create().texOffs(25, 21).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F, deformation).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		crystal3.addOrReplaceChild("crystal5", CubeListBuilder.create().texOffs(25, 21).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F, deformation).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, -0.7854F));

		rotator.addOrReplaceChild("crystal6", CubeListBuilder.create().texOffs(29, 26).addBox(-2.5F, -4.0F, 0.0F, 5.0F, 4.0F, 0.0F, deformation), PartPose.offsetAndRotation(4.0F, -6.0F, -2.0F, -0.4363F, 0.0F, 1.5708F));

		PartDefinition leftLeg = rotator.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 46).addBox(-0.5F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, deformation), PartPose.offsetAndRotation(0.5F, 4.0F, 0.0F, 0.0F, 0.0F, -0.2138F));

		leftLeg.addOrReplaceChild("crystal8", CubeListBuilder.create().texOffs(25, 21).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F, deformation).mirror(false), PartPose.offsetAndRotation(3.5F, 3.0F, 0.0F, 1.5708F, 0.0F, 0.3927F));

		PartDefinition rightLeg = rotator.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, deformation), PartPose.offsetAndRotation(-0.5F, 4.0F, 0.0F, 0.0F, 0.0F, 0.2138F));

		rightLeg.addOrReplaceChild("crystal7", CubeListBuilder.create().texOffs(29, 26).mirror().addBox(-4.0F, -4.0F, 0.0F, 5.0F, 4.0F, 0.0F, deformation).mirror(false), PartPose.offsetAndRotation(-4.5F, 4.0F, 0.0F, 0.4363F, 0.0F, -1.5708F));

		rotator.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -3.0F, 0.0F, 8.0F, 6.0F, 6.0F, deformation), PartPose.offset(4.0F, -1.0F, -3.0F));

		rotator.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 22).addBox(-8.0F, -3.0F, 0.0F, 8.0F, 6.0F, 6.0F, deformation), PartPose.offset(-4.0F, -1.0F, -3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.idleAnimationState, CreteorAnimation.IDLE, ageInTicks);
		rotator.xRot = -90 * Mth.DEG_TO_RAD;
		rotator.zRot = 90 * Mth.DEG_TO_RAD;
		rotator.yRot = -(Mth.rotLerp(Mth.frac(ageInTicks), entity.prevRollAngle, entity.rollAngle) + 90) * Mth.DEG_TO_RAD;
		if (entity.getSwell() >= 80) {
			root.yRot = Mth.lerp(Mth.frac(ageInTicks), entity.prevSpin, entity.spin) * Mth.DEG_TO_RAD;
		}
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
