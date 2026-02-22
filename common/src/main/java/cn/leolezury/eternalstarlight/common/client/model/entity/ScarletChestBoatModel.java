package cn.leolezury.eternalstarlight.common.client.model.entity;

import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ScarletChestBoatModel extends ChestBoatModel {
	public ScarletChestBoatModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyModel() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		ScarletBoatModel.createChildren(partDefinition);
		partDefinition.addOrReplaceChild("chest_bottom", CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F), PartPose.offsetAndRotation(-2.0F, -5.0F, -6.0F, 0.0F, -1.5708F, 0.0F));
		partDefinition.addOrReplaceChild("chest_lid", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F), PartPose.offsetAndRotation(-2.0F, -9.0F, -6.0F, 0.0F, -1.5708F, 0.0F));
		partDefinition.addOrReplaceChild("chest_lock", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(-1.0F, -6.0F, -1.0F, 0.0F, -1.5708F, 0.0F));
		return LayerDefinition.create(meshDefinition, 128, 128);
	}
}
