package cn.leolezury.eternalstarlight.common.util;

import java.util.ArrayList;
import java.util.List;

public record SmoothSegmentedValue(List<Segment> segments) {
	public static SmoothSegmentedValue of(Easing easing, float from, float to, float size) {
		return new SmoothSegmentedValue(new ArrayList<>(List.of(new Segment(easing, from, to, size))));
	}

	public static SmoothSegmentedValue constant(float value) {
		return new SmoothSegmentedValue(List.of(new Segment(v -> v, value, value, 1)));
	}

	public SmoothSegmentedValue add(Easing easing, float from, float to, float size) {
		segments().add(new Segment(easing, from, to, size));
		return this;
	}

	public float calculate(float progress) {
		float accumulatedSize = 0;
		for (Segment segment : segments()) {
			accumulatedSize += segment.size();
			if (accumulatedSize >= progress) {
				return segment.easing().interpolate(1 - (accumulatedSize - progress) / segment.size(), segment.from(), segment.to());
			}
		}
		return segments().isEmpty() ? 0 : segments().getLast().to();
	}

	public record Segment(Easing easing, float from, float to, float size) {

	}
}
