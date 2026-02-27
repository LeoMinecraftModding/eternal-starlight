package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class TrailEffect {
	private static final int MAX_CAPACITY = 64;

	private final ArrayList<TrailPoint> points = new ArrayList<>();
	public final ArrayList<TrailPoint> renderPoints = new ArrayList<>();
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

	public TrailEffect(float width, float length) {
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
			renderPoints.set(i, renderPoints.get(i).withProgressFactor((totalLength - currentLength) / renderLength));
			currentLength += distance;
		}
		if (renderPoints.size() > 1) {
			renderPoints.set(renderPoints.size() - 1, renderPoints.getLast().withProgressFactor(0));
		}
	}

	private TrailPoint interpolateTrailPoint(float progress, TrailPoint first, TrailPoint second) {
		return new TrailPoint(ESMathUtil.lerpVec(progress, first.pos(), second.pos()));
	}

	@FunctionalInterface
	public interface TrailOffsetFunction {
		TrailOffsetFunction FACE_CAMERA = (look, camXRot, camYRot, tangent) -> tangent.cross(look);
		TrailOffsetFunction Z_ROT = (look, camXRot, camYRot, tangent) -> new Vec3(0, 1, 0).zRot(camXRot * -Mth.DEG_TO_RAD);

		Vec3 calculateTrailOffset(Vec3 look, float camXRot, float camYRot, Vec3 tangent);
	}

	public record TrailPoint(Vec3 pos, float progressFactor) {
		public TrailPoint(Vec3 pos) {
			this(pos, 1);
		}

		public TrailPoint withProgressFactor(float progress) {
			return new TrailPoint(pos(), progress);
		}
	}
}
