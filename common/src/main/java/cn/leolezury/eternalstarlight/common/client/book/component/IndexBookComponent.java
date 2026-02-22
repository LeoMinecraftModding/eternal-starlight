package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IndexBookComponent extends BookComponent<IndexBookComponent.Config> {
	public IndexBookComponent() {
		super(Config.CODEC);
	}

	private List<Entry> filterEnabledEntries(Config config, BookContext context) {
		List<Entry> filtered = new ArrayList<>(config.entries());
		filtered.removeIf(entry -> !context.isComponentEnabled(entry.jumpTo));
		return filtered;
	}

	@Override
	public int getTotalHeight(Config config, BookContext context) {
		int totalHeight = config.offset() + config.extraHeight();
		for (Entry entry : filterEnabledEntries(config, context)) {
			totalHeight += entry.getHeight(config, context);
		}
		return totalHeight;
	}

	@Override
	public void render(Config config, BookContext context, GuiGraphics graphics, int x, int y) {
		int startHeight = config.offset();
		for (Entry entry : filterEnabledEntries(config, context)) {
			entry.render(config, context, graphics, x, y + startHeight);
			startHeight += entry.getHeight(config, context);
		}
	}

	@Override
	public void onClick(Config config, BookContext context, int x, int y) {
		int startHeight = y + config.offset();
		for (Entry entry : filterEnabledEntries(config, context)) {
			int currentHeight = entry.getHeight(config, context);
			if (context.getMouseY() > startHeight && context.getMouseY() < startHeight + currentHeight) {
				context.jumpToComponent(entry.jumpTo);
				break;
			}
			startHeight += currentHeight;
		}
	}

	public static class Entry {
		public static final Codec<Entry> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BookContent.CODEC.fieldOf("text").forGetter(o -> o.text),
			ResourceLocation.CODEC.fieldOf("jump_to").forGetter(o -> o.jumpTo),
			ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("listening").forGetter(o -> o.listening),
			Codec.INT.fieldOf("icon_frame_width").forGetter(o -> o.iconFrameWidth),
			Codec.INT.fieldOf("icon_frame_height").forGetter(o -> o.iconFrameHeight),
			ResourceLocation.CODEC.fieldOf("icon_frame").forGetter(o -> o.iconFrame),
			CompoundTag.CODEC.fieldOf("icon").forGetter(o -> o.icon)
		).apply(instance, Entry::new));

		private final BookContent text;
		private final ResourceLocation jumpTo;
		private final HashSet<ResourceLocation> listening;
		private final int iconFrameWidth;
		private final int iconFrameHeight;
		private final ResourceLocation iconFrame;
		private final CompoundTag icon;
		private ItemStack cachedIcon;

		public Entry(BookContent text, ResourceLocation jumpTo, HashSet<ResourceLocation> listening, int iconFrameWidth, int iconFrameHeight, ResourceLocation iconFrame, CompoundTag icon) {
			this.text = text;
			this.jumpTo = jumpTo;
			this.listening = listening;
			this.iconFrameWidth = iconFrameWidth;
			this.iconFrameHeight = iconFrameHeight;
			this.iconFrame = iconFrame;
			this.icon = icon;
		}

		public ResourceLocation getJumpToId() {
			return jumpTo;
		}

		public HashSet<ResourceLocation> getListeningIds() {
			return listening;
		}

		public Component getText() {
			return text.toComponent();
		}

		public ItemStack getIcon() {
			if (cachedIcon == null && Minecraft.getInstance().level != null) {
				cachedIcon = ItemStack.parseOptional(Minecraft.getInstance().level.registryAccess(), icon);
			}
			return cachedIcon == null ? ItemStack.EMPTY : cachedIcon;
		}

		private List<FormattedCharSequence> splitText(Config config, BookContext context, Component component) {
			return context.getFont().split(component, config.width() - iconFrameWidth - 5);
		}

		public int getHeight(Config config, BookContext context) {
			return Math.max((splitText(config, context, getText()).size() - 1) * config.lineHeight() + context.getFont().lineHeight / 2 + iconFrameHeight / 2, iconFrameHeight);
		}

		public void render(Config config, BookContext context, GuiGraphics graphics, int x, int y) {
			graphics.blit(iconFrame, x, y, 0, 0, iconFrameWidth, iconFrameHeight, iconFrameWidth, iconFrameHeight);
			graphics.renderItem(getIcon(), x + iconFrameWidth / 2 - 8, y + iconFrameHeight / 2 - 8);
			boolean selected = context.getMouseX() >= context.getContentX() && context.getMouseX() <= context.getContentX() + context.getBookDefinition().width() - 2 * context.getBookDefinition().frameWidth()
				&& context.getMouseY() > y && context.getMouseY() < y + getHeight(config, context);
			List<FormattedCharSequence> list = splitText(config, context, selected ? getText().copy().withStyle(ChatFormatting.UNDERLINE) : getText());
			for (int i = 0; i < list.size(); i++) {
				graphics.drawString(context.getFont(), list.get(i), x + iconFrameWidth + 5, y + iconFrameHeight / 2 - context.getFont().lineHeight / 2 + i * config.lineHeight(), -1, true);
			}
		}
	}

	public record Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, List<Entry> entries, int offset, int extraHeight, int width, int lineHeight) implements BookComponentConfig {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("id").forGetter(Config::id),
			ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("unlock_conditions").forGetter(Config::unlockConditions),
			Entry.CODEC.listOf().fieldOf("entries").forGetter(Config::entries),
			Codec.INT.fieldOf("offset").forGetter(Config::offset),
			Codec.INT.fieldOf("extra_height").forGetter(Config::extraHeight),
			Codec.INT.fieldOf("width").forGetter(Config::width),
			Codec.INT.fieldOf("line_height").forGetter(Config::lineHeight)
		).apply(instance, Config::new));
	}
}
