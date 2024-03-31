package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.living.ShieldBlockEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            CommonHandlers.onServerTick(event.getServer());
        }
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.level instanceof ServerLevel serverLevel) {
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
    public static void onLivingHurt(LivingHurtEvent event) {
        event.setAmount(CommonHandlers.onLivingHurt(event.getEntity(), event.getSource(), event.getAmount()));
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        CommonHandlers.onLivingTick(event.getEntity());
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        CommonHandlers.onBlockBroken(event.getPlayer(), event.getPos(), event.getState());
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        CommonHandlers.onShieldBlock(event.getEntity(), event.getDamageSource());
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        CommonHandlers.onArrowHit(event.getProjectile(), event.getRayTraceResult());
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
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            for (Map.Entry<Block, Block> entry : CommonSetupHandlers.STRIPPABLES.get().entrySet()) {
                if (event.getState().is(entry.getKey())) {
                    event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
                }
            }
        } else if (event.getToolAction() == ToolActions.HOE_TILL) {
            for (Map.Entry<Block, Block> entry : CommonSetupHandlers.TILLABLES.get().entrySet()) {
                if (event.getState().is(entry.getKey())) {
                    event.setFinalState(entry.getValue().withPropertiesOf(event.getState()));
                }
            }
        }
    }
}
