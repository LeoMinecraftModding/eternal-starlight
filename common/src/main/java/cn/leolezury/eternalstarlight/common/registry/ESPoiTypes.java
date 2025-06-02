package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class ESPoiTypes {
	public static final RegistrationProvider<PoiType> POI_TYPES = RegistrationProvider.get(Registries.POINT_OF_INTEREST_TYPE, EternalStarlight.ID);
	public static final RegistryObject<PoiType, PoiType> STARFIRE_BIRD_NEST = POI_TYPES.register("starfire_bird_nest", () -> new PoiType(ImmutableList.of(ESBlocks.STARFIRE_BIRD_NEST.get()).stream().flatMap((block) -> block.getStateDefinition().getPossibleStates().stream()).collect(ImmutableSet.toImmutableSet()), 1, 1));

	public static void loadClass() {
	}
}
