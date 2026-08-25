package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record ResolvedBookDefinition(BookDefinition definition, List<List<ConfiguredBookComponent<?, ?>>> components) {
	public int width() {
		return definition.width();
	}

	public int height() {
		return definition.height();
	}

	public int frameWidth() {
		return definition.frameWidth();
	}

	public BookDefinition.Buttons buttons() {
		return definition.buttons();
	}

	public BookDefinition.Scrollbar scrollbar() {
		return definition.scrollbar();
	}

	public BookDefinition.Textures textures() {
		return definition.textures();
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
