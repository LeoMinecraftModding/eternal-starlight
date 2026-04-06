package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ESKeyframeAnimations {
	public static void animate(AnimatedModel model, AnimationDefinition animationDefinition, long accumulatedTime, float scale, Vector3f animationVecCache) {
		float elapsedSeconds = getElapsedSeconds(animationDefinition, accumulatedTime);
		for (Map.Entry<String, List<AnimationChannel>> entry : animationDefinition.boneAnimations().entrySet()) {
			Optional<ModelPart> partOptional = model.getAnyDescendantWithName(entry.getKey());
			List<AnimationChannel> channels = entry.getValue();
			partOptional.ifPresent(part -> channels.forEach(channel -> {
				Keyframe[] keyframes = channel.keyframes();
				int fromIndex = Math.max(0, Mth.binarySearch(0, keyframes.length, queryIndex -> elapsedSeconds <= keyframes[queryIndex].timestamp()) - 1);
				int toIndex = Math.min(keyframes.length - 1, fromIndex + 1);
				Keyframe fromKeyframe = keyframes[fromIndex];
				Keyframe toKeyframe = keyframes[toIndex];
				float timeSinceFromKeyframe = elapsedSeconds - fromKeyframe.timestamp();
				float keyframeProgress;
				if (toIndex != fromIndex) {
					keyframeProgress = Mth.clamp(timeSinceFromKeyframe / (toKeyframe.timestamp() - fromKeyframe.timestamp()), 0.0F, 1.0F);
				} else {
					keyframeProgress = 0.0F;
				}
				toKeyframe.interpolation().apply(animationVecCache, keyframeProgress, keyframes, fromIndex, toIndex, scale);
				channel.target().apply(part, animationVecCache);
			}));
		}
	}

	private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
		float f = (float) accumulatedTime / 1000.0F;
		return definition.looping() ? f % definition.lengthInSeconds() : f;
	}
}
