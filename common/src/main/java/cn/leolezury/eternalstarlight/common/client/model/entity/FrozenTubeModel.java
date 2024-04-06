package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FrozenTubeModel<T extends FrozenTube> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "frozen_tube"), "main");
    private final ModelPart rod;

    public FrozenTubeModel(ModelPart root) {
        this.rod = root.getChild("rod");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rod = partdefinition.addOrReplaceChild("rod", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 23.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rod.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        rod.xRot = headPitch * Mth.DEG_TO_RAD;
    }

    @Override
    public RenderType renderType(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucent(resourceLocation);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rod.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
