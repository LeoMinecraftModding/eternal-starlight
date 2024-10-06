package cn.leolezury.eternalstarlight.common.client.model.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class ThermalSpringStoneArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	public static final ModelLayerLocation INNER_LOCATION = new ModelLayerLocation(EternalStarlight.id("thermal_springstone_armor"), "inner");
	public static final ModelLayerLocation OUTER_LOCATION = new ModelLayerLocation(EternalStarlight.id("thermal_springstone_armor"), "outer");

	public ThermalSpringStoneArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidArmorModel.createMesh(deformation, 0f);
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.getChild("head");

		head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(16, 32).mirror().addBox(0.0F, -4.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(0, 32).mirror().addBox(2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -4.0F, -1.0F, 0.0F, 0.0F, -0.2138F));

		head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0F, -4.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(0, 32).addBox(-6.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -4.0F, -1.0F, 0.0F, 0.0F, 0.2138F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int packedLight, int packedOverlay, int color) {
		super.renderToBuffer(stack, builder, ClientHandlers.FULL_BRIGHT, packedOverlay, color);
	}
}
