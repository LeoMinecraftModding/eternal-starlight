package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;

public class ESFluids {
    public static final RegistrationProvider<Fluid> FLUIDS = RegistrationProvider.get(Registries.FLUID, EternalStarlight.MOD_ID);
    public static final RegistryObject<Fluid, EtherFluid.Still> ETHER_STILL = FLUIDS.register("ether", ESPlatform.INSTANCE::createEtherFluid);
    public static final RegistryObject<Fluid, EtherFluid.Flowing> ETHER_FLOWING = FLUIDS.register("flowing_ether", ESPlatform.INSTANCE::createFlowingEtherFluid);

    public static void loadClass() {}
}
