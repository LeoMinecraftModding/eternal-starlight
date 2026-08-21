package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.posteffect.FlashPostEffect;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectType;
import cn.leolezury.eternalstarlight.common.posteffect.ShockwavePostEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class ESPostEffects {
	public static final ResourceKey<Registry<PostEffectType<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(EternalStarlight.id("post_effect"));
	public static final RegistrationProvider<PostEffectType<?>> POST_EFFECTS = RegistrationProvider.newRegistry(REGISTRY_KEY, EternalStarlight.ID);
	public static final RegistryObject<PostEffectType<?>, ShockwavePostEffect> SHOCKWAVE = POST_EFFECTS.register("shockwave", () -> ShockwavePostEffect.INSTANCE);
	public static final RegistryObject<PostEffectType<?>, FlashPostEffect> FLASH = POST_EFFECTS.register("flash", () -> FlashPostEffect.INSTANCE);

	public static PostEffectType<?> get(ResourceLocation id) {
		return POST_EFFECTS.registry().get(id);
	}

	public static Set<ResourceLocation> keys() {
		return POST_EFFECTS.registry().keySet();
	}

	public static void loadClass() {
	}
}
