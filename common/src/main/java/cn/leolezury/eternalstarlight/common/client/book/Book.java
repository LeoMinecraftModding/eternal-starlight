package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record Book(List<BookComponentDefinition> components, int width, int height, int buttonWidth, int buttonHeight, ResourceLocation background, ResourceLocation leftButton, ResourceLocation rightButton) {
}
