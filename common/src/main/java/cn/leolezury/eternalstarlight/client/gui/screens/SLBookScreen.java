package cn.leolezury.eternalstarlight.client.gui.screens;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class SLBookScreen extends Screen {
    private static final int PAGE_BUTTON_WIDTH = 23;
    private static final int PAGE_BUTTON_HEIGHT = 13;
    private PageButton forwardButton;
    private PageButton backButton;
    private BookRenderData bookData;
    private int totalPages;
    private int currentPage;
    private int relativePage;
    private List<List<FormattedCharSequence>> cachedComponents = new ArrayList<>();
    private List<Integer> pagesPerSection = new ArrayList<>();

    public SLBookScreen(BookRenderData bookData) {
        super(GameNarrator.NO_TITLE);
        this.bookData = bookData;
        this.currentPage = 0;
        this.relativePage = 0;
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - bookData.bookWidth) / 2;
        int y = (this.height - bookData.bookHeight) / 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(x + bookData.bookWidth - PAGE_BUTTON_WIDTH, y + bookData.bookHeight - PAGE_BUTTON_HEIGHT, true, (button) -> {
            this.pageForward();
        }, true));
        this.backButton = this.addRenderableWidget(new PageButton(x, y + bookData.bookHeight - PAGE_BUTTON_HEIGHT, false, (button) -> {
            this.pageBack();
        }, true));
    }

    protected void pageForward() {
        if (this.currentPage < this.totalPages - 2) {
            this.currentPage += 2;
        }
        updateButtonVisibility();
    }

    protected void pageBack() {
        if (this.currentPage >= 2) {
            this.currentPage -= 2;
        }
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        this.backButton.visible = currentPage >= 2;
        this.forwardButton.visible = currentPage < totalPages - 2;
    }

    private int getCurrentChapter(int page) {
        List<BookRenderData.ChapterRenderData> chapters = bookData.getChapters();
        int pages = 0;
        for (int i = 0; i < chapters.size() + 1; i++) {
            pages += pagesPerSection.get(i);
            if (page < pages) {
                relativePage = page - pages + pagesPerSection.get(i);
                return i - 1;
            }
        }
        relativePage = 0;
        return chapters.size() - 1;
    }

    @Override
    public void render(GuiGraphics graphics, int i, int i1, float f) {
        renderBackground(graphics);
        List<BookRenderData.ChapterRenderData> chapters = bookData.getChapters();
        if (cachedComponents.isEmpty()) {
            // build the cache
            int linesPerPage = (bookData.bookHeight - 2 * bookData.textOffsetY) / this.font.lineHeight;
            MutableComponent index = Component.empty();
            AtomicInteger order = new AtomicInteger(1);
            chapters.forEach((data) -> {
                index.append(Component.translatable("book." + EternalStarlight.MOD_ID + ".chapter")).append(" " + order.get() + ": ").append(data.getTitle()).append("\n");
                order.getAndIncrement();
            });
            List<FormattedCharSequence> indexLines = this.font.split(index, bookData.bookWidth / 2 - bookData.textOffsetX);
            cachedComponents.add(indexLines);
            int indexPages = indexLines.size() % linesPerPage == 0 ? indexLines.size() / linesPerPage : indexLines.size() / linesPerPage + 1;
            pagesPerSection.add(indexPages + 1);
            totalPages += indexPages;
            totalPages += 1; // because the book has a title and an image, takes one page
            for (BookRenderData.ChapterRenderData chapter : chapters) {
                // split content to lines
                int textWidth = bookData.bookWidth / 2 - bookData.textOffsetX;
                List<FormattedCharSequence> lines = this.font.split(chapter.getContent(), textWidth);
                cachedComponents.add(lines);
                int pages = lines.size() % linesPerPage == 0 ? lines.size() / linesPerPage : lines.size() / linesPerPage + 1;
                pagesPerSection.add(pages + 1);
                totalPages += pages;
                totalPages += 1; // because each chapter has a title and an image, takes one page
            }
            updateButtonVisibility();
        }
        int x = (width - bookData.bookWidth) / 2;
        int y = (height - bookData.bookHeight) / 2;
        int leftPageX = x + bookData.textOffsetX;
        int rightPageX = x + bookData.bookWidth / 2;
        graphics.blit(bookData.getBackgroundLocation(), x, y, 0, 0, 0, bookData.bookWidth, bookData.bookHeight, bookData.bookWidth, bookData.bookHeight);
        renderPage(graphics, 0, leftPageX, y + bookData.textOffsetY);
        if (currentPage + 1 < totalPages) {
            renderPage(graphics, 1, rightPageX, y + bookData.textOffsetY);
        }
        super.render(graphics, i, i1, f);
    }

    private void renderPage(GuiGraphics graphics, int pageOffset, int x, int y) {
        int linesPerPage = (bookData.bookHeight - 2 * bookData.textOffsetY) / this.font.lineHeight;
        int currentChapter = getCurrentChapter(currentPage + pageOffset);
        List<BookRenderData.ChapterRenderData> chapters = bookData.getChapters();
        BookRenderData.ChapterRenderData chapter = new BookRenderData.ChapterRenderData(new ResourceLocation(""), new ResourceLocation(""), Component.empty(), Component.empty(), 0);
        if (currentChapter >= 0) {
            chapter = chapters.get(currentChapter);
        }
        if (relativePage == 0) {
            // then it's a title page
            graphics.blit(currentChapter < 0 ? bookData.getTitleBackgroundLocation() : chapter.getImageLocation(), x, y, 0, 0, 0, bookData.bookWidth / 2 - bookData.textOffsetX, bookData.bookHeight - 2 * bookData.textOffsetY, bookData.bookWidth / 2 - bookData.textOffsetX, bookData.bookHeight - 2 * bookData.textOffsetY);
            Component title = currentChapter < 0 ? bookData.getTitle() : chapter.getTitle();
            graphics.drawString(font, title, x + (bookData.bookWidth / 2 - bookData.textOffsetX - font.width(title)) / 2, y + (bookData.bookHeight - 2 * bookData.textOffsetY) / 4 * 3, 0, false);
        } else {
            // then it's a normal page
            // get the lines for the current chapter
            List<FormattedCharSequence> components = cachedComponents.get(currentChapter + 1);
            for (int i = (relativePage - 1) * linesPerPage; i < Math.min(relativePage * linesPerPage, components.size()); i++) {
                int textY = (i - (relativePage - 1) * linesPerPage) * font.lineHeight;
                graphics.drawString(font, components.get(i), x, y + textY, 0, false);
            }
        }
    }
}
