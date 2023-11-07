package cn.leolezury.eternalstarlight.common.client.gui.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record BookRenderData (
        int bookWidth, int bookHeight,
        int textOffsetX, int textOffsetY,
        ResourceLocation backgroundLocation, ResourceLocation titleBackgroundLocation,
        Component title,
        List<ChapterRenderData> chapters
) {
    public record ChapterRenderData (
            ResourceLocation displayEntity, float entityDisplayScale, int entityOffset,
            Component title, Component content,
            ResourceLocation imageLocation
    ) {}
}
