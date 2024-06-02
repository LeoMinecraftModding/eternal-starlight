package cn.leolezury.eternalstarlight.common.client.model.animation.definition;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class StarlightGolemAnimation {
        public static final AnimationDefinition LASER_BEAM = AnimationDefinition.Builder.withLength(10.0F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.375F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.degreeVec(-12.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(10.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(3.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5833F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.6667F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.875F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9583F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.125F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2083F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.3333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4167F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.6667F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.75F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.875F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.9583F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.1667F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.25F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.375F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.4583F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.6667F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.875F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.9583F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.125F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2083F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.4167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.1667F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe(3.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.625F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.8333F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9167F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.1667F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.375F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4583F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.625F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7083F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.9167F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.125F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2083F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.4167F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.625F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7083F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.9167F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.1667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.25F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.4583F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0F, 5.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-70.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-190.0F, 120.0F, -85.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.0F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 110.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.7083F, KeyframeAnimations.degreeVec(50.0F, 35.0F, 120.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.25F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(10.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.625F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7083F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.8333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9167F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.1667F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.25F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.375F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4583F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.625F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7083F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.9167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.125F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2083F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.4167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.625F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7083F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.9167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.1667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.25F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.3333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.4583F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5417F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.625F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.1667F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe(3.2083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.6667F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.875F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9583F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.125F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2083F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4167F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.6667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.75F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.9583F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.1667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.25F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.4583F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.6667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.9583F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2083F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2917F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0F, -5.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-70.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-190.0F, -120.0F, 85.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.0F, KeyframeAnimations.degreeVec(0.0F, -50.0F, -110.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.7083F, KeyframeAnimations.degreeVec(50.0F, -35.0F, -120.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.25F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(10.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.6667F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.75F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.875F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9583F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0417F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.125F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2083F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.6667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.75F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.8333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.9583F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0417F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.125F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.1667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.25F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.3333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.4583F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5417F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.625F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.6667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.8333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.9583F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0417F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.125F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2083F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2917F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.375F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5833F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.6667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.1667F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe(3.2083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.625F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7083F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9167F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.1667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.25F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4583F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7083F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7917F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2083F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2917F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7083F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7917F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.25F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.3333F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5417F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.625F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(7.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(8.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(9.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(10.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.1667F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.625F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7083F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7917F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0833F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.1667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.25F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.3333F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.4583F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5417F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.625F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7083F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7917F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.875F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0833F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.1667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2083F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2917F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.375F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5833F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.6667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7083F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7917F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.875F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0833F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.1667F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.25F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.3333F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.4167F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5417F, KeyframeAnimations.posVec(-1.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.625F, KeyframeAnimations.posVec(1.0F, 2.0F, 0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.7083F, KeyframeAnimations.posVec(0.0F, 2.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe(3.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.6667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.75F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.9583F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0417F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2083F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2917F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5833F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.75F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.8333F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0417F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.125F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.25F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.3333F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5417F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.625F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.8333F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0417F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.125F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.2917F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.375F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.5833F, KeyframeAnimations.scaleVec(1.02F, 1.02F, 1.02F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.6667F, KeyframeAnimations.scaleVec(0.98F, 0.98F, 0.98F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition ENERGIZED_FLAME = AnimationDefinition.Builder.withLength(1.5F).looping()
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6667F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(25.0F, 15.0F, 25.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-55.0F, -5.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-150.0F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-190.0F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(25.0F, -15.0F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-55.0F, 5.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-150.0F, 10.0F, 40.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-190.0F, 10.0F, 40.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition SMASH = AnimationDefinition.Builder.withLength(3.0F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.25F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(-220.0F, -20.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(-105.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-115.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.7083F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(-220.0F, 20.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(-105.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-115.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.7083F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition CHARGE_START = AnimationDefinition.Builder.withLength(1.0F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -2.5F, 3.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 4.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 4.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("base", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition CHARGE_END = AnimationDefinition.Builder.withLength(1.5F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.125F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.5F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-75.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.8333F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-75.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.8333F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2083F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.1667F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("base", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition CHARGE = AnimationDefinition.Builder.withLength(0.5F).looping()
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.5F, 3.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("base", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(5.0F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(15.0F, 20.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-20.0F, -5.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.125F, KeyframeAnimations.degreeVec(40.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.5F, KeyframeAnimations.degreeVec(40.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.875F, KeyframeAnimations.degreeVec(25.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5F, KeyframeAnimations.degreeVec(-15.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.75F, KeyframeAnimations.degreeVec(85.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10.0F, 30.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(20.0F, 20.0F, 35.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.125F, KeyframeAnimations.degreeVec(-70.0F, -5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.4167F, KeyframeAnimations.degreeVec(-70.0F, -5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7083F, KeyframeAnimations.degreeVec(-80.0F, -5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-30.0F, 20.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7917F, KeyframeAnimations.degreeVec(10.0F, 20.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, -25.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.4167F, KeyframeAnimations.degreeVec(35.0F, 10.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.125F, KeyframeAnimations.degreeVec(-70.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.4167F, KeyframeAnimations.degreeVec(-70.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.7083F, KeyframeAnimations.degreeVec(-80.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-20.0F, -20.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.75F, KeyframeAnimations.degreeVec(10.0F, -20.0F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, -25.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5833F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.125F, KeyframeAnimations.degreeVec(50.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.2083F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.25F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.9583F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.2917F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();
}
