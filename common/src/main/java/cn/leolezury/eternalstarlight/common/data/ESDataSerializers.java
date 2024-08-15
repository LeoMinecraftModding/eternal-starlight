package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;

public class ESDataSerializers {

	public static final EntityDataSerializer<ResourceKey<Crest>> CREST;

	static {
		CREST = EntityDataSerializer.forValueType(ResourceKey.streamCodec(ESRegistries.CREST));

		EntityDataSerializers.registerSerializer(CREST);
	}
}
