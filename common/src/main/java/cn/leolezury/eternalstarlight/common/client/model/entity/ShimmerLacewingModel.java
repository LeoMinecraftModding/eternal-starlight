package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.ShimmerLacewingAnimation;
import cn.leolezury.eternalstarlight.common.entity.animal.ShimmerLacewing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ShimmerLacewingModel<T extends ShimmerLacewing> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_lacewing"), "main");
    private final ModelPart root;

    public ShimmerLacewingModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.0F));

        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -6.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(17, 17).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition body5 = body4.addOrReplaceChild("body5", CubeListBuilder.create().texOffs(19, 10).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition wing7 = body3.addOrReplaceChild("wing7", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.3054F, -0.5236F, 0.0F));

        PartDefinition wing8 = body3.addOrReplaceChild("wing8", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.3054F, 0.5236F, 0.0F));

        PartDefinition wing3 = body2.addOrReplaceChild("wing3", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.5F, 1.1781F, -0.3927F, -0.4363F));

        PartDefinition wing4 = body2.addOrReplaceChild("wing4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.5F, 1.1781F, 0.3927F, 0.4363F));

        PartDefinition wing5 = body2.addOrReplaceChild("wing5", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -1.5F, -1.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.5F, 2.5F, 0.6109F, -0.3491F, -0.2356F));

        PartDefinition wing6 = body2.addOrReplaceChild("wing6", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, -1.5F, -1.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -0.5F, 2.5F, 0.6109F, 0.3491F, 0.2356F));

        PartDefinition wing1 = body1.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.5F, 1.3963F, -0.1745F, -0.6981F));

        PartDefinition wing2 = body1.addOrReplaceChild("wing2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.0F, 0.5F, 1.3963F, 0.1745F, 0.6981F));

        PartDefinition left_legs = body1.addOrReplaceChild("left_legs", CubeListBuilder.create().texOffs(14, 0).addBox(0.0F, -0.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.5F, 1.0F, 0.4712F, 0.1309F, -0.2356F));

        PartDefinition right_legs = body1.addOrReplaceChild("right_legs", CubeListBuilder.create().texOffs(14, 0).mirror().addBox(0.0F, -0.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.5F, 1.0F, 0.4712F, -0.1309F, 0.2356F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(8, 20).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, -7.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition left_eye = root.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(12, 22).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.0F, -5.0F, -9.0F, 0.9163F, 0.0F, 0.0F));

        PartDefinition right_eye = root.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(12, 22).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -5.0F, -9.0F, 0.9163F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        animate(entity.idleAnimationState, ShimmerLacewingAnimation.IDLE, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
