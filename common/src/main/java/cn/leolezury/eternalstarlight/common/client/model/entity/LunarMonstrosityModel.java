package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.LunarMonstrosityAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class LunarMonstrosityModel<T extends LunarMonstrosity> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("lunar_monstrosity"), "main");
    private final ModelPart root;
    public final ModelPart stemAll;
    public final ModelPart stemMiddle;
    public final ModelPart stemTop;
    public final ModelPart head;
    private final ModelPart eye;

    public LunarMonstrosityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.stemAll = root.getChild("root").getChild("stem_all");
        this.stemMiddle = root.getChild("root").getChild("stem_all").getChild("stem_middle");
        this.stemTop = root.getChild("root").getChild("stem_all").getChild("stem_middle").getChild("stem_top");
        this.head = root.getChild("root").getChild("stem_all").getChild("stem_middle").getChild("stem_top").getChild("head");
        this.eye = root.getChild("root").getChild("stem_all").getChild("stem_middle").getChild("stem_top").getChild("head").getChild("eye");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition stem_all = root.addOrReplaceChild("stem_all", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -20.0F, -2.5F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition stem_middle = stem_all.addOrReplaceChild("stem_middle", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -20.0F, -2.5F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition stem_top = stem_middle.addOrReplaceChild("stem_top", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -20.0F, -2.5F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition head = stem_top.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        head.addOrReplaceChild("petal_down", CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, 1.0F, -20.0F, 10.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -5.0F, -0.1745F, 0.0F, 0.0F));

        head.addOrReplaceChild("petal_right", CubeListBuilder.create().texOffs(-10, 55).addBox(-0.3F, 1.0F, -5.0F, 20.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        head.addOrReplaceChild("petal_left", CubeListBuilder.create().texOffs(-10, 45).addBox(-20.0F, 0.0F, -5.0F, 20.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        head.addOrReplaceChild("petal_up", CubeListBuilder.create().texOffs(20, 20).addBox(-5.0F, 1.0F, 0.0F, 10.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 5.0F, 0.1745F, 0.0F, 0.0F));

        head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(61, 0).addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(60, 15).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("soul", CubeListBuilder.create().texOffs(52, 30).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leaf1 = root.addOrReplaceChild("leaf1", CubeListBuilder.create().texOffs(-8, 75).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.6981F, 1.5708F));

        leaf1.addOrReplaceChild("leaf_outside1", CubeListBuilder.create().texOffs(-10, 65).addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3526F));

        PartDefinition leaf2_rotated = root.addOrReplaceChild("leaf2_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2566F, 0.0F));

        PartDefinition leaf2 = leaf2_rotated.addOrReplaceChild("leaf2", CubeListBuilder.create().texOffs(-8, 75).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.6981F, 1.5708F));

        leaf2.addOrReplaceChild("leaf_outside2", CubeListBuilder.create().texOffs(-10, 65).addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3526F));

        PartDefinition leaf3_rotated = root.addOrReplaceChild("leaf3_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.5133F, 0.0F));

        PartDefinition leaf3 = leaf3_rotated.addOrReplaceChild("leaf3", CubeListBuilder.create().texOffs(-8, 75).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.6981F, 1.5708F));

        leaf3.addOrReplaceChild("leaf_outside3", CubeListBuilder.create().texOffs(-10, 65).addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3526F));

        PartDefinition leaf4_rotated = root.addOrReplaceChild("leaf4_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.5133F, 0.0F));

        PartDefinition leaf4 = leaf4_rotated.addOrReplaceChild("leaf4", CubeListBuilder.create().texOffs(-8, 75).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.6981F, 1.5708F));

        leaf4.addOrReplaceChild("leaf_outside4", CubeListBuilder.create().texOffs(-10, 65).addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3526F));

        PartDefinition leaf5_rotated = root.addOrReplaceChild("leaf5_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.2566F, 0.0F));

        PartDefinition leaf5 = leaf5_rotated.addOrReplaceChild("leaf5", CubeListBuilder.create().texOffs(-8, 75).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.6981F, 1.5708F));

        leaf5.addOrReplaceChild("leaf_outside5", CubeListBuilder.create().texOffs(-10, 65).addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3526F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.eye.visible = entity.getPhase() == 0;
        if (entity.getAttackTicks() >= 0 && entity.getAttackState() != 0 && entity.deathTime <= 0) {
            int state = entity.getAttackState();
            switch (state) {
                case -2 -> {
                    // entity.headPos = ModelUtils.getModelPosition(entity, entity.getYRot(), List.of(head, stemTop, stemMiddle, stemAll, root));
                }
                case -1 -> {
                    animate(entity.switchPhaseAnimationState, LunarMonstrosityAnimation.SWITCH_PHASE, ageInTicks);
                }
                case 1 -> {
                    animate(entity.toxicBreathAnimationState, LunarMonstrosityAnimation.TOXIC_BREATH, ageInTicks);
                    // entity.headPos = ModelUtils.getModelPosition(entity, entity.getYRot(), List.of(head, stemTop, stemMiddle, stemAll, root));
                }
                case 2 -> {
                    animate(entity.sporeAnimationState, LunarMonstrosityAnimation.SPORE, ageInTicks);
                }
                case 3 -> {
                    animate(entity.vineAnimationState, LunarMonstrosityAnimation.VINE, ageInTicks);
                }
                case 4 -> {
                    animate(entity.biteAnimationState, LunarMonstrosityAnimation.BITE, ageInTicks);
                }
                case 5 -> {
                    animate(entity.disappearAnimationState, LunarMonstrosityAnimation.DISAPPEAR, ageInTicks);
                }
                case 6 -> {
                    animate(entity.sneakAnimationState, LunarMonstrosityAnimation.SNEAK, ageInTicks);
                }
                case 7 -> {
                    animate(entity.appearAnimationState, LunarMonstrosityAnimation.APPEAR, ageInTicks);
                }
            }
        }
        if (entity.deathTime > 0) {
            animate(entity.deathAnimationState, LunarMonstrosityAnimation.DEATH, ageInTicks);
        }
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
