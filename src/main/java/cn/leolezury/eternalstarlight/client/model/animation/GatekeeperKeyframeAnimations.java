package cn.leolezury.eternalstarlight.client.model.animation;

import cn.leolezury.eternalstarlight.client.model.TheGatekeeperModel;
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
public class GatekeeperKeyframeAnimations {
    public static void animate(TheGatekeeperModel<?> model, AnimationDefinition definition, long accumulatedTime, float fl, Vector3f cache) {
        float f = getElapsedSeconds(definition, accumulatedTime);

        for(Map.Entry<String, List<AnimationChannel>> entry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getAnyDescendantWithName(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent((p_232330_) -> {
                list.forEach((p_232311_) -> {
                    Keyframe[] akeyframe = p_232311_.keyframes();
                    int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, (p_232315_) -> f <= akeyframe[p_232315_].timestamp()) - 1);
                    int j = Math.min(akeyframe.length - 1, i + 1);
                    Keyframe keyframe = akeyframe[i];
                    Keyframe keyframe1 = akeyframe[j];
                    float f1 = f - keyframe.timestamp();
                    float f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                    keyframe1.interpolation().apply(cache, f2, akeyframe, i, j, fl);
                    p_232311_.target().apply(p_232330_, cache);
                });
            });
        }

    }

    private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
        float f = (float)accumulatedTime / 1000.0F;
        return definition.looping() ? f % definition.lengthInSeconds() : f;
    }

    public static Vector3f posVec(float x, float y, float z) {
        return new Vector3f(x, -y, z);
    }

    public static Vector3f degreeVec(float x, float y, float z) {
        return new Vector3f(x * ((float)Math.PI / 180F), y * ((float)Math.PI / 180F), z * ((float)Math.PI / 180F));
    }

    public static Vector3f scaleVec(double x, double y, double z) {
        return new Vector3f((float)(x - 1.0D), (float)(y - 1.0D), (float)(z - 1.0D));
    }
}
