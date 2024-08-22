package cn.leolezury.eternalstarlight.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class TrailEffect {
	private static final int MAX_CAPACITY = 65536;

	private final ArrayList<TrailPoint> verticalPoints = new ArrayList<>();
	private final ArrayList<TrailPoint> horizontalPoints = new ArrayList<>();
	private final ArrayList<TrailPoint> verticalRenderPoints = new ArrayList<>();
	private final ArrayList<TrailPoint> horizontalRenderPoints = new ArrayList<>();
	private final float width;
	private float oldLength;
	private float length;

	public ArrayList<TrailPoint> getVerticalRenderPoints() {
		return verticalRenderPoints;
	}

	public ArrayList<TrailPoint> getHorizontalRenderPoints() {
		return horizontalRenderPoints;
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

	public TrailEffect(float width, int length) {
		this.width = width;
		this.length = length;
	}

	private List<TrailPoint> getPoints(boolean vertical) {
		return vertical ? verticalPoints : horizontalPoints;
	}

	public void update(TrailPoint point, boolean vertical) {
		if (getPoints(vertical).isEmpty() || getPoints(vertical).getFirst().center().distanceTo(point.center()) > 0.1) {
			getPoints(vertical).addFirst(point);
		}
		if (getPoints(vertical).size() > MAX_CAPACITY) {
			getPoints(vertical).removeLast();
		}
	}

	public void update(Vec3 pos, Vec3 delta) {
		this.oldLength = length;
		float yaw = ESMathUtil.positionToYaw(delta);
		float pitch = ESMathUtil.positionToPitch(delta);
		Vec3 upper = ESMathUtil.rotationToPosition(pos, width / 2f, pitch - 90, yaw);
		Vec3 lower = ESMathUtil.rotationToPosition(pos, width / 2f, pitch + 90, yaw);
		update(new TrailPoint(upper, lower), true);
		Vec3 offset = upper.subtract(lower).cross(delta).normalize().scale(width / 2);
		Vec3 upper1 = pos.add(offset);
		Vec3 lower1 = pos.add(offset.scale(-1));
		update(new TrailPoint(upper1, lower1), false);
	}

	public void prepareRender(Vec3 pos, Vec3 delta, float partialTicks) {
		float yaw = ESMathUtil.positionToYaw(delta);
		float pitch = ESMathUtil.positionToPitch(delta);
		Vec3 upper = ESMathUtil.rotationToPosition(pos, width / 2f, pitch - 90, yaw);
		Vec3 lower = ESMathUtil.rotationToPosition(pos, width / 2f, pitch + 90, yaw);
		verticalRenderPoints.clear();
		verticalRenderPoints.addAll(verticalPoints);
		prepare(new TrailPoint(upper, lower), true, partialTicks);
		Vec3 offset = upper.subtract(lower).cross(delta).normalize().scale(width / 2);
		Vec3 upper1 = pos.add(offset);
		Vec3 lower1 = pos.add(offset.scale(-1));
		horizontalRenderPoints.clear();
		horizontalRenderPoints.addAll(horizontalPoints);
		prepare(new TrailPoint(upper1, lower1), false, partialTicks);
	}

	private void prepare(TrailPoint point, boolean vertical, float partialTicks) {
		ArrayList<TrailPoint> points = vertical ? verticalRenderPoints : horizontalRenderPoints;
		ArrayList<TrailPoint> modified = new ArrayList<>();
		points.addFirst(point);
		float totalLength = 0;
		float renderLength = Mth.lerp(partialTicks, oldLength, length);
		for (int i = 0; i < points.size() - 1; i++) {
			TrailPoint from = points.get(i);
			TrailPoint to = points.get(i + 1);
			float distance = (float) from.center().distanceTo(to.center());
			totalLength += distance;
			if (totalLength > renderLength) {
				points.set(i + 1, interpolateTrailPoint((totalLength - renderLength) / distance, to, from));
				modified.addAll(points.subList(0, i + 2));
				totalLength = renderLength;
				break;
			}
		}
		if (!modified.isEmpty()) {
			points.clear();
			points.addAll(modified);
		}
		float currentLength = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			TrailPoint from = points.get(i);
			TrailPoint to = points.get(i + 1);
			float distance = (float) from.center().distanceTo(to.center());
			points.set(i, points.get(i).withWidth((totalLength - currentLength) * (width / totalLength)));
			currentLength += distance;
		}
		if (points.size() > 1) {
			points.set(points.size() - 1, points.getLast().withWidth(0.01f));
		}
	}

	private TrailPoint interpolateTrailPoint(float progress, TrailPoint first, TrailPoint second) {
		return new TrailPoint(ESMathUtil.lerpVec(progress, first.upper(), second.upper()), ESMathUtil.lerpVec(progress, first.lower(), second.lower()));
	}

	@Environment(EnvType.CLIENT)
	public void render(VertexConsumer consumer, PoseStack stack, boolean vertical, float r, float g, float b, float a, int light) {
		ArrayList<TrailPoint> points = vertical ? verticalRenderPoints : horizontalRenderPoints;
		if (points.size() >= 2) {
			for (int i = 0; i < points.size() - 1; i++) {
				TrailPoint from = points.get(i);
				TrailPoint to = points.get(i + 1);
				PoseStack.Pose pose = stack.last();
				consumer.addVertex(pose, (float) from.upper().x, (float) from.upper().y, (float) from.upper().z).setColor(r, g, b, i == 0 ? 0 : a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) to.upper().x, (float) to.upper().y, (float) to.upper().z).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) to.lower().x, (float) to.lower().y, (float) to.lower().z).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) from.lower().x, (float) from.lower().y, (float) from.lower().z).setColor(r, g, b, i == 0 ? 0 : a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			}
		}
	}

	public record TrailPoint(Vec3 upper, Vec3 lower) {
		public Vec3 center() {
			return lower().add(upper().subtract(lower()).scale(0.5));
		}

		public float width() {
			return (float) upper().distanceTo(lower());
		}

		public TrailPoint withWidth(float width) {
			Vec3 center = center();
			Vec3 upperVec = upper().subtract(center);
			Vec3 lowerVec = lower().subtract(center);
			return new TrailPoint(center.add(upperVec.normalize().scale(width / 2)), center.add(lowerVec.normalize().scale(width / 2)));
		}
	}
}
