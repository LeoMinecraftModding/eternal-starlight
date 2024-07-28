package cn.leolezury.eternalstarlight.neoforge.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ESFluidTypes {
	public static final RegistrationProvider<FluidType> FLUID_TYPES = RegistrationProvider.get(NeoForgeRegistries.FLUID_TYPES.key(), EternalStarlight.ID);
	public static final RegistryObject<FluidType, FluidType> ETHER = FLUID_TYPES.register("ether", () -> new FluidType(
		FluidType.Properties.create()
			.canExtinguish(true)
			.canSwim(false)
			.canDrown(false)
			.pathType(PathType.DANGER_OTHER)
			.adjacentPathType(null)
			.lightLevel(15)
			.density(3000)
			.viscosity(6000)
	) {
		@Override
		public double motionScale(Entity entity) {
			return 0.0014;
		}
	});

	public static void loadClass() {
	}
}
