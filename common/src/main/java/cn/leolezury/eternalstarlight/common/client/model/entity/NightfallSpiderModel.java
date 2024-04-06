package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.NightfallSpider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class NightfallSpiderModel<T extends NightfallSpider> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "nightfall_spider"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleHindLeg;
    private final ModelPart leftMiddleHindLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public NightfallSpiderModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.rightHindLeg = root.getChild("leg1");
        this.leftHindLeg = root.getChild("leg2");
        this.rightMiddleHindLeg = root.getChild("leg3");
        this.leftMiddleHindLeg = root.getChild("leg4");
        this.rightMiddleFrontLeg = root.getChild("leg5");
        this.leftMiddleFrontLeg = root.getChild("leg6");
        this.rightFrontLeg = root.getChild("leg7");
        this.leftFrontLeg = root.getChild("leg8");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -4.0F, -6.5F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -3.5F));

        head.addOrReplaceChild("left_fang", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -6.0F, -11.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 3.5F));

        head.addOrReplaceChild("right_fang", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, -6.0F, -11.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, 3.5F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -12.0F, -1.0F, 10.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(30, 24).addBox(-3.0F, -10.0F, -3.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(23, 20).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 17.0F, 7.0F));

        partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(23, 20).mirror().addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 17.0F, 7.0F));

        partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(23, 20).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 17.0F, 4.0F));

        partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(23, 20).mirror().addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 17.0F, 4.0F));

        partdefinition.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(23, 20).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 17.0F, 1.0F));

        partdefinition.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(23, 20).mirror().addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 17.0F, 1.0F));

        partdefinition.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(23, 20).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 17.0F, -2.0F));

        partdefinition.addOrReplaceChild("leg8", CubeListBuilder.create().texOffs(23, 20).mirror().addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 17.0F, -2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.rightHindLeg.zRot = -0.7853982F;
        this.leftHindLeg.zRot = 0.7853982F;
        this.rightMiddleHindLeg.zRot = -0.58119464F;
        this.leftMiddleHindLeg.zRot = 0.58119464F;
        this.rightMiddleFrontLeg.zRot = -0.58119464F;
        this.leftMiddleFrontLeg.zRot = 0.58119464F;
        this.rightFrontLeg.zRot = -0.7853982F;
        this.leftFrontLeg.zRot = 0.7853982F;
        this.rightHindLeg.yRot = 0.7853982F;
        this.leftHindLeg.yRot = -0.7853982F;
        this.rightMiddleHindLeg.yRot = 0.3926991F;
        this.leftMiddleHindLeg.yRot = -0.3926991F;
        this.rightMiddleFrontLeg.yRot = -0.3926991F;
        this.leftMiddleFrontLeg.yRot = 0.3926991F;
        this.rightFrontLeg.yRot = -0.7853982F;
        this.leftFrontLeg.yRot = 0.7853982F;
        float n = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float o = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbSwingAmount;
        float p = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbSwingAmount;
        float q = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbSwingAmount;
        float r = Math.abs(Mth.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float s = Math.abs(Mth.sin(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * limbSwingAmount;
        float t = Math.abs(Mth.sin(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * limbSwingAmount;
        float u = Math.abs(Mth.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * limbSwingAmount;
        ModelPart leg = this.rightHindLeg;
        leg.yRot += n;
        leg = this.leftHindLeg;
        leg.yRot += -n;
        leg = this.rightMiddleHindLeg;
        leg.yRot += o;
        leg = this.leftMiddleHindLeg;
        leg.yRot += -o;
        leg = this.rightMiddleFrontLeg;
        leg.yRot += p;
        leg = this.leftMiddleFrontLeg;
        leg.yRot += -p;
        leg = this.rightFrontLeg;
        leg.yRot += q;
        leg = this.leftFrontLeg;
        leg.yRot += -q;
        leg = this.rightHindLeg;
        leg.zRot += r;
        leg = this.leftHindLeg;
        leg.zRot += -r;
        leg = this.rightMiddleHindLeg;
        leg.zRot += s;
        leg = this.leftMiddleHindLeg;
        leg.zRot += -s;
        leg = this.rightMiddleFrontLeg;
        leg.zRot += t;
        leg = this.leftMiddleFrontLeg;
        leg.zRot += -t;
        leg = this.rightFrontLeg;
        leg.zRot += u;
        leg = this.leftFrontLeg;
        leg.zRot += -u;
    }
}
