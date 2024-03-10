package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.FreezeAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class FreezeModel<T extends Freeze> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "freeze"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public FreezeModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        rods.addOrReplaceChild("rod1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, -0.5F, -6.0F, -0.5672F, 0.0F, 0.0F));

        rods.addOrReplaceChild("rod2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-6.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.5672F));

        rods.addOrReplaceChild("rod3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(6.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

        rods.addOrReplaceChild("rod4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, -0.5F, 6.0F, 0.5672F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        animate(entity.idleAnimationState, FreezeAnimation.IDLE, ageInTicks);
        if (entity.isAggressive()) {
            animate(entity.idleAnimationState, FreezeAnimation.IDLE_AGGRESSIVE, ageInTicks);
        }
        if (entity.isAttacking()) {
            animate(entity.throwAnimationState, FreezeAnimation.THROW, ageInTicks);
        }
    }

    @Override
    public RenderType renderType(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucent(resourceLocation);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
