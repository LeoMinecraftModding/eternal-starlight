package cn.leolezury.eternalstarlight.manager.book.chapter;

import net.minecraft.resources.ResourceLocation;

public class ChapterData {
    public float entityDisplayScale;
    private ResourceLocation trigger;
    public ResourceLocation getTrigger() {
        return trigger;
    }
    public void setTrigger(ResourceLocation trigger) {
        this.trigger = trigger;
    }

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
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public ChapterData(ResourceLocation trigger, ResourceLocation imageLocation, ResourceLocation displayEntity, String title, String content, int entityDisplayScale) {
        setTrigger(trigger);
        setImageLocation(imageLocation);
        setDisplayEntity(displayEntity);
        setTitle(title);
        setContent(content);
        this.entityDisplayScale = entityDisplayScale;
    }

    public static class Builder {
        ChapterData chapterData;

        public Builder() {
            ResourceLocation empty = new ResourceLocation("");
            this.chapterData = new ChapterData(empty, empty, empty, "", "", 0);
        }

        public ChapterData.Builder withTrigger(ResourceLocation location) {
            chapterData.setTrigger(location);
            return this;
        }

        public ChapterData.Builder withImage(ResourceLocation location) {
            chapterData.setImageLocation(location);
            return this;
        }

        public ChapterData.Builder withEntityDisplay(ResourceLocation location) {
            chapterData.setDisplayEntity(location);
            return this;
        }

        public ChapterData.Builder withDisplayScale(float scale) {
            chapterData.entityDisplayScale = scale;
            return this;
        }

        public ChapterData.Builder withTitle(String title) {
            chapterData.setTitle(title);
            return this;
        }

        public ChapterData.Builder withContent(String content) {
            chapterData.setContent(content);
            return this;
        }

        public ChapterData build() {
            return chapterData;
        }
    }
}
