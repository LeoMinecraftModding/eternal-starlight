package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public record ConfiguredBookComponent<C extends BookComponentConfig, B extends BookComponent<C>>(B component, C config) {
	public static final Codec<ConfiguredBookComponent<?, ?>> CODEC = BookComponentRegistry.BY_NAME_CODEC
		.dispatch(configured -> configured.component, BookComponent::getConfiguredCodec);

	public int getTotalHeight(BookContext context) {
		return component().getTotalHeight(config(), context);
	}

	public void render(BookContext context, GuiGraphics graphics, int x, int y) {
		component().render(config(), context, graphics, x, y);
	}

	public void renderDelayed(BookContext context, GuiGraphics graphics, int x, int y) {
		component().renderDelayed(config(), context, graphics, x, y);
	}

	public void tick(BookContext context, int x, int y) {
		component().tick(config(), context, x, y);
	}

	public void onClick(BookContext context, int x, int y) {
		component().onClick(config(), context, x, y);
	}
}
