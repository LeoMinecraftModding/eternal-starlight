package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.SeekerVariant;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ESSeekerVariants {
	public static final ResourceKey<SeekerVariant> LUNAR = create("lunar");
	public static final ResourceKey<SeekerVariant> SCARLET = create("scarlet");

	public static void bootstrap(BootstrapContext<SeekerVariant> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(LUNAR, new SeekerVariant(EternalStarlight.id("entity/seeker/lunar"), EternalStarlight.id("entity/seeker/lunar_glow"), EternalStarlight.id("entity/seeker/lunar_tentacle"), HolderSet.direct(biomes.getOrThrow(ESBiomes.STARLIGHT_FOREST), biomes.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST))));
		context.register(SCARLET, new SeekerVariant(EternalStarlight.id("entity/seeker/scarlet"), EternalStarlight.id("entity/seeker/scarlet_glow"), EternalStarlight.id("entity/seeker/scarlet_tentacle"), HolderSet.direct(biomes.getOrThrow(ESBiomes.SCARLET_FOREST))));
	}

	public static ResourceKey<SeekerVariant> create(String name) {
		return ResourceKey.create(ESRegistries.SEEKER_VARIANT, EternalStarlight.id(name));
	}
}
