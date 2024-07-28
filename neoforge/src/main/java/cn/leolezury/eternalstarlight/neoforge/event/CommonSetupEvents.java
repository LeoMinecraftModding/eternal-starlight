package cn.leolezury.eternalstarlight.neoforge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.neoforge.platform.NeoForgePlatform;
import cn.leolezury.eternalstarlight.neoforge.registry.ESFluidTypes;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = EternalStarlight.ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonSetupEvents {
	@SubscribeEvent
	private static void setup(FMLCommonSetupEvent event) {
		FluidInteractionRegistry.addInteraction(ESFluidTypes.ETHER.get(), new FluidInteractionRegistry.InteractionInformation((level, blockPos, relativePos, fluidState) -> !level.getFluidState(relativePos).isEmpty() && !level.getBlockState(relativePos).is(ESBlocks.ETHER.get()), ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState()));
		CommonSetupHandlers.commonSetup();
	}

	@SubscribeEvent
	private static void onNewRegistry(NewRegistryEvent event) {
		for (Registry<?> registry : NeoForgePlatform.NEW_REGISTRIES) {
			event.register(registry);
		}
	}

	@SubscribeEvent
	private static void onAttributeCreate(EntityAttributeCreationEvent event) {
		CommonSetupHandlers.createAttributes(event::put);
	}

	@SubscribeEvent
	private static void onSpawnPlacementRegister(RegisterSpawnPlacementsEvent event) {
		CommonSetupHandlers.SpawnPlacementRegisterStrategy strategy = new CommonSetupHandlers.SpawnPlacementRegisterStrategy() {
			@Override
			public <T extends Mob> void register(EntityType<T> entityType, @Nullable SpawnPlacementType placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
				event.register(entityType, placementType, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.AND);
			}
		};
		CommonSetupHandlers.registerSpawnPlacements(strategy);
	}
}
