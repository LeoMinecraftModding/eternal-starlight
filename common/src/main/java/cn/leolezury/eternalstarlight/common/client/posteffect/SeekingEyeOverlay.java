package cn.leolezury.eternalstarlight.common.client.posteffect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SeekingEyeOverlay {
	private static final ResourceLocation CHAIN_LOCATION = EternalStarlight.id("shaders/post/seeking_eye.json");
	private static final int SHADER_RETRY_TICKS = 20;

	private static PostChain chain;
	private static int chainWidth = -1;
	private static int chainHeight = -1;
	private static long nextEffectLoadTick = Long.MIN_VALUE;

	public static void render(float[] starPositions, float[] starRadii, float[] starColors, float[] starAlphas, float[] starPhases, float[] starRotations, float[] starRayCounts, float[] starRayLengths, int starCount, int hoveredIndex, float fade, float time, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		PostChain postChain = loadPostEffect(minecraft);
		if (postChain == null) {
			return;
		}
		List<PostPass> passes = postChain.passes;
		if (passes.isEmpty()) {
			release();
			return;
		}
		EffectInstance effect = passes.getFirst().getEffect();
		setUniform(effect, "uTime", time);
		setUniform(effect, "uFade", fade);
		setUniform(effect, "uStarCount", starCount);
		setUniform(effect, "uHoverIndex", hoveredIndex);
		setUniformArray(effect, "uStarPositions", starPositions);
		setUniformArray(effect, "uStarRadii", starRadii);
		setUniformArray(effect, "uStarColors", starColors);
		setUniformArray(effect, "uStarAlphas", starAlphas);
		setUniformArray(effect, "uStarPhases", starPhases);
		setUniformArray(effect, "uStarRotations", starRotations);
		setUniformArray(effect, "uStarRayCounts", starRayCounts);
		setUniformArray(effect, "uStarRayLengths", starRayLengths);

		postChain.process(partialTicks);
		// the final pass leaves the window backbuffer (framebuffer 0) bound, restore the main render target for the rest of the GUI
		minecraft.getMainRenderTarget().bindWrite(true);
	}

	private static PostChain loadPostEffect(Minecraft minecraft) {
		if (chain == null) {
			if (minecraft.level == null || minecraft.level.getGameTime() < nextEffectLoadTick) {
				return null;
			}
			try {
				chain = new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getMainRenderTarget(), CHAIN_LOCATION);
				chainWidth = -1;
				chainHeight = -1;
			} catch (Exception e) {
				EternalStarlight.LOGGER.error("Failed to load seeking eye post chain {}", CHAIN_LOCATION, e);
				release();
				nextEffectLoadTick = minecraft.level.getGameTime() + SHADER_RETRY_TICKS;
				return null;
			}
		}
		RenderTarget mainRenderTarget = minecraft.getMainRenderTarget();
		if (mainRenderTarget.width != chainWidth || mainRenderTarget.height != chainHeight) {
			chain.resize(mainRenderTarget.width, mainRenderTarget.height);
			chainWidth = mainRenderTarget.width;
			chainHeight = mainRenderTarget.height;
		}
		nextEffectLoadTick = Long.MIN_VALUE;
		return chain;
	}

	public static void release() {
		if (chain != null) {
			chain.close();
			chain = null;
		}
		chainWidth = -1;
		chainHeight = -1;
		// PostChain.close() destroys its render targets, and RenderTarget.destroyBuffers() leaves the window backbuffer (framebuffer 0) bound
		Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
	}

	private static void setUniform(EffectInstance effect, String name, int value) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(value);
		}
	}

	private static void setUniform(EffectInstance effect, String name, float value) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(value);
		}
	}

	private static void setUniformArray(EffectInstance effect, String name, float[] values) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(values);
		}
	}
}
