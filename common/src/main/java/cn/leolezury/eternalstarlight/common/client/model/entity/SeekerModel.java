package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.SeekerAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class SeekerModel<T extends Seeker> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("seeker"), "main");
	private final ModelPart root;

	public SeekerModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(30, 22).addBox(-3.5F, 3.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -11.0F, -5.5F, 11.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		head.addOrReplaceChild("head_decoration", CubeListBuilder.create().texOffs(0, 22).addBox(-7.0F, -7.0F, -0.5F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		root.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(30, 34).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -2.0F));

		root.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(30, 34).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 2.0F));

		root.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(30, 25).addBox(0.0F, 0.0F, -4.5F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 7.0F, 0.0F));

		root.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(30, 25).addBox(0.0F, 0.0F, -4.5F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 7.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.moveAnimationState, SeekerAnimation.MOVE, ageInTicks);
		animate(entity.attackAnimationState, SeekerAnimation.ATTACK, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
