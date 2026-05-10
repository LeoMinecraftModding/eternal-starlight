package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ESNoises {
	public static final ResourceKey<NormalNoise.NoiseParameters> DEPTH_OFFSET = create("depth_offset");

	public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
		register(context, DEPTH_OFFSET, -5, 1.0);
	}

	private static void register(
		BootstrapContext<NormalNoise.NoiseParameters> context,
		ResourceKey<NormalNoise.NoiseParameters> key,
		int firstOctave,
		double amplitude,
		double... otherAmplitudes
	) {
		context.register(key, new NormalNoise.NoiseParameters(firstOctave, amplitude, otherAmplitudes));
	}

	private static ResourceKey<NormalNoise.NoiseParameters> create(String key) {
		return ResourceKey.create(Registries.NOISE, EternalStarlight.id(key));
	}
}
