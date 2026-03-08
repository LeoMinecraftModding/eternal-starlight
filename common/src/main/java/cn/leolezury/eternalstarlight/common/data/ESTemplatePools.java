package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.world.gen.structure.pool.ESSinglePoolElement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class ESTemplatePools {
	public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_COMMON = create("portal_ruins_common");
	public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_FOREST = create("portal_ruins_forest");
	public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_DESERT = create("portal_ruins_desert");
	public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_JUNGLE = create("portal_ruins_jungle");
	public static final ResourceKey<StructureTemplatePool> PORTAL_RUINS_COLD = create("portal_ruins_cold");
	public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_BOSS = create("golem_forge/boss");
	public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_ROOM = create("golem_forge/room");
	public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_DECOR_SMALL = create("golem_forge/decor/small");
	public static final ResourceKey<StructureTemplatePool> GOLEM_FORGE_DECOR_LARGE = create("golem_forge/decor/large");

	public static final ResourceKey<StructureProcessorList> PORTAL_RUINS_VINES = createProcessor("portal_ruins_vines");

	public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
		Holder<StructureTemplatePool> emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

		context.register(PORTAL_RUINS_COMMON, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(ESSinglePoolElement.make(EternalStarlight.id("portal_ruins/common").toString(), processors.getOrThrow(PORTAL_RUINS_VINES), 0), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(PORTAL_RUINS_FOREST, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(ESSinglePoolElement.make(EternalStarlight.id("portal_ruins/forest").toString(), processors.getOrThrow(PORTAL_RUINS_VINES), 0), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(PORTAL_RUINS_DESERT, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(ESSinglePoolElement.make(EternalStarlight.id("portal_ruins/desert").toString(), processors.getOrThrow(PORTAL_RUINS_VINES), 0), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(PORTAL_RUINS_JUNGLE, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(ESSinglePoolElement.make(EternalStarlight.id("portal_ruins/jungle").toString(), Holder.direct(new StructureProcessorList(List.of())), 0), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(PORTAL_RUINS_COLD, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(ESSinglePoolElement.make(EternalStarlight.id("portal_ruins/cold").toString(), Holder.direct(new StructureProcessorList(List.of())), 0), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(GOLEM_FORGE_BOSS, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/room/boss").toString()), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(GOLEM_FORGE_ROOM, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/room/common").toString()), 5),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/room/alloy").toString()), 2),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/room/storage").toString()), 3),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/room/frozen").toString()), 3),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/room/permafrost").toString()), 1)
		), StructureTemplatePool.Projection.RIGID));
		context.register(GOLEM_FORGE_DECOR_SMALL, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/small_1").toString()), 4),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/small_2").toString()), 3)
		), StructureTemplatePool.Projection.RIGID));
		context.register(GOLEM_FORGE_DECOR_LARGE, new StructureTemplatePool(emptyPool, ImmutableList.of(
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/large_1").toString()), 2),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/large_2").toString()), 2),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/large_3").toString()), 5),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/large_4").toString()), 3),
			Pair.of(StructurePoolElement.single(EternalStarlight.id("golem_forge/decor/large_5").toString()), 4)
		), StructureTemplatePool.Projection.RIGID));
	}

	public static void bootstrapProcessors(BootstrapContext<StructureProcessorList> context) {
		context.register(PORTAL_RUINS_VINES, new StructureProcessorList(List.of(
			new RuleProcessor(List.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.VINE, 0.6F),
					AlwaysTrueTest.INSTANCE,
					Blocks.AIR.defaultBlockState()
				)
			))
		)));
	}

	public static ResourceKey<StructureTemplatePool> create(String name) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, EternalStarlight.id(name));
	}

	public static ResourceKey<StructureProcessorList> createProcessor(String name) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, EternalStarlight.id(name));
	}
}
