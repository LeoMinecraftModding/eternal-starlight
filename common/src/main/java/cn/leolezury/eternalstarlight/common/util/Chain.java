package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Chain(List<Segment> segments) {
	public Chain(Vec3 position, int numSegments, float segmentLength) {
		this(new ArrayList<>());
		for (int i = 0; i < numSegments; i++) {
			segments().add(new Segment(position.add(0, numSegments - i - 1, 0), segmentLength));
		}
	}

	public void update(Vec3 targetPos, Vec3 headRotationTarget, Vec3 attachPos, float rotationSpeed, float headRotationSpeed) {
		Vec3 rotationTarget = Vec3.ZERO;
		Vec3 lastLowerPos = targetPos;
		for (int i = 0; i < segments().size(); i++) {
			Segment segment = segments().get(i);
			if (i == 0) {
				segment.rotateTowards(headRotationTarget, headRotationSpeed);
			} else {
				segment.rotateTowards(rotationTarget, rotationSpeed);
			}
			segment.setUpperPosition(lastLowerPos);
			rotationTarget = segment.getUpperPosition();
			lastLowerPos = segment.getLowerPosition();
		}
		if (attachPos != null) {
			Vec3 offset = attachPos.subtract(lastLowerPos);
			for (Segment segment : segments()) {
				segment.setLowerPosition(segment.getLowerPosition().add(offset));
			}
		}
	}

	public Optional<Vec3> getEndPos() {
		return segments().isEmpty() ? Optional.empty() : Optional.of(segments().getFirst().getUpperPosition());
	}

	public void save(CompoundTag tag) {
		tag.putInt("SegmentsSize", segments().size());
		for (int i = 0; i < segments().size(); i++) {
			Segment segment = segments().get(i);
			CompoundTag segmentTag = new CompoundTag();
			segment.save(segmentTag);
			tag.put("Segment" + i, segmentTag);
		}
	}

	public static Chain load(CompoundTag tag) {
		List<Segment> segments = new ArrayList<>();
		int segmentsSize = tag.getInt("SegmentsSize");
		for (int i = 0; i < segmentsSize; i++) {
			if (tag.contains("Segment" + i, CompoundTag.TAG_COMPOUND)) {
				CompoundTag segmentTag = tag.getCompound("Segment" + i);
				Segment segment = Segment.load(segmentTag);
				segments.add(segment);
			}
		}
		return new Chain(segments);
	}

	public static class Segment {
		private final float length;

		public float getLength() {
			return length;
		}

		private Vec3 position;

		public Vec3 getLowerPosition() {
			return position;
		}

		public Vec3 getMiddlePosition() {
			return ESMathUtil.rotationToPosition(position, length / 2f, pitch, yaw);
		}

		public Vec3 getUpperPosition() {
			return ESMathUtil.rotationToPosition(position, length, pitch, yaw);
		}

		public void setLowerPosition(Vec3 position) {
			this.position = position;
		}

		public void setUpperPosition(Vec3 position) {
			this.position = position.add(getLowerPosition()).subtract(getUpperPosition());
		}

		private float pitch, yaw;

		public float getPitch() {
			return pitch;
		}

		public void setPitch(float pitch) {
			this.pitch = pitch;
		}

		public float getYaw() {
			return yaw;
		}

		public void setYaw(float yaw) {
			this.yaw = yaw;
		}

		public Segment(Vec3 position, float length) {
			this.position = position;
			this.length = length;
		}

		public Segment(Vec3 position, float length, float pitch, float yaw) {
			this(position, length);
			this.pitch = pitch;
			this.yaw = yaw;
		}

		public void rotateTowards(Vec3 target, float maxSpeed) {
			float wantedPitch = ESMathUtil.positionToPitch(position, target);
			float wantedYaw = ESMathUtil.positionToYaw(position, target);
			setPitch(Mth.approachDegrees(pitch, wantedPitch, maxSpeed));
			setYaw(Mth.approachDegrees(yaw, wantedYaw, maxSpeed));
		}

		public void save(CompoundTag tag) {
			tag.putFloat("X", (float) getLowerPosition().x);
			tag.putFloat("Y", (float) getLowerPosition().y);
			tag.putFloat("Z", (float) getLowerPosition().z);
			tag.putFloat("Length", getLength());
			tag.putFloat("Pitch", getPitch());
			tag.putFloat("Yaw", getYaw());
		}

		public static Segment load(CompoundTag tag) {
			Vec3 pos = new Vec3(tag.getFloat("X"), tag.getFloat("Y"), tag.getFloat("Z"));
			float length = tag.getFloat("Length");
			float pitch = tag.getFloat("Pitch");
			float yaw = tag.getFloat("Yaw");
			return new Segment(pos, length, pitch, yaw);
		}
	}
}
