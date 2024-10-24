package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TextBookComponent extends BookComponent {
	private final Component text;
	private final boolean mustEndAtEvenPage;
	private final List<FormattedCharSequence> cachedComponents = new ArrayList<>();

	public TextBookComponent(Component text, int width, int height) {
		this(text, true, width, height);
	}

	public TextBookComponent(Component text, boolean mustEndAtEvenPage, int width, int height) {
		super(width, height);
		this.text = text;
		this.mustEndAtEvenPage = mustEndAtEvenPage;
	}

	@Override
	public int getPageCount(int pagesBefore, Font font) {
		if (cachedComponents.isEmpty()) {
			cachedComponents.addAll(font.split(text, width));
		}
		int linesPerPage = height / font.lineHeight;
		int pageCount = cachedComponents.size() % linesPerPage == 0 ? cachedComponents.size() / linesPerPage : cachedComponents.size() / linesPerPage + 1;
		return mustEndAtEvenPage ? (pageCount % 2 == 0 ? (pageCount + pagesBefore % 2) : (pageCount + (1 - pagesBefore % 2))) : pageCount;
	}

	@Override
	public void render(BookAccess access, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY) {
		int linesPerPage = height / font.lineHeight;
		for (int i = access.getRelativePage() * linesPerPage; i < Math.min((access.getRelativePage() + 1) * linesPerPage, cachedComponents.size()); i++) {
			int textY = (i - access.getRelativePage() * linesPerPage) * font.lineHeight;
			graphics.drawString(font, cachedComponents.get(i), x, y + textY, 0, false);
		}
	}

	@Override
	public void tick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

	}

	@Override
	public void singleTick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

	}

	@Override
	public void onClick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

	}
}
