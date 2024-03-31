package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.command.ESCommand;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.entity.living.animal.*;
import cn.leolezury.eternalstarlight.common.entity.living.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import cn.leolezury.eternalstarlight.common.entity.living.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.common.entity.living.monster.NightshadeSpider;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import com.google.common.base.Suppliers;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CommonSetupHandlers {
    public static final Supplier<Map<Block, Block>> STRIPPABLES = Suppliers.memoize(() -> Map.of(
            ESBlocks.LUNAR_LOG.get(), ESBlocks.STRIPPED_LUNAR_LOG.get(),
            ESBlocks.NORTHLAND_LOG.get(), ESBlocks.STRIPPED_NORTHLAND_LOG.get(),
            ESBlocks.STARLIGHT_MANGROVE_LOG.get(), ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get(),
            ESBlocks.SCARLET_LOG.get(), ESBlocks.STRIPPED_SCARLET_LOG.get(),
            ESBlocks.TORREYA_LOG.get(), ESBlocks.STRIPPED_TORREYA_LOG.get()
    ));

    public static final Supplier<Map<Block, Block>> TILLABLES = Suppliers.memoize(() -> Map.of(
            ESBlocks.NIGHTSHADE_DIRT.get(), ESBlocks.NIGHTSHADE_FARMLAND.get(),
            ESBlocks.NIGHTSHADE_GRASS_BLOCK.get(), ESBlocks.NIGHTSHADE_FARMLAND.get()
    ));

    public static final List<Supplier<Item>> SHIELDS = List.of(
            ESItems.GLACITE_SHIELD
    );

    public interface EntityAttributeRegisterStrategy {
        <T extends LivingEntity> void register(EntityType<T> entityType, AttributeSupplier attributeSupplier);
    }

    public static void createAttributes(EntityAttributeRegisterStrategy strategy) {
        strategy.register(ESEntities.BOARWARF.get(), Boarwarf.createAttributes().build());
        strategy.register(ESEntities.ASTRAL_GOLEM.get(), AstralGolem.createAttributes().build());
        strategy.register(ESEntities.LONESTAR_SKELETON.get(), LonestarSkeleton.createAttributes().build());
        strategy.register(ESEntities.NIGHTSHADE_SPIDER.get(), NightshadeSpider.createNightshadeSpider().build());
        strategy.register(ESEntities.ENT.get(), Ent.createAttributes().build());
        strategy.register(ESEntities.RATLIN.get(), Ratlin.createAttributes().build());
        strategy.register(ESEntities.YETI.get(), Yeti.createAttributes().build());
        strategy.register(ESEntities.AURORA_DEER.get(), AuroraDeer.createAttributes().build());
        strategy.register(ESEntities.CRYSTALLIZED_MOTH.get(), CrystallizedMoth.createAttributes().build());
        strategy.register(ESEntities.SHIMMER_LACEWING.get(), ShimmerLacewing.createAttributes().build());
        strategy.register(ESEntities.GRIMSTONE_GOLEM.get(), GrimstoneGolem.createAttributes().build());
        strategy.register(ESEntities.LUMINOFISH.get(), LuminoFish.createAttributes().build());
        strategy.register(ESEntities.LUMINARIS.get(), Luminaris.createAttributes().build());
        strategy.register(ESEntities.TWILIGHT_GAZE.get(), TwilightGaze.createAttributes().build());
        strategy.register(ESEntities.THE_GATEKEEPER.get(), TheGatekeeper.createAttributes().build());
        strategy.register(ESEntities.STARLIGHT_GOLEM.get(), StarlightGolem.createAttributes().build());
        strategy.register(ESEntities.FREEZE.get(), Freeze.createAttributes().build());
        strategy.register(ESEntities.LUNAR_MONSTROSITY.get(), LunarMonstrosity.createAttributes().build());
    }

    public interface SpawnPlacementRegisterStrategy {
        <T extends Mob> void register(EntityType<T> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate);
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterStrategy strategy) {
        strategy.register(ESEntities.BOARWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        strategy.register(ESEntities.LONESTAR_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        strategy.register(ESEntities.NIGHTSHADE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        strategy.register(ESEntities.ENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ent::checkEntSpawnRules);
        strategy.register(ESEntities.RATLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ratlin::checkRatlinSpawnRules);
        strategy.register(ESEntities.YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Yeti::checkYetiSpawnRules);
        strategy.register(ESEntities.AURORA_DEER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AuroraDeer::checkAuroraDeerSpawnRules);
        strategy.register(ESEntities.CRYSTALLIZED_MOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystallizedMoth::checkMothSpawnRules);
        strategy.register(ESEntities.SHIMMER_LACEWING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShimmerLacewing::checkLacewingSpawnRules);
        strategy.register(ESEntities.GRIMSTONE_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrimstoneGolem::checkGolemSpawnRules);
        strategy.register(ESEntities.LUMINOFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuminoFish::checkAbyssalWaterAnimalSpawnRules);
        strategy.register(ESEntities.LUMINARIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuminoFish::checkAbyssalWaterAnimalSpawnRules);
        strategy.register(ESEntities.TWILIGHT_GAZE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuminoFish::checkAbyssalWaterAnimalSpawnRules);
        strategy.register(ESEntities.FREEZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
    }

    public interface FuelRegisterStrategy {
        void register(ItemLike item, int time);
        void register(TagKey<Item> itemTag, int time);
    }

    public static void registerFuels(FuelRegisterStrategy strategy) {
        strategy.register(ESTags.Items.YETI_FUR, 100);
        strategy.register(ESTags.Items.YETI_FUR_CARPETS, 100);
        strategy.register(ESBlocks.SALTPETER_BLOCK.get(), 16000);
        strategy.register(ESItems.SALTPETER_POWDER.get(), 1600);
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        ESCommand.register(dispatcher, context);
    }

    public static void registerChunkGenerator() {
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, EternalStarlight.MOD_ID + ":es_gen", ESChunkGenerator.CODEC);
    }

    public static void registerBiomeSource() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, EternalStarlight.MOD_ID + ":es_biomes", ESBiomeSource.CODEC);
    }
}
