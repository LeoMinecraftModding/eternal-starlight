package cn.leolezury.eternalstarlight.common.client.posteffect;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.*;

public class PostEffectRenderer {
	private static final Map<ResourceLocation, PostEffectChain> CHAINS = new HashMap<>();

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
			chain.render(entry.getValue(), level, camera, viewMatrix, projectionMatrix, partialTicks);
		}

		CHAINS.entrySet().removeIf(entry -> {
			if (!seenTypes.contains(entry.getKey())) {
				entry.getValue().release();
				return true;
			}
			return false;
		});
	}

	private static void releaseChains() {
		for (PostEffectChain chain : CHAINS.values()) {
			chain.release();
		}
		CHAINS.clear();
	}

	private static void clearAll() {
		releaseChains();
		WorldPostEffectManager.clear();
		currentLevel = null;
	}
}
