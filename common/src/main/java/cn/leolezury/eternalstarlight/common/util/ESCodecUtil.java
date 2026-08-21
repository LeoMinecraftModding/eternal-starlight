package cn.leolezury.eternalstarlight.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FastColor;

import java.util.AbstractMap;
import java.util.Map;

public class ESCodecUtil {
	public static final Codec<Integer> RGB_COLOR_CODEC = Codec.withAlternative(Codec.INT, ExtraCodecs.VECTOR3F, vector3f -> FastColor.ARGB32.colorFromFloat(0, vector3f.x(), vector3f.y(), vector3f.z()));

	@SuppressWarnings("unchecked")
	public static <K, V> Codec<Map<K, V>> createCodecForMap(Codec<K> keyCodec, Codec<V> valueCodec) {
		Codec<Map.Entry<K, V>> entryCodec = RecordCodecBuilder.create((instance) -> instance.group(
			keyCodec.fieldOf("key").forGetter(Map.Entry::getKey),
			valueCodec.fieldOf("value").forGetter(Map.Entry::getValue)
		).apply(instance, AbstractMap.SimpleEntry::new));
		return entryCodec.listOf().xmap(e -> Map.ofEntries(e.toArray(new Map.Entry[0])), map -> map.entrySet().stream().toList());
	}
}
