package cn.leolezury.eternalstarlight.common.posteffect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESCodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;

import java.util.List;

public class FlashPostEffect extends PostEffectType<FlashPostEffect.Data> {
	public static final int DEFAULT_FADE_IN = 5;
	public static final int DEFAULT_FADE_OUT = 10;
	public static final int DEFAULT_COLOR = 0xFFFFFF;

	public record Data(int fadeIn, int fadeOut, int color) implements PostEffectData {
		public static final MapCodec<Data> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.INT.optionalFieldOf("fade_in", DEFAULT_FADE_IN).forGetter(Data::fadeIn),
			Codec.INT.optionalFieldOf("fade_out", DEFAULT_FADE_OUT).forGetter(Data::fadeOut),
			ESCodecUtil.RGB_COLOR_CODEC.optionalFieldOf("color", DEFAULT_COLOR).forGetter(Data::color)
		).apply(builder, Data::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, Data::fadeIn,
			ByteBufCodecs.INT, Data::fadeOut,
			ByteBufCodecs.INT, Data::color,
			Data::new
		);

		@Override
		public PostEffectType<Data> type() {
			return FlashPostEffect.INSTANCE;
		}
	}

	public static final String UNIFORM_PARAMS = "uFlashParams";
	public static final int PARAM_COUNT = 5;

	private static final float[] PARAMS = new float[MAX_EFFECTS * PARAM_COUNT];

	public static final FlashPostEffect INSTANCE = new FlashPostEffect();

	public FlashPostEffect() {
		super(EternalStarlight.id("flash"), Data.CODEC, Data.STREAM_CODEC);
	}

	@Override
	protected void uploadTypeUniforms(EffectInstance effect, List<PostEffectInstance> instances, float partialTicks) {
		int count = Math.min(instances.size(), MAX_EFFECTS);
		for (int i = 0; i < count; i++) {
			Data data = getData(instances.get(i));
			PARAMS[i * PARAM_COUNT] = data.fadeIn();
			PARAMS[i * PARAM_COUNT + 1] = data.fadeOut();
			PARAMS[i * PARAM_COUNT + 2] = FastColor.ARGB32.red(data.color()) / 255F;
			PARAMS[i * PARAM_COUNT + 3] = FastColor.ARGB32.green(data.color()) / 255F;
			PARAMS[i * PARAM_COUNT + 4] = FastColor.ARGB32.blue(data.color()) / 255F;
		}
		setUniformArray(effect, UNIFORM_PARAMS, PARAMS);
	}
}
