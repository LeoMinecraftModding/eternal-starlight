package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.TwilightGazeAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.TwilightGaze;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TwilightGazeModel<T extends TwilightGaze> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("twilight_gaze"), "main");
    private final ModelPart root;

    public TwilightGazeModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 9).addBox(-2.0F, -4.0F, -0.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 12.5F, -0.2618F, 0.0F, 0.0F));

        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -4.5F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 4.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(18, 8).addBox(0.0F, -8.75F, -10.75F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-1.5F, -3.75F, -10.75F, 3.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.75F, 11.5F));

        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(20, 26).addBox(-1.5F, -2.75F, -9.75F, 3.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(0.0F, -5.75F, -9.75F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(0.0F, 3.25F, -9.75F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -11.0F));

        PartDefinition tail1 = body2.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -1.75F, -4.75F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

        tail2.addOrReplaceChild("upper_tail_fin", CubeListBuilder.create().texOffs(21, 0).addBox(0.0F, -3.25F, -9.75F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, -0.6545F, 0.0F, 0.0F));

        tail2.addOrReplaceChild("lower_tail_fin", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -1.5F, -5.75F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0.4363F, 0.0F, 0.0F));

        body1.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.5F, -3.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.0F, -2.5F, -0.2182F, 0.0F, 0.9599F));

        body1.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -0.5F, -3.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 2.0F, -2.5F, -0.2182F, 0.0F, -0.9599F));

        body1.addOrReplaceChild("body1_swell", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.75F, -10.75F, 3.0F, 5.0F, 15.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.root.xRot = -headPitch * Mth.DEG_TO_RAD;
        this.root.yRot = (netHeadYaw + 180) * Mth.DEG_TO_RAD;
        animate(entity.swimAnimationState, TwilightGazeAnimation.SWIM, ageInTicks, (float) (16.0f * entity.getDeltaMovement().length()), entity.isInWater() ? 1.0f : 1.5f);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
