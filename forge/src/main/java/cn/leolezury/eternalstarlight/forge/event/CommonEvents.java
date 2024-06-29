package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
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
	public static void onServerTick(ServerTickEvent.Post event) {
		CommonHandlers.onServerTick(event.getServer());
	}

	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			CommonHandlers.onLevelTick(serverLevel);
		}
	}

	@SubscribeEvent
	public static void onLevelLoad(LevelEvent.Load event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			CommonHandlers.onLevelLoad(serverLevel);
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingDamageEvent.Pre event) {
		event.getContainer().setNewDamage(CommonHandlers.onLivingHurt(event.getEntity(), event.getContainer().getSource(), event.getContainer().getOriginalDamage()));
	}

	@SubscribeEvent
	public static void onLivingTick(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof LivingEntity living) {
			CommonHandlers.onLivingTick(living);
		}
	}

	@SubscribeEvent
	public static void onBlockBroken(BlockEvent.BreakEvent event) {
		CommonHandlers.onBlockBroken(event.getPlayer(), event.getPos(), event.getState());
	}

	@SubscribeEvent
	public static void onShieldBlock(LivingShieldBlockEvent event) {
		CommonHandlers.onShieldBlock(event.getEntity(), event.getDamageSource());
	}

	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event) {
		CommonHandlers.onArrowHit(event.getProjectile(), event.getRayTraceResult());
	}

	@SubscribeEvent
	public static void onCompleteAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
		CommonHandlers.onCompleteAdvancement(event.getEntity(), event.getAdvancement());
	}

	@SubscribeEvent
	public static void onAddReloadListener(AddReloadListenerEvent event) {
		CommonHandlers.addReloadListeners(event::addListener);
	}

	@SubscribeEvent
	public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
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
	public static void onCommandRegister(RegisterCommandsEvent event) {
		CommonSetupHandlers.registerCommands(event.getDispatcher(), event.getBuildContext());
	}

	@SubscribeEvent
	public static void onToolModify(BlockEvent.BlockToolModificationEvent event) {
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
		}
	}
}
