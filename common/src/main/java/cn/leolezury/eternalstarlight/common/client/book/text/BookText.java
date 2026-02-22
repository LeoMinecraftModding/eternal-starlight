package cn.leolezury.eternalstarlight.common.client.book.text;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public record BookText(boolean translation, String content) {
	public static final Codec<BookText> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		Codec.BOOL.fieldOf("translation").forGetter(BookText::translation),
		Codec.STRING.fieldOf("content").forGetter(BookText::content)
	).apply(instance, BookText::new));

	public static final Codec<List<BookText>> LIST_CODEC = CODEC.listOf();

	private static class FormatState {
		Integer color = null;
		ChatFormatting style = null;
		String link = null;
	}

	public BookText(boolean translation, String content) {
		this.translation = translation;
		this.content = content;
	}

	public String getString() {
		if (translation) {
			Language language = Language.getInstance();
			return language.getOrDefault(content);
		} else {
			return content;
		}
	}

	public static List<Component> parse(String input) {
		List<Component> result = new ArrayList<>();
		StringBuilder currentText = new StringBuilder();
		FormatState currentState = new FormatState();

		int i = 0;
		while (i < input.length()) {
			char c = input.charAt(i);

			if (c == '$' && i + 1 < input.length() && input.charAt(i + 1) == '{') {
				if (!currentText.isEmpty()) {
					result.add(createText(currentText.toString(), currentState));
					currentText.setLength(0);
				}

				int endIndex = input.indexOf('}', i + 2);
				if (endIndex == -1) {
					currentText.append("${");
					i += 2;
					continue;
				}

				String command = input.substring(i + 2, endIndex);
				processCommand(command, currentState);
				i = endIndex + 1;
			} else {
				currentText.append(c);
				i++;
			}
		}

		if (!currentText.isEmpty()) {
			result.add(createText(currentText.toString(), currentState));
		}

		return result;
	}

	private static void processCommand(String command, FormatState state) {
		if (command.equals("r")) {
			state.color = null;
			state.style = null;
			state.link = null;
		}
		if (command.startsWith("color:")) {
			String color = command.substring(6).trim();
			if (color.startsWith("#")) {
				try {
					state.color = Integer.parseInt(color.substring(1), 16);
				} catch (NumberFormatException ignored) {

				}
			}
		}
		if (command.startsWith("style:")) {
			String style = command.substring(6).trim();
			if (style.length() == 1) {
				state.style = ChatFormatting.getByCode(style.charAt(0));
			}
		}
		if (command.startsWith("link:")) {
			state.link = command.substring(5).trim();
		}
	}

	private static Component createText(String content, FormatState state) {
		MutableComponent text = Component.literal(content);
		if (state.color != null) {
			text.withColor(state.color);
		}
		if (state.style != null) {
			text.withStyle(state.style);
		}
		if (state.link != null) {
			text.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, state.link)));
		}
		return text;
	}
}
