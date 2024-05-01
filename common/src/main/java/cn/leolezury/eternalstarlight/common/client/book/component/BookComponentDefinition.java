package cn.leolezury.eternalstarlight.common.client.book.component;

import net.minecraft.resources.ResourceLocation;

public record BookComponentDefinition(BookComponent component, ResourceLocation id, int xOffsetL, int yOffsetL, int xOffsetR, int yOffsetR) {

}
