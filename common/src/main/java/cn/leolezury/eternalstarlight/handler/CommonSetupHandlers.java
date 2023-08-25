package cn.leolezury.eternalstarlight.handler;

import cn.leolezury.eternalstarlight.entity.animal.Dryad;
import cn.leolezury.eternalstarlight.entity.animal.TwilightSquid;
import cn.leolezury.eternalstarlight.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.entity.boss.TheGatekeeper;
import cn.leolezury.eternalstarlight.entity.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.entity.monster.NightshadeSpider;
import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.entity.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.init.EntityInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommonSetupHandlers {
    public static final Map<ResourceLocation, Supplier<? extends Block>> pottedPlantsMap = new HashMap<>();

    public static void setup() {
        pottedPlantsMap.put(BlockInit.STARLIGHT_FLOWER.getId(), BlockInit.POTTED_STARLIGHT_FLOWER);
        pottedPlantsMap.put(BlockInit.SWAMP_ROSE.getId(), BlockInit.POTTED_SWAMP_ROSE);
        pottedPlantsMap.put(BlockInit.LUNAR_SAPLING.getId(), BlockInit.POTTED_LUNAR_SAPLING);
        pottedPlantsMap.put(BlockInit.NORTHLAND_SAPLING.getId(), BlockInit.POTTED_NORTHLAND_SAPLING);
        pottedPlantsMap.put(BlockInit.STARLIGHT_MANGROVE_SAPLING.getId(), BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING);
    }

    public interface EntityAttributeRegisterStrategy {
        <T extends Entity> void register(EntityType<T> entityType, AttributeSupplier attributeSupplier);
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
        <T extends Entity> void register(EntityType<T> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate);
    }

    public static void onSpawnPlacementRegister(SpawnPlacementRegisterStrategy strategy) {
        strategy.register(EntityInit.BOARWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        strategy.register(EntityInit.LONESTAR_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        strategy.register(EntityInit.NIGHTSHADE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        strategy.register(EntityInit.TWILIGHT_SQUID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TwilightSquid::checkTwilightSquidSpawnRules);
        strategy.register(EntityInit.DRYAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dryad::checkDryadSpawnRules);
    }
}
