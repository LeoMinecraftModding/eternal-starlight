package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class LuminoFishAnimation {
    public static final AnimationDefinition SWIM = AnimationDefinition.Builder.withLength(0.75f).looping()
            .addAnimation("root",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("left_fin",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("right_fin",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("tail",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(35f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(-35f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();
    public static final AnimationDefinition SWELL = AnimationDefinition.Builder.withLength(1f)
            .addAnimation("root",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(-25f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("head_swell",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.scaleVec(1.2f, 1.2f, 1.2f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("tail",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();
}
