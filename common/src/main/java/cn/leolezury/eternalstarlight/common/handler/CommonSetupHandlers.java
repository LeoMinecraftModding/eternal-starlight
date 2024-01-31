package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.animal.*;
import cn.leolezury.eternalstarlight.common.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.boss.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.common.entity.monster.NightshadeSpider;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
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
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

public class CommonSetupHandlers {
    public interface EntityAttributeRegisterStrategy {
        <T extends LivingEntity> void register(EntityType<T> entityType, AttributeSupplier attributeSupplier);
    }

    public static void createAttributes(EntityAttributeRegisterStrategy strategy) {
        strategy.register(EntityInit.BOARWARF.get(), Boarwarf.createAttributes().build());
        strategy.register(EntityInit.ASTRAL_GOLEM.get(), AstralGolem.createAttributes().build());
        strategy.register(EntityInit.LONESTAR_SKELETON.get(), LonestarSkeleton.createAttributes().build());
        strategy.register(EntityInit.NIGHTSHADE_SPIDER.get(), NightshadeSpider.createNightshadeSpider().build());
        strategy.register(EntityInit.ENT.get(), Ent.createAttributes().build());
        strategy.register(EntityInit.RATLIN.get(), Ratlin.createAttributes().build());
        strategy.register(EntityInit.YETI.get(), Yeti.createAttributes().build());
        strategy.register(EntityInit.AURORA_DEER.get(), AuroraDeer.createAttributes().build());
        strategy.register(EntityInit.LUMINOFISH.get(), LuminoFish.createAttributes().build());
        strategy.register(EntityInit.LUMINARIS.get(), Luminaris.createAttributes().build());
        strategy.register(EntityInit.THE_GATEKEEPER.get(), TheGatekeeper.createAttributes().build());
        strategy.register(EntityInit.STARLIGHT_GOLEM.get(), StarlightGolem.createAttributes().build());
        strategy.register(EntityInit.LUNAR_MONSTROSITY.get(), LunarMonstrosity.createAttributes().build());
    }

    public interface SpawnPlacementRegisterStrategy {
        <T extends Mob> void register(EntityType<T> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate);
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterStrategy strategy) {
        strategy.register(EntityInit.BOARWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        strategy.register(EntityInit.LONESTAR_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        strategy.register(EntityInit.NIGHTSHADE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        strategy.register(EntityInit.ENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ent::checkEntSpawnRules);
        strategy.register(EntityInit.RATLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ratlin::checkRatlinSpawnRules);
        strategy.register(EntityInit.YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Yeti::checkYetiSpawnRules);
        strategy.register(EntityInit.AURORA_DEER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AuroraDeer::checkAuroraDeerSpawnRules);
        strategy.register(EntityInit.LUMINOFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuminoFish::checkAbyssalWaterAnimalSpawnRules);
        strategy.register(EntityInit.LUMINARIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Luminaris::checkAbyssalWaterAnimalSpawnRules);
    }

    public interface FuelRegisterStrategy {
        void register(ItemLike item, int time);
        void register(TagKey<Item> itemTag, int time);
    }

    public static void registerFuels(FuelRegisterStrategy strategy) {
        strategy.register(ESTags.Items.YETI_FUR, 100);
        strategy.register(ESTags.Items.YETI_FUR_CARPETS, 100);
    }

    public static void registerChunkGenerator() {
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, EternalStarlight.MOD_ID + ":es_gen", ESChunkGenerator.CODEC);
    }

    public static void registerBiomeSource() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, EternalStarlight.MOD_ID + ":es_biomes", ESBiomeSource.CODEC);
    }
}
