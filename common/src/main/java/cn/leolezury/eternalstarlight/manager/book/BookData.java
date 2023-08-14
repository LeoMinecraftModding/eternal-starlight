package cn.leolezury.eternalstarlight.manager.book;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    public int bookWidth;
    public int bookHeight;
    public int textOffsetX;
    public int textOffsetY;
    private ResourceLocation backgroundLocation;
    public ResourceLocation getBackgroundLocation() {
        return backgroundLocation;
    }
    public void setBackgroundLocation(ResourceLocation backgroundLocation) {
        this.backgroundLocation = backgroundLocation;
    }
    private ResourceLocation titleBackgroundLocation;
    public ResourceLocation getTitleBackgroundLocation() {
        return titleBackgroundLocation;
    }
    public void setTitleBackgroundLocation(ResourceLocation titleBackgroundLocation) {
        this.titleBackgroundLocation = titleBackgroundLocation;
    }
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    List<ResourceLocation> chapters = new ArrayList<>();
    public List<ResourceLocation> getChapters() {
        return chapters;
    }
    public void addChapter(ResourceLocation chapter) {
        this.chapters.add(chapter);
    }
    public void addChapters(List<ResourceLocation> chapters) {
        this.chapters.addAll(chapters);
    }
    public void setChapters(List<ResourceLocation> chapters) {
        this.chapters = chapters;
    }
    
    public BookData(ResourceLocation backgroundLocation, ResourceLocation imageLocation, String title, List<ResourceLocation> chapters, int bookWidth, int bookHeight, int textOffsetX, int textOffsetY) {
        setBackgroundLocation(backgroundLocation);
        setTitleBackgroundLocation(imageLocation);
        setTitle(title);
        addChapters(chapters);
        this.bookWidth = bookWidth;
        this.bookHeight = bookHeight;
        this.textOffsetX = textOffsetX;
        this.textOffsetY = textOffsetY;
    }

    public static class Builder {
        BookData bookData;

        public Builder() {
            ResourceLocation empty = new ResourceLocation("");
            this.bookData = new BookData(empty, empty, "", List.of(), 0, 0, 0, 0);
        }

        public Builder withBackground(ResourceLocation location) {
            bookData.setBackgroundLocation(location);
            return this;
        }

        public Builder withTitleBackground(ResourceLocation location) {
            bookData.setTitleBackgroundLocation(location);
            return this;
        }

        public Builder withTitle(String title) {
            bookData.setTitle(title);
            return this;
        }

        public Builder withChapter(ResourceLocation chapter) {
            bookData.addChapter(chapter);
            return this;
        }

        public Builder withChapters(List<ResourceLocation> chapters) {
            bookData.addChapters(chapters);
            return this;
        }

        public Builder withWidth(int width) {
            bookData.bookWidth = width;
            return this;
        }

        public Builder withHeight(int height) {
            bookData.bookHeight = height;
            return this;
        }

        public Builder withTextOffsetX(int offsetX) {
            bookData.textOffsetX = offsetX;
            return this;
        }

        public Builder withTextOffsetY(int offsetY) {
            bookData.textOffsetY = offsetY;
            return this;
        }

        public BookData build() {
            return bookData;
        }
    }
}
