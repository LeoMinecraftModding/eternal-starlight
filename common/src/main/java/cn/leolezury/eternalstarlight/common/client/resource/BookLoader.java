package cn.leolezury.eternalstarlight.common.client.resource;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BookLoader extends SimpleJsonResourceReloadListener {
	private final Map<ResourceLocation, BookDefinition> books = new HashMap<>();

	public BookLoader() {
		super(new Gson(), "eternal_starlight/books");
	}

	@Nullable
	public BookDefinition getBook(ResourceLocation key) {
		return books.get(key);
	}

	@Override
	public void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
		books.clear();
		int loaded = 0;
		for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
			try {
				BookDefinition definition = BookDefinition.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
					.getOrThrow(JsonParseException::new);
				books.put(entry.getKey(), definition);
				loaded++;
			} catch (Exception e) {
				EternalStarlight.LOGGER.error("Failed to book definition {}", entry.getKey(), e);
			}
		}
		EternalStarlight.LOGGER.info("Loaded {} book definitions", loaded);
	}
}
