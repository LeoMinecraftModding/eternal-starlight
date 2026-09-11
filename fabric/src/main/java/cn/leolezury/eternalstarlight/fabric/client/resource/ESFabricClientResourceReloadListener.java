package cn.leolezury.eternalstarlight.fabric.client.resource;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.resource.ESClientResourceReloadListener;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class ESFabricClientResourceReloadListener extends ESClientResourceReloadListener implements IdentifiableResourceReloadListener {
	@Override
	public ResourceLocation getFabricId() {
		return EternalStarlight.id("client_resource_reload");
	}
}
