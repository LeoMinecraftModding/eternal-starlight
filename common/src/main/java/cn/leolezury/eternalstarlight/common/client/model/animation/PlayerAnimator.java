package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class PlayerAnimator {
    public static final HashMap<AnimationTrigger, AnimationStateFunction> ANIMATIONS = new HashMap<>();

    public static boolean renderingFirstPersonPlayer = false;

    public static void register(AnimationTrigger trigger, AnimationStateFunction function) {
        ANIMATIONS.put(trigger, function);
    }

    public static class UseItemAnimationTrigger implements AnimationTrigger {
        private final Supplier<? extends Item> itemSupplier;

        public UseItemAnimationTrigger(Supplier<? extends Item> itemSupplier) {
            this.itemSupplier = itemSupplier;
        }

        @Override
        public boolean shouldPlay(AbstractClientPlayer player) {
            return player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).is(itemSupplier.get());
        }

        @Override
        public float animateTicks(AbstractClientPlayer player, float ageInTicks) {
            return player.getTicksUsingItem() + (ageInTicks - (int) ageInTicks);
        }
    }
    public interface AnimationTrigger {
        boolean shouldPlay(AbstractClientPlayer player);
        float animateTicks(AbstractClientPlayer player, float ageInTicks);
    }

    public interface AnimationStateFunction {
        PlayerAnimationState get(AbstractClientPlayer player);
    }

    public static class UseItemHandAnimationTransformer implements AnimationTransformer {
        private Vec3 hatOriginalPos;
        private Vec3 hatOriginalRot;
        private Vec3 headOriginalPos;
        private Vec3 headOriginalRot;
        private Vec3 bodyOriginalPos;
        private Vec3 bodyOriginalRot;
        private Vec3 jacketOriginalPos;
        private Vec3 jacketOriginalRot;
        private Vec3 rightArmOriginalPos;
        private Vec3 rightArmOriginalRot;
        private Vec3 rightSleeveOriginalPos;
        private Vec3 rightSleeveOriginalRot;
        private Vec3 leftArmOriginalPos;
        private Vec3 leftArmOriginalRot;
        private Vec3 leftSleeveOriginalPos;
        private Vec3 leftSleeveOriginalRot;
        private Vec3 rightLegOriginalPos;
        private Vec3 rightLegOriginalRot;
        private Vec3 rightPantsOriginalPos;
        private Vec3 rightPantsOriginalRot;
        private Vec3 leftLegOriginalPos;
        private Vec3 leftLegOriginalRot;
        private Vec3 leftPantsOriginalPos;
        private Vec3 leftPantsOriginalRot;

        @Override
        public boolean shouldApply(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
            return player.getUsedItemHand() == InteractionHand.OFF_HAND;
        }

        @Override
        public void preAnimate(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
            hatOriginalPos = makeModelPartPos(model.hat);
            hatOriginalRot = makeModelPartRot(model.hat);

            headOriginalPos = makeModelPartPos(model.head);
            headOriginalRot = makeModelPartRot(model.head);

            bodyOriginalPos = makeModelPartPos(model.body);
            bodyOriginalRot = makeModelPartRot(model.body);

            jacketOriginalPos = makeModelPartPos(model.jacket);
            jacketOriginalRot = makeModelPartRot(model.jacket);

            rightArmOriginalPos = makeModelPartPos(model.rightArm);
            rightArmOriginalRot = makeModelPartRot(model.rightArm);

            rightSleeveOriginalPos = makeModelPartPos(model.rightSleeve);
            rightSleeveOriginalRot = makeModelPartRot(model.rightSleeve);

            leftArmOriginalPos = makeModelPartPos(model.leftArm);
            leftArmOriginalRot = makeModelPartRot(model.leftArm);

            leftSleeveOriginalPos = makeModelPartPos(model.leftSleeve);
            leftSleeveOriginalRot = makeModelPartRot(model.leftSleeve);

            rightLegOriginalPos = makeModelPartPos(model.rightLeg);
            rightLegOriginalRot = makeModelPartRot(model.rightLeg);

            rightPantsOriginalPos = makeModelPartPos(model.rightPants);
            rightPantsOriginalRot = makeModelPartRot(model.rightPants);

            leftLegOriginalPos = makeModelPartPos(model.leftLeg);
            leftLegOriginalRot = makeModelPartRot(model.leftLeg);

            leftPantsOriginalPos = makeModelPartPos(model.leftPants);
            leftPantsOriginalRot = makeModelPartRot(model.leftPants);
        }

        @Override
        public void postAnimate(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
            transformModelPart(model.hat, hatOriginalPos, hatOriginalRot);
            transformModelPart(model.head, headOriginalPos, headOriginalRot);

            transformModelPart(model.body, bodyOriginalPos, bodyOriginalRot);
            transformModelPart(model.jacket, jacketOriginalPos, jacketOriginalRot);

            transformModelPartLeftAndRight(model.leftArm, model.rightArm, leftArmOriginalPos, leftArmOriginalRot, rightArmOriginalPos, rightArmOriginalRot);
            transformModelPartLeftAndRight(model.leftSleeve, model.rightSleeve, leftSleeveOriginalPos, leftSleeveOriginalRot, rightSleeveOriginalPos, rightSleeveOriginalRot);

            transformModelPartLeftAndRight(model.leftLeg, model.rightLeg, leftLegOriginalPos, leftLegOriginalRot, rightLegOriginalPos, rightLegOriginalRot);
            transformModelPartLeftAndRight(model.leftPants, model.rightPants, leftPantsOriginalPos, leftPantsOriginalRot, rightPantsOriginalPos, rightPantsOriginalRot);
        }

        @Override
        public Optional<ModelPart> modifyModelPart(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, String original) {
            switch (original) {
                case "hat" -> {
                    return Optional.of(model.hat);
                }
                case "head" -> {
                    return Optional.of(model.head);
                }
                case "body" -> {
                    return Optional.of(model.body);
                }
                case "jacket" -> {
                    return Optional.of(model.jacket);
                }
                case "right_arm" -> {
                    return Optional.of(model.leftArm);
                }
                case "right_sleeve" -> {
                    return Optional.of(model.leftSleeve);
                }
                case "left_arm" -> {
                    return Optional.of(model.rightArm);
                }
                case "left_sleeve" -> {
                    return Optional.of(model.rightSleeve);
                }
                case "right_leg" -> {
                    return Optional.of(model.leftLeg);
                }
                case "right_pants" -> {
                    return Optional.of(model.leftPants);
                }
                case "left_leg" -> {
                    return Optional.of(model.rightLeg);
                }
                case "left_pants" -> {
                    return Optional.of(model.rightPants);
                }
            }
            return Optional.empty();
        }

        @Override
        public float modifyTicks(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original) {
            return original;
        }

        @Override
        public float modifyScale(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original) {
            return original;
        }

        private void transformModelPart(ModelPart part, Vec3 originalPos, Vec3 originalRot) {
            part.x = (float) (part.x - 2 * (part.x - originalPos.x));
            part.yRot = (float) (part.yRot - 2 * (part.yRot - originalRot.y));
            part.zRot = (float) (part.zRot - 2 * (part.zRot - originalRot.z));
        }

        private void transformModelPartLeftAndRight(ModelPart left, ModelPart right, Vec3 leftOriginalPos, Vec3 leftOriginalRot, Vec3 rightOriginalPos, Vec3 rightOriginalRot) {
            transformModelPart(left, leftOriginalPos, leftOriginalRot);
            transformModelPart(right, rightOriginalPos, rightOriginalRot);
        }

        private Vec3 makeModelPartPos(ModelPart part) {
            return new Vec3(part.x, part.y, part.z);
        }

        private Vec3 makeModelPartRot(ModelPart part) {
            return new Vec3(part.xRot, part.yRot, part.zRot);
        }
    }

    public interface AnimationTransformer {
        boolean shouldApply(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model);
        void preAnimate(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model);
        void postAnimate(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model);
        Optional<ModelPart> modifyModelPart(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, String original);
        float modifyTicks(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original);
        float modifyScale(PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original);
    }

    public record PlayerAnimationState (AnimationDefinition definition, List<AnimationTransformer> transformers, boolean renderLeftArm, boolean renderRightArm, boolean resetLeftArmBeforeAnimation, boolean resetRightArmBeforeAnimation) {
    }
}
