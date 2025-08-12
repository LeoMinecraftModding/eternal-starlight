package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@Environment(EnvType.CLIENT)
public record BookDefinition(List<ConfiguredBookComponent<?, ?>> components,
							 int width, int height, int frameWidth,
							 int buttonWidth, int buttonHeight, int upButtonOffset, int downButtonOffset, int buttonDistanceFromRight,
							 int scrollbarWidth, int scrollbarHeight, int scrollbarXOffset, int scrollbarYOffset, int scrollButtonWidth, int scrollButtonColor,
							 Textures textures) {
	public static final Codec<BookDefinition> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		ConfiguredBookComponent.CODEC.listOf().fieldOf("components").forGetter(BookDefinition::components),
		Codec.INT.fieldOf("width").forGetter(BookDefinition::width),
		Codec.INT.fieldOf("height").forGetter(BookDefinition::height),
		Codec.INT.fieldOf("content_offset_x").forGetter(BookDefinition::frameWidth),
		Codec.INT.fieldOf("button_width").forGetter(BookDefinition::buttonWidth),
		Codec.INT.fieldOf("button_height").forGetter(BookDefinition::buttonHeight),
		Codec.INT.fieldOf("up_button_offset").forGetter(BookDefinition::upButtonOffset),
		Codec.INT.fieldOf("down_button_offset").forGetter(BookDefinition::downButtonOffset),
		Codec.INT.fieldOf("button_distance_from_right").forGetter(BookDefinition::buttonDistanceFromRight),
		Codec.INT.fieldOf("scrollbar_width").forGetter(BookDefinition::scrollbarWidth),
		Codec.INT.fieldOf("scrollbar_height").forGetter(BookDefinition::scrollbarHeight),
		Codec.INT.fieldOf("scrollbar_x_offset").forGetter(BookDefinition::scrollbarXOffset),
		Codec.INT.fieldOf("scrollbar_y_offset").forGetter(BookDefinition::scrollbarYOffset),
		Codec.INT.fieldOf("scroll_button_width").forGetter(BookDefinition::scrollButtonWidth),
		Codec.INT.fieldOf("scroll_button_color").forGetter(BookDefinition::scrollButtonColor),
		Textures.CODEC.fieldOf("textures").forGetter(BookDefinition::textures)
	).apply(instance, BookDefinition::new));

	public record Textures(ResourceLocation background, ResourceLocation overlay, ResourceLocation upButton, ResourceLocation downButton) {
		public static final Codec<Textures> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("background").forGetter(Textures::background),
			ResourceLocation.CODEC.fieldOf("overlay").forGetter(Textures::overlay),
			ResourceLocation.CODEC.fieldOf("up_button").forGetter(Textures::upButton),
			ResourceLocation.CODEC.fieldOf("down_button").forGetter(Textures::downButton)
		).apply(instance, Textures::new));
	}
}
