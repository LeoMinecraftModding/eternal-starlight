package cn.leolezury.eternalstarlight.manager.gatekeeper;

import cn.leolezury.eternalstarlight.EternalStarlight;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class TheGatekeeperNameManager extends SimpleJsonResourceReloadListener {
    private static final ResourceLocation NAMES_LOCATION = new ResourceLocation(EternalStarlight.MOD_ID, "eternal_starlight/gatekeeper_names/gatekeeper_names.json");
    private static final RandomSource RANDOM = RandomSource.create();
    public static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();
    private final List<String> names = Lists.newArrayList();
    private static final String folder = "eternal_starlight/gatekeeper_names";

    public TheGatekeeperNameManager() {
        super(GSON_INSTANCE, folder);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceList, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for(Resource iresource : resourceManagerIn.getResourceStack(NAMES_LOCATION)) {
            try (InputStream inputstream = iresource.open();
                 Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
            ) {
                JsonObject jsonobject = GsonHelper.fromJson(GSON_INSTANCE, reader, JsonObject.class);
                boolean replace = jsonobject.get("replace").getAsBoolean();
                if (replace)
                    names.clear();
                JsonArray entryList = jsonobject.get("values").getAsJsonArray();
                for(JsonElement entry : entryList) {
                    names.add(entry.getAsString());
                }
            }
            catch (RuntimeException | IOException ignored) {

            }
        }
    }

    @Nullable
    public String getTheGatekeeperName() {
        return this.names.get(RANDOM.nextInt(this.names.size()));
    }
}
