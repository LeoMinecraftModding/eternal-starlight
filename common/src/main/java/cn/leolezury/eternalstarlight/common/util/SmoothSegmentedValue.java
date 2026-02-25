package cn.leolezury.eternalstarlight.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SmoothSegmentedValue(List<Segment> segments) {
	public static SmoothSegmentedValue of(Easing easing, float from, float to, float size) {
		return new SmoothSegmentedValue(List.of(new Segment(easing, from, to, size)));
	}

	public static SmoothSegmentedValue constant(float value) {
		Segment seg = new Segment(v -> v, value, value, 1f);
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
	}
}
