package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.GrimstoneGolemAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

@Environment(EnvType.CLIENT)
public class GrimstoneGolemModel<T extends GrimstoneGolem> extends AnimatedEntityModel<T> implements ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "grimstone_golem"), "main");
    private final ModelPart root;
    private final ModelPart body;

    public GrimstoneGolemModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("root").getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -12.0F, -5.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -16.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(9, 44).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(26, 10).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 0.0F));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -3.0F, 0.0F));

        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 29).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -8.0F, 0.0F));

        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 29).mirror().addBox(-0.5F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, -8.0F, 0.0F));

        root.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-0.5F, 0.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, -15.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        root.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -15.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        animate(entity.raiseArmsAnimationState, GrimstoneGolemAnimation.RAISE_ARMS, ageInTicks);
        animate(entity.lowerArmsAnimationState, GrimstoneGolemAnimation.LOWER_ARMS, ageInTicks);
        animate(entity.displayAnimationState, GrimstoneGolemAnimation.DISPLAY, ageInTicks);
        animateWalk(GrimstoneGolemAnimation.WALK, limbSwing, limbSwingAmount, 2.5f, 1.2f);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        poseStack.translate(0.0F, 0.0625F, 0.1875F);
        poseStack.scale(0.7F, 0.7F, 0.7F);
        poseStack.translate(0.0625F, 0.6F, -0.7F);
    }
}
