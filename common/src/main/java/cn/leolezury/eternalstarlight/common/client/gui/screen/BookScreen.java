package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.book.Book;
import cn.leolezury.eternalstarlight.common.client.book.BookAccess;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.ESPageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BookScreen extends Screen {
    private final Book book;
    private int pageSize;
    private int currentPage;
    private int mouseX, mouseY;

    private ESPageButton leftButton;
    private ESPageButton rightButton;

    public BookScreen(Book book) {
        super(Component.empty());
        this.book = book;
    }

    @Override
    protected void init() {
        leftButton = addRenderableWidget(new ESPageButton(getBaseX(), getBaseY() + book.height(), book, false, true, button -> flipLeft()));
        rightButton = addRenderableWidget(new ESPageButton(getBaseX() + book.width() - book.buttonWidth(), getBaseY() + book.height(), book, true, true, button -> flipRight()));
        pageSize = 0;
        for (BookComponentDefinition definition : getComponents()) {
            pageSize += definition.component().getPageCount(font);
        }
        updateVisibility();
    }

    @Override
    public void mouseMoved(double d, double e) {
        super.mouseMoved(d, e);
        mouseX = (int) d;
        mouseY = (int) e;
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        BookComponentDefinition left = getCurrentComponent(true);
        BookComponentDefinition right = getCurrentComponent(false);
        if (left != null) {
            left.component().onClick(createBookAccess(true), font,
                    getBaseX() + left.xOffsetL(),
                    getBaseY() + left.yOffsetL(),
                    (int) d, (int) e);
        }
        if (right != null) {
            right.component().onClick(createBookAccess(false), font,
                    getBaseX() + book.width() / 2 + right.xOffsetR(),
                    getBaseY() + right.yOffsetR(),
                    (int) d, (int) e);
        }
        return super.mouseClicked(d, e, i);
    }

    @Override
    public void tick() {
        BookComponentDefinition left = getCurrentComponent(true);
        BookComponentDefinition right = getCurrentComponent(false);
        if (left != null) {
            left.component().tick(createBookAccess(true), font,
                    getBaseX() + left.xOffsetL(),
                    getBaseY() + left.yOffsetL(),
                    mouseX, mouseY);
        }
        if (right != null) {
            right.component().tick(createBookAccess(false), font,
                    getBaseX() + book.width() / 2 + right.xOffsetR(),
                    getBaseY() + right.yOffsetR(),
                    mouseX, mouseY);
        }
        updateVisibility();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.blit(book.background().withSuffix(".png"), getBaseX(), getBaseY(), 0, 0, book.width(), book.height(), book.width(), book.height());
        BookComponentDefinition left = getCurrentComponent(true);
        BookComponentDefinition right = getCurrentComponent(false);
        if (left != null) {
            left.component().render(createBookAccess(true), guiGraphics, font,
                    getBaseX() + left.xOffsetL(),
                    getBaseY() + left.yOffsetL(),
                    mouseX, mouseY);
        }
        if (right != null) {
            right.component().render(createBookAccess(false), guiGraphics, font,
                    getBaseX() + book.width() / 2 + right.xOffsetR(),
                    getBaseY() + right.yOffsetR(),
                    mouseX, mouseY);
        }
    }

    public void flipLeft() {
        setPage(currentPage - 2);
    }

    public void flipRight() {
        setPage(currentPage + 2);
    }

    private int getBaseX() {
        return (width - book.width()) / 2;
    }

    private int getBaseY() {
        return (height - book.height()) / 2;
    }

    private @Nullable BookComponentDefinition getCurrentComponent(boolean left) {
        if (getCurrentComponentIndex(left) == -1) {
            return null;
        }
        return getComponents().get(getCurrentComponentIndex(left));
    }

    private int getCurrentComponentIndex(boolean left) {
        int pages = 0;
        for (int i = 0; i < getComponents().size(); i++) {
            BookComponentDefinition definition = getComponents().get(i);
            pages += definition.component().getPageCount(font);
            if (pages >= currentPage + (left ? 1 : 2)) {
                return i;
            }
        }
        return -1;
    }

    private BookAccess createBookAccess(boolean left) {
        return new BookAccess() {
            @Override
            public int getRelativePage() {
                int pages = 0;
                for (int i = 0; i < getComponents().size(); i++) {
                    BookComponentDefinition definition = getComponents().get(i);
                    pages += definition.component().getPageCount(font);
                    if (pages >= currentPage + (left ? 1 : 2)) {
                        return currentPage + (left ? 0 : 1) - (pages - definition.component().getPageCount(font));
                    }
                }
                return 0;
            }

            @Override
            public void setPage(int page) {
                BookScreen.this.setPage(page);
            }

            @Override
            public List<BookComponentDefinition> getComponents() {
                return BookScreen.this.getComponents();
            }
        };
    }

    private void setPage(int page) {
        currentPage = page - page % 2;
    }

    private void updateVisibility() {
        leftButton.visible = currentPage >= 2;
        rightButton.visible = pageSize - currentPage > 2;
    }

    private List<BookComponentDefinition> getComponents() {
        return book.components();
    }
}
