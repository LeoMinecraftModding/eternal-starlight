package cn.leolezury.eternalstarlight.common.client.posteffect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostEffectRegistry {
	private static final Map<ResourceLocation, PostEffectType<?>> TYPES = new LinkedHashMap<>();

	public static final ShockwavePostEffect SHOCKWAVE = register(ShockwavePostEffect.INSTANCE);

	public static <T extends PostEffectType<?>> T register(T type) {
		TYPES.put(type.id(), type);
		return type;
	}

	@Nullable
	public static PostEffectType<?> get(ResourceLocation id) {
		return TYPES.get(id);
	}

	public static List<ResourceLocation> keys() {
		return List.copyOf(TYPES.keySet());
	}

	public static void spawnOnClient(ResourceLocation typeId, PostEffectData data, Vec3 position, int duration, float radius, float intensity) {
		PostEffectType<?> type = get(typeId);
		if (type != null) {
			WorldPostEffectManager.spawn(type, data, position, duration, radius, intensity);
		}
	}
}
