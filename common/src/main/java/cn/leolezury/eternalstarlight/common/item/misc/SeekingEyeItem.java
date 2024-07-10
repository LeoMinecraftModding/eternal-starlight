package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.entity.misc.EyeOfSeeking;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;

public class SeekingEyeItem extends Item {
	public SeekingEyeItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		if (level instanceof ServerLevel serverLevel) {
			TagKey<Structure> key = ESTags.Structures.BOSS_STRUCTURES;
			if (player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND).is(ESTags.Items.GOLEM_FORGE_LOCATORS)) {
				key = ESTags.Structures.GOLEM_FORGE;
			}
			if (player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND).is(ESTags.Items.CURSED_GARDEN_LOCATORS)) {
				key = ESTags.Structures.CURSED_GARDEN;
			}
			BlockPos blockPos = serverLevel.findNearestMapStructure(key, player.blockPosition(), 100, false);
			if (blockPos != null) {
				EyeOfSeeking eyeOfSeeking = new EyeOfSeeking(level, player.getX(), player.getY(0.5D), player.getZ());
				eyeOfSeeking.setItem(itemStack);
				eyeOfSeeking.signalTo(blockPos);
				level.gameEvent(GameEvent.PROJECTILE_SHOOT, eyeOfSeeking.position(), GameEvent.Context.of(player));
				level.addFreshEntity(eyeOfSeeking);

				level.playSound(null, player.getX(), player.getY(), player.getZ(), ESSoundEvents.SEEKING_EYE_LAUNCH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
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
