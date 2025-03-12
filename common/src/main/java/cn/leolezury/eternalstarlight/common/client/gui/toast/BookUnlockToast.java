package cn.leolezury.eternalstarlight.common.client.gui.toast;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class BookUnlockToast implements Toast {
	private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("toast/advancement");
	private final Component chapterName;

	public BookUnlockToast(Component chapterName) {
		this.chapterName = chapterName;
	}

	@Override
	public Toast.Visibility render(GuiGraphics graphics, ToastComponent component, long timeSinceLastVisible) {
		graphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());

		List<FormattedCharSequence> list = component.getMinecraft().font.split(chapterName, 125);
		if (list.size() == 1) {
			graphics.drawString(component.getMinecraft().font, Component.translatable("book." + EternalStarlight.ID + ".unlock"), 30, 7, 16776960 | -16777216, false);
			graphics.drawString(component.getMinecraft().font, list.getFirst(), 30, 18, -1, false);
		} else {
			if (timeSinceLastVisible < 1500L) {
				int color = Mth.floor(Mth.clamp((1500L - timeSinceLastVisible) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
				graphics.drawString(component.getMinecraft().font, Component.translatable("book." + EternalStarlight.ID + ".unlock"), 30, 11, 16776960 | color, false);
			} else {
				int color = Mth.floor(Mth.clamp((timeSinceLastVisible - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
				Objects.requireNonNull(component.getMinecraft().font);
				int yPos = this.height() / 2 - list.size() * 9 / 2;

				for (FormattedCharSequence formattedCharSequence : list) {
					graphics.drawString(component.getMinecraft().font, formattedCharSequence, 30, yPos, 16777215 | color, false);
					Objects.requireNonNull(component.getMinecraft().font);
					yPos += 9;
				}
			}
		}

		graphics.renderFakeItem(ESItems.BOOK.get().getDefaultInstance(), 8, 8);
		return timeSinceLastVisible >= 5000.0F * component.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
	}
}