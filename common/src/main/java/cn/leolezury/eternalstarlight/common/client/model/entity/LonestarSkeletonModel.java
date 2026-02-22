package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.entity.living.monster.LonestarSkeleton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LonestarSkeletonModel<T extends LonestarSkeleton> extends SkeletonModel<T> {
	private final ModelPart innerHead;

	public LonestarSkeletonModel(ModelPart root) {
		super(root);
		if (getHead().hasChild("inner_head")) {
			this.innerHead = getHead().getChild("inner_head");
		} else {
			this.innerHead = null;
		}
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partDefinition = meshDefinition.getRoot();
		createDefaultSkeletonMesh(partDefinition);
		partDefinition.getChild("head").addOrReplaceChild("inner_head", CubeListBuilder.create().texOffs(0, 32).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(48, 24).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (innerHead != null) {
			innerHead.resetPose();
			if (entity.level().tickRateManager().runsNormally() && !Minecraft.getInstance().isPaused()) {
				innerHead.x += (float) (entity.getRandom().nextGaussian() * 0.25);
				innerHead.y += (float) (entity.getRandom().nextGaussian() * 0.25);
			}
		}
	}
}
