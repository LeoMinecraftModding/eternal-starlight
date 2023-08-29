package cn.leolezury.eternalstarlight.quilt.manager.book.chapter;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.manager.book.chapter.ChapterManager;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;
import net.minecraft.resources.ResourceLocation;

public class QuiltChapterManager extends ChapterManager implements IdentifiableResourceReloader {
    @Override
    public @NotNull ResourceLocation getQuiltId() {
        return new ResourceLocation(EternalStarlight.MOD_ID, "chapter");
    }
}
