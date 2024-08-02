package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VfxRegistry {
	private static final Map<ResourceLocation, SyncedVfxType> EFFECTS = new HashMap<>();

	public static final SyncedVfxType SCREEN_SHAKE = register("screen_shake", new ScreenShakeVfx());

	private static SyncedVfxType register(String location, SyncedVfxType type) {
		return register(EternalStarlight.id(location), type);
	}

	public static SyncedVfxType register(ResourceLocation location, SyncedVfxType type) {
		EFFECTS.put(location, type);
		return type;
	}

	public static Optional<SyncedVfxType> get(ResourceLocation location) {
		return Optional.ofNullable(EFFECTS.get(location));
	}

	public static ResourceLocation getKey(SyncedVfxType type) {
		for (Map.Entry<ResourceLocation, SyncedVfxType> entry : EFFECTS.entrySet()) {
			if (entry.getValue() == type) {
				return entry.getKey();
			}
		}
		return ResourceLocation.withDefaultNamespace("unregistered");
	}
}
