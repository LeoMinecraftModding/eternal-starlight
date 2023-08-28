package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class DimensionInit {
    public static final ResourceKey<Level> STARLIGHT_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EternalStarlight.MOD_ID, "starlight"));
    public static final ResourceKey<DimensionType> STARLIGHT_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, STARLIGHT_KEY.registry());
}
