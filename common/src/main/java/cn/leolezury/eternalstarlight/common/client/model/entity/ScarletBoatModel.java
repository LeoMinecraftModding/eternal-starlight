package cn.leolezury.eternalstarlight.common.client.model.entity;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ScarletBoatModel extends BoatModel {
	public ScarletBoatModel(ModelPart root) {
		super(root);
	}

	public static void createChildren(PartDefinition partdefinition) {
		partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-14.0F, -8.0F, 0.0F, 28.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-8.0F, -5.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(36, 27).mirror().addBox(-2.0F, -7.0F, -1.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(40, 19).mirror().addBox(0.0F, -3.0F, 1.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		partdefinition.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-9.0F, -3.0F, -1.0F, 18.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-15.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		partdefinition.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-2.0F, -3.0F, -1.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(36, 43).mirror().addBox(-14.0F, -5.0F, -1.0F, 12.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 0.0F, -3.1416F, 0.0F));

		partdefinition.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-14.0F, -3.0F, -1.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(64, 43).addBox(2.0F, -5.0F, -1.0F, 12.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 9.0F));

		partdefinition.addOrReplaceChild("left_paddle", CubeListBuilder.create().texOffs(62, 0).mirror().addBox(-1.0F, -1.0F, -5.5F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(62, 0).mirror().addBox(-0.01F, -4.0F, 8.5F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -4.0F, 9.0F, -0.5236F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("right_paddle", CubeListBuilder.create().texOffs(62, 20).mirror().addBox(-1.0F, -1.0F, -5.5F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(62, 20).mirror().addBox(-0.99F, -4.0F, 8.5F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -4.0F, -9.0F, -0.5236F, 3.1416F, 0.0F));

		partdefinition.addOrReplaceChild("water_patch", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-14.0F, -8.0F, 0.0F, 28.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
	}

	public static LayerDefinition createBodyModel() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		createChildren(partDefinition);
		return LayerDefinition.create(meshDefinition, 128, 64);
	}
}
