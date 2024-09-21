package cn.leolezury.eternalstarlight.neoforge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Map;

@EventBusSubscriber(modid = EternalStarlight.ID)
public class CommonEvents {
	@SubscribeEvent
	private static void onServerTick(ServerTickEvent.Post event) {
		CommonHandlers.onServerTick(event.getServer());
	}

	@SubscribeEvent
	private static void onLevelTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			CommonHandlers.onLevelTick(serverLevel);
		}
	}

	@SubscribeEvent
	private static void onLevelLoad(LevelEvent.Load event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			CommonHandlers.onLevelLoad(serverLevel);
		}
	}

	@SubscribeEvent
	private static void onLivingHurt(LivingDamageEvent.Pre event) {
		event.setNewDamage(CommonHandlers.onLivingHurt(event.getEntity(), event.getContainer().getSource(), event.getContainer().getNewDamage()));
	}

	@SubscribeEvent
	private static void onLivingTick(EntityTickEvent.Post event) {
		CommonHandlers.onEntityTick(event.getEntity());
	}

	@SubscribeEvent
	private static void onBlockBroken(BlockEvent.BreakEvent event) {
		CommonHandlers.onBlockBroken(event.getPlayer(), event.getPos(), event.getState());
	}

	@SubscribeEvent
	private static void onShieldBlock(LivingShieldBlockEvent event) {
		if (event.getOriginalBlock()) {
			CommonHandlers.onShieldBlock(event.getEntity(), event.getDamageSource());
		}
	}

	@SubscribeEvent
	private static void onProjectileImpact(ProjectileImpactEvent event) {
		CommonHandlers.onArrowHit(event.getProjectile(), event.getRayTraceResult());
	}

	@SubscribeEvent
	private static void onCompleteAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
		CommonHandlers.onCompleteAdvancement(event.getEntity(), event.getAdvancement());
	}

	@SubscribeEvent
	private static void onAddReloadListener(AddReloadListenerEvent event) {
		CommonHandlers.addReloadListeners(event::addListener);
	}

	@SubscribeEvent
	private static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
		CommonSetupHandlers.registerFuels(new CommonSetupHandlers.FuelRegisterStrategy() {
			@Override
			public void register(ItemLike item, int time) {
				if (event.getItemStack().is(item.asItem())) {
					event.setBurnTime(time);
				}
			}

			@Override
			public void register(TagKey<Item> itemTag, int time) {
				if (event.getItemStack().is(itemTag)) {
					event.setBurnTime(time);
				}
			}
		});
	}

	@SubscribeEvent
	private static void onCommandRegister(RegisterCommandsEvent event) {
		CommonSetupHandlers.registerCommands(event.getDispatcher(), event.getBuildContext());
	}

	@SubscribeEvent
	private static void onBlockToolModification(BlockEvent.BlockToolModificationEvent event) {
		if (event.getItemAbility() == ItemAbilities.AXE_STRIP) {
			for (Map.Entry<Block, Block> entry : CommonSetupHandlers.STRIPPABLES.get().entrySet()) {
				if (event.getState().is(entry.getKey())) {
					event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
				}
			}
		} else if (event.getItemAbility() == ItemAbilities.HOE_TILL) {
			for (Map.Entry<Block, Block> entry : CommonSetupHandlers.TILLABLES.get().entrySet()) {
				if (event.getState().is(entry.getKey())) {
					event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
				}
			}
		} else if (event.getItemAbility() == ItemAbilities.SHOVEL_FLATTEN) {
			for (Map.Entry<Block, Block> entry : CommonSetupHandlers.FLATTENABLES.get().entrySet()) {
				if (event.getState().is(entry.getKey())) {
					event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
				}
			}
		}
	}
}
