package cn.leolezury.eternalstarlight.manager.book;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.event.server.ServerEvents;
import cn.leolezury.eternalstarlight.manager.book.chapter.ChapterData;
import cn.leolezury.eternalstarlight.manager.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.message.OpenSLBookMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;

import java.util.*;

public class BookManager extends SimpleJsonResourceReloadListener {
    public static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();
    private final Map<ResourceLocation, BookData> bookDataMap = new HashMap<>();
    private static final String folder = "eternal_starlight/books";

    public BookManager() {
        super(GSON_INSTANCE, folder);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceList, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : resourceList.entrySet()) {
            ResourceLocation location = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if (jsonElement.isJsonObject() && jsonElement instanceof JsonObject jsonObject) {
                bookDataMap.put(location, fromJson(jsonObject));
            }
        }
    }

    private BookData fromJson(JsonObject jsonObject) {
        BookData.Builder builder = new BookData.Builder();
        if (jsonObject.has("background")) {
            ResourceLocation location = new ResourceLocation(jsonObject.get("background").getAsString());
            builder.withBackground(location);
        }
        if (jsonObject.has("title_background")) {
            ResourceLocation location = new ResourceLocation(jsonObject.get("title_background").getAsString());
            builder.withTitleBackground(location);
        }
        if (jsonObject.has("title")) {
            builder.withTitle(jsonObject.get("title").getAsString());
        }
        if (jsonObject.has("chapters")) {
            JsonArray array = jsonObject.getAsJsonArray("chapters");
            List<ResourceLocation> chapters = array.asList().stream().map(jsonElement -> new ResourceLocation(jsonElement.getAsString())).toList();
            builder.withChapters(chapters);
        }
        if (jsonObject.has("width")) {
            builder.withWidth(jsonObject.get("width").getAsInt());
        }
        if (jsonObject.has("height")) {
            builder.withHeight(jsonObject.get("height").getAsInt());
        }
        if (jsonObject.has("text_offset_x")) {
            builder.withTextOffsetX(jsonObject.get("text_offset_x").getAsInt());
        }
        if (jsonObject.has("text_offset_y")) {
            builder.withTextOffsetY(jsonObject.get("text_offset_y").getAsInt());
        }
        return builder.build();
    }

    public BookData getBookData(ResourceLocation location) {
        return bookDataMap.get(location);
    }
    public Set<ResourceLocation> getBookLocations() {
        return bookDataMap.keySet();
    }

    public static void openBook(ResourceLocation bookLocation, ServerPlayer serverPlayer) {
        BookManager bookManager = ServerEvents.getBookManager();
        ChapterManager chapterManager = ServerEvents.getChapterManager();
        if (bookManager.getBookLocations().contains(bookLocation)) {
            BookData bookData = bookManager.getBookData(bookLocation);
            List<ChapterData> chapterDataList = new ArrayList<>();
            for (ResourceLocation chapterLocation : bookData.getChapters()) {
                if (chapterManager.getChapterLocations().contains(chapterLocation)) {
                    ChapterData chapterData = chapterManager.getChapterData(chapterLocation);
                    if (serverPlayer.getServer() != null) {
                        ServerAdvancementManager advancementManager = serverPlayer.getServer().getAdvancements();
                        Advancement advancement = advancementManager.getAdvancement(chapterData.getTrigger());
                        if (advancement != null) {
                            AdvancementProgress advancementProgress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
                            if (advancementProgress.isDone()) {
                                chapterDataList.add(chapterData);
                            }
                        }
                    }
                }
            }
            EternalStarlight.sendMessageToClient(new OpenSLBookMessage(bookData, chapterDataList), serverPlayer);
        }
    }
}
