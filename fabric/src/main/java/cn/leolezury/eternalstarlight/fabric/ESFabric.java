package cn.leolezury.eternalstarlight.fabric;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.fabric.network.FabricNetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SpawnPlacements;

public class ESFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EternalStarlight.init();

        // setup handlers
        // CommonSetupHandlers.setup();
        FabricNetworkHandler.init(false);
        CommonSetupHandlers.createAttributes(FabricDefaultAttributeRegistry::register);
        CommonSetupHandlers.registerSpawnPlacement(SpawnPlacements::register);


        // common handlers
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            CommonHandlers.onRightClickBlock(world, player, hand, hitResult.getBlockPos());
            return InteractionResult.PASS;
        });

        CommonHandlers.addReloadListeners(listener -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener((IdentifiableResourceReloadListener) listener));
    }
}
