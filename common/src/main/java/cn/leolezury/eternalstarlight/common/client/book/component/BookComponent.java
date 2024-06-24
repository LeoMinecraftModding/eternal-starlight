package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public abstract class BookComponent {
	protected final int width;
	protected final int height;

	public BookComponent(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public abstract int getPageCount(Font font);

	public abstract void render(BookAccess access, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY);

	public abstract void tick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY);

	public abstract void singleTick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY);

	public abstract void onClick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY);
}
