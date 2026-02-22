package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookComponentRegistry {
	private static final Map<ResourceLocation, BookComponent<?>> COMPONENTS = new HashMap<>();
	public static final Codec<BookComponent<?>> BY_NAME_CODEC = ResourceLocation.CODEC.xmap(id -> get(id).orElseThrow(), BookComponentRegistry::getKey);

	public static final TextBookComponent TEXT = register("text", new TextBookComponent());
	public static final DisplayBookComponent DISPLAY = register("display", new DisplayBookComponent());
	public static final IndexBookComponent INDEX = register("index", new IndexBookComponent());
	public static final MobInfoBookComponent MOB_INFO = register("mob_info", new MobInfoBookComponent());

	private static <T extends BookComponent<?>> T register(String location, T type) {
		return register(EternalStarlight.id(location), type);
	}

	public static <T extends BookComponent<?>> T register(ResourceLocation location, T type) {
		COMPONENTS.put(location, type);
		return type;
	}

	public static Optional<BookComponent<?>> get(ResourceLocation location) {
		return Optional.ofNullable(COMPONENTS.get(location));
	}

	public static ResourceLocation getKey(BookComponent<?> type) {
		for (Map.Entry<ResourceLocation, BookComponent<?>> entry : COMPONENTS.entrySet()) {
			if (entry.getValue() == type) {
				return entry.getKey();
			}
		}
		return ResourceLocation.withDefaultNamespace("unregistered");
	}
}
