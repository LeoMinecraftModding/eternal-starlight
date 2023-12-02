package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.animal.Dryad;
import cn.leolezury.eternalstarlight.common.entity.animal.TwilightSquid;
import cn.leolezury.eternalstarlight.common.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.boss.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.common.entity.monster.NightshadeSpider;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommonSetupHandlers {
    public static final Map<ResourceLocation, Supplier<? extends Block>> pottedPlantsMap = new HashMap<>();

    public static void setup() {
        pottedPlantsMap.put(BlockInit.STARLIGHT_FLOWER.getId(), BlockInit.POTTED_STARLIGHT_FLOWER);
        pottedPlantsMap.put(BlockInit.CONEBLOOM.getId(), BlockInit.POTTED_CONEBLOOM);
        pottedPlantsMap.put(BlockInit.NIGHTFAN.getId(), BlockInit.POTTED_NIGHTFAN);
        pottedPlantsMap.put(BlockInit.PINK_ROSE.getId(), BlockInit.POTTED_PINK_ROSE);
        pottedPlantsMap.put(BlockInit.STARLIGHT_TORCHFLOWER.getId(), BlockInit.POTTED_STARLIGHT_TORCHFLOWER);
        pottedPlantsMap.put(BlockInit.SWAMP_ROSE.getId(), BlockInit.POTTED_SWAMP_ROSE);
        pottedPlantsMap.put(BlockInit.LUNAR_SAPLING.getId(), BlockInit.POTTED_LUNAR_SAPLING);
        pottedPlantsMap.put(BlockInit.NORTHLAND_SAPLING.getId(), BlockInit.POTTED_NORTHLAND_SAPLING);
        pottedPlantsMap.put(BlockInit.STARLIGHT_MANGROVE_SAPLING.getId(), BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING);
    }

    public interface EntityAttributeRegisterStrategy {
        <T extends LivingEntity> void register(EntityType<T> entityType, AttributeSupplier attributeSupplier);
    }

    public static void createAttributes(EntityAttributeRegisterStrategy strategy) {
        strategy.register(EntityInit.BOARWARF.get(), Boarwarf.createAttributes().build());
        strategy.register(EntityInit.ASTRAL_GOLEM.get(), AstralGolem.createAttributes().build());
        strategy.register(EntityInit.LONESTAR_SKELETON.get(), LonestarSkeleton.createAttributes().build());
        strategy.register(EntityInit.NIGHTSHADE_SPIDER.get(), NightshadeSpider.createNightshadeSpider().build());
        strategy.register(EntityInit.TWILIGHT_SQUID.get(), TwilightSquid.createAttributes().build());
        strategy.register(EntityInit.DRYAD.get(), Dryad.createAttributes().build());
        strategy.register(EntityInit.THE_GATEKEEPER.get(), TheGatekeeper.createAttributes().build());
        strategy.register(EntityInit.STARLIGHT_GOLEM.get(), StarlightGolem.createAttributes().build());
        strategy.register(EntityInit.LUNAR_MONSTROSITY.get(), LunarMonstrosity.createAttributes().build());
    }

    public interface SpawnPlacementRegisterStrategy {
        <T extends Mob> void register(EntityType<T> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate);
    }

    public static void registerSpawnPlacement(SpawnPlacementRegisterStrategy strategy) {
        strategy.register(EntityInit.BOARWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        strategy.register(EntityInit.LONESTAR_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        strategy.register(EntityInit.NIGHTSHADE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        strategy.register(EntityInit.TWILIGHT_SQUID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TwilightSquid::checkTwilightSquidSpawnRules);
        strategy.register(EntityInit.DRYAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dryad::checkDryadSpawnRules);
    }

    public static void registerChunkGenerator() {
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, EternalStarlight.MOD_ID + ":es_gen", ESChunkGenerator.CODEC);
    }

    public static void registerBiomeSource() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, EternalStarlight.MOD_ID + ":es_biomes", ESBiomeSource.CODEC);
    }
}
