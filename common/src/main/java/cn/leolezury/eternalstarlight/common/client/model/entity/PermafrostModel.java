package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.PermafrostAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.PermafrostMeleeEndPhase;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.PermafrostMeleePhase;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.PermafrostMeleeTransitionPhase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class PermafrostModel<T extends Permafrost> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("permafrost"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public PermafrostModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = root.getChild("root").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition lower = root.addOrReplaceChild("lower", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition armature = lower.addOrReplaceChild("armature", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(40, 8).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armature1 = armature.addOrReplaceChild("armature1", CubeListBuilder.create().texOffs(0, 57).mirror().addBox(-9.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 3.5F, -1.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition armature2 = armature.addOrReplaceChild("armature2", CubeListBuilder.create().texOffs(0, 57).addBox(0.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.5F, -1.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition armature3 = armature.addOrReplaceChild("armature3", CubeListBuilder.create().texOffs(0, 57).addBox(0.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.5F, 1.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition armature4 = armature.addOrReplaceChild("armature4", CubeListBuilder.create().texOffs(0, 57).mirror().addBox(-9.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 3.5F, 1.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition wheel = lower.addOrReplaceChild("wheel", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, -1.2F, -4.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(40, 20).addBox(-4.0F, -1.2F, 2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(52, 24).addBox(2.0F, -1.2F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(40, 24).addBox(-4.0F, -1.2F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(18, 52).addBox(0.0F, -1.2F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(18, 56).addBox(-2.0F, -1.2F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(18, 55).addBox(0.0F, -1.2F, -7.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(18, 58).addBox(0.0F, -1.2F, 4.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(18, 61).addBox(-7.0F, -1.2F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(18, 58).addBox(4.0F, -1.2F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.2F, 0.0F));

		PartDefinition right_chain = wheel.addOrReplaceChild("right_chain", CubeListBuilder.create(), PartPose.offset(-5.5F, 0.8F, 0.0F));

		PartDefinition right_chain1 = right_chain.addOrReplaceChild("right_chain1", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition right_chain2 = right_chain1.addOrReplaceChild("right_chain2", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition right_rod = right_chain2.addOrReplaceChild("right_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 3).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition left_chain = wheel.addOrReplaceChild("left_chain", CubeListBuilder.create(), PartPose.offset(5.5F, 0.8F, 0.0F));

		PartDefinition left_chain1 = left_chain.addOrReplaceChild("left_chain1", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition left_chain2 = left_chain1.addOrReplaceChild("left_chain2", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition left_rod = left_chain2.addOrReplaceChild("left_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 3).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition forward_chain = wheel.addOrReplaceChild("forward_chain", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, -5.5F));

		PartDefinition forward_chain1 = forward_chain.addOrReplaceChild("forward_chain1", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition forward_chain2 = forward_chain1.addOrReplaceChild("forward_chain2", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition forward_rod = forward_chain2.addOrReplaceChild("forward_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 6).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition backward_chain = wheel.addOrReplaceChild("backward_chain", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, 5.5F));

		PartDefinition backward_chain1 = backward_chain.addOrReplaceChild("backward_chain1", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition backward_chain2 = backward_chain1.addOrReplaceChild("backward_chain2", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition backward_rod = backward_chain2.addOrReplaceChild("backward_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 6).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.5F))
			.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		animate(entity.idleAnimationState, PermafrostAnimation.IDLE, ageInTicks);
		if (entity.getBehaviorTicks() >= 0 && entity.getBehaviorState() != 0 && entity.deathTime <= 0) {
			int state = entity.getBehaviorState();
			switch (state) {
				case PermafrostMeleePhase.ID -> {
					animate(entity.meleeAnimationState, PermafrostAnimation.MELEE, ageInTicks);
				}
				case PermafrostMeleeTransitionPhase.ID -> {
					animate(entity.meleeTransitionAnimationState, PermafrostAnimation.MELEE_TRANSITION, ageInTicks);
				}
				case PermafrostMeleeEndPhase.ID -> {
					animate(entity.meleeEndAnimationState, PermafrostAnimation.MELEE_END, ageInTicks);
				}
			}
		}
	}

	@Override
	public RenderType renderType(ResourceLocation resourceLocation) {
		return RenderType.entityTranslucent(resourceLocation);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
