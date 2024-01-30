package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.YetiAnimation;
import cn.leolezury.eternalstarlight.common.entity.animal.Yeti;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class YetiModel<T extends Yeti> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "yeti"), "main");
    private final ModelPart root;
    private final ModelPart eye;

    public YetiModel(ModelPart root) {
        this.root = root.getChild("root");
        this.eye = root.getChild("root").getChild("head").getChild("eye");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 2.0F, 1.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 2.0F, 1.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -13.0F, -4.0F, 11.0F, 13.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(8, 8).addBox(-5.5F, -13.0F, -5.0F, 11.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(31, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -4.0F));

        PartDefinition eye = head.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -10.0F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, YetiAnimation.IDLE, ageInTicks);
        if (entity.getRollState() != 2) {
            this.animateWalk(YetiAnimation.WALK, limbSwing, limbSwingAmount, 5.0f, 1f);
        }
        this.root.xRot = 0;
        if (entity.getRollState() != 0) {
            switch (entity.getRollState()) {
                case 1 -> this.animate(entity.rollStartAnimationState, YetiAnimation.ROLL_START, ageInTicks);
                case 2 -> {
                    this.animate(entity.rollAnimationState, YetiAnimation.ROLL, ageInTicks);
                    float rollAngle = Mth.rotLerp(Minecraft.getInstance().getFrameTime(), entity.prevRollAngle, entity.rollAngle);
                    this.root.xRot = rollAngle * Mth.DEG_TO_RAD;
                }
                case 3 -> {
                    this.animate(entity.rollEndAnimationState, YetiAnimation.ROLL_END, ageInTicks);
                    float progress = Math.min(10f, entity.rollEndAnimationState.getAccumulatedTime() / 1000f * 20f) / 10f;
                    float rollAngle = Mth.rotLerp(progress, entity.rollAngle, 0);
                    this.root.xRot = rollAngle * Mth.DEG_TO_RAD;
                }
            }
        }
        // from GuardianModel
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
            this.eye.x += Mth.sqrt((float)Math.abs(e)) * 2.0F * (float)Math.signum(e);
        }
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
