package cn.leolezury.eternalstarlight.common.client.model.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class StarlitDiamondArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	public static final ModelLayerLocation INNER_LOCATION = new ModelLayerLocation(EternalStarlight.id("starlit_diamond_armor"), "inner");
	public static final ModelLayerLocation OUTER_LOCATION = new ModelLayerLocation(EternalStarlight.id("starlit_diamond_armor"), "outer");

	public StarlitDiamondArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidArmorModel.createMesh(deformation, 0f);
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 48).addBox(-4.0F, -3.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.75F))
			.texOffs(56, 18).addBox(-4.25F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.5F))
			.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F))
			.texOffs(40, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}