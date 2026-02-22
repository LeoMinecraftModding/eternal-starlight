package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record BookDefinition(List<List<ConfiguredBookComponent<?, ?>>> components,
							 int width, int height, int frameWidth,
							 Buttons buttons,
							 Scrollbar scrollbar,
							 Textures textures) {
	public static final Codec<BookDefinition> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		ConfiguredBookComponent.CODEC.listOf().listOf().fieldOf("components").forGetter(BookDefinition::components),
		Codec.INT.fieldOf("width").forGetter(BookDefinition::width),
		Codec.INT.fieldOf("height").forGetter(BookDefinition::height),
		Codec.INT.fieldOf("frame_width").forGetter(BookDefinition::frameWidth),
		Buttons.CODEC.fieldOf("buttons").forGetter(BookDefinition::buttons),
		Scrollbar.CODEC.fieldOf("scrollbar").forGetter(BookDefinition::scrollbar),
		Textures.CODEC.fieldOf("textures").forGetter(BookDefinition::textures)
	).apply(instance, BookDefinition::new));

	public record Buttons(int upDownButtonWidth, int upDownButtonHeight, int upButtonOffset, int downButtonOffset, int upDownButtonDistanceFromRight, int leftRightButtonWidth, int leftRightButtonHeight, int leftButtonOffset, int rightButtonOffset, int leftRightButtonDistanceFromTop) {
		public static final Codec<Buttons> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.INT.fieldOf("up_down_button_width").forGetter(Buttons::upDownButtonWidth),
			Codec.INT.fieldOf("up_down_button_height").forGetter(Buttons::upDownButtonHeight),
			Codec.INT.fieldOf("up_button_offset").forGetter(Buttons::upButtonOffset),
			Codec.INT.fieldOf("down_button_offset").forGetter(Buttons::downButtonOffset),
			Codec.INT.fieldOf("up_down_button_distance_from_right").forGetter(Buttons::upDownButtonDistanceFromRight),
			Codec.INT.fieldOf("left_right_button_width").forGetter(Buttons::leftRightButtonWidth),
			Codec.INT.fieldOf("left_right_button_height").forGetter(Buttons::leftRightButtonHeight),
			Codec.INT.fieldOf("left_button_offset").forGetter(Buttons::leftButtonOffset),
			Codec.INT.fieldOf("right_button_offset").forGetter(Buttons::rightButtonOffset),
			Codec.INT.fieldOf("left_right_button_distance_from_top").forGetter(Buttons::leftRightButtonDistanceFromTop)
		).apply(instance, Buttons::new));
	}

	public record Scrollbar(int scrollbarWidth, int scrollbarHeight, int scrollbarXOffset, int scrollbarYOffset, int scrollButtonWidth, int scrollButtonColor) {
		public static final Codec<Scrollbar> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.INT.fieldOf("scrollbar_width").forGetter(Scrollbar::scrollbarWidth),
			Codec.INT.fieldOf("scrollbar_height").forGetter(Scrollbar::scrollbarHeight),
			Codec.INT.fieldOf("scrollbar_x_offset").forGetter(Scrollbar::scrollbarXOffset),
			Codec.INT.fieldOf("scrollbar_y_offset").forGetter(Scrollbar::scrollbarYOffset),
			Codec.INT.fieldOf("scroll_button_width").forGetter(Scrollbar::scrollButtonWidth),
			Codec.INT.fieldOf("scroll_button_color").forGetter(Scrollbar::scrollButtonColor)
		).apply(instance, Scrollbar::new));
	}

	public record Textures(ResourceLocation background, ResourceLocation overlay, ResourceLocation topOverlay, ResourceLocation upButton, ResourceLocation downButton, ResourceLocation leftButton, ResourceLocation rightButton) {
		public static final Codec<Textures> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("background").forGetter(Textures::background),
			ResourceLocation.CODEC.fieldOf("overlay").forGetter(Textures::overlay),
			ResourceLocation.CODEC.fieldOf("top_overlay").forGetter(Textures::topOverlay),
			ResourceLocation.CODEC.fieldOf("up_button").forGetter(Textures::upButton),
			ResourceLocation.CODEC.fieldOf("down_button").forGetter(Textures::downButton),
			ResourceLocation.CODEC.fieldOf("left_button").forGetter(Textures::leftButton),
			ResourceLocation.CODEC.fieldOf("right_button").forGetter(Textures::rightButton)
		).apply(instance, Textures::new));
	}

	public Optional<ConfiguredBookComponent<?, ?>> getComponent(ResourceLocation id) {
		for (ConfiguredBookComponent<?, ?> component : components().stream().flatMap(List::stream).toList()) {
			if (component.config().id().equals(id)) {
				return Optional.of(component);
			}
		}
		return Optional.empty();
	}
}
