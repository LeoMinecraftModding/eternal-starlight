package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.EctostoneVariant;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ESEctostoneVariants {
	public static final ResourceKey<EctostoneVariant> GRIMSTONE = create("grimstone");
	public static final ResourceKey<EctostoneVariant> RADIANITE = create("radianite");
	public static final ResourceKey<EctostoneVariant> SPRINGSTONE = create("springstone");
	public static final ResourceKey<EctostoneVariant> TOXITE = create("toxite");
	public static final ResourceKey<EctostoneVariant> VOIDSTONE = create("voidstone");

	public static void bootstrap(BootstrapContext<EctostoneVariant> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(GRIMSTONE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/grimstone"), EternalStarlight.id("entity/ectostone/grimstone_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_FOREST), biomes.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST))));
		context.register(RADIANITE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/radianite"), EternalStarlight.id("entity/ectostone/radianite_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.SOLARIS_ISLES))));
		context.register(SPRINGSTONE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/springstone"), EternalStarlight.id("entity/ectostone/springstone_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_FOREST))));
		context.register(TOXITE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/toxite"), EternalStarlight.id("entity/ectostone/toxite_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.SCARLET_FOREST))));
		context.register(VOIDSTONE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/voidstone"), EternalStarlight.id("entity/ectostone/voidstone_glow"), HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_FOREST), biomes.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST))));
	}

	public static ResourceKey<EctostoneVariant> create(String name) {
		return ResourceKey.create(ESRegistries.ECTOSTONE_VARIANT, EternalStarlight.id(name));
	}
}
