package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.SeekerAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class SeekerModel<T extends Seeker> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("seeker"), "main");
	private final ModelPart root;
	private final ModelPart eye;

	public SeekerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.eye = this.root.getChild("head").getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 46).addBox(-3.5F, 3.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 26).addBox(-5.5F, -11.0F, -4.5F, 11.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
			.texOffs(0, 58).addBox(-4.5F, -13.0F, -4.5F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(54, 21).addBox(-3.5F, -13.0F, -0.5F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(40, 26).addBox(-5.5F, 0.0F, -4.5F, 11.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		head.addOrReplaceChild("head_decoration", CubeListBuilder.create().texOffs(0, 0).addBox(-13.5F, -17.0F, -0.5F, 27.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		head.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(28, 46).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, -4.75F));

		root.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(54, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -3.0F));

		root.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(54, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 3.0F));

		root.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(54, -9).addBox(0.0F, 0.0F, -4.5F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 3.0F, 0.0F));

		root.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(54, -9).addBox(0.0F, 0.0F, -4.5F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.moveAnimationState, SeekerAnimation.MOVE, ageInTicks);
		animate(entity.attackAnimationState, SeekerAnimation.ATTACK, ageInTicks);
		// from GuardianModel
		Entity camera = Minecraft.getInstance().getCameraEntity();
		if (camera != null) {
			Vec3 targetEyePos = camera.getEyePosition(0.0F);
			Vec3 seekerEyePos = entity.getEyePosition(0.0F);
			Vec3 seekerView = entity.calculateViewVector(-entity.getSeekerXRot() + Math.signum(entity.getSeekerXRot()) * 90, entity.getSeekerYRot() - 90);
			seekerView = new Vec3(seekerView.x, 0.0, seekerView.z);
			Vec3 eyeDiff = new Vec3(seekerEyePos.x - targetEyePos.x, 0.0, seekerEyePos.z - targetEyePos.z).normalize().yRot(Mth.PI / 2);
			double e = seekerView.dot(eyeDiff);
			this.eye.x += Mth.sqrt((float) Math.abs(e)) * 2.0F * (float) Math.signum(e);
		}
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
