package cn.leolezury.eternalstarlight.neoforge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.VanillaGameEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
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
		ESCommonHandler.onServerTick(event.getServer());
	}

	@SubscribeEvent
	private static void onLevelTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			ESCommonHandler.onLevelTick(serverLevel);
		}
	}

	@SubscribeEvent
	private static void onLevelLoad(LevelEvent.Load event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			ESCommonHandler.onLevelLoad(serverLevel);
		}
	}

	@SubscribeEvent
	private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		ESCommonHandler.onPlayerJoin(event.getEntity());
	}

	@SubscribeEvent
	private static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (!event.isCanceled()) {
			boolean allow = ESCommonHandler.onAllowLivingHurt(event.getEntity(), event.getSource(), event.getAmount());
			if (!allow) {
				event.setCanceled(true);
			}
		}
		event.setAmount(ESCommonHandler.onModifyLivingHurtDamage(event.getEntity(), event.getSource(), event.getAmount()));
		event.getContainer().setPostAttackInvulnerabilityTicks(ESCommonHandler.onModifyPostAttackInvulnerabilityTicks(event.getEntity(), event.getSource(), event.getAmount(), event.getContainer().getPostAttackInvulnerabilityTicks()));
	}

	@SubscribeEvent
	private static void onPreLivingHurt(LivingDamageEvent.Pre event) {
		event.setNewDamage(ESCommonHandler.onModifyLivingActualHurtDamage(event.getEntity(), event.getSource(), event.getNewDamage()));
	}

	@SubscribeEvent
	private static void onPostLivingHurt(LivingDamageEvent.Post event) {
		ESCommonHandler.onPostLivingHurt(event.getEntity(), event.getSource(), event.getNewDamage());
	}

	@SubscribeEvent
	private static void onLivingHeal(LivingHealEvent event) {
		event.setAmount(ESCommonHandler.onLivingHeal(event.getEntity(), event.getAmount()));
	}

	@SubscribeEvent
	private static void onLivingDeath(LivingDeathEvent event) {
		if (!event.isCanceled()) {
			boolean allow = ESCommonHandler.onAllowLivingDeath(event.getEntity(), event.getSource());
			if (!allow) {
				event.setCanceled(true);
			}
		}
		if (!event.isCanceled()) {
			ESCommonHandler.onLivingDeath(event.getEntity(), event.getSource());
		}
	}

	@SubscribeEvent
	private static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
		event.modifyVisibility(ESCommonHandler.onLivingVisibility(event.getEntity(), event.getLookingEntity(), event.getVisibilityModifier()));
	}

	@SubscribeEvent
	private static void onLivingChangeTarget(LivingChangeTargetEvent event) {
		LivingEntity target = ESCommonHandler.onLivingChangeTarget(event.getEntity(), event.getNewAboutToBeSetTarget());
		if (target != event.getNewAboutToBeSetTarget()) {
			event.setNewAboutToBeSetTarget(target);
		}
	}

	@SubscribeEvent
	private static void onLivingBreathe(LivingBreatheEvent event) {
		if (!event.canBreathe() && event.getConsumeAirAmount() > 0) {
			int result = ESCommonHandler.onLivingDecreaseAirSupply(event.getEntity());
			if (result > 0) {
				event.setCanBreathe(true);
				event.setRefillAirAmount(result);
			}
			if (result < 0) {
				event.setConsumeAirAmount(Math.max(event.getConsumeAirAmount() + result, 0));
			}
		}
	}

	@SubscribeEvent
	private static void onLivingTick(EntityTickEvent.Post event) {
		ESCommonHandler.onEntityTick(event.getEntity());
	}

	@SubscribeEvent
	private static void onCriticalHit(CriticalHitEvent event) {
		if (event.isCriticalHit()) {
			ESCommonHandler.onCriticalHit(event.getEntity(), event.getTarget(), event.getEntity().getAttackStrengthScale(0.5f));
		}
	}

	@SubscribeEvent
	private static void onBlockBroken(BlockEvent.BreakEvent event) {
		ESCommonHandler.onBlockBroken(event.getPlayer(), event.getPos(), event.getState());
	}

	@SubscribeEvent
	private static void onBlockBreakSpeed(PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(ESCommonHandler.onBlockBreakSpeed(event.getEntity(), event.getState(), event.getNewSpeed()));
	}

	@SubscribeEvent
	private static void onShieldBlock(LivingShieldBlockEvent event) {
		if (event.getOriginalBlock()) {
			ESCommonHandler.onShieldBlock(event.getEntity(), event.getDamageSource());
		}
	}

	@SubscribeEvent
	private static void onProjectileImpact(ProjectileImpactEvent event) {
		ESCommonHandler.onProjectileImpact(event.getProjectile(), event.getRayTraceResult());
	}

	@SubscribeEvent
	private static void onCompleteAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
		ESCommonHandler.onCompleteAdvancement(event.getEntity(), event.getAdvancement());
	}

	@SubscribeEvent
	private static void onVanillaGameEvent(VanillaGameEvent event) {
		if (!event.isCanceled()) {
			boolean allow = ESCommonHandler.onVanillaGameEvent(event.getLevel(), event.getVanillaEvent(), event.getEventPosition(), event.getContext());
			if (!allow) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	private static void onAddReloadListener(AddReloadListenerEvent event) {
		ESCommonSetupHandler.addReloadListeners(event::addListener);
	}

	@SubscribeEvent
	private static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
		ESCommonSetupHandler.registerFuels(new ESCommonSetupHandler.FuelRegisterStrategy() {
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
	private static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
		ESCommonSetupHandler.registerPotions(new ESCommonSetupHandler.BrewingRegisterStrategy() {
			@Override
			public void registerConversion(Holder<Potion> input, Item ingredient, Holder<Potion> output) {
				event.getBuilder().addMix(input, ingredient, output);
			}

			@Override
			public void registerStart(Item ingredient, Holder<Potion> potion) {
				event.getBuilder().addStartMix(ingredient, potion);
			}
		});
	}

	@SubscribeEvent
	private static void onRegisterCommands(RegisterCommandsEvent event) {
		ESCommonSetupHandler.registerCommands(event.getDispatcher(), event.getBuildContext());
	}

	@SubscribeEvent
	private static void onBlockToolModification(BlockEvent.BlockToolModificationEvent event) {
		if (event.getItemAbility() == ItemAbilities.AXE_STRIP) {
			for (Map.Entry<Block, Block> entry : ESCommonSetupHandler.STRIPPABLES.get().entrySet()) {
				if (event.getState().is(entry.getKey())) {
					event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
				}
			}
		} else if (event.getItemAbility() == ItemAbilities.HOE_TILL) {
			for (Map.Entry<Block, Block> entry : ESCommonSetupHandler.TILLABLES.get().entrySet()) {
				if (event.getState().is(entry.getKey())) {
					event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
				}
			}
		} else if (event.getItemAbility() == ItemAbilities.SHOVEL_FLATTEN) {
			for (Map.Entry<Block, Block> entry : ESCommonSetupHandler.FLATTENABLES.get().entrySet()) {
				if (event.getState().is(entry.getKey())) {
					event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
				}
			}
		}
	}
}
