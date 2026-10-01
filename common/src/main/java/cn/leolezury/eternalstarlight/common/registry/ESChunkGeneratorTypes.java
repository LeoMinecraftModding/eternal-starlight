package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class ESChunkGeneratorTypes {
	public static final RegistrationProvider<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR_TYPES = RegistrationProvider.get(Registries.CHUNK_GENERATOR, EternalStarlight.ID);
	public static final RegistryObject<MapCodec<? extends ChunkGenerator>, MapCodec<ESChunkGenerator>> BIOME_BASED = CHUNK_GENERATOR_TYPES.register("biome_based", () -> ESChunkGenerator.CODEC);

	public static void loadClass() {
	}
}
