package cn.leolezury.eternalstarlight.common.client.trail;

import net.minecraft.util.Mth;
import org.joml.Vector4f;

import java.util.function.Function;

public final class TrailStyles {
	public static final float HEAD_FRACTION = 1f / 6f;

	public static final Function<Float, Float> TAPER = progress -> Mth.lerp(Mth.clamp(progress, 0f, 1f), 1f / 16f, 1f);
	public static final Function<Float, Float> SOLAR_TAPER = progress -> {
		float p = Mth.clamp(progress, 0f, 1f);
		return p < 0.75f
			? Mth.lerp(p / 0.75f, 1f / 32f, 1f)
			: Mth.lerp((p - 0.75f) / 0.25f, 1f, 1f / 16f);
	};

	public static final Function<Float, Vector4f> ENERGY = gradient(0x47ADC4, 0x24D6DC, 0x00FFF4, 0xC1FFFD);
	public static final Function<Float, Vector4f> FLARE = gradient(0xDE70FF, 0xFF93DD, 0xFFCC72, 0xFFFF74);
	public static final Function<Float, Vector4f> SOUL = gradient(0x5A6DA5, 0x7999C4, 0x99BBE5, 0xC2E5FC);
	public static final Function<Float, Vector4f> ETHER = gradient(0xD1FFE1, 0xDAFFF1, 0xEDFFFF, 0xFFFFFF);
	public static final Function<Float, Vector4f> PARRY = gradient(0xBA6156, 0xF2A65E, 0xFFB570, 0xFFFFFF);
	public static final Function<Float, Vector4f> SPACE_MATTER = gradient(0x000000, 0x722A03, 0xE55406, 0xFFB93E);
	public static final Function<Float, Vector4f> PLANET = gradient(0x48B9F7, 0x85D0FA, 0xC2E8FC, 0xFFFFFF);

	public static Function<Float, Vector4f> gradient(int... bands) {
		Vector4f[] colors = new Vector4f[bands.length];
		for (int i = 0; i < bands.length; i++) {
			colors[i] = new Vector4f(
				((bands[i] >> 16) & 0xFF) / 255f,
				((bands[i] >> 8) & 0xFF) / 255f,
				(bands[i] & 0xFF) / 255f,
				1f
			);
		}
		return progress -> {
			float p = Mth.clamp(progress, 0f, 1f);
			if (p >= 1f - HEAD_FRACTION) {
				return new Vector4f(colors[colors.length - 1]);
			}
			float scaled = p / (1f - HEAD_FRACTION) * (colors.length - 1);
			int index = Mth.clamp((int) scaled, 0, colors.length - 2);
			return new Vector4f(colors[index]).lerp(colors[index + 1], scaled - index);
		};
	}

	// for trails whose color comes from their particle options
	public static Function<Float, Vector4f> tint(Function<Float, Vector4f> gradient, Vector4f tint) {
		return progress -> {
			Vector4f color = gradient.apply(progress);
			return new Vector4f(color.x() * tint.x(), color.y() * tint.y(), color.z() * tint.z(), color.w() * tint.w());
		};
	}
}
