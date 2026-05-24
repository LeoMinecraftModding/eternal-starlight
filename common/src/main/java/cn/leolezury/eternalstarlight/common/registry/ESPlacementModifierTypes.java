package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.placement.AvoidStructureFilter;
import cn.leolezury.eternalstarlight.common.world.gen.feature.placement.HeightRangeFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ESPlacementModifierTypes {
	public static final RegistrationProvider<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = RegistrationProvider.get(Registries.PLACEMENT_MODIFIER_TYPE, EternalStarlight.ID);
	public static final RegistryObject<PlacementModifierType<?>, PlacementModifierType<AvoidStructureFilter>> AVOID_STRUCTURE = PLACEMENT_MODIFIER_TYPES.register("avoid_structure", () -> () -> AvoidStructureFilter.CODEC);
	public static final RegistryObject<PlacementModifierType<?>, PlacementModifierType<HeightRangeFilter>> HEIGHT_RANGE_FILTER = PLACEMENT_MODIFIER_TYPES.register("height_range_filter", () -> () -> HeightRangeFilter.CODEC);

	public static void loadClass() {
	}
}
