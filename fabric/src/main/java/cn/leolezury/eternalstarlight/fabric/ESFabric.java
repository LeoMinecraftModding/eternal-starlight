package cn.leolezury.eternalstarlight.fabric;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.HoeItem;
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
        ServerTickEvents.END_SERVER_TICK.register(CommonHandlers::onServerTick);
        ServerTickEvents.START_WORLD_TICK.register(CommonHandlers::onLevelTick);
        ServerWorldEvents.LOAD.register((server, world) -> CommonHandlers.onLevelLoad(world));

        CommonHandlers.addReloadListeners(listener -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener((IdentifiableResourceReloadListener) listener));

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.getBlockState(hitResult.getBlockPos()).is(ESTags.Blocks.NIGHTSHADE_TILLABLE_BLOCK) && player.getItemInHand(hand).getItem() instanceof HoeItem) {
                world.setBlockAndUpdate(hitResult.getBlockPos(), ESBlocks.NIGHTSHADE_FARMLAND.get().defaultBlockState());
                player.getItemInHand(hand).hurtAndBreak(1, player, reason -> reason.broadcastBreakEvent(hand));
                world.playSound(player, hitResult.getBlockPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
