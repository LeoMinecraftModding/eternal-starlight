package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.TheGatekeeperAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

import java.util.List;
import java.util.stream.Stream;

public class TheGatekeeperModel<T extends TheGatekeeper> extends AnimatedEntityModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper"), "main");
	public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper_slim"), "main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper"), "outer");
	public static final ModelLayerLocation SLIM_OUTER_LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper_slim"), "outer");

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftHand;
	private final ModelPart rightHand;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final boolean slim;
	public final List<String> allPartNames;

	public float alphaFactor = 1;

	public TheGatekeeperModel(ModelPart root, boolean slim) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.leftArm = this.body.getChild("left_arm");
		this.rightArm = this.body.getChild("right_arm");
		this.leftHand = this.leftArm.getChild("left_hand");
		this.rightHand = this.rightArm.getChild("right_hand");
		this.leftLeg = this.root.getChild("left_leg");
		this.rightLeg = this.root.getChild("right_leg");
		this.slim = slim;
		this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
	}

	public static LayerDefinition createBodyLayer(boolean slim) {
		return createBodyLayer(slim, 0);
	}

	public static LayerDefinition createBodyLayer(boolean slim, float deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		if (slim) {
			PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)), PartPose.offset(5.0F, -10.0F, 0.0F));

			leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(0.5F, 8.0F, 0.0F));

			leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

			PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)), PartPose.offset(-5.0F, -10.0F, 0.0F));

			rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-0.5F, 8.0F, 0.0F));

			rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		} else {
			PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)), PartPose.offset(5.0F, -10.0F, 0.0F));

			leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

			leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

			PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)), PartPose.offset(-5.0F, -10.0F, 0.0F));

			rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

			rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		}

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(deformation)), PartPose.offset(0.0F, -12.0F, 0.0F));

		head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(deformation + 0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(2.0F, 0.0F, 0.0F));

		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(-2.0F, 0.0F, 0.0F));

		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root.getAllParts().forEach(ModelPart::resetPose);
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		if (!entity.isActivated()) {
			animate(entity.sitAnimationState, TheGatekeeperAnimation.SIT, ageInTicks);
			animate(entity.standAnimationState, TheGatekeeperAnimation.STAND, ageInTicks);
			animate(entity.talkAnimationState, TheGatekeeperAnimation.TALK, ageInTicks);
		} else if (entity.deathTime <= 0) {
			if (entity.getBehaviorState() != GatekeeperStepBackPhase.ID
				&& entity.getBehaviorState() != GatekeeperJumpEndPhase.ID
				&& entity.getBehaviorState() != GatekeeperGreatswordPhase.ID
				&& entity.getBehaviorState() != GatekeeperHammerPhase.ID
				&& entity.getBehaviorState() != GatekeeperDashPhase.ID
				&& entity.getBehaviorState() != GatekeeperGreatswordComboPhase.ID) {
				animateWalk(TheGatekeeperAnimation.RUN, limbSwing, limbSwingAmount, 2f, 1f);
			}
			if (entity.getBehaviorTicks() >= 0 && entity.getBehaviorState() != 0) {
				int state = entity.getBehaviorState();
				switch (state) {
					case GatekeeperStepBackPhase.ID -> animate(entity.stepBackAnimationState, TheGatekeeperAnimation.STEP_BACK, ageInTicks);
					case GatekeeperJumpStartPhase.ID -> animate(entity.jumpStartAnimationState, TheGatekeeperAnimation.JUMP_START, ageInTicks);
					case GatekeeperJumpTransitionPhase.ID -> animate(entity.jumpTransitionAnimationState, TheGatekeeperAnimation.JUMP_TRANSITION, ageInTicks);
					case GatekeeperJumpEndPhase.ID -> animate(entity.jumpEndAnimationState, TheGatekeeperAnimation.JUMP_END, ageInTicks);
					case GatekeeperGreatswordPhase.ID -> animate(entity.greatswordAnimationState, TheGatekeeperAnimation.GREATSWORD, ageInTicks);
					case GatekeeperHammerPhase.ID -> animate(entity.hammerAnimationState, TheGatekeeperAnimation.HAMMER, ageInTicks);
					case GatekeeperDashPhase.ID -> animate(entity.dashAnimationState, TheGatekeeperAnimation.DASH, ageInTicks);
					case GatekeeperGreatswordComboPhase.ID -> animate(entity.greatswordComboAnimationState, TheGatekeeperAnimation.GREATSWORD_COMBO, ageInTicks);
					case GatekeeperBowPhase.ID -> animate(entity.bowAnimationState, TheGatekeeperAnimation.BOW, ageInTicks);
					case GatekeeperBowComboPhase.ID -> animate(entity.bowComboAnimationState, TheGatekeeperAnimation.BOW_COMBO, ageInTicks);
					case GatekeeperCastFireballPhase.ID -> animate(entity.castFireballAnimationState, TheGatekeeperAnimation.MAGIC, ageInTicks);
					case GatekeeperTeleportPhase.ID -> animate(entity.teleportAnimationState, TheGatekeeperAnimation.MAGIC, ageInTicks);
					case GatekeeperEatPhase.ID -> animate(entity.eatAnimationState, TheGatekeeperAnimation.EAT, ageInTicks);
					case GatekeeperEatFailPhase.ID -> animate(entity.eatFailAnimationState, TheGatekeeperAnimation.EAT_FAIL, ageInTicks);
				}
			} else {
				animate(entity.blockAnimationState, TheGatekeeperAnimation.BLOCK, ageInTicks);
			}
		}
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		root.translateAndRotate(stack);
		body.translateAndRotate(stack);
		ModelPart armPart = arm == HumanoidArm.LEFT ? leftArm : rightArm;
		ModelPart handPart = arm == HumanoidArm.LEFT ? leftHand : rightHand;
		armPart.translateAndRotate(stack);
		if (this.slim) {
			float f = 0.5F * (float) (arm == HumanoidArm.RIGHT ? 1 : -1);
			handPart.x += f;
			handPart.translateAndRotate(stack);
			handPart.x -= f;
		} else {
			handPart.translateAndRotate(stack);
		}
		stack.translate(0, -0.55, 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public RenderType renderType(ResourceLocation resourceLocation) {
		return RenderType.entityTranslucent(resourceLocation);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, FastColor.ARGB32.color(Math.round(FastColor.ARGB32.alpha(color) * alphaFactor), FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color)));
	}
}
