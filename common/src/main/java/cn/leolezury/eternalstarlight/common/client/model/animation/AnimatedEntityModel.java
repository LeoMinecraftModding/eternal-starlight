package cn.leolezury.eternalstarlight.common.client.model.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public abstract class AnimatedEntityModel<E extends Entity> extends EntityModel<E> implements AnimatedModel {
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root().render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
