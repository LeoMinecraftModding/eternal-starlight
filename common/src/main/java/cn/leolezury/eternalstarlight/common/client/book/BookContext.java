package cn.leolezury.eternalstarlight.common.client.book;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public interface BookContext {
	int getMouseX();

	int getMouseY();

	Font getFont();

	BookDefinition getBookDefinition();

	int getContentX();

	int getContentY();

	int getTickCount();

	void jumpToComponent(ResourceLocation id);
}
