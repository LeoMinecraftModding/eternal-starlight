package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.CrystallizedMothAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.CrystallizedMoth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CrystallizedMothModel<T extends CrystallizedMoth> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("crystallized_moth"), "main");
	private final ModelPart root;

	public CrystallizedMothModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(0, 12).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
			.texOffs(38, 24).addBox(2.0F, 0.0F, -4.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
			.texOffs(38, 24).addBox(-2.0F, 0.0F, -4.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -3.0F, 0.0F, 0.3491F, 0.0F));

		root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-18.0F, 0.0F, 0.0F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.5F, -3.0F, 0.0F, -0.3491F, 0.0F));

		root.addOrReplaceChild("left_wing_small", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, -5.0F, 3.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0436F, -0.2618F, -0.0436F));

		root.addOrReplaceChild("right_wing_small", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-12.0F, -5.0F, 3.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0436F, 0.2618F, 0.0436F));

		root.addOrReplaceChild("left_tentacle", CubeListBuilder.create().texOffs(38, 12).addBox(-1.0F, 0.0F, -6.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.5F, -6.0F, -0.829F, -0.3927F, 0.0F));

		root.addOrReplaceChild("right_tentacle", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(-3.0F, 0.0F, -6.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -1.5F, -6.0F, -0.829F, 0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		root.xRot += headPitch * Mth.DEG_TO_RAD;
		root.yRot += netHeadYaw * Mth.DEG_TO_RAD;
		if (young) {
			root.xScale = 0.6f;
			root.yScale = 0.6f;
			root.zScale = 0.6f;
		}
		animate(entity.idleAnimationState, CrystallizedMothAnimation.IDLE, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}
