package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookAccess;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class TextBookComponent extends BookComponent {
    private final Component text;
    private final List<FormattedCharSequence> cachedComponents = new ArrayList<>();

    public TextBookComponent(Component text, int width, int height) {
        super(width, height);
        this.text = text;
    }

    @Override
    public int getPageCount(Font font) {
        if (cachedComponents.isEmpty()) {
            cachedComponents.addAll(font.split(text, width));
        }
        int linesPerPage = height / font.lineHeight;
        return cachedComponents.size() % linesPerPage == 0 ? cachedComponents.size() / linesPerPage : cachedComponents.size() / linesPerPage + 1;
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
    public void onClick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

    }
}
