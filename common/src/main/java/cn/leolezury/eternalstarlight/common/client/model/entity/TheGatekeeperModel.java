package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.TheGatekeeperAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

@Environment(EnvType.CLIENT)
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
    private final boolean slim;

    public TheGatekeeperModel(ModelPart root, boolean slim) {
        this.root = root.getChild("root");
        this.body = root.getChild("root").getChild("body");
        this.head = body.getChild("head");
        this.leftArm = body.getChild("left_arm");
        this.rightArm = body.getChild("right_arm");
        this.leftHand = leftArm.getChild("left_hand");
        this.rightHand = rightArm.getChild("right_hand");
        this.slim = slim;
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
            PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(5.0F, -10.0F, 0.0F));

            leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(0.5F, 8.0F, 0.0F));

            leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(-5.0F, -10.0F, 0.0F));

            rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-0.5F, 8.0F, 0.0F));

            rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        } else {
            PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(5.0F, -10.0F, 0.0F));

            leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

            leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation)), PartPose.offset(-5.0F, -10.0F, 0.0F));

            rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

            rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
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
        animateWalk(entity.getMainHandItem().isEmpty() ? TheGatekeeperAnimation.WALK_1 : TheGatekeeperAnimation.WALK_2, limbSwing, limbSwingAmount, 2.5f, 1.2f);
        if (entity.getAttackTicks() >= 0 && entity.getAttackState() != 0 && entity.deathTime <= 0) {
            int state = entity.getAttackState();
            switch (state) {
                case GatekeeperMeleePhase.ID -> {
                    leftArm.resetPose();
                    rightArm.resetPose();
                    animate(entity.meleeAnimationStateA, TheGatekeeperAnimation.ATTACK_1, ageInTicks);
                    animate(entity.meleeAnimationStateB, TheGatekeeperAnimation.ATTACK_2, ageInTicks);
                    animate(entity.meleeAnimationStateC, TheGatekeeperAnimation.ATTACK_3, ageInTicks);
                }
                case GatekeeperDodgePhase.ID -> {
                    animate(entity.dodgeAnimationState, TheGatekeeperAnimation.DODGE, ageInTicks);
                }
                case GatekeeperDashPhase.ID -> {
                    leftArm.resetPose();
                    rightArm.resetPose();
                    animate(entity.dashAnimationState, TheGatekeeperAnimation.DASH, ageInTicks);
                }
                case GatekeeperCastFireballPhase.ID -> {
                    animate(entity.castFireballAnimationState, TheGatekeeperAnimation.CAST_FIREBALL, ageInTicks);
                }
                case GatekeeperDanceFightPhase.ID -> {
                    leftArm.resetPose();
                    rightArm.resetPose();
                    animate(entity.danceFightAnimationState, TheGatekeeperAnimation.DANCE_FIGHT, ageInTicks);
                }
                case GatekeeperSwingSwordPhase.ID -> {
                    leftArm.resetPose();
                    rightArm.resetPose();
                    animate(entity.swingSwordAnimationState, TheGatekeeperAnimation.SWING_SWORD, ageInTicks);
                }
                case GatekeeperComboPhase.ID -> {
                    leftArm.resetPose();
                    rightArm.resetPose();
                    animate(entity.comboAnimationState, TheGatekeeperAnimation.COMBO, ageInTicks);
                }
            }
        }
    }

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
        stack.translate(0, -0.625, 0);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
