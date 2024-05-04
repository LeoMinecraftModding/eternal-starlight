package cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class BoarwarfDruidModel<T extends Boarwarf> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("boarwarf"), "druid");
    private final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart leftArm;
    public final ModelPart rightArm;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart leftEar;

    public BoarwarfDruidModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftArm = body.getChild("left_arm");
        this.rightArm = body.getChild("right_arm");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftEar = head.getChild("left_ear");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, 1.0F, -3.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.75F))
                .texOffs(0, 32).addBox(-5.0F, 1.0F, -3.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        body.addOrReplaceChild("flower_body", CubeListBuilder.create().texOffs(0, 60).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.5F, 6.5F, -3.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(52, 21).addBox(-1.0F, 4.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.0F, -6.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        head.addOrReplaceChild("flower_head", CubeListBuilder.create().texOffs(0, 60).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.5F, -7.5F, 0.5F, -1.5708F, -0.3927F, 1.5708F));

        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(36, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-6.0F, 3.0F, 0.0F));

        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(6.0F, 3.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 17).addBox(-3.1F, 0.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 14.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 17).addBox(-1.9F, 0.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(1.9F, 14.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void copyPropertiesFrom(BoarwarfModel<?> model) {
        this.body.copyFrom(model.body);
        this.head.copyFrom(model.head);
        this.leftArm.copyFrom(model.leftArm);
        this.rightArm.copyFrom(model.rightArm);
        this.leftLeg.copyFrom(model.leftLeg);
        this.rightLeg.copyFrom(model.rightLeg);
        this.leftEar.copyFrom(model.leftEar);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return root;
    }
}
