package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.visual.ScreenShake;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScreenShakeVfx extends VfxType<ScreenShakeVfx.Data> {
	public record Data(float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq, Easing fadeEasing) implements VfxData {
		public static final MapCodec<Data> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.FLOAT.fieldOf("horizontal_power").forGetter(Data::horizontalPower),
			Codec.FLOAT.fieldOf("vertical_power").forGetter(Data::verticalPower),
			Codec.FLOAT.fieldOf("horizontal_frequency").forGetter(Data::horizontalFreq),
			Codec.FLOAT.fieldOf("vertical_frequency").forGetter(Data::verticalFreq),
			Easing.CODEC.optionalFieldOf("fade_easing", Easing.IN_QUART).forGetter(Data::fadeEasing)
		).apply(builder, Data::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT, Data::horizontalPower,
			ByteBufCodecs.FLOAT, Data::verticalPower,
			ByteBufCodecs.FLOAT, Data::horizontalFreq,
			ByteBufCodecs.FLOAT, Data::verticalFreq,
			Easing.STREAM_CODEC, Data::fadeEasing,
			Data::new
		);

		@Override
		public VfxType<Data> type() {
			return INSTANCE;
		}
	}

	public static final ScreenShakeVfx INSTANCE = new ScreenShakeVfx();

	public ScreenShakeVfx() {
		super(Data.CODEC, Data.STREAM_CODEC);
	}

	@Override
	public void spawnOnClient(VfxInstance instance) {
		ESMiscUtil.runWhenOnClient(() -> () -> ESClientHandler.SCREEN_SHAKES.add(new ScreenShake(
			instance.dimension().location(),
			instance.position(),
			instance.radius(),
			instance.duration(),
			getData(instance).horizontalPower(),
			getData(instance).verticalPower(),
			getData(instance).horizontalFreq(),
			getData(instance).verticalFreq(),
			getData(instance).fadeEasing()
		)));
	}

	public static VfxInstance createInstance(ResourceKey<Level> dimension, Vec3 pos, float radius, int duration, float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq) {
		return createInstance(dimension, pos, radius, duration, horizontalPower, verticalPower, horizontalFreq, verticalFreq, Easing.IN_QUART);
	}

	public static VfxInstance createInstance(ResourceKey<Level> dimension, Vec3 pos, float radius, int duration, float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq, Easing fadeEasing) {
		return new VfxInstance(INSTANCE, dimension, pos, duration, radius, new Data(horizontalPower, verticalPower, horizontalFreq, verticalFreq, fadeEasing));
	}
}
