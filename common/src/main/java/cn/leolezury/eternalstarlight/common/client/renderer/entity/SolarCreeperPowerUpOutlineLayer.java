package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.SolarCreeperModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeperPowerUpPhase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SolarCreeperPowerUpOutlineLayer<T extends SolarCreeper> extends RenderLayer<T, SolarCreeperModel<T>> {
	private static final ResourceLocation OUTLINE_TEXTURE = EternalStarlight.id("textures/entity/blank.png");

	public SolarCreeperPowerUpOutlineLayer(RenderLayerParent<T, SolarCreeperModel<T>> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		SolarCreeperModel<T> model = getParentModel();
		VertexConsumer consumer = buffer.getBuffer(ESRenderType.entityCutoutGlow(OUTLINE_TEXTURE));
		float healthProgress = Mth.clamp(1 - 2 * (entity.getHealth() / entity.getMaxHealth()), 0, 1);
		float outlineR = Mth.lerp(healthProgress, 255 / 255f, 197 / 255f);
		float outlineG = Mth.lerp(healthProgress, 255 / 255f, 26 / 255f);
		float outlineB = Mth.lerp(healthProgress, 170 / 255f, 0 / 255f);
		float expansionFactor = 1 + Mth.sin(ageInTicks * 0.1f) * 0.2f;

		if (entity.getBehaviorState() == SolarCreeperPowerUpPhase.ID) {
			if (entity.getBehaviorTicks() >= 3) {
				float progress = entity.getAnimationTicks(partialTicks) / SolarCreeperPowerUpPhase.DURATION;
				float outlineDegree = SolarCreeperPowerUpPhase.OUTLINE_DEGREE.calculate(progress);

				float bodyExp = outlineExpansion(outlineDegree, 0.0f, 0.2f) * expansionFactor;
				float leg1Exp = outlineExpansion(outlineDegree, 0.2f, 0.4f) * expansionFactor;
				float leg2Exp = outlineExpansion(outlineDegree, 0.4f, 0.6f) * expansionFactor;
				float leg3Exp = outlineExpansion(outlineDegree, 0.6f, 0.8f) * expansionFactor;
				float leg4Exp = outlineExpansion(outlineDegree, 0.8f, 1.0f) * expansionFactor;

				if (bodyExp <= 0 && leg1Exp <= 0 && leg2Exp <= 0 && leg3Exp <= 0 && leg4Exp <= 0) return;

				poseStack.pushPose();
				model.root.translateAndRotate(poseStack);
				if (bodyExp > 0) {
					ESModelUtil.renderOutlineModelPart(model.body, poseStack, consumer, bodyExp, outlineR, outlineG, outlineB, 1);
				}
				if (leg1Exp > 0) {
					ESModelUtil.renderOutlineModelPart(model.leg1, poseStack, consumer, leg1Exp, outlineR, outlineG, outlineB, 1);
				}
				if (leg2Exp > 0) {
					ESModelUtil.renderOutlineModelPart(model.leg2, poseStack, consumer, leg2Exp, outlineR, outlineG, outlineB, 1);
				}
				if (leg3Exp > 0) {
					ESModelUtil.renderOutlineModelPart(model.leg3, poseStack, consumer, leg3Exp, outlineR, outlineG, outlineB, 1);
				}
				if (leg4Exp > 0) {
					ESModelUtil.renderOutlineModelPart(model.leg4, poseStack, consumer, leg4Exp, outlineR, outlineG, outlineB, 1);
				}
				poseStack.popPose();
			}
		} else if (entity.getPhase() > 0) {
			ESModelUtil.renderOutlineModelPart(model.root, poseStack, consumer, 0.05f * expansionFactor, outlineR, outlineG, outlineB, 1);
		}
	}

	private static float outlineExpansion(float outlineDegree, float start, float end) {
		if (outlineDegree <= start) return 0;
		if (outlineDegree >= end) return 0.05f;
		return Mth.lerp((outlineDegree - start) / (end - start), 0, 0.05f);
	}
}
