package cn.leolezury.eternalstarlight.common.client.book.text;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class BookContent {
	public static final Codec<BookContent> CODEC = BookText.LIST_CODEC.xmap(BookContent::new, content -> content.texts);

	private final List<BookText> texts;
	private MutableComponent cachedText = null;

	public BookContent(List<BookText> texts) {
		this.texts = texts;
	}

	public Component toComponent() {
		if (cachedText == null) {
			cachedText = Component.empty();
			StringBuilder merged = new StringBuilder();
			for (BookText text : texts) {
				merged.append(text.getString());
			}
			BookText.parse(merged.toString()).forEach(component -> cachedText.append(component));
		}
		return cachedText;
	}
}
