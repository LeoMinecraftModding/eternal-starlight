package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.AethersentGolemAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class AethersentGolemModel<T extends AethersentGolem> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("aethersent_golem"), "main");
	private final ModelPart root;
	private final ModelPart eye;

	public AethersentGolemModel(ModelPart root) {
		this.root = root;
		this.eye = root.getChild("head").getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -8.0F, -1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 12.0F, -3.0F));

		head.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -3.25F, -1.05F));

		PartDefinition leftAntenna = head.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(0.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, -3.5F, 2.5F));

		leftAntenna.addOrReplaceChild("left_antenna_upper", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, -1.5F, -0.5F));

		PartDefinition rightAntenna = head.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -3.5F, 2.5F));

		rightAntenna.addOrReplaceChild("right_antenna_upper", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.5F, -0.5F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(10, 16).addBox(-5.0F, -12.0F, -1.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 24.0F, -2.0F));

		PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(34, 18).mirror().addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition rightArm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(34, 18).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

		PartDefinition bow = rightArm.addOrReplaceChild("bow", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition lowerBow = bow.addOrReplaceChild("lower_bow", CubeListBuilder.create().texOffs(24, 34).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -2.5F, -0.5F, 0.0F, 0.0F, 0.2138F));

		lowerBow.addOrReplaceChild("lower_bow_body", CubeListBuilder.create().texOffs(8, 36).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(0, 34).addBox(-8.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.2138F));

		lowerBow.addOrReplaceChild("lower_bow_tip", CubeListBuilder.create().texOffs(36, 36).addBox(0.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.5F, 0.0F, 0.0F, 0.0F, -0.2138F));

		PartDefinition upperBow = bow.addOrReplaceChild("upper_bow", CubeListBuilder.create().texOffs(24, 34).mirror().addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -2.5F, -0.5F, 0.0F, 0.0F, -0.2138F));

		upperBow.addOrReplaceChild("upper_bow_body", CubeListBuilder.create().texOffs(8, 36).mirror().addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(0, 34).mirror().addBox(6.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2138F));

		upperBow.addOrReplaceChild("upper_bow_tip", CubeListBuilder.create().texOffs(36, 36).mirror().addBox(-2.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.2138F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.shootAnimationState, AethersentGolemAnimation.SHOOT, ageInTicks);
		animate(entity.shootEndAnimationState, AethersentGolemAnimation.SHOOT_END, ageInTicks);
		// from GuardianModel
		if (!entity.shootAnimationState.isStarted()) {
			Entity camera = Minecraft.getInstance().getCameraEntity();
			if (camera != null) {
				Vec3 targetEyePos = camera.getEyePosition(0.0F);
				Vec3 yetiEyePos = entity.getEyePosition(0.0F);
				if (targetEyePos.y > yetiEyePos.y) {
					this.eye.y += -1.0F;
				} else {
					this.eye.y += 0.0F;
				}

				Vec3 vec33 = entity.getViewVector(0.0F);
				vec33 = new Vec3(vec33.x, 0.0, vec33.z);
				Vec3 vec34 = (new Vec3(yetiEyePos.x - targetEyePos.x, 0.0, yetiEyePos.z - targetEyePos.z)).normalize().yRot(1.5707964F);
				double e = vec33.dot(vec34);
				this.eye.x += Mth.sqrt((float) Math.abs(e)) * 2.0F * (float) Math.signum(e);
			}
		}
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
