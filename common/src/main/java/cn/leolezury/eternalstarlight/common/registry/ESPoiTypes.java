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
	public static final RegistryObject<PoiType, PoiType> STARFIRE_BIRD_NEST = POI_TYPES.register("starfire_bird_nest", () -> new PoiType(ImmutableList.of(
		ESBlocks.STARFIRE_BIRD_NEST.get(),
		ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get()
	).stream().flatMap((block) -> block.getStateDefinition().getPossibleStates().stream()).collect(ImmutableSet.toImmutableSet()), 1, 1));
	public static final RegistryObject<PoiType, PoiType> ENERGY_BLOCK = POI_TYPES.register("energy_block", () -> new PoiType(ImmutableList.of(ESBlocks.ENERGY_BLOCK.get()).stream().flatMap((block) -> block.getStateDefinition().getPossibleStates().stream()).collect(ImmutableSet.toImmutableSet()), 1, 1));

	public static void loadClass() {
	}
}
