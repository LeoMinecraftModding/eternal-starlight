package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class POIInit {
    public static final RegistrationProvider<PoiType> POIS = RegistrationProvider.get(Registries.POINT_OF_INTEREST_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<PoiType, PoiType> STARLIGHT_PORTAL = POIS.register("starlight_portal", () -> new PoiType(ImmutableSet.copyOf(BlockInit.STARLIGHT_PORTAL.get().getStateDefinition().getPossibleStates()), 0, 1));
    public static void loadClass() {}
}
