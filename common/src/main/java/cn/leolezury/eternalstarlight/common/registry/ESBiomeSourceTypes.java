package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;

public class ESBiomeSourceTypes {
	public static final RegistrationProvider<MapCodec<? extends BiomeSource>> BIOME_SOURCE_TYPES = RegistrationProvider.get(Registries.BIOME_SOURCE, EternalStarlight.ID);
	public static final RegistryObject<MapCodec<? extends BiomeSource>, MapCodec<ESBiomeSource>> MULTI_NOISE = BIOME_SOURCE_TYPES.register("multi_noise", () -> ESBiomeSource.CODEC);

	public static void loadClass() {
	}
}
