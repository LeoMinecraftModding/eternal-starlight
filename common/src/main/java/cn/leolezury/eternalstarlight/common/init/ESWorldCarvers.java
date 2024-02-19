package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.carver.ESCaveCarver;
import cn.leolezury.eternalstarlight.common.world.gen.carver.ESExtraCavesCarver;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class ESWorldCarvers {
    public static final RegistrationProvider<WorldCarver<?>> WORLD_CARVERS = RegistrationProvider.get(Registries.CARVER, EternalStarlight.MOD_ID);
    public static final RegistryObject<WorldCarver<?>, WorldCarver<CaveCarverConfiguration>> CAVES = WORLD_CARVERS.register("caves", () -> new ESCaveCarver(CaveCarverConfiguration.CODEC));
    public static final RegistryObject<WorldCarver<?>, WorldCarver<CarverConfiguration>> CAVES_EXTRA = WORLD_CARVERS.register("caves_extra", () -> new ESExtraCavesCarver(CarverConfiguration.CODEC.codec()));
    public static void loadClass() {}
}
