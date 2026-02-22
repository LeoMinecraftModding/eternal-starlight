package cn.leolezury.eternalstarlight.common.client.book;

import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;

public interface BookContext {
	int getMouseX();

	int getMouseY();

	Font getFont();

	BookDefinition getBookDefinition();

	int getContentX();

	int getContentY();

	int getTickCount();

	boolean isComponentEnabled(ResourceLocation id);

	void jumpToComponent(ResourceLocation id);
}
