package cn.leolezury.eternalstarlight.quilt.manager.book;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.manager.book.BookManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

public class QuiltBookManager extends BookManager implements IdentifiableResourceReloader {
    @Override
    public @NotNull ResourceLocation getQuiltId() {
        return new ResourceLocation(EternalStarlight.MOD_ID, "book");
    }
}
