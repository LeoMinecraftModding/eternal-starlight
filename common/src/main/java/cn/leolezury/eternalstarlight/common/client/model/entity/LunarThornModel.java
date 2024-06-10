package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class LunarThornModel<T extends LunarThorn> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("lunar_thorn"), "main");

    private final ModelPart thorn;

    public LunarThornModel(ModelPart root) {
        this.thorn = root.getChild("thorn");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition thorn = partdefinition.addOrReplaceChild("thorn", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, -2.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -14.0F, 2.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        thorn.addOrReplaceChild("rotated1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, 2.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -14.0F, -2.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        thorn.addOrReplaceChild("rotated2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        thorn.addOrReplaceChild("rotated3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void scale(float scale) {
        this.thorn.xScale = scale;
        this.thorn.yScale = scale;
        this.thorn.zScale = scale;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        thorn.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
