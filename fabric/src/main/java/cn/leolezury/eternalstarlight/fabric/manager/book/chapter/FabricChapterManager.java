package cn.leolezury.eternalstarlight.fabric.manager.book.chapter;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.manager.book.chapter.ChapterManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class FabricChapterManager extends ChapterManager implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(EternalStarlight.MOD_ID, "chapter");
    }
}
