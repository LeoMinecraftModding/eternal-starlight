package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.init.ESBlocks;
import cn.leolezury.eternalstarlight.forge.init.FluidTypeInit;
import cn.leolezury.eternalstarlight.forge.platform.ForgePlatform;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEvents {
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        FluidInteractionRegistry.addInteraction(FluidTypeInit.ETHER.get(), new FluidInteractionRegistry.InteractionInformation((level, blockPos, relativePos, fluidState) -> !level.getFluidState(relativePos).isEmpty() && !level.getBlockState(relativePos).is(ESBlocks.ETHER.get()), Blocks.QUARTZ_BLOCK.defaultBlockState()));
    }

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        for (Registry<?> registry : ForgePlatform.newRegistries) {
            event.register(registry);
        }
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
        CommonSetupHandlers.registerSpawnPlacements(strategy);
    }
}
