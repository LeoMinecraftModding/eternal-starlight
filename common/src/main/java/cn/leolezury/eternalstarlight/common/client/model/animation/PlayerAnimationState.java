package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.minecraft.client.animation.AnimationDefinition;

public record PlayerAnimationState (AnimationDefinition definition, boolean renderLeftArm, boolean renderRightArm) {
}
