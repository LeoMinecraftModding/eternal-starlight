package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

    @Shadow @Final private Minecraft minecraft;

    // Copied from vanilla renderArmWithItem, the crossbow part.
    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    private void es_render(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci) {
        if (!abstractClientPlayer.isScoping()) {
            boolean bl = interactionHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidArm = bl ? abstractClientPlayer.getMainArm() : abstractClientPlayer.getMainArm().getOpposite();
            boolean bl2;
            float l;
            float m;
            float n;
            float o;
            if (itemStack.is(ItemInit.CRYSTAL_CROSSBOW.get())) {
                bl2 = CrossbowItem.isCharged(itemStack);
                boolean bl3 = humanoidArm == HumanoidArm.RIGHT;
                int k = bl3 ? 1 : -1;
                if (abstractClientPlayer.isUsingItem() && abstractClientPlayer.getUseItemRemainingTicks() > 0 && abstractClientPlayer.getUsedItemHand() == interactionHand) {
                    applyItemArmTransform(poseStack, humanoidArm, i);
                    poseStack.translate((float)k * -0.4785682F, -0.094387F, 0.05731531F);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
                    poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 65.3F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785F));
                    l = (float)itemStack.getUseDuration() - ((float)minecraft.player.getUseItemRemainingTicks() - f + 1.0F);
                    m = l / (float)CrossbowItem.getChargeDuration(itemStack);
                    if (m > 1.0F) {
                        m = 1.0F;
                    }

                    if (m > 0.1F) {
                        n = Mth.sin((l - 0.1F) * 1.3F);
                        o = m - 0.1F;
                        float p = n * o;
                        poseStack.translate(p * 0.0F, p * 0.004F, p * 0.0F);
                    }

                    poseStack.translate(m * 0.0F, m * 0.0F, m * 0.04F);
                    poseStack.scale(1.0F, 1.0F, 1.0F + m * 0.2F);
                    poseStack.mulPose(Axis.YN.rotationDegrees((float)k * 45.0F));
                } else {
                    l = -0.4F * Mth.sin(Mth.sqrt(h) * 3.1415927F);
                    m = 0.2F * Mth.sin(Mth.sqrt(h) * 6.2831855F);
                    n = -0.2F * Mth.sin(h * 3.1415927F);
                    poseStack.translate((float)k * l, m, n);
                    this.applyItemArmTransform(poseStack, humanoidArm, i);
                    this.applyItemArmAttackTransform(poseStack, humanoidArm, h);
                    if (bl2 && h < 0.001F && bl) {
                        poseStack.translate((float)k * -0.641864F, 0.0F, 0.0F);
                        poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 10.0F));
                    }
                }

                renderItem(abstractClientPlayer, itemStack, bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl3, poseStack, multiBufferSource, j);

                ci.cancel();
            }
        }
    }
}
