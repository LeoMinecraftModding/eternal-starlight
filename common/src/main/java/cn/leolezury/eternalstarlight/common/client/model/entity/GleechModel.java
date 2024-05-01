package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.GleechAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GleechModel<T extends Gleech> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "gleech"), "main");
    private final ModelPart root;

    public GleechModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, -4.0F, -4.4F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(20, 27).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -0.4F));

        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(32, 28).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 1.9F));

        body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 29).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 2.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        root.xScale = root.yScale = root.zScale = entity.isLarval() ? 0.6f : 1f;
        if (riding) {
            root.xRot = -270 * Mth.DEG_TO_RAD;
            root.zRot = -180 * Mth.DEG_TO_RAD;
        }
        animate(entity.idleAnimationState, GleechAnimation.IDLE, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
