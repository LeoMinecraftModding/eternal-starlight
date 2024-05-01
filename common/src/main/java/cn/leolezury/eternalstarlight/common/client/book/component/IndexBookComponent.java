package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookAccess;
import cn.leolezury.eternalstarlight.common.util.Color;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class IndexBookComponent extends BookComponent {
    private final Component introText;
    private final List<IndexItem> indexItems = new ArrayList<>();
    private final Int2IntArrayMap oldHoverTicks = new Int2IntArrayMap();
    private final Int2IntArrayMap hoverTicks = new Int2IntArrayMap();
    private final List<FormattedCharSequence> cachedComponents = new ArrayList<>();
    private final List<FormattedCharSequence> hoveredIndexItemComponents = new ArrayList<>();
    private final IntList linesPerIndexItem = new IntArrayList();
    private int indexStartLine;

    public IndexBookComponent(Component introText, int width, int height, IndexItem... indexItems) {
        super(width, height);
        this.introText = introText;
        this.indexItems.addAll(List.of(indexItems));
    }

    @Override
    public int getPageCount(Font font) {
        if (cachedComponents.isEmpty()) {
            cachedComponents.addAll(font.split(introText, width));
            indexStartLine = cachedComponents.size();
            for (IndexItem indexItem : indexItems) {
                cachedComponents.addAll(font.split(indexItem.text().copy().withColor(0x473d4b), width));
                List<FormattedCharSequence> list = font.split(indexItem.text().copy().withColor(0x261c09).withStyle(ChatFormatting.ITALIC), width);
                hoveredIndexItemComponents.addAll(list);
                linesPerIndexItem.add(list.size());
            }
        }
        int linesPerPage = height / font.lineHeight;
        return cachedComponents.size() % linesPerPage == 0 ? cachedComponents.size() / linesPerPage : cachedComponents.size() / linesPerPage + 1;
    }

    private int getIndexItemFromRelativeLine(int line) {
        int lines = 0;
        for (int i = 0; i < indexItems.size(); i++) {
            lines += linesPerIndexItem.getInt(i);
            if (lines > line) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void render(BookAccess access, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY) {
        int linesPerPage = height / font.lineHeight;
        int relativeY = mouseY - y;
        int line = access.getRelativePage() * linesPerPage + Math.min(relativeY / font.lineHeight, linesPerPage - 1);
        int selected = getIndexItemFromRelativeLine(line - indexStartLine);
        int startLine = indexStartLine;
        for (int i = 0; i < selected; i++) {
            startLine += linesPerIndexItem.getInt(i);
        }
        int lineOffset = selected == -1 ? 0 : linesPerIndexItem.getInt(selected);
        int renderY = y + indexStartLine * font.lineHeight;
        for (int i = 0; i < indexItems.size(); i++) {
            int oldHover = oldHoverTicks.getOrDefault(i, 0);
            int hover = hoverTicks.getOrDefault(i, 0);
            float hoverTicks = Math.min(Mth.lerp(Minecraft.getInstance().getFrameTime(), oldHover, hover), 20);
            int color = Color.argbi(0, 1, 1, 1).blend(Color.argb(0x66000000), hoverTicks / 20).argb();
            graphics.fillGradient(x, renderY, x + width, renderY + linesPerIndexItem.getInt(i) * font.lineHeight, color, color);
            renderY += linesPerIndexItem.getInt(i) * font.lineHeight;
        }
        for (int i = access.getRelativePage() * linesPerPage; i < Math.min((access.getRelativePage() + 1) * linesPerPage, cachedComponents.size()); i++) {
            int textY = (i - access.getRelativePage() * linesPerPage) * font.lineHeight;
            FormattedCharSequence toDraw = cachedComponents.get(i);
            if (selected != -1 && i >= startLine && i < startLine + lineOffset && line >= indexStartLine && mouseX > x && mouseX < x + width) {
                toDraw = hoveredIndexItemComponents.get(i - indexStartLine);
            }
            graphics.drawString(font, toDraw, x, y + textY, 0, false);
        }
    }

    @Override
    public void tick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {
        int linesPerPage = height / font.lineHeight;
        int relativeY = mouseY - y;
        int line = access.getRelativePage() * linesPerPage + Math.min(relativeY / font.lineHeight, linesPerPage - 1);
        int selected = getIndexItemFromRelativeLine(line - indexStartLine);
        boolean put = false;
        oldHoverTicks.putAll(hoverTicks);
        if (line >= indexStartLine && selected != -1 && mouseX > x && mouseX < x + width) {
            put = true;
            int oldTicks = hoverTicks.getOrDefault(selected, 0);
            if (oldTicks < 20) {
                hoverTicks.put(selected, oldTicks + 1);
            }
        }
        for (Int2IntMap.Entry entry : hoverTicks.int2IntEntrySet()) {
            if (entry.getIntKey() != selected || !put) {
                hoverTicks.put(entry.getIntKey(), Math.max(entry.getIntValue() - 1, 0));
            }
        }
    }

    @Override
    public void onClick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {
        int linesPerPage = height / font.lineHeight;
        int relativeY = mouseY - y;
        int line = access.getRelativePage() * linesPerPage + Math.min(relativeY / font.lineHeight, linesPerPage - 1);
        int selected = getIndexItemFromRelativeLine(line - indexStartLine);
        if (line >= indexStartLine && selected != -1 && mouseX > x && mouseX < x + width) {
            ResourceLocation jumpTo = indexItems.get(selected).jumpTo();
            for (int i = 0; i < access.getComponents().size(); i++) {
                BookComponentDefinition definition = access.getComponents().get(i);
                if (definition.id().equals(jumpTo)) {
                    int pages = 0;
                    for (int j = 0; j < i; j++) {
                        pages += access.getComponents().get(j).component().getPageCount(font);
                    }
                    access.setPage(pages);
                    break;
                }
            }
        }
    }

    public record IndexItem(Component text, ResourceLocation jumpTo) {

    }
}
