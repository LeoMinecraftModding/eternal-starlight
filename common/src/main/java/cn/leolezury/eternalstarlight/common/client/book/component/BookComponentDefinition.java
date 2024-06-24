package cn.leolezury.eternalstarlight.common.client.book.component;

import net.minecraft.resources.ResourceLocation;

public record BookComponentDefinition(BookComponent component, ResourceLocation id, int xOffsetL, int yOffsetL, int xOffsetR, int yOffsetR, boolean enabled) {
	public BookComponentDefinition(BookComponent component, ResourceLocation id, int xOffsetL, int yOffsetL, int xOffsetR, int yOffsetR) {
		this(component, id, xOffsetL, yOffsetL, xOffsetR, yOffsetR, true);
	}
}
