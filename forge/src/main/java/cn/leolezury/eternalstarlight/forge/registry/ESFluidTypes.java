package cn.leolezury.eternalstarlight.forge.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ESFluidTypes {
	public static final RegistrationProvider<FluidType> FLUID_TYPES = RegistrationProvider.get(NeoForgeRegistries.FLUID_TYPES.key(), EternalStarlight.ID);
	public static final RegistryObject<FluidType, FluidType> ETHER = FLUID_TYPES.register("ether", () -> new FluidType(FluidType.Properties.create()));

	public static void loadClass() {
	}
}
