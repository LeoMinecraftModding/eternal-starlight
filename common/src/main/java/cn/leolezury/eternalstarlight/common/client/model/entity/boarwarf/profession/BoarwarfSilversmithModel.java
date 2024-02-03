package cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class BoarwarfSilversmithModel<T extends Boarwarf> extends AnimatedEntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "boarwarf"), "silversmith");
    private final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;

    public BoarwarfSilversmithModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
                .texOffs(26, 1).addBox(-2.0F, -2.675F, -6.675F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.175F)), PartPose.offset(0.0F, -10.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void copyPropertiesFrom(BoarwarfModel<?> model) {
        this.body.copyFrom(model.body);
        this.head.copyFrom(model.head);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return root;
    }
}
