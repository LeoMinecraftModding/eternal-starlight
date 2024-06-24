package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector4f;

public interface TrailOwner {
	TrailEffect newTrail();

	void updateTrail(TrailEffect effect);

	Vector4f getTrailColor();

	@Environment(EnvType.CLIENT)
	RenderType getTrailRenderType();
}
