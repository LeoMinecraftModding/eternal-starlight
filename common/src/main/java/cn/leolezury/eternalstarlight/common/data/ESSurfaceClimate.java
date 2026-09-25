package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Climate;

import java.util.ArrayList;
import java.util.List;

/**
 * The dimension's climate table, read by both the biome source and the terrain height function.
 */
public class ESSurfaceClimate {
	public static final ResourceKey<Climate.ParameterList<Holder<BiomeData>>> STARLIGHT =
		ResourceKey.create(ESRegistries.SURFACE_CLIMATE, EternalStarlight.id("starlight"));

	public static final Codec<Climate.ParameterList<Holder<BiomeData>>> CODEC =
		Climate.ParameterList.codec(RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("biome_data"));

	public static final Codec<Holder<Climate.ParameterList<Holder<BiomeData>>>> HOLDER_CODEC =
		RegistryFileCodec.create(ESRegistries.SURFACE_CLIMATE, CODEC);

	public static void bootstrap(BootstrapContext<Climate.ParameterList<Holder<BiomeData>>> context) {
		HolderGetter<BiomeData> biomeData = context.lookup(ESRegistries.BIOME_DATA);
		List<Pair<Climate.ParameterPoint, Holder<BiomeData>>> parameters = new ArrayList<>();
		new ESBiomeBuilder().addBiomes(pair -> parameters.add(pair.mapSecond(biomeData::getOrThrow)));
		context.register(STARLIGHT, new Climate.ParameterList<>(parameters));
	}
}
