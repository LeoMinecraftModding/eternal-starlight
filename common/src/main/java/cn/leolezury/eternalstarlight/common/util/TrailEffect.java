package cn.leolezury.eternalstarlight.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class TrailEffect {
	private static final int MAX_CAPACITY = 65536;

	private final ArrayList<TrailPoint> points = new ArrayList<>();
	private final ArrayList<TrailPoint> renderPoints = new ArrayList<>();
	private final float width;
	private float oldLength;
	private float length;

	public float getWidth() {
		return width;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public TrailEffect(float width, int length) {
		this.width = width;
		this.length = length;
	}

	public void update(TrailPoint point) {
		if (points.isEmpty() || points.getFirst().pos().distanceTo(point.pos()) > 0.01) {
			points.addFirst(point);
		}
		if (points.size() > MAX_CAPACITY) {
			points.removeLast();
		}
	}

	public void update(Vec3 pos) {
		this.oldLength = length;
		update(new TrailPoint(pos, 1));
	}

	public void prepareRender(Vec3 pos, float partialTicks) {
		renderPoints.clear();
		renderPoints.addAll(points);
		prepare(new TrailPoint(pos), partialTicks);
	}

	private void prepare(TrailPoint point, float partialTicks) {
		ArrayList<TrailPoint> modified = new ArrayList<>();
		renderPoints.addFirst(point);
		float totalLength = 0;
		float renderLength = Mth.lerp(partialTicks, oldLength, length);
		for (int i = 0; i < renderPoints.size() - 1; i++) {
			TrailPoint from = renderPoints.get(i);
			TrailPoint to = renderPoints.get(i + 1);
			float distance = (float) from.pos().distanceTo(to.pos());
			totalLength += distance;
			if (totalLength > renderLength) {
				renderPoints.set(i + 1, interpolateTrailPoint((totalLength - renderLength) / distance, to, from));
				modified.addAll(renderPoints.subList(0, i + 2));
				totalLength = renderLength;
				break;
			}
		}
		if (!modified.isEmpty()) {
			renderPoints.clear();
			renderPoints.addAll(modified);
		}
		float currentLength = 0;
		for (int i = 0; i < renderPoints.size() - 1; i++) {
			TrailPoint from = renderPoints.get(i);
			TrailPoint to = renderPoints.get(i + 1);
			float distance = (float) from.pos().distanceTo(to.pos());
			renderPoints.set(i, renderPoints.get(i).withAlphaFactor((totalLength - currentLength) / renderLength));
			currentLength += distance;
		}
		if (renderPoints.size() > 1) {
			renderPoints.set(renderPoints.size() - 1, renderPoints.getLast().withAlphaFactor(0.01f));
		}
	}

	private TrailPoint interpolateTrailPoint(float progress, TrailPoint first, TrailPoint second) {
		return new TrailPoint(ESMathUtil.lerpVec(progress, first.pos(), second.pos()));
	}

	@Environment(EnvType.CLIENT)
	public void render(VertexConsumer consumer, PoseStack stack, TrailOffsetFunction function, float r, float g, float b, float a, int light) {
		int size = renderPoints.size();
		if (size < 2) return;

		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		Vec3 look = new Vec3(camera.getLookVector()).normalize();
		float halfWidth = width / 2;

		Vec3[] tangents = new Vec3[size];
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				Vec3 delta = renderPoints.get(1).pos().subtract(renderPoints.get(0).pos());
				tangents[i] = delta.lengthSqr() < 1e-8 ? new Vec3(0, 0, 1) : delta.normalize();
			} else if (i == size - 1) {
				Vec3 delta = renderPoints.get(size - 1).pos().subtract(renderPoints.get(size - 2).pos());
				tangents[i] = delta.lengthSqr() < 1e-8 ? tangents[i - 1] : delta.normalize();
			} else {
				Vec3 prevToNext = renderPoints.get(i + 1).pos().subtract(renderPoints.get(i - 1).pos());
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
			Vec3 offsetDir = function.calculateTrailOffset(look, camera.getXRot(), camera.getYRot(), tangent).normalize();
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
			TrailPoint from = renderPoints.get(i);
			TrailPoint to = renderPoints.get(i + 1);

			Vec3 fromUpper = from.pos().add(upperOffsets[i]);
			Vec3 toUpper = to.pos().add(upperOffsets[i + 1]);
			Vec3 toLower = to.pos().add(lowerOffsets[i + 1]);
			Vec3 fromLower = from.pos().add(lowerOffsets[i]);

			float fromAlpha = Mth.clamp(a * from.alphaFactor(), 0, 1);
			float toAlpha = Mth.clamp(a * to.alphaFactor(), 0, 1);

			consumer.addVertex(pose, (float) fromUpper.x(), (float) fromUpper.y(), (float) fromUpper.z())
				.setColor(r, g, b, fromAlpha).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			consumer.addVertex(pose, (float) toUpper.x(), (float) toUpper.y(), (float) toUpper.z())
				.setColor(r, g, b, toAlpha).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			consumer.addVertex(pose, (float) toLower.x(), (float) toLower.y(), (float) toLower.z())
				.setColor(r, g, b, toAlpha).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			consumer.addVertex(pose, (float) fromLower.x(), (float) fromLower.y(), (float) fromLower.z())
				.setColor(r, g, b, fromAlpha).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		}
	}

	@FunctionalInterface
	public interface TrailOffsetFunction {
		TrailOffsetFunction FACE_CAMERA = (look, camXRot, camYRot, tangent) -> tangent.cross(look);
		TrailOffsetFunction Z_ROT = (look, camXRot, camYRot, tangent) -> new Vec3(0, 1, 0).zRot(camXRot * -Mth.DEG_TO_RAD);

		Vec3 calculateTrailOffset(Vec3 look, float camXRot, float camYRot, Vec3 tangent);
	}

	public record TrailPoint(Vec3 pos, float alphaFactor) {
		public TrailPoint(Vec3 pos) {
			this(pos, 1);
		}

		public TrailPoint withAlphaFactor(float alpha) {
			return new TrailPoint(pos(), alpha);
		}
	}
}
