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
	public void render(VertexConsumer consumer, PoseStack stack, float r, float g, float b, float a, int light) {
		if (renderPoints.size() >= 2) {
			for (int i = 0; i < renderPoints.size() - 1; i++) {
				TrailPoint from = renderPoints.get(i);
				TrailPoint to = renderPoints.get(i + 1);
				Vec3 fromDelta = to.pos().subtract(from.pos());
				Vec3 toDelta = i == renderPoints.size() - 2 ? fromDelta : renderPoints.get(i + 2).pos().subtract(to.pos());
				PoseStack.Pose pose = stack.last();
				consumer.addVertex(pose, (float) from.getUpperPoint(fromDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).x, (float) from.getUpperPoint(fromDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).y, (float) from.getUpperPoint(fromDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).z).setColor(r, g, b, Mth.clamp(a * from.alphaFactor(), 0, 1)).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) to.getUpperPoint(toDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).x, (float) to.getUpperPoint(toDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).y, (float) to.getUpperPoint(toDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).z).setColor(r, g, b, Mth.clamp(a * to.alphaFactor(), 0, 1)).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) to.getLowerPoint(toDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).x, (float) to.getLowerPoint(toDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).y, (float) to.getLowerPoint(toDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).z).setColor(r, g, b, Mth.clamp(a * to.alphaFactor(), 0, 1)).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				consumer.addVertex(pose, (float) from.getLowerPoint(fromDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).x, (float) from.getLowerPoint(fromDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).y, (float) from.getLowerPoint(fromDelta, width, Minecraft.getInstance().gameRenderer.getMainCamera()).z).setColor(r, g, b, Mth.clamp(a * from.alphaFactor(), 0, 1)).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			}
		}
	}

	public record TrailPoint(Vec3 pos, float alphaFactor) {
		public TrailPoint(Vec3 pos) {
			this(pos, 1);
		}

		public Vec3 getUpperPoint(Vec3 delta, float width, Camera camera) {
			return pos.add(delta.cross(new Vec3(camera.getLookVector())).normalize().scale(width / 2));
		}

		public Vec3 getLowerPoint(Vec3 delta, float width, Camera camera) {
			return pos.add(delta.cross(new Vec3(camera.getLookVector())).normalize().scale(-width / 2));
		}

		public TrailPoint withAlphaFactor(float alpha) {
			return new TrailPoint(pos(), alpha);
		}
	}
}
