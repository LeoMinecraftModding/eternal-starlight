package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;

public class BookProgressButton extends Button {
	private final BookDefinition book;
	private final boolean isDown;

	public BookProgressButton(int x, int y, BookDefinition book, boolean isDown, Button.OnPress onPress) {
		super(x, y, book.buttons().upDownButtonWidth(), book.buttons().upDownButtonHeight(), CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
		this.book = book;
		this.isDown = isDown;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(0.0, 0.0, BookScreen.BUTTON_Z_OFFSET);
		guiGraphics.blit(isDown ? book.textures().downButton() : book.textures().upButton(), this.getX(), this.getY(), 0, 0, book.buttons().upDownButtonWidth(), book.buttons().upDownButtonHeight(), book.buttons().upDownButtonWidth(), book.buttons().upDownButtonHeight());
		guiGraphics.pose().popPose();
	}
}
