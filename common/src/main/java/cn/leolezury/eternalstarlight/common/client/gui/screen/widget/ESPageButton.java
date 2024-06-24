package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import cn.leolezury.eternalstarlight.common.client.book.Book;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.sounds.SoundEvents;

@Environment(EnvType.CLIENT)
public class ESPageButton extends Button {
	private final Book book;
	private final boolean isForward;
	private final boolean playTurnSound;

	public ESPageButton(int x, int y, Book book, boolean isForward, boolean turnSound, Button.OnPress onPress) {
		super(x + (isForward ? -1 : 1) * book.buttonXOffset(), y - book.buttonYOffset(), book.buttonWidth(), book.buttonHeight(), CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
		this.book = book;
		this.isForward = isForward;
		this.playTurnSound = turnSound;
	}

	public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
		guiGraphics.blit(isForward ? book.rightButton() : book.leftButton(), this.getX(), this.getY(), 0, 0, book.buttonWidth(), book.buttonHeight(), book.buttonWidth(), book.buttonHeight());
	}

	public void playDownSound(SoundManager soundManager) {
		if (this.playTurnSound) {
			soundManager.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
		}
	}
}
