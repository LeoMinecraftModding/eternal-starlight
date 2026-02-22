package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.AuroraDeerAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.AuroraDeer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class AuroraDeerModel<T extends AuroraDeer> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("aurora_deer"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart leftAntler;
	private final ModelPart rightAntler;

	public AuroraDeerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = root.getChild("root").getChild("body").getChild("neck").getChild("head");
		this.leftAntler = head.getChild("left_antler");
		this.rightAntler = head.getChild("right_antler");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -10.0F, -2.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -8.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.75F, -5.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(0, 14).addBox(-1.5F, -2.75F, -9.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, 1.0F, 0.0F, 0.0F, 0.0F));

		head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(14, 17).addBox(0.0F, -1.5F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(22, 17).addBox(0.0F, 0.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.25F, 0.0F, 0.0F, -0.7854F, 0.0F));

		head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(14, 17).mirror().addBox(-3.0F, -1.5F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(22, 17).mirror().addBox(-2.0F, 0.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -3.25F, 0.0F, 0.0F, 0.7854F, 0.0F));

		head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(28, 6).addBox(-2.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(36, 6).addBox(-2.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(44, 5).addBox(-2.0F, -4.0F, 2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(54, 6).addBox(-2.0F, -7.0F, 3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(28, 13).addBox(-2.0F, -9.0F, -2.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
			.texOffs(46, 13).addBox(-2.0F, -10.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.75F, 2.0F, -0.2138F, 0.0F, 0.2138F));

		head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(28, 6).mirror().addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(36, 6).mirror().addBox(0.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(44, 5).mirror().addBox(0.0F, -4.0F, 2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(54, 6).mirror().addBox(0.0F, -7.0F, 3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(28, 13).mirror().addBox(0.0F, -9.0F, -2.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(46, 13).mirror().addBox(0.0F, -10.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -4.75F, 2.0F, -0.2138F, 0.0F, -0.2138F));

		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.7854F, 0.0F, 0.0F));

		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 23).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -10.0F, -6.0F));

		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 23).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -10.0F, -6.0F));

		root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(40, 21).addBox(-2.25F, -4.0F, -2.5F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(48, 49).addBox(-2.25F, 4.0F, -0.5F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.25F, -12.0F, 5.5F));

		root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(40, 21).mirror().addBox(-2.75F, -4.0F, -2.5F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(48, 49).mirror().addBox(-1.75F, 4.0F, -0.5F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.25F, -12.0F, 5.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		if (young) {
			root.xScale = 0.6f;
			root.yScale = 0.6f;
			root.zScale = 0.6f;
			head.xScale = 1.67f;
			head.yScale = 1.67f;
			head.zScale = 1.67f;
		}
		leftAntler.visible = entity.hasLeftAntler();
		rightAntler.visible = entity.hasRightAntler();
		this.animate(entity.idleAnimationState, AuroraDeerAnimation.IDLE, ageInTicks);
		this.animateWalk(AuroraDeerAnimation.WALK, limbSwing, limbSwingAmount, young ? 3.0f : 5.0f, 1.0f);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
