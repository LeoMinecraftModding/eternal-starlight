package cn.leolezury.eternalstarlight.fabric.platform;

import cn.leolezury.eternalstarlight.common.client.resource.BookLoader;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.fabric.client.model.item.ESFabricGlowingBakedModel;
import cn.leolezury.eternalstarlight.fabric.client.resource.ESFabricBookLoader;
import com.google.auto.service.AutoService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

@AutoService(ESClientPlatform.class)
public class ESFabricClientPlatform implements ESClientPlatform {
	@Override
	public BookLoader createBookLoader() {
		return new ESFabricBookLoader();
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
