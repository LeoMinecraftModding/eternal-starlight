package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Inject(method = "setModelProperties", at = @At("RETURN"))
    private void es_setModelProperties(AbstractClientPlayer player, CallbackInfo ci) {
        if (PlayerAnimator.renderingFirstPersonPlayer) {
            PlayerModel<AbstractClientPlayer> playerModel = ((PlayerRenderer) (Object) this).getModel();
            ItemStack useItem = player.getItemInHand(player.getUsedItemHand());
            int tick = player.getTicksUsingItem();
            boolean animated = false;
            boolean renderLeftArm = false;
            boolean renderRightArm = false;

            for (Map.Entry<PlayerAnimator.AnimationTrigger, PlayerAnimator.AnimationStateFunction> entry : PlayerAnimator.ANIMATIONS.entrySet()) {
                if (entry.getKey().shouldPlay(player)) {
                    animated = true;
                    PlayerAnimator.PlayerAnimationState state = entry.getValue().get(player);
                    renderLeftArm = renderLeftArm || state.renderLeftArm();
                    renderRightArm = renderRightArm || state.renderRightArm();
                }
            }

            if (animated) {
                playerModel.setAllVisible(false);
                if (renderLeftArm) {
                    playerModel.leftArm.visible = true;
                    playerModel.leftSleeve.visible = true;
                }
                if (renderRightArm) {
                    playerModel.rightArm.visible = true;
                    playerModel.rightSleeve.visible = true;
                }
            }
        }
    }
}
