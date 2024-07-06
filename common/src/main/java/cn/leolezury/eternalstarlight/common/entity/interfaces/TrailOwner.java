package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector4f;

public interface TrailOwner {
	TrailEffect newTrail();

	void updateTrail(TrailEffect effect);

	@Environment(EnvType.CLIENT)
	default TrailEffect.TrailPoint adjustPoint(TrailEffect.TrailPoint point, boolean vertical, float partialTicks) {
		return point;
	}

	Vector4f getTrailColor();

	default boolean isTrailFullBright() {
		return false;
	}

	default boolean shouldRenderHorizontal() {
		return true;
	}

	@Environment(EnvType.CLIENT)
	RenderType getTrailRenderType();
}
