package cn.leolezury.eternalstarlight;

import cn.leolezury.eternalstarlight.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.handler.CommonSetupHandlers;
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
        // setup handlers
        // CommonSetupHandlers.setup();
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
