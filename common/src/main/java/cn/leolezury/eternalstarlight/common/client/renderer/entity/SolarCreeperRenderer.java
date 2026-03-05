package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.SolarCreeperModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeperIntroPhase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;

public class SolarCreeperRenderer<T extends SolarCreeper> extends MobRenderer<T, SolarCreeperModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/solar_creeper.png");
	private static final ResourceLocation SUN_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/sun.png");

	private final OrbModel<Entity> sunModel;

	public SolarCreeperRenderer(EntityRendererProvider.Context context) {
		super(context, new SolarCreeperModel<>(context.bakeLayer(SolarCreeperModel.LAYER_LOCATION)), 0.5f);
		this.sunModel = new OrbModel<>(context.bakeLayer(OrbModel.LAYER_LOCATION));
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		if (entity.tickCount < 3) return;
		int state = entity.getBehaviorState();
		float animationTicks = entity.getAnimationTicks(partialTicks);
		float bodyScale = 1, sunScale = 0, shineScale = 0;
		if (state == SolarCreeperIntroPhase.ID) {
			bodyScale = SolarCreeperIntroPhase.BODY_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
			sunScale = 2 * SolarCreeperIntroPhase.SUN_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
			shineScale = SolarCreeperIntroPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
		}
		if (bodyScale > 0) {
			poseStack.pushPose();
			poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
			poseStack.scale(bodyScale, bodyScale, bodyScale);
			poseStack.translate(0.0F, -entity.getBbHeight() / 2, 0.0F);
			super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
			poseStack.popPose();
		}
		if (sunScale > 0) {
			poseStack.pushPose();
			poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
			poseStack.scale(sunScale, sunScale, sunScale);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, -1.5F, 0.0F);
			RenderType renderType = this.sunModel.renderType(SUN_TEXTURE);
			VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
			this.sunModel.setupAnim(entity, 0, 0, getBob(entity, partialTicks), 0, 0);
			this.sunModel.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			poseStack.popPose();
		}
		if (shineScale > 0) {
			poseStack.pushPose();
			poseStack.translate(0, entity.getBbHeight() / 2, 0);
			poseStack.scale(shineScale, shineScale, shineScale);
			poseStack.mulPose(new Quaternionf(this.entityRenderDispatcher.cameraOrientation()).rotateY(Mth.PI));
			PoseStack.Pose pose = poseStack.last();
			VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.dragonRays());
			for (int i = 0; i < 5; i++) {
				vertexConsumer.addVertex(pose, 0, 0, 0).setColor(FastColor.ARGB32.color(255, 255, 213, 74));
				float angle = i * Mth.TWO_PI / 5 + (animationTicks / SolarCreeperIntroPhase.DURATION) * Mth.PI * 1.5f;
				vertexConsumer.addVertex(pose, Mth.sin(angle) * entity.getBbHeight() * 3, Mth.cos(angle) * entity.getBbHeight() * 3, 0).setColor(FastColor.ARGB32.color(0, 255, 213, 74));
				float largerAngle = angle + Mth.TWO_PI / 12;
				vertexConsumer.addVertex(pose, Mth.sin(largerAngle) * entity.getBbHeight() * 3, Mth.cos(largerAngle) * entity.getBbHeight() * 3, 0).setColor(FastColor.ARGB32.color(0, 255, 213, 74));
			}
			poseStack.popPose();
		}
	}

	@Override
	protected float getFlipDegrees(T entity) {
		return 0;
	}

	@Override
	protected float getWhiteOverlayProgress(T entity, float partialTicks) {
		int state = entity.getBehaviorState();
		float animationTicks = entity.getAnimationTicks(partialTicks);
		float shineScale = 0;
		if (state == SolarCreeperIntroPhase.ID) {
			shineScale = SolarCreeperIntroPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
		}
		return shineScale;
	}

	@Override
	protected float getShadowRadius(T mob) {
		return mob.getBehaviorState() == SolarCreeperIntroPhase.ID ? 0 : super.getShadowRadius(mob);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
