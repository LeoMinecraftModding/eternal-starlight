package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.EctostoneAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class EctostoneModel<T extends Ectostone> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("ectostone"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart cannons;
	private final ModelPart cannon1;
	private final ModelPart particle1;
	private final ModelPart cannon2;
	private final ModelPart particle2;
	private final ModelPart cannon3;
	private final ModelPart particle3;
	private final ModelPart cannon4;
	private final ModelPart particle4;

	public EctostoneModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.cannons = this.body.getChild("cannons");
		this.cannon1 = this.cannons.getChild("cannon1");
		this.particle1 = this.cannon1.getChild("particle1");
		this.cannon2 = this.cannons.getChild("cannon2");
		this.particle2 = this.cannon2.getChild("particle2");
		this.cannon3 = this.cannons.getChild("cannon3");
		this.particle3 = this.cannon3.getChild("particle3");
		this.cannon4 = this.cannons.getChild("cannon4");
		this.particle4 = this.cannon4.getChild("particle4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cannons = body.addOrReplaceChild("cannons", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cannon1 = cannons.addOrReplaceChild("cannon1", CubeListBuilder.create().texOffs(0, 15).addBox(-5.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -1.5F, 0.0F));

		cannon1.addOrReplaceChild("particle1", CubeListBuilder.create(), PartPose.offset(-2.5F, -2.5F, 5.0F));

		PartDefinition cannon2 = cannons.addOrReplaceChild("cannon2", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.5F, 0.0F));

		cannon2.addOrReplaceChild("particle2", CubeListBuilder.create(), PartPose.offset(2.5F, -2.5F, 5.0F));

		PartDefinition cannon3 = cannons.addOrReplaceChild("cannon3", CubeListBuilder.create().texOffs(0, 15).addBox(-5.0F, 0.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.5F, 0.0F));

		cannon3.addOrReplaceChild("particle3", CubeListBuilder.create(), PartPose.offset(-2.5F, 2.5F, 5.0F));

		PartDefinition cannon4 = cannons.addOrReplaceChild("cannon4", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, 0.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.5F, 0.0F));

		cannon4.addOrReplaceChild("particle4", CubeListBuilder.create(), PartPose.offset(2.5F, 2.5F, 5.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partial = Mth.frac(ageInTicks);
		Vec3 entityPos = entity.getPosition(partial);
		float bodyYaw = Mth.lerp(partial, entity.yBodyRotO, entity.yBodyRot);
		if (entity.deathTime > 0) {
			animate(entity.idleAnimationState, EctostoneAnimation.IDLE, ageInTicks);
		} else {
			switch (entity.getBehaviorState()) {
				case EctostoneShootPhase.ID -> animate(entity.shootAnimationState, EctostoneAnimation.SHOOT, ageInTicks);
				case EctostoneSmashPhase.ID -> animate(entity.smashAnimationState, EctostoneAnimation.SMASH, ageInTicks);
				case EctostoneSmashTransitionPhase.ID -> animate(entity.smashTransitionAnimationState, EctostoneAnimation.SMASH_TRANSITION, ageInTicks);
				case EctostoneSmashEndPhase.ID -> animate(entity.smashEndAnimationState, EctostoneAnimation.SMASH_END, ageInTicks);
				case EctostoneContinuousShootPhase.ID -> animate(entity.continuousShootAnimationState, EctostoneAnimation.CONTINUOUS_SHOOT, ageInTicks);
				case EctostoneFallAsleepPhase.ID -> animate(entity.fallAsleepAnimationState, EctostoneAnimation.FALL_ASLEEP, ageInTicks);
				case EctostoneWakeUpPhase.ID -> animate(entity.wakeUpAnimationState, EctostoneAnimation.WAKE_UP, ageInTicks);
				default -> {
					if (entity.isDormant()) {
						applyStatic(EctostoneAnimation.SLEEP);
					} else {
						animate(entity.idleAnimationState, EctostoneAnimation.IDLE, ageInTicks);
					}
				}
			}
		}
		entity.particlePositions[0] = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, cannons, cannon1, particle1), new Vector3f(0, 0, 0));
		entity.particlePositions[1] = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, cannons, cannon2, particle2), new Vector3f(0, 0, 0));
		entity.particlePositions[2] = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, cannons, cannon3, particle3), new Vector3f(0, 0, 0));
		entity.particlePositions[3] = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, cannons, cannon4, particle4), new Vector3f(0, 0, 0));
	}
}
