package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class POIInit {
    public static final DeferredRegister<PoiType> POIS
            = DeferredRegister.create(ForgeRegistries.POI_TYPES, EternalStarlight.MOD_ID);

    public static final RegistryObject<PoiType> STARLIGHT_PORTAL = POIS.register("starlight_portal", () -> new PoiType(ImmutableSet.copyOf(BlockInit.STARLIGHT_PORTAL.get().getStateDefinition().getPossibleStates()), 0, 1));
}
