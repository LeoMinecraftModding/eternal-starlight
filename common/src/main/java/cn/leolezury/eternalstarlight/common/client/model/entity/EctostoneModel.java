package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.EctostoneAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.Ectostone;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.EctostoneContinuousShootPhase;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.EctostoneShootPhase;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.EctostoneSmashPhase;
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
	private final ModelPart bone4;
	private final ModelPart particle4;
	private final ModelPart bone3;
	private final ModelPart particle3;
	private final ModelPart bone2;
	private final ModelPart particle2;
	private final ModelPart bone;
	private final ModelPart particle1;

	public EctostoneModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.cannons = this.body.getChild("cannons");
		this.bone4 = this.cannons.getChild("bone4");
		this.particle4 = this.bone4.getChild("particle4");
		this.bone3 = this.cannons.getChild("bone3");
		this.particle3 = this.bone3.getChild("particle3");
		this.bone2 = this.cannons.getChild("bone2");
		this.particle2 = this.bone2.getChild("particle2");
		this.bone = this.cannons.getChild("bone");
		this.particle1 = this.bone.getChild("particle1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cannons = body.addOrReplaceChild("cannons", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = cannons.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, 0.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.5F, 0.0F));

		PartDefinition particle4 = bone4.addOrReplaceChild("particle4", CubeListBuilder.create(), PartPose.offset(2.5F, 2.5F, 5.0F));

		PartDefinition bone3 = cannons.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 15).addBox(-5.0F, 0.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.5F, 0.0F));

		PartDefinition particle3 = bone3.addOrReplaceChild("particle3", CubeListBuilder.create(), PartPose.offset(-2.5F, 2.5F, 5.0F));

		PartDefinition bone2 = cannons.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.5F, 0.0F));

		PartDefinition particle2 = bone2.addOrReplaceChild("particle2", CubeListBuilder.create(), PartPose.offset(2.5F, -2.5F, 5.0F));

		PartDefinition bone = cannons.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 15).addBox(-5.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -1.5F, 0.0F));

		PartDefinition particle1 = bone.addOrReplaceChild("particle1", CubeListBuilder.create(), PartPose.offset(-2.5F, -2.5F, 5.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, EctostoneAnimation.IDLE, ageInTicks);
		animate(entity.dormancyAnimationState, EctostoneAnimation.DORMANCY, ageInTicks);
		animate(entity.startdormancyAnimationState, EctostoneAnimation.START_DORMANCY, ageInTicks);
		animate(entity.awakeAnimationState, EctostoneAnimation.AWAKE, ageInTicks);
		float partial = Mth.frac(ageInTicks);
		Vec3 entityPos = entity.getPosition(partial);
		float bodyYaw = Mth.lerp(partial, entity.yBodyRotO, entity.yBodyRot);
		if (entity.getBehaviorTicks() >= 0 && entity.getBehaviorState() != 0 && entity.deathTime <= 0) {
			int state = entity.getBehaviorState();
			switch (state) {
				case EctostoneShootPhase.ID -> {
					animate(entity.shootAnimationState, EctostoneAnimation.SHOOT, ageInTicks);
				}
				case EctostoneSmashPhase.ID -> {
					animate(entity.smashAnimationState, EctostoneAnimation.SMASH, ageInTicks);
				}
				case EctostoneContinuousShootPhase.ID -> {
					animate(entity.coshootAnimationState, EctostoneAnimation.CONTINUOUS_SHOOT, ageInTicks);
				}
			}
		}
		entity.particle1Pos = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, bone, particle1), new Vector3f(0, 0, 0));
		entity.particle2Pos = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, bone2, particle2), new Vector3f(0, 0, 0));
		entity.particle3Pos = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, bone3, particle3), new Vector3f(0, 0, 0));
		entity.particle4Pos = ESModelUtil.getModelPartWorldPosition(entity, entityPos, bodyYaw, List.of(root, body, bone4, particle4), new Vector3f(0, 0, 0));

	}
}