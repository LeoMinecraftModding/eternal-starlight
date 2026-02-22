package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.HashSet;
import java.util.List;

public class TextBookComponent extends BookComponent<TextBookComponent.Config> {
	public TextBookComponent() {
		super(Config.CODEC);
	}

	@Override
	public int getTotalHeight(Config config, BookContext context) {
		return context.getFont().split(config.text().toComponent(), config.width()).size() * config.lineHeight() + config.offset() + config.extraHeight();
	}

	@Override
	public void render(Config config, BookContext context, GuiGraphics graphics, int x, int y) {
		List<FormattedCharSequence> list = context.getFont().split(config.text().toComponent(), config.width());
		for (int i = 0; i < list.size(); i++) {
			int renderY = y + config.offset() + i * config.lineHeight();
			float alpha = Math.abs(renderY - (context.getContentY() - context.getBookDefinition().frameWidth() + 0.5f * context.getBookDefinition().height())) / (0.5f * context.getBookDefinition().height() - context.getBookDefinition().frameWidth());
			graphics.drawString(context.getFont(), list.get(i), x, renderY, FastColor.ARGB32.colorFromFloat(Mth.clamp(1.2f - alpha, 0.1f, 1), 0, 0, 0), true);
		}
	}

	@Override
	public void onClick(Config config, BookContext context, int x, int y) {
		List<FormattedCharSequence> list = context.getFont().split(config.text().toComponent(), config.width());
		int line = (context.getMouseY() - (y + config.offset())) / config.lineHeight();
		if (context.getMouseY() >= (y + config.offset()) && line >= 0 && line < list.size()) {
			FormattedCharSequence charSequence = list.get(line);
			int width = context.getFont().width(charSequence);
			if (context.getMouseX() >= x && context.getMouseX() <= x + width) {
				Style style = context.getFont().getSplitter().componentStyleAtWidth(charSequence, context.getMouseX() - x);
				if (style != null) {
					ClickEvent event = style.getClickEvent();
					if (event != null && event.getAction() == ClickEvent.Action.CHANGE_PAGE) {
						context.jumpToComponent(ResourceLocation.parse(event.getValue()));
					}
				}
			}
		}
	}

	public record Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, BookContent text, int offset, int extraHeight, int width, int lineHeight) implements BookComponentConfig {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("id").forGetter(Config::id),
			ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("unlock_conditions").forGetter(Config::unlockConditions),
			BookContent.CODEC.fieldOf("text").forGetter(Config::text),
			Codec.INT.fieldOf("offset").forGetter(Config::offset),
			Codec.INT.fieldOf("extra_height").forGetter(Config::extraHeight),
			Codec.INT.fieldOf("width").forGetter(Config::width),
			Codec.INT.fieldOf("line_height").forGetter(Config::lineHeight)
		).apply(instance, Config::new));
	}
}
