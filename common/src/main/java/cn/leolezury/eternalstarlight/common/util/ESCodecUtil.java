package cn.leolezury.eternalstarlight.common.util;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FastColor;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

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

	public static <B, T1, T2, T3, T4, T5, T6, T7, R> StreamCodec<B, R> compositeStreamCodec(StreamCodec<? super B, T1> codec1, Function<R, T1> getter1, StreamCodec<? super B, T2> codec2, Function<R, T2> getter2, StreamCodec<? super B, T3> codec3, Function<R, T3> getter3, StreamCodec<? super B, T4> codec4, Function<R, T4> getter4, StreamCodec<? super B, T5> codec5, Function<R, T5> getter5, StreamCodec<? super B, T6> codec6, Function<R, T6> getter6, StreamCodec<? super B, T7> codec7, Function<R, T7> getter7, Function7<T1, T2, T3, T4, T5, T6, T7, R> factory) {
		return new StreamCodec<>() {
			@Override
			public void encode(B buf, R object) {
				codec1.encode(buf, getter1.apply(object));
				codec2.encode(buf, getter2.apply(object));
				codec3.encode(buf, getter3.apply(object));
				codec4.encode(buf, getter4.apply(object));
				codec5.encode(buf, getter5.apply(object));
				codec6.encode(buf, getter6.apply(object));
				codec7.encode(buf, getter7.apply(object));
			}

			@Override
			public R decode(B buf) {
				return factory.apply(codec1.decode(buf), codec2.decode(buf), codec3.decode(buf), codec4.decode(buf), codec5.decode(buf), codec6.decode(buf), codec7.decode(buf));
			}
		};
	}

	public static <B, T1, T2, T3, T4, T5, T6, T7, T8, R> StreamCodec<B, R> compositeStreamCodec(StreamCodec<? super B, T1> codec1, Function<R, T1> getter1, StreamCodec<? super B, T2> codec2, Function<R, T2> getter2, StreamCodec<? super B, T3> codec3, Function<R, T3> getter3, StreamCodec<? super B, T4> codec4, Function<R, T4> getter4, StreamCodec<? super B, T5> codec5, Function<R, T5> getter5, StreamCodec<? super B, T6> codec6, Function<R, T6> getter6, StreamCodec<? super B, T7> codec7, Function<R, T7> getter7, StreamCodec<? super B, T8> codec8, Function<R, T8> getter8, Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> factory) {
		return new StreamCodec<>() {
			@Override
			public void encode(B buf, R object) {
				codec1.encode(buf, getter1.apply(object));
				codec2.encode(buf, getter2.apply(object));
				codec3.encode(buf, getter3.apply(object));
				codec4.encode(buf, getter4.apply(object));
				codec5.encode(buf, getter5.apply(object));
				codec6.encode(buf, getter6.apply(object));
				codec7.encode(buf, getter7.apply(object));
				codec8.encode(buf, getter8.apply(object));
			}

			@Override
			public R decode(B buf) {
				return factory.apply(codec1.decode(buf), codec2.decode(buf), codec3.decode(buf), codec4.decode(buf), codec5.decode(buf), codec6.decode(buf), codec7.decode(buf), codec8.decode(buf));
			}
		};
	}
}
