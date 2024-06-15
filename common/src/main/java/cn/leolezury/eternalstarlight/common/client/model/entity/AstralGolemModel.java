package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class AstralGolemModel<T extends AstralGolem> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "main");
    public static final ModelLayerLocation INNER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "inner_armor");
    public static final ModelLayerLocation OUTER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "outer_armor");
    private float armXRot = 0;
    private int tintColor = -1;

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
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        AstralGolemMaterial material = entity.getMaterial();
        tintColor = material == null ? -1 : material.tintColor();
        if (entity.getMainHandItem().isEmpty()) {
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
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.leftArmPose = ArmPose.EMPTY;
        int animationTick = entity.getAttackAnimationTick();
        if (animationTick > 0) {
            armXRot = -2.0F + 1.5F * Mth.triangleWave((float)animationTick - partialTick, 10.0F);
        } else {
            armXRot = 0;
        }
        if (entity.isGolemBlocking()) {
            this.leftArmPose = ArmPose.BLOCK;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, tintColor == -1 ? color : FastColor.ARGB32.multiply(color, tintColor));
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, tintColor == -1 ? color : FastColor.ARGB32.multiply(color, tintColor));
        rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, tintColor == -1 ? color : FastColor.ARGB32.multiply(color, tintColor));
        leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, tintColor == -1 ? color : FastColor.ARGB32.multiply(color, tintColor));
    }
}