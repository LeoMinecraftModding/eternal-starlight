package cn.leolezury.eternalstarlight.common.client.posteffect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class ShockwavePostEffect extends PostEffectType<ShockwavePostEffect.Data> {
	public static final float DEFAULT_FREQUENCY = 1.0F;
	public static final float DEFAULT_FALLOFF = 1.5F;
	public static final float DEFAULT_THICKNESS = 3.0F;

	public record Data(float frequency, float falloff, float thickness) implements PostEffectData {
		public static final MapCodec<Data> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.FLOAT.optionalFieldOf("frequency", DEFAULT_FREQUENCY).forGetter(Data::frequency),
			Codec.FLOAT.optionalFieldOf("falloff", DEFAULT_FALLOFF).forGetter(Data::falloff),
			Codec.FLOAT.optionalFieldOf("thickness", DEFAULT_THICKNESS).forGetter(Data::thickness)
		).apply(builder, Data::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT, Data::frequency,
			ByteBufCodecs.FLOAT, Data::falloff,
			ByteBufCodecs.FLOAT, Data::thickness,
			Data::new
		);

		@Override
		public PostEffectType<Data> type() {
			return ShockwavePostEffect.INSTANCE;
		}
	}

	public static final String UNIFORM_PARAMS = "uShockwaveParams";
	public static final int PARAM_COUNT = 4;

	private static final float[] PARAMS = new float[MAX_EFFECTS * PARAM_COUNT];

	public static final ShockwavePostEffect INSTANCE = new ShockwavePostEffect();

	public ShockwavePostEffect() {
		super(EternalStarlight.id("shockwave"), Data.CODEC, Data.STREAM_CODEC);
	}

	@Override
	protected void uploadTypeUniforms(EffectInstance effect, List<PostEffectInstance> instances, float partialTicks) {
		int count = Math.min(instances.size(), MAX_EFFECTS);
		for (int i = 0; i < count; i++) {
			Data data = getData(instances.get(i));
			PARAMS[i * PARAM_COUNT] = data.frequency();
			PARAMS[i * PARAM_COUNT + 1] = data.falloff();
			PARAMS[i * PARAM_COUNT + 2] = data.thickness();
			PARAMS[i * PARAM_COUNT + 3] = 0;
		}
		setUniformArray(effect, UNIFORM_PARAMS, PARAMS);
	}
}
