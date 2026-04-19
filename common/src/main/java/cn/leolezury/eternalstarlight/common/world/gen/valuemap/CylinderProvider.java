package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import net.minecraft.world.phys.Vec3;

public record CylinderProvider(float radius, float height) implements ValueMapProvider {
	@Override
	public float getValue(float x, float y, float z) {
		if (y < 0 || y > height()) return 1;
		Vec3 vec3 = new Vec3(x, y, z);
		if (vec3.horizontalDistance() <= radius()) {
			return (float) vec3.horizontalDistance() / radius();
		} else {
			return 1;
		}
	}
}
