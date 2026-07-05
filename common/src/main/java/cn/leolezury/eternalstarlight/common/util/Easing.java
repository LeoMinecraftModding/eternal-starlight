package cn.leolezury.eternalstarlight.common.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@FunctionalInterface
public interface Easing {
	float calculate(float f);

	default float interpolate(float f, float from, float to) {
		return from + calculate(f) * (to - from);
	}

	Map<String, Easing> EASINGS = new LinkedHashMap<>();

	static void register(String name, Easing easing) {
		EASINGS.put(name, easing);
	}

	static Optional<Easing> byName(String name) {
		return Optional.ofNullable(EASINGS.get(name));
	}

	Codec<Easing> CODEC = Codec.STRING.xmap(
		name -> EASINGS.getOrDefault(name, v -> v),
		easing -> EASINGS.entrySet().stream()
			.filter(e -> e.getValue() == easing)
			.map(Map.Entry::getKey)
			.findFirst()
			.orElse("identity")
	);

	StreamCodec<ByteBuf, Easing> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(
		name -> EASINGS.getOrDefault(name, v -> v),
		easing -> EASINGS.entrySet().stream()
			.filter(e -> e.getValue() == easing)
			.map(Map.Entry::getKey)
			.findFirst()
			.orElse("identity")
	);

	Easing IDENTITY = registerEasing("identity", v -> v);

	Easing IN_SINE = registerEasing("in_sine",
		x -> (float) (1 - Math.cos((x * Math.PI) / 2)));

	Easing OUT_SINE = registerEasing("out_sine",
		x -> (float) (Math.sin((x * Math.PI) / 2)));

	Easing IN_OUT_SINE = registerEasing("in_out_sine",
		x -> (float) (-(Math.cos(Math.PI * x) - 1) / 2));

	Easing IN_QUAD = registerEasing("in_quad",
		x -> x * x);

	Easing OUT_QUAD = registerEasing("out_quad",
		x -> 1 - (1 - x) * (1 - x));

	Easing IN_OUT_QUAD = registerEasing("in_out_quad",
		x -> (float) (x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2));

	Easing IN_CUBIC = registerEasing("in_cubic",
		x -> x * x * x);

	Easing OUT_CUBIC = registerEasing("out_cubic",
		x -> (float) (1 - Math.pow(1 - x, 3)));

	Easing IN_OUT_CUBIC = registerEasing("in_out_cubic",
		x -> (float) (x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2));

	Easing IN_QUART = registerEasing("in_quart",
		x -> x * x * x * x);

	Easing OUT_QUART = registerEasing("out_quart",
		x -> (float) (1 - Math.pow(1 - x, 4)));

	Easing IN_OUT_QUART = registerEasing("in_out_quart",
		x -> (float) (x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2));

	Easing IN_QUINT = registerEasing("in_quint",
		x -> x * x * x * x * x);

	Easing OUT_QUINT = registerEasing("out_quint",
		x -> (float) (1 - Math.pow(1 - x, 5)));

	Easing IN_OUT_QUINT = registerEasing("in_out_quint",
		x -> (float) (x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2));

	Easing IN_EXPO = registerEasing("in_expo",
		x -> (float) (x == 0 ? 0 : Math.pow(2, 10 * x - 10)));

	Easing OUT_EXPO = registerEasing("out_expo",
		x -> (float) (x == 1 ? 1 : 1 - Math.pow(2, -10 * x)));

	Easing IN_OUT_EXPO = registerEasing("in_out_expo",
		x -> (float) (x == 0
		? 0
		: x == 1
		? 1
		: x < 0.5 ? Math.pow(2, 20 * x - 10) / 2
		  : (2 - Math.pow(2, -20 * x + 10)) / 2));

	Easing IN_CIRC = registerEasing("in_circ",
		x -> (float) (1 - Math.sqrt(1 - Math.pow(x, 2))));

	Easing OUT_CIRC = registerEasing("out_circ",
		x -> (float) (Math.sqrt(1 - Math.pow(x - 1, 2))));

	Easing IN_OUT_CIRC = registerEasing("in_out_circ",
		x -> (float) (x < 0.5
		? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2
			: (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2));

	Easing IN_BACK = registerEasing("in_back",
		x -> {
		float c1 = 1.70158f;
		float c3 = c1 + 1;
		return c3 * x * x * x - c1 * x * x;
		});

	Easing OUT_BACK = registerEasing("out_back",
		x -> {
		float c1 = 1.70158f;
		float c3 = c1 + 1;
		return (float) (1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2));
		});

	Easing IN_OUT_BACK = registerEasing("in_out_back",
		x -> {
		float c1 = 1.70158f;
		float c2 = c1 * 1.525f;
		return (float) (x < 0.5
			? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
			: (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2);
		});

	Easing IN_ELASTIC = registerEasing("in_elastic",
		x -> {
		float c4 = (float) ((2f * Math.PI) / 3f);
		return x == 0
			? 0
			: (float) (x == 1
			? 1
			: -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4));
		});

	Easing OUT_ELASTIC = registerEasing("out_elastic",
		x -> {
		float c4 = (float) ((2 * Math.PI) / 3);

		return x == 0
			? 0
			: (float) (x == 1
			? 1
			: Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1);
		});

	Easing IN_OUT_ELASTIC = registerEasing("in_out_elastic",
		x -> {
		float c5 = (float) ((2 * Math.PI) / 4.5);
		double v = Math.sin((20 * x - 11.125) * c5);
		return x == 0
			? 0
			: (float) (x == 1
			? 1
			: x < 0.5
			? -(Math.pow(2, 20 * x - 10) * v) / 2
			: (Math.pow(2, -20 * x + 10) * v) / 2 + 1);
		});

	Easing OUT_BOUNCE = registerEasing("out_bounce",
		x -> {
		float n1 = 7.5625f;
		float d1 = 2.75f;

		if (x < 1 / d1) {
			return n1 * x * x;
		} else if (x < 2 / d1) {
			return n1 * (x -= (float) (1.5 / d1)) * x + 0.75f;
		} else if (x < 2.5 / d1) {
			return n1 * (x -= (float) (2.25 / d1)) * x + 0.9375f;
		} else {
			return n1 * (x -= (float) (2.625 / d1)) * x + 0.984375f;
		}
		});

	Easing IN_BOUNCE = registerEasing("in_bounce",
		x -> 1 - OUT_BOUNCE.calculate(1 - x));

	Easing IN_OUT_BOUNCE = registerEasing("in_out_bounce",
		x -> x < 0.5
		? (1 - OUT_BOUNCE.calculate(1 - 2 * x)) / 2
			: (1 + OUT_BOUNCE.calculate(2 * x - 1)) / 2);

	private static Easing registerEasing(String name, Easing easing) {
		register(name, easing);
		return easing;
	}
}
