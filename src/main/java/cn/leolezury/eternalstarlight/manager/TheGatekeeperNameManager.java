package cn.leolezury.eternalstarlight.manager;

import cn.leolezury.eternalstarlight.EternalStarlight;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TheGatekeeperNameManager extends SimplePreparableReloadListener<List<String>> {
    private static final ResourceLocation NAMES_LOCATION = new ResourceLocation(EternalStarlight.MOD_ID, "texts/gatekeeper_names.txt");
    private static final RandomSource RANDOM = RandomSource.create();
    private final List<String> names = Lists.newArrayList();

    protected List<String> prepare(ResourceManager mgr, ProfilerFiller filler) {
        try (BufferedReader reader = Minecraft.getInstance().getResourceManager().openAsReader(NAMES_LOCATION)) {
            return reader.lines().map(String::trim).collect(Collectors.toList());
        } catch (IOException ioexception) {
            return Collections.emptyList();
        }
    }

    protected void apply(List<String> list, ResourceManager mgr, ProfilerFiller filler) {
        this.names.clear();
        this.names.addAll(list);
    }

    @Nullable
    public String getTheGatekeeperName() {
        return this.names.get(RANDOM.nextInt(this.names.size()));
    }
}
