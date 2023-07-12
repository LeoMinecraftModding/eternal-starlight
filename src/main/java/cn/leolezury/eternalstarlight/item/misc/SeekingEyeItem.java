package cn.leolezury.eternalstarlight.item.misc;

import cn.leolezury.eternalstarlight.entity.misc.EyeOfSeeking;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
import cn.leolezury.eternalstarlight.util.SLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;

public class SeekingEyeItem extends Item {
    public SeekingEyeItem(Properties p) {
        super(p);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.fail(itemStack);
        }
        player.startUsingItem(hand);
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            TagKey<Structure> structureTagKey = SLTags.Structures.BOSS_STRUCTURES;
            if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.REDSTONE)) {
                structureTagKey = SLTags.Structures.GOLEM_FORGE;
            }
            if (player.getItemInHand(InteractionHand.OFF_HAND).is(ItemInit.RED_CRYSTAL_MOSS_CARPET.get()) || player.getItemInHand(InteractionHand.OFF_HAND).is(ItemInit.BLUE_CRYSTAL_MOSS_CARPET.get())) {
                structureTagKey = SLTags.Structures.CURSED_GARDEN;
            }
            // else if
            BlockPos blockPos = serverLevel.findNearestMapStructure(structureTagKey, player.blockPosition(), 100, false);
            if (blockPos != null) {
                EyeOfSeeking eyeOfSeeking = new EyeOfSeeking(level, player.getX(), player.getY(0.5D), player.getZ());
                eyeOfSeeking.setItem(itemStack);
                eyeOfSeeking.signalTo(blockPos);
                level.gameEvent(GameEvent.PROJECTILE_SHOOT, eyeOfSeeking.position(), GameEvent.Context.of(player));
                level.addFreshEntity(eyeOfSeeking);

                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEventInit.SEEKING_EYE_LAUNCH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                player.swing(hand, true);
                return InteractionResultHolder.success(itemStack);
            }
        }

        return InteractionResultHolder.consume(itemStack);
    }
}
