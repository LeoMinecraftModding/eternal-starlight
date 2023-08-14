package cn.leolezury.eternalstarlight.client.gui.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BookRenderData {
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
    private Component title;
    public Component getTitle() {
        return title;
    }
    public void setTitle(Component title) {
        this.title = title;
    }
    List<ChapterRenderData> chapters = new ArrayList<>();
    public List<ChapterRenderData> getChapters() {
        return chapters;
    }
    public void addChapters(List<ChapterRenderData> chapters) {
        this.chapters.addAll(chapters);
    }

    public BookRenderData(ResourceLocation backgroundLocation, ResourceLocation titleBackgroundLocation, Component title, List<ChapterRenderData> chapters, int bookWidth, int bookHeight, int textOffsetX, int textOffsetY) {
        setBackgroundLocation(backgroundLocation);
        setTitleBackgroundLocation(titleBackgroundLocation);
        setTitle(title);
        addChapters(chapters);
        this.bookWidth = bookWidth;
        this.bookHeight = bookHeight;
        this.textOffsetX = textOffsetX;
        this.textOffsetY = textOffsetY;
    }

    public static class ChapterRenderData {
        public float entityDisplayScale;
        private ResourceLocation imageLocation;
        public ResourceLocation getImageLocation() {
            return imageLocation;
        }
        public void setImageLocation(ResourceLocation imageLocation) {
            this.imageLocation = imageLocation;
        }
        private ResourceLocation displayEntity;
        public ResourceLocation getDisplayEntity() {
            return displayEntity;
        }
        public void setDisplayEntity(ResourceLocation displayEntity) {
            this.displayEntity = displayEntity;
        }
        private Component title;
        public Component getTitle() {
            return title;
        }
        public void setTitle(Component title) {
            this.title = title;
        }
        private Component content;
        public Component getContent() {
            return content;
        }
        public void setContent(Component content) {
            this.content = content;
        }

        public ChapterRenderData(ResourceLocation displayEntity, ResourceLocation imageLocation, Component title, Component content, float entityDisplayScale) {
            setDisplayEntity(displayEntity);
            setImageLocation(imageLocation);
            setTitle(title);
            setContent(content);
            this.entityDisplayScale = entityDisplayScale;
        }
    }
}
