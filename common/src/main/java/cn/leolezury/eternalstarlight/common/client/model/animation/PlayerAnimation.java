package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class PlayerAnimation {
    public static final AnimationDefinition MOONRING_GREATSWORD_TEST = AnimationDefinition.Builder.withLength(5f)
            .addAnimation("left_arm",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-158.53f, -13.12f, 30f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("left_sleeve",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-158.53f, -13.12f, 30f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.5f, KeyframeAnimations.degreeVec(-160f, -5f, 10f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5f, KeyframeAnimations.degreeVec(-160f, -15f, 40f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();
}
