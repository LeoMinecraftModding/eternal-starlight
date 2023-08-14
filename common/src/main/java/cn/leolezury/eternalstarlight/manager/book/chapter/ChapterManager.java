package cn.leolezury.eternalstarlight.manager.book.chapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChapterManager extends SimpleJsonResourceReloadListener {
    public static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();
    private final Map<ResourceLocation, ChapterData> chapterDataMap = new HashMap<>();
    private static final String folder = "eternal_starlight/chapters";

    public ChapterManager() {
        super(GSON_INSTANCE, folder);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceList, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : resourceList.entrySet()) {
            ResourceLocation location = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if (jsonElement.isJsonObject() && jsonElement instanceof JsonObject jsonObject) {
                chapterDataMap.put(location, fromJson(jsonObject));
            }
        }
    }

    private ChapterData fromJson(JsonObject jsonObject) {
        ChapterData.Builder builder = new ChapterData.Builder();
        if (jsonObject.has("trigger")) {
            ResourceLocation location = new ResourceLocation(jsonObject.get("trigger").getAsString());
            builder.withTrigger(location);
        }
        if (jsonObject.has("image")) {
            ResourceLocation location = new ResourceLocation(jsonObject.get("image").getAsString());
            builder.withImage(location);
        }
        if (jsonObject.has("display_entity")) {
            ResourceLocation location = new ResourceLocation(jsonObject.get("display_entity").getAsString());
            builder.withEntityDisplay(location);
        }
        if (jsonObject.has("entity_display_scale")) {
            builder.withDisplayScale(jsonObject.get("entity_display_scale").getAsInt());
        }
        if (jsonObject.has("title")) {
            builder.withTitle(jsonObject.get("title").getAsString());
        }
        if (jsonObject.has("content")) {
            builder.withContent(jsonObject.get("content").getAsString());
        }
        return builder.build();
    }

    public ChapterData getChapterData(ResourceLocation location) {
        return chapterDataMap.get(location);
    }

    public Set<ResourceLocation> getChapterLocations() {
        return chapterDataMap.keySet();
    }
}
