package cn.leolezury.eternalstarlight.common.client.resource;

import cn.leolezury.eternalstarlight.common.client.renderer.entity.ESPaintingRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ESClientResourceReloadListener implements ResourceManagerReloadListener {
	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		ESPaintingRenderer.clearCache();
	}
}
