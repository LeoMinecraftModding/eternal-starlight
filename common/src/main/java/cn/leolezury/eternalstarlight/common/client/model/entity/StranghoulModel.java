package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Stranghoul;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import cn.leolezury.eternalstarlight.common.item.combat.SpearItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class StranghoulModel<T extends Stranghoul> extends HumanoidModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("stranghoul"), "main");
	public static final ModelLayerLocation INNER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("stranghoul"), "inner_armor");
	public static final ModelLayerLocation OUTER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("stranghoul"), "outer_armor");

	public StranghoulModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.5F));

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -32.0F, -4.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 28).addBox(-3.0F, 8.0F, -1.5F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(12, 16).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(36, 20).mirror().addBox(-2.0F, -2.0F, -2.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.5F));

		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 20).addBox(-1.0F, -2.0F, -2.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.5F));

		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.3F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.6F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 20).addBox(-1.7F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.6F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	// from DrownedModel & SkeletonModel
	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
		this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
		ItemStack stack = entity.getItemInHand(InteractionHand.MAIN_HAND);
		if (stack.getItem() instanceof SpearItem && entity.isAggressive()) {
			if (entity.getMainArm() == HumanoidArm.RIGHT) {
				this.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
			} else {
				this.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
			}
		}
		if ((stack.getItem() instanceof SeedsLauncherItem
			|| (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.FABRIC && stack.is(Items.BOW))
			|| (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.NEOFORGE && stack.getItem() instanceof BowItem))
			&& entity.isAggressive()) {
			if (entity.getMainArm() == HumanoidArm.RIGHT) {
				this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
			} else {
				this.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
			}
		}

		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (entity.isAggressive() && entity.getWeaponItem().isEmpty()) {
			AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
		}
		if (entity.isEating()) {
			boolean inverted = entity.getMainArm() == HumanoidArm.RIGHT;
			ModelPart eatingArm = getArm(entity.getMainArm().getOpposite());
			eatingArm.xRot = (Mth.sin(ageInTicks * 1.5f) + 1) * 0.5f * Mth.PI * 0.12f - Mth.PI * 0.5f;
			eatingArm.yRot = inverted ? 0.4f : -0.4f;
			eatingArm.zRot = 0;
		}
		if (entity.isBartering()) {
			this.head.xRot = 0.5F;
			this.head.yRot = 0.0F;
			if (entity.isLeftHanded()) {
				this.rightArm.yRot = -0.5F;
				this.rightArm.xRot = -0.9F;
			} else {
				this.leftArm.yRot = 0.5F;
				this.leftArm.xRot = -0.9F;
			}
		}
	}
}
