package cn.leolezury.eternalstarlight.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SmoothSegmentedValue(List<Segment> segments) {
	public static SmoothSegmentedValue of(Easing easing, float from, float to, float size) {
		return new SmoothSegmentedValue(List.of(new Segment(easing, from, to, size)));
	}

	public static SmoothSegmentedValue constant(float value) {
		Segment seg = new Segment(Easing.IDENTITY, value, value, 1f);
		return new SmoothSegmentedValue(Collections.singletonList(seg));
	}

	public SmoothSegmentedValue add(Easing easing, float from, float to, float size) {
		List<Segment> newSegments = new ArrayList<>(segments);
		newSegments.add(new Segment(easing, from, to, size));
		return new SmoothSegmentedValue(Collections.unmodifiableList(newSegments));
	}

	public float calculate(float progress) {
		if (segments.isEmpty()) {
			return 0f;
		}
		float accumulated = 0f;
		for (Segment seg : segments) {
			float size = seg.size();
			if (size <= 0) {
				continue;
			}
			float nextAccumulated = accumulated + size;
			if (progress <= nextAccumulated) {
				float t = (progress - accumulated) / size;
				return seg.easing().interpolate(t, seg.from(), seg.to());
			}
			accumulated = nextAccumulated;
		}
		return segments.getLast().to();
	}

	public record Segment(Easing easing, float from, float to, float size) {
		public Segment {
			if (size <= 0) {
				throw new IllegalArgumentException("Segment size must be positive");
			}
		}

		public static final Codec<Segment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Easing.CODEC.fieldOf("easing").forGetter(Segment::easing),
			Codec.FLOAT.fieldOf("from").forGetter(Segment::from),
			Codec.FLOAT.fieldOf("to").forGetter(Segment::to),
			Codec.FLOAT.fieldOf("size").forGetter(Segment::size)
		).apply(instance, Segment::new));

		public static final StreamCodec<ByteBuf, Segment> STREAM_CODEC = StreamCodec.composite(
			Easing.STREAM_CODEC, Segment::easing,
			ByteBufCodecs.FLOAT, Segment::from,
			ByteBufCodecs.FLOAT, Segment::to,
			ByteBufCodecs.FLOAT, Segment::size,
			Segment::new
		);
	}

	public static final Codec<SmoothSegmentedValue> CODEC = Segment.CODEC.listOf().xmap(
		SmoothSegmentedValue::new,
		SmoothSegmentedValue::segments
	);

	public static final StreamCodec<ByteBuf, SmoothSegmentedValue> STREAM_CODEC = Segment.STREAM_CODEC.apply(ByteBufCodecs.list()).map(
		SmoothSegmentedValue::new,
		SmoothSegmentedValue::segments
	);
}
