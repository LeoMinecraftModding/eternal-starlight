package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.forge.network.ForgeNetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEvents {
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(CommonSetupHandlers::setup);
        event.enqueueWork(ForgeNetworkHandler::init);
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        CommonSetupHandlers.createAttributes(event::put);
    }

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        CommonSetupHandlers.SpawnPlacementRegisterStrategy strategy = new CommonSetupHandlers.SpawnPlacementRegisterStrategy() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
                event.register(entityType, placementType, heightmap, predicate, SpawnPlacementRegisterEvent.Operation.OR);
            }
        };
        CommonSetupHandlers.registerSpawnPlacement(strategy);
    }
}
