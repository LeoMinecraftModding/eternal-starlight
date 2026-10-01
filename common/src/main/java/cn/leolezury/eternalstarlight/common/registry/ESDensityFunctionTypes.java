package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.density.RiverValueFunction;
import cn.leolezury.eternalstarlight.common.world.gen.density.SmoothedFunction;
import cn.leolezury.eternalstarlight.common.world.gen.density.SurfaceBiomeHeightFunction;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;

public class ESDensityFunctionTypes {
	public static final RegistrationProvider<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = RegistrationProvider.get(Registries.DENSITY_FUNCTION_TYPE, EternalStarlight.ID);
	public static final RegistryObject<MapCodec<? extends DensityFunction>, MapCodec<SurfaceBiomeHeightFunction>> SURFACE_BIOME_HEIGHT = DENSITY_FUNCTION_TYPES.register("surface_biome_height", SurfaceBiomeHeightFunction.CODEC::codec);
	public static final RegistryObject<MapCodec<? extends DensityFunction>, MapCodec<RiverValueFunction>> RIVER_VALUE = DENSITY_FUNCTION_TYPES.register("river_value", RiverValueFunction.CODEC::codec);
	public static final RegistryObject<MapCodec<? extends DensityFunction>, MapCodec<SmoothedFunction>> SMOOTHED = DENSITY_FUNCTION_TYPES.register("smoothed", SmoothedFunction.CODEC::codec);

	public static void loadClass() {
	}
}
