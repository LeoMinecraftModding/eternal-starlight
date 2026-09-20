package cn.leolezury.eternalstarlight.common.client.trail;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Trail {
	public static final int DEFAULT_CAPACITY = 128;

	private final List<TrailPoint> points = new ArrayList<>();
	private final List<TrailPoint> renderPoints = new ArrayList<>();
	private final int capacity;
	private final float width;
	private float oldLength;
	private float length;
	private float u0 = 0, u1 = 1, v0 = 0, v1 = 1;
	@Nullable
	private Function<Float, Vector4f> colorFunction;

	public Trail(float width, float length) {
		this(width, length, DEFAULT_CAPACITY);
	}

	public Trail(float width, float length, int capacity) {
		this.width = width;
		this.length = length;
		this.capacity = capacity;
	}

	public float getWidth() {
		return width;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public void setLengthImmediate(float length) {
		this.oldLength = length;
		this.length = length;
	}

	public List<TrailPoint> getPoints() {
		return points;
	}

	public List<TrailPoint> getRenderPoints() {
		return renderPoints;
	}

	public float getU0() {
		return u0;
	}

	public float getU1() {
		return u1;
	}

	public float getV0() {
		return v0;
	}

	public float getV1() {
		return v1;
	}

	public void setUv(float u0, float u1, float v0, float v1) {
		this.u0 = u0;
		this.u1 = u1;
		this.v0 = v0;
		this.v1 = v1;
	}

	public void setColorFunction(@Nullable Function<Float, Vector4f> colorFunction) {
		this.colorFunction = colorFunction;
	}

	@Nullable
	public Function<Float, Vector4f> getColorFunction() {
		return colorFunction;
	}

	public void update(TrailPoint point) {
		this.oldLength = this.length;
		if (points.isEmpty() || points.getFirst().pos().distanceTo(point.pos()) > 0.01) {
			points.addFirst(point);
		}
		while (points.size() > capacity) {
			points.removeLast();
		}
	}

	public void update(Vec3 pos) {
		update(TrailPoint.cameraFacing(pos));
	}

	public void clear() {
		points.clear();
		renderPoints.clear();
	}

	public void prepareRender(@Nullable TrailPoint head, float partialTicks) {
		renderPoints.clear();
		renderPoints.addAll(points);
		float renderLength = Mth.lerp(partialTicks, oldLength, length);
		if (renderLength <= 0.001f) {
			renderPoints.clear();
			return;
		}
		if (head != null) {
			if (!renderPoints.isEmpty()) {
				TrailPoint newest = renderPoints.getFirst();
				head.color(new Vector4f(newest.color()));
				head.setU(newest.u());
			}
			renderPoints.addFirst(head);
		}
		if (renderPoints.size() < 2) {
			renderPoints.clear();
			return;
		}
		prepare(renderLength);
	}

	private void prepare(float renderLength) {
		ArrayList<TrailPoint> modified = new ArrayList<>();
		float totalLength = 0;
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
			renderPoints.get(i).setProgressFactor(Mth.clamp((totalLength - currentLength) / renderLength, 0, 1));
			currentLength += distance;
		}
		if (renderPoints.size() > 1) {
			renderPoints.getLast().setProgressFactor(0);
		}
	}

	private TrailPoint interpolateTrailPoint(float progress, TrailPoint from, TrailPoint to) {
		Vec3 pos = ESMathUtil.lerpVec(progress, from.pos(), to.pos());
		TrailPoint point;
		Vec3 fromOffset = from.offset();
		Vec3 toOffset = to.offset();
		if (fromOffset != null && toOffset != null) {
			Vec3 upper = from.pos().add(fromOffset).lerp(to.pos().add(toOffset), progress);
			Vec3 lower = from.pos().subtract(fromOffset).lerp(to.pos().subtract(toOffset), progress);
			point = TrailPoint.fixed(lower, upper);
		} else {
			point = new TrailPoint(pos, null, to.facing());
		}
		point.color(new Vector4f(from.color()).lerp(to.color(), progress));
		point.setU(from.u() < 0 || to.u() < 0 ? -1 : Mth.lerp(progress, from.u(), to.u()));
		return point;
	}
}
