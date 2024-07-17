package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public interface ParticleOperator {
	Vec3 getSpeed();

	void setSpeed(Vec3 speed);

	float getQuadSize();

	void setQuadSize(float quadSize);

	float getAge();

	void setAge(int age);

	int getLifetime();

	void setLifetime(int lifetime);

	Vector4f getColor();

	void setColor(Vector4f color);

	void remove();
}
