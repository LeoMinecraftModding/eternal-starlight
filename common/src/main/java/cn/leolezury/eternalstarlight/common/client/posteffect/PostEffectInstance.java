package cn.leolezury.eternalstarlight.common.client.posteffect;

import net.minecraft.world.phys.Vec3;

public class PostEffectInstance {
	private final PostEffectType<?> type;
	private final PostEffectData data;
	private final Vec3 position;
	private final float duration;
	private final float radius;
	private final float intensity;
	private float age;

	public PostEffectInstance(PostEffectType<?> type, PostEffectData data, Vec3 position, float duration, float radius, float intensity) {
		this.type = type;
		this.data = data;
		this.position = position;
		this.duration = duration;
		this.radius = radius;
		this.intensity = intensity;
	}

	public PostEffectType<?> getType() {
		return type;
	}

	public PostEffectData getData() {
		return data;
	}

	public Vec3 getPosition() {
		return position;
	}

	public float getDuration() {
		return duration;
	}

	public float getRadius() {
		return radius;
	}

	public float getIntensity() {
		return intensity;
	}

	public float getAge() {
		return age;
	}

	public void tick() {
		age++;
	}

	public boolean shouldRemove() {
		return age >= duration;
	}
}
