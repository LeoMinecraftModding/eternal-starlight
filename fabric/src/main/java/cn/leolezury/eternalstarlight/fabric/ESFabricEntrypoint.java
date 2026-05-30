package cn.leolezury.eternalstarlight.fabric;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
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
		BuiltInRegistries.POINT_OF_INTEREST_TYPE.registryKeySet().forEach(key -> {
			if (key.location().getNamespace().equals(EternalStarlight.ID)) {
				BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolder(key).ifPresent(holder -> holder.value().matchingStates().forEach(state -> PoiTypes.TYPE_BY_STATE.put(state, holder)));
			}
		});

		// setup handlers
		ESCommonSetupHandler.commonSetup();
		ESCommonSetupHandler.registerPackets(new ESCommonSetupHandler.NetworkRegisterStrategy() {
			@Override
			public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
				PayloadTypeRegistry.playC2S().register(packetInfo.type(), packetInfo.streamCodec());
				PayloadTypeRegistry.playS2C().register(packetInfo.type(), packetInfo.streamCodec());
			}
		});
		ESCommonSetupHandler.registerPackets(new ESCommonSetupHandler.NetworkRegisterStrategy() {
			@Override
			public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
				ServerPlayNetworking.registerGlobalReceiver(packetInfo.type(), (payload, context) -> packetInfo.handler().handle(payload, context.player()));
			}
		});
		ESCommonSetupHandler.createAttributes(FabricDefaultAttributeRegistry::register);
		ESCommonSetupHandler.registerSpawnPlacements(SpawnPlacements::register);
		ESCommonSetupHandler.registerFuels(new ESCommonSetupHandler.FuelRegisterStrategy() {
			@Override
			public void register(ItemLike item, int time) {
				FuelRegistry.INSTANCE.add(item, time);
			}

			@Override
			public void register(TagKey<Item> itemTag, int time) {
				FuelRegistry.INSTANCE.add(itemTag, time);
			}
		});
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> ESCommonSetupHandler.registerPotions(new ESCommonSetupHandler.BrewingRegisterStrategy() {
			@Override
			public void registerConversion(Holder<Potion> input, Item ingredient, Holder<Potion> output) {
				builder.registerPotionRecipe(input, Ingredient.of(ingredient), output);
			}

			@Override
			public void registerStart(Item ingredient, Holder<Potion> potion) {
				builder.registerRecipes(Ingredient.of(ingredient), potion);
			}
		}));
		CommandRegistrationCallback.EVENT.register(((dispatcher, context, environment) -> ESCommonSetupHandler.registerCommands(dispatcher, context)));
		ESCommonSetupHandler.registerChunkGenerator();
		ESCommonSetupHandler.registerBiomeSource();
		ESCommonSetupHandler.addReloadListeners(listener -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener((IdentifiableResourceReloadListener) listener));

		// common handlers
		ServerTickEvents.END_SERVER_TICK.register(ESCommonHandler::onServerTick);
		ServerTickEvents.START_WORLD_TICK.register(ESCommonHandler::onLevelTick);
		ServerWorldEvents.LOAD.register((server, world) -> ESCommonHandler.onLevelLoad(world));
		ServerPlayerEvents.JOIN.register(ESCommonHandler::onPlayerJoin);
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> ESCommonHandler.onBlockBroken(player, pos, state));
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(ESCommonHandler::onAllowLivingHurt);
		ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> ESCommonHandler.onAllowLivingDeath(entity, source));
		ServerLivingEntityEvents.AFTER_DEATH.register(ESCommonHandler::onLivingDeath);

		for (Map.Entry<Block, Block> entry : ESCommonSetupHandler.STRIPPABLES.get().entrySet()) {
			StrippableBlockRegistry.register(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<Block, Block> entry : ESCommonSetupHandler.TILLABLES.get().entrySet()) {
			TillableBlockRegistry.register(entry.getKey(), HoeItem::onlyIfAirAbove, entry.getValue().defaultBlockState());
		}
		for (Map.Entry<Block, Block> entry : ESCommonSetupHandler.FLATTENABLES.get().entrySet()) {
			FlattenableBlockRegistry.register(entry.getKey(), entry.getValue().defaultBlockState());
		}
	}
}
