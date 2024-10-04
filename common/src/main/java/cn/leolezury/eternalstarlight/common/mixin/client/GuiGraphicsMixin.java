package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
	@Shadow
	public abstract void blit(ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n);

	@Shadow
	@Final
	private PoseStack pose;

	@Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
	private void renderItemDecorations(Font font, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
		if (itemStack.is(ESTags.Items.WIP)) {
			this.pose.pushPose();
			this.pose.translate(0.0F, 0.0F, 200.0F);
			blit(ClientHandlers.WIP, i, j, 0.0F, 0.0F, 16, 16, 16, 16);
			this.pose.popPose();
		}
	}
}
