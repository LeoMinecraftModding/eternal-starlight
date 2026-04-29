package cn.leolezury.eternalstarlight.common.client.gui.toast;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SimpleTextToast implements Toast {
	private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("toast/advancement");
	private final Component title;
	private final Component text;
	private final ItemStack icon;

	public SimpleTextToast(Component title, Component text, ItemStack icon) {
		this.title = title;
		this.text = text;
		this.icon = icon;
	}

	@Override
	public Toast.Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
		guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());
		List<FormattedCharSequence> list = toastComponent.getMinecraft().font.split(text, 125);
		if (list.size() == 1) {
			guiGraphics.drawString(toastComponent.getMinecraft().font, title, 30, 7, -1, false);
			guiGraphics.drawString(toastComponent.getMinecraft().font, list.getFirst(), 30, 18, -1, false);
		} else {
			if (timeSinceLastVisible < 1500L) {
				int color = Mth.floor(Mth.clamp((1500L - timeSinceLastVisible) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
				guiGraphics.drawString(toastComponent.getMinecraft().font, title, 30, 11, 16776960 | color, false);
			} else {
				int color = Mth.floor(Mth.clamp((timeSinceLastVisible - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
				int textY = this.height() / 2 - list.size() * 9 / 2;

				for (FormattedCharSequence formattedcharsequence : list) {
					guiGraphics.drawString(toastComponent.getMinecraft().font, formattedcharsequence, 30, textY, 16777215 | color, false);
					textY += 9;
				}
			}
		}

		guiGraphics.renderFakeItem(icon, 8, 8);
		return timeSinceLastVisible >= 5000.0 * toastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
	}
}