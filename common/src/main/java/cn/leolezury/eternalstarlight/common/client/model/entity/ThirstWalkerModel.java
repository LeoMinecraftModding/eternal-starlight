package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.ThirstWalkerAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ThirstWalker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class ThirstWalkerModel<T extends ThirstWalker> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("thirst_walker"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public ThirstWalkerModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 18).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -1.0F));

        head.addOrReplaceChild("head_outer", CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -7.0F, -8.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, 4.0F));

        PartDefinition crystals = body.addOrReplaceChild("crystals", CubeListBuilder.create().texOffs(-11, 0).addBox(-5.5F, 0.0F, -2.0F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, 0.6109F, 0.5236F, -0.6109F));

        crystals.addOrReplaceChild("rotated", CubeListBuilder.create().texOffs(-11, 0).addBox(-5.5F, 0.0F, -2.0F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -8.0F, 0.0F));

        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 30).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -8.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 30).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 4.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        animate(entity.idleAnimationState, ThirstWalkerAnimation.IDLE, ageInTicks);
        animateWalk(ThirstWalkerAnimation.WALK, limbSwing, limbSwingAmount, 2.5f, 1.2f);
        animate(entity.meleeAnimationState, entity.isIntentionalAttack() ? ThirstWalkerAnimation.INTENTIONAL_ATTACK : ThirstWalkerAnimation.ATTACK, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
