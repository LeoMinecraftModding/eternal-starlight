package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import org.joml.Vector4f;

public interface TrailOwner {
	TrailEffect newTrail();

	void updateTrail(TrailEffect effect);

	Vector4f getTrailColor();

	default boolean isTrailFullBright() {
		return false;
	}

	default boolean isTrailSolid() {
		return false;
	}

	default TrailEffect.TrailOffsetFunction getTrailOffsetFunction() {
		return TrailEffect.TrailOffsetFunction.FACE_CAMERA;
	}
}
