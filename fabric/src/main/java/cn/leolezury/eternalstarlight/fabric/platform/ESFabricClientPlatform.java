package cn.leolezury.eternalstarlight.fabric.platform;

import cn.leolezury.eternalstarlight.common.client.resource.ESBookLoader;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.fabric.client.model.item.ESFabricGlowingBakedModel;
import cn.leolezury.eternalstarlight.fabric.client.resource.ESFabricBookLoader;
import cn.leolezury.eternalstarlight.fabric.client.resource.ESFabricClientResourceReloadListener;
import com.google.auto.service.AutoService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

@AutoService(ESClientPlatform.class)
public class ESFabricClientPlatform implements ESClientPlatform {
	@Override
	public ESBookLoader createBookLoader() {
		return new ESFabricBookLoader();
	}

	@Override
	public ResourceManagerReloadListener createResourceReloadListener() {
		return new ESFabricClientResourceReloadListener();
	}

	@Override
	public BakedModel getGlowingBakedModel(BakedModel origin) {
		return new ESFabricGlowingBakedModel(origin);
	}

	@Override
	public void sendToServer(CustomPacketPayload packet) {
		if (Minecraft.getInstance().getConnection() != null) {
			ClientPlayNetworking.send(packet);
		}
	}
}
