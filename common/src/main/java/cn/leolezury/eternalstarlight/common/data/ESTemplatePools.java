package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ESTemplatePools {
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_BOSS = create("golem_forge/boss");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROAD = create("golem_forge/road");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROAD_OR_ROOM = create("golem_forge/road_or_room");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROAD_OR_ROOM_DOUBLE = create("golem_forge/road_or_room_double");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_CHIMNEY = create("golem_forge/chimney");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        HolderGetter<PlacedFeature> featureGetter = context.lookup(Registries.PLACED_FEATURE);
        // HolderGetter<StructureProcessorList> processorGetter = context.lookup(Registries.PROCESSOR_LIST);

        context.register(GOLEM_FORGE_BOSS, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/boss").toString()), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_ROAD, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/road_double").toString()), 1),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/road_single").toString()), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_ROAD_OR_ROOM, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/road_double").toString()), 4),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/road_single").toString()), 5),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/chimney_room").toString()), 2),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/lava_storage").toString()), 2),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/material_storage").toString()), 1),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/battle_room").toString()), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_ROAD_OR_ROOM_DOUBLE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/road_double").toString()), 4),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/chimney_room").toString()), 2),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/lava_storage").toString()), 2),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/material_storage").toString()), 1),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/battle_room").toString()), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_CHIMNEY, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.feature(featureGetter.getOrThrow(ESPlacedFeatures.GOLEM_FORGE_CHIMNEY)), 1)
        ), StructureTemplatePool.Projection.RIGID));
    }

    public static ResourceKey<StructureTemplatePool> create(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
