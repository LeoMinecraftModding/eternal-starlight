package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.carver.ESCaveCarver;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class ESWorldCarvers {
	public static final RegistrationProvider<WorldCarver<?>> WORLD_CARVERS = RegistrationProvider.get(Registries.CARVER, EternalStarlight.ID);
	public static final RegistryObject<WorldCarver<?>, WorldCarver<CaveCarverConfiguration>> CAVES = WORLD_CARVERS.register("caves", () -> new ESCaveCarver(CaveCarverConfiguration.CODEC));

	public static void loadClass() {
	}
}
