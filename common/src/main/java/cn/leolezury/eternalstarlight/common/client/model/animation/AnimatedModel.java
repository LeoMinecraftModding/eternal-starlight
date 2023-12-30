package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

import java.util.Optional;


@Environment(EnvType.CLIENT)
public interface AnimatedModel {
    Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    default void animate(AnimationState state, AnimationDefinition definition, float tickCount) {
        this.animate(state, definition, tickCount, 1.0F, 1.0F);
    }

    default void animate(AnimationState state, AnimationDefinition definition, float tickCount, float speed, float scale) {
        state.updateTime(tickCount, speed);
        state.ifStarted((animState) -> ESKeyframeAnimations.animate(this, definition, animState.getAccumulatedTime(), scale, ANIMATION_VECTOR_CACHE));
    }

    default void animateWalk(AnimationDefinition definition, float swing, float swingAmount, float speed, float scale) {
        long accumulatedTime = (long)(swing * 50.0F * speed);
        float interpolationScale = Math.min(swingAmount * scale, 1.0F);
        ESKeyframeAnimations.animate(this, definition, accumulatedTime, interpolationScale, ANIMATION_VECTOR_CACHE);
    }

    ModelPart root();

    default Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root") ? Optional.of(root()) : root().getAllParts().filter((part) -> part.hasChild(name)).findFirst().map((part) -> part.getChild(name));
    }
}
