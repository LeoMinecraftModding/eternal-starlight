package cn.leolezury.eternalstarlight.common.client.resource;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.ResolvedBookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESBookLoader extends SimpleJsonResourceReloadListener {
	private static final String SECTIONS_FOLDER = "sections/";

	private final Map<ResourceLocation, ResolvedBookDefinition> books = new HashMap<>();

	public ESBookLoader() {
		super(new Gson(), EternalStarlight.ID + "/books");
	}

	@Nullable
	public ResolvedBookDefinition getBook(ResourceLocation key) {
		return books.get(key);
	}

	@Override
	public void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
		books.clear();
		Map<ResourceLocation, List<ConfiguredBookComponent<?, ?>>> sections = new HashMap<>();
		Map<ResourceLocation, BookDefinition> definitions = new HashMap<>();
		int sectionCount = 0;
		int loaded = 0;
		for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
			try {
				if (entry.getKey().getPath().startsWith(SECTIONS_FOLDER)) {
					List<ConfiguredBookComponent<?, ?>> components = ConfiguredBookComponent.CODEC.listOf().parse(JsonOps.INSTANCE, entry.getValue())
						.getOrThrow(JsonParseException::new);
					ResourceLocation id = entry.getKey().withPath(path -> path.substring(SECTIONS_FOLDER.length()));
					sections.put(id, components);
					sectionCount++;
				} else {
					BookDefinition definition = BookDefinition.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
						.getOrThrow(JsonParseException::new);
					definitions.put(entry.getKey(), definition);
				}
			} catch (Exception e) {
				EternalStarlight.LOGGER.error("Failed to parse book file {}", entry.getKey(), e);
			}
		}
		for (Map.Entry<ResourceLocation, BookDefinition> entry : definitions.entrySet()) {
			BookDefinition definition = entry.getValue();
			List<List<ConfiguredBookComponent<?, ?>>> components = new ArrayList<>();
			boolean missing = false;
			for (ResourceLocation section : definition.sections()) {
				List<ConfiguredBookComponent<?, ?>> sectionComponents = sections.get(section);
				if (sectionComponents == null) {
					EternalStarlight.LOGGER.error("Failed to load book {}: section {} not found", entry.getKey(), section);
					missing = true;
					break;
				}
				components.add(sectionComponents);
			}
			if (!missing) {
				books.put(entry.getKey(), new ResolvedBookDefinition(definition, components));
				loaded++;
			}
		}
		EternalStarlight.LOGGER.info("Loaded {} book definitions ({} sections)", loaded, sectionCount);
	}
}
