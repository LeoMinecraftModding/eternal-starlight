package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class POIInit {
//    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<PoiType> POIS = RegistrationProvider.get(Registries.POINT_OF_INTEREST_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<PoiType> STARLIGHT_PORTAL = POIS.register("starlight_portal", () -> new PoiType(ImmutableSet.copyOf(BlockInit.STARLIGHT_PORTAL.get().getStateDefinition().getPossibleStates()), 0, 1));
    public static void postRegistry() {}
}
