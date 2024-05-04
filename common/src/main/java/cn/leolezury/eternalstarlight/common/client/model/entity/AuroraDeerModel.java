package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.AuroraDeerAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.AuroraDeer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class AuroraDeerModel<T extends AuroraDeer> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("aurora_deer"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftHorn;
    private final ModelPart rightHorn;

    public AuroraDeerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = root.getChild("root").getChild("body").getChild("neck").getChild("head");
        this.leftHorn = head.getChild("left_horn");
        this.rightHorn = head.getChild("right_horn");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.153F, -10.6325F, 8.0F, 7.0F, 20.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -8.847F, 0.6325F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.153F, 9.3675F, -0.5672F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -7.153F, -8.6325F));

        neck.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 30).addBox(-2.0F, -0.9F, -2.2F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 27).addBox(-2.0F, -6.1F, -6.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-2.0F, -3.1F, -9.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(10, 1).addBox(-5.5F, -1.5F, -0.4F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -5.5F, 2.0F, 0.0F, -0.3491F, 0.0F));

        head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 12).addBox(-0.3F, -1.5F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -5.5F, 2.0F, 0.0F, 0.3491F, 0.0F));

        head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(16, 20).addBox(-0.5F, -7.0F, -3.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.0F, 0.5F, -0.3927F, 0.1745F, 0.2138F));

        head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, -7.0F, -3.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.0F, 0.5F, -0.3927F, -0.1745F, -0.2138F));

        root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(20, 38).addBox(-1.5F, -1.0F, -2.25F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(9, 46).addBox(-1.5F, 4.0F, -1.25F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -9.0F, -7.65F));

        root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(34, 38).addBox(-1.5F, -1.0F, -2.25F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(45, 44).addBox(-1.5F, 4.0F, -1.25F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -9.0F, -7.65F));

        root.addOrReplaceChild("left_rear_leg", CubeListBuilder.create().texOffs(36, 9).addBox(-1.5F, -1.0F, -2.25F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-1.5F, 4.0F, -1.25F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -9.0F, 8.15F));

        root.addOrReplaceChild("right_rear_leg", CubeListBuilder.create().texOffs(42, 27).addBox(-1.5F, 4.0F, -1.25F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-1.5F, -1.0F, -2.25F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -9.0F, 8.15F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        if (young) {
            root.xScale = 0.8f;
            root.yScale = 0.8f;
            root.zScale = 0.8f;
            head.xScale = 1.25f;
            head.yScale = 1.25f;
            head.zScale = 1.25f;
        }
        leftHorn.visible = entity.hasLeftHorn();
        rightHorn.visible = entity.hasRightHorn();
        this.animate(entity.idleAnimationState, AuroraDeerAnimation.IDLE, ageInTicks);
        this.animateWalk(AuroraDeerAnimation.WALK, limbSwing, limbSwingAmount, young ? 3.0f : 5.0f, 1.0f);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
