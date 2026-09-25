package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ESNoises {
	public static final ResourceKey<NormalNoise.NoiseParameters> BIOME_HEIGHT = create("biome_height");
	public static final ResourceKey<NormalNoise.NoiseParameters> RIVER = create("river");
	public static final ResourceKey<NormalNoise.NoiseParameters> RIVER_SHIFT = create("river_shift");

	public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
		// five octaves, down to a sixteen block wavelength. The height field needs detail at that scale: with only the
		// two lowest octaves every biome reads as a flat patch, and the climate table's parameter ranges then show up in
		// the world as flat quadrilaterals with rounded corners.
		register(context, BIOME_HEIGHT, -8, 1.0, 0.5, 0.25, 0.125, 0.0625);
		register(context, RIVER, -10, 1.0, 0.5);
		// the shift noise warps the river field by roughly 80 blocks
		register(context, RIVER_SHIFT, -7, 20.0);
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
