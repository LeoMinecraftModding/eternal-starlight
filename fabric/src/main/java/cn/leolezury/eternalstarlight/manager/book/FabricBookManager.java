package cn.leolezury.eternalstarlight.manager.book;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class FabricBookManager extends BookManager implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(EternalStarlight.MOD_ID, "book_manager");
    }
}
