package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.HashSet;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TextBookComponent extends BookComponent<TextBookComponent.Config> {
	public TextBookComponent() {
		super(Config.CODEC);
	}

	@Override
	public int getTotalHeight(Config config, BookContext context) {
		return context.getFont().split(config.text(), config.width()).size() * config.lineHeight();
	}

	@Override
	public void render(Config config, BookContext context, GuiGraphics graphics, int x, int y) {
		List<FormattedCharSequence> list = context.getFont().split(config.text(), config.width());
		for (int i = 0; i < list.size(); i++) {
			float alpha = Math.abs(y + i * config.lineHeight() - (context.getContentY() - context.getBookDefinition().frameWidth() + 0.5f * context.getBookDefinition().height())) / (0.5f * context.getBookDefinition().height() - context.getBookDefinition().frameWidth());
			graphics.drawString(context.getFont(), list.get(i), x, y + i * config.lineHeight(), FastColor.ARGB32.colorFromFloat(Mth.clamp(1.2f - alpha, 0.1f, 1), 0, 0, 0), true);
		}
	}

	public record Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, Component text, int width, int lineHeight) implements BookComponentConfig {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("id").forGetter(Config::id),
			ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("unlock_conditions").forGetter(Config::unlockConditions),
			ComponentSerialization.CODEC.fieldOf("text").forGetter(Config::text),
			Codec.INT.fieldOf("width").forGetter(Config::width),
			Codec.INT.fieldOf("line_height").forGetter(Config::lineHeight)
		).apply(instance, Config::new));
	}
}
