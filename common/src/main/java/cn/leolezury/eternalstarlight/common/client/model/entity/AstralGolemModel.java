package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.AstralGolemRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

@Environment(EnvType.CLIENT)
public class AstralGolemModel extends HumanoidModel<AstralGolemRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "main");
	public static final ModelLayerLocation INNER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "inner_armor");
	public static final ModelLayerLocation OUTER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "outer_armor");

	public AstralGolemModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 26.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 26.0F, 0.0F));

		// useless model parts to replace vanilla model parts
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(AstralGolemRenderState state) {
		super.setupAnim(state);
		float armXRot;
		if (state.attackAnimationTick > 0) {
			armXRot = -2.0F + 1.5F * Mth.triangleWave(state.attackAnimationTick, 10.0F);
		} else {
			armXRot = 0;
		}
		if (state.getMainHandItem().isEmpty()) {
			leftArm.xRot += armXRot;
			rightArm.xRot += armXRot;
		} else {
			rightArm.xRot = rightArm.xRot - 1.57f - armXRot;
		}
		this.head.y += 12.0F;
		this.hat.y += 12.0F;
		this.head.visible = true;
		this.body.y += 12.0F;
		this.leftArm.y += 12.0F;
		this.rightArm.y += 12.0F;
	}

	@Override
	protected ArmPose getArmPose(AstralGolemRenderState state, HumanoidArm arm) {
		if (arm == HumanoidArm.LEFT && state.isBlocking) {
			return ArmPose.BLOCK;
		}
		return ArmPose.EMPTY;
	}
}