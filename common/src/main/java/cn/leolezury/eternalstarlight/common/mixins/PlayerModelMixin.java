package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.client.model.animation.ESKeyframeAnimations;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimationState;
import cn.leolezury.eternalstarlight.common.client.model.animation.model.AnimatedModel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void setupAnim(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (livingEntity.isUsingItem()) {
            AnimationDefinition definition = null;
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
                    break;
                }
            }
            if (animated) {
                ESKeyframeAnimations.animate(this, definition, (long) (tick * 1000L / 20f), 1.0F, ANIMATION_VECTOR_CACHE);
            }
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void setupAnimResetPose(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
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
            case "cloak" -> {
                return Optional.of(playerModel.cloak);
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
