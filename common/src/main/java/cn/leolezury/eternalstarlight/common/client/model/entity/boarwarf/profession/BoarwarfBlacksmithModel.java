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
public class BoarwarfBlacksmithModel<T extends Boarwarf> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("boarwarf"), "blacksmith");
    private final ModelPart root;
    public final ModelPart body;
    public final ModelPart leftArm;
    public final ModelPart rightArm;

    public BoarwarfBlacksmithModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.leftArm = body.getChild("left_arm");
        this.rightArm = body.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(0, 32).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(36, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-6.0F, -8.0F, 0.0F));

        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(6.0F, -8.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void copyPropertiesFrom(BoarwarfModel<?> model) {
        this.body.copyFrom(model.body);
        this.leftArm.copyFrom(model.leftArm);
        this.rightArm.copyFrom(model.rightArm);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return root;
    }
}
