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
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Map;

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
        ServerTickEvents.END_SERVER_TICK.register(CommonHandlers::onServerTick);
        ServerTickEvents.START_WORLD_TICK.register(CommonHandlers::onLevelTick);
        ServerWorldEvents.LOAD.register((server, world) -> CommonHandlers.onLevelLoad(world));
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> CommonHandlers.onBlockBroken(player, pos, state));

        CommonHandlers.addReloadListeners(listener -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener((IdentifiableResourceReloadListener) listener));

        for (Map.Entry<Block, Block> entry : CommonSetupHandlers.STRIPPABLES.get().entrySet()) {
            StrippableBlockRegistry.register(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Block, Block> entry : CommonSetupHandlers.TILLABLES.get().entrySet()) {
            TillableBlockRegistry.register(entry.getKey(), HoeItem::onlyIfAirAbove, entry.getValue().defaultBlockState());
        }
    }
}
