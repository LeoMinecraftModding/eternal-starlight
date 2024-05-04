package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.LuminoFishAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.LuminoFish;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class LuminoFishModel<T extends LuminoFish> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("luminofish"), "main");
    private final ModelPart head;

    public LuminoFishModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        head.addOrReplaceChild("head_swell", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -3.5F, -4.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = head.addOrReplaceChild("body", CubeListBuilder.create().texOffs(15, 14).addBox(-1.0F, -1.0F, -0.4F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 1.0F));

        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 9).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        head.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 0).addBox(-0.1F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.0F, -1.0F));

        head.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 2).addBox(-1.9F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.0F, -1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        animate(entity.swimAnimationState, LuminoFishAnimation.SWIM, ageInTicks, 1.0f, entity.isInWater() ? 1.0f : 1.5f);
        if (entity.getSwellTicks() > 0) {
            animate(entity.swellAnimationState, LuminoFishAnimation.SWELL, ageInTicks);
        }
    }

    @Override
    public ModelPart root() {
        return head;
    }
}
