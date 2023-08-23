package cn.leolezury.eternalstarlight.client.model.armor;

import cn.leolezury.eternalstarlight.EternalStarlight;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ThermalSpringStoneArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
    public static final ModelLayerLocation INNER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone_armor"), "inner");
    public static final ModelLayerLocation OUTER_LOCATION = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone_armor"), "outer");

    public ThermalSpringStoneArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidArmorModel.createMesh(deformation, 0f);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");

        PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, -6.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition left_upper_horn = left_horn.addOrReplaceChild("left_upper_horn", CubeListBuilder.create().texOffs(8, 32).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -5.0F, 0.0F, 0.0F, 0.0F, 1.0472F));

        PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -6.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition right_upper_horn = right_horn.addOrReplaceChild("right_upper_horn", CubeListBuilder.create().texOffs(8, 32).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -5.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(stack, builder, packedLight, packedOverlay, red, green, blue, alpha);

        if (Minecraft.getInstance().level != null) {
            float light;
            int fullBright = 0xF000F0;
            long ticks = Minecraft.getInstance().level.getGameTime() % 40;
            if (ticks < 20) {
                light = ticks / 20f;
            } else {
                light = (39 - ticks) / 20f;
            }
            super.renderToBuffer(stack, builder, (int) (fullBright / 2f + fullBright * light / 2f), packedOverlay, red, green, blue, alpha);
        }
    }
}
