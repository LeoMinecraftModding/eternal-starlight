package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ChainOfSoulsItem extends Item {
	public ChainOfSoulsItem(Properties properties) {
		super(properties);
	}

	@NotNull
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack stack = player.getItemInHand(interactionHand);
		if (!level.isClientSide) {
			ChainOfSouls hook = level.getEntity(ESDataAttachments.GRAPPLING.getData(player)) instanceof ChainOfSouls chain ? chain : null;
			if (hook != null) {
				retrieve(level, player, hook);
			} else {
				stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
				this.shoot(level, player, stack);
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
	}

	private void shoot(Level level, Player player, ItemStack weapon) {
		if (!level.isClientSide) {
			level.addFreshEntity(new ChainOfSouls(level, player, weapon));
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		player.gameEvent(GameEvent.ITEM_INTERACT_START);
	}

	private static void retrieve(Level level, Player player, ChainOfSouls hook) {
		if (!level.isClientSide()) {
			hook.discard();
			ESDataAttachments.GRAPPLING.setData(player, -1);
		}

		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
	}

	@Override
	public int getEnchantmentValue() {
		return 1;
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESItems.TENACIOUS_PETAL.get()) || repairCandidate.is(ESItems.TENACIOUS_VINE.get()) || super.isValidRepairItem(stack, repairCandidate);
	}
}
