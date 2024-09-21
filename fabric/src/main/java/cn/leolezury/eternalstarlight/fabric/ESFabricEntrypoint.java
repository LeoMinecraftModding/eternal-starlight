package cn.leolezury.eternalstarlight.fabric;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.fabric.network.FabricNetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Holder;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class ESFabricEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {
		EternalStarlight.init();

		// setup handlers
		CommonSetupHandlers.commonSetup();
		FabricNetworkHandler.registerPackets();
		FabricNetworkHandler.registerPacketReceivers();
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
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> CommonSetupHandlers.registerPotions(new CommonSetupHandlers.BrewingRegisterStrategy() {
			@Override
			public void registerConversion(Holder<Potion> input, Item ingredient, Holder<Potion> output) {
				builder.registerPotionRecipe(input, Ingredient.of(ingredient), output);
			}

			@Override
			public void registerStart(Item ingredient, Holder<Potion> potion) {
				builder.registerRecipes(Ingredient.of(ingredient), potion);
			}
		}));
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
		for (Map.Entry<Block, Block> entry : CommonSetupHandlers.FLATTENABLES.get().entrySet()) {
			FlattenableBlockRegistry.register(entry.getKey(), entry.getValue().defaultBlockState());
		}
	}
}
