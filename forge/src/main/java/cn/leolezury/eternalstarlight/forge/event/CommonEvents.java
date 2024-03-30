package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.living.ShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

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
    public static void onHoeTillBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().getBlockState(event.getPos()).is(ESTags.Blocks.NIGHTSHADE_TILLABLE_BLOCK) && event.getItemStack().getItem() instanceof HoeItem) {
            event.getLevel().setBlockAndUpdate(event.getPos(), ESBlocks.NIGHTSHADE_FARMLAND.get().defaultBlockState());
            event.getItemStack().hurtAndBreak(1, event.getEntity(), reason -> reason.broadcastBreakEvent(event.getHand()));
            event.getLevel().playSound(event.getEntity(), event.getPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
