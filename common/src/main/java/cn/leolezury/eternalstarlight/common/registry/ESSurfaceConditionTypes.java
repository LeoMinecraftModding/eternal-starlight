package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.surface.AboveSurfaceCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ESSurfaceConditionTypes {
	public static final RegistrationProvider<MapCodec<? extends SurfaceRules.ConditionSource>> SURFACE_CONDITION_TYPES = RegistrationProvider.get(Registries.MATERIAL_CONDITION, EternalStarlight.ID);
	public static final RegistryObject<MapCodec<? extends SurfaceRules.ConditionSource>, MapCodec<AboveSurfaceCondition>> ABOVE_SURFACE = SURFACE_CONDITION_TYPES.register("above_surface", AboveSurfaceCondition.CODEC::codec);

	public static void loadClass() {
	}
}
