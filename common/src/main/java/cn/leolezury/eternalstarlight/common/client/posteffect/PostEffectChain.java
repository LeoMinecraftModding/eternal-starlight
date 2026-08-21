package cn.leolezury.eternalstarlight.common.client.posteffect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectInstance;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectType;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.List;

public class PostEffectChain implements AutoCloseable {
	private static final int SHADER_RETRY_TICKS = 20;

	private final PostEffectType<?> type;
	private PostChain postChain;
	private int postChainWidth = -1;
	private int postChainHeight = -1;
	private long nextEffectLoadTick = Long.MIN_VALUE;

	public PostEffectChain(PostEffectType<?> type) {
		this.type = type;
	}

	public PostEffectType<?> getType() {
		return type;
	}

	public void render(List<PostEffectInstance> instances, ClientLevel level, Camera camera, Matrix4f viewMatrix, Matrix4f projectionMatrix, RenderTarget depthTarget, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		PostChain chain = loadPostEffect(minecraft, level.getGameTime());
		if (chain == null) {
			return;
		}
		EffectInstance effect = type.getPostEffect(chain);
		if (effect == null) {
			release();
			return;
		}
		effect.setSampler("DepthSampler", depthTarget::getDepthTextureId);

		Matrix4f viewProj = new Matrix4f(projectionMatrix).mul(viewMatrix);
		Matrix4f viewProjInv = new Matrix4f(viewProj).invert();
		if (!viewProjInv.isFinite()) {
			// the chain may have just been created, which leaves the window backbuffer (framebuffer 0) bound
			// restore the main render target for the hand render
			minecraft.getMainRenderTarget().bindWrite(true);
			return;
		}
		Vec3 cameraPosition = camera.getPosition();
		setUniformMat4(effect, "uViewProj", viewProj);
		setUniformMat4(effect, "uViewProjInv", viewProjInv);
		setUniform3(effect, "uCameraPosition", (float) cameraPosition.x, (float) cameraPosition.y, (float) cameraPosition.z);
		setUniform(effect, "uTime", (level.getGameTime() + partialTicks) / 20.0F);

		type.uploadUniforms(effect, instances, partialTicks);

		postChain.process(partialTicks);
		minecraft.getMainRenderTarget().bindWrite(true);
	}

	@Nullable
	private PostChain loadPostEffect(Minecraft minecraft, long gameTick) {
		if (postChain == null) {
			if (gameTick < nextEffectLoadTick) {
				return null;
			}
			try {
				postChain = new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getMainRenderTarget(), type.postChainLocation());
				postChainWidth = -1;
				postChainHeight = -1;
			} catch (Exception e) {
				EternalStarlight.LOGGER.error("Failed to load post effect chain {}", type.postChainLocation(), e);
				release();
				nextEffectLoadTick = gameTick + SHADER_RETRY_TICKS;
				return null;
			}
		}
		int width = minecraft.getMainRenderTarget().width;
		int height = minecraft.getMainRenderTarget().height;
		if (width != postChainWidth || height != postChainHeight) {
			postChain.resize(width, height);
			postChainWidth = width;
			postChainHeight = height;
		}
		nextEffectLoadTick = Long.MIN_VALUE;
		return postChain;
	}

	public void release() {
		if (postChain != null) {
			postChain.close();
			postChain = null;
		}
		postChainWidth = -1;
		postChainHeight = -1;
		// PostChain.close() destroys its render targets, and RenderTarget.destroyBuffers() leaves the window backbuffer (framebuffer 0) bound
		// we run at the AFTER_LEVEL render stage, where the vanilla pipeline still expects the main render target to be bound for the depth clear and the first-person hand render
		// otherwise the hand is drawn into the backbuffer and gets overwritten for one frame (a flash)
		Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
	}

	@Override
	public void close() {
		release();
	}

	private static void setUniform(EffectInstance effect, String name, float value) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(value);
		}
	}

	private static void setUniform3(EffectInstance effect, String name, float first, float second, float third) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(first, second, third);
		}
	}

	private static void setUniformMat4(EffectInstance effect, String name, Matrix4f matrix) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(matrix);
		}
	}
}
