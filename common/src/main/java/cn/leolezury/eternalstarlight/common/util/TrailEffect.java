package cn.leolezury.eternalstarlight.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public record TrailEffect(ArrayList<TrailPoint> trailPoints, float width, int length) {
    public TrailEffect(float width, int length) {
        this(new ArrayList<>(), width, length);
    }

    public void update(TrailPoint point) {
        trailPoints().add(point);
        if (trailPoints().size() > length()) {
            trailPoints().removeFirst();
        }
    }

    public void update(Vec3 pos, Vec3 delta) {
        float yaw = ESMathUtil.positionToYaw(delta);
        float pitch = ESMathUtil.positionToPitch(delta);
        Vec3 upper = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch - 90, yaw);
        Vec3 lower = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch + 90, yaw);
        update(new TrailPoint(upper, lower));
    }

    public void createCurrentPoint(Vec3 pos, Vec3 delta) {
        float yaw = ESMathUtil.positionToYaw(delta);
        float pitch = ESMathUtil.positionToPitch(delta);
        Vec3 upper = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch - 90, yaw);
        Vec3 lower = ESMathUtil.rotationToPosition(pos, width() / 2f, pitch + 90, yaw);
        trailPoints().addFirst(new TrailPoint(upper, lower));
    }

    public void removeNearest() {
        if (!trailPoints().isEmpty()) {
            trailPoints().removeFirst();
        }
    }

    @Environment(EnvType.CLIENT)
    public void render(VertexConsumer consumer, PoseStack stack, float r, float g, float b, float a, int light) {
        if (trailPoints().size() >= 2) {
            float secX = 1f / (trailPoints().size() - 1);
            float textureX = 0;
            for (int i = 0; i < trailPoints().size() - 1; i++) {
                TrailPoint from = trailPoints().get(i);
                TrailPoint to = trailPoints().get(i + 1);
                PoseStack.Pose pose = stack.last();
                consumer.vertex(pose, (float) from.upper().x, (float) from.upper().y, (float) from.upper().z).color(r, g, b, a).uv(textureX, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(pose, 0, 1, 0).endVertex();
                consumer.vertex(pose, (float) to.upper().x, (float) to.upper().y, (float) to.upper().z).color(r, g, b, a).uv(textureX + secX, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(pose, 0, 1, 0).endVertex();
                consumer.vertex(pose, (float) to.lower().x, (float) to.lower().y, (float) to.lower().z).color(r, g, b, a).uv(textureX + secX, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(pose, 0, 1, 0).endVertex();
                consumer.vertex(pose, (float) from.lower().x, (float) from.lower().y, (float) from.lower().z).color(r, g, b, a).uv(textureX, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(pose, 0, 1, 0).endVertex();
                textureX += secX;
            }
        }
    }

    public record TrailPoint(Vec3 upper, Vec3 lower) {

    }
}
