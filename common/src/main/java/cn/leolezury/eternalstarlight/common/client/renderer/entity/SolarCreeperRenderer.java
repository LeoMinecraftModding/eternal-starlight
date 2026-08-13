package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.SolarCreeperModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.*;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class SolarCreeperRenderer<T extends SolarCreeper> extends MobRenderer<T, SolarCreeperModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/solar_creeper.png");
	private static final ResourceLocation SUN_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/sun.png");
	private static final ResourceLocation BLACK_HOLE_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/black_hole.png");
	private static final ResourceLocation LASER_JITTER_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/solar_ray_jitter.png");
	private static final ResourceLocation LASER_STATIC_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/solar_ray_static.png");
	private static final ResourceLocation SOLAR_TETHER_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/solar_tether.png");

	private final OrbModel<Entity> sunModel;

	public SolarCreeperRenderer(EntityRendererProvider.Context context) {
		super(context, new SolarCreeperModel<>(context.bakeLayer(SolarCreeperModel.LAYER_LOCATION)), 0.5f);
		this.sunModel = new OrbModel<>(context.bakeLayer(OrbModel.LAYER_LOCATION));
		addLayer(new SolarCreeperPowerUpOutlineLayer<T>(this));
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		if (entity.tickCount < 3) return;
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		int state = entity.getBehaviorState();
		float animationTicks = entity.getAnimationTicks(partialTicks);
		float bodyScale = 1, sunScale = 0, blackHoleScale = 0, shineScale = 0, fullDuration = 0;
		int sunColor = -1;
		Vec3 pos = new Vec3(
			Mth.lerp(partialTicks, entity.xo, entity.getX()),
			Mth.lerp(partialTicks, entity.yo, entity.getY()),
			Mth.lerp(partialTicks, entity.zo, entity.getZ())
		);
		Vec3 sunAbovePos = entity.getSunAbovePos(partialTicks);
		if (state == SolarCreeperIntroPhase.ID) {
			bodyScale = SolarCreeperIntroPhase.BODY_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
			sunScale = 2 * SolarCreeperIntroPhase.SUN_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
			shineScale = SolarCreeperIntroPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
			fullDuration = SolarCreeperIntroPhase.DURATION;
		}
		if (state == SolarCreeperSupernovaPhase.ID) {
			bodyScale = SolarCreeperSupernovaPhase.BODY_SCALE.calculate(animationTicks / SolarCreeperSupernovaPhase.DURATION);
			sunScale = 2 * SolarCreeperSupernovaPhase.SUN_SCALE.calculate(animationTicks / SolarCreeperSupernovaPhase.DURATION);
			shineScale = SolarCreeperSupernovaPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperSupernovaPhase.DURATION);
			fullDuration = SolarCreeperSupernovaPhase.DURATION;
		}
		if (state == SolarCreeperSolarRayPhase.ID) {
			sunScale = 2 * SolarCreeperSolarRayPhase.SUN_SCALE.calculate(animationTicks / SolarCreeperSolarRayPhase.DURATION);
			shineScale = SolarCreeperSolarRayPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperSolarRayPhase.DURATION);
			fullDuration = SolarCreeperSolarRayPhase.DURATION;
		}
		if (state == SolarCreeperBlackHolePhase.ID) {
			float progress = animationTicks / SolarCreeperBlackHolePhase.DURATION;
			float baseScale = 2 * SolarCreeperBlackHolePhase.SUN_SCALE.calculate(progress);
			float jitterFreq = SolarCreeperBlackHolePhase.JITTER_FREQ.calculate(progress);
			float jitter = 1 + 0.12f * Mth.sin(jitterFreq * animationTicks * 0.3f);
			sunScale = baseScale * jitter;
			float redness = SolarCreeperBlackHolePhase.SUN_REDNESS.calculate(progress);
			sunColor = FastColor.ARGB32.color(255, 255, (int) Mth.lerp(redness, 255, 80), (int) Mth.lerp(redness, 255, 80));
			blackHoleScale = SolarCreeperBlackHolePhase.BLACK_HOLE_SCALE.calculate(progress);
			shineScale = SolarCreeperBlackHolePhase.SHINE_SCALE.calculate(progress) * jitter;
			fullDuration = SolarCreeperBlackHolePhase.DURATION;
		}
		if (state == SolarCreeperGalaxyPhase.ID) {
			float progress = animationTicks / SolarCreeperGalaxyPhase.DURATION;
			float baseScale = SolarCreeperGalaxyPhase.BODY_SCALE.calculate(progress);
			float jitterFreq = SolarCreeperGalaxyPhase.JITTER_FREQ.calculate(progress);
			float jitter = 1 + 0.12f * Mth.sin(jitterFreq * animationTicks * 0.3f);
			bodyScale = baseScale * jitter;
			fullDuration = SolarCreeperGalaxyPhase.DURATION;
		}
		if (state == SolarCreeperPowerUpPhase.ID) {
			getModel().shatterProgress = SolarCreeperPowerUpPhase.SHATTER_DEGREE.calculate(animationTicks / SolarCreeperPowerUpPhase.DURATION);
		} else {
			getModel().shatterProgress = 0;
		}
		boolean tooEarly = entity.getBehaviorTicks() < 3;
		if (bodyScale > 0) {
			if (tooEarly) {
				bodyScale = 1;
			}
			poseStack.pushPose();
			poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
			if (state == SolarCreeperGalaxyPhase.ID) {
				poseStack.scale(bodyScale, 1 / (bodyScale * bodyScale), bodyScale);
			} else {
				poseStack.scale(bodyScale, bodyScale, bodyScale);
			}
			poseStack.translate(0.0F, -entity.getBbHeight() / 2, 0.0F);
			super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
			poseStack.popPose();
		}
		if (tooEarly) return;
		if (sunScale > 0) {
			poseStack.pushPose();
			if (state == SolarCreeperSolarRayPhase.ID || state == SolarCreeperBlackHolePhase.ID) {
				poseStack.translate(sunAbovePos.x - pos.x, sunAbovePos.y - pos.y, sunAbovePos.z - pos.z);
			} else {
				poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
			}
			poseStack.scale(sunScale, sunScale, sunScale);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, -1.5F, 0.0F);
			RenderType renderType = this.sunModel.renderType(SUN_TEXTURE);
			VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
			this.sunModel.setupAnim(entity, 0, 0, getBob(entity, partialTicks), 0, 0);
			this.sunModel.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, sunColor);
			poseStack.popPose();
		}
		if (blackHoleScale > 0) {
			poseStack.pushPose();
			poseStack.translate(sunAbovePos.x - pos.x, sunAbovePos.y - pos.y, sunAbovePos.z - pos.z);
			poseStack.scale(blackHoleScale, blackHoleScale, blackHoleScale);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, -1.5F, 0.0F);
			RenderType renderType = this.sunModel.renderType(BLACK_HOLE_TEXTURE);
			VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
			this.sunModel.setupAnim(entity, 0, 0, getBob(entity, partialTicks), 0, 0);
			this.sunModel.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			poseStack.popPose();
		}
		if (shineScale > 0) {
			poseStack.pushPose();
			if (state == SolarCreeperSolarRayPhase.ID || state == SolarCreeperBlackHolePhase.ID) {
				poseStack.translate(sunAbovePos.x - pos.x, sunAbovePos.y - pos.y, sunAbovePos.z - pos.z);
			} else {
				poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
			}
			poseStack.scale(shineScale, shineScale, shineScale);
			if (state == SolarCreeperBlackHolePhase.ID) {
				poseStack.scale(entity.getBbHeight() * 4, entity.getBbHeight() * 4, entity.getBbHeight() * 4);
				PoseStack.Pose pose = poseStack.last();
				VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.dragonRays());
				Vec3 camPos = camera.getPosition();
				Vec3 sight = camPos.subtract(sunAbovePos);
				Vec3 sideOffset = SolarCreeperBlackHolePhase.BLACK_HOLE_RAY_NORMAL.cross(sight).normalize().scale(0.2);
				vertexConsumer.addVertex(pose, 0, 0, 0).setColor(FastColor.ARGB32.color(255, 139, 38, 19));
				vertexConsumer.addVertex(pose, SolarCreeperBlackHolePhase.BLACK_HOLE_RAY_NORMAL.add(sideOffset).toVector3f()).setColor(FastColor.ARGB32.color(0, 229, 84, 6));
				vertexConsumer.addVertex(pose, SolarCreeperBlackHolePhase.BLACK_HOLE_RAY_NORMAL.subtract(sideOffset).toVector3f()).setColor(FastColor.ARGB32.color(0, 229, 84, 6));
				vertexConsumer.addVertex(pose, 0, 0, 0).setColor(FastColor.ARGB32.color(255, 139, 38, 19));
				vertexConsumer.addVertex(pose, SolarCreeperBlackHolePhase.BLACK_HOLE_RAY_NORMAL.scale(-1).subtract(sideOffset).toVector3f()).setColor(FastColor.ARGB32.color(0, 229, 84, 6));
				vertexConsumer.addVertex(pose, SolarCreeperBlackHolePhase.BLACK_HOLE_RAY_NORMAL.scale(-1).add(sideOffset).toVector3f()).setColor(FastColor.ARGB32.color(0, 229, 84, 6));
			} else {
				poseStack.mulPose(new Quaternionf(this.entityRenderDispatcher.cameraOrientation()).rotateY(Mth.PI));
				PoseStack.Pose pose = poseStack.last();
				VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.dragonRays());
				int rays = 5;
				for (int i = 0; i < rays; i++) {
					vertexConsumer.addVertex(pose, 0, 0, 0).setColor(FastColor.ARGB32.color(255, 255, 213, 74));
					float angle = i * Mth.TWO_PI / rays + (animationTicks / fullDuration) * Mth.PI * 1.5f;
					vertexConsumer.addVertex(pose, Mth.sin(angle) * entity.getBbHeight() * 3, Mth.cos(angle) * entity.getBbHeight() * 3, 0).setColor(FastColor.ARGB32.color(0, 255, 213, 74));
					float largerAngle = angle + Mth.TWO_PI / 12;
					vertexConsumer.addVertex(pose, Mth.sin(largerAngle) * entity.getBbHeight() * 3, Mth.cos(largerAngle) * entity.getBbHeight() * 3, 0).setColor(FastColor.ARGB32.color(0, 255, 213, 74));
				}
			}
			poseStack.popPose();
		}
		if (state == SolarCreeperSolarRayPhase.ID) {
			Vec3 sight = camera.getPosition().subtract(sunAbovePos);
			Vector3f normalVec = entity.getRenderSolarRayNormal(partialTicks);
			float angle = entity.getRenderSolarRayAngle(partialTicks);
			Vec3 n = new Vec3(normalVec);
			if (n.lengthSqr() < 0.01) n = new Vec3(0, 1, 0);
			n = n.normalize();
			Vec3 zOffset = sunAbovePos.subtract(camera.getPosition()).normalize().scale(0.01);
			poseStack.pushPose();
			poseStack.translate(sunAbovePos.x - pos.x, sunAbovePos.y - pos.y, sunAbovePos.z - pos.z);
			PoseStack.Pose pose = poseStack.last();
			for (int i = 0; i < 6; i++) {
				if (entity.getRenderSolarRayWidth(i, partialTicks) > 0) {
					float length = entity.getRenderSolarRayLength(i, partialTicks);
					Vec3 diff = ESMathUtil.rotateAroundAxis(n, i * 60 + angle, length);
					Vec3 bodyEndDiff = diff.normalize().scale(Math.max(diff.length() - 0.3f, 0));
					float jitterWidth = 0.8f;
					jitterWidth = jitterWidth * 0.2f * (float) Math.sin((entity.tickCount + partialTicks) * 2.1f) + jitterWidth * 0.8f;
					jitterWidth *= entity.getRenderSolarRayWidth(i, partialTicks);
					Vec3 jitterOffset = diff.cross(sight).normalize().scale(jitterWidth / 2);
					float staticWidth = 0.8f;
					staticWidth *= entity.getRenderSolarRayWidth(i, partialTicks);
					Vec3 staticOffset = diff.cross(sight).normalize().scale(staticWidth / 2);
					VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LASER_JITTER_TEXTURE));

					consumer.addVertex(pose, jitterOffset.toVector3f()).setColor(FastColor.ARGB32.color(255, 255, 213, 74)).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, jitterOffset.scale(-1).toVector3f()).setColor(FastColor.ARGB32.color(255, 255, 213, 74)).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, bodyEndDiff.add(jitterOffset.scale(-1)).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, bodyEndDiff.add(jitterOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);

					consumer.addVertex(pose, bodyEndDiff.add(jitterOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, bodyEndDiff.add(jitterOffset.scale(-1)).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, diff.add(jitterOffset.scale(-1)).toVector3f()).setColor(-1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, diff.add(jitterOffset).toVector3f()).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);

					consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LASER_STATIC_TEXTURE));

					consumer.addVertex(pose, staticOffset.add(zOffset).toVector3f()).setColor(FastColor.ARGB32.color(255, 255, 213, 74)).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, staticOffset.scale(-1).add(zOffset).toVector3f()).setColor(FastColor.ARGB32.color(255, 255, 213, 74)).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, bodyEndDiff.add(staticOffset.scale(-1)).add(zOffset).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, bodyEndDiff.add(staticOffset).add(zOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);

					consumer.addVertex(pose, bodyEndDiff.add(staticOffset).add(zOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, bodyEndDiff.add(staticOffset.scale(-1)).add(zOffset).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, diff.add(staticOffset.scale(-1)).add(zOffset).toVector3f()).setColor(-1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
					consumer.addVertex(pose, diff.add(staticOffset).add(zOffset).toVector3f()).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0, 1, 0);
				}
			}
			poseStack.popPose();
		}
		if (state == SolarCreeperPowerUpPhase.ID) {
			float powerUpProgress = animationTicks / SolarCreeperPowerUpPhase.DURATION;
			float starScale = SolarCreeperPowerUpPhase.STAR_SCALE.calculate(powerUpProgress);
			float connection = SolarCreeperPowerUpPhase.CONNECTION_DEGREE.calculate(powerUpProgress);

			if (starScale > 0) {
				poseStack.pushPose();
				float age = entity.tickCount + partialTicks;
				poseStack.translate(0, entity.getBbHeight() / 2 + Math.sin(age * 0.2) * 0.6, 0);
				ShiningStarRenderer.renderStar(poseStack, buffer, age, entity.getId(), starScale, FastColor.ARGB32.color(255, 243, 234, 126));
				poseStack.popPose();
			}

			if (connection > 0) {
				renderSolarTether(entity, partialTicks, poseStack, buffer, pos, connection);
			}
		}
	}

	private void renderSolarTether(T entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, Vec3 pos, float connection) {
		SolarCreeperModel<T> model = getModel();

		float yaw = Mth.lerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);

		Vec3 headPos = ESModelUtil.getModelPartOffsetPosition(entity, yaw, List.of(model.root, model.body, model.head));
		Vec3 bodyPos = ESModelUtil.getModelPartOffsetPosition(entity, yaw, List.of(model.root, model.body));
		Vec3 leg1Pos = ESModelUtil.getModelPartOffsetPosition(entity, yaw, List.of(model.root, model.leg1));
		Vec3 leg2Pos = ESModelUtil.getModelPartOffsetPosition(entity, yaw, List.of(model.root, model.leg2));
		Vec3 leg3Pos = ESModelUtil.getModelPartOffsetPosition(entity, yaw, List.of(model.root, model.leg3));
		Vec3 leg4Pos = ESModelUtil.getModelPartOffsetPosition(entity, yaw, List.of(model.root, model.leg4));

		Vec3[] positions = {headPos, bodyPos, leg1Pos, leg2Pos, leg3Pos, leg4Pos};

		Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		Vec3 sight = camPos.subtract(pos).normalize();

		VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(SOLAR_TETHER_TEXTURE));
		PoseStack.Pose pose = poseStack.last();

		float[][] segments = {
			{0f, 0.2f, 0, 1},
			{0.2f, 0.4f, 1, 2},
			{0.4f, 0.6f, 2, 3},
			{0.6f, 0.8f, 3, 4},
			{0.8f, 1.0f, 4, 5}
		};

		for (float[] seg : segments) {
			float segStart = seg[0];
			float segEnd = seg[1];
			int fromIdx = (int) seg[2];
			int toIdx = (int) seg[3];

			if (connection <= segStart) continue;
			float segProgress = Math.min((connection - segStart) / (segEnd - segStart), 1f);

			Vec3 from = positions[fromIdx];
			Vec3 to = positions[toIdx];
			to = ESMathUtil.lerpVec(segProgress, from, to);
			Vec3 diff = to.subtract(from);

			if (diff.length() < 0.01) continue;

			Vec3 sideOffset = diff.cross(sight).normalize().scale(0.035f);

			consumer.addVertex(pose, from.add(sideOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			consumer.addVertex(pose, from.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			consumer.addVertex(pose, to.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			consumer.addVertex(pose, to.add(sideOffset).toVector3f()).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
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
		if (entity.getBehaviorTicks() >= 3) {
			if (state == SolarCreeperIntroPhase.ID) {
				shineScale = SolarCreeperIntroPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperIntroPhase.DURATION);
			}
			if (state == SolarCreeperSupernovaPhase.ID) {
				shineScale = SolarCreeperSupernovaPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperSupernovaPhase.DURATION);
			}
			if (state == SolarCreeperGalaxyPhase.ID) {
				shineScale = SolarCreeperGalaxyPhase.SHINE_SCALE.calculate(animationTicks / SolarCreeperGalaxyPhase.DURATION);
			}
		}
		return Mth.clamp(shineScale, 0, 1);
	}

	@Override
	protected float getShadowRadius(T mob) {
		return (mob.getBehaviorState() == SolarCreeperIntroPhase.ID
			|| mob.getBehaviorState() == SolarCreeperSupernovaPhase.ID)
			? 0 : super.getShadowRadius(mob);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
