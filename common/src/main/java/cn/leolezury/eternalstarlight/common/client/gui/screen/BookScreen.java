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
        book.removeDisabled();
        this.book = book;
        this.currentPage = -2;
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
    public boolean mouseClicked(double x, double y, int i) {
        if (bookOpened()) {
            BookComponentDefinition left = getCurrentComponent(true);
            BookComponentDefinition right = getCurrentComponent(false);
            if (left != null) {
                left.component().onClick(createBookAccess(true), font,
                        getBaseX() + left.xOffsetL(),
                        getBaseY() + left.yOffsetL(),
                        (int) x, (int) y);
            }
            if (right != null) {
                right.component().onClick(createBookAccess(false), font,
                        getBaseX() + book.width() / 2 + right.xOffsetR(),
                        getBaseY() + right.yOffsetR(),
                        (int) x, (int) y);
            }
        } else if (currentPage < 0) {
            if (x >= getBaseX() + book.width() / 2f && x <= getBaseX() + book.width() && y >= getBaseY() && y <= getBaseY() + book.height()) {
                setPage(0);
            }
        } else if (x >= getBaseX() && x <= getBaseX() + book.width() / 2f && y >= getBaseY() && y <= getBaseY() + book.height()) {
            setPage(pageSize - 1);
        }
        return super.mouseClicked(x, y, i);
    }

    @Override
    public void tick() {
        if (bookOpened()) {
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
            if (left == null || right == null || left.component() != right.component()) {
                if (left != null) {
                    left.component().singleTick(createBookAccess(true), font,
                            getBaseX() + left.xOffsetL(),
                            getBaseY() + left.yOffsetL(),
                            mouseX, mouseY);
                }
                if (right != null) {
                    right.component().singleTick(createBookAccess(false), font,
                            getBaseX() + book.width() / 2 + right.xOffsetR(),
                            getBaseY() + right.yOffsetR(),
                            mouseX, mouseY);
                }
            } else {
                left.component().singleTick(createBookAccess(true), font,
                        getBaseX() + left.xOffsetL(),
                        getBaseY() + left.yOffsetL(),
                        mouseX, mouseY);
            }
        }
        updateVisibility();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderBackground(guiGraphics, i, j, f);
        if (bookOpened()) {
            guiGraphics.blit(book.background(), getBaseX(), getBaseY(), 0, 0, book.width(), book.height(), book.width(), book.height());
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
        } else if (currentPage < 0) {
            guiGraphics.blit(book.cover(), getBaseX() + book.width() / 2, getBaseY(), 0, 0, book.width() / 2, book.height(), book.width() / 2, book.height());
        } else {
            guiGraphics.blit(book.backCover(), getBaseX(), getBaseY(), 0, 0, book.width() / 2, book.height(), book.width() / 2, book.height());
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void flipLeft() {
        setPage(currentPage - 2);
        updateVisibility();
    }

    public void flipRight() {
        setPage(currentPage + 2);
        updateVisibility();
    }

    private int getBaseX() {
        return (width - book.width()) / 2;
    }

    private int getBaseY() {
        return (height - book.height()) / 2;
    }

    private boolean bookOpened() {
        return currentPage >= 0 && currentPage < pageSize;
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
            public boolean isLeftPage() {
                return left;
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
        boolean visible = bookOpened();
        leftButton.visible = visible;
        rightButton.visible = visible;
    }

    private List<BookComponentDefinition> getComponents() {
        return book.components();
    }
}
