package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public interface TrailOwner {
	TrailEffect createNewTrail();

	void updateTrail(TrailEffect effect);

	default Vec3 getSmoothTrailPosition(Vec3 smoothPos) {
		return this instanceof Entity entity ? smoothPos.add(0, entity.getBbHeight() / 2, 0) : smoothPos;
	}

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
