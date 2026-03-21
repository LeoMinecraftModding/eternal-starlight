package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.PermafrostAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class PermafrostModel<T extends Permafrost> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("permafrost"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart lower;
	private final ModelPart armature;
	public final List<String> allPartNames;

	public float alphaFactor = 1;

	public PermafrostModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = root.getChild("root").getChild("head");
		this.lower = root.getChild("root").getChild("lower");
		this.armature = root.getChild("root").getChild("lower").getChild("armature");
		this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition lower = root.addOrReplaceChild("lower", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition armature = lower.addOrReplaceChild("armature", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(40, 8).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		armature.addOrReplaceChild("armature1", CubeListBuilder.create().texOffs(0, 57).mirror().addBox(-9.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 3.5F, -1.0F, 0.0F, -0.7854F, 0.0F));

		armature.addOrReplaceChild("armature2", CubeListBuilder.create().texOffs(0, 57).addBox(0.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.5F, -1.0F, 0.0F, 0.7854F, 0.0F));

		armature.addOrReplaceChild("armature3", CubeListBuilder.create().texOffs(0, 57).addBox(0.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.5F, 1.0F, 0.0F, -0.7854F, 0.0F));

		armature.addOrReplaceChild("armature4", CubeListBuilder.create().texOffs(0, 57).mirror().addBox(-9.0F, -6.5F, 0.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 3.5F, 1.0F, 0.0F, 0.7854F, 0.0F));

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

		PartDefinition rightChainCenter = wheel.addOrReplaceChild("right_chain_center", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, 0.0F));

		PartDefinition rightChain = rightChainCenter.addOrReplaceChild("right_chain", CubeListBuilder.create(), PartPose.offset(-5.5F, 0.0F, 0.0F));

		PartDefinition rightChain1 = rightChain.addOrReplaceChild("right_chain1", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition rightChain2 = rightChain1.addOrReplaceChild("right_chain2", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		rightChain2.addOrReplaceChild("right_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 3).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition leftChainCenter = wheel.addOrReplaceChild("left_chain_center", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.8F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition leftChain = leftChainCenter.addOrReplaceChild("left_chain", CubeListBuilder.create(), PartPose.offset(-5.5F, 0.0F, 0.0F));

		PartDefinition leftChain1 = leftChain.addOrReplaceChild("left_chain1", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition leftChain2 = leftChain1.addOrReplaceChild("left_chain2", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		leftChain2.addOrReplaceChild("left_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 3).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition forwardChainCenter = wheel.addOrReplaceChild("forward_chain_center", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.8F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition forwardChain = forwardChainCenter.addOrReplaceChild("forward_chain", CubeListBuilder.create(), PartPose.offset(-5.5F, 0.0F, 0.0F));

		PartDefinition forwardChain1 = forwardChain.addOrReplaceChild("forward_chain1", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition forwardChain2 = forwardChain1.addOrReplaceChild("forward_chain2", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		forwardChain2.addOrReplaceChild("forward_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 3).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition backwardChainCenter = wheel.addOrReplaceChild("backward_chain_center", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.8F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition backwardChain = backwardChainCenter.addOrReplaceChild("backward_chain", CubeListBuilder.create(), PartPose.offset(-5.5F, 0.0F, 0.0F));

		PartDefinition backwardChain1 = backwardChain.addOrReplaceChild("backward_chain1", CubeListBuilder.create().texOffs(48, -1).addBox(0.0F, -0.5F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition backwardChain2 = backwardChain1.addOrReplaceChild("backward_chain2", CubeListBuilder.create().texOffs(48, 2).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		backwardChain2.addOrReplaceChild("backward_rod", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(52, 31).addBox(-1.0F, 3.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 3).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.5F))
			.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity.isDeadOrDying()) {
			AtomicInteger offsetCount = new AtomicInteger();
			this.root().getAllParts().forEach(part -> part.offsetPos(new Vector3f((float) Math.cos(entity.tickCount * 3.25 + offsetCount.getAndIncrement() * 1.5), (float) Math.cos(entity.tickCount * 2.25 + offsetCount.getAndIncrement() * 1.5), (float) Math.cos(entity.tickCount * 3.25 + offsetCount.getAndIncrement() * 1.5)).normalize().mul(0.2f)));
		}
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
				case PermafrostRangedPhase.ID -> {
					animate(entity.rangedAnimationState, PermafrostAnimation.RANGED, ageInTicks);
				}
				case PermafrostSneezePhase.ID -> {
					animate(entity.sneezeAnimationState, PermafrostAnimation.RANGED_SNEEZE, ageInTicks);
				}
			}
		}
		entity.smokePos = ESModelUtil.getModelPartWorldPosition(entity, Mth.lerp(Mth.frac(ageInTicks), entity.yBodyRotO, entity.yBodyRot), List.of(root(), lower, armature));
	}

	@Override
	public RenderType renderType(ResourceLocation resourceLocation) {
		return RenderType.entityTranslucent(resourceLocation);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, FastColor.ARGB32.color(Math.round(FastColor.ARGB32.alpha(color) * alphaFactor), FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color)));
	}
}
