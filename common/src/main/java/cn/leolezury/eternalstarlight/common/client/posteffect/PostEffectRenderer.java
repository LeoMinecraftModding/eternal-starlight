package cn.leolezury.eternalstarlight.common.client.posteffect;

import cn.leolezury.eternalstarlight.common.posteffect.PostEffectInstance;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectType;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.*;

public class PostEffectRenderer {
	private static final Map<ResourceLocation, PostEffectChain> CHAINS = new HashMap<>();
	private static RenderTarget depthSnapshot;

	private static ClientLevel currentLevel;

	public static void render(ClientLevel level, Camera camera, Matrix4f viewMatrix, Matrix4f projectionMatrix, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		if (level == null || minecraft.player == null) {
			clearAll();
			return;
		}
		if (level != currentLevel) {
			clearAll();
			currentLevel = level;
		}
		if (WorldPostEffectManager.isEmpty()) {
			releaseChains();
			return;
		}

		Map<PostEffectType<?>, List<PostEffectInstance>> grouped = new LinkedHashMap<>();
		for (PostEffectInstance instance : WorldPostEffectManager.getActiveEffects()) {
			grouped.computeIfAbsent(instance.getType(), type -> new ArrayList<>()).add(instance);
		}
		Set<ResourceLocation> seenTypes = new HashSet<>();
		for (Map.Entry<PostEffectType<?>, List<PostEffectInstance>> entry : grouped.entrySet()) {
			PostEffectType<?> type = entry.getKey();
			seenTypes.add(type.id());
			PostEffectChain chain = CHAINS.computeIfAbsent(type.id(), id -> new PostEffectChain(type));
			chain.render(entry.getValue(), level, camera, viewMatrix, projectionMatrix, depthSnapshot, partialTicks);
		}

		CHAINS.entrySet().removeIf(entry -> {
			if (!seenTypes.contains(entry.getKey())) {
				entry.getValue().release();
				return true;
			}
			return false;
		});

		// restore the true level depth so post-processing from other mods running after us still sees it (our chains flatten the depth of the main target on their final copy pass)
		if (depthSnapshot != null) {
			minecraft.getMainRenderTarget().copyDepthFrom(depthSnapshot);
			minecraft.getMainRenderTarget().bindWrite(true);
		}
	}

	public static void captureDepth() {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}
		RenderTarget mainRenderTarget = minecraft.getMainRenderTarget();
		if (depthSnapshot == null || depthSnapshot.width != mainRenderTarget.width || depthSnapshot.height != mainRenderTarget.height) {
			if (depthSnapshot != null) {
				depthSnapshot.destroyBuffers();
			}
			depthSnapshot = new TextureTarget(mainRenderTarget.width, mainRenderTarget.height, true, Minecraft.ON_OSX);
		}
		depthSnapshot.copyDepthFrom(mainRenderTarget);
		// copyDepthFrom leaves the window backbuffer (framebuffer 0) bound, restore the main render target for the rest of the frame
		mainRenderTarget.bindWrite(true);
	}

	private static void releaseChains() {
		for (PostEffectChain chain : CHAINS.values()) {
			chain.release();
		}
		CHAINS.clear();
	}

	public static void reload() {
		releaseChains();
	}

	private static void clearAll() {
		releaseChains();
		if (depthSnapshot != null) {
			depthSnapshot.destroyBuffers();
			depthSnapshot = null;
		}
		WorldPostEffectManager.clear();
		currentLevel = null;
	}
}
