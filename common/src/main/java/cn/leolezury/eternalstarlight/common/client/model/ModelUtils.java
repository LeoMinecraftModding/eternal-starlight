package cn.leolezury.eternalstarlight.common.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ModelUtils {
    public static void translateAndRotateFromModel(PoseStack stack, List<ModelPart> parts, int index) {
        ModelPart part = parts.get(index);
        if (index + 1 < parts.size()) {
            translateAndRotateFromModel(stack, parts, index + 1);
        }
        part.translateAndRotate(stack);
    }

    public static Vec3 getModelPosition(Entity entity, float yaw, List<ModelPart> parts) {
        PoseStack stack = new PoseStack();
        stack.translate(entity.getX(), entity.getY(), entity.getZ());
        stack.mulPose(new Quaternionf().rotationY((-yaw + 180.0F) * (float) Math.PI / 180f));
        stack.scale(-1, -1, 1);
        stack.translate(0, -1.5f, 0);

        translateAndRotateFromModel(stack, parts, 0);
        Vector4f vec = new Vector4f(0, 0, 0, 1).mul(stack.last().pose());
        return new Vec3(vec.x(), vec.y(), vec.z());
    }
}
