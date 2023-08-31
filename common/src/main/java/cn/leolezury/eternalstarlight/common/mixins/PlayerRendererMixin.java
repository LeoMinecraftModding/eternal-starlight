package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimationState;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Inject(method = "setModelProperties", at = @At("RETURN"))
    private void setModelProperties(AbstractClientPlayer player, CallbackInfo ci) {
        if (ClientSetupHandlers.renderingFirstPersonPlayer) {
            PlayerModel<AbstractClientPlayer> playerModel = ((PlayerRenderer) (Object) this).getModel();
            ItemStack useItem = player.getItemInHand(player.getUsedItemHand());
            int tick = player.getTicksUsingItem();
            boolean animated = false;
            boolean renderLeftArm = false;
            boolean renderRightArm = false;

            for (Supplier<Item> itemSupplier : ClientSetupHandlers.playerAnimatingItemMap.keySet()) {
                if (useItem.is(itemSupplier.get())) {
                    animated = true;
                    PlayerAnimationState state = ClientSetupHandlers.playerAnimatingItemMap.get(itemSupplier).get(useItem, tick);
                    renderLeftArm = state.renderLeftArm();
                    renderRightArm = state.renderRightArm();
                    break;
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
