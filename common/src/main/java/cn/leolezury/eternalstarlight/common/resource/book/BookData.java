package cn.leolezury.eternalstarlight.common.resource.book;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record BookData (
        int bookWidth, int bookHeight,
        int textOffsetX, int textOffsetY,
        ResourceLocation backgroundLocation, ResourceLocation titleBackgroundLocation,
        String title,
        List<ResourceLocation> chapters
) {
    public BookData {
        chapters = new ArrayList<>();
    }

    public static class Builder {
        private int bookWidth, bookHeight, textOffsetX, textOffsetY;
        private ResourceLocation backgroundLocation, titleBackgroundLocation;
        private String title;
        private final List<ResourceLocation> chapters;

        public Builder() {
            chapters = new ArrayList<>();
        }

        public Builder withBackground(ResourceLocation location) {
            this.backgroundLocation = location;
            return this;
        }

        public Builder withTitleBackground(ResourceLocation location) {
            this.titleBackgroundLocation = location;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withChapter(ResourceLocation chapter) {
            this.chapters.add(chapter);
            return this;
        }

        public Builder withChapters(List<ResourceLocation> chapters) {
            this.chapters.addAll(chapters);
            return this;
        }

        public Builder withWidth(int width) {
            this.bookWidth = width;
            return this;
        }

        public Builder withHeight(int height) {
            this.bookHeight = height;
            return this;
        }

        public Builder withTextOffsetX(int offsetX) {
            this.textOffsetX = offsetX;
            return this;
        }

        public Builder withTextOffsetY(int offsetY) {
            this.textOffsetY = offsetY;
            return this;
        }

        public BookData build() {
            return new BookData(bookWidth, bookHeight, textOffsetX, textOffsetY, backgroundLocation, titleBackgroundLocation, title, chapters);
        }
    }
}
