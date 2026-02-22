package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.GuiGraphics;

public abstract class BookComponent<C extends BookComponentConfig> {
	private final MapCodec<ConfiguredBookComponent<C, BookComponent<C>>> configuredCodec;

	public BookComponent(Codec<C> configCodec) {
		this.configuredCodec = configCodec.fieldOf("config").xmap(c -> new ConfiguredBookComponent<>(this, c), ConfiguredBookComponent::config);
	}

	public MapCodec<ConfiguredBookComponent<C, BookComponent<C>>> getConfiguredCodec() {
		return configuredCodec;
	}

	public abstract int getTotalHeight(C config, BookContext context);

	public abstract void render(C config, BookContext context, GuiGraphics graphics, int x, int y);

	public void renderDelayed(C config, BookContext context, GuiGraphics graphics, int x, int y) {

	}

	public void tick(C config, BookContext context, int x, int y) {

	}

	public void onClick(C config, BookContext context, int x, int y) {

	}
}
