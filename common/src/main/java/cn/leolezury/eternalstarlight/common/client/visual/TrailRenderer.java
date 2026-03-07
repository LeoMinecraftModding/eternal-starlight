package cn.leolezury.eternalstarlight.common.client.visual;

import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class TrailRenderer {
	public static void render(TrailEffect effect, VertexConsumer consumer, PoseStack stack, TrailEffect.TrailOffsetFunction function, boolean solid, float r, float g, float b, float a, int light) {
		render(effect, consumer, stack, function, solid, false, r, g, b, a, 0, 1, 0, 1, light);
	}

	public static void render(TrailEffect effect, VertexConsumer consumer, PoseStack stack, TrailEffect.TrailOffsetFunction function, boolean solid, boolean particleFormat, float r, float g, float b, float a, float u0, float u1, float v0, float v1, int light) {
		int size = effect.renderPoints.size();
		if (size < 2) return;

		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		float halfWidth = effect.getWidth() / 2;

		Vec3[] tangents = new Vec3[size];
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				Vec3 delta = effect.renderPoints.get(1).pos().subtract(effect.renderPoints.get(0).pos());
				tangents[i] = delta.lengthSqr() < 1e-8 ? new Vec3(0, 0, 1) : delta.normalize();
			} else if (i == size - 1) {
				Vec3 delta = effect.renderPoints.get(size - 1).pos().subtract(effect.renderPoints.get(size - 2).pos());
				tangents[i] = delta.lengthSqr() < 1e-8 ? tangents[i - 1] : delta.normalize();
			} else {
				Vec3 prevToNext = effect.renderPoints.get(i + 1).pos().subtract(effect.renderPoints.get(i - 1).pos());
				tangents[i] = prevToNext.lengthSqr() < 1e-8 ? tangents[i - 1] : prevToNext.normalize();
			}
		}

		Vec3[] upperOffsets = new Vec3[size];
		Vec3[] lowerOffsets = new Vec3[size];
		for (int i = 0; i < size; i++) {
			Vec3 tangent = tangents[i];
			if (tangent.lengthSqr() < 0.5) {
				tangent = new Vec3(0, 1, 0);
			}
			Vec3 offsetDir = function.calculateTrailOffset(effect.renderPoints.get(i).pos().subtract(camera.getPosition()), camera.getXRot(), camera.getYRot(), tangent).normalize();
			if (offsetDir.lengthSqr() < 0.5) {
				offsetDir = new Vec3(0, 1, 0);
			}
			upperOffsets[i] = offsetDir.scale(halfWidth);
			lowerOffsets[i] = offsetDir.scale(-halfWidth);
			if (i > 0 && upperOffsets[i].normalize().dot(upperOffsets[i - 1].normalize()) < 0) {
				upperOffsets[i] = upperOffsets[i].reverse();
				lowerOffsets[i] = lowerOffsets[i].reverse();
			}
		}

		PoseStack.Pose pose = stack.last();
		for (int i = 0; i < size - 1; i++) {
			TrailEffect.TrailPoint from = effect.renderPoints.get(i);
			TrailEffect.TrailPoint to = effect.renderPoints.get(i + 1);

			Vec3 fromUpper = from.pos().add(upperOffsets[i]);
			Vec3 toUpper = to.pos().add(upperOffsets[i + 1]);
			Vec3 toLower = to.pos().add(lowerOffsets[i + 1]);
			Vec3 fromLower = from.pos().add(lowerOffsets[i]);

			float fromAlpha = solid ? 1 : Mth.clamp(a * from.progressFactor(), 0, 1);
			float toAlpha = solid ? 1 : Mth.clamp(a * to.progressFactor(), 0, 1);

			if (particleFormat) {
				consumer.addVertex(pose, (float) fromUpper.x(), (float) fromUpper.y(), (float) fromUpper.z())
					.setUv(Mth.lerp(from.progressFactor(), u0, u1), v0).setColor(r, g, b, fromAlpha).setLight(light);
				consumer.addVertex(pose, (float) toUpper.x(), (float) toUpper.y(), (float) toUpper.z())
					.setUv(Mth.lerp(to.progressFactor(), u0, u1), v0).setColor(r, g, b, toAlpha).setLight(light);
				consumer.addVertex(pose, (float) toLower.x(), (float) toLower.y(), (float) toLower.z())
					.setUv(Mth.lerp(to.progressFactor(), u0, u1), v1).setColor(r, g, b, toAlpha).setLight(light);
				consumer.addVertex(pose, (float) fromLower.x(), (float) fromLower.y(), (float) fromLower.z())
					.setUv(Mth.lerp(from.progressFactor(), u0, u1), v1).setColor(r, g, b, fromAlpha).setLight(light);
			} else {
				consumer.addVertex(pose, (float) fromUpper.x(), (float) fromUpper.y(), (float) fromUpper.z())
					.setColor(r, g, b, fromAlpha).setUv(Mth.lerp(from.progressFactor(), u0, u1), v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) toUpper.x(), (float) toUpper.y(), (float) toUpper.z())
					.setColor(r, g, b, toAlpha).setUv(Mth.lerp(to.progressFactor(), u0, u1), v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) toLower.x(), (float) toLower.y(), (float) toLower.z())
					.setColor(r, g, b, toAlpha).setUv(Mth.lerp(to.progressFactor(), u0, u1), v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) fromLower.x(), (float) fromLower.y(), (float) fromLower.z())
					.setColor(r, g, b, fromAlpha).setUv(Mth.lerp(from.progressFactor(), u0, u1), v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			}
		}
	}
}
