package cn.leolezury.eternalstarlight.fabric.client.resource;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.resource.BookLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class ESFabricBookLoader extends BookLoader implements IdentifiableResourceReloadListener {
	@Override
	public ResourceLocation getFabricId() {
		return EternalStarlight.id("book");
	}
}
