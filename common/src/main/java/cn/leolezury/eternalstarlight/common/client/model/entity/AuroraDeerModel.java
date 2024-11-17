package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.AuroraDeerAnimation;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.AuroraDeerRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class AuroraDeerModel extends EntityModel<AuroraDeerRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("aurora_deer"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart leftAntler;
	private final ModelPart rightAntler;

	public AuroraDeerModel(ModelPart root) {
		super(root);
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
	public void setupAnim(AuroraDeerRenderState state) {
		super.setupAnim(state);
		head.yRot = state.yRot * Mth.DEG_TO_RAD;
		head.xRot = state.xRot * Mth.DEG_TO_RAD;
		if (state.isBaby) {
			root.xScale = 0.6f;
			root.yScale = 0.6f;
			root.zScale = 0.6f;
			head.xScale = 1.67f;
			head.yScale = 1.67f;
			head.zScale = 1.67f;
		}
		leftAntler.visible = state.hasLeftAntler;
		rightAntler.visible = state.hasRightAntler;
		this.animate(state.idleAnimationState, AuroraDeerAnimation.IDLE, state.ageInTicks);
		this.animateWalk(AuroraDeerAnimation.WALK, state.walkAnimationPos, state.walkAnimationSpeed, state.isBaby ? 3.0f : 5.0f, 1.0f);
	}
}
