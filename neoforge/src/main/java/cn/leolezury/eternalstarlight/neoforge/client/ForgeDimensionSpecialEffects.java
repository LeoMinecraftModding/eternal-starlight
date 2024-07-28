package cn.leolezury.eternalstarlight.neoforge.client;

import cn.leolezury.eternalstarlight.common.client.ESDimensionSpecialEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;

public class ForgeDimensionSpecialEffects extends ESDimensionSpecialEffects {
	public ForgeDimensionSpecialEffects(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
		super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
	}

	@Override
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
		return ESDimensionSpecialEffects.doRenderSky(level, partialTick, modelViewMatrix, camera, projectionMatrix, isFoggy, setupFog);
	}

	@Override
	public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
		return ESDimensionSpecialEffects.doRenderWeather(level, ticks, partialTick, lightTexture, camX, camY, camZ);
	}
}
