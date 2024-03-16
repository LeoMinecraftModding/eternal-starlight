package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.world.gen.structure.pool.ESSinglePoolElement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class ESTemplatePools {
    public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_COMMON = create("portal_ruins_common");
    public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_FOREST = create("portal_ruins_forest");
    public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_DESERT = create("portal_ruins_desert");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_BOSS = create("golem_forge/boss");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROAD = create("golem_forge/road");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROAD_OR_ROOM = create("golem_forge/road_or_room");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROAD_OR_ROOM_DOUBLE = create("golem_forge/road_or_room_double");
    public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_CHIMNEY = create("golem_forge/chimney");

    public static final ResourceKey<StructureProcessorList> PORTAL_RUINS_VINES = createProcessor("portal_ruins_vines");
    public static final ResourceKey<StructureProcessorList> GOLEM_FORGE_OXIDIZATION = createProcessor("golem_forge/oxidization");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

        context.register(PORTAL_RUINS_COMMON, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(ESSinglePoolElement.make(new ResourceLocation(EternalStarlight.MOD_ID, "portal_ruins/common").toString(), processors.getOrThrow(PORTAL_RUINS_VINES), 0), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(PORTAL_RUINS_FOREST, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(ESSinglePoolElement.make(new ResourceLocation(EternalStarlight.MOD_ID, "portal_ruins/forest").toString(), processors.getOrThrow(PORTAL_RUINS_VINES), 0), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(PORTAL_RUINS_DESERT, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(ESSinglePoolElement.make(new ResourceLocation(EternalStarlight.MOD_ID, "portal_ruins/desert").toString(), processors.getOrThrow(PORTAL_RUINS_VINES), 0), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_BOSS, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/boss").toString(), processors.getOrThrow(GOLEM_FORGE_OXIDIZATION)), 1)
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
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/material_storage").toString(), processors.getOrThrow(GOLEM_FORGE_OXIDIZATION)), 1),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/battle_room").toString()), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_ROAD_OR_ROOM_DOUBLE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/road_double").toString()), 4),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/chimney_room").toString()), 2),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/lava_storage").toString()), 2),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/material_storage").toString(), processors.getOrThrow(GOLEM_FORGE_OXIDIZATION)), 1),
                Pair.of(StructurePoolElement.single(new ResourceLocation(EternalStarlight.MOD_ID, "golem_forge/battle_room").toString()), 2)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(GOLEM_FORGE_CHIMNEY, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.feature(features.getOrThrow(ESPlacedFeatures.GOLEM_FORGE_CHIMNEY)), 1)
        ), StructureTemplatePool.Projection.RIGID));
    }

    public static void bootstrapProcessors(BootstapContext<StructureProcessorList> context) {
        context.register(PORTAL_RUINS_VINES, new StructureProcessorList(List.of(
                new RuleProcessor(List.of(
                        new ProcessorRule(
                                new RandomBlockMatchTest(Blocks.VINE, 0.6F),
                                AlwaysTrueTest.INSTANCE,
                                Blocks.AIR.defaultBlockState()
                        )
                ))
        )));
        context.register(GOLEM_FORGE_OXIDIZATION, new StructureProcessorList(List.of(
                new RuleProcessor(List.of(
                        new ProcessorRule(
                                new RandomBlockMatchTest(ESBlocks.GOLEM_STEEL_TILES.get(), 0.8F),
                                AlwaysTrueTest.INSTANCE,
                                ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get().defaultBlockState()
                        )
                ))
        )));
    };

    public static ResourceKey<StructureTemplatePool> create(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }

    public static ResourceKey<StructureProcessorList> createProcessor(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
