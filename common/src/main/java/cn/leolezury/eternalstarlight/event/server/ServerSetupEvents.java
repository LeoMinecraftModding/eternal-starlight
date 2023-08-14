package cn.leolezury.eternalstarlight.event.server;

import cn.leolezury.eternalstarlight.EternalStarlight;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerSetupEvents {
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.STARLIGHT_FLOWER.getId(), BlockInit.POTTED_STARLIGHT_FLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.SWAMP_ROSE.getId(), BlockInit.POTTED_SWAMP_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.LUNAR_SAPLING.getId(), BlockInit.POTTED_LUNAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.NORTHLAND_SAPLING.getId(), BlockInit.POTTED_NORTHLAND_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.STARLIGHT_MANGROVE_SAPLING.getId(), BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING);
        });
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(EntityInit.BOARWARF.get(), Boarwarf.createAttributes().build());
        event.put(EntityInit.ASTRAL_GOLEM.get(), AstralGolem.createAttributes().build());
        event.put(EntityInit.LONESTAR_SKELETON.get(), LonestarSkeleton.createAttributes().build());
        event.put(EntityInit.NIGHTSHADE_SPIDER.get(), NightshadeSpider.createNightshadeSpider().build());
        event.put(EntityInit.TWILIGHT_SQUID.get(), TwilightSquid.createAttributes().build());
        event.put(EntityInit.DRYAD.get(), Dryad.createAttributes().build());
        event.put(EntityInit.THE_GATEKEEPER.get(), TheGatekeeper.createAttributes().build());
        event.put(EntityInit.STARLIGHT_GOLEM.get(), StarlightGolem.createAttributes().build());
        event.put(EntityInit.LUNAR_MONSTROSITY.get(), LunarMonstrosity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(EntityInit.BOARWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(EntityInit.LONESTAR_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(EntityInit.NIGHTSHADE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(EntityInit.TWILIGHT_SQUID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TwilightSquid::checkTwilightSquidSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(EntityInit.DRYAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dryad::checkDryadSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
