package cn.leolezury.eternalstarlight.fabric.platform;

import cn.leolezury.eternalstarlight.common.client.resource.BookLoader;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.fabric.client.model.item.ESFabricGlowingBakedModel;
import cn.leolezury.eternalstarlight.fabric.client.resource.ESFabricBookLoader;
import com.google.auto.service.AutoService;
import net.minecraft.client.resources.model.BakedModel;

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
}
