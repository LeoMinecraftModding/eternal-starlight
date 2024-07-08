package cn.leolezury.eternalstarlight.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record TrailEffect(ArrayList<TrailPoint> verticalPoints, ArrayList<TrailPoint> horizontalPoints, float width, int length) {
	public TrailEffect(float width, int length) {
		this(new ArrayList<>(), new ArrayList<>(), width, length);
	}

	private List<TrailPoint> getPoints(boolean vertical) {
		return vertical ? verticalPoints() : horizontalPoints();
	}

	public void update(TrailPoint point, boolean vertical) {
		getPoints(vertical).addFirst(point);
		if (getPoints(vertical).size() > length()) {
			getPoints(vertical).removeLast();
		}
	}

	public void update(Vec3 pos, Vec3 delta) {
		float yaw = ESMathUtil.positionToYaw(delta);
		float pitch = ESMathUtil.positionToPitch(delta);
		Vec3 upper = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch - 90, yaw);
		Vec3 lower = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch + 90, yaw);
		update(new TrailPoint(upper, lower), true);
		Vec3 offset = upper.subtract(lower).cross(delta).normalize().scale(width() / 2);
		Vec3 upper1 = pos.add(offset);
		Vec3 lower1 = pos.add(offset.scale(-1));
		update(new TrailPoint(upper1, lower1), false);
	}

	public void createCurrentPoint(Vec3 pos, Vec3 delta) {
		float yaw = ESMathUtil.positionToYaw(delta);
		float pitch = ESMathUtil.positionToPitch(delta);
		Vec3 upper = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch - 90, yaw);
		Vec3 lower = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch + 90, yaw);
		verticalPoints().addFirst(new TrailPoint(upper, lower));
		Vec3 offset = upper.subtract(lower).cross(delta).normalize().scale(width() / 2);
		Vec3 upper1 = pos.add(offset);
		Vec3 lower1 = pos.add(offset.scale(-1));
		horizontalPoints().addFirst(new TrailPoint(upper1, lower1));
	}

	public void removeNearest() {
		if (!verticalPoints().isEmpty()) {
			verticalPoints().removeFirst();
		}
		if (!horizontalPoints().isEmpty()) {
			horizontalPoints().removeFirst();
		}
	}

	public boolean trailPointsAllSame() {
		return trailPointsAllSame(true) && trailPointsAllSame(false);
	}

	public boolean trailPointsAllSame(boolean vertical) {
		if (getPoints(vertical).isEmpty()) {
			return true;
		}
		TrailPoint point = getPoints(vertical).getFirst();
		boolean allSame = true;
		for (TrailPoint trailPoint : getPoints(vertical)) {
			if (trailPoint != point) {
				allSame = false;
				break;
			}
		}
		return allSame;
	}

	public float trailLength(float partialTicks, boolean vertical) {
		float sum = 0;
		for (int i = 0; i < getPoints(vertical).size() - 1; i++) {
			TrailPoint from = getPoints(vertical).get(i);
			TrailPoint to = getPoints(vertical).get(i + 1);
			if (i == getPoints(vertical).size() - 2 && getPoints(vertical).size() == length() + 1) {
				to = new TrailPoint(ESMathUtil.lerpVec(partialTicks, to.upper(), from.upper()), ESMathUtil.lerpVec(partialTicks, to.lower(), from.lower()));
			}
			sum += (float) to.center().distanceTo(from.center());
		}
		return sum;
	}

	@Environment(EnvType.CLIENT)
	public void render(VertexConsumer consumer, PoseStack stack, float partialTicks, boolean vertical, float r, float g, float b, float a, int light) {
		if (getPoints(vertical).size() >= 2) {
			float textureX = 0;
			float trailLength = trailLength(partialTicks, vertical);
			for (int i = 0; i < getPoints(vertical).size() - 1; i++) {
				TrailPoint from = getPoints(vertical).get(i);
				TrailPoint to = getPoints(vertical).get(i + 1);
				if (i == getPoints(vertical).size() - 2 && getPoints(vertical).size() == length() + 1) {
					to = new TrailPoint(ESMathUtil.lerpVec(partialTicks, to.upper(), from.upper()), ESMathUtil.lerpVec(partialTicks, to.lower(), from.lower()));
				}
				float secX = (float) to.center().distanceTo(from.center()) / trailLength;
				PoseStack.Pose pose = stack.last();
				consumer.addVertex(pose, (float) from.upper().x, (float) from.upper().y, (float) from.upper().z).setColor(r, g, b, a).setUv(textureX, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) to.upper().x, (float) to.upper().y, (float) to.upper().z).setColor(r, g, b, a).setUv(textureX + secX, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) to.lower().x, (float) to.lower().y, (float) to.lower().z).setColor(r, g, b, a).setUv(textureX + secX, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) from.lower().x, (float) from.lower().y, (float) from.lower().z).setColor(r, g, b, a).setUv(textureX, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				textureX += secX;
			}
		}
	}

	public record TrailPoint(Vec3 upper, Vec3 lower) {
		public Vec3 center() {
			return upper().add(lower().subtract(upper()).scale(0.5));
		}

		public float width() {
			return (float) (upper().distanceTo(lower()) / 2f);
		}
	}
}
