package cn.leolezury.eternalstarlight.fabric;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.fabric.network.FabricNetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class ESFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EternalStarlight.init();

        // setup handlers
        FabricNetworkHandler.init(false);
        if (ESPlatform.INSTANCE.isPhysicalClient()) {
            FabricNetworkHandler.init(true);
        }
        CommonSetupHandlers.createAttributes(FabricDefaultAttributeRegistry::register);
        CommonSetupHandlers.registerSpawnPlacements(SpawnPlacements::register);
        CommonSetupHandlers.registerFuels(new CommonSetupHandlers.FuelRegisterStrategy() {
            @Override
            public void register(ItemLike item, int time) {
                FuelRegistry.INSTANCE.add(item, time);
            }

            @Override
            public void register(TagKey<Item> itemTag, int time) {
                FuelRegistry.INSTANCE.add(itemTag, time);
            }
        });
        CommandRegistrationCallback.EVENT.register(((dispatcher, context, environment) -> CommonSetupHandlers.registerCommands(dispatcher, context)));
        CommonSetupHandlers.registerChunkGenerator();
        CommonSetupHandlers.registerBiomeSource();


        // common handlers
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            CommonHandlers.onRightClickBlock(world, player, hand, hitResult.getBlockPos());
            return InteractionResult.PASS;
        });
        ServerTickEvents.END_SERVER_TICK.register(CommonHandlers::onServerTick);
        ServerTickEvents.START_WORLD_TICK.register(CommonHandlers::onLevelTick);
        ServerWorldEvents.LOAD.register((server, world) -> CommonHandlers.onLevelLoad(world));

        CommonHandlers.addReloadListeners(listener -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener((IdentifiableResourceReloadListener) listener));
    }
}
