package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.ESKeyframeAnimations;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> implements AnimatedModel {
    @Unique
    private static List<PlayerAnimator.AnimationTransformer> transformers = new ArrayList<>();
    @Unique
    private static PlayerAnimator.PlayerAnimationState playerAnimationState;
    @Unique
    private static AbstractClientPlayer animatedPlayer;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void es_setupAnim(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (livingEntity instanceof AbstractClientPlayer player) {
            for (Map.Entry<PlayerAnimator.AnimationTrigger, PlayerAnimator.AnimationStateFunction> entry : PlayerAnimator.ANIMATIONS.entrySet()) {
                if (entry.getKey().shouldPlay(player)) {
                    PlayerAnimator.PlayerAnimationState state = entry.getValue().get(player);
                    transformers = state.transformers();
                    playerAnimationState = state;
                    animatedPlayer = player;
                    animatePlayer(entry.getKey().animateTicks(player, ageInTicks));
                }
            }
        }
    }

    @Unique
    private void animatePlayer(float ticks) {
        AnimationDefinition definition = playerAnimationState.definition();
        boolean resetLeftArm = playerAnimationState.resetLeftArmBeforeAnimation();
        boolean resetRightArm = playerAnimationState.resetRightArmBeforeAnimation();

        PlayerModel<?> playerModel = (PlayerModel<?>) (Object) this;
        if (resetLeftArm) {
            playerModel.leftArm.resetPose();
            playerModel.leftSleeve.resetPose();
        }
        if (resetRightArm) {
            playerModel.rightArm.resetPose();
            playerModel.rightSleeve.resetPose();
        }

        float scale = 1.0f;

        for (PlayerAnimator.AnimationTransformer transformer : transformers) {
            if (transformer.shouldApply(playerAnimationState, animatedPlayer, playerModel)) {
                transformer.preAnimate(playerAnimationState, animatedPlayer, playerModel);
                scale = transformer.modifyScale(playerAnimationState, animatedPlayer, playerModel, scale);
                ticks = transformer.modifyTicks(playerAnimationState, animatedPlayer, playerModel, ticks);
            }
        }

        ESKeyframeAnimations.animate(this, definition, (long) (ticks * 1000L / 20f), scale, ANIMATION_VECTOR_CACHE);

        for (PlayerAnimator.AnimationTransformer transformer : transformers) {
            if (transformer.shouldApply(playerAnimationState, animatedPlayer, playerModel)) {
                transformer.postAnimate(playerAnimationState, animatedPlayer, playerModel);
            }
        }
    }

    @Unique
    private void transformModelPart(ModelPart part, Vec3 originalPos, Vec3 originalRot) {
        part.x = (float) (part.x - 2 * (part.x - originalPos.x));
        part.yRot = (float) (part.yRot - 2 * (part.yRot - originalRot.y));
        part.zRot = (float) (part.zRot - 2 * (part.zRot - originalRot.z));
    }

    @Unique
    private void transformModelPartLeftAndRight(ModelPart left, ModelPart right, Vec3 leftOriginalPos, Vec3 leftOriginalRot, Vec3 rightOriginalPos, Vec3 rightOriginalRot) {
        transformModelPart(left, leftOriginalPos, leftOriginalRot);
        transformModelPart(right, rightOriginalPos, rightOriginalRot);
    }

    @Unique
    private Vec3 makeModelPartPos(ModelPart part) {
        return new Vec3(part.x, part.y, part.z);
    }

    @Unique
    private Vec3 makeModelPartRot(ModelPart part) {
        return new Vec3(part.xRot, part.yRot, part.zRot);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void es_setupAnimResetPose(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        PlayerModel<?> playerModel = (PlayerModel<?>) (Object) this;
        playerModel.head.resetPose();
        playerModel.body.resetPose();
    }

    @Unique
    @Override
    public ModelPart root() {
        return null;
    }

    @Unique
    @Override
    public Optional<ModelPart> getAnyDescendantWithName(String name) {
        PlayerModel<?> playerModel = (PlayerModel<?>) (Object) this;
        for (PlayerAnimator.AnimationTransformer transformer : transformers) {
            if (transformer.shouldApply(playerAnimationState, animatedPlayer, playerModel)) {
                return transformer.modifyModelPart(playerAnimationState, animatedPlayer, playerModel, name);
            }
        }
        switch (name) {
            case "hat" -> {
                return Optional.of(playerModel.hat);
            }
            case "head" -> {
                return Optional.of(playerModel.head);
            }
            case "body" -> {
                return Optional.of(playerModel.body);
            }
            case "jacket" -> {
                return Optional.of(playerModel.jacket);
            }
            case "right_arm" -> {
                return Optional.of(playerModel.rightArm);
            }
            case "right_sleeve" -> {
                return Optional.of(playerModel.rightSleeve);
            }
            case "left_arm" -> {
                return Optional.of(playerModel.leftArm);
            }
            case "left_sleeve" -> {
                return Optional.of(playerModel.leftSleeve);
            }
            case "right_leg" -> {
                return Optional.of(playerModel.rightLeg);
            }
            case "right_pants" -> {
                return Optional.of(playerModel.rightPants);
            }
            case "left_leg" -> {
                return Optional.of(playerModel.leftLeg);
            }
            case "left_pants" -> {
                return Optional.of(playerModel.leftPants);
            }
        }
        return Optional.empty();
    }
}
