package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.client.model.animation.ESKeyframeAnimations;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimationState;
import cn.leolezury.eternalstarlight.common.client.model.animation.model.AnimatedModel;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> implements AnimatedModel {
    @Unique
    private static boolean doTransform = false;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void setupAnim(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (livingEntity.isUsingItem()) {
            AnimationDefinition definition = null;
            boolean transform = false;
            boolean resetLeftArm = false;
            boolean resetRightArm = false;
            // tricky
            ItemStack useItem = livingEntity.getItemInHand(livingEntity.getUsedItemHand());
            float delta = ageInTicks - ((int) ageInTicks);
            float tick = livingEntity.getTicksUsingItem() + delta;
            boolean animated = false;
            for (Supplier<Item> itemSupplier : ClientSetupHandlers.playerAnimatingItemMap.keySet()) {
                if (useItem.is(itemSupplier.get())) {
                    animated = true;
                    PlayerAnimationState state = ClientSetupHandlers.playerAnimatingItemMap.get(itemSupplier).get(useItem, tick);
                    definition = state.definition();
                    transform = state.shouldTransformToHand();
                    resetLeftArm = state.resetLeftArmBeforeAnimation();
                    resetRightArm = state.resetRightArmBeforeAnimation();
                    break;
                }
            }
            if (animated) {
                doTransform = transform && livingEntity.getUsedItemHand() == InteractionHand.OFF_HAND;

                PlayerModel<?> playerModel = (PlayerModel<?>) (Object) this;
                if (resetLeftArm) {
                    playerModel.leftArm.resetPose();
                    playerModel.leftSleeve.resetPose();
                }
                if (resetRightArm) {
                    playerModel.rightArm.resetPose();
                    playerModel.rightSleeve.resetPose();
                }

                Vec3 hatOriginalPos = makeModelPartPos(playerModel.hat);
                Vec3 hatOriginalRot = makeModelPartRot(playerModel.hat);

                Vec3 headOriginalPos = makeModelPartPos(playerModel.head);
                Vec3 headOriginalRot = makeModelPartRot(playerModel.head);

                Vec3 bodyOriginalPos = makeModelPartPos(playerModel.body);
                Vec3 bodyOriginalRot = makeModelPartRot(playerModel.body);

                Vec3 jacketOriginalPos = makeModelPartPos(playerModel.jacket);
                Vec3 jacketOriginalRot = makeModelPartRot(playerModel.jacket);

                Vec3 cloakOriginalPos = makeModelPartPos(playerModel.cloak);
                Vec3 cloakOriginalRot = makeModelPartRot(playerModel.cloak);

                Vec3 rightArmOriginalPos = makeModelPartPos(playerModel.rightArm);
                Vec3 rightArmOriginalRot = makeModelPartRot(playerModel.rightArm);

                Vec3 rightSleeveOriginalPos = makeModelPartPos(playerModel.rightSleeve);
                Vec3 rightSleeveOriginalRot = makeModelPartRot(playerModel.rightSleeve);

                Vec3 leftArmOriginalPos = makeModelPartPos(playerModel.leftArm);
                Vec3 leftArmOriginalRot = makeModelPartRot(playerModel.leftArm);

                Vec3 leftSleeveOriginalPos = makeModelPartPos(playerModel.leftSleeve);
                Vec3 leftSleeveOriginalRot = makeModelPartRot(playerModel.leftSleeve);

                Vec3 rightLegOriginalPos = makeModelPartPos(playerModel.rightLeg);
                Vec3 rightLegOriginalRot = makeModelPartRot(playerModel.rightLeg);

                Vec3 rightPantsOriginalPos = makeModelPartPos(playerModel.rightPants);
                Vec3 rightPantsOriginalRot = makeModelPartRot(playerModel.rightPants);

                Vec3 leftLegOriginalPos = makeModelPartPos(playerModel.leftLeg);
                Vec3 leftLegOriginalRot = makeModelPartRot(playerModel.leftLeg);

                Vec3 leftPantsOriginalPos = makeModelPartPos(playerModel.leftPants);
                Vec3 leftPantsOriginalRot = makeModelPartRot(playerModel.leftPants);

                ESKeyframeAnimations.animate(this, definition, (long) (tick * 1000L / 20f), 1.0F, ANIMATION_VECTOR_CACHE);

                if (doTransform) {
                    transformModelPart(playerModel.hat, hatOriginalPos, hatOriginalRot);
                    transformModelPart(playerModel.head, headOriginalPos, headOriginalRot);

                    transformModelPart(playerModel.body, bodyOriginalPos, bodyOriginalRot);
                    transformModelPart(playerModel.jacket, jacketOriginalPos, jacketOriginalRot);
                    transformModelPart(playerModel.cloak, cloakOriginalPos, cloakOriginalRot);

                    transformModelPartLeftAndRight(playerModel.leftArm, playerModel.rightArm, leftArmOriginalPos, leftArmOriginalRot, rightArmOriginalPos, rightArmOriginalRot);
                    transformModelPartLeftAndRight(playerModel.leftSleeve, playerModel.rightSleeve, leftSleeveOriginalPos, leftSleeveOriginalRot, rightSleeveOriginalPos, rightSleeveOriginalRot);

                    transformModelPartLeftAndRight(playerModel.leftLeg, playerModel.rightLeg, leftLegOriginalPos, leftLegOriginalRot, rightLegOriginalPos, rightLegOriginalRot);
                    transformModelPartLeftAndRight(playerModel.leftPants, playerModel.rightPants, leftPantsOriginalPos, leftPantsOriginalRot, rightPantsOriginalPos, rightPantsOriginalRot);
                }

                doTransform = false;
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

    @Unique
    private Vec3 makeModelPartScale(ModelPart part) {
        return new Vec3(part.xScale, part.yScale, part.zScale);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void setupAnimResetPose(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        PlayerModel<?> playerModel = (PlayerModel<?>) (Object) this;
        playerModel.head.resetPose();
        playerModel.body.resetPose();
        // playerModel.cloak.resetPose();
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
            case "cloak" -> {
                return Optional.of(playerModel.cloak);
            }
            case "right_arm" -> {
                return Optional.of(doTransform ? playerModel.leftArm : playerModel.rightArm);
            }
            case "right_sleeve" -> {
                return Optional.of(doTransform ? playerModel.leftSleeve : playerModel.rightSleeve);
            }
            case "left_arm" -> {
                return Optional.of(doTransform ? playerModel.rightArm : playerModel.leftArm);
            }
            case "left_sleeve" -> {
                return Optional.of(doTransform ? playerModel.rightSleeve : playerModel.leftSleeve);
            }
            case "right_leg" -> {
                return Optional.of(doTransform ? playerModel.leftLeg : playerModel.rightLeg);
            }
            case "right_pants" -> {
                return Optional.of(doTransform ? playerModel.leftPants : playerModel.rightPants);
            }
            case "left_leg" -> {
                return Optional.of(doTransform ? playerModel.rightLeg : playerModel.leftLeg);
            }
            case "left_pants" -> {
                return Optional.of(doTransform ? playerModel.rightPants : playerModel.leftPants);
            }
        }
        return Optional.empty();
    }
}
