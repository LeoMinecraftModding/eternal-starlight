package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ESMiscUtil {
	public static void runWhenOnClient(Supplier<Runnable> toRun) {
		if (ESPlatform.INSTANCE.isPhysicalClient()) {
			toRun.get().run();
		}
	}

	public static String shuffleString(String input) {
		List<Character> characters = input.chars()
			.mapToObj(e -> (char) e)
			.collect(Collectors.toList());
		Collections.shuffle(characters);
		return characters.stream()
			.map(String::valueOf)
			.collect(Collectors.joining());
	}
}
