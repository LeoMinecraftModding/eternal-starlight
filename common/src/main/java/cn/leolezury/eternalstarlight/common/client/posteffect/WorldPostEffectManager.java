package cn.leolezury.eternalstarlight.common.client.posteffect;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class WorldPostEffectManager {
	private static final int MAX_TOTAL_EFFECTS = 256;

	private static final List<PostEffectInstance> ACTIVE_EFFECTS = new ArrayList<>();

	public static List<PostEffectInstance> getActiveEffects() {
		return ACTIVE_EFFECTS;
	}

	public static boolean isEmpty() {
		return ACTIVE_EFFECTS.isEmpty();
	}

	public static void tick() {
		Iterator<PostEffectInstance> iterator = ACTIVE_EFFECTS.iterator();
		while (iterator.hasNext()) {
			PostEffectInstance effect = iterator.next();
			effect.tick();
			if (effect.shouldRemove()) {
				iterator.remove();
			}
		}
	}

	public static void spawn(PostEffectType<?> type, PostEffectData data, Vec3 position, float duration, float radius, float intensity) {
		ACTIVE_EFFECTS.add(new PostEffectInstance(type, data, position, duration, radius, intensity));
		ACTIVE_EFFECTS.sort(Comparator.comparingDouble(WorldPostEffectManager::getPriority).reversed());
		while (ACTIVE_EFFECTS.size() > MAX_TOTAL_EFFECTS) {
			ACTIVE_EFFECTS.removeLast();
		}
	}

	private static double getPriority(PostEffectInstance effect) {
		return effect.getIntensity() * Math.max(effect.getDuration() - effect.getAge(), 0);
	}

	public static void clear() {
		ACTIVE_EFFECTS.clear();
	}
}
