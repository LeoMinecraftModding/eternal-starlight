package cn.leolezury.eternalstarlight.common.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ModelUtils {
    public static Vec3 getModelPosition(Entity entity, float yaw, List<ModelPart> parts) {
        PoseStack stack = new PoseStack();
        stack.translate(entity.getX(), entity.getY(), entity.getZ());
        stack.mulPose(new Quaternionf().rotationY((-yaw + 180.0F) * Mth.DEG_TO_RAD));
        stack.scale(-1, -1, 1);
        stack.translate(0, -1.5f, 0);

        for (ModelPart part : parts) {
            part.translateAndRotate(stack);
        }

        Vector4f vec = new Vector4f(0, 0, 0, 1).mul(stack.last().pose());
        Vec3 pos = new Vec3(vec.x(), vec.y(), vec.z());
        Vec3 subtract = pos.subtract(entity.position());
        double scale = 1;
        if (entity instanceof LivingEntity living && living.getAttributes().hasAttribute(Attributes.SCALE)) {
            AttributeInstance instance = living.getAttribute(Attributes.SCALE);
            if (instance != null) {
                scale = instance.getValue();
            }
        }
        return entity.position().add(subtract.scale(scale));
    }
}
