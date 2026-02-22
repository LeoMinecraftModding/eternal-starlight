package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TinyCreteor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TinyCreteorModel<T extends TinyCreteor> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("tiny_creteor"), "main");
	public static final ModelLayerLocation ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("tiny_creteor"), "armor");
	private final ModelPart root;
	private final ModelPart rotator;

	public TinyCreteorModel(ModelPart root) {
		this.root = root.getChild("root");
		this.rotator = root.getChild("root").getChild("rotator");
	}

	public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition rotator = root.addOrReplaceChild("rotator", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation), PartPose.offset(0.0F, 0.0F, 0.0F));

		rotator.addOrReplaceChild("crystal1", CubeListBuilder.create().texOffs(33, 8).addBox(-4.0F, -4.0F, 0.0F, 6.0F, 6.0F, 0.0F, deformation), PartPose.offset(-4.0F, -4.0F, 0.5F));

		rotator.addOrReplaceChild("crystal2", CubeListBuilder.create().texOffs(1, 26).addBox(-2.5F, -6.0F, 0.0F, 5.0F, 6.0F, 0.0F, deformation), PartPose.offsetAndRotation(-4.0F, 1.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		rotator.addOrReplaceChild("crystal3", CubeListBuilder.create().texOffs(1, 26).mirror().addBox(-2.5F, -6.0F, 0.0F, 5.0F, 6.0F, 0.0F, deformation).mirror(false), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		rotator.addOrReplaceChild("crystal4", CubeListBuilder.create().texOffs(1, 17).mirror().addBox(0.0F, -8.0F, 0.0F, 4.0F, 8.0F, 0.0F, deformation).mirror(false), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		rotator.addOrReplaceChild("crystal5", CubeListBuilder.create().texOffs(33, 15).addBox(0.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, deformation), PartPose.offset(4.0F, 0.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		rotator.zRot = -Mth.rotLerp(Mth.frac(ageInTicks), entity.prevRollAngle, entity.rollAngle) * Mth.DEG_TO_RAD;
		root.xRot = headPitch * Mth.DEG_TO_RAD;
		root.yRot = netHeadYaw * Mth.DEG_TO_RAD;
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
