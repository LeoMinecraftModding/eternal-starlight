package cn.leolezury.eternalstarlight.manager.book.chapter;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class FabricChapterManager extends ChapterManager implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(EternalStarlight.MOD_ID, "chapter_manager");
    }
}
