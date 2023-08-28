package cn.leolezury.eternalstarlight.common.client.gui.screens;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
public class ESBookScreen extends Screen {
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

    public ESBookScreen(BookRenderData bookData) {
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
            int textWidth = bookData.bookWidth / 2 - bookData.textOffsetX * 2;
            int linesPerPage = (bookData.bookHeight - 2 * bookData.textOffsetY) / this.font.lineHeight;
            MutableComponent index = Component.empty();
            AtomicInteger order = new AtomicInteger(1);
            chapters.forEach((data) -> {
                index.append(Component.translatable("book." + EternalStarlight.MOD_ID + ".chapter")).append(" " + order.get() + ": ").append(data.getTitle()).append("\n");
                order.getAndIncrement();
            });
            List<FormattedCharSequence> indexLines = this.font.split(index, textWidth);
            cachedComponents.add(indexLines);
            int indexPages = indexLines.size() % linesPerPage == 0 ? indexLines.size() / linesPerPage : indexLines.size() / linesPerPage + 1;
            pagesPerSection.add(indexPages + 1);
            totalPages += indexPages;
            totalPages += 1; // because the book has a title and an image, takes one page
            for (BookRenderData.ChapterRenderData chapter : chapters) {
                // split content to lines
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
        int rightPageX = x + bookData.bookWidth / 2 + bookData.textOffsetX;
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
        BookRenderData.ChapterRenderData chapter = new BookRenderData.ChapterRenderData(new ResourceLocation(""), new ResourceLocation(""), Component.empty(), Component.empty(), 0, 0);
        if (currentChapter >= 0) {
            chapter = chapters.get(currentChapter);
        }
        if (relativePage == 0) {
            // then it's a title page
            // draw background
            graphics.blit(currentChapter < 0 ? bookData.getTitleBackgroundLocation() : chapter.getImageLocation(), x - bookData.textOffsetX, y - bookData.textOffsetY, 0, 0, 0, bookData.bookWidth / 2, bookData.bookHeight, bookData.bookWidth / 2, bookData.bookHeight);
            // draw title
            Component title = currentChapter < 0 ? bookData.getTitle() : chapter.getTitle();
            graphics.drawString(font, title, x + (bookData.bookWidth / 2 - 2 * bookData.textOffsetX - font.width(title)) / 2, y + (bookData.bookHeight - 2 * bookData.textOffsetY) / 4 * 3, 0, false);
            // draw entity display
            EntityType<?> entityType;
            if ((entityType = ESPlatform.INSTANCE.getEntityType(chapter.getDisplayEntity())) != null) {
                Entity entity = null;
                if (minecraft != null && minecraft.level != null) {
                    entity = entityType.create(minecraft.level);
                }
                if (entity instanceof LivingEntity livingEntity) {
                    renderEntityInInventoryFollowsAngle(graphics, x + (bookData.bookWidth / 2 - 2 * bookData.textOffsetX) / 2, y + chapter.entityOffset, (int) (17 * chapter.entityDisplayScale), 0, 0, livingEntity);
                }
            }
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

    // Copied from Forge-Tweaked InventoryScreen
    public static void renderEntityInInventoryFollowsAngle(GuiGraphics p_282802_, int p_275688_, int p_275245_, int p_275535_, float angleXComponent, float angleYComponent, LivingEntity p_275689_) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float)Math.PI / 180F));
        quaternionf.mul(quaternionf1);
        float f2 = p_275689_.yBodyRot;
        float f3 = p_275689_.getYRot();
        float f4 = p_275689_.getXRot();
        float f5 = p_275689_.yHeadRotO;
        float f6 = p_275689_.yHeadRot;
        p_275689_.yBodyRot = 180.0F + f * 20.0F;
        p_275689_.setYRot(180.0F + f * 40.0F);
        p_275689_.setXRot(-f1 * 20.0F);
        p_275689_.yHeadRot = p_275689_.getYRot();
        p_275689_.yHeadRotO = p_275689_.getYRot();
        renderEntityInInventory(p_282802_, p_275688_, p_275245_, p_275535_, quaternionf, quaternionf1, p_275689_);
        p_275689_.yBodyRot = f2;
        p_275689_.setYRot(f3);
        p_275689_.setXRot(f4);
        p_275689_.yHeadRotO = f5;
        p_275689_.yHeadRot = f6;
    }

    public static void renderEntityInInventory(GuiGraphics p_282665_, int p_283622_, int p_283401_, int p_281360_, Quaternionf p_281880_, @Nullable Quaternionf p_282882_, LivingEntity p_282466_) {
        p_282665_.pose().pushPose();
        p_282665_.pose().translate((double)p_283622_, (double)p_283401_, 50.0D);
        p_282665_.pose().mulPoseMatrix((new Matrix4f()).scaling((float)p_281360_, (float)p_281360_, (float)(-p_281360_)));
        p_282665_.pose().mulPose(p_281880_);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (p_282882_ != null) {
            p_282882_.conjugate();
            entityrenderdispatcher.overrideCameraOrientation(p_282882_);
        }

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(p_282466_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, p_282665_.pose(), p_282665_.bufferSource(), 15728880);
        });
        p_282665_.flush();
        entityrenderdispatcher.setRenderShadow(true);
        p_282665_.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
