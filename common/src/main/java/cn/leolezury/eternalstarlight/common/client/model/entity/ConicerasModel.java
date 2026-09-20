package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.ConicerasAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Coniceras;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ConicerasModel<T extends Coniceras> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("coniceras"), "main");
	private final ModelPart root;
	private final ModelPart shell;
	private final ModelPart shell4;
	private final ModelPart shell3;
	private final ModelPart shell2;
	private final ModelPart shell1;
	private final ModelPart body;
	private final ModelPart mouth_top;
	private final ModelPart inner_mouth;
	private final ModelPart mouth_bottom;

	public ConicerasModel(ModelPart root) {
		this.root = root.getChild("root");
		this.shell = this.root.getChild("shell");
		this.shell4 = this.shell.getChild("shell4");
		this.shell3 = this.shell4.getChild("shell3");
		this.shell2 = this.shell3.getChild("shell2");
		this.shell1 = this.shell2.getChild("shell1");
		this.body = this.root.getChild("body");
		this.mouth_top = this.body.getChild("mouth_top");
		this.inner_mouth = this.body.getChild("inner_mouth");
		this.mouth_bottom = this.body.getChild("mouth_bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(24, 25).addBox(-1.0F, -2.0F, 4.0F, 7.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(24, 15).addBox(-1.0F, -2.0F, 0.0F, 7.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(24, 25).addBox(-1.0F, -2.0F, 4.0F, 7.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.0F, -2.5F));

		PartDefinition shell4 = shell.addOrReplaceChild("shell4", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -2.5F, -6.5F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 1.0F, 0.5F));

		PartDefinition shell3 = shell4.addOrReplaceChild("shell3", CubeListBuilder.create().texOffs(0, 26).addBox(-2.0F, -2.0F, -4.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition shell2 = shell3.addOrReplaceChild("shell2", CubeListBuilder.create().texOffs(32, 34).addBox(-1.5F, -1.5F, -4.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition shell1 = shell2.addOrReplaceChild("shell1", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -4.01F, -1.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 2.5F));

		PartDefinition mouth_top = body.addOrReplaceChild("mouth_top", CubeListBuilder.create().texOffs(30, 0).addBox(-2.498F, 0.988F, 0.498F, 5.0F, 3.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offset(0.0F, -5.0F, 8.5F));

		PartDefinition inner_mouth = body.addOrReplaceChild("inner_mouth", CubeListBuilder.create().texOffs(18, 34).addBox(-1.5F, -4.01F, 6.4F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 2.5F));

		PartDefinition mouth_bottom = body.addOrReplaceChild("mouth_bottom", CubeListBuilder.create().texOffs(30, 7).addBox(-2.5F, -2.01F, 0.4F, 5.0F, 2.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offset(0.0F, 1.0F, 8.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity.isCharging()) {
			animate(entity.chargeAnimationState, ConicerasAnimation.charge, ageInTicks);
		}
	}
}