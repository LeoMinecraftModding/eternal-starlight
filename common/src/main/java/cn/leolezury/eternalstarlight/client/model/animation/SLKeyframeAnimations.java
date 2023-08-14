package cn.leolezury.eternalstarlight.client.model.animation;

import cn.leolezury.eternalstarlight.client.model.animation.model.AnimatedModel;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class SLKeyframeAnimations {
    public static void animate(AnimatedModel model, AnimationDefinition definition, long accumulatedTime, float scale, Vector3f cache) {
        float f = getElapsedSeconds(definition, accumulatedTime);

        for (Map.Entry<String, List<AnimationChannel>> entry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getAnyDescendantWithName(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent((modelPart) -> {
                list.forEach((animationChannel) -> {
                    Keyframe[] akeyframe = animationChannel.keyframes();
                    int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, (i1) -> f <= akeyframe[i1].timestamp()) - 1);
                    int j = Math.min(akeyframe.length - 1, i + 1);
                    Keyframe keyframe = akeyframe[i];
                    Keyframe keyframe1 = akeyframe[j];
                    float f1 = f - keyframe.timestamp();
                    float f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                    keyframe1.interpolation().apply(cache, f2, akeyframe, i, j, scale);
                    animationChannel.target().apply(modelPart, cache);
                });
            });
        }
    }



    private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
        float f = (float)accumulatedTime / 1000.0F;
        return definition.looping() ? f % definition.lengthInSeconds() : f;
    }
}
