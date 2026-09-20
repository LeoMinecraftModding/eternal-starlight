package cn.leolezury.eternalstarlight.common.client.trail;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.function.Function;

public class TrailRenderer {
	public static void render(Trail trail, VertexConsumer consumer, PoseStack stack, boolean particleFormat, boolean solid, int light) {
		List<TrailPoint> points = trail.getRenderPoints();
		int size = points.size();
		if (size < 2) {
			return;
		}

		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		Vec3 cameraPos = camera.getPosition();
		Quaternionf cameraRotation = new Quaternionf(camera.rotation());
		float halfWidth = trail.getWidth() / 2;
		Function<Float, Vector4f> colorFunction = trail.getColorFunction();

		Vec3[] tangents = new Vec3[size];
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				Vec3 delta = points.get(1).pos().subtract(points.get(0).pos());
				tangents[i] = delta.lengthSqr() < 1e-8 ? new Vec3(0, 0, 1) : delta.normalize();
			} else if (i == size - 1) {
				Vec3 delta = points.get(size - 1).pos().subtract(points.get(size - 2).pos());
				tangents[i] = delta.lengthSqr() < 1e-8 ? tangents[i - 1] : delta.normalize();
			} else {
				Vec3 prevToNext = points.get(i + 1).pos().subtract(points.get(i - 1).pos());
				tangents[i] = prevToNext.lengthSqr() < 1e-8 ? tangents[i - 1] : prevToNext.normalize();
			}
		}

		Vec3[] upperOffsets = new Vec3[size];
		Vec3[] lowerOffsets = new Vec3[size];
		boolean[] flipped = new boolean[size];
		for (int i = 0; i < size; i++) {
			TrailPoint point = points.get(i);
			Vec3 fixedOffset = point.offset();
			if (fixedOffset != null) {
				upperOffsets[i] = fixedOffset;
				lowerOffsets[i] = fixedOffset.scale(-1);
				continue;
			}
			Vec3 tangent = tangents[i];
			if (tangent.lengthSqr() < 0.5) {
				tangent = new Vec3(0, 1, 0);
			}
			Vec3 offsetDir = calculateOffset(point, tangent, cameraPos, cameraRotation, halfWidth);
			upperOffsets[i] = offsetDir;
			lowerOffsets[i] = offsetDir.scale(-1);
			if (i > 0 && points.get(i - 1).offset() == null && upperOffsets[i].normalize().dot(upperOffsets[i - 1].normalize()) < 0) {
				upperOffsets[i] = upperOffsets[i].reverse();
				lowerOffsets[i] = lowerOffsets[i].reverse();
				flipped[i] = true;
			}
		}

		PoseStack.Pose pose = stack.last();
		for (int i = 0; i < size - 1; i++) {
			TrailPoint from = points.get(i);
			TrailPoint to = points.get(i + 1);

			Vec3 fromUpper = from.pos().add(upperOffsets[i]);
			Vec3 toUpper = to.pos().add(upperOffsets[i + 1]);
			Vec3 toLower = to.pos().add(lowerOffsets[i + 1]);
			Vec3 fromLower = from.pos().add(lowerOffsets[i]);

			Vector4f fromColor = colorFunction != null ? colorFunction.apply(from.progressFactor()) : from.color();
			Vector4f toColor = colorFunction != null ? colorFunction.apply(to.progressFactor()) : to.color();
			float fromAlpha = solid ? 1 : Mth.clamp(fromColor.w() * from.progressFactor(), 0, 1);
			float toAlpha = solid ? 1 : Mth.clamp(toColor.w() * to.progressFactor(), 0, 1);

			float fromU = from.u() >= 0 ? from.u() : Mth.lerp(from.progressFactor(), trail.getU0(), trail.getU1());
			float toU = to.u() >= 0 ? to.u() : Mth.lerp(to.progressFactor(), trail.getU0(), trail.getU1());

			float fromUpperV = flipped[i] ? trail.getV1() : trail.getV0();
			float fromLowerV = flipped[i] ? trail.getV0() : trail.getV1();
			float toUpperV = flipped[i + 1] ? trail.getV1() : trail.getV0();
			float toLowerV = flipped[i + 1] ? trail.getV0() : trail.getV1();

			if (particleFormat) {
				consumer.addVertex(pose, (float) fromUpper.x(), (float) fromUpper.y(), (float) fromUpper.z())
					.setUv(fromU, fromUpperV).setColor(fromColor.x(), fromColor.y(), fromColor.z(), fromAlpha).setLight(light);
				consumer.addVertex(pose, (float) toUpper.x(), (float) toUpper.y(), (float) toUpper.z())
					.setUv(toU, toUpperV).setColor(toColor.x(), toColor.y(), toColor.z(), toAlpha).setLight(light);
				consumer.addVertex(pose, (float) toLower.x(), (float) toLower.y(), (float) toLower.z())
					.setUv(toU, toLowerV).setColor(toColor.x(), toColor.y(), toColor.z(), toAlpha).setLight(light);
				consumer.addVertex(pose, (float) fromLower.x(), (float) fromLower.y(), (float) fromLower.z())
					.setUv(fromU, fromLowerV).setColor(fromColor.x(), fromColor.y(), fromColor.z(), fromAlpha).setLight(light);
			} else {
				consumer.addVertex(pose, (float) fromUpper.x(), (float) fromUpper.y(), (float) fromUpper.z())
					.setColor(fromColor.x(), fromColor.y(), fromColor.z(), fromAlpha).setUv(fromU, fromUpperV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) toUpper.x(), (float) toUpper.y(), (float) toUpper.z())
					.setColor(toColor.x(), toColor.y(), toColor.z(), toAlpha).setUv(toU, toUpperV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) toLower.x(), (float) toLower.y(), (float) toLower.z())
					.setColor(toColor.x(), toColor.y(), toColor.z(), toAlpha).setUv(toU, toLowerV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) fromLower.x(), (float) fromLower.y(), (float) fromLower.z())
					.setColor(fromColor.x(), fromColor.y(), fromColor.z(), fromAlpha).setUv(fromU, fromLowerV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			}
		}
	}

	private static Vec3 calculateOffset(TrailPoint point, Vec3 tangent, Vec3 cameraPos, Quaternionf cameraRotation, float halfWidth) {
		Vec3 view = point.pos().subtract(cameraPos);
		Vec3 dir;
		if (point.facing() == TrailPoint.Facing.CAMERA_BILLBOARD) {
			Vector3f up = new Vector3f(0, 1, 0).rotate(cameraRotation);
			Vec3 upVec = new Vec3(up.x(), up.y(), up.z());
			dir = upVec.subtract(tangent.scale(upVec.dot(tangent)));
			if (dir.lengthSqr() < 1e-6) {
				dir = tangent.cross(view);
			}
		} else {
			dir = tangent.cross(view);
		}
		if (dir.lengthSqr() < 1e-6) {
			dir = new Vec3(0, 1, 0);
		}
		return dir.normalize().scale(halfWidth);
	}
}
