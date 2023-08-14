package cn.leolezury.eternalstarlight.client.model.animation.model;

import cn.leolezury.eternalstarlight.client.model.animation.SLKeyframeAnimations;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public abstract class AnimatedEntityModel<E extends Entity> extends EntityModel<E> implements AnimatedModel {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    protected void animate(AnimationState state, AnimationDefinition definition, float tickCount) {
        this.animate(state, definition, tickCount, 1.0F);
    }

    protected void animate(AnimationState state, AnimationDefinition definition, float tickCount, float speed) {
        state.updateTime(tickCount, speed);
        state.ifStarted((animState) -> SLKeyframeAnimations.animate(this, definition, animState.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
    }

    protected void animateWalk(AnimationDefinition definition, float swing, float swingAmount, float speed, float scale) {
        long accumulatedTime = (long)(swing * 50.0F * speed);
        float interpolationScale = Math.min(swingAmount * scale, 1.0F);
        SLKeyframeAnimations.animate(this, definition, accumulatedTime, interpolationScale, ANIMATION_VECTOR_CACHE);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
