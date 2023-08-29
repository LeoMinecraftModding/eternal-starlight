package cn.leolezury.eternalstarlight.fabric.client;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ESModelLoadingPlugin implements ModelLoadingPlugin {
    public static final List<ResourceLocation> MODELS = new ArrayList<>();

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.addModels(MODELS);
    }
}
