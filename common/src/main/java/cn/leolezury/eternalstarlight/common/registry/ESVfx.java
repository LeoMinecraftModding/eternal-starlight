package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import cn.leolezury.eternalstarlight.common.vfx.VfxType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ESVfx {
	public static final ResourceKey<Registry<VfxType<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(EternalStarlight.id("vfx"));
	public static final RegistrationProvider<VfxType<?>> VFX = RegistrationProvider.newRegistry(REGISTRY_KEY, EternalStarlight.ID);
	public static final RegistryObject<VfxType<?>, ScreenShakeVfx> SCREEN_SHAKE = VFX.register("screen_shake", () -> ScreenShakeVfx.INSTANCE);

	public static VfxType<?> get(ResourceLocation id) {
		return VFX.registry().get(id);
	}

	public static void loadClass() {
	}
}
