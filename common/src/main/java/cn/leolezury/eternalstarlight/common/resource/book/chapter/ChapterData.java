package cn.leolezury.eternalstarlight.common.resource.book.chapter;

import net.minecraft.resources.ResourceLocation;

public record ChapterData (
        ResourceLocation displayEntity, float entityDisplayScale, int entityOffset,
        ResourceLocation trigger,
        String title, String content,
        ResourceLocation imageLocation
) {
    public static class Builder {
        private ResourceLocation displayEntity;
        private float entityDisplayScale;
        private int entityOffset;
        private ResourceLocation trigger;
        private String title;
        private String content;
        private ResourceLocation imageLocation;

        public Builder() {}

        public ChapterData.Builder withTrigger(ResourceLocation location) {
            this.trigger = location;
            return this;
        }

        public ChapterData.Builder withImage(ResourceLocation location) {
            this.imageLocation = location;
            return this;
        }

        public ChapterData.Builder withEntityDisplay(ResourceLocation location) {
            this.displayEntity = location;
            return this;
        }

        public ChapterData.Builder withDisplayScale(float scale) {
            this.entityDisplayScale = scale;
            return this;
        }

        public ChapterData.Builder withEntityOffset(int offset) {
            this.entityOffset = offset;
            return this;
        }

        public ChapterData.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ChapterData.Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public ChapterData build() {
            return new ChapterData(displayEntity, entityDisplayScale, entityOffset, trigger, title, content, imageLocation);
        }
    }
}
