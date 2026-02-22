package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.StarlightGolemAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StarlightGolemModel<T extends StarlightGolem> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("starlight_golem"), "main");
	private final ModelPart root;
	private final ModelPart head;
	public final List<String> allPartNames;

	public float alphaFactor = 1;

	public StarlightGolemModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("upper").getChild("head");
		this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper = partdefinition.addOrReplaceChild("upper", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		upper.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(84, 83).addBox(-8.0F, -3.0F, -4.0F, 7.0F, 27.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(0, 70).addBox(-8.0F, 24.0F, -4.0F, 7.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -29.0F, 0.0F));

		upper.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 91).addBox(1.0F, -3.0F, -4.0F, 7.0F, 27.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(0, 46).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(46, 70).addBox(1.0F, 24.0F, -4.0F, 7.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -29.0F, 0.0F));

		upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -15.0F, -8.0F, 20.0F, 30.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

		upper.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 33).addBox(-7.0F, -10.5F, -6.5F, 14.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -30.5F, -3.5F));

		partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 46).addBox(-12.0F, -4.0F, -10.0F, 24.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity.getPhase() == 1) {
			AtomicInteger offsetCount = new AtomicInteger();
			this.root().getAllParts().forEach(part -> part.offsetPos(new Vector3f((float) Math.cos(entity.tickCount * 3.25 + offsetCount.getAndIncrement() * 1.5), (float) Math.cos(entity.tickCount * 2.25 + offsetCount.getAndIncrement() * 1.5), (float) Math.cos(entity.tickCount * 3.25 + offsetCount.getAndIncrement() * 1.5)).normalize().mul(0.2f)));
		}
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		if (entity.getBehaviorTicks() >= 0 && entity.getBehaviorState() != 0 && entity.deathAnimationTime <= 0) {
			int state = entity.getBehaviorState();
			switch (state) {
				case StarlightGolemLaserBeamPhase.ID -> animate(entity.laserBeamAnimationState, StarlightGolemAnimation.LASER_BEAM, ageInTicks);
				case StarlightGolemSummonFlamePhase.ID -> animate(entity.summonFlameAnimationState, StarlightGolemAnimation.ENERGIZED_FLAME, ageInTicks);
				case StarlightGolemSmashPhase.ID -> animate(entity.smashAnimationState, entity.getPhase() == 1 ? StarlightGolemAnimation.SMASH_LEAP : StarlightGolemAnimation.SMASH, ageInTicks);
				case StarlightGolemChargeStartPhase.ID -> animate(entity.chargeStartAnimationState, StarlightGolemAnimation.CHARGE_START, ageInTicks);
				case StarlightGolemChargePhase.ID -> animate(entity.chargeAnimationState, StarlightGolemAnimation.CHARGE, ageInTicks);
				case StarlightGolemChargeEndPhase.ID -> animate(entity.chargeEndAnimationState, StarlightGolemAnimation.CHARGE_END, ageInTicks);
			}
		}
		if (entity.deathAnimationTime > 0) {
			animate(entity.deathAnimationState, StarlightGolemAnimation.DEATH, ageInTicks);
		}
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
