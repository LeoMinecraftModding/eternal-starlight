package cn.leolezury.eternalstarlight.client.model;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.animation.StarlightGolemAnimation;
import cn.leolezury.eternalstarlight.client.model.animation.model.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.entity.boss.StarlightGolem;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StarlightGolemModel<T extends StarlightGolem> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "starlight_golem"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public StarlightGolemModel(ModelPart root) {
        this.root = root;
        head = root.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -34.0F, -8.0F, 20.0F, 30.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(84, 83).addBox(-8.0F, -3.0F, -4.0F, 7.0F, 27.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 70).addBox(-8.0F, 24.0F, -4.0F, 7.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -29.0F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 91).addBox(1.0F, -3.0F, -4.0F, 7.0F, 27.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(46, 70).addBox(1.0F, 24.0F, -4.0F, 7.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -29.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 33).addBox(-7.0F, -10.5F, -6.5F, 14.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -30.5F, -3.5F));

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 46).addBox(-12.0F, -4.0F, -10.0F, 24.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        head.xRot = headPitch * ((float)Math.PI / 180F);
        if (entity.getAttackTicks() >= 0 && entity.getAttackState() != 0 && entity.deathTime <= 0) {
            int state = entity.getAttackState();
            switch (state) {
                case 1 -> {
                    animate(entity.energyBeamAnimationState, StarlightGolemAnimation.BEAM, ageInTicks);
                }
                case 2 -> {
                    animate(entity.flameAnimationState, StarlightGolemAnimation.FLAME, ageInTicks);
                }
                case 3 -> {
                    animate(entity.shakeGroundAnimationState, StarlightGolemAnimation.SHAKE_GROUND, ageInTicks);
                }
                case 4 -> {
                    animate(entity.startPowerAnimationState, StarlightGolemAnimation.POWER_START, ageInTicks);
                }
                case 5 -> {
                    animate(entity.powerAnimationState, StarlightGolemAnimation.POWER, ageInTicks);
                }
                case 6 -> {
                    animate(entity.endPowerAnimationState, StarlightGolemAnimation.POWER_END, ageInTicks);
                }
            }
        }
        if (entity.deathTime > 0) {
            animate(entity.deathAnimationState, StarlightGolemAnimation.DEATH, ageInTicks);
        }
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
